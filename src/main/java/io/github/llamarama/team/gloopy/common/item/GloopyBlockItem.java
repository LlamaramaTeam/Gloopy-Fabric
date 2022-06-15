package io.github.llamarama.team.gloopy.common.item;

import com.google.common.base.Suppliers;
import io.github.llamarama.team.gloopy.Gloopy;
import io.github.llamarama.team.gloopy.common.lib.CachedFunction;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FallingBlock;
import net.minecraft.item.*;
import net.minecraft.text.LiteralTextContent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class GloopyBlockItem extends BlockItem {
    public static final String GLOOPY_BLOCK = "GloopyBlock";
    private static final Supplier<List<CachedFunction<GloopyBlockItem, ItemStack>>> FALLING_BLOCK_STACKS =
            Suppliers.memoize(() -> Registry.BLOCK.stream()
                    .filter(it -> it instanceof FallingBlock)
                    .map(it -> (FallingBlock) it)
                    .map(it -> CachedFunction.<GloopyBlockItem, ItemStack>of(item -> createBlockItemStack(it, item)))
                    .toList()
            );

    public GloopyBlockItem(Settings settings) {
        super(Blocks.SAND, settings);
    }

    public static @NotNull ItemStack createBlockItemStack(@NotNull Block block, @NotNull Item item) {
        var stack = new ItemStack(item);
        stack.getOrCreateNbt().putString(GLOOPY_BLOCK, Registry.BLOCK.getId(block).toString());
        return stack;
    }

    @Override
    public Text getName(@NotNull ItemStack stack) {
        var mutableText = MutableText.of(new LiteralTextContent("Gloopy "));
        var block = Registry.BLOCK
                .getOrEmpty(new Identifier(stack.getOrCreateNbt().getString(GLOOPY_BLOCK)))
                .orElse(Blocks.AIR);
        mutableText.append(block.getName());

        return mutableText;
    }

    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
        if (this.isIn(group)) {
            FALLING_BLOCK_STACKS.get().forEach(f -> stacks.add(f.apply(this)));
        }
    }

    @Nullable
    @Override
    protected BlockState getPlacementState(@NotNull ItemPlacementContext context) {
        var blockId = new Identifier(context.getStack().getOrCreateNbt().getString(GLOOPY_BLOCK));
        BlockState placementState = Registry.BLOCK.getOrEmpty(blockId).orElse(Blocks.AIR).getPlacementState(context);

        if (placementState == null || placementState.isAir()) {
            return null;
        }

        return this.canPlace(context, placementState) ? placementState.with(Gloopy.GLOOPY, true) : null;
    }

}
