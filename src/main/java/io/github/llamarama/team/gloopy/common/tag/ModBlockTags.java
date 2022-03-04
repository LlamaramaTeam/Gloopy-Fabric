package io.github.llamarama.team.gloopy.common.tag;

import io.github.llamarama.team.gloopy.Gloopy;
import net.minecraft.block.Block;
import net.minecraft.tag.TagKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import org.jetbrains.annotations.NotNull;

public final class ModBlockTags {

    public static final TagKey<Block> GLOOP_BLACKLIST =
            TagKey.of(Registry.BLOCK_KEY, Gloopy.mod("blocks/gloop_blacklist"));

    public static boolean isBlacklisted(@NotNull Block block) {
        return Registry.BLOCK.getOrCreateEntryList(ModBlockTags.GLOOP_BLACKLIST).contains(RegistryEntry.of(block));
    }

}
