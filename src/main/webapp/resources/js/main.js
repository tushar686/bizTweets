function setHighlitedMenu(i) {
	$('#menu li').each(function() {
		if($(this).index() == i) $(this).addClass("current");
		else $(this).removeClass("current");
	});
}

function restCall(method, url, data, dataType, contentType, successCallback) {
	 $.ajax({ 
         type: method,       
         url: url,
         data: data,
         dataType: dataType,
         contentType: contentType,
         success: successCallback,
         failure: failureCallback
     });
}

function failureCallback(error) {
	alert(error);
	console.log(error);
}

