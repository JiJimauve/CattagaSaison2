console.log("Kube NickName Ready");


ServerEvents.commandRegistry(event => {
    const { commands: Commands, arguments: Arguments } = event;

    event.register(
        Commands.literal('kubename') // Nom de la commande
            .requires(src => src.hasPermission(2)) // Permission niveau 2 (OP)
            .then(Commands.argument('newNickName', Arguments.STRING.create(event)) // Paramètre : le nouveau pseudo
                .executes(ctx => {
                    const NewNickName = Arguments.STRING.getResult(ctx, 'newNickName');
                    const player = ctx.source.player;

                    if (player) {
                        // Exécution de la commande de changement de pseudo
                        player.runCommandSilent(`execute as ${player.username} run nick name ${NewNickName}`);
                        
                        // Petit feedback pour le joueur
                        player.tell(`Ton pseudo a été changé en : ${NewNickName}`);
                    }
                    
                    return 1;
                })
            )
    );
});


ServerEvents.commandRegistry(event => {
    const { commands: Commands, arguments: Arguments } = event;

    event.register(
        Commands.literal('kubenamereset') // Nom de la commande
            .requires(src => src.hasPermission(2)) // Permission niveau 2 (OP)
                .executes(ctx => {
                    const player = ctx.source.player;

                    if (player) {
                        // Exécution de la commande de changement de pseudo
                        player.runCommandSilent(`execute as ${player.username} run nick reset`);
                        
                        // Petit feedback pour le joueur
                        player.tell(`Ton pseudo a été réinitialisé`);
                    }
                    
                    return 1;
                })
            
    );
});