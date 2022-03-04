package io.github.llamarama.team.gloopy.common.register;

import io.github.llamarama.team.gloopy.Gloopy;
import io.github.llamarama.team.gloopy.common.item.GloopyBlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.Map;

public final class ModItems {

    private static final Map<String, Item> REGISTRY = new HashMap<>();

    public static final Item GLOOPY_ITEM = register("gloopy_item",
            new GloopyBlockItem(new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));

    static Item register(String id, Item item) {
        REGISTRY.put(id, item);
        return item;
    }

    public static void init() {
        REGISTRY.forEach((s, item) -> Registry.register(Registry.ITEM, Gloopy.mod(s), item));
    }

}
