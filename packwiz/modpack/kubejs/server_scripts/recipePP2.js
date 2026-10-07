
console.info('Remove Recipe PP2 : V1.0.0')

// Listen for the "recipes" server event.
ServerEvents.recipes(event => {
	// Remove Jar because make immersive portal fail
	event.remove({ output: 'supplementaries:jar' });
	event.remove({ output: 'supplementaries:jar_boat' });

	event.remove({ output: 'waystones:red_sharestone' });

		
	event.shapeless(
	  Item.of('kubejs:marshmallow', 1), 
	  [
		'minecraft:egg', 	       // arg 2: the array of inputs
		'2x minecraft:sugar'
	  ]
	);
	
	event.shaped(
	  Item.of('kubejs:labubutalk', 1), 
	  [
		'A A',
		' A ', // arg 2: the shape (array of strings)
		'A A'
	  ],
	  {
		A: 'minecraft:iron_ingot'
	  }
	);
	
	event.shaped(
	  Item.of('kubejs:labubutalk', 1), 
	  [
		'A A',
		' A ', // arg 2: the shape (array of strings)
		'A A'
	  ],
	  {
		A: 'kubejs:xen_ingot'
	  }
	);
});
