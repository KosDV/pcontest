var json;

$(document).ready(function() {
	var nif = $.cookie('nif');
	var password = $.cookie('password');

	getPictures(nif, password, callbackGetPhotos, callbackErrorGetPhotos);

});

function callbackErrorGetPhotos(jqXHR, options, error) {
	alert("Connection to the server failed");
}

function callbackGetPhotos(data, status, jqxhr) {
	json = data;
	loadPictures(json);
}

function loadPictures(json) {
	var i = 0;
	var tmp = json.PhotosToVote.listPhotosToVote;

	$('#imagesList').html('');
	while (i < tmp.length) {
		$('#imagesList')
				.append(
						'<div id="photo'
								+ tmp[i].id
								+ '" class="col-md-6 portfolio-item"><a href="'
								+ tmp[i].url
								+ '" title="'
								+ tmp[i].title
								+ '"data-gallery><img id="url'
								+ i
								+ '" class="img-responsive" src="'
								+ tmp[i].url
								+ '" alt="'
								+ tmp[i].title
								+ '"/></a><p id="title'
								+ i
								+ '" align="center"><input type="checkbox" id="checkbox_id '
								+ i + '" name="input"> <b>' + tmp[i].title
								+ '</b></p></div>');
		i++;
	}
}

function loadInfo(i) {
	var title;
	var description;
	var url;
	var latLng;

	var tmp = json.PhotosToVote.listPhotosToVote;
	$(info_title).html('<h5>' + tmp[i].title + '</h5>');
	$(info_desc).html('<h5>' + tmp[i].description + '</h5>');

	$(info_image).html(
			'<img id="url" class="img-responsive" src="' + tmp[i].url + '">');
	$(info_map).html('');
}

$('#log_out').click(function(e) {
	e.preventDefault();
	$.cookie('nif', 'undefined', -1);
	$.cookie('password', 'undefined', -1);
	var nif = $.cookie('nif');
	var pass = $.cookie('password');

	window.location.href = "index.html";
});