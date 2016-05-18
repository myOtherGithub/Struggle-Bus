//struggleController.js

struggles = "";

function getBusses(url){
	$.ajax({ 
	    type: 'GET', 
	    url: url, 
	    dataType:'json',
	    success: function (data) {
			struggles = data;
			populateBusses(data);
	    }
	});
}

timeTillNextCall= 1000;
function busHeartbeat(url){
	
	getBusses(url);
	
	setTimeout( function() {
		console.log(timeTillNextCall);
		getBusses(url);
	    busHeartbeat(url);
	    timeTillNextCall*=2;
	}, timeTillNextCall);
	
	
}

function populateBusses(data){
	$("#strugglesList").empty();
	for(var i = Object.keys(data).length-1; i>=0; i--){
		var currentBus = generateBus(data[i]);
		$("#strugglesList").append(currentBus);
	}
}

function postBus(){
	var busObject = {};
	busObject.uuid = "";
	busObject.title = $(".struggleTitleInput").val();
	busObject.struggle = $(".struggleInput").val();
	busObject.riders = 1;
	busObject.busColor = "yellow";
	busObject.postDate = new Date();
	
	var jsonData = JSON.stringify(busObject); 

	$.ajax({ 
 		type: 'POST', 
	    url: "./postStruggle", 
	    headers: { 
	        'Accept': 'application/json',
	        'Content-Type': 'application/json' 
	    },
	    dataType: 'json', 			    
	   	data: jsonData, 
	    contentType: 'application/json',
	    mimeType: 'application/json',
	    success: function (data) {
			hidePostBusModal();
			showMessage("success! your post has been submitted");
	    }
	});
}

function showPostBusModal(){
	$(".struggleTitleInput").val("");
	$(".struggleInput").val("");
	$("#backgroundShader").fadeIn();
	$(".postStruggle").fadeIn();
}

function hidePostBusModal(){
	$("#backgroundShader").fadeOut();
	$(".postStruggle").fadeOut();
	$(".struggleTitleInput").val("");
	$(".struggleInput").val("");
}

function showCopyBusModal(url){
	$(".copyStruggleUrlInput").val(url);
	$("#backgroundShader").fadeIn();
	$(".copyStruggleUrlModal").fadeIn();
}

function hideCopyBusModal(){
	$("#backgroundShader").fadeOut();
	$(".copyStruggleUrlModal").fadeOut();
	$(".copyStruggleUrlInput").val("");
}

function createTopBar(){
 	var topbar = populateTopBar();
 	$("#menu").append(topbar);
}

function showMessage(message){
 
}

function ridePost(uuid){
	var busObject = {};
	busObject.uuid = uuid;
	busObject.title = "";
	busObject.struggle = "";
	busObject.riders = 1;
	busObject.busColor = "yellow";
	busObject.postDate = new Date();
	
	var jsonData = JSON.stringify(busObject); 
	$.ajax({ 
 		type: 'POST', 
	    url: "./joinStruggle", 
	    headers: { 
	        'Accept': 'application/json',
	        'Content-Type': 'application/json' 
	    },
	    dataType: 'json', 			    
	   	data: jsonData, 
	    contentType: 'application/json',
	    mimeType: 'application/json',
	    success: function (data) {
	    	var following = Cookies.getJSON('bussess');
	    	
	    	if(following  === undefined){
	    	 	Cookies.set('bussess', [{uuid}]);
	    	}else{
	    		following.push({uuid});
	    		Cookies.set('bussess', following);
	    	}
	    	getBusses("./getHotBusses");
	    }
	});
}

function isRiding(uuid){
	var returnValue = false;
	var followings = Cookies.getJSON('bussess');
	if(followings  !== undefined){
		for(var i = Object.keys(followings).length-1; i>=0; i--){
		
			if(followings[i].uuid == uuid){
				returnValue = true;
				return returnValue;
			}
		}
	}
	return returnValue;
}