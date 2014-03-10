function loadBizEntities() {
	setHighlitedMenu(1);
	restCall("GET", "/bizTweets/getEntities", "", "json", "application/json", success);
}

function success(response) {
	console.log(response);
	var div_entities = "<div class='divTable wordwrap'>"
	$.each(response, function (index, schema) {
		entity = (jQuery.parseJSON(schema));
		if(index > 2 && entity._id.indexOf("field") == -1) {
			div_entities = div_entities 
			+ "<div class='divRow'><div class='divCell entity'>" +  entity._id.replace(/\;/g,' | ') 
			+ " &nbsp;<a href='#' id=follow" + index + " class=" + entity._id + " onclick='follow(this.id)'>Follow</a></div></div>";
		}
	});
	div_entities = div_entities + "</div>";
	
	$("#content").html(div_entities);
}

function follow(id) {
	$("#" + id).text("Unfollow");
}