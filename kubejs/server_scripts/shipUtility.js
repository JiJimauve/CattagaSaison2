//const Identifier = Java.loadClass('net.minecraft.class_2960') // Identifier
//const BlockPos = Java.loadClass('net.minecraft.class_2338')   // BlockPos

/*
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
*/

ServerEvents.commandRegistry(event => {
    const { commands } = event;

    event.register(
        commands.literal('listentities')
            .requires(source => source.hasPermission(2))
            .executes(ctx => {
    		const server = ctx.source.getServer();
                const source = ctx.source;
                const player = ctx.source.player;
                const level = source.getLevel();
                const origin = source.getPosition();

                // Dimensions du rectangle
                const width = 20;
                const length = 20;
                const height = 40;

                // Centre du rectangle
                const center_x = 0; //origin.x();
                const center_y = 78; //origin.y();
                const center_z = 0; //origin.z();

                // Limites du rectangle
                const min_x = center_x - width / 2;
                const max_x = center_x + width / 2;
                const min_y = center_y;
                const max_y = center_y + height;
                const min_z = center_z - length / 2;
                const max_z = center_z + length / 2;

                const entities = level.getEntities();

                let count = 0;

                entities.forEach(entity => {
                    const pos = entity.position();
                    
                    if (!pos) {
                    	//player.tell(JSON.stringify(pos));
                    	return;
                    }


		if (entity.type === 'minecraft:player') {
		
                        console.log(
                            `[KubeJS] Entité candidate: ` +
                            `${entity.type} ` +
                            `(${min_x} <? ${pos.x()} <? ${max_x}, ${min_y} <? ${pos.y()} <? ${min_y}, ${min_z} <? ${pos.z()} <? ${min_z})`
                        );
                }
                
                    if (
                        pos.x() >= min_x &&
                        pos.x() <= max_x &&
                        pos.y() >= min_y &&
                        pos.y() <= max_y &&
                        pos.z() >= min_z &&
                        pos.z() <= max_z
                    ) {
                        count++;

                        console.log(
                            `[KubeJS] Entité trouvée: ` +
                            `${entity.id} ` +
                            `${entity.name} ` +
                            `${entity.type} ` +
                            `(${pos.x()}, ${pos.y()}, ${pos.z()})`
                        );
                        
			if (entity.type === 'minecraft:player') {
				const intx = Math.floor(pos.x());
				const inty = Math.floor(pos.y());
				const intz = Math.floor(pos.z());
			//player.tell(`Teleportation de ${entity.name}`);
			//player.tell(`execute positioned ${intx} ${inty} ${intz} as @a[distance=..3] run execute in multiworld:world3 run tp @s ~ ~ ~`);
			
				//execute positioned 0 81 0 as @e[distance=..10] run execute in multiworld:world3 positioned 0 81 0 run tp @e[x=${intx},y=${inty},z=${intz}] ~ ~ ~

				
				server.runCommandSilent(
				    //`execute in ${spawnBase_dimension} run setblock ${indicatorBloc_X} ${indicatorBloc_Y} ${indicatorBloc_Z} ${blocId}`
				    `execute positioned ${intx} ${inty} ${intz} as @a[distance=..3] run execute in multiworld:world3 run tp @s ~ ~ ~`
				);
			}
                        /*source.sendSuccess(
                            `Entité: ${entity.type} ` +
                            `à ${Math.floor(pos.x)}, ` +
                            `${Math.floor(pos.y)}, ` +
                            `${Math.floor(pos.z)}`,
                            false
                        );*/
                    }
                });

                /*source.sendSuccess(
                    `Nombre total d'entités: ${count}`,
                    false
                );*/
			//player.tell(`Nombre total d'entités: ${count}`);

                return 0;
            })
    );
});

// command /tpShip [value] to set the counter
ServerEvents.commandRegistry(event => {
    const { commands: Commands, arguments: Arguments } = event;
    event.register(
      Commands.literal('tpShip')
        .requires(s => s.hasPermission(4))
        .then(
          Commands.argument('value', Arguments.INTEGER.create(event)) 
            .executes(ctx => tpShipCommand(ctx.source.getServer() , ctx.source.player , Arguments.INTEGER.getResult(ctx, 'value')))
        )
    );
  
    // Real tpShip function
    const tpShipCommand = (server, player, value) => {
    		if (player) {
			player.tell('Omg');
		}
		
		//console.log("/countSet " + value + " STARTED from " + player.name + " current count : " + count);
		
		/*if (value < 0) {
			count = 0;
		}
		else {
			count = value;
		}
		
		if (player) {
			player.tell('Count is now : ' + count);
		}
		
	  	countChanged(server);*/
		
	  	//console.log("/countSet " + value + " ENDED from " + player.name + " current count : " + count);
		return 0;
    };
});

console.info(`ServerScript : ShipUtility is now UP `)

// **********************************************
// Server Started
// Get The Number From saved value
ServerEvents.loaded(event => {
	console.log(" -------------------------------------------------------------- ServerScript : ShipUtility ServerEvents.loaded ---------------------------------------------------------- ");
	

	if (count == undefined) {
		loadFromSaveBloc(event.server, event.server.getLevel("minecraft:overworld") );
	}
	

	console.log(" -------------------------------------------------------------- ServerScript : ShipUtility ServerEvents.loaded ---------------------------------------------------------- ");
		
	
});
//})();

