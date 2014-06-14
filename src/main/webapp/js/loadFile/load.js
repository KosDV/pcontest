$(document).ready(function() {
	var nif = $.cookie('nif');
	var pass = $.cookie('password');

	alert(nif + ", " + pass);

	$('#nif_user').val($.cookie('nif'));
	$('#password_user').val($.cookie('password'));
});

// $('#upload').submit(function(e) {
// e.preventDefault();
// var URL = "https://localhost:4430/api/web/photos/upload";
// });

$('#fileupload').change(function(evt) {
	var files = evt.target.files;
	var f = files[0];
	var fileName = f.name;
	var fileType = f.type;

	if (f.type !== 'image/jpeg')
		$('#fileupload').val('');
});
