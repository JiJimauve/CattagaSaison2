
// Safe is not breakable
BlockEvents.broken('supplementaries:safe', event => {
	event.player.tell("Ce bloc est visiblement incassable");
	event.cancel()
})


// Safe is not breakable
BlockEvents.broken('supplementaries:netherite_door', event => {
	event.player.tell("Ce bloc est visiblement incassable");
	event.cancel()
})

