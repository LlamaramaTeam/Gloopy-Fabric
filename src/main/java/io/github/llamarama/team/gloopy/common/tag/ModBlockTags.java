package io.github.llamarama.team.gloopy.common.tag;

import io.github.llamarama.team.gloopy.Gloopy;
import net.minecraft.block.Block;
import net.minecraft.tag.TagKey;
import net.minecraft.util.registry.Registry;

public final class ModBlockTags {

    public static final TagKey<Block> GLOOP_BLACKLIST =
            TagKey.of(Registry.BLOCK_KEY, Gloopy.mod("gloop_blacklist"));

}
