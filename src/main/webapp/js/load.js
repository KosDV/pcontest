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

	$.ajax({
		url : formUrl,
		type : 'POST',
		data : formData,
		mimeType : "multipart/form-data",
		contentType : false,
		cache : false,
		processData : false,
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
	alert("Està al load js");
	window.location.replace("main.html");
}
