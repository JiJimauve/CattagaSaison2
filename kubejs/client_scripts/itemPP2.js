
/** Carving pumpin (that are used for fake hat & stuff) cannot be placed **/

BlockEvents.placed('minecraft:carved_pumpkin', event => {
  event.cancel();
  })

BlockEvents.placed('refurbished_furniture:oak_storage_jar', event => {
  event.cancel();
})

/*
// Check if player has tag "labubu"
if (player.getTags().contains('labubu')) {
player.addEffect('minecraft:luck', 1200, 0) // 1200 ticks = 1 minute
}
*/