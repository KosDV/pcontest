/** variables definition * */
var json;
var msg;
var JSONobject;

$(document)
		.ready(
				function() {

					var nif = $.cookie('nif');
					var password = $.cookie('password');

					msg = '{"results":{"census":10,"results":[{"nif":"12345678A","votes":10,"photo":{"title":"A","description":"A","author":"A","url":"https://localhost:4430/photo/NmJmOGVlMzg3YzMxZWQ2ZTgzNDRhMmVjMDQwNzdlN2Y3NGM2NDcxOTg5MzRhYjI0NTA4NGMwNjhlZDEyNzAxY2M4NGMxMDA0ZWNhNjcxOTA5Y2MxMGNmMWM2MzJhZTUxYjcxOGQyODNiYzYzOWU0YWIzMDU4ZmZhN2ZkYTU3MDU=/photo.jpg","latLng":null}},{"nif":"12345678I","votes":10,"photo":{"title":"I","description":"I","author":"I","url":"https://localhost:4430/photo/NDQ2YWM1ZjllMmFjMjVhMjJiNjgyYzgyOTZjNWE4YmYyNzcxNTQzNTdkNDllZDgwODc0OTg5MWQzOWI1Y2M4NjE4ODgwMDg4ODYxYmY0OTc3ZjE3OGY4YjQ4NTg0ZTM5ZTM1ZTdkMWU5NTAwMTQ3N2QzNThmZjg2ZTM0N2NiMDY=/photo.jpg","latLng":null}},{"nif":"12345678J","votes":0,"photo":{"title":"J","description":"J","author":"J","url":"https://localhost:4430/photo/YmU3MDJmNWM0NDk5YTk5ZTJhYzBkNzAzOGRjN2VlZTcyODE3ZDE3Mjc5ZWY4NDI0NzU5OGVlYTM4MzM2YTZmZGUyNjM4MGRiODA2YWY4ZjUyYTIwNmRkYmQ4NjhjYjE4ZjNmMTE1NTIyNjU5Njg3ZjFjYzU5MTMyMWNmMDQxNWQ=/photo.jpg","latLng":null}}],"photos":10,"votes":10}}';
					// getResults(nif, password, callbackGetResults,
					// callbackGetResultsError);

					JSONobject = JSON.parse(msg);
					createTable(JSONobject);

				});

function callbackGetResults(data, status, jqxhr) {
	json = data;
	createTable(json);
}

function callbackGetResultsError(jqXHR, options, error) {
	console.log("Connection to the server failed");
}

function createTable(JSONobject) {
	var author;
	var title;

	var totalPictures; // photos

	var i = 0;

	var tmp = JSONobject.results.results;
	var census = JSONobject.results.census;
	console.log(census);
	console.log(tmp[1].photo.author);
	console.log(tmp[1].photo.title);
	console.log(tmp[1].nif);

	$('#results_table').html('');
	$('#results_table')
			.append(
					'<thead><tr><th>Name</th><th>Photo Title</th><th>Census</th><th>URL</th></tr></thead>');
	while (i < tmp.length) {
		$('#results_table')
				.append(
						'<tr><td>'
								+ tmp[i].photo.author
								+ '</td><td>'
								+ tmp[i].photo.title
								+ '</td><td>'
								+ census
								+ '</td><td><a href="'
								+ tmp[i].photo.url
								+ '"><span class="glyphicon glyphicon-picture"></span></a></td></tr>');
		i++;
	}
}