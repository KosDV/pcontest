$('#signin-form').submit(function(e) {
	e.preventDefault();

	$.cookie('nif', $('#nif').val());
	$.cookie('password', $('#password').val());

	var nif = $('#nif').val();
	var password = $('#password').val();

	var opcion = 0;
	loginUser(nif, password, opcion, loginUserSuccess, loginUserError);
});

function loginUserError(jqXHR, options, error) {
	if (jqXHR.status == 404) {
		bootbox.alert("You are not registered.");
	}

	if (jqXHR.status == 409) {
		bootbox.alert("User or password incorrect.");
	}

	if (jqXHR.status == 403) {
		bootbox.alert("Permission denied.");
	}
}

function loginUserSuccess(data, status, jqxhr) {
	if (jqxhr.status == 200) {
		window.location.href = "homepage.html";
	}

	if (jqxhr.status == 202) { // upload a picture
		window.location.href = "homepage.html";
	}

}

$(function() {
	var textfield = $("input[name=user]");
	$('button[type="submit"]')
			.click(
					function(e) {
						e.preventDefault();
						// little validation just to check username
						if (textfield.val() != "") {
							// $("body").scrollTo("#output");
							$("#output")
									.addClass(
											"alert alert-success animated fadeInUp")
									.html(
											"Welcome back "
													+ "<span style='text-transform:uppercase'>"
													+ textfield.val()
													+ "</span>");
							$("#output").removeClass(' alert-danger');
							$("input").css({
								"height" : "0",
								"padding" : "0",
								"margin" : "0",
								"opacity" : "0"
							});
							// change button text
							$('button[type="submit"]').html("continue")
									.removeClass("btn-info").addClass(
											"btn-default").click(function() {
										$("input").css({
											"height" : "auto",
											"padding" : "10px",
											"opacity" : "1"
										}).val("");
									});

							// show avatar
							$(".avatar")
									.css(
											{
												"background-image" : "url('http://api.randomuser.me/0.3.2/portraits/women/35.jpg')"
											});
						} else {
							// remove success mesage replaced with error message
							$("#output").removeClass(' alert alert-success');
							$("#output").addClass(
									"alert alert-danger animated fadeInUp")
									.html("sorry enter a username ");
						}
						// console.log(textfield.val());

					});
});
