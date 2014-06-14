$('#fileupload').change(function(evt){
	var files = evt.target.files; // FileList object
	var f = files[0];
	var fileName = f.name;
	var fileType = f.type;
	alert('Name: ' + fileName + ' Type: ' + fileType);

	if (f.type !== 'image/jpeg') {
		alert('Error!');
		$('#fileupload').val('');
	} else {
		alert('OK!');
		// TODO: send user to homepage.html after checking everything OK
	}
});