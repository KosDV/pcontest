function Prova() {

	var html = '<section><h2><span>Contact</span> us now</h2><p>If you are interested in either our project or our group, you can contact us on:</p><div><a href= "https://www.facebook.com/hemav.academics"><img src="caretos/fb.jpg" width="50" height="50" ></a><a href= "https://twitter.com/hemavgroup"><img src="caretos/twitter.jpg" width="50" height="50" ></a></div><iframe src="https://www.google.com/maps/embed?pb=!1m14!1m12!1m3!1d2717.434688773861!2d1.986894000000012!3d41.275161000000004!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!5e1!3m2!1ses!2ses!4v1397242029465" width="600" height="450" frameborder="0" style="border:0"></iframe></section>';
	document.getElementById("contingut-pagina").innerHTML = html;
}

function About() {
	var html = "";
	html += "	<div class=\"container theme-showcase\" role=\"main\">";
	html += "		<!-- Main jumbotron for a primary marketing message or call to action -->";
	html += "		<div class=\"jumbotron\">";
	html += "			<h1>Photo Contest<\/h1>";
	html += "			<p>This is photography contests. It includes a large callout called a jumbotron and three";
	html += "				supporting pieces of content. Use it as a starting point to create";
	html += "				something more unique.<\/p>";
	html += "		<\/div>";
	html += "	<\/div>";

	document.getElementById("contingut-pagina").innerHTML = html;
}

function Contact() {

	var html = "";
	html += "<div class=\"container\">";
	html += "		<form role=\"form\" style=\"width: 400px; margin: 0 auto;\">";
	html += "			<h1>Contact us<\/h1>";
	html += "			<div class=\"required-field-block\">";
	html += "				<input type=\"text\" placeholder=\"Name\" class=\"form-control\">";
	html += "				<div class=\"required-icon\">";
	html += "					<div class=\"text\">*<\/div>";
	html += "				<\/div>";
	html += "			<\/div>";
	html += "";
	html += "			<div class=\"required-field-block\">";
	html += "				<input type=\"text\" placeholder=\"Email\" class=\"form-control\">";
	html += "				<div class=\"required-icon\">";
	html += "					<div class=\"text\">*<\/div>";
	html += "				<\/div>";
	html += "			<\/div>";
	html += "";
	html += "			<input type=\"text\" placeholder=\"Phone\" class=\"form-control\">";
	html += "";
	html += "			<div class=\"required-field-block\">";
	html += "				<textarea rows=\"3\" class=\"form-control\" placeholder=\"Message\"><\/textarea>";
	html += "				<div class=\"required-icon\">";
	html += "					<div class=\"text\">*<\/div>";
	html += "				<\/div>";
	html += "			<\/div>";
	html += "";
	html += "			<button class=\"btn btn-primary\">Send<\/button>";
	html += "		<\/form>";
	html += "	<\/div>";

	document.getElementById("contingut-pagina").innerHTML = html;
}

function LoadPicture() {
	var html = "";
	html += "	<div class=\"container\">";
	html += "		<h2>File Upload with Jersey<\/h2>";
	html += "		<form action=\"localhost:8000\/prueba\/upload\" method=\"post\"";
	html += "			enctype=\"multipart\/form-data\">";
	html += "			<p>";
	html += "				Select a file: <input type=\"file\" name=\"file\" size=\"45\" \/>";
	html += "			<\/p>";
	html += "			<input type=\"submit\" value=\"Upload\" \/>";
	html += "		<\/form>";
	html += "	<\/div>";

	document.getElementById("contingut-pagina").innerHTML = html;
}

function DeadLine() {
	var html = "";
	html += "<section id=\"contingut\">";
	html += "		<!-- Button trigger modal -->";
	html += "		<button class=\"btn btn-primary btn-lg\" data-toggle=\"modal\"";
	html += "			data-target=\"#myModal\">Contest closed<\/button>";
	html += "";
	html += "		<!-- Modal -->";
	html += "		<div class=\"modal fade\" id=\"myModal\" tabindex=\"-1\" role=\"dialog\"";
	html += "			aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">";
	html += "			<div class=\"modal-dialog\">";
	html += "				<div class=\"modal-content\">";
	html += "					<div class=\"modal-header\">";
	html += "						<button type=\"button\" class=\"close\" data-dismiss=\"modal\"";
	html += "							aria-hidden=\"true\">&times;<\/button>";
	html += "						<h4 class=\"modal-title\" id=\"myModalLabel\">Contest closed<\/h4>";
	html += "					<\/div>";
	html += "					<div class=\"modal-body\">";
	html += "						The photography contest is closed :-(. If you'd like to receive";
	html += "						more information, please don't hesistate to contact us <a";
	html += "							href=\"#contact\" onClick='Contact()'>here<\/a>";
	html += "					<\/div>";
	html += "					<div class=\"modal-footer\">";
	html += "						<button type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\">Close<\/button>";
	html += "					<\/div>";
	html += "				<\/div>";
	html += "			<\/div>";
	html += "		<\/div>";
	html += "	<\/section>";

	document.getElementById("contingut-pagina").innerHTML = html;
}

function Home() {

	var html = "";
	html += "<section id=\"contingut\">";
	html += "				<!--------------------- FIRST LINE --------------------->";
	html += "				<div class=\"row\">";
	html += "					<div class=\"col-md-4 portfolio-item\">";
	html += "						<a href=\"#project-link\"> <img class=\"img-responsive\"";
	html += "							src=\"\/assets\/sampleImg\/1.jpg\">";
	html += "						<\/a>";
	html += "						<h3>";
	html += "							Sandland <input type=\"checkbox\" name=\"input\" value=\"Type here\">";
	html += "						<\/h3>";
	html += "					<\/div>";
	html += "";
	html += "					<div class=\"col-md-4 portfolio-item\">";
	html += "						<a href=\"#project-link\"> <img class=\"img-responsive\"";
	html += "							src=\"\/assets\/sampleImg\/4.jpg\">";
	html += "						<\/a>";
	html += "						<h3>";
	html += "							Old Bambi <input type=\"checkbox\" name=\"input\" value=\"Type here\">";
	html += "						<\/h3>";
	html += "					<\/div>";
	html += "";
	html += "					<div class=\"col-md-4 portfolio-item\">";
	html += "						<a href=\"#project-link\"> <img class=\"img-responsive\"";
	html += "							src=\"\/assets\/sampleImg\/3.jpg\">";
	html += "						<\/a>";
	html += "						<h3>";
	html += "							Skyrim <input type=\"checkbox\" name=\"input\" value=\"Type here\">";
	html += "						<\/h3>";
	html += "					<\/div>";
	html += "				<\/div>";
	html += "";
	html += "				<!--------------------- SECOND LINE --------------------->";
	html += "				<div class=\"row\">";
	html += "					<div class=\"col-md-4 portfolio-item\">";
	html += "						<a href=\"#project-link\"> <img class=\"img-responsive\"";
	html += "							src=\"\/assets\/sampleImg\/5.jpg\">";
	html += "						<\/a>";
	html += "						<h3>";
	html += "							NASCAR <input type=\"checkbox\" name=\"input\" value=\"Type here\">";
	html += "						<\/h3>";
	html += "					<\/div>";
	html += "";
	html += "					<div class=\"col-md-4 portfolio-item\">";
	html += "						<a href=\"#project-link\"> <img class=\"img-responsive\"";
	html += "							src=\"\/assets\/sampleImg\/6.jpg\">";
	html += "						<\/a>";
	html += "						<h3>";
	html += "							Sunset <input type=\"checkbox\" name=\"input\" value=\"Type here\">";
	html += "						<\/h3>";
	html += "					<\/div>";
	html += "";
	html += "					<div class=\"col-md-4 portfolio-item\">";
	html += "						<a href=\"#project-link\"> <img class=\"img-responsive\"";
	html += "							src=\"\/assets\/sampleImg\/2.jpg\">";
	html += "						<\/a>";
	html += "						<h3>";
	html += "							Station <input type=\"checkbox\" name=\"input\" value=\"Type here\">";
	html += "						<\/h3>";
	html += "					<\/div>";
	html += "				<\/div>";
	html += "";
	html += "				<!--------------------- THIRD LINE --------------------->";
	html += "				<div class=\"row\">";
	html += "					<div class=\"col-md-4 portfolio-item\">";
	html += "						<a href=\"#project-link\"> <img class=\"img-responsive\"";
	html += "							src=\"http:\/\/placehold.it\/700x400\">";
	html += "						<\/a>";
	html += "						<h3>";
	html += "							<input type=\"checkbox\" name=\"input\" value=\"Type here\">";
	html += "						<\/h3>";
	html += "					<\/div>";
	html += "					<div class=\"col-md-4 portfolio-item\">";
	html += "						<a href=\"#project-link\"> <img class=\"img-responsive\"";
	html += "							src=\"http:\/\/placehold.it\/700x400\">";
	html += "						<\/a>";
	html += "						<h3>";
	html += "							<input type=\"checkbox\" name=\"input\" value=\"Type here\">";
	html += "						<\/h3>";
	html += "					<\/div>";
	html += "					<div class=\"col-md-4 portfolio-item\">";
	html += "						<a href=\"#project-link\"> <img class=\"img-responsive\"";
	html += "							src=\"http:\/\/placehold.it\/700x400\">";
	html += "						<\/a>";
	html += "						<h3>";
	html += "							<input type=\"checkbox\" name=\"input\" value=\"Type here\">";
	html += "						<\/h3>";
	html += "					<\/div>";
	html += "				<\/div>";
	html += "			<\/section>";

	document.getElementById("contingut-pagina").innerHTML = html;
}

//var pathArray = window.location.href.split('#');
//alert("path: " + pathArray[pathArray.length - 1]);
//switch (pathArray[pathArray.length - 1]) {
//case "loadPicture":
//	LoadPicture();
//default:
//	Home();
//}