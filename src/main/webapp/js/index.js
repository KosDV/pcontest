$('#signin-form').submit(function(e) {
	e.preventDefault();

	var nif = $('#nif').val();
	var password = $('#password').val();

	loginUser(nif, password, callback, callbackError);
});

function callbackError(jqXHR, options, error) {
	alert("Connection to the server failed");
}

function callback(data, status, jqxhr) {
	alert("Connection OK");
	alert("Response: " + JSON.stringify(data) + " Status: " + status);

	var code = JSON.stringify(data.status.code);
	var contestStatus = JSON.stringify(data.status.contest);
	var photo = JSON.stringify(data.status.photo)
	var msg = JSON.stringify(data.status.message);

	if (code == 200) {
		if (photo) {
			alert("You have already uploaded a picture");
			window.location.replace("homepage.html");
		} else {
			alert("Please, upload a picture first");
			window.location.replace("load.html");
		}
		
	} else {
		alert(msg);
	}
}
