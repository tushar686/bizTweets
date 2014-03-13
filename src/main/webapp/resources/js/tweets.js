var page = {cursor: 0};

function loadTweets() {
	setHighlitedMenu(2);
	restCall("GET", "/bizTweets/getTweets?user=tushar686@gmail.com&cursor="+ page.cursor, "", "json", "application/json", success_loadTweets);
}

function loadNextTweets() {
	page.cursor = page.cursor + 1;
	loadTweets();
}

function loadPreviousTweets() {
	if(page.cursor > 0) {
		page.cursor = page.cursor - 1;
		loadTweets();
	}
}

function success_loadTweets(response) {
	var div_main = "<div class='divTable wordwrap'>" + generatePrevAndNextLinks();
	$.each(response, function (index, tweetsPerEntity) {		
		div_main = div_main + generateHTMLForEntity(tweetsPerEntity);
	});
	
	$("#content").html(div_main + "</div>");
}

function generatePrevAndNextLinks() {
	var div_prevNext = "<div>"
		+ "<span><a href='#' onClick='loadPreviousTweets()'>Previous</a></span>"
		+ "<span class='next'><a href='#' onClick='loadNextTweets()'>Next</a></span>"
		+ "</div>";
	
	return div_prevNext;	
}

function generateHTMLForEntity(tweetsPerEntity) {
	var div_entities = "<div class='entitySeparator'>";
	$.each(tweetsPerEntity, function (index, tweets) {
		tweetsObj = $.parseJSON(tweets);			 
		div_entities = div_entities + generateHTMLForTweets(tweetsObj);
	});
	return div_entities + "</div>";
}

function generateHTMLForTweets(tweetsObj) {
	var div_tweets = "<div class='tweetsSeparator'>";
	for(var key in tweetsObj) {
		if(key.toString() != "_id") {
			div_tweets = div_tweets 
			+ "<div class='divRow'><div class='divCell tweets'>"   
			+ key + " = " + tweetsObj[key] 
			+"</div></div>";
		}
	}
	return div_tweets + "</div>";
}
