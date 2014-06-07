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

	if (usernameOK && surnameOK && idOK && birthOK && emailOK && passwordOK) {
		var data = new Object();
		data.name = name;
		data.surname = surname;
		data.nif = nif;
		data.birth = birth;
		data.email = email;
		data.password = password;
		alert(JSON.stringify({
			"user" : data
		}));
		registerUser(JSON.stringify({
			"user" : data
		}), registerUserSuccess, registerUserError);
	} else {
		alert("Error al introducir los datos!");
	}
});

function registerUserSuccess(data, status, jqxhr) {
	alert("Connection OK");
	alert("Response: " + JSON.stringify(data) + " Status: " + status);

	var code = JSON.stringify(data.code.code);
	var msg = JSON.stringify(data.code.message);

	if (code == 200) {
		alert("User registered!");
		window.location.replace("homepage.html#loadPicture");
	} else {
		alert(msg);
	}

}

function registerUserError(jqxhr, options, error) {
	if (jqxhr.status == 409) {
		usernameExists = true;
		$('#signup-form').addClass('error');
		alert("El usuario ya existe");
	} else {
		var response = $.parseJSON(jqxhr.responseText);
		alert("Error." + response.errorMessage);
	}
}