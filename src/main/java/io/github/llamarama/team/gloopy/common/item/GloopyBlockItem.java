package io.github.llamarama.team.gloopy.common.item;

import io.github.llamarama.team.gloopy.Gloopy;
import io.github.llamarama.team.gloopy.common.util.BlockStateEncode;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FallingBlock;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GloopyBlockItem extends BlockItem {

    private static final Set<Function<GloopyBlockItem, ItemStack>> FALLING_BLOCK_STACKS = Registry.BLOCK.stream()
            .filter(it -> it instanceof FallingBlock)
            .map(it -> (FallingBlock) it)
            .map(Block::getStateManager)
            .filter(it -> it.getProperties().contains(Gloopy.GLOOPY))
            .map(it -> (Function<GloopyBlockItem, ItemStack>) (item) ->
                    createBlockItemStack(it.getDefaultState().with(Gloopy.GLOOPY, true), item)
            ).collect(Collectors.toSet());

    public GloopyBlockItem(Settings settings) {
        super(Blocks.WHITE_WOOL, settings);
    }

    public static @NotNull ItemStack createBlockItemStack(@NotNull BlockState state, @NotNull Item item) {
        var stack = new ItemStack(item);
        Optional<NbtElement> stateNbt = BlockStateEncode.encode(state);
        stateNbt.ifPresent(res -> stack.getOrCreateNbt().put("GloopyState", res));
        return stack;
    }

    @Nullable
    @Override
    protected BlockState getPlacementState(@NotNull ItemPlacementContext context) {
        NbtCompound nbt = context.getStack().getOrCreateSubNbt("GloopyState");
        return BlockStateEncode.decode(nbt).orElseGet(Blocks.STONE::getDefaultState);
    }

    @Override
    public Text getName(@NotNull ItemStack stack) {
        var mutableText = new LiteralText("Gloopy ");
        mutableText.append(BlockStateEncode.decode(stack.getOrCreateSubNbt("GloopyState"))
                .map(it -> it.getBlock().getName())
                .orElseGet(() -> new TranslatableText("item.gloopy.block_item"))
        );

        return mutableText;
    }

    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
        FALLING_BLOCK_STACKS.forEach(f -> stacks.add(f.apply(this)));
    }

}
