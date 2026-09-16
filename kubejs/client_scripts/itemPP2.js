
/** Carving pumpin (that are used for fake hat & stuff) cannot be placed **/

BlockEvents.placed('minecraft:carved_pumpkin', event => {
  event.cancel();
  })

BlockEvents.placed('refurbished_furniture:oak_storage_jar', event => {
  event.cancel();
})

// -------------------------------------------
// Utility function to check if there is a tag
function playerHasTag(player, tag) {
    if (!player) {
		return false
	} 
	
	return player.getTags().contains(tag)
}

// Staff Item
// Cancel event if player does not have tag "staff" 

BlockEvents.rightClicked('kubejs:staff_button', event => {
	
	if (!playerHasTag(event.getEntity(), 'staff') {
		player.tell("Rien ne se passe")
		event.cancel();
	}
	
});

// Helper Item
// Cancel event if player does not have tag "helper" 

BlockEvents.rightClicked('kubejs:helper_button', event => {
	
	if (!playerHasTag(event.getEntity(), 'helper') {
		player.tell("Rien ne se passe")
		event.cancel();
	}
	
});