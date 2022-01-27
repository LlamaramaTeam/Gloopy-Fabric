package io.github.llamarama.team.gloopy.common.register;

import io.github.llamarama.team.gloopy.Gloopy;
import io.github.llamarama.team.gloopy.common.block.GloopyBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public final class ModBlocks {

    private static final Map<String, Block> REGISTRY = new HashMap<>();
    private static final Supplier<AbstractBlock.Settings> SETTINGS =
            () -> AbstractBlock.Settings.copy(Blocks.WHITE_CONCRETE_POWDER);

    public static final Block GLOOPY_WHITE_CONCRETE_POWDER = register("gloopy_white_concrete_powder",
            new GloopyBlock(Blocks.WHITE_CONCRETE_POWDER, SETTINGS.get()));
    public static final Block GLOOPY_ORANGE_CONCRETE_POWDER = register("gloopy_orange_concrete_powder",
            new GloopyBlock(Blocks.ORANGE_CONCRETE_POWDER, SETTINGS.get()));
    public static final Block GLOOPY_MAGENTA_CONCRETE_POWDER = register("gloopy_magenta_concrete_powder",
            new GloopyBlock(Blocks.MAGENTA_CONCRETE_POWDER, SETTINGS.get()));
    public static final Block GLOOPY_LIGHT_BLUE_CONCRETE_POWDER = register("gloopy_light_blue_concrete_powder",
            new GloopyBlock(Blocks.LIGHT_BLUE_CONCRETE_POWDER, SETTINGS.get()));
    public static final Block GLOOPY_YELLOW_CONCRETE_POWDER = register("gloopy_yellow_concrete_powder",
            new GloopyBlock(Blocks.YELLOW_CONCRETE_POWDER, SETTINGS.get()));
    public static final Block GLOOPY_LIME_CONCRETE_POWDER = register("gloopy_lime_concrete_powder",
            new GloopyBlock(Blocks.LIME_CONCRETE_POWDER, SETTINGS.get()));
    public static final Block GLOOPY_PINK_CONCRETE_POWDER = register("gloopy_pink_concrete_powder",
            new GloopyBlock(Blocks.PINK_CONCRETE_POWDER, SETTINGS.get()));
    public static final Block GLOOPY_GRAY_CONCRETE_POWDER = register("gloopy_gray_concrete_powder",
            new GloopyBlock(Blocks.GRAY_CONCRETE_POWDER, SETTINGS.get()));
    public static final Block GLOOPY_LIGHT_GRAY_CONCRETE_POWDER = register("gloopy_light_gray_concrete_powder",
            new GloopyBlock(Blocks.LIGHT_GRAY_CONCRETE_POWDER, SETTINGS.get()));
    public static final Block GLOOPY_CYAN_CONCRETE_POWDER = register("gloopy_cyan_concrete_powder",
            new GloopyBlock(Blocks.CYAN_CONCRETE_POWDER, SETTINGS.get()));
    public static final Block GLOOPY_PURPLE_CONCRETE_POWDER = register("gloopy_purple_concrete_powder",
            new GloopyBlock(Blocks.PURPLE_CONCRETE_POWDER, SETTINGS.get()));
    public static final Block GLOOPY_BLUE_CONCRETE_POWDER = register("gloopy_blue_concrete_powder",
            new GloopyBlock(Blocks.BLUE_CONCRETE_POWDER, SETTINGS.get()));
    public static final Block GLOOPY_BROWN_CONCRETE_POWDER = register("gloopy_brown_concrete_powder",
            new GloopyBlock(Blocks.BROWN_CONCRETE_POWDER, SETTINGS.get()));
    public static final Block GLOOPY_GREEN_CONCRETE_POWDER = register("gloopy_green_concrete_powder",
            new GloopyBlock(Blocks.GREEN_CONCRETE_POWDER, SETTINGS.get()));
    public static final Block GLOOPY_RED_CONCRETE_POWDER = register("gloopy_red_concrete_powder",
            new GloopyBlock(Blocks.RED_CONCRETE_POWDER, SETTINGS.get()));
    public static final Block GLOOPY_BLACK_CONCRETE_POWDER = register("gloopy_black_concrete_powder",
            new GloopyBlock(Blocks.BLACK_CONCRETE_POWDER, SETTINGS.get()));

    private static Block registerNoItem(String id, Block block) {
        REGISTRY.put(id, block);
        return block;
    }

    private static Block register(String id, Block block) {
        var ret = registerNoItem(id, block);
        ModItems.register(id, new BlockItem(ret, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        return ret;
    }

    public static void init() {
        REGISTRY.forEach((s, block) -> Registry.register(Registry.BLOCK, Gloopy.mod(s), block));
    }

}
