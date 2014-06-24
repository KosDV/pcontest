$(document)
		.ready(
				function() {
					var nif = $.cookie('nif');
					var pass = $.cookie('password');
					var contestStatus = $.cookie('contestStatus');

					alert(nif + ", " + pass + ", " + contestStatus);

					if (contestStatus == 601) {
						$('#user_information')
								.after(
										'Presentations are opened. You have already uploaded a picture. You will be able to vote as soon as the votation period starts. <br>');
						$('#info_btn').hide();
					} else if (contestStatus == 602) {
						$('#user_information').after('Time to vote :-D! <br>');
						$('#info_btn').show();
						$('#info_btn').click(function() {
							window.location = "homepage.html";
						});
					} else if (contestStatus == 603) {
						$('#user_information')
								.after(
										'pContest is close at the moment. If you would like to check out the previous winners, please feel free to visit the following page..<br>');
						$('#info_btn').show();
						$('#info_btn').click(function() {
							window.location = "results.html";
						});
					} else if (contestStatus == 900) {
						$('#user_information')
								.after(
										'You cannot upload a picture. Presentations period is closed :(');
						$('#info_btn').hide();
					} else if (contestStatus == 609) {
						$('#user_information')
								.after(
										'Sorry! There are not enough participants. Pcontest is currently closed :(');
						$('#info_btn').hide();
					} else {
						$('#user_information').after('');
						$('#info_btn').hide();
					}

				});

// LOG OUT
$('#log_out').click(function(e) {
	e.preventDefault();
	$.cookie('nif', 'undefined', -1);
	$.cookie('password', 'undefined', -1);
	var nif = $.cookie('nif');
	var pass = $.cookie('password');

	alert(nif + ", " + pass);
	window.location.href = "index.html";
});