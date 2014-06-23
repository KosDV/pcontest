var API_BASE_URL = "https://localhost:4430/api";

function registerUser(user, callbackRegUser, callbackRegUserError) {
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
			callbackRegUser(data, status, jqxhr);
		},
		error : function(jqXHR, options, error) {
			callbackRegUserError(jqXHR, options, error);
		}
	});
}

function loginUser(nif, password, callbackLogUser, callbackLogUserError) {
	var url = API_BASE_URL + '/web/users/login?nif=' + nif + '&pass='
			+ password;

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
			callbackLogUser(data, status, jqxhr);
		},
		error : function(jqXHR, options, error) {
			callbackLogUserError(jqXHR, options, error);
		}
	});
}

// function loadPicture(photo, callbackLoad, callbackLoadError) {
// var url = API_BASE_URL + '/web/photos/upload';
//	
// $.support.cors = true
// $.ajax({
// url : url,
// type : 'POST',
// data : photo,
// dataType : 'json',
// headers : {
// "Content-Type" : "Application/json",
// "Accept" : "Application/json"
// },
// crossDomain : true,
// success : function(data, status, jqxhr) {
// callbackLoad(data, status, jqxhr);
// },
// error : function(jqXHR, options, error) {
// callbackLoadError(jqXHR, options, error);
// }
// });
//	
// }

function getPictures(nif, password, callbackGetPhotos, callbackErrorGetPhotos) {
	var url = API_BASE_URL + '/web/photos/vote?nif=' + nif + '&pass='
			+ password;

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
			callbackGetPhotos(data, status, jqxhr);
		},
		error : function(jqXHR, options, error) {
			callbackErrorGetPhotos(jqXHR, options, error);
		}
	});

}
