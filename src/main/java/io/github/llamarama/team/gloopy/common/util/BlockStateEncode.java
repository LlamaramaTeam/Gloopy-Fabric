package io.github.llamarama.team.gloopy.common.util;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class BlockStateEncode {

    public static Optional<NbtElement> encode(@NotNull BlockState state) {
        return BlockState.CODEC.encode(state, NbtOps.INSTANCE, new NbtCompound()).result();
    }

    public static Optional<BlockState> decode(NbtCompound nbt) {
        return BlockState.CODEC.decode(new Dynamic<>(NbtOps.INSTANCE, nbt)).result().map(Pair::getFirst);
    }

}
