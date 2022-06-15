package io.github.llamarama.team.gloopy.mixin;

import io.github.llamarama.team.gloopy.Gloopy;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FallingBlock.class)
public abstract class MixinFallingBlock extends Block {

    public MixinFallingBlock(Settings settings) {
        super(settings);
    }

    @Inject(method = "scheduledTick", at = @At("HEAD"), cancellable = true)
    private void cancelFalling(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        if (state.get(Gloopy.GLOOPY)) {
            ci.cancel();
        }
    }

}
