console.info('Proximity Chat loaded')

const NickService = Java.loadClass('com.sammy.namechanger1_20_1.nickname.NicknameService');

const proximityChatDist = "30";

function escapeTellraw(str) {
	return str.replace('"', '\\"');}

PlayerEvents.chat(event => {
	const player = event.player;
	
	
  	const x = player.position().x();
	const y = player.position().y();
	const z = player.position().z();
	const dim = player.getLevel().dimension;
	
	let nickname = NickService.getNickname(event.server, player.uuid)
	
	if (nickname === null) {
		nickname = player.username;
	}
	nickname = escapeTellraw(nickname);
	let msg = escapeTellraw(event.message);
	/* console.log(`<${nickname} | ${player.username}> ${msg}`);
		console.log(`execute in ${dim} run tellraw @a[x=${x},y=${y},z=${z},distance=..${proximityChatDist}] {"text":"<${nickname}> ${msg}"}`);*/

	event.server.runCommandSilent(`execute in ${dim} run tellraw @a[x=${x},y=${y},z=${z},distance=..${proximityChatDist}] {"text":"<${nickname}> ${msg}"}`);
	event.cancel();
})
