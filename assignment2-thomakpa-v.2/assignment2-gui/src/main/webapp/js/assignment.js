

function getStudentData() {
	// This must be implemented by you. The json variable should be fetched
	// from the server, not initiated with a static value as below.
	// You must first download the student json data from the server
	// then call populateStudentTable(json);
	// and then populateStudentLocationForm(json);
	/*$.getJSON("/api/student.json", function(json) {
		populateStudentTable(json);
		populateStudentLocationForm(json);
	});*/
	
	$.ajax({
		url: '/api/student',
		type: 'GET',
		dataType: 'json',
		success: function (json ){
		populateStudentTable(json);
		populateStudentLocationForm(json);
		}
		});
}

function populateStudentTable(json) {
	$('#studentTable').empty();
	// for each student make a row in the student location table
	// and show the name, all courses and location.
	// if there is no location print "No location" in the <td> instead
	// tip: see populateStudentLocationForm(json) og google how to insert html
	// from js with jquery.
	// Also search how to make rows and columns in a table with html

	// the table can you see in index.jsp with id="studentTable"

	for (var i = 0; i < Object.keys(json).length; i++) {
		var text = '';
		var student = json[i];
		student = explodeJSON(student);
		text += '<tr><td>' + student.name + '</td><td>';
		
		for (var j = 0; j < student.courses.length; j++) {
			var course = student.courses[j];
			course = explodeJSON(course);
			text += course.courseCode + " ";
			
		}

		if ( student.latitude != null && student.longitude != null) {
		text += '</td><td>' + student.latitude + ', ' + student.longitude
				+ '</td></tr>';
		var myLatlng = new google.maps.LatLng(student.latitude, student.longitude);                        
		var marker = new google.maps.Marker({
		   position: myLatlng,
		   map: map,
		   title: student.name
		});
		} else {
			text += '</td><td>no location</td></tr>';
		}

		
		
		$('#studentTable').append(text);
	}
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


function get_location() {
	if (Modernizr.geolocation) {
		navigator.geolocation.getCurrentPosition(location_found);
	} else {
		console.log("Noe geolocation support");
	}
}


function location_found(position) {
	
	/*
	//$.getJSON("/api/student/" + $("selectedStudent").val() + "/location", function(json) {
		$.getJSON("/api/student/" + "1" + "/location", function(json) {
			
		latitude : position.coords.latitude; 
		longitude : position.coords.longitude;
		
		
	}, 	function(json) {
			console.log("TEST location ");
			populateStudentTable(json);
			console.log(position.coords.latitude);
			console.log(position.coords.longitude);
	}
	);
	
	*/
	
	$.ajax({
		url : "/api/student/" + document.getElementById("selectedStudent").value + "/location",
		type : "GET", data : { latitude : position.coords.latitude, longitude : position.coords.longitude
		},
		success : function(json) {
			populateStudentTable(json);
		},
		error : function(error) {
			console.log("error location found");
		}
	});

}

var objectStorage = new Object();

function explodeJSON(object) {
	if (object instanceof Object == true) {
		objectStorage[object['@id']] = object;
		//console.log('Object is object');
	} else {
		console.log('Object is not object');
		object = objectStorage[object];
		//console.log(object);
	}
	//console.log(object);
	return object;
}

var map;
function initialize_map() {
	var mapOptions = {
		zoom : 10,
		mapTypeId : google.maps.MapTypeId.ROADMAP
	};
	map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
	if (navigator.geolocation) {
		navigator.geolocation.getCurrentPosition(function(position) {
			var pos = new google.maps.LatLng(position.coords.latitude,
					position.coords.longitude);
			map.setCenter(pos);
		}, function() {
			handleNoGeolocation(true);
		});
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
