$('#signin-form').submit(function(e) {
	e.preventDefault();

	var nif = $('#nif').val();
	var password = $('#password').val();

	$.cookie('nif', nif);
	$.cookie('password', password);

	loginUser(nif, password, callbackLogUser, callbackLogUserError);
});

function callbackLogUserError(jqXHR, options, error) {
	alert("Connection to the server failed");
}

function callbackLogUser(data, status, jqxhr) {
	alert("Connection OK");

	var code = JSON.stringify(data.status.code);
	var contestStatus = JSON.stringify(data.status.contest);
	var photo = JSON.stringify(data.status.photo);
	var msg = JSON.stringify(data.status.message);

	if (code == 200) {
		if (contestStatus == 601) {
			alert("PRESENTATIONS_OPENED");
			if (!eval(photo)) {
				alert("Please, upload a picture first");
				window.location.replace("load.html");
			} else {
				alert(contestStatus);
				alert("You have already uploaded a picture");
				displayInformation(contestStatus);
				window.location.replace("information.html");
				
			}
		}
	} else {
		alert("Log in msg: " + msg);
	}
}
