var player;
var gameplay;
var app;

function notificationHandler(obj) {
	console.log("Got notification of type: " + obj.type);
	var value = obj[obj.type];
	if (obj.type === "Heartbeat") {
		// Long and ignore, this is just to confirm we are still here
		console.log("Got heartbeat");
	} else
	if (obj.type === "AvatarsChanged") {
		console.log("Got avatars change notification");
		updateAvatars();
	}
}

function updateAvatars() {
	var avatarsReq = new AvatarsRequest(gameplay["id"]);
	postWithResponse("avatars", avatarsReq, function(rspBody) {
		var avatarsRsp = JSON.parse(rspBody);
		var avatars = avatarsRsp["avatars"];
		app.setState({avatars: avatars});
	});
}

function modAvatars(op) {
	var modOp = new ModifyAvatarsRequest(gameplay["id"], op);
	post("modifyAvatars", modOp, function() {
		updateAvatars();
	});
}

function gameplayInit() {
	app = React.render(React.createElement(App, null), document.body);
	var gameplayReq = new GameplayRequest("jakis-tam-typ");
	postWithResponse("gameplay", gameplayReq, function(rspBody) {
		gameplay = JSON.parse(rspBody);
		app.setState({gameplay: gameplay});
		updateAvatars();
	});
}

function main() {
	player = new Player("swinka", "latajaca");
	postWithResponse("login", player, function(resp) {
		console.log("Login completed, with: " + resp);
		if (resp == '"OK"') {
			console.log("OK");
			longpoll(notificationHandler);
			gameplayInit();
		} else
		if (resp == '"WRONG_PASSWORD"') {
			console.log("wrong password");
		} else
		if (resp == '"UNKNOWN_USERNAME"') {
			console.log("unknown username");
		} else {
			console.error("Unknown server response to login: " + resp);
		}
	});
}
