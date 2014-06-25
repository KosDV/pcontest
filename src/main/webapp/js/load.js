/** variables definition * */
var nif;
var pass;
var contestStatus;

$(document).ready(function() {
	nif = $.cookie('nif');
	pass = $.cookie('password');
	contestStatus = $.cookie('contestStatus');

	console.log(nif + ", " + pass + ", " + contestStatus);
	
	if (contestStatus == 602) {
		console.log("VOTES_OPENED");
		window.location.replace("information.html");
	} else if (contestStatus == 603) {
		console.log("CONTEST_CLOSED");
		window.location.replace("information.html");
	} else {
		console.log("CONTEST_OPENED");
	}

	$('#nif_user').val(nif);
	$('#password_user').val(pass);
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
		data : formData,
		dataType : "json",
		contentType : false,
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
	console.log("Connection to the server failed");
}

function callbackLoad(data, status, jqxhr) {
	console.log("Connection OK");

	var code = JSON.stringify(data.status.code);
	var msg = JSON.stringify(data.status.message);
	var photo = JSON.stringify(data.status.photo);

	console.log("WOLOLO: " + code + ", " + msg + ", " + photo);

	window.location.replace("information.html");
}

$('#log_out').click(function(e) {
	e.preventDefault();
	$.cookie('nif', 'undefined', -1);
	$.cookie('password', 'undefined', -1);
	var nif = $.cookie('nif');
	var pass = $.cookie('password');

	console.log(nif + ", " + pass);
	window.location.href = "index.html";
});

$('#fileupload').change(function(evt) {
	var files = evt.target.files;
	var f = files[0];
	var fileName = f.name;
	var fileType = f.type;

	if (f.type !== 'image/jpeg')
		$('#fileupload').val('');
});
