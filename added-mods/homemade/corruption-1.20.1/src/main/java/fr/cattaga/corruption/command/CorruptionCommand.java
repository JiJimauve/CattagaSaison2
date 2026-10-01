package fr.cattaga.corruption.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import fr.cattaga.corruption.block.corruption.propagator.CorruptionPropagatorOptions;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class CorruptionCommand {


    // ----------------------------------------------------------------------------------------------------------------

    public static void initialize() {

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(Commands.literal("corruption")
                    .requires(source -> source.hasPermission(4))
                    .executes(context -> {
                        context.getSource()
                                .sendSuccess(() -> Component.literal("/corruption is used to set properties of the corruption"), false);
                        return 1;
                    })
                    .then(Commands.literal("turnPercent").executes(
                            context -> {
                                context.getSource()
                                        .sendFailure(Component.literal("turnPercent is used to set the % chance for a block to turn Corrupted near a CorruptionPropagator, Please use a integer value between 0-100 ( Currently "+ CorruptionPropagatorOptions.CORRUPTION_SPREAD_PERCENT +  "%)"));

                                return 1;
                            }
                    ).then(Commands.argument("percent", IntegerArgumentType.integer()).executes(context -> {

                        int percent = context.getArgument("percent", Integer.class);

                        if (percent < 0 || percent > 100) {
                            context.getSource()
                                    .sendFailure(Component.literal("Called /corruption turnPercent with " + percent + " Please use a integer value between 0-100\n ( Currently "+ CorruptionPropagatorOptions.CORRUPTION_SPREAD_PERCENT +  "%)"));
                            return 0;

                        }

                        CorruptionPropagatorOptions.CORRUPTION_SPREAD_PERCENT = percent;

                        context.getSource()
                                .sendSuccess(
                                        () -> Component.literal("Corruption will now have " + percent + "% chance to turn block into corrupted one"), false);
                        return 1;
                    })))
                    .then(Commands.literal("energyfactor").executes(
                            context -> {
                                context.getSource()
                                        .sendFailure(Component.literal("energyfactor is used to set the Factor of a Propagator to work ( 1 = Fast ; 100 = Very Slow )\nExample Propagator will turn near block corrupted in : energyFactor x ~3 Sec \n( Currently "+ CorruptionPropagatorOptions.PROPAGATOR_TICK_RATE +  "%)"));

                                return 1;
                            }
                    ).then(Commands.argument("factor", IntegerArgumentType.integer()).executes(context -> {

                        int nbTick = context.getArgument("factor", Integer.class);

                        if (nbTick <= 0) {
                            CorruptionPropagatorOptions.PROPAGATOR_TICK_RATE = 0;
                            context.getSource()
                                    .sendSuccess(
                                            () -> Component.literal("Corruption Propagator will do nothing on their own from now on ( will not get any energy )"), false);
                            return 1 ;
                        }

                        CorruptionPropagatorOptions.PROPAGATOR_TICK_RATE = nbTick;

                        context.getSource()
                                .sendSuccess(
                                        () -> Component.literal("Corruption will now take " + nbTick + " x ~3 Sec to turn one near bloc corrupted"), false);
                        return 1;
                    })))
            );
        });


    }

}
