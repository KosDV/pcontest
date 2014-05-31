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

$('#signup-form').submit(
		function(e) {
			e.preventDefault();
			var name = $('#name').val();
			var surname = $('#surname').val();
			var nif = $('#nif').val();
			var birth = $('#birth').val();
			var email = $('#email').val();
			var password = $('#password').val();

			if (usernameOK && surnameOK && idOK && birthOK && emailOK
					&& passwordOK) {
				var data = new Object();
				data.name = name;
				data.surname = surname;
				data.nif = nif;
				data.birth = birth;
				data.email = email;
				data.password = password;
				alert(JSON.stringify(data));
				registerUser(JSON.stringify(data), registerUserSuccess,
						registerUserError);
			} else {
				alert("No submit");
			}
		});

function registerUserSuccess(data, status, jqxhr) {
	bootbox.alert("Usuario creado correctamente. ", function() {
		window.location.href = "index.html";
	});
}

function registerUserError(jqxhr, options, error) {
	if (jqxhr.status == 409) {
		usernameExists = true;
		$('#form-signup').addClass('error');
		bootbox.alert("El usuario ya existe");
	} else {
		var response = $.parseJSON(jqxhr.responseText);
		bootbox.alert("Error." + response.errorMessage);
	}
}