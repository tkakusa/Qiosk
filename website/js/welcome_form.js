$(document).ready(function() {
	$('#swap2employer').click(function() {
		//hide worker form
		$('#signup-worker').addClass('hide');

		//show employer form
		$('#signup-employer').removeClass('hide');
	});

	$('#swap2worker').click(function() {
		//hide employer form
		$('#signup-employer').addClass('hide');

		//show worker form
		$('#signup-worker').removeClass('hide');
	});
});

$(document).ready(function() {
	$("#form-signin").submit(function(e){
		$.ajax({
				type: "post",
				url: "http://10.73.172.61:8000/loginUser/",
				crossDomain: true,
				data: $("#form-signin").serialize(),
				contentType: "application/x-www-form-urlencoded",
				success: function(responseData, textStatus, jqXHR) {
					localStorage.setItem('token',responseData);
					window.location.replace("./hub.html");
				},
				error: function(jqXHR, textStatus, errorThrown) {
					alert("Incorrect Login");
				}
		});
	});
	
	$("#form-signup-worker").submit(function(e){
		$.ajax({
				type: "post",
				url: "http://10.73.172.61:8000/createUser/",
				crossDomain: true,
				data: $("#form-signup-worker").serialize(),
				contentType: "application/x-www-form-urlencoded",
				success: function(responseData, textStatus, jqXHR) {
					localStorage.setItem('token',responseData);
					window.location.replace("./hub.html");
				},
				error: function(jqXHR, textStatus, errorThrown) {
					alert("Missing Field");
				}
		});
	});
	
	$("#form-signup-employer").submit(function(e){
		$.ajax({
				type: "post",
				url: "http://10.73.172.61:8000/createEmployer/",
				crossDomain: true,
				data: $("#form-signup-employer").serialize(),
				contentType: "application/x-www-form-urlencoded",
				success: function(responseData, textStatus, jqXHR) {
					localStorage.setItem('token',responseData);
					window.location.replace("./hub.html");
				},
				error: function(jqXHR, textStatus, errorThrown) {
					alert("Missing Field");
				}
		});
	});
});
