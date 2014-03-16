var tweetsPage = {cursor: 1};

function loadTweets() {
	setHighlitedMenu(2);
	showProgressbar();
	restCall("GET", "/bizTweets/getTweets?user=tushar686@gmail.com&cursor="+ tweetsPage.cursor, "", "json", "application/json", success_loadTweets);
}

function loadNextTweets() {
	if(tweetsPage.cursor > 0) {
		tweetsPage.cursor = tweetsPage.cursor - 1;
		loadTweets();
	}
}

function loadPreviousTweets() {
	tweetsPage.cursor = tweetsPage.cursor + 1;
	loadTweets();
}

function success_loadTweets(response) {
	hideProgressbar();
	var tableOfTweets = "<table class='table table-striped table-hover'>";
	
	tableOfTweets = tableOfTweets + generatePrevAndNextLinks();	
	tableOfTweets = tableOfTweets + iterateThroughEachFollowedEntity(response);	
	tableOfTweets = tableOfTweets + "</tbody></table>";
	
	$("#content").html(tableOfTweets);
}

function iterateThroughEachFollowedEntity(response) {
	var entityRows = ""
	$.each(response, function (index, tweetsOfEntity) {
		entityRows = entityRows + "<thead><tr><th colspan=2 class='danger'>Entity " + (index + 1) + "</th></tr></thead>";
		entityRows = entityRows + iterateThroughTweetsOfEntity(tweetsOfEntity);
	});
	console.log(entityRows);
	return entityRows;
}

function generatePrevAndNextLinks() {
	var prevNext = "<tr>"
		+ "<td><a href='#' onClick='loadPreviousTweets()'>Previous</a></td>"
		+ "<td><a href='#' onClick='loadNextTweets()'>Next</a></td>"
		+ "</tr>";
	
	return prevNext;	
}

function iterateThroughTweetsOfEntity(tweetsOfEntity) {
	var tweetsRow = "";
	$.each(tweetsOfEntity, function (index, tweets) {
		tweetsObj = $.parseJSON(tweets);			 
		tweetsRow = tweetsRow + "<tr><td>" + (index + 1) + "</td>"
				 + "<td>" + generateHTMLForTweets(tweetsObj) + "</td>";
				 + "</tr>";
	});
	return tweetsRow;
}

function generateHTMLForTweets(tweetsObj) {
	var tweetDiv = "<div>";
	for(var key in tweetsObj) {
		if(key.toString() != "_id") {
			tweetDiv = tweetDiv + "<div>" + key + " = " + tweetsObj[key] +"</div>";
		}
	}
	return tweetDiv + "</div>";
}
