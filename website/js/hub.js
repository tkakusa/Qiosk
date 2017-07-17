var ip = '192.168.0.5';
$(document).ready(function() {

	$.ajax({
			type: 'GET',
			url: "http://"+ip+":8000/profile/",
			headers: {
				'Authorization':localStorage.getItem('token'),
				'Content-Type' : 'application/x-www-form-urlencoded; charset=UTF-8'
			},
			crossDomain: true,
			data: {
			},
			contentType: "application/x-www-form-urlencoded; charset=UTF-8",
			success: function(responseData, textStatus, jqXHR) {
				fillInitial(responseData);					
			},
			error: function(jqXHR, textStatus, errorThrown) {
				//alert("Browse Issue");
			}
	});

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

		$('div#job-card').remove();

		$.ajax({
				type: 'GET',
				url: "http://"+ip+":8000/list/",
				headers: {
					'Authorization':localStorage.getItem('token'),
					'Content-Type' : 'application/x-www-form-urlencoded; charset=UTF-8'
				},
				crossDomain: true,
				data: {
					//'token' : localStorage.getItem('token')
				},
				success: function(responseData, textStatus, jqXHR) {
					//alert(JSON.stringify(responseData));
					fillJobs(responseData, 1);
				},
				error: function(jqXHR, textStatus, errorThrown) {
					alert("Browse Issue");
				}
		});
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

	$("#nav-profile").click(function() {
		$("#hub-profile").removeClass("hide");
		$("#hub-browse").removeClass("hide");
		$("#hub-current").removeClass("hide");

		$("#hub-browse").addClass("hide");
		$("#hub-current").addClass("hide");
	});

	$("#nav-browse").click(function() {
		$("#hub-browse").removeClass("hide");
		$("#hub-profile").removeClass("hide");
		$("#hub-current").removeClass("hide");

		$("#hub-profile").addClass("hide");
		$("#hub-current").addClass("hide");
	});

	$("#nav-current").click(function() {
		$("#hub-browse").removeClass("hide");
		$("#hub-profile").removeClass("hide");
		$("#hub-current").removeClass("hide");

		$("#hub-browse").addClass("hide");
		$("#hub-profile").addClass("hide");
		
		$('div#job-card').remove();

		$.ajax({
				type: 'GET',
				url: "http://"+ip+":8000/acceptedJobs/",
				headers: {
					'Authorization':localStorage.getItem('token'),
					'Content-Type' : 'application/x-www-form-urlencoded; charset=UTF-8'
				},
				crossDomain: true,
				data: {
					//'token' : localStorage.getItem('token')
				},
				success: function(responseData, textStatus, jqXHR) {
					//alert(JSON.stringify(responseData));
					fillJobs(responseData, 0);
				},
				error: function(jqXHR, textStatus, errorThrown) {
					alert("Browse Issue");
				}
		});
	});
});

function fillPendingJobs(response) {
	
}

function getPendingJobs() {
	$.ajax({
			type: 'GET',
			url: "http://"+ip+":8000/pendingJobs/",
			headers: {
				'Authorization':localStorage.getItem('token'),
				'Content-Type' : 'application/x-www-form-urlencoded; charset=UTF-8'
			},
			crossDomain: true,
			data: {
				//'token' : localStorage.getItem('token')
			},
			success: function(responseData, textStatus, jqXHR) {
				document.getElementById("pending-jobs").innerHTML = responseData.length;		
			},
			error: function(jqXHR, textStatus, errorThrown) {
				alert("Browse Issue");
			}
	});
}

function fillInitial(json) {
	//alert('Initial');
	document.getElementById("brief-name").innerHTML = json.firstName + ' ' + json.lastName;
	document.getElementById("brief-money").innerHTML = "$" + json.accountBalance;
	document.getElementById("brief-rating").innerHTML = json.rating;
	document.getElementById("account-balance").innerHTML = "Balance: $" + json.accountBalance;
	getPendingJobs();
}

function fillJobs(json, accept) {
	for (var i = 0; i < json.length; i++) {
		var obj = json[i];

		var name = obj.title;
		var pay = obj.payment;
		var desc = obj.description;
		var start = obj.startDate;
		var create = obj.postDate;
		var numPpl = obj.numberPeopleNeeded;
		var numTotal = obj.numberPeopleAccepted;
		var address = obj.address;
		var stat = obj.status;

		var s = '<div class="row"><div class="columns large-8 medium-8"><h2>'+name+'</h2><p>'+desc+'</p><p>'+address+'</p></div><div class="columns large-4 medium-4"><h3>$'+pay+'.00</h3><p>'+start+'</p><p>'+numPpl+'/'+numTotal+' accepted</p></div></div>';
		
		if (accept) {
			s += '<div class="row"><a id="accept'+obj.pk+'" href="#" class="button large-6 medium-6 large-centered medium-centered columns">Accept</a></div>';
		}

		var div = document.createElement('div');
		div.className = 'job-card row large-10 medium-10 card';
		div.id ='job-card';
		div.innerHTML = s;
		$('.all').append(div);
	}

	$('a').click(function() {
		var id = this.id;
		if (id.includes('accept')) {
			var pk = id.replace("accept","");
			$.ajax({
					type: 'POST',
					url: "http://"+ip+":8000/joinJob/",
					headers: {
						'Authorization':localStorage.getItem('token'),
						'Content-Type' : 'application/x-www-form-urlencoded; charset=UTF-8'
					},
					crossDomain: true,
					data: {
						'pk'	  : pk
					},
					success: function(responseData, textStatus, jqXHR) {
						alert('Joined!');
					},
					error: function(jqXHR, textStatus, errorThrown) {
						alert("Failed to Join :(");
					}
			});
		}
	});
}
