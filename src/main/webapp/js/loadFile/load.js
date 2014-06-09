function handleFileSelect(evt) {
	var files = evt.target.files; // FileList object
	var f = files[0];
	var fileName = f.name;
	var fileType = f.type;
	alert('Name: ' + fileName + ' Type: ' + fileType);

	// TODO: si la imagen no es JPEG --> Error;

	if (f.type !== 'image/jpeg') {
		alert('Error!');
	} else {
		alert('OK!');
		// TODO: send user to homepage.html after checking everything OK
	}
}
document.getElementById('fileupload').addEventListener('change',
		handleFileSelect, false);

// $(document).ready(){
// alert("entra");
// }
// $('#fileupload').fileupload({
// //alert("hola");
// add : function(e, data) {
// $.getJSON('/photos', function(result) {
// data.formData = result; // e.g. {id: 123}
// data.submit();
// });
// }
// });
