
// Creating null material ( => No Amor Point )
/*
ItemEvents.armorTierRegistry(event => {
	
	// 'nonProtecting' is a material (like Iron) but just for display data (no armor point added)
  event.add('nonProtecting', tier => {
    tier.durabilityMultiplier = 250 // Each slot will be multiplied with [13, 15, 16, 11]
    tier.slotProtections = [0, 0, 0, 0] // Slot indicies are [FEET, LEGS, BODY, HEAD]
    tier.enchantmentValue = 9
    tier.equipSound = 'minecraft:item.armor.equip_iron'
    tier.repairIngredient = '#forge:ingots/iron'
    tier.toughness = 0 // diamond has 2, netherite 3
    tier.knockbackResistance = 0
  })
})*/

let notAllowedUsage = 0;

console.info('Items PP2 : V1.0.0')
// Creating items
StartupEvents.registry('item', event => {
	/** Marshmallow now exist **/
	event.create('marshmallow').food(food => {
		food
			.hunger(6)
			.saturation(6)
			.fastToEat()
			.eaten(ctx => {
				let player = ctx.player
				
				// Check if player has tag "labubu"
				if (player.getTags().contains('labubu')) {
					//player.tell("nom nom");

					ctx.level.runCommandSilent(`effect give ${player.username} minecraft:luck 60 0`);
				}
			})
	}).displayName('Guimauve').tooltip('§5Une douceur sucrée !');




  // Used For TP To Dim:Bolt
  event.create('flint_and_quart').unstackable().displayName('Metal et Quartz').tooltip('§5Je n\'y toucherais pas si j\'étais toi ...').
  use((level, player, hand) => 
  {
		
	// Usable only if Xen
	if (player.getTags().contains("xen")) {		
		return true;
	}
	
	
	// Get Player Data
	let x = Math.floor(player.x)
	let y = Math.floor(player.y)
	let z = Math.floor(player.z)
	const dim = player.getLevel().dimension;
	
	
	// Effects
	const duration = notAllowedUsage + 6;
	level.runCommandSilent(`execute in ${dim} run playsound entity.lightning_bolt.impact hostile @a[x=${x},y=${y},z=${z},distance=..20] ${x} ${y} ${z} 1 2`);
	
	level.runCommandSilent(`execute if block ${x} ${y} ${z} minecraft:air run setblock ${x} ${y} ${z} minecraft:fire[age=0]`);
	level.runCommandSilent(`effect give ${player.username} minecraft:poison ${duration} 6`);
	
	// Remove Item 
	/*		0 = MAINHAND 		1 = OFFHAND		2 = FEET		3 = LEGS		4 = CHEST		5 = HEAD	*/
	if (hand == "MAIN_HAND") {
		player.drop(Item.of(player.mainHandItem, 1), false)

		player.setItemSlot(0, "minecraft:air")
	}
	else if (hand == "OFF_HAND") {
		player.drop(Item.of(player.offHandItem, 1), false)

		player.setItemSlot(1, "minecraft:air")
	}
	
	if (notAllowedUsage > 5) {
		player.tell("Je vais finir par y passer ...");
		
		
		// Adding second effect
		const durationHit = notAllowedUsage -4;
		level.runCommandSilent(`effect give ${player.username} minecraft:instant_damage ${durationHit} 0`);
		
	} else {
		player.tell("Je ne devrais pas insister ...");
	}



	notAllowedUsage = notAllowedUsage + 1;
	return false;
  })
  ;
     
  // Used For labubutalk to talk
    event.create('labubutalk').unstackable().displayName('Un appareil')
  .use((level, player, hand) => 
  {
	// Usable if xen
	if (player.getTags().contains('xen')) {
		
		let x = Math.floor(player.x)
		let y = Math.floor(player.y)
		let z = Math.floor(player.z)
		const dim = player.getLevel().dimension;


		if (player.pitch < -25) 
		{
			level.runCommandSilent(`execute in ${dim} run playsound kubejs:entity.pp2.etonnant1 hostile @a[x=${x},y=${y},z=${z},distance=..20] ${x} ${y} ${z}`);
			
		} else if (player.pitch < 25) 
		{
			level.runCommandSilent(`execute in ${dim} run playsound kubejs:entity.pp2.etonnant2 hostile @a[x=${x},y=${y},z=${z},distance=..20] ${x} ${y} ${z}`);
			
		} else {
			level.runCommandSilent(`execute in ${dim} run playsound kubejs:entity.pp2.etonnant3 hostile @a[x=${x},y=${y},z=${z},distance=..20] ${x} ${y} ${z}`);
		}
	
	}
	// Usable if labubu
	else if (player.getTags().contains('labubu')) {
		
		let x = Math.floor(player.x)
		let y = Math.floor(player.y)
		let z = Math.floor(player.z)
		const dim = player.getLevel().dimension;


		if (player.pitch < -25) 
		{
			level.runCommandSilent(`execute in ${dim} run playsound kubejs:entity.pp2.tam player @a[x=${x},y=${y},z=${z},distance=..20] ${x} ${y} ${z}`);
			
		} else if (player.pitch < 25) 
		{
			level.runCommandSilent(`execute in ${dim} run playsound kubejs:entity.pp2.po player @a[x=${x},y=${y},z=${z},distance=..20] ${x} ${y} ${z}`);
			
		} else {
			level.runCommandSilent(`execute in ${dim} run playsound kubejs:entity.pp2.pi player @a[x=${x},y=${y},z=${z},distance=..20] ${x} ${y} ${z}`);
		}
	}
	return true;
  })
  ;
	
	
	  
  /** Emerald of company **/
  // Used for specific trading
  event.create('company_emerald').displayName('Émeraude de Cattaga™');
	
  /** Access Card **/
  event.create('badge_r').displayName('Badge').tooltip('§5Un badge d\'acces rouge').tag('pp2:badge').unstackable() ;
  event.create('badge_b').displayName('Badge').tooltip('§5Un badge d\'acces bleu').tag('pp2:badge').unstackable() ;
  event.create('badge_g').displayName('Badge').tooltip('§5Un badge d\'acces vert').tag('pp2:badge').unstackable() ;
  event.create('badge_p').displayName('Badge').tooltip('§5Un badge d\'acces violet').tag('pp2:badge').unstackable() ;
  
  event.create('badge_represantant').displayName('Badge de représentant').tooltip('§5Un badge d\'acces').tag('pp2:badge').unstackable() ;
  event.create('badge_direction').displayName('Badge de la direction').tooltip('§5Un badge d\'acces').tag('pp2:badge').unstackable() ;
  
  
  event.create('xen_ingot').displayName('Lingot').tooltip('§5Un métal surprenant')
  .use((level, player, hand) => 
  {
	// Sounds if xen
	if (player.getTags().contains('xen')) {
		
		let x = Math.floor(player.x)
		let y = Math.floor(player.y)
		let z = Math.floor(player.z)
		const dim = player.getLevel().dimension;

		if (player.pitch > 10) 
		{
			level.runCommandSilent(`execute in ${dim} run playsound kubejs:entity.pp2.donneenergie hostile @a[x=${x},y=${y},z=${z},distance=..40] ${x} ${y} ${z}`);
		} else {			
			level.runCommandSilent(`execute in ${dim} run playsound kubejs:entity.pp2.rendenergie hostile @a[x=${x},y=${y},z=${z},distance=..40] ${x} ${y} ${z}`);
		} 
	}
	return true;
  });
  
  
  event.create('xeneye').displayName('Oeil étrange').tooltip('§5Un genre d\'oeil')
  .use((level, player, hand) => 
  {
	// Sounds if xen
	if (player.getTags().contains('xen')) {
		
		let x = Math.floor(player.x)
		let y = Math.floor(player.y)
		let z = Math.floor(player.z)
		const dim = player.getLevel().dimension;

		if (player.pitch > 10) 
		{
			level.runCommandSilent(`execute in ${dim} run playsound kubejs:entity.pp2.menace1 hostile @a[x=${x},y=${y},z=${z},distance=..40] ${x} ${y} ${z}`);
		} else {			
			level.runCommandSilent(`execute in ${dim} run playsound kubejs:entity.pp2.menace2 hostile @a[x=${x},y=${y},z=${z},distance=..40] ${x} ${y} ${z}`);
		} 
	}
	return true;
  });
  
  
  event.create('xendoubleeye').displayName('Yeux étranges').tooltip('§5Une drôle paire d\'yeux').use((level, player, hand) => 
  {
	// Sounds if xen
	if (player.getTags().contains('xen')) {
		
		let x = Math.floor(player.x)
		let y = Math.floor(player.y)
		let z = Math.floor(player.z)
		const dim = player.getLevel().dimension;

		if (player.pitch > 10) 
		{
			level.runCommandSilent(`execute in ${dim} run playsound kubejs:entity.pp2.menace1 hostile @a[x=${x},y=${y},z=${z},distance=..40] ${x} ${y} ${z}`);
		} else {			
			level.runCommandSilent(`execute in ${dim} run playsound kubejs:entity.pp2.menace2 hostile @a[x=${x},y=${y},z=${z},distance=..40] ${x} ${y} ${z}`);
		} 
	}
	return true;
  });

  event.create('stapler', "sword").displayName('Agrafeuse').tier('stone');

	event.create('mustard').food(food => {
		food
		  .hunger(1)
		  .saturation(1) // This value does not directly translate to saturation points gained
		  // The real value can be assumed to be:
		  // min(hunger * saturation * 2 + saturation, foodAmountAfterEating)
		  .effect('minecraft:absorption', 1200, 0, 1.0)
		  // .effect(effectId, duration, amplifier, probability) Duration in tick (20 tick per sec)
		  .alwaysEdible() // Like golden apples
		  .fastToEat() // Like dried kelp
	  })
  
})
