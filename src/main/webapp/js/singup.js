/** variables definition * */
var usernameOK = false;
var surnameOK = false;
var passwordOK = false;
var emailOK = false;
var usernameExists = false;
var idOK = false;
var birthOK = false;

function callbackRegUser(data, status, jqxhr) {
	console.log("Entra al callbackRegUser");
	var code = JSON.stringify(data.status.code);
	var photo = JSON.stringify(data.status.photo);
	var msg = JSON.stringify(data.status.message);
	var contestStatus = JSON.stringify(data.status.contest);

	$.cookie('contestStatus', contestStatus);

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
				$.cookie('contestStatus', contestStatus);
				window.location.replace("information.html");
			} else {
				console
						.log("You have already uploaded a picture. Time to vote!");
				window.location.replace("homepage.html");
			}
		}
	} else {
		if (data.status.code == 608) {
			alert(msg);
		}
		
	}
}

function callbackRegUserError(jqxhr, options, error) {
	console.log("Connection to the server failed");
}

/** check form data * */
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
		console.log("Dades del signup: " + JSON.stringify({
			"user" : data
		}));
		registerUser(JSON.stringify({
			"user" : data
		}), callbackRegUser, callbackRegUserError);
	} else {
		console.log("Error al introducir los datos!");
	}
});
