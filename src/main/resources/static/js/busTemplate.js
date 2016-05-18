//bus template

function generateBus(busObject){
	var joined = isRiding(busObject.uuid);
	var busWord = "";
	if(joined){
		busWord = "YOU'RE RIDING THIS BUS";
	}else{
		busWord = "RIDE BUS";
	}
	
	var busTemplate = $([
	    '<div class="struggleContainer" id="'+busObject.uuid+'">',
		'	<div class="struggleContent">',
		' 		<h3>'+busObject.title+'</h3>',
		'		<p>'+busObject.struggle+'</p>',
		'	</div>',
		'	<div class="riderInfo">',
		' 		<div class="riderCount">'+busObject.riders+' riders</div>',
		'		<div class="share" id="postId-'+busObject.uuid+'"> Share Bus </div>',
		' 		<div id="'+busObject.uuid+'" class="join joined-'+joined+'">'+busWord+'</div>',
		' 	</div>',
		'</div>'
	].join("\n"));
	return busTemplate;
}

function populateTopBar(){
	var topBarTemplate = $([
	    '<div id="menuItemContainer">',
	 	    '<a href="./hotstruggles.html"><div class="menuItem" id="hot"><h2>🔥</h2></div></a>',
	 	    '<a href="./topstruggles.html"><div class="menuItem" id="top"><h2>👍</h2></div></a>',
	 	    '<a href="./newstruggles.html"><div class="menuItem" id="new"><h2>⭐</h2></div></a>',
	 	    '<div class="menuItem" id="post"><h2>📝</h2></div>',
 	    '</div>'
 	].join("\n"));
	return topBarTemplate;
}