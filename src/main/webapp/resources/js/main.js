var global = {usersFollowEntities: "", entities: ""}

$(function(){
	hideProgressbar();
	loadUserDetails();
	loadEntities();
	});

function setHighlitedMenu(inexOfClickedMenu) {
	$('.nav li').each(function() {
		if($(this).index() == inexOfClickedMenu) $(this).addClass("active");
		else $(this).removeClass("active");
	});
}

function restCall(method, url, data, dataType, contentType, successCallback, completeCallback) {
	 $.ajax({ 
         type: method,       
         url: url,
         data: data,
         dataType: dataType,
         contentType: contentType,
         success: successCallback,
         complete: completeCallback,
         failure: failureCallback
     });
}

function failureCallback(error) {
	alert(error);
	console.log(error);
}

function loadUserDetails() {
	restCall("GET", "/bizTweets/getUserFollowingEntities?user=tushar686@gmail.com", "", "json", "application/json", success_loadUserUserFollowingEntities);
}

function success_loadUserUserFollowingEntities(response) {
	global.usersFollowEntities = response;
}

function loadEntities() {
	restCall("GET", "/bizTweets/getEntities", "", "json", "application/json", success_loadEntities);
}

function success_loadEntities(response) {
	global.entities = response;
}

function hideProgressbar() {
	$("#progressbar").hide();
}

function showProgressbar() {
	$("#progressbar").show();
}