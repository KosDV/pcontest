$(document).ready(function() {
	alert("Entra al load.js");
	var nif = $.cookie('nif');
	var pass = $.cookie('password');

	alert(nif + ", " + pass);

	$('#nif_user').val(nif);
	$('#password_user').val(pass);
});

$('#fileupload').change(function(evt) {
	var files = evt.target.files;
	var f = files[0];
	var fileName = f.name;
	var fileType = f.type;

	if (f.type !== 'image/jpeg')
		$('#fileupload').val('');
});

$('#upload').submit(function(e) {
	e.preventDefault();

	var formObj = $(this);
	var formUrl = formObj.attr("action");
	var formData = new FormData(this);

	$.support.cors = true
	$.ajax({
		url : formUrl,
		type : 'POST',
		data : formData, // Data to be sent in the body of the request for
							// POST requests.
		dataType : "json", // Specifies the type of data expected in the
							// response.
		contentType : false, // Specifies the HTTP Content-Type header for
								// the request.
		cache : false,
		processData : false,
		crossDomain : true,
		success : function(data, status, jqxhr) {
			callbackLoad(data, status, jqxhr);
		},
		error : function(jqXHR, options, error) {
			callbackLoadError(jqXHR, options, error);
		}
	});
});

function callbackLoadError(jqXHR, options, error) {
	alert("Connection to the server failed");
}

function callbackLoad(data, status, jqxhr) {
	alert("Connection OK");

	var code = JSON.stringify(data.status.code);
	var msg = JSON.stringify(data.status.message);
	var photo = JSON.stringify(data.status.photo);

	alert("WOLOLO: " + code + ", " + msg + ", " + photo);

	window.location.replace("information.html");
}

// LOG OUT
$('#log_out').click(function(e) {
	e.preventDefault();
	$.cookie('nif', 'undefined', -1);
	$.cookie('password', 'undefined', -1);
	var nif = $.cookie('nif');
	var pass = $.cookie('password');

	alert(nif + ", " + pass);
	window.location.href = "index.html";
});
