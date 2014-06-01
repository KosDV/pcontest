//var API_BASE_URL = "http://localhost:8000/api/web/users/login;
// http://localhost:8000/api/web/users/login?nif=XXXX&pass=YYYY
$('#signup-form').submit(function(e) {
	e.preventDefault();

	$.cookie('nif', $('#nif').val());
	$.cookie('password', $('#password').val());

	var nif = $('#nif').val();
	var password = $('#password').val();

	alert(nif);
	alert(password);
	loginUser(nif, password, loginUserSuccess, loginUserError);
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

}

// LOG IN
function loginUser(nif, password, callback, callbackError) {
	var url = "http://localhost:8000/api/web/users/login" + '?nif=' + nif
			+ '&pass=' + password;

	$.ajax({
		url : url,
		type : 'GET',
		username : nif,
		password : password,
		headers : {

			"Accept" : "application/json"
		},
		crossDomain : true,

		success : function(data, status, jqxhr) {
			callback(data, status, jqxhr);
		},
		error : function(jqXHR, options, error) {
			callbackError(jqXHR, options, error);
		}
	});
}

