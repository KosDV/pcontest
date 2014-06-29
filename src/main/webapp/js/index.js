$('#signin-form').submit(function(e) {
	e.preventDefault();

	var nif = $('#nif').val();
	var password = $('#password').val();

	$.cookie('nif', nif, {
		secure : true
	});
	$.cookie('password', password, {
		secure : true
	});
	console.log("index.js");
	loginUser(nif, password, callbackLogUser, callbackLogUserError);
});

function callbackLogUserError(jqXHR, options, error) {
	console.log("Connection to the server failed");
}

function callbackLogUser(data, status, jqxhr) {
	console.log("Connection OK");

	var code = JSON.stringify(data.status.code);
	var contestStatus = JSON.stringify(data.status.contest);
	var photo = JSON.stringify(data.status.photo);
	var msg = JSON.stringify(data.status.message);

	$.cookie('contestStatus', contestStatus, {
		secure : true
	});

	if (code == 200) {
		if (contestStatus == 601) {
			console.log("PRESENTATIONS_OPENED");
			if (!eval(photo)) {
				console.log("Please, upload a picture first");
				window.location.replace("load.html");
			} else {
				console.log("You have already uploaded a picture");
				window.location.replace("information.html");
			}
		} else if (contestStatus == 602) {
			console.log("VOTES_OPENED");
			if (!eval(photo)) {
				console
						.log("You cannot upload a picture. Presentations period is closed.");
				contestStatus = 900;
				$.cookie('contestStatus', contestStatus, {
					secure : true
				});
				window.location.replace("information.html");
			} else {
				window.location.replace("homepage.html");
			}
		} else if (contestStatus == 603) {
			$.cookie('contestStatus', contestStatus, {
				secure : true
			});

			window.location.replace("information.html");
		} else {
			console.log("You have already uploaded a picture. Time to vote!");
			window.location.replace("homepage.html");
		}
	} else {
		alert("Log in msg: " + msg);
	}
}
