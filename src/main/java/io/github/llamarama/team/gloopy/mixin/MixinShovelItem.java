package io.github.llamarama.team.gloopy.mixin;

import io.github.llamarama.team.gloopy.common.block.GloopyBlock;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ShovelItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.world.WorldEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShovelItem.class)
public abstract class MixinShovelItem extends Item {

    public MixinShovelItem(Settings settings) {
        super(settings);
    }

    @Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
    public void useOnBlockInject(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        var world = context.getWorld();
        var pos = context.getBlockPos();
        var state = world.getBlockState(pos);
        var player = context.getPlayer();

        if (state.getBlock() instanceof GloopyBlock gloopyBlock && player != null) {
            if (player instanceof ServerPlayerEntity serverPlayer) {
                Criteria.ITEM_USED_ON_BLOCK.trigger(serverPlayer, pos, player.getStackInHand(context.getHand()));
            }

            player.getStackInHand(context.getHand()).damage(1, player,
                    entity -> entity.sendToolBreakStatus(context.getHand()));
            world.playSound(null, pos, SoundEvents.ITEM_AXE_WAX_OFF, SoundCategory.BLOCKS,
                    1.0f, 1.0f
            );
            world.syncWorldEvent(WorldEvents.WAX_REMOVED, pos, 0);
            world.setBlockState(pos, gloopyBlock.getNormalVariant().getDefaultState());
            cir.setReturnValue(ActionResult.success(world.isClient));
        }
    }

}
