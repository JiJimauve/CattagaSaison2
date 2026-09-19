package fr.cattaga;

import fr.cattaga.block.CorruptionCarpet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class CorruptionBlock {

    public static final Block CORRUPTION_BLOCK = register(
            new Block(
                    BlockBehaviour.Properties.of()
                            .randomTicks()
                            .strength(-1.0f)
                            .sound(SoundType.SCULK)
                            .lightLevel(state -> 11)
                            .pushReaction(PushReaction.BLOCK)
            ),
            "corruptionblock",
            true
    );

    // Propagate Corruption Block

    public static final Block CORRUPTION_PROPAGATOR_BLOCK = register(
            new SculkCatalystBlock(
                    BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).strength(3.0F, 3.0F).sound(SoundType.SCULK_CATALYST).lightLevel(blockStatex -> 6)
            ),
            "corruptionpropagatorblock",
            true
    );

    // Glow Linchen
    public static final Block CORRUPTION_LICHEN_BLOCK = register(
            new GlowLichenBlock(
                    BlockBehaviour.Properties.of()
                        .mapColor(MapColor.GLOW_LICHEN)
                        .replaceable()
                        .noCollission()
                        .strength(0.2F)
                        .sound(SoundType.SCULK)
                        .lightLevel(GlowLichenBlock.emission(7))
                        .pushReaction(PushReaction.DESTROY)

            ),
            "corruptionlichenblock",
            true
    );

    // Corruption Vein
    // TODO ADD Corruption Vein based on sculk vein
    public static final Block CORRUPTION_VEIN_BLOCK = register(
            new SculkVeinBlock(
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.COLOR_BLACK)
                            .forceSolidOn()
                            .noCollission()
                            .strength(0.2F)
                            .sound(SoundType.SCULK_VEIN)
                            .pushReaction(PushReaction.DESTROY)
            ),

            "corruptionveinblock",
            true
    );

    public static Block register(Block block, String name, boolean shouldRegisterItem) {
        // Register the block and its item.
        ResourceLocation id = new ResourceLocation(Corruption.MOD_ID, name);

        // Register Item : Means creating the item that you can hold and pose
        // Sometimes, you may not want to register an item for the block.
        // Eg: if it's technical block like `minecraft:air` or `minecraft:end_gateway`
        if (shouldRegisterItem) {
            BlockItem blockItem = new BlockItem(block, new Item.Properties());
            Registry.register(BuiltInRegistries.ITEM, id, blockItem);
        }

        return Registry.register(BuiltInRegistries.BLOCK, id, block);
    }

    public static void initialize() {
    }

}