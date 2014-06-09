$('#signin-form').submit(function(e) {
	e.preventDefault();

	var nif = $('#nif').val();
	var password = $('#password').val();

	loginUser(nif, password, loginUserSuccess, loginUserError);
});
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

function callbackError(jqXHR, options, error) {
	alert("Connection to the server failed");
}

function callback(data, status, jqxhr) {
	alert("Connection OK");
	alert("Response: " + JSON.stringify(data) + " Status: " + status);

	var code = JSON.stringify(data.status.code);
	var msg = JSON.stringify(data.status.message);

	if (code == 200) {
		alert("Login OK");
		window.location.replace("homepage.html");
	} else {
		alert(msg);
	}
}
