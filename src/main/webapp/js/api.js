var API_BASE_URL = "http://localhost:8000/api/web/users/register";
// var API_BASE_URL = "https://localhost:80443/api/web/users/register";

// REGISTRAR USUARIO
function registerUser(user, callback, callbackError) {
	var url = API_BASE_URL;// + 'web' + 'users';
	$.support.cors = true
	$.ajax({
		url : url,
		type : 'POST',
		data : user,
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