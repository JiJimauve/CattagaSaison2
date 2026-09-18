package fr.cattaga;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
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

    // Moss Carpet

    // Glow Linchen

    public static Block register(Block block, String name, boolean shouldRegisterItem) {
        // Register the block and its item.
        ResourceLocation id = new ResourceLocation(Corruption.MOD_ID, name);


        // Register Item : Means creating the item that you can hold and pose
        // Sometimes, you may not want to register an item for the block.
        // Eg: if it's a technical block like `minecraft:air` or `minecraft:end_gateway`
        if (shouldRegisterItem) {
            BlockItem blockItem = new BlockItem(block, new Item.Properties());
            Registry.register(BuiltInRegistries.ITEM, id, blockItem);
        }

        return Registry.register(BuiltInRegistries.BLOCK, id, block);
    }

    public static void initialize() {

    }

}