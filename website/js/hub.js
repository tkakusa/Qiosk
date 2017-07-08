$(document).ready(function() {
	$('#nav-profile').click(function() {
		$('#current').removeClass('top middle bottom').addClass('top');
		$('#nav-profile').addClass('active');
		$('#nav-browse').removeClass('active');
		$('#nav-current').removeClass('active');	
	});	
	
	$('#nav-browse').click(function() {
		$('#current').removeClass('top middle bottom').addClass('middle');
		$('#nav-profile').removeClass('active');
		$('#nav-browse').addClass('active');
		$('#nav-current').removeClass('active');	
	});	
	
	$('#nav-current').click(function() {
		$('#current').removeClass('top middle bottom').addClass('bottom');
		$('#nav-profile').removeClass('active');
		$('#nav-browse').removeClass('active');
		$('#nav-current').addClass('active');	
	});	

	$('#logout').click(function() {
		document.location.href="./index.html";
	});
});
