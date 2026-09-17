
/** Carving pumpin (that are used for fake hat & stuff) cannot be placed **/

BlockEvents.placed('minecraft:carved_pumpkin', event => {
  event.cancel();
  })

BlockEvents.placed('refurbished_furniture:oak_storage_jar', event => {
  event.cancel();
})

// -------------------------------------------
// Utility function to check if there is a tag
// If not tag tell player that it won't work 
function playerHasTag(player, tag) {
    if (!player) {
		return false
	} 
	
	return player.getTags().contains(tag)
}

function playerTellMsg(player,msg) {
	if (player) {
		player.tell(msg)
	}
}

// Staff Item
// Cancel event if player does not have tag "staff" 

BlockEvents.rightClicked('kubejs:staff_button', event => {
	
	if (!playerHasTag(event.getEntity(), 'staff')) {
		playerTellMsg(event.getEntity(), "Rien ne se passe");
		event.cancel();
	}
	
});

// Helper Item
// Cancel event if player does not have tag "helper" 

BlockEvents.rightClicked('kubejs:helper_button', event => {
	
	if (!playerHasTag(event.getEntity(), 'helper')) { 
	playerTellMsg(event.getEntity(), "Rien ne se passe");
		event.cancel();
	}
	
});