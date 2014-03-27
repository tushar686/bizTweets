var searchPage = {cursor: 1, dialogContent: "", prevDialogRef: "", badgeId: "", entityId: ""};

function freshSearchTweets() {
	showProgressbar();
	searchPage.cursor = 1;
	searchPage.prevDialogRef = "";
	searchPage.dialogContent = "";
	searchTweets();
}

function searchTweets() {
	restCall("GET", "/bizTweets/searchTweets?user=tushar686@gmail.com&cursor="+ searchPage.cursor + "&searchString=" + $("#srch-term").val() + "&queryType=ExactMatch", "", "json", "application/json", success_searchTweets);
}


function success_searchTweets(response) {
	hideProgressbar();
	var searchResult = searchPage.dialogContent + "<table class='table table-striped table-hover'><tbody>";
	searchResult = searchResult + generateLinkForMoreTweets();
	searchResult = searchResult + iterateThroughTweetsAndGenerateRows(response);
	searchResult = searchResult + generateLinkForMoreTweets();
	searchResult = searchResult + "</tbody></table>";
	showDialog(searchResult);
	searchPage.dialogContent = searchPage.dialogContent + searchResult;
}

function iterateThroughTweetsAndGenerateRows(response) {
	var searchResult = "";
	var prevEntityName = "";
	$.each(response, function (index_tweetList, tweetList) {
	$.each(tweetList, function (index_tweets, tweets) {
		var entityId = "entity" + searchPage.cursor + index_tweetList + "_" + index_tweets;
		searchResult = searchResult + entityRowSeparator(entityId, prevEntityName, tweets.entityName);
		prevEntityName = tweets.entityName;
		searchResult = searchResult + iterateThroughTweetsMetadataAndGenerateRows(entityId, tweets.metadata);
	});
	});
	return searchResult;
}

function entityRowSeparator(entityId, prevEntityName, currentEntityName) {
	var row = "<tr class=";
	var col = "<td>" + currentEntityName + "<span>"+ generateLinkForEntityFollow(entityId, currentEntityName) +"</span>" + "</td></tr>";
	if(prevEntityName != currentEntityName)
		return row + "'danger'>" + col;
	return row + "'success'>" + col;;
}


function iterateThroughTweetsMetadataAndGenerateRows(entityId, metadata) {
	var searchResult = "<tr><td>";
	$.each(metadata, function (index_meta, metadata) {
		var fieldId = entityId + "field" + index_meta;
		searchResult = searchResult 
			+ "<button id=" + fieldId 
			+ " type='button' onClick='toggleFieldButtonColor(this.id)' "
			+ "class='btn btn-success'>" + metadata.key + ": " + metadata.value 
			+ "&nbsp;" + generateLinkBadgeForFieldFollow(fieldId)
			+ "</button>";
		
	});
	searchResult = searchResult + "</td></tr>";
	return searchResult;
}

function toggleFieldButtonColor(id) {
	if($("#" + id).attr("class").indexOf("btn-success") > -1) {
		$("#" + id).removeClass("btn-success");
		$("#" + id).addClass("btn-danger");
	} else {
		$("#" + id).removeClass("btn-danger");
		$("#" + id).addClass("btn-success");
	}

}

function showDialog(tweets) {
	if(searchPage.prevDialogRef != "")
		searchPage.prevDialogRef.close();
	
	searchPage.prevDialogRef = new BootstrapDialog({
		buttons: [ {
            label: 'Close',
            action: function(dialogRef){
            	loadTweets();
                dialogRef.close();
            }
        }],
        closable: false,
		title: "Search Results",
        message: tweets,
    });
	
	searchPage.prevDialogRef.open();
}

function generateLinkForMoreTweets() {
	return "<tr>"
		+ "<td align='right'><button type='button' onClick='loadMoreTweets()' class='btn btn-link'>More..</button></td>"
		+ "</tr>";
}

function loadMoreTweets() {
	showProgressbar();
	searchPage.cursor = searchPage.cursor + 1;
	searchTweets();
}

function generateLinkForEntityFollow(entityId, currentEntityName) {
	return "<button id="+ entityId +" lang="+ currentEntityName +" type='button' onClick='followEntity(this.id)' class='btn btn-link'>Follow..</button>";
}

function generateLinkBadgeForFieldFollow(fieldId) {
	return "<span id=badge" + fieldId + " class='badge btn-info' onClick='followField(this.id)'>f</span>";
}

function followField(badgeId) {
	var fieldId = badgeId.substring(5);
	toggleFieldButtonColor(fieldId);
	var fieldNameValue = $("#"+ fieldId).text();
	fieldNameValue = fieldNameValue.substring(0, fieldNameValue.indexOf("f"));
	showDialogForFieldFollow(fieldNameValue, badgeId);
}

function showDialogForFieldFollow(fieldNameValue, badgeId) {
	BootstrapDialog.show({
        message: buildFollowFieldDialogContent(fieldNameValue.split(":")[0], fieldNameValue.split(":")[1]),
        closable: false,
        buttons: [{
            label: 'Follow',
            cssClass: 'btn-success',
            action: function(dialogRef){
            	searchPage.badgeId = badgeId;
            	makeFollowFieldRESTCall(fieldNameValue.split(":")[0]);
            	dialogRef.close();
            }
        }, {
            label: 'Close',
            action: function(dialogRef){
                dialogRef.close();
            }
        }]
    });
	
}

function buildFollowFieldDialogContent(fieldName, fieldValue) {
	var content = "<table class='table table-striped table-hover'><tbody>"
		+ "<tr><td>" + fieldName +"</td>"
		+ "<td>" + getDropDownOperators() + "</td>"
		+ "<td><input type='text' id='fieldOperandValue' class='form-control' placeholder='" + fieldValue + "'></td>"
		+ "</tr></tbody></table>";
	return content;
}

function getDropDownOperators() {
	var dropDown = "<select class='selectpicker' id='fieldOperatorOption'>"
						+ "<option>=</option>"
						+ "<option><</option>"
						+ "<option>></option>"
						+ "<option>startsWith</option>"
						+ "<option>endsWith</option>"
				+ "</select>";
	return dropDown;
}

function makeFollowFieldRESTCall(fieldName) {
	var jsonUsersObj = buildJSONForFollowRequest("", fieldName, $("#fieldOperatorOption").val(), $("#fieldOperandValue").val());
	restCall("POST", "/bizTweets/follow",jsonUsersObj, "json", "application/json", "", success_followField);
}


function buildJSONForFollowRequest(entityName, fieldName, fieldOperator, fieldValue) {
var req = 	"{" +
			  "\"user\": \"tushar686@gmail.com\"," +
			  "\"follow\": {" +
			  "\"entityName\": \"" + entityName + "\"," + 
			  "\"followMetadata\": [" +
			  	 "{" +
			        "\"field\": \""+ fieldName +"\"," +
			        "\"fieldValue\": \"" + fieldValue + "\"," +
			        "\"operator\": \""+ fieldOperator +"\"," +
			        "\"nestedOperator\": \"\"" +
			      "}" +
			    "]" +
			  "}" +
			"}";
	return req;
}

function success_followField(response) {
	if(response.status == 201) {
		$("#" + searchPage.badgeId).text("u");
		
		disableBadgeHoverEfferct();
		highlightEntireButtonWithWarning();
	}
}

function disableBadgeHoverEfferct() {
	$("#" + searchPage.badgeId).removeClass("btn-info");
}

function highlightEntireButtonWithWarning() {
	var fieldId = searchPage.badgeId.substring(5);
	$("#" + fieldId).removeClass("btn-success");		
	$("#" + fieldId).addClass("btn-warning");
}

function followEntity(entityId) {
	makeFollowEntityRESTCall($("#" + entityId).attr("lang"));
	searchPage.entityId = entityId;
}

function makeFollowEntityRESTCall(entityName) {
	var jsonUsersObj = buildJSONForFollowRequest(entityName, "", "", "");
	restCall("POST", "/bizTweets/follow",jsonUsersObj, "json", "application/json", "", success_followEntity);
}

function success_followEntity(response) {
	if(response.status == 201) {
		$("#" + searchPage.entityId).text("Unfollow..");
	}
}