var usernameOK = false;
var surnameOK = false;
var passwordOK = false;
var emailOK = false;
var usernameExists = false;
var idOK = false;
var birthOK = false;

$('#name').blur(function() {
	var username = $('#name').val();
	if (username === '') {
		usernameOK = false;
	} else {
		usernameOK = true;
		usernameExists = false;
	}
});

$('#surname').blur(function() {
	var username = $('#surname').val();
	if (surname === '') {
		surnameOK = false;
	} else {
		surnameOK = true;
	}
});

$('#password').blur(function() {
	var password = $('#password').val();
	if (password === '') {
		passwordOK = false;
	} else {
		passwordOK = true;
	}
});

$('#email').blur(function() {
	var email = $('#email').val();
	if (email === '') {
		emailOK = false;
	} else {
		emailOK = true;
	}
});

$('#birth').blur(function() {
	var email = $('#birth').val();
	if (birth === '') {
		birthOK = false;
	} else {
		birthOK = true;
	}
});

$('#nif').blur(function() {
	var nif = $('#nif').val();
	if (nif === '') {
		idOK = false;
	} else {
		idOK = true;
	}
});

$('#signup-form').submit(function(e) {
	e.preventDefault();
	var name = $('#name').val();
	var surname = $('#surname').val();
	var nif = $('#nif').val();
	var birth = $('#birth').val();
	var email = $('#email').val();
	var password = $('#password').val();

	$.cookie('nif', $('#nif').val(),{secure:true});
	$.cookie('password', $('#password').val(),{secure:true});

	if (usernameOK && surnameOK && idOK && birthOK && emailOK && passwordOK) {
		var data = new Object();
		data.name = name;
		data.surname = surname;
		data.nif = nif;
		data.birth = birth;
		data.email = email;
		data.password = password;
		alert("Dades del signup: " + JSON.stringify({
			"user" : data
		}));
		registerUser(JSON.stringify({
			"user" : data
		}), callbackRegUser, callbackRegUserError);
	} else {
		alert("Error al introducir los datos!");
	}
});

function callbackRegUser(data, status, jqxhr) {
	alert("Entra al callbackRegUser");
	var code = JSON.stringify(data.status.code);
	var photo = JSON.stringify(data.status.photo);
	var msg = JSON.stringify(data.status.message);
	var contestStatus = JSON.stringify(data.status.contest);

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
				alert("You have already uploaded a picture. Time to vote!");
				window.location.replace("homepage.html");
			}
		}
	} else {
		alert("Log in msg: " + msg);
	}
}

function callbackRegUserError(jqxhr, options, error) {
	alert("Entra al callbackRegUserError");
	alert("Connection to the server failed");
}