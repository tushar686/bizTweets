var userDetails = {details: ""}

$(function(){
	loadUserDetails();
	});

function setHighlitedMenu(i) {
	$('#menu li').each(function() {
		if($(this).index() == i) $(this).addClass("current");
		else $(this).removeClass("current");
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
	restCall("GET", "/bizTweets/getUserDetails?user=tushar686@gmail.com", "", "json", "application/json", success_loadUserDetails);
}

function success_loadUserDetails(response) {
	userDetails.details = response;
}