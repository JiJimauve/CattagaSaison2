

console.info('Blocks PP2 : V1.0.0')
// Creating Blocks
StartupEvents.registry('block', event => {
	
  // Creating the Xen Rock base
  event.create('xenrock').
  displayName('Roche Rouge et grise')
  .hardness(400)
  .resistance(500)
  .noDrops()
  .noValidSpawns(true)
  .requiresTool(true) // Requires a tool or it won't drop (see tags below)
    .tagBlock('minecraft:mineable/pickaxe') // Need pickaxe
    .tagBlock('minecraft:needs_iron_tool') // Need diamond
  ; 
  
   
  // Creating the bedrock_white
  event.create('bedrock_white')
	  .displayName('Bedrock blanche')
	  .unbreakable()
	  .noDrops()
	  .noValidSpawns(true)
  
  
  // Creating the bedrock_green // <= ERROR OLD BLOCK DO NOT USE
  event.create('bedrock_green')
	  .displayName('BedRock')
	  .unbreakable()
	  .noDrops()
	  .noValidSpawns(true)
  
  
  // Creating the forceField
  event.create('forcefield')
	  .displayName('Un champ de force')
	  .unbreakable()
	  .noDrops()
	  .noValidSpawns(true)
	  .defaultTranslucent()
	  .texture('up', 'kubejs:block/empty')
	  .texture('down', 'kubejs:block/empty')
	  .texture('north', 'kubejs:block/forcefield')
	  .texture('south', 'kubejs:block/forcefield')
	  .texture('east', 'kubejs:block/forcefield')
	  .texture('west', 'kubejs:block/forcefield');
  
  event.create('forcefield_green')
	  .displayName('Un champ de force Vert')
	  .unbreakable()
	  .noDrops()
	  .noValidSpawns(true)
	  .defaultTranslucent()
	  .texture('up', 'kubejs:block/empty')
	  .texture('down', 'kubejs:block/empty')
	  .texture('north', 'kubejs:block/forcefield_green')
	  .texture('south', 'kubejs:block/forcefield_green')
	  .texture('east', 'kubejs:block/forcefield_green')
	  .texture('west', 'kubejs:block/forcefield_green')
	  .mapColor('grass');
	  

  event.create('forcefield_red')  
	  .displayName('Un champ de force Rouge')
	  .unbreakable()
	  .noDrops()
	  .noValidSpawns(true)
	  .defaultTranslucent()
	  .texture('up', 'kubejs:block/empty')
	  .texture('down', 'kubejs:block/empty')
	  .texture('north', 'kubejs:block/forcefield_red')
	  .texture('south', 'kubejs:block/forcefield_red')
	  .texture('east', 'kubejs:block/forcefield_red')
	  .texture('west', 'kubejs:block/forcefield_red')
	  .mapColor('grass');
	  
    // Creating CouchRock => like bed rock but breakable
  event.create('couchrock_white').
  displayName('Roche Noir et Blanche')
  .hardness(40)
  .resistance(8700) 
  .noDrops()
  .noValidSpawns(true)
  .requiresTool(true) // Requires a tool or it won't drop (see tags below)
    .tagBlock('minecraft:mineable/pickaxe') // Need pickaxe
    .tagBlock('minecraft:needs_diamond_tool') // Need diamond
  ;
  
  event.create('couchrock_red').
  displayName('Roche Noir et Blanche')
  .hardness(150)
  .resistance(8700) 
  .noDrops()
  .noValidSpawns(true)
  .requiresTool(true) // Requires a tool or it won't drop (see tags below)
    .tagBlock('minecraft:mineable/pickaxe') // Need pickaxe
    .tagBlock('minecraft:needs_diamond_tool') // Need diamond
  ; 
  
  event.create('couchrock_purple').
  displayName('Roche Noir et Blanche')
  .hardness(150)
  .resistance(8700)
  .noDrops()
  .noValidSpawns(true)
  .requiresTool(true) // Requires a tool or it won't drop (see tags below)
    .tagBlock('minecraft:mineable/pickaxe') // Need pickaxe
    .tagBlock('minecraft:needs_diamond_tool') // Need diamond
  ; 
  
  event.create('couchrock_green').
  displayName('Roche Noir et Blanche')
  .hardness(150)
  .resistance(8700) 
  .noDrops()
  .noValidSpawns(true)
  .requiresTool(true) // Requires a tool or it won't drop (see tags below)
    .tagBlock('minecraft:mineable/pickaxe') // Need pickaxe
    .tagBlock('minecraft:needs_diamond_tool') // Need diamond
  ; 
  
  // Xen Block
  
    event.create('xenmetal').
  displayName('Métal surprenant')
  .hardness(200)
  .resistance(100) 
  .noValidSpawns(true)
  .requiresTool(true) // Requires a tool or it won't drop (see tags below)
    .tagBlock('minecraft:mineable/pickaxe') // Need pickaxe
    .tagBlock('minecraft:needs_diamond_tool') // Need Iron
  ;  
    
    event.create('xenmetalbis').
  displayName('Métal surprenant')
  .hardness(200)
  .resistance(100) 
  .noValidSpawns(true)
  .requiresTool(true) // Requires a tool or it won't drop (see tags below)
    .tagBlock('minecraft:mineable/pickaxe') // Need pickaxe
    .tagBlock('minecraft:needs_iron_tool') // Need Iron
  ; 
  
  event.create('xenmetalreinforced').
  displayName('Métal surprenant renforcée')
  	  .unbreakable()
	  .noDrops()
	  .noValidSpawns(true)
  ; 
  
  event.create('xenmetalslab', "slab").
  displayName('Métal surprenant')
  .hardness(200)
  .resistance(100) 
  .noValidSpawns(true)
  .requiresTool(true) // Requires a tool or it won't drop (see tags below)
  .tagBlock('minecraft:mineable/pickaxe') // Need pickaxe
  .tagBlock('minecraft:needs_diamond_tool') // Need Iron
  .textureAll('kubejs:block/xenmetal')
  ; 
  
  event.create('xenmetalstairs', "stairs").
  displayName('Métal surprenant')
  .hardness(200)
  .resistance(100) 
  .noValidSpawns(true)
  .requiresTool(true) // Requires a tool or it won't drop (see tags below)
  .tagBlock('minecraft:mineable/pickaxe') // Need pickaxe
  .tagBlock('minecraft:needs_diamond_tool') // Need Iron
  .textureAll('kubejs:block/xenmetal')
  ; 
    
  event.create('xenmetalslabbis', "slab").
  displayName('Métal surprenant')
  .hardness(200)
  .resistance(100) 
  .noValidSpawns(true)
  .requiresTool(true) // Requires a tool or it won't drop (see tags below)
  .tagBlock('minecraft:mineable/pickaxe') // Need pickaxe
  .tagBlock('minecraft:needs_diamond_tool') // Need Iron
  .textureAll('kubejs:block/xenmetalbis')
  ; 
  
  event.create('xenmetalstairsbis', "stairs").
  displayName('Métal surprenant')
  .hardness(200)
  .resistance(100) 
  .noValidSpawns(true)
  .requiresTool(true) // Requires a tool or it won't drop (see tags below)
  .tagBlock('minecraft:mineable/pickaxe') // Need pickaxe
  .tagBlock('minecraft:needs_diamond_tool') // Need Iron
  .textureAll('kubejs:block/xenmetalbis')
  ; 
  
  
  event.create('xenrockblack').
  displayName('Roche noir')
  .hardness(20)
  .resistance(10) 
  .noValidSpawns(true)
  .requiresTool(true) // Requires a tool or it won't drop (see tags below)
    .tagBlock('minecraft:mineable/pickaxe') // Need pickaxe
    .tagBlock('minecraft:needs_stone_tool') // Need Stone
  ; 
  
  event.create('xenrockgray').
  displayName('Roche Rouge')
  .hardness(20)
  .resistance(10) 
  .noValidSpawns(true)
  .requiresTool(true) // Requires a tool or it won't drop (see tags below)
    .tagBlock('minecraft:mineable/pickaxe') // Need pickaxe
    .tagBlock('minecraft:needs_stone_tool') // Need Stone
  ; 
  
  event.create('xenrockred').
  displayName('Roche Rouge')
  .hardness(20)
  .resistance(10) 
  .noValidSpawns(true)
  .requiresTool(true) // Requires a tool or it won't drop (see tags below)
    .tagBlock('minecraft:mineable/pickaxe') // Need pickaxe
    .tagBlock('minecraft:needs_stone_tool') // Need Stone
  ; 
  
  
  // Unbreakable Glass
  event.create('reinforcedglass').
  displayName('Verre renforcée')
  .unbreakable()
  .noDrops()
  .defaultCutout()
  .noValidSpawns(true)
    .glassSoundType()
  ; 
  
     event.create('green_stained_reinforcedglass').
  displayName('Verre renforcée')
  .unbreakable()
  .noDrops()
  .defaultTranslucent()
  .noValidSpawns(true)
    .glassSoundType()
  ; 
  
  event.create('red_stained_reinforcedglass').
  displayName('Verre renforcée')
  .unbreakable()
  .noDrops()
  .defaultTranslucent()
  .noValidSpawns(true)
    .glassSoundType()
  ; 
  
  event.create('purple_stained_reinforcedglass').
  displayName('Verre renforcée')
  .unbreakable()
  .noDrops()
  .defaultTranslucent()
  .noValidSpawns(true)
    .glassSoundType()
  ; 
    
	
  event.create('lime_stained_reinforcedglass').
  displayName('Verre renforcée')
  .unbreakable()
  .noDrops()
  .defaultTranslucent()
  .noValidSpawns(true)
    .glassSoundType()
  ; 
	
  event.create('blue_stained_reinforcedglass').
  displayName('Verre renforcée')
  .unbreakable()
  .noDrops()
  .defaultTranslucent()
  .noValidSpawns(true)
  .glassSoundType()
  ; 
  
  // Hard Breakable Glass
  event.create('green_stained_inforcedglass').
  displayName('Verre renforcée')
  .hardness(150)
  .resistance(8700) 
  .noDrops()
  .noValidSpawns(true)
  .defaultTranslucent()
  .requiresTool(true) // Requires a tool or it won't drop (see tags below)
    .tagBlock('minecraft:mineable/pickaxe') // Need pickaxe
    .tagBlock('minecraft:needs_diamond_tool') // Need diamond
  ; 
   
  event.create('lime_stained_inforcedglass').
  displayName('Verre renforcée')
  .hardness(150)
  .resistance(8700) 
  .noDrops()
  .defaultTranslucent()
  .noValidSpawns(true)
  .requiresTool(true) // Requires a tool or it won't drop (see tags below)
    .tagBlock('minecraft:mineable/pickaxe') // Need pickaxe
    .tagBlock('minecraft:needs_diamond_tool') // Need diamond
  ; 
  

  
   // Hard Breakable White Concret
  event.create('white_inforced_concret').
  displayName('Béton Blanc')
  .hardness(75)
  .resistance(8700) 
  .noDrops()
  .noValidSpawns(true)
  .requiresTool(true) // Requires a tool or it won't drop (see tags below)
    .tagBlock('minecraft:mineable/pickaxe') // Need pickaxe
    .tagBlock('minecraft:needs_diamond_tool') // Need diamond
  ; 
  
  
   // Hard Breakable White Concret
  event.create('white_reinforced_concret').
  displayName('Béton Blanc')
  .unbreakable()
  .noDrops()
  .noValidSpawns(true)
  ; 
  
  
  
  
})
