const SERVER_VERSION = "1.2.0"

NetworkEvents.dataReceived("client_version", event => {
	const player = event.player
	const clientVersion = event.data.version

	if (clientVersion != SERVER_VERSION) {
		
		player.runCommandSilent(`title ${player.username} times 1s 15s 1s`);	
		
		player.runCommandSilent(`title ${player.username} title {"text":"Votre modpack n'est pas à jour, merci de mettre à jour","color":"red"}`)
		player.runCommandSilent(`title ${player.username} subtitle {"text":"Regardez la procédure de mise à jour ou demandez sur le Discord","color":"yellow"}`)

		player.tell(
			Text.red("Version serveur ")
			.append(Text.white(SERVER_VERSION))
			.append(Text.red(" <> Version client "))
			.append(Text.white(clientVersion))
		)
		
		player.tell(
		Text.yellow("Demande de l'aide sur ")
		.append(
			Text.aqua("[Discord]")
			.clickOpenUrl("https://discord.com/channels/357263205455626260/1480561875568562330")
			.hover("Clique pour accéder à la conversation discord")
		)
		);
			
			
		console.log(`Received VERSION MISS MATCHED CHECK from : ${player.username} : <${clientVersion}> <?> ${SERVER_VERSION}`);
		
		player.runCommandSilent(`tell @a[tag=op] [MSG SYSTEME] ${player.username} s'est co avec une version différente : SERV ${SERVER_VERSION} <> ${clientVersion} `);
		
	}
})