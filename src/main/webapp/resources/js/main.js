var global = {usersFollowEntities: "", entities: ""}

$(function(){
	loadTweets();
	//loadUserDetails();
	//loadEntities();
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

function hideProgressbar() {
	$("#mainProgressbar").hide();
}

function showProgressbar() {
	$("#mainProgressbar").show();
}