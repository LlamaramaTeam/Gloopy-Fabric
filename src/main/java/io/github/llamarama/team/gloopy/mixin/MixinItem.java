package io.github.llamarama.team.gloopy.mixin;

import io.github.llamarama.team.gloopy.common.block.GloopyBlock;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class MixinItem {

    @Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
    private void addHoneyBottleGloop(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        var this$ = (Item) (Object) this;
        if (this$ == Items.HONEY_BOTTLE) {
            World world = context.getWorld();
            BlockPos blockPos = context.getBlockPos();
            BlockState blockState = world.getBlockState(blockPos);

            cir.setReturnValue(GloopyBlock.from(blockState).map(state -> {
                PlayerEntity playerEntity = context.getPlayer();
                Hand hand = Hand.MAIN_HAND;
                ItemStack itemStack = context.getStack();

                if (playerEntity instanceof ServerPlayerEntity serverPlayer) {
                    Criteria.ITEM_USED_ON_BLOCK.trigger(serverPlayer, blockPos, itemStack);
                }

                if (playerEntity != null) {
                    var newStack = ItemUsage.exchangeStack(
                            playerEntity.getStackInHand(hand),
                            playerEntity,
                            Items.GLASS_BOTTLE.getDefaultStack()
                    );

                    playerEntity.setStackInHand(hand, newStack);
                }

                world.setBlockState(blockPos, state);
                world.syncWorldEvent(playerEntity, WorldEvents.BLOCK_WAXED, blockPos, 0);
                return ActionResult.success(world.isClient);
            }).orElse(ActionResult.PASS));
        }
    }

}
