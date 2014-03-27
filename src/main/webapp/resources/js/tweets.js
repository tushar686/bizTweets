var tweetsPage = {cursor: 1};

function loadTweets() {
	showProgressbar();
	setHighlitedMenu(0);
	$("#content").html("");
	restCall("GET", "/bizTweets/getTweets?user=tushar686@gmail.com&cursor="+ tweetsPage.cursor, "", "json", "application/json", success_loadTweets);
}

function loadNextTweets() {
	showProgressbar();
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
	console.log(response);
	hideProgressbar();
	var tableOfTweets = "<table class='table table-striped table-hover'>";
	
	tableOfTweets = tableOfTweets + generatePrevAndNextLinks();	
	tableOfTweets = tableOfTweets + iterateThroughEachFollowedEntity(response);	
	tableOfTweets = tableOfTweets + "</tbody></table>";
	
	$("#content").html(tableOfTweets);
}

function iterateThroughEachFollowedEntity(response) {
	var entityRows = "";
	$.each(response, function (index, tweetsOfEntity) {
		entityRows = entityRows + "<thead><tr><th colspan=2 class='danger'> " + (index + 1) + "</th></tr></thead>";
		entityRows = entityRows + iterateThroughTweetsOfEntity(tweetsOfEntity);
	});
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
		tweetsRow = tweetsRow + "<tr><td>" + (index + 1) + "</td>"
		 + "<td>" + generateHTMLForTweets(tweets) + "</td>";
		 + "</tr>";
	});
	return tweetsRow;
}

function generateHTMLForTweets(tweets) {
	var tweetDiv = "<div>";
	for(var tweet in tweets) {
		if(tweet.toString() == "entityName") {
			tweetDiv = tweetDiv + "<div>" + tweet + " = " + tweets[tweet] +"</div>";
		} else if(tweet.toString() == "metadata") {
			tweetDiv = tweetDiv + "<div>";
			for(index in tweets[tweet]) {
				tweetDiv = tweetDiv + "<span>" + tweets[tweet][index].key + " : " + tweets[tweet][index].value +" &nbsp;</span>";
			}
			tweetDiv = tweetDiv + "</div>";
		}
	}
	return tweetDiv + "</div>";
}
