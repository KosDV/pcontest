$('#signin-form').submit(function(e) {
	e.preventDefault();

	var nif = $('#nif').val();
	var password = $('#password').val();

	$.cookie('nif', nif,{secure:true});
	$.cookie('password', password,{secure:true});

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

	alert(contestStatus);
	$.cookie('contestStatus', contestStatus,{secure:true});

	if (code == 200) {
		if (contestStatus == 601) {
			alert("PRESENTATIONS_OPENED");
			if (!eval(photo)) {
				alert("Please, upload a picture first");
				window.location.replace("load.html");
			} else {
				alert("You have already uploaded a picture");
				window.location.replace("information.html");
			}
		} else if (contestStatus == 602) {
			alert("VOTES_OPENED");
			if (!eval(photo)) {
				alert("You cannot upload a picture. Presentations period is closed.");
				contestStatus = 900;
				$.cookie('contestStatus', contestStatus,{secure:true});
				window.location.replace("information.html");
			} else {
				window.location.replace("homepage.html");
			}
		} else if (contestStatus == 603) {
			alert()
			$.cookie('contestStatus', contestStatus,{secure:true});
			window.location.replace("information.html");
		} else {
			alert("You have already uploaded a picture. Time to vote!");
			window.location.replace("homepage.html");
		}
	} else {
		alert("Log in msg: " + msg);
	}
}
