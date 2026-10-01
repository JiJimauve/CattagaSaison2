package fr.cattaga.corruption;

import com.mojang.datafixers.types.Type;
import fr.cattaga.corruption.block.corruption.propagator.CorruptionPropagatorBlock;
import fr.cattaga.corruption.block.corruption.propagator.CorruptionPropagatorBlockEntity;
import fr.cattaga.corruption.block.corruption.vein.CorruptionVeinBlock;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class CorruptionBlock {

    public static final TagKey<Block> CORRUPTED = TagKey.create(Registries.BLOCK,
            Corruption.generateResourceLocation("corrupted"));


    public static final Block CORRUPTION_BLOCK = registerBlock(
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


    public static final Block CORRUPTION_EGG_BLOCK = registerBlock(
            new Block(
                    BlockBehaviour.Properties.of()
                            .randomTicks()
                            .strength(-1.0f)
                            .sound(SoundType.SCULK)
                            .lightLevel(state -> 11)
                            .pushReaction(PushReaction.BLOCK)

            ),
            "corruptioneggblock",
            true
    );

    // Propagate Corruption Block
    // This block has a custom entity built in it  ( CORRUPTION_PROPAGATOR )

    public static final Block CORRUPTION_PROPAGATOR_BLOCK = registerBlock(
            new CorruptionPropagatorBlock(
                    BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).strength(3.0F, 3.0F).sound(SoundType.SCULK_CATALYST).lightLevel(blockState -> 6)
            ),
            "corruptionpropagatorblock",
            true
    );

    // Create a BlockEntity
    //      an Entity : CorruptionPropagatorBlockEntity : CORRUPTION_PROPAGATOR_BLOCK_ENTITY
    //      registered "corruption_propagator" ( id calc : "corruption:corruption_propagator" )
    //      attached to the block CorruptionPropagatorBlock : CORRUPTION_PROPAGATOR_BLOCK
    //      wich will be created at spawn
    public static final BlockEntityType<CorruptionPropagatorBlockEntity> CORRUPTION_PROPAGATOR_BLOCK_ENTITY = registerBlockEntity(
            BlockEntityType.Builder.of(CorruptionPropagatorBlockEntity::new, CORRUPTION_PROPAGATOR_BLOCK), "corruption_propagator");
    //                  BlockEntityType<SculkCatalystBlockEntity> SCULK_CATALYST


    // Glow Linchen
    public static final Block CORRUPTION_LICHEN_BLOCK = registerBlock(
            new GlowLichenBlock(
                    BlockBehaviour.Properties.of()
                        .mapColor(MapColor.GLOW_LICHEN)
                        .replaceable()
                        .noCollission()
                        .strength(0.2F)
                        .sound(SoundType.SCULK)
                        .lightLevel(GlowLichenBlock.emission(7))
                        .pushReaction(PushReaction.DESTROY)
                            .randomTicks()

            ),
            "corruptionlichenblock",
            true
    );

    // Corruption Vein
    public static final Block CORRUPTION_VEIN_BLOCK = registerBlock(
            new CorruptionVeinBlock(
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

    // ----------------------------------------------------------------------------------------------------------------
    // Registery

    private static Block registerBlock(Block block, String name, boolean shouldRegisterItem) {
        // Register the block and its item.
        ResourceLocation id = Corruption.generateResourceLocation(name);

        // Register Item : Means creating the item that you can hold and pose
        // Sometimes, you may not want to register an item for the block.
        // Eg: if it's technical block like `minecraft:air` or `minecraft:end_gateway`
        if (shouldRegisterItem) {
            BlockItem blockItem = new BlockItem(block, new Item.Properties());
            Registry.register(BuiltInRegistries.ITEM, id, blockItem);
        }

        return Registry.register(BuiltInRegistries.BLOCK, id, block);
    }

    private static <T extends BlockEntity> BlockEntityType<T> registerBlockEntity(BlockEntityType.Builder<T> builder, String name) {


        // Register the block and its item.
        ResourceLocation id = Corruption.generateResourceLocation(name);



        Type<?> type = Util.fetchChoiceType(References.BLOCK_ENTITY, name);


        return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, id, builder.build(type));
    }

    // ----------------------------------------------------------------------------------------------------------------

    public static void initialize() {
    }

}