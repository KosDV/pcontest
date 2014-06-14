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
	var msg = JSON.stringify(data.status.message);

	if (code == 200) {
		alert("Login OK");
		window.location.replace("homepage.html");
	} else {
		alert(msg);
	}
}
