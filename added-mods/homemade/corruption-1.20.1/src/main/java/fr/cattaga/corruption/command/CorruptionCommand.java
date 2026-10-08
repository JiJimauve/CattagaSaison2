package fr.cattaga.corruption.command;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class CorruptionCommand {

    private static String OptionsDisplay() {

        float sec = CorruptionOptions.PROPAGATOR_TICK_RATE * 0.05f;

        return "(Turn " + CorruptionOptions.PROPAGATOR_MAX_TURN + " try ( " + CorruptionOptions.CORRUPTION_SPREAD_PERCENT + "% winRate ) every " + sec + " seconds ( " + CorruptionOptions.PROPAGATOR_TICK_RATE + " ticks )";
    }

    // ----------------------------------------------------------------------------------------------------------------

    public static void initialize() {

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(Commands.literal("corruptionspeed")
                    .requires(source -> source.hasPermission(4))
                    .executes(context -> {
                        context.getSource()
                                .sendSuccess(() -> Component.literal("/corruption is used to easely set propagation speed of the corruption"), false);
                        return 1;
                    })
                    .then(Commands.literal("none").executes(
                            context -> {

                                CorruptionOptions.CORRUPTION_SPREAD_PERCENT = 0;
                                CorruptionOptions.PROPAGATOR_TICK_RATE = 0;
                                CorruptionOptions.PROPAGATOR_MAX_TURN = 512;
                                CorruptionOptions.PROPAGATOR_HATCH = false;

                                context.getSource()
                                        .sendSuccess(
                                                () -> Component.literal("Corruption does NOT spread anymore." +     CorruptionCommand.OptionsDisplay()), false);
                                return 1;
                            }))
                            .then(Commands.literal("ultraslow").executes(
                                    context -> {

                                        CorruptionOptions.CORRUPTION_SPREAD_PERCENT = 100;
                                        CorruptionOptions.PROPAGATOR_TICK_RATE = 120;
                                        CorruptionOptions.PROPAGATOR_MAX_TURN = 512;
                                        CorruptionOptions.PROPAGATOR_HATCH = true;

                                        context.getSource()
                                                .sendSuccess(
                                                        () -> Component.literal("Corruption spread at Ultra Slow speed ." +     CorruptionCommand.OptionsDisplay()), false);
                                        return 1;
                                    }))
                            .then(Commands.literal("veryslow").executes(
                            context -> {

                                CorruptionOptions.CORRUPTION_SPREAD_PERCENT = 100;
                                CorruptionOptions.PROPAGATOR_TICK_RATE = 100;
                                CorruptionOptions.PROPAGATOR_MAX_TURN = 512;
                                CorruptionOptions.PROPAGATOR_HATCH = true;

                                context.getSource()
                                        .sendSuccess(
                                                () -> Component.literal("Corruption spread at Very Slow speed ." +     CorruptionCommand.OptionsDisplay()), false);
                                return 1;
                            }))
                    .then(Commands.literal("slow").executes(
                            context -> {

                                CorruptionOptions.CORRUPTION_SPREAD_PERCENT = 100;
                                CorruptionOptions.PROPAGATOR_TICK_RATE = 80;
                                CorruptionOptions.PROPAGATOR_MAX_TURN = 512;
                                CorruptionOptions.PROPAGATOR_HATCH = true;

                                context.getSource()
                                        .sendSuccess(
                                                () -> Component.literal("Corruption spread at Slow speed ." +     CorruptionCommand.OptionsDisplay()), false);
                                return 1;
                            }))
                    .then(Commands.literal("normal").executes(
                            context -> {

                                CorruptionOptions.CORRUPTION_SPREAD_PERCENT = 100;
                                CorruptionOptions.PROPAGATOR_TICK_RATE = 60;
                                CorruptionOptions.PROPAGATOR_MAX_TURN = 512;
                                CorruptionOptions.PROPAGATOR_HATCH = true;

                                context.getSource()
                                        .sendSuccess(
                                                () -> Component.literal("Corruption spread at Normal speed ." +     CorruptionCommand.OptionsDisplay()), false);
                                return 1;
                            }))
                    .then(Commands.literal("fast").executes(
                            context -> {

                                CorruptionOptions.CORRUPTION_SPREAD_PERCENT = 100;
                                CorruptionOptions.PROPAGATOR_TICK_RATE = 40;
                                CorruptionOptions.PROPAGATOR_MAX_TURN = 512;
                                CorruptionOptions.PROPAGATOR_HATCH = true;

                                context.getSource()
                                        .sendSuccess(
                                                () -> Component.literal("Corruption spread at Fast speed ." +     CorruptionCommand.OptionsDisplay()), false);
                                return 1;
                            }))
                    .then(Commands.literal("veryfast").executes(
                            context -> {

                                CorruptionOptions.CORRUPTION_SPREAD_PERCENT = 100;
                                CorruptionOptions.PROPAGATOR_TICK_RATE = 20;
                                CorruptionOptions.PROPAGATOR_MAX_TURN = 512;
                                CorruptionOptions.PROPAGATOR_HATCH = true;

                                context.getSource()
                                        .sendSuccess(
                                                () -> Component.literal("Corruption spread at Very Fast speed ." +     CorruptionCommand.OptionsDisplay()), false);
                                return 1;
                            }))
                    .then(Commands.literal("ultrafast").executes(
                            context -> {

                                CorruptionOptions.CORRUPTION_SPREAD_PERCENT = 100;
                                CorruptionOptions.PROPAGATOR_TICK_RATE = 10;
                                CorruptionOptions.PROPAGATOR_MAX_TURN = 512;
                                CorruptionOptions.PROPAGATOR_HATCH = true;

                                context.getSource()
                                        .sendSuccess(
                                                () -> Component.literal("Corruption spread at Ultra Fast speed ." +     CorruptionCommand.OptionsDisplay()), false);
                                return 1;
                            }))
                    .then(Commands.literal("megaultrafast").executes(
                            context -> {

                                CorruptionOptions.CORRUPTION_SPREAD_PERCENT = 100;
                                CorruptionOptions.PROPAGATOR_TICK_RATE = 5;
                                CorruptionOptions.PROPAGATOR_MAX_TURN = 512;
                                CorruptionOptions.PROPAGATOR_HATCH = true;


                                context.getSource()
                                        .sendSuccess(
                                                () -> Component.literal("Corruption spread at Mega Ultra Fast speed ." +     CorruptionCommand.OptionsDisplay()), false);


                                context.getSource()
                                        .sendFailure(Component.literal("( BE CAREFULL SERVER MIGHT LAG AND/OR CRASH 💀💀💀💀 )"));

                                return 1;
                            }))
                    .then(Commands.literal("maximumspeed").executes(
                            context -> {

                                CorruptionOptions.CORRUPTION_SPREAD_PERCENT = 100;
                                CorruptionOptions.PROPAGATOR_TICK_RATE = 5;
                                CorruptionOptions.PROPAGATOR_MAX_TURN = 512;
                                CorruptionOptions.PROPAGATOR_HATCH = true;

                                context.getSource()
                                        .sendSuccess(
                                                () -> Component.literal("Corruption spread at MAX speed  ." +     CorruptionCommand.OptionsDisplay()), false);

                                context.getSource()
                                        .sendFailure(Component.literal("( BE CAREFULL SERVER MIGHT LAG AND/OR CRASH 💀💀💀💀💀💀💀💀💀💀💀💀 )"));

                                return 1;
                            }))
            );
        });


        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(Commands.literal("corruptionoption")
                    .requires(source -> source.hasPermission(4))
                    .executes(context -> {
                        context.getSource()
                                .sendSuccess(() -> Component.literal("/corruptionoption is used to set options of the corruption. Current options : "+     CorruptionCommand.OptionsDisplay() ), false);
                        return 1;
                    })
                    .then(Commands.literal("turnPercent").executes(
                            context -> {
                                context.getSource()
                                        .sendFailure(Component.literal("turnPercent is used to set the % chance for a block to turn Corrupted near a CorruptionPropagator, Please use a integer value between 0-100 ( Currently "+ CorruptionOptions.CORRUPTION_SPREAD_PERCENT +  "%)"));

                                return 1;
                            }
                    ).then(Commands.argument("percent", IntegerArgumentType.integer()).executes(context -> {

                        int percent = context.getArgument("percent", Integer.class);

                        if (percent < 0 || percent > 100) {
                            context.getSource()
                                    .sendFailure(Component.literal("Called /corruption turnPercent with " + percent + " Please use a integer value between 0-100\n ( Currently "+ CorruptionOptions.CORRUPTION_SPREAD_PERCENT +  "%)"));
                            return 0;

                        }

                        CorruptionOptions.CORRUPTION_SPREAD_PERCENT = percent;

                        context.getSource()
                                .sendSuccess(
                                        () -> Component.literal("Corruption will now have " + percent + "% chance to turn block into corrupted one"), false);
                        return 1;
                    })))
                    .then(Commands.literal("turnPercent").executes(
                            context -> {
                                context.getSource()
                                        .sendFailure(Component.literal("turnPercent is used to set the % chance for a block to turn Corrupted near a CorruptionPropagator, Please use a integer value between 0-100 ( Currently "+ CorruptionOptions.CORRUPTION_SPREAD_PERCENT +  "%)"));

                                return 1;
                            }
                    ).then(Commands.argument("percent", IntegerArgumentType.integer()).executes(context -> {

                        int percent = context.getArgument("percent", Integer.class);

                        if (percent < 0 || percent > 100) {
                            context.getSource()
                                    .sendFailure(Component.literal("Called /corruption turnPercent with " + percent + " Please use a integer value between 0-100\n ( Currently "+ CorruptionOptions.CORRUPTION_SPREAD_PERCENT +  "%)"));
                            return 0;

                        }

                        CorruptionOptions.CORRUPTION_SPREAD_PERCENT = percent;

                        context.getSource()
                                .sendSuccess(
                                        () -> Component.literal("Corruption will now have " + percent + "% chance to turn block into corrupted one"), false);
                        return 1;
                    })))//
                    .then(Commands.literal("turnMax").executes(
                            context -> {
                                context.getSource()
                                        .sendFailure(Component.literal("turnMax is used to set maximal Corruption entity that the server will calculate ( Currently "+ CorruptionOptions.PROPAGATOR_MAX_TURN +  ")"));

                                return 1;
                            }
                    ).then(Commands.argument("amount", IntegerArgumentType.integer()).executes(context -> {

                        int amount = context.getArgument("amount", Integer.class);

                        if (amount < 0 ) {
                            CorruptionOptions.PROPAGATOR_MAX_TURN = 0;

                            context.getSource()
                                    .sendSuccess(
                                            () -> Component.literal("Corruption will not calculate anything from now on ( PROPAGATOR_MAX_TURN = 0 ) "), false);

                        }

                        if (amount > 1024 ) {
                            context.getSource()
                                    .sendFailure(Component.literal(" WARNING : More than 1024 can turn server VERY LAGGY"));
                        } else if (amount > 512 ) {
                            context.getSource()
                                    .sendFailure(Component.literal(" WARNING : More than 512 can turn server LAGGY"));
                        }


                        CorruptionOptions.PROPAGATOR_MAX_TURN = amount;

                        context.getSource()
                                .sendSuccess(
                                        () -> Component.literal("Corruption will now proceed max " + amount + " Corrupted Propagator Blocks on tick rate ( 1 tick every " + CorruptionOptions.PROPAGATOR_TICK_RATE + ")"), false);
                        return 1;
                    })))
                    .then(Commands.literal("tickRate").executes(
                            context -> {
                                context.getSource()
                                        .sendFailure(Component.literal("TickRate is used to set the Factor of a Propagator to work ( 5 = VERY Fast ; 20 ~ 1 sec ; 1200 = 60 sec ; 10 000 => very slow )\nExample Propagator will turn near block corrupted in : energyFactor x ~1 Sec \n( Currently "+ CorruptionOptions.PROPAGATOR_TICK_RATE +  " tick)"));

                                return 1;
                            }
                    ).then(Commands.argument("amount", IntegerArgumentType.integer()).executes(context -> {

                        int nbTick = context.getArgument("amount", Integer.class);

                        if (nbTick <= 0) {
                            CorruptionOptions.PROPAGATOR_TICK_RATE = 0;
                            context.getSource()
                                    .sendSuccess(
                                            () -> Component.literal("Corruption Propagator will do nothing on their own from now on ( will not get any energy )"), false);
                            return 1 ;
                        }

                        /*if (nbTick <= 5) {
                            context.getSource()
                                    .sendFailure(Component.literal("energyfactor CANNOT be set bellow 5 (too unstable) \n( ask for  "+ nbTick +  " tick)"));

                            nbTick = 5;
                        }*/

                        CorruptionOptions.PROPAGATOR_TICK_RATE = nbTick;

                        float sec = nbTick * 0.05f;

                        context.getSource()
                                .sendSuccess(
                                        () -> Component.literal("Corruption will now take " + CorruptionOptions.PROPAGATOR_TICK_RATE + " x 0.05 Sec (~"+sec+"sec) to turn one near bloc corrupted"), false);
                        return 1;
                    })))
                    .then(Commands.literal("hatch").executes(
                            context -> {
                                context.getSource()
                                        .sendFailure(Component.literal("hatch is used to turn CorruptionEgg into CorruptionPropagator ( Currently " + CorruptionOptions.PROPAGATOR_HATCH + " )"));

                                return 1;
                            }
                    ).then(Commands.argument("activate", BoolArgumentType.bool()).executes(context -> {

                        CorruptionOptions.PROPAGATOR_HATCH = context.getArgument("activate", Boolean.class);

                        if (CorruptionOptions.PROPAGATOR_HATCH) {
                            context.getSource()
                                    .sendSuccess(
                                            () -> Component.literal("Egg will now Hatch ( CorruptionEggBlock will turn CorruptionPropagationBlock) ( " + CorruptionOptions.PROPAGATOR_HATCH + " )"), false);
                        } else {
                            context.getSource()
                                    .sendSuccess(
                                            () -> Component.literal("Egg STOP Hatching ( CorruptionEggBlock will turn CorruptionPropagationBlock) " + CorruptionOptions.PROPAGATOR_TICK_RATE + " "), false);
                        }


                        return 1;
                    })))
            );
        });


    }

}
