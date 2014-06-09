var API_BASE_URL = "https://localhost:4330/api";
//var API_BASE_URL = "http://localhost:8000/api";

// REGISTRAR USUARIO
function registerUser(user, callback, callbackError) {
	var url = API_BASE_URL + '/web/users/register';
	$.support.cors = true
	$.ajax({
		url : url,
		type : 'POST',
		data : user,
		dataType : 'json',
		headers : {
			"Content-Type" : "Application/json",
			"Accept" : "Application/json"
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

function loginUser(nif, password, callback, callbackError) {
	var url = API_BASE_URL + '/web/users/login?nif=' + nif + '&pass=' + password;

	$.support.cors = true
	$.ajax({
		url : url,
		type : 'GET',
		headers : {
			"Content-Type" : "Application/json",
			"Accept" : "Application/json"
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

function uploadPhoto(nif, passw, photo, callback, callbackError) {
	var url = API_BASE_URL +'/web/photos/upload';
	$.support.cors = true
	$.ajax({
		url : url,
		type : 'POST',
		data : photo,
		dataType : 'json',
		headers : {
			"Content-Type" : "Application/json",
			"Accept" : "Application/json",
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