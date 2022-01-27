package io.github.llamarama.team.gloopy.common.register;

import io.github.llamarama.team.gloopy.Gloopy;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.Map;

public final class ModItems {

    private static final Map<String, Item> REGISTRY = new HashMap<>();

    static Item register(String id, Item item) {
        REGISTRY.put(id, item);
        return item;
    }

    public static void init() {
        REGISTRY.forEach((s, item) -> Registry.register(Registry.ITEM, Gloopy.mod(s), item));
    }

}
