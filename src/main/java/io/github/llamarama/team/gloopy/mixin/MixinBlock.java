package io.github.llamarama.team.gloopy.mixin;

import io.github.llamarama.team.gloopy.Gloopy;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.state.StateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(Block.class)
public abstract class MixinBlock {

    @Shadow
    private BlockState defaultState;

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/state/StateManager$Builder;build(Ljava/util/function/Function;Lnet/minecraft/state/StateManager$Factory;)Lnet/minecraft/state/StateManager;", shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILHARD)
    private void addCustomProperty(AbstractBlock.Settings settings, CallbackInfo ci, StateManager.Builder<Block, BlockState> builder) {
        var this$ = (Block) (Object) this;
        if (this$ instanceof FallingBlock) {
            builder.add(Gloopy.GLOOPY);
        }
    }

    @Redirect(method = "getDefaultState", at = @At(value = "FIELD", target = "Lnet/minecraft/block/Block;defaultState:Lnet/minecraft/block/BlockState;"))
    private BlockState changeField(Block instance) {
        if (instance instanceof FallingBlock) {
            return this.defaultState.with(Gloopy.GLOOPY, false);
        }

        return this.defaultState;
    }

}
