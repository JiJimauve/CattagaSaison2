const CLIENT_VERSION = "1.2.0"

ClientEvents.loggedIn(event => {
	
	event.player.sendData("client_version", {
		version: CLIENT_VERSION
	})
})