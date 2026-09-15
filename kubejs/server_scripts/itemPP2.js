
/** Carving pumpin (that are used for fake hat & stuff) cannot be placed **/
BlockEvents.placed('minecraft:carved_pumpkin', event => {
  event.cancel();
})

BlockEvents.placed('refurbished_furniture:oak_storage_jar', event => {
  event.cancel();
})
