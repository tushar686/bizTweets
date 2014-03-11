var page = {id: ""};

function loadEntities() {
	setHighlitedMenu(1);
	restCall("GET", "/bizTweets/getEntities", "", "json", "application/json", success_loadEntities);
}

function success_loadEntities(response) {
	var div_entities = "<div class='divTable wordwrap'>";
	$.each(response, function (index, schema) {
		entity = ($.parseJSON(schema));
		if(index > 2 && entity._id.indexOf("field") == -1) {
			div_entities = div_entities 
			+ "<div class='divRow'><div class='divCell entity'>" +  entity._id.replace(/\;/g,' | ') 
			+ " &nbsp;" + chooseFollowOrUnfollowLink(index, entity._id) + "</div></div>";
		}
	});
	div_entities = div_entities + "</div>";	
	$("#content").html(div_entities);
}

function chooseFollowOrUnfollowLink(index, entity) {
	var alreadyFollowing = false;
	$.each(userDetails.details, function (index, detail) {
		userJSON = $.parseJSON(detail);
		if(userJSON.followingEntity == entity)
			alreadyFollowing = true;
	});
	if(alreadyFollowing)
		return "<a href='#' id=follow" + index + " class=" + entity + " onclick='unfollow(this.id)'>Unfollow</a>";
	return "<a href='#' id=follow" + index + " class=" + entity + " onclick='follow(this.id)'>Follow</a>";
}

function follow(id) {
	restCall("POST", "/bizTweets/follow", "{\"followingEntity\": \""+$("#"+id).attr("class")+"\",\"user\": \"tushar686@gmail.com\"}", "json", "application/json", "", success_follow);
	page.id = id;
}

function success_follow(response) {
	if(response.status == 201)
		$("#" + page.id).text("Unfollow");
}

function unfollow(id) {
	restCall("DELETE", "/bizTweets/unfollow?user=tushar686@gmail.com&unfollowingEntity="+$("#"+id).attr("class"), "", "json", "application/json", "", success_unfollow);
	page.id = id;
}

function success_unfollow(response) {
	if(response.status == 204)
		$("#" + page.id).text("Follow");
}

