package io.github.llamarama.team.gloopy.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class GloopyBlock extends Block {

    public static final Map<Block, Block> NORMAL_TO_GLOOPY = new HashMap<>();

    private final Block normal;

    public GloopyBlock(Block normal, Settings settings) {
        super(settings);
        this.normal = normal;

        // Register ourselves to the map
        NORMAL_TO_GLOOPY.put(normal, this);
    }

    public static Optional<BlockState> from(@NotNull BlockState normal) {
        return Optional.ofNullable(NORMAL_TO_GLOOPY.get(normal.getBlock())).map(Block::getDefaultState);
    }

    public Block getNormalVariant() {
        return normal;
    }

}
