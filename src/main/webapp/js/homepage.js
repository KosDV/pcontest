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
var tmp;

function loadPictures(json) {
	var i = 0;

	tmp = json.PhotosToVote.listPhotosToVote;

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
						+ '</b>    <a onClick="loadInfo(' + i
						+ ')">+ info</a></p></div>');
		i++;
	}
	votes = json.PhotosToVote.votes;
	individualVoteLength = json.PhotosToVote.individualVoteLength;
	totalVoteLength = json.PhotosToVote.totalVoteLength;
	console.log(checkedIds + ", " + individualVoteLength + ", "
			+ totalVoteLength);

	$('#user_info').html('');
	$('#exif_table').html('');
	$('#user_info')
			.append(
					'<div><h4>You must select '
							+ votes
							+ ' pictures to vote</h4><button type="button" onClick="sendVote('
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

function sendVote(individualVoteLength, totalVoteLength) {
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

	var iter = 0;
	console.log(iter);
	var voteStr = '';
	for (iter = 0; iter < totalVoteLength; iter++) {
		voteStr += 0;
	}
	console.log("Original: " + voteStr);
	var pos = 0;
	var len = checkedIds.length;
	console.log("lengthlen: " + len);

	var iter = 0;
	var strL = '';
	var strR = '';
	var strFirst = '';
	for (iter = 0; iter < len; iter++) {
		pos = (checkedIds[iter] * individualVoteLength) - 1;
		console.log("pos: " + pos);

		strL = voteStr.slice(0, pos);
		console.log("strL: " + strL);

		strR = voteStr.slice(pos + 1, voteStr.length + 1);
		console.log("strR: " + strR);

		voteStr = strL + 1 + strR;
	}
	console.log("voteStr: " + voteStr);
	var nif = $.cookie('nif');
	var password = $.cookie('password');

	var vote = JSON.stringify({
		"vote" : {
			"encrypted-vote" : voteStr
		}
	});
	sendEncVote(nif, password, vote, callbackVote, callbackVoteError);
}

function loadInfo(i) {

	$('#exif_table').html('');
	$('#title_exif_data').html('<h3>EXIF DATA INFORMATION</h3>');
	$('#exif_table')
			.append(
					'<thead><tr><th>Name</th><th>Title</th><th>Description</th><th>Date</th><th>GPS Latitude</th><th>GPS Longitude</th><th>ISO</th><th>Aperture</th></tr></thead>');
	while (i < tmp.length) {
		$('#exif_table').append(
				'<tr><td>' + tmp[i].name + '</td><td>' + tmp[i].title
						+ '</td><td>' + tmp[i].description + '</td><td>'
						+ tmp[i].date + '</td><td>' + tmp[i].gpsLatitude
						+ '</td><td>' + tmp[i].gpsLongitude + '</td><td>'
						+ tmp[i].ISO + '</td><td>' + tmp[i].aperture + '</td>'
						+ tmp[i].exposureTime + '<td></td></tr>');
		i++;
	}
	$('html, body').animate({
		scrollTop : ($('#title_exif_data').offset().top)
	}, 500);
}

function callbackVote(data, status, jqxhr) {
	var contestStatus = data.status.contest;
	var voted = data.status.voted;

	if (contestStatus == 602) {
		console.log("VOTES_OPENED");
		if (voted) {
			$.cookie('contestStatus', 901);
			console.log("You have already voted. Wait for the result");
			window.location.replace("information.html");
		}
	}
}

function callbackVoteError(jqXHR, options, error) {
	console.log("Connection to the server failed");
}

// LOG OUT
$('#log_out').click(function(e) {
	e.preventDefault();
	$.cookie('nif', 'undefined', -1);
	$.cookie('password', 'undefined', -1);
	$.cookie('contestStatus', 'undefined', -1);
	var nif = $.cookie('nif');
	var pass = $.cookie('password');
	var contestStatus = $.cookie('contestStatus');

	console.log(nif + ", " + pass + ", " + contestStatus);
	window.location.href = "index.html";
});
