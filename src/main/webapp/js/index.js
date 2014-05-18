$('#signin-form').submit(function(e) {
	e.preventDefault();

	$.cookie('email', $('#email').val());
	$.cookie('password', $('#password').val());

	var email = $('#email').val();
	var password = $('#password').val();

	var opcion = 0;
	loginUser(email, password, opcion, loginUserSuccess, loginUserError);
});

function loginUserError(jqXHR, options, error) {
	if (jqXHR.status == 404) {
		bootbox.alert("You are not registered.");
	}

	if (jqXHR.status == 409) {
		bootbox.alert("User or password incorrect.");
	}

	if (jqXHR.status == 403) {
		bootbox.alert("Permission denied.");
	}
}

function loginUserSuccess(data, status, jqxhr) {
	if (jqxhr.status == 200) {
		window.location.href = "homepage.html";
	}

	if (jqxhr.status == 202) { // upload a picture
		window.location.href = "homepage.html";
	}

	if (jqxhr.status == 201) {// se loguea como administrador
		window.location.href = "gestionArtistas.html";
	}

}
