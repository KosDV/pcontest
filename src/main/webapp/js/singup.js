var usernameOK = false;
var surnameOK = false;
var passwordOK = false;
var emailOK = false;
var usernameExists = false;
var idOK = false,
var telOK = false;

ERROR_PAGE = "error.html";

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

$('#mail').blur(function() {
	var mail = $('#mail').val();
	if (mail === '') {
		mailOK = false;
	} else {
		mailOK = true;
	}
});

$('#tel').blur(function() {
	var mail = $('#tel').val();
	if (tel === '') {
		telOK = false;
	} else {
		telOK = true;
	}
});

$('#id').blur(function() {
	var id = $('#id').val();
	if (id === '') {
		idOK = false;
	} else {
		idOK = true;
	}
});

$('#form-signup').submit(
		function(e) {
			e.preventDefault();
			var name = $('#name').val();
			var surname = $('#surname').val();
			var id = $('#id').val();
			var tel = $('#tel').val();
			var mail = $('#mail').val();
			var password = $('#password').val();

			if (usernameOK && surnameOK && idOK && telOK && mailOK
					&& passwordOK) {
				var data = new Object();
				data.name = name;
				data.surname = surname;
				data.id = id;
				data.tel = tel;
				data.mail = mail;
				data.password = password;
				alert(JSON.stringify(data));
				registerUser(JSON.stringify(data), registerUserSuccess,
						registerUserError);
			} else {
				alert("No submit");
			}
		});

function registerUserSuccess(data, status, jqxhr){
 	bootbox.alert("Usuario creado correctamente. ", 
 			function(){
 				window.location.href = "index.html";
 			});
}