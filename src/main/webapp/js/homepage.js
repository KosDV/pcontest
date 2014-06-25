$(document).ready(function() {
	var nif = $.cookie('nif');
	var password = $.cookie('password');

	console.log(nif + ", " + password);

	getPictures(nif, password, callbackGetPhotos, callbackErrorGetPhotos);

});

function callbackErrorGetPhotos(jqXHR, options, error) {
	alert("Connection to the server failed");
}

function callbackGetPhotos(data, status, jqxhr) {

	console.log($.cookie('password'));

	if (!data.status) {
		json = data;
		loadPictures(json);
	} else {
		console.log("Error");
		$.cookie('contestStatus', data.status.code, {
			secure : true
		});
		window.location.replace("information.html");
	}
}

var json;
var bol;
var votes;
var checkedIds = [];
var individualVoteLength;
var totalVoteLength;

function loadPictures(json) {
	var i = 0;

	var tmp = json.PhotosToVote.listPhotosToVote;

	$('#imagesList').html('');
	while (i < tmp.length) {
		$('#imagesList').append(
				'<div id="photo' + tmp[i].id
						+ '" class="col-md-6 portfolio-item"><a href="'
						+ tmp[i].url + '"title="' + tmp[i].title
						+ '"data-gallery><img id="url' + i
						+ '" class="img-responsive" src="' + tmp[i].url
						+ '" alt="' + tmp[i].title + '"/></a><p id="title' + i
						+ '" align="center"><input type="checkbox" id="'
						+ tmp[i].id + '" name="input"> <b>' + tmp[i].title
						+ '</b></p></div>');
		i++;
	}
	votes = json.PhotosToVote.votes;
	individualVoteLength = json.PhotosToVote.individualVoteLength;
	totalVoteLength = json.PhotosToVote.totalVoteLength;
	console.log(checkedIds + ", " + individualVoteLength + ", "
			+ totalVoteLength);

	$('#user_info').html('');
	$('#user_info')
			.append(
					'<div><h4>You must select '
							+ votes
							+ ' pictures to vote.</h4><button type="button" onClick="sendVote('
							+ individualVoteLength
							+ ', '
							+ totalVoteLength
							+ ')" display="inline" class="votebtn btn btn-default">Vote</button></div>');
	$("input[type=checkbox][name=input]")
			.click(
					function() {
						bol = $("input[type=checkbox][name=input]:checked").length >= votes;
						$("input[type=checkbox][name=input]").not(":checked")
								.attr("disabled", bol);

						if ($("input[type=checkbox][name=input]:checked").length == votes) {
							checkedIds = $(":checkbox:checked").map(function() {
								return this.id;
							}).get();
							console.log(checkedIds);
						}
					});
}

function sendVote(totalVoteLength, individualVoteLength) {
	console.log("Votes: " + votes + " Checked IDs: " + checkedIds);
	if (checkedIds.length == votes) {
		encVote(checkedIds, totalVoteLength, individualVoteLength);
	} else {
		alert("Please, vote " + votes + " pictures.");
	}
}

function encVote(checkedIds, totalVoteLength, individualVoteLength) {
	console.log("checkedIds: " + checkedIds);
	console.log("totalVoteLength: " + totalVoteLength);
	console.log("individualVoteLength: " + individualVoteLength);
}

$('#log_out').click(function(e) {
	e.preventDefault();
	$.cookie('nif', 'undefined', -1);
	$.cookie('password', 'undefined', -1);
	var nif = $.cookie('nif');
	var pass = $.cookie('password');

	window.location.href = "index.html";
});

// function loadInfo(i) {
// var title;
// var description;
// var url;
// var latLng;
//
// var tmp = json.PhotosToVote.listPhotosToVote;
// $(info_title).html('<h5>' + tmp[i].title + '</h5>');
// $(info_desc).html('<h5>' + tmp[i].description + '</h5>');
//
// $(info_image).html(
// '<img id="url" class="img-responsive" src="' + tmp[i].url + '">');
// $(info_map).html('');
// }
