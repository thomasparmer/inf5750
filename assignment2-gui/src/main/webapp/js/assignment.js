function getStudentData() {
	// This must be implemented by you. The json variable should be fetched
	// from the server, not initiated with a static value as below.
	// You must first download the student json data from the server
	// then call populateStudentTable(json);
	// and then populateStudentLocationForm(json);

	$.ajax({
		url : 'http://localhost:8080/assignment2-gui/api/student',
		type : 'GET',
		dataType : 'json',
		success : function(json) {
			console.log(json)
			populateStudentTable(json);
			populateStudentLocationForm(json);
		}
	});

}

function populateStudentTable(json) {
	console.log("populateStudentTable");
	// for each student make a row in the student location table
	// and show the name, all courses and location.
	// if there is no location print "No location" in the <td> instead
	// tip: see populateStudentLocationForm(json) og google how to insert html
	// from js with jquery.
	// Also search how to make rows and columns in a table with html

	// the table can you see in index.jsp with id="studentTable"

	var text = '';
	for (var i = 0; i < json.length; i++) {
		var student = json[i];
		student = explodeJSON(student);
		text += '<tr class="populateStudentTable"><td>' + student.name + '</td>';

		var courseText = '';
		for (var j = 0; j < student.courses.length; j++) {
			courseText += student.courses[j].courseCode + " ";
		}

		text += '<td>' + courseText + '</td>';
		text += '<td>' + student.latitude + ',' + student.longitude
				+ '</td></tr>';
		add_marker(student.latitude, student.longitude, student.name);
	}

	$('#studentTable').append(formString);
}

function populateStudentLocationForm(json) {
	var formString = '<tr><td><select id="selectedStudent" name="students">';
	for (var s = 0; s < json.length; s++) {
		var student = json[s];
		student = explodeJSON(student);
		formString += '<option value="' + student.id + '">' + student.name
				+ '</option>';
	}
	formString += '</select></td></tr>';

	$('#studentLocationTable').append(formString);

}

$('#locationbtn').on('click', function(e) {
	e.preventDefault();
	get_location();
});

// This function gets called when you press the Set Location button
/*
 * function get_location() { navigator.geolocation.getCurrentPosition(show_map); }
 */

function get_location() {
	navigator.geolocation.getCurrentPosition(
			function(position) {
					location_found(position);
			},
			function(er){
				console.log(er);
			}
);
}

// Call this function when you've succesfully obtained the location.
function location_found(position) {

	// Extract latitude and longitude and save on the server using an AJAX call.
	// When you've updated the location, call populateStudentTable(json); again
	// to put the new location next to the student on the page. .
	var comboBox = document.getElementById("selectedStudent");
	var user = comboBox.options[comboBox.selectedIndex].value;
	// ajax
	$.ajax({
		url : "http://localhost:8080/assignment2-gui/api/student/" + user
				+ "/location",
		type : "GET",
		data : {
			latitude : position.coords.latitude,
			longitude : position.coords.longitude
		},
		success : function(result) {
			// then call populateStudentTable(json);
			populateStudentTable(result);
		},
		error : function(er) {
			console.log(er);
		}
	});

}

var objectStorage = new Object();

function explodeJSON(object) {
	if (object instanceof Object == true) {
		objectStorage[object['@id']] = object;
		console.log('Object is object');
	} else {
		console.log('Object is not object');
		object = objectStorage[object];
		console.log(object);
	}
	console.log(object);
	return object;
}

var map;
function initialize_map() {
	var mapOptions = {
		zoom : 10,
		mapTypeId : google.maps.MapTypeId.ROADMAP
	};
	map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
	// Try HTML5 geolocation
	if (navigator.geolocation) {
		navigator.geolocation.getCurrentPosition(function(position) {
			var pos = new google.maps.LatLng(position.coords.latitude,
					position.coords.longitude);
			map.setCenter(pos);
		}, function() {
			handleNoGeolocation(true);
		});
	} else {
		// Browser doesn't support Geolocation
		// Should really tell the user…
	}
}

function handleNoGeolocation(errorFlag) {
	if (errorFlag) {
		var content = 'Error: The Geolocation service failed.';
	} else {
		var content = 'Error: Your browser doesn\'t support geolocation.';
	}

	var options = {
		map : map,
		position : new google.maps.LatLng(60, 105),
		content : content
	};

	var infowindow = new google.maps.InfoWindow(options);
	map.setCenter(options.position);
}
