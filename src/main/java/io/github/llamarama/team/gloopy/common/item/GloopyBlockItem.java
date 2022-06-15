package io.github.llamarama.team.gloopy.common.item;

import io.github.llamarama.team.gloopy.Gloopy;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FallingBlock;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.text.LiteralTextContent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Function;

public class GloopyBlockItem extends BlockItem {

    public static final String GLOOPY_STATE = "GloopyState";
    private static final List<Function<GloopyBlockItem, ItemStack>> FALLING_BLOCK_STACKS = Registry.BLOCK.stream()
            .filter(it -> it instanceof FallingBlock)
            .map(it -> (FallingBlock) it)
            .map(Block::getStateManager)
            .map(it -> (Function<GloopyBlockItem, ItemStack>) (item) ->
                    createBlockItemStack(it.getDefaultState().with(Gloopy.GLOOPY, true), item)
            ).toList();

    public GloopyBlockItem(Settings settings) {
        super(Blocks.SAND, settings);
    }

    public static @NotNull ItemStack createBlockItemStack(@NotNull BlockState state, @NotNull Item item) {
        var stack = new ItemStack(item);
        NbtCompound stateNbt = NbtHelper.fromBlockState(state);
        stack.getOrCreateNbt().put(GLOOPY_STATE, stateNbt);
        return stack;
    }

    @Override
    public Text getName(@NotNull ItemStack stack) {
        var mutableText = MutableText.of(new LiteralTextContent("Gloopy "));
        mutableText.append(NbtHelper.toBlockState(stack.getOrCreateSubNbt(GLOOPY_STATE)).getBlock().getName());

        return mutableText;
    }

    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
        if (this.isIn(group)) {
            FALLING_BLOCK_STACKS.forEach(f -> stacks.add(f.apply(this)));
        }
    }

    @Nullable
    @Override
    protected BlockState getPlacementState(@NotNull ItemPlacementContext context) {
        NbtCompound nbt = context.getStack().getOrCreateSubNbt(GLOOPY_STATE);
        Block block = NbtHelper.toBlockState(nbt).getBlock();

        BlockState placementState = block.getPlacementState(context);

        if (placementState == null || placementState.isAir()) {
            return null;
        }

        return this.canPlace(context, placementState) ? placementState.with(Gloopy.GLOOPY, true) : null;
    }

}
