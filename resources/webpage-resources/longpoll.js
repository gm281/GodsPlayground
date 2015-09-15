function longpoll(handler) {
	get("longpoll", function(rsp) {
		var obj = JSON.parse(rsp);
		handler(obj);
		longpoll(handler);
	});
}
