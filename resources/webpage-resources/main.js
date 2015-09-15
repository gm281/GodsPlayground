function notificationHandler(obj) {
	console.log("Got notification of type: " + obj.type);
	var value = obj[obj.type];
	if (obj.type === "Heartbeat") {
		// Long and ignore, this is just to confirm we are still here
		console.log("Got heartbeat");
	} else
	if (obj.type === "GodsPlaygroundInterestingDataStructure") {
		interesting = value;
		//document.getElementById("p2").innerHTML= "The cycle counter: " + interesting.someLong + ", other bits: " + interesting.someClass.someInt + ", " + interesting.someArray[1];
	}
}

function main() {
	var player = new Player("swinka", "latajaca");
	postWithResponse("login", player, function(resp) {
		console.log("Login completed, with: " + resp);
		if (resp == '"OK"') {
			console.log("OK");
			longpoll(notificationHandler);
			React.render(React.createElement(App, null), document.body);
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
