// Global
var JSONobject;

$(document).ready(function() {
	var nif = $.cookie('nif');
	var password = $.cookie('password');

	// //BORRAR
	/*
	 * var data =
	 * '{"PhotosToVote":{"n":115836834038114737932769981489975356861332142458482215125989887597840484303316737271218860905588540434320434095870339412636194361624256322864091405695114767363377373181263541081679725401250016928985480391559112062884425415378138116797489070155815511421312400461088683002718600636837348199277241271515991121601,"g":3740048650284190113788647293118948560426033476120935293115169295858982092948326759283343427665135228212804220187845730377920108083114721065320182315165644681274891715233002977137753511977778389894328963740875936281435373434200906915892843734898827677390828229097427673637040699819700733516060268577266813034275763632767256090075766623309416112809814759024467011558008733913740082091473749002576402717045188784255389304424527404226878837169840430402741380010167844963326800556213848209311250608520617840325218152333192505032497050375947069646210606747062387180057916768644466234911160233021934490077130211609585156490,"listPhotosToVote":[{"title":"H","description":"Hdasdasdasdas","id":12,"date":null,"dimensions":null,"brand":null,"model":null,"flash":null,"ISO":null,"focalLength":null,"fNum":null,"exposureTime":null,"url":"/assets/sampleImg/1.jpg","iso":null,"fileName":"photo.jpg","latLng":null},{"title":"J","description":"Jdasdasdasdas","id":14,"date":null,"dimensions":null,"brand":null,"model":null,"flash":null,"ISO":null,"focalLength":null,"fNum":null,"exposureTime":null,"url":"/assets/sampleImg/2.jpg","iso":null,"fileName":"photo.jpg","latLng":null}],"individual-vote-length":2,"total-vote-length":26}}';
	 * 
	 * JSONobject = JSON.parse(data); loadPictures();
	 */
	// ///////// FIN BORRAR
	getPictures(nif, password, callbackGetPhotos, callbackErrorGetPhotos); // Uncomment

});

function callbackErrorGetPhotos(jqXHR, options, error) {
	alert("Connection to the server failed");
}

function callbackGetPhotos(data, status, jqxhr) {
	// var contestStatus = JSON.stringify(data.status.code);
	//
	// $.cookie('contestStatus', contestStatus);
	// if (contestStatus == 609) {
	// window.location.replace("information.html");
	// }
	// JSONobject = JSON.parse(data);

	// var result = JSON.stringify(data.status.code);

	json = data;
	loadPictures(json);
}

var json;

function loadPictures(json) {
	var title;
	var description;
	var url;
	var latLng;
	var id;

	var i = 0;
	var tmp = json.PhotosToVote.listPhotosToVote;

	$('#imagesList').html('');
	while (i < tmp.length) {
		$('#imagesList')
				.append(
						'<div id="photo'
								+ i
								+ '" class="col-md-6 portfolio-item"><div onClick="loadInfo('
								+ i
								+ ')"><img id="url'
								+ i
								+ '" class="img-responsive" src="'
								+ tmp[i].url
								+ '" alt="'
								+ tmp[i].title
								+ '"/><p id="title'
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

// LOG OUT
$('#log_out').click(function(e) {
	e.preventDefault();
	$.cookie('nif', 'undefined', -1);
	$.cookie('password', 'undefined', -1);
	var nif = $.cookie('nif');
	var pass = $.cookie('password');

	window.location.href = "index.html";
});