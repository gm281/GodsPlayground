function Player(username, password) {
    this.username = username;
    this.password = password;
}

function GameplayRequest(type) {
	this.type = type;
}

function AvatarsRequest(gameplayId) {
	this.gameplayId = gameplayId;
}

function ModifyAvatarsRequest(gameplayId, addOrRemove) {
    this.gameplayId = gameplayId;
    this.addOrRemove = addOrRemove;
}
