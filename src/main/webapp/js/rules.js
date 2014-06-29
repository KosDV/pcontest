$(document).ready(function() {
	var nif = $.cookie('nif');
	var pass = $.cookie('password');
	var contestStatus = $.cookie('constestStatus');

	console.log(nif + ", " + pass);
});

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