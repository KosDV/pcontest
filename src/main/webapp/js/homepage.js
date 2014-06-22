$(document).ready(function() {
	var nif = $.cookie('nif');
	var password = $.cookie('password');

	alert("Homepage: " + nif + " - " + password);
	getPictures(nif, password, callback, callbackError);
});

function callbackErrorGetPhotos(jqXHR, options, error) {
	alert("Connection to the server failed");
}

function callbackGetPhotos(data, status, jqxhr) {
	alert("Connection OK");
	alert("Està al homepage js");

	// var code = JSON.stringify(data.status.code);
	// var contestStatus = JSON.stringify(data.status.contest);
	// var photo = JSON.stringify(data.status.photo);
	// var msg = JSON.stringify(data.status.message);

	loadPictures();
}

function loadPictures() {
	// load image array
	var images = {
		'image1' : 'assets/sampleImg/3.jpg',
		'image2' : 'assets/sampleImg/4.jpg',
		'image3' : 'assets/sampleImg/2.jpg'
	};
	var title = 'hola 
		';

	$
			.each(
					images,
					function() {
						$('#imagesList')
								.append(
										'<div class="col-md-4 portfolio-item"><a href="index.html"><img class="img-responsive" src="'
												+ this
												+ '"></a><h5><p style="text-align:center"><b> '
												+ title
												+ '</b><input type="checkbox" name="input"></p></h5></div>');
					});
}

// LOG OUT
$('#log_out').click(function(e) {
	e.preventDefault();
	$.cookie('nif', 'undefined', -1);
	$.cookie('password', 'undefined', -1);
	var nif = $.cookie('nif');
	var pass = $.cookie('password');

	window.location.href = "index.html";
});

function getImages() {
	
}