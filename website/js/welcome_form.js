$(document).ready(function() {
	var ip = '192.168.0.5';

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
		
	$("#form-signin").submit(function(e){
		$.ajax({
				type: "post",
				url: "http://"+ip+":8000/loginUser/",
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
				url: "http://"+ip+":8000/createUser/",
				crossDomain: true,
				data: $("#form-signup-worker").serialize(),
				contentType: "application/x-www-form-urlencoded",
				success: function(responseData, textStatus, jqXHR) {
					localStorage.setItem('token',responseData);
					window.location.replace("./hub.html");
				},
				error: function(jqXHR, textStatus, errorThrown) {
					//alert("Missing Field");
				}
		});
	});
	
	$("#form-signup-employer").submit(function(e){
		$.ajax({
				type: "post",
				url: "http://"+ip+":8000/createEmployer/",
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
/*
var localVideo;
    var localCanvas;

    initialize = function() {
      localVideo = document.getElementById("localVideo");
      localCanvas = document.getElementById("localCanvas");
      try {
        navigator.getUserMedia({video:true}, onGotStream, onFailedStream);
    //trace("Requested access to local media");
  } catch (e) {
    alert("getUserMedia error " + e);
    //trace_e(e, "getUserMedia error");
  }
}

poll = function() {
  var w = localVideo.videoWidth;
  var h = localVideo.videoHeight;
  var canvas = document.createElement('canvas');
  canvas.width  = w;
  canvas.height = h;
  var ctx = canvas.getContext('2d');
  ctx.drawImage(localVideo, 0, 0, w, h);
  var comp = ccv.detect_objects({ "canvas" : ccv.grayscale(canvas),
                                "cascade" : cascade,
                                "interval" : 5,
                                "min_neighbors" : 1 });
  /* draw detected area /
  localCanvas.width = localVideo.clientWidth;
  localCanvas.height = localVideo.clientHeight;

  var ctx2 = localCanvas.getContext('2d');
  ctx2.lineWidth = 2;
  ctx2.lineJoin = "round";
  ctx2.clearRect (0, 0, localCanvas.width,localCanvas.height);

  var x_offset = 0, y_offset = 0, x_scale = 1, y_scale = 1;
  if (localVideo.clientWidth * localVideo.videoHeight > localVideo.videoWidth * localVideo.clientHeight) {
    x_offset = (localVideo.clientWidth - localVideo.clientHeight *
                localVideo.videoWidth / localVideo.videoHeight) / 2;
  } else {
    y_offset = (localVideo.clientHeight - localVideo.clientWidth *
                localVideo.videoHeight / localVideo.videoWidth) / 2;
  }
  x_scale = (localVideo.clientWidth - x_offset * 2) / localVideo.videoWidth;
  y_scale = (localVideo.clientHeight - y_offset * 2) / localVideo.videoHeight;

  for (var i = 0; i < comp.length; i++) {
    comp[i].x = comp[i].x * x_scale + x_offset;
    comp[i].y = comp[i].y * y_scale + y_offset;
    comp[i].width = comp[i].width * x_scale;
    comp[i].height = comp[i].height * y_scale;

    var opacity = 0.1;
    if (comp[i].confidence > 0) {
      opacity += comp[i].confidence / 10;
      if (opacity > 1.0) opacity = 1.0;
    }

    //ctx2.strokeStyle = "rgba(255,0,0," + opacity * 255 + ")";
    ctx2.lineWidth = opacity * 10;
    ctx2.strokeStyle = "rgb(255,0,0)";
    ctx2.strokeRect(comp[i].x, comp[i].y, comp[i].width, comp[i].height);
  }
  setTimeout(poll, 1000);
}


onGotStream = function(stream) {
  localVideo.style.opacity = 1;
  localVideo.srcObject = stream;
  localStream = stream;

  //trace("User has granted access to local media. url = " + url);
  setTimeout(poll, 2000);
}

onFailedStream = function(error) {
  alert("Failed to get access to local media. Error code was " + error.code + ".");
  //trace_warning("Failed to get access to local media. Error code was " + error.code);
}


setTimeout(initialize, 1);*/
