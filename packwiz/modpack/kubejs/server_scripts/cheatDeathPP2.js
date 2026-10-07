const Identifier = Java.loadClass('net.minecraft.class_2960') // Identifier
const BlockPos = Java.loadClass('net.minecraft.class_2338')   // BlockPos

/* 
Used in RolePlay server,

Player will do some action that will increase count value,
A clock will call command to decrease count every now and then

If Count value drop to 0 all player that die will be reborn in another dimension

*/

console.info(`ServerScript : CheatDeath is now UP `)
console.info(`CD : Indicator bloc ${indicatorBloc_X} ${indicatorBloc_Y} ${indicatorBloc_Z}`)
console.info(`CD : Save bloc  ${saveBloc_X} ${saveBloc_Y} ${saveBloc_Z}  `)
console.info(`CD : Count Value : ${count}  `)



let count = undefined;

let reviveCost = 53;
let labubuReviveCost = 150 + 0;

let lowerStep = 1;
let higherStep = 1000;

let spawnInZeroDim = false;

let spawnBase_dimension = 'minecraft:overworld' ;
let spawnBaseX = 0;
let spawnBaseY = -55;
let spawnBaseZ = 0;
let spawnBaseAngle = 80;


let spawnZero_dimension = 'immersive_portals:alternate3' ;
let spawnZeroX = -3486;
let spawnZeroY = 31;
let spawnZeroZ = -140;
let spawnZeroAngle = 180;


let indicatorBloc_X = -3471;
let indicatorBloc_Y = 0;
let indicatorBloc_Z = -146;

let saveBloc_X = -3471;
let saveBloc_Y = 1;
let saveBloc_Z = -146;


let xen_dimension = 'minecraft:the_nether' ;
let xenX = -1176;
let xenY = 53;
let xenZ = -75;
let xenAngle = 180;


// rotation = 0 -> 15
// Only manage good 0 4 8 12 
function displayCount(server) {
	// Welcome room
	displayCountDetailed(server, 'minecraft:overworld' , -3419, 76,-162, 4);


	// Working Place
	displayCountDetailed(server, 'minecraft:overworld' , -3391, 101, -158, 12);
	displayCountDetailed(server, 'minecraft:overworld' , -3471, 5, -148, 12);

	// motor room
	displayCountDetailed(server, 'minecraft:overworld' , -3377, -49, -169, 0);

	displayCountDetailed(server, 'minecraft:overworld' , -3372, -49, -156, 8);
	
/*
	displayCountDetailed(server, 12 , -57 , 3 , 4);

	displayCountDetailed(server, -25 , -48 , 25 , 8);
	
	displayCountDetailed(server, -20 , -57 , 10 , 12);*/
	
	
	
}

function displayCountDetailed(server, dimensionName, x , y , z, rotation) {
		
	// Convertir en string
	
	let str = "0";
	if (count != undefined) {
		str = String(count);
	}
	
	// Ajouter des zéros devant si moins de 5 chiffres
	while (str.length < 5) {
		str = "0" + str;
	}

	// Prendre les 5 premiers caractères
	let number1 = parseInt(str.charAt(0));
	let number2 = parseInt(str.charAt(1));
	let number3 = parseInt(str.charAt(2));
	let number4 = parseInt(str.charAt(3));
	let number5 = parseInt(str.charAt(4));
	
	//console.log(`Count ${count} > ${number1} ${number2} ${number3} ${number4} ${number5} `);

	//console.log(`displayCount : ${x} ${y} ${z} ${rotation}`);
								

	
	if (rotation == 0) {
		server.runCommandSilent(
			`execute in ${dimensionName} run fill ${x} ${y} ${z} ${x+4} ${y} ${z} air`
		);
		server.runCommandSilent(
			`execute in ${dimensionName} run setblock ${x} ${y} ${z} minecraft:white_banner[rotation=${rotation}]${bannerPattern[number1]} replace`
		);
		server.runCommandSilent(
			`execute in ${dimensionName} run setblock ${x+1} ${y} ${z} minecraft:white_banner[rotation=${rotation}]${bannerPattern[number2]} replace`
		);
		server.runCommandSilent(
			`execute in ${dimensionName} run setblock ${x+2} ${y} ${z} minecraft:white_banner[rotation=${rotation}]${bannerPattern[number3]} replace`
		);
		server.runCommandSilent(
			`execute in ${dimensionName} run setblock ${x+3} ${y} ${z} minecraft:white_banner[rotation=${rotation}]${bannerPattern[number4]} replace`
		);
		server.runCommandSilent(
			`execute in ${dimensionName} run setblock ${x+4} ${y} ${z} minecraft:white_banner[rotation=${rotation}]${bannerPattern[number5]} replace`
		);
	} else if (rotation == 8) {
		server.runCommandSilent(
			`execute in ${dimensionName} run fill ${x} ${y} ${z} ${x-4} ${y} ${z} air`
		);
		server.runCommandSilent(
			`execute in ${dimensionName} run setblock ${x} ${y} ${z} minecraft:white_banner[rotation=${rotation}]${bannerPattern[number1]} replace`
		);
		server.runCommandSilent(
			`execute in ${dimensionName} run setblock ${x-1} ${y} ${z} minecraft:white_banner[rotation=${rotation}]${bannerPattern[number2]} replace`
		);
		server.runCommandSilent(
			`execute in ${dimensionName} run setblock ${x-2} ${y} ${z} minecraft:white_banner[rotation=${rotation}]${bannerPattern[number3]} replace`
		);
		server.runCommandSilent(
			`execute in ${dimensionName} run setblock ${x-3} ${y} ${z} minecraft:white_banner[rotation=${rotation}]${bannerPattern[number4]} replace`
		);
		server.runCommandSilent(
			`execute in ${dimensionName} run setblock ${x-4} ${y} ${z} minecraft:white_banner[rotation=${rotation}]${bannerPattern[number5]} replace`
		);
	} else if (rotation == 12) {
		server.runCommandSilent(
			`execute in ${dimensionName} run fill ${x} ${y} ${z} ${x} ${y} ${z-4} air`
		);
		server.runCommandSilent(
			`execute in ${dimensionName} run setblock ${x} ${y} ${z} minecraft:white_banner[rotation=${rotation}]${bannerPattern[number1]} replace`
		);
		server.runCommandSilent(
			`execute in ${dimensionName} run setblock ${x} ${y} ${z-1} minecraft:white_banner[rotation=${rotation}]${bannerPattern[number2]} replace`
		);
		server.runCommandSilent(
			`execute in ${dimensionName} run setblock ${x} ${y} ${z-2} minecraft:white_banner[rotation=${rotation}]${bannerPattern[number3]} replace`
		);
		server.runCommandSilent(
			`execute in ${dimensionName} run setblock ${x} ${y} ${z-3} minecraft:white_banner[rotation=${rotation}]${bannerPattern[number4]} replace`
		);
		server.runCommandSilent(
			`execute in ${dimensionName} run setblock ${x} ${y} ${z-4} minecraft:white_banner[rotation=${rotation}]${bannerPattern[number5]} replace`
		);
	
	}
	
	else {
		server.runCommandSilent(
			`execute in ${dimensionName} run fill ${x} ${y} ${z} ${x} ${y} ${z+4} air`
		);
		server.runCommandSilent(
			`execute in ${dimensionName} run setblock ${x} ${y} ${z} minecraft:white_banner[rotation=${rotation}]${bannerPattern[number1]} replace`
		);
		server.runCommandSilent(
			`execute in ${dimensionName} run setblock ${x} ${y} ${z+1} minecraft:white_banner[rotation=${rotation}]${bannerPattern[number2]} replace`
		);
		server.runCommandSilent(
			`execute in ${dimensionName} run setblock ${x} ${y} ${z+2} minecraft:white_banner[rotation=${rotation}]${bannerPattern[number3]} replace`
		);
		server.runCommandSilent(
			`execute in ${dimensionName} run setblock ${x} ${y} ${z+3} minecraft:white_banner[rotation=${rotation}]${bannerPattern[number4]} replace`
		);
		server.runCommandSilent(
			`execute in ${dimensionName} run setblock ${x} ${y} ${z+4} minecraft:white_banner[rotation=${rotation}]${bannerPattern[number5]} replace`
		);
	
	}
	
}

function updateIndicatorBloc(server) {
    let blocId = 'minecraft:green_wool';
    if (count <= reviveCost){
        blocId = 'minecraft:red_wool';    
    } else if (count <= reviveCost * 2 ) {
        blocId = 'minecraft:orange_wool';
    }
        
    server.runCommandSilent(
            `execute in ${spawnBase_dimension} run setblock ${indicatorBloc_X} ${indicatorBloc_Y} ${indicatorBloc_Z} ${blocId}`
        );

}

function updateSpawnZeroBloc(server) {

	if (count <= reviveCost){
		server.runCommandSilent(`execute in ${spawnZero_dimension} run fill -3347 35 -114 -3346 35 -115 kubejs:bedrock_white`);
		server.runCommandSilent(`execute in ${spawnZero_dimension} run fill -3344 36 -114 -3344 41 -115 minecraft:polished_blackstone_bricks`);		

	} else {
		server.runCommandSilent(`execute in ${spawnZero_dimension} run fill -3347 35 -114 -3346 35 -115 minecraft:air`);		
		server.runCommandSilent(`execute in ${spawnZero_dimension} run fill -3344 36 -114 -3344 41 -115 minecraft:sea_lantern`);		

	}

	server.runCommandSilent(`execute in ${spawnZero_dimension} kill entity @e[x=-3344,y=40,z=-114,type=immersive_paintings:graffiti,distance=..3]`);		
	server.runCommandSilent(`execute in ${spawnZero_dimension} summon immersive_paintings:graffiti -3344.03 39.00 -115.00 {Motion: [0.0d, 0.0d, 0.0d], Frame: "immersive_paintings:none", Facing: 1b, Invulnerable: 1b, Material: "immersive_paintings:none", Air: 300s, OnGround: 0b, "coffins:recovery_compass": {}, PortalCooldown: 0, Rotation: 0, FallDistance: 0.0f, Motive: "immersive_paintings:jimauve/fleche", Fire: -1s, TileY: 38, TileX: -3345, TileZ: -115, BalmData: {}}
	`);		


}

function updateSaveBloc(server) {
	
	// Set The SaveBloc as a command Bloc if not
	server.runCommandSilent(
		`execute unless block ${saveBloc_X} ${saveBloc_Y} ${saveBloc_Z} minecraft:command_block run setblock ${saveBloc_X} ${saveBloc_Y} ${saveBloc_Z} minecraft:command_block`
	);

    // Set The Command of SaveBloc as the counter
	server.runCommandSilent(
		`execute in minecraft:overworld run data merge block ${saveBloc_X} ${saveBloc_Y} ${saveBloc_Z} {Command:"${count}"}`
	);
	
}

function countChanged(server) {
	updateSaveBloc(server);
	
	updateIndicatorBloc(server);
	
	displayCount(server);
	
	spawnInZeroDim = (count < reviveCost);

	updateSpawnZeroBloc(server);
}

ServerEvents.customCommand('countLower', event => {    	
	if (event.player && !event.player.op) {
		event.player.tell('Oh Chacal ! JOUE RP UN PEU !');
		return;
	}
	
	if (event.player) {
		event.player.tell('Command allowed only from CommandBloc');
	} else {
		if (count > 0 ) {
			count = count - lowerStep;
			
			if (count < 0 ) {
				count = 0;
			}
		}
		
		countChanged(event.server);
		
	}
})

ServerEvents.customCommand('countHigher', event => {    
	if (event.player && !event.player.op) {
		event.player.tell('Oh Chacal ! JOUE RP UN PEU !');
		return;
	}
	
	if (event.player) {
		event.player.tell('Command allowed only from CommandBloc');
	} else {
		count = count + higherStep
		
		countChanged(event.server);
	}
})

ServerEvents.customCommand('count', event => {    	
	if (event.player && !event.player.op) {
		event.player.tell('Oh Chacal ! JOUE RP UN PEU !');
		return;
	}
	
	countChanged(event.server);
	
	if (event.player) {
		event.player.tell('Current Count : ' + count);
	} 
})


ServerEvents.customCommand('countSet', event => {    	
	if (event.player && !event.player.op) {
		event.player.tell('Oh Chacal ! JOUE RP UN PEU !');
		return;
	}
	
	if (event.arguments.length >= 0) {

		let newValue = parseInt(event.arguments[0]);

		if (!isNaN(newValue) && newValue >= 0) {
			count = newValue;
			countChanged(event.server);
		} else {
			event.player.tell('Nombre invalide.');
			return;
		}
	}
		
	if (event.player) {
		event.player.tell('Current Count : ' + count);
	} 
})

// command /countSet [value] to set the counter
ServerEvents.commandRegistry(event => {
    const { commands: Commands, arguments: Arguments } = event;
    event.register(
      Commands.literal('countSet')
        .requires(s => s.hasPermission(4))
        .then(
          Commands.argument('value', Arguments.INTEGER.create(event)) 
            .executes(ctx => countCommand(ctx.source.getServer() , ctx.source.player , Arguments.INTEGER.getResult(ctx, 'value')))
        )
    );
  
    // Real setCount function
    const countCommand = (server , player, value) => {
		
		if (event.player && !event.player.op) {
			event.player.tell('Oh Chacal ! JOUE RP UN PEU !');
			return;
		}
		
		//console.log("/countSet " + value + " STARTED from " + player.name + " current count : " + count);
		
		if (value < 0) {
			count = 0;
		}
		else {
			count = value;
		}
		
		if (player) {
			player.tell('Count is now : ' + count);
		}
		
	  	countChanged(server);
		
	  	//console.log("/countSet " + value + " ENDED from " + player.name + " current count : " + count);
		return 1;
    };
});

// command /countAdd [value] to add a value (value can be negative)
ServerEvents.commandRegistry(event => {
    const { commands: Commands, arguments: Arguments } = event;
    event.register(
      Commands.literal('countAdd')
        .requires(s => s.hasPermission(4))
        .then(
          Commands.argument('value', Arguments.INTEGER.create(event)) 
            .executes(ctx => countCommand(ctx.source.getServer() , ctx.source.player , Arguments.INTEGER.getResult(ctx, 'value')))
        )
    );
  
    // Real setCount function
    const countCommand = (server , player, value) => {
		
		if (event.player && !event.player.op) {
			event.player.tell('Oh Chacal ! JOUE RP UN PEU !');
			return;
		}
		
		//console.log("/countAdd " + value + " STARTED from " + player.name + " current count : " + count);


		count = count + value;
		
		if (count < 0) {
			count = 0;
		}
		
		if (player) {
			player.tell('Count is now : ' + count);
		}
		
	  	countChanged(server);
		//console.log("/countAdd " + value + " ENDED from " + player.name + " current count : " + count);

	  	return 0
    };
});


// ** ************************** ** //
// Loading Count at Server Start 

// Function find saveElement
function loadFromSaveBloc(server, level, player) {
	//	let level = event.server.getLevel("minecraft:overworld")
	//console.log("level" ,level);
	//console.log("entities" ,level.getEntities());
	
	
	// Set The SaveBloc as a command Bloc if not
	server.runCommandSilent(
		`execute unless block ${saveBloc_X} ${saveBloc_Y} ${saveBloc_Z} minecraft:command_block run setblock ${saveBloc_X} ${saveBloc_Y} ${saveBloc_Z} minecraft:command_block`
	);

	
	let block = level.getBlock(new BlockPos(saveBloc_X,saveBloc_Y,saveBloc_Z))
	
	if (!block) {
		//console.log(`loadFromSaveBloc ${saveBloc_X} ${saveBloc_Y} ${saveBloc_Z} : Don't exist `);
				
		if (player) {
			player.tell(`loadFromSaveBloc ${saveBloc_X} ${saveBloc_Y} ${saveBloc_Z} : Don't exist`);
		}
		return;
	}
	
	if (!block.entityData) {
		//console.log(`loadFromSaveBloc ${saveBloc_X} ${saveBloc_Y} ${saveBloc_Z} : Has no entityData `);
						
		if (player) {
			player.tell(`loadFromSaveBloc ${saveBloc_X} ${saveBloc_Y} ${saveBloc_Z} : Has no entityData`);
		}
		return;
	}
	
	
	let commandValue = 0;
	if (block.entityData.Command) {
		commandValue = parseInt(block.entityData.Command)
	} else {
		//console.log(`loadFromSaveBloc ${saveBloc_X} ${saveBloc_Y} ${saveBloc_Z} : Has no entityData.Command \n ${block.entityData.toString()}`);
				
		if (player) {
			player.tell(`loadFromSaveBloc ${saveBloc_X} ${saveBloc_Y} ${saveBloc_Z} : Has no entityData.Command`);
		}
	}
	 
	if (isNaN(commandValue)) {
		count = 0
	}
	else {
		count = commandValue
	}
	
	
	if (count < 0) {
		count = 0 
	}
	
	countChanged(server)
	
	//console.log(`loadFromSaveBloc ${saveBloc_X} ${saveBloc_Y} ${saveBloc_Z} : Loaded Count from entityData.Command ${count}`);
			
	if (player) {
		player.tell(`loadFromSaveBloc ${saveBloc_X} ${saveBloc_Y} ${saveBloc_Z} : Loaded Count from entityData.Command ${count}`);
	}		
}

// **********************************************
// Server Started
// Get The Number From saved value
ServerEvents.loaded(event => {
	
	
	console.log(" -------------------------------------------------------------- ServerScript : CheatDeath ServerEvents.loaded ---------------------------------------------------------- ");
	
	if (count == undefined) {
		loadFromSaveBloc(event.server, event.server.getLevel("minecraft:overworld") );
	}
	
	console.log(" -------------------------------------------------------------- ServerScript : CheatDeath ServerEvents.loaded ---------------------------------------------------------- ");
		
	
});


ServerEvents.customCommand('loadCounterFromSaveBloc', event => {
	
	if (event.player && !event.player.op) {
		return;
	}
	
    loadFromSaveBloc( event.server , event.server.getLevel("minecraft:overworld"), event.player );	
})



let xenBlocKey = BlockPos(xenX, xenY, xenZ)

let zeroBlocKey = BlockPos(spawnZeroX, spawnZeroY, spawnZeroZ)

// **********************************************
// When player die, if spawnInZeroDim == true and respawnpoint not in good dim, force spawn in dim
EntityEvents.death(event => {
    if (!event.entity.isPlayer()) {
        return;
    }
	
	let player = event.entity;
	const respawnDimension = event.entity.getRespawnDimension().location().toString();


	// Check if player has tag "xen" => respawn in xen world
	if (player.getTags().contains('xen')) {
			if (respawnDimension != xen_dimension) {

				const xenDimensionKey = event.server.getLevel(xen_dimension).dimensionKey;
				
				player.setRespawnPosition(
					xenDimensionKey,                  		// dimension
					xenBlocKey,							// position
					xenAngle,                        // angle
					true,                                  // forced
					false                                  // show message
				)
			
		}
		
		return;
	}
	
	
	
	// Check if player has tag "labubu" => respawn on self*
	if (player.getTags().contains('labubu')) {

		const x = player.position().x();
		const y = player.position().y()+ 1 ;
		const z = player.position().z();
		const dim = player.getLevel().dimension;
		const dimKey = event.server.getLevel(dim).dimensionKey;
		
		player.setRespawnPosition(
			dimKey,           // dimension
			BlockPos(x,y,z),	                   // position
			180,             // angle
			true,            // forced
			false            // show message
		)
		
		return;
	}
	
	
	// Normal player
	if (spawnInZeroDim) {
		
		const respawnDimension = event.entity.getRespawnDimension().location().toString();
		
		if (respawnDimension != spawnZero_dimension) {
					
			const zeroDimensionKey = event.server.getLevel(spawnZero_dimension).dimensionKey;
			
			player.setRespawnPosition(
				zeroDimensionKey,                  // dimension
				zeroBlocKey,					   // position
				spawnZeroAngle,                    // angle
				true,                              // forced
				false                              // show message
			)
		}
	
	} 


});

// When player respawn

PlayerEvents.respawned(event => {
	if (!event.entity.isPlayer()) {
        return;
    }
	
	let player = event.entity;
	const respawnDimension = event.entity.getRespawnDimension().location().toString();

	// Check if player has tag "nopurgatoire" => no move
	if (player.getTags().contains('xen')) {
			if (respawnDimension != xen_dimension) {

				const xenDimensionKey = event.server.getLevel(xen_dimension).dimensionKey;
				
				player.setRespawnPosition(
					xenDimensionKey,                  		// dimension
					xenBlocKey,							// position
					xenAngle,                        // angle
					true,                                  // forced
					false                                  // show message
				)
			}
		
		
		return;
	}
	
	
	const x = player.position().x();
	const y = player.position().y()+ 1 ;
	const z = player.position().z();
	
	x = Math.floor(x);
	y = Math.floor(y);
	z = Math.floor(z);
	
	const dim = player.getLevel().dimension;
		
		
    // Check if player has tag "labubu" => respawn on self
	if (player.getTags().contains('labubu')) {

		const dimKey = event.server.getLevel(dim).dimensionKey;
		
		player.setRespawnPosition(
			dimKey,           // dimension
			BlockPos(x,y,z),	                   // position
			180,             // angle
			true,            // forced
			false            // show message
		)
		
		//Play sound
		event.server.runCommandSilent(`execute in ${dim} run playsound kubejs:entity.pp2.pipotam player @a[x=${x},y=${y},z=${z},distance=..10] ${x} ${y} ${z} 1 0.5 0`);

        // Particule 	
		event.server.runCommandSilent(`execute in ${dim} run particle totem_of_undying ${x} ${y} ${z} 0 1 0 12 30`);

		//add Effect
		event.server.runCommandSilent(`effect give ${player.getName()} minecraft:fire_resistance 30 3`);
        event.server.runCommandSilent(`effect give ${player.getName()} minecraft:slowness 30 5`);
        event.server.runCommandSilent(`effect give ${player.getName()} minecraft:glowing 60 5`);

		// count reduce
		count = count - labubuReviveCost;
		
		if (count < 0 ) {
			count = 0;
		}
		countChanged(event.server);
		
		return;
	}
	
	if (spawnInZeroDim || count < reviveCost) {

		if (respawnDimension != xen_dimension) {
		
			const zeroDimensionKey = event.server.getLevel(spawnZero_dimension).dimensionKey;
			
			player.setRespawnPosition(
				zeroDimensionKey,                  	// dimension
				zeroBlocKey,						// position
				spawnZeroAngle,                     // angle
				true,                               // forced
				false                               // show message
			)
		}
		
		
		//Play sound
		event.server.runCommandSilent(`execute in ${dim} run playsound kubejs:entity.pp2.revive ambient @a[x=${x},y=${y},z=${z},distance=..10] ${x} ${y} ${z} 1 1 0`);

	} else if (respawnDimension != spawnZero_dimension) {
		
		count = count - reviveCost
		countChanged(event.server);
		
	}
})



// BANNER BLOC
// Example : /setblock 4 -58 20 minecraft:white_banner[rotation=2]{rotation:90,Patterns:[{Pattern:bs,Color:15},{Pattern:ls,Color:15},{Pattern:ts,Color:15},{Pattern:rs,Color:15},{Pattern:bo,Color:0}]} replace
// add : "setblock ~ ~1 ~ minecraft:white_banner[rotation=7]" Before and : " replace" at the end
const bannerPattern= [
"{Patterns:[{Pattern:bs,Color:15},{Pattern:ls,Color:15},{Pattern:ts,Color:15},{Pattern:rs,Color:15},{Pattern:bo,Color:0}]}", // Number 0

"{Patterns:[{Pattern:cs,Color:15},{Pattern:tl,Color:15},{Pattern:cbo,Color:0},{Pattern:bs,Color:15},{Pattern:bo,Color:0}]}", // Number 1

"{Patterns:[{Pattern:ts,Color:15},{Pattern:mr,Color:0},{Pattern:bs,Color:15},{Pattern:dls,Color:15},{Pattern:bo,Color:0}]}", // Number 2

"{Patterns:[{Pattern:bs,Color:15},{Pattern:ms,Color:15},{Pattern:ts,Color:15},{Pattern:cbo,Color:0},{Pattern:rs,Color:15},{Pattern:bo,Color:0}]}", // Number 3

"{Patterns:[{Pattern:ls,Color:15},{Pattern:hhb,Color:0},{Pattern:rs,Color:15},{Pattern:ms,Color:15},{Pattern:bo,Color:0}]}", // Number 4

"{Patterns:[{Pattern:bs,Color:15},{Pattern:mr,Color:0},{Pattern:ts,Color:15},{Pattern:drs,Color:15},{Pattern:bo,Color:0}]}", // Number 5

"{Patterns:[{Pattern:bs,Color:15},{Pattern:rs,Color:15},{Pattern:hh,Color:0},{Pattern:ms,Color:15},{Pattern:ts,Color:15},{Pattern:ls,Color:15},{Pattern:bo,Color:0}]}", // Number 6

"{Patterns:[{Pattern:dls,Color:15},{Pattern:ts,Color:15},{Pattern:bo,Color:0}]}", // Number 7

"{Patterns:[{Pattern:ts,Color:15},{Pattern:ls,Color:15},{Pattern:ms,Color:15},{Pattern:bs,Color:15},{Pattern:rs,Color:15},{Pattern:bo,Color:0}]}", // Number 8

"{Patterns:[{Pattern:ls,Color:15},{Pattern:hhb,Color:0},{Pattern:ms,Color:15},{Pattern:ts,Color:15},{Pattern:rs,Color:15},{Pattern:bs,Color:15},{Pattern:bo,Color:0}]}", // Number 9

]