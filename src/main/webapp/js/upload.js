$('#load-image-form').submit(function(e) {
	e.preventDefault();

	uploadPhoto(JSON.stringify({
		"photo" : data
	}), Success, Error);
});

function Success(data, status, jqxhr) {
	alert("Connection OK");
	alert("Response: " + JSON.stringify(data) + "Status: " + status);

	var code = JSON.stringify(data.status.code);
	var msg = JSON.stringify(data.status.message);

	if (code == 200) {
		alert("Picture sent!");
		window.location.replace("homepage.html#loadPicture");
	} else {
		alert(msg);
	}
}

function Error(jqxhr, options, error) {
	alert("Connection KO");
	alert("Response: " + JSON.stringify(error));
}