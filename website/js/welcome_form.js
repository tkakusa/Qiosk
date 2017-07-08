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
