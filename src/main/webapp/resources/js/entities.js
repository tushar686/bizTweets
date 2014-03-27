var entitiesPage = {id: ""}

function loadEntities() {
	restCall("GET", "/bizTweets/getEntities", "", "json", "application/json", success_loadEntities);
}

function success_loadEntities(response) {
	global.entities = response;
}

function showEntities() {
	setHighlitedMenu(1);
	$("#content").html(generateTableOfEntities());
}

function generateTableOfEntities() {
	var tableOfEntities = "<table class='table table-striped table-hover'>";
	tableOfEntities = tableOfEntities + "<thead><tr><th colspan=3 class='danger'>Entities</th></tr></thead><tbody>";
	
	tableOfEntities = tableOfEntities + iterateThroughAllEntities();
	
	return tableOfEntities = tableOfEntities + "</tbody></table>";
	
}

function iterateThroughAllEntities() {
	var entityRows = "";
	$.each(global.entities, function (index, schema) {
		jsonSchema = $.parseJSON(schema);
		entityRows = entityRows 
		+ "<tr>"
		+ "<td>" + (index+1) + "</td>"
		+ "<td>" +  jsonSchema.entity.replace(/\,/g,' | ')  + "</td>"
		+ "<td>" + chooseFollowOrUnfollowLink(index, jsonSchema.entity) + "</td>"
		+ "</tr>";
		
	});
	return entityRows;
}

function chooseFollowOrUnfollowLink(index, entity) {
	var alreadyFollowing = false;
	$.each(global.usersFollowEntities, function (index, followingEntity) {
		if(followingEntity == entity)
			alreadyFollowing = true;
	});
	if(alreadyFollowing)
		return "<a href='#' id=follow" + index + " class=" + entity + " onclick='unfollow(this.id)'>Unfollow</a>";
	return "<a href='#' id=follow" + index + " class=" + entity + " onclick='follow(this.id)'>Follow</a>";
}

function follow(id) {
	restCall("POST", "/bizTweets/follow", "{\"followingEntity\": \""+$("#"+id).attr("class")+"\",\"user\": \"tushar686@gmail.com\"}", "json", "application/json", "", success_follow);
	entitiesPage.id = id;
}

function success_follow(response) {
	if(response.status == 201)
		$("#" + entitiesPage.id).text("Unfollow");
}

function unfollow(id) {
	restCall("DELETE", "/bizTweets/unfollow?user=tushar686@gmail.com&unfollowingEntity="+$("#"+id).attr("class"), "", "json", "application/json", "", success_unfollow);
	entitiesPage.id = id;
}

function success_unfollow(response) {
	if(response.status == 204)
		$("#" + entitiesPage.id).text("Follow");
}

