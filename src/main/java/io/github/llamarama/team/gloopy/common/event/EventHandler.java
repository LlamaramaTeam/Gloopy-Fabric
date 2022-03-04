package io.github.llamarama.team.gloopy.common.event;

import io.github.llamarama.team.gloopy.Gloopy;
import io.github.llamarama.team.gloopy.common.tag.ModBlockTags;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.item.ShovelItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import org.jetbrains.annotations.NotNull;

public final class EventHandler {

    public static ActionResult onBlockUse(@NotNull PlayerEntity playerEntity, @NotNull World world, Hand hand, @NotNull BlockHitResult blockHitResult) {
        BlockPos blockPos = blockHitResult.getBlockPos();
        BlockState blockState = world.getBlockState(blockPos);
        var handStack = playerEntity.getStackInHand(hand);

        if (blockState.getBlock() instanceof FallingBlock && !ModBlockTags.isBlacklisted(blockState.getBlock())) {
            if (handStack.isOf(Items.HONEY_BOTTLE) && !blockState.get(Gloopy.GLOOPY)) {
                if (playerEntity instanceof ServerPlayerEntity serverPlayer) {
                    Criteria.ITEM_USED_ON_BLOCK.trigger(serverPlayer, blockPos, handStack);
                }

                var newStack = ItemUsage.exchangeStack(
                        playerEntity.getStackInHand(hand),
                        playerEntity,
                        Items.GLASS_BOTTLE.getDefaultStack()
                );

                playerEntity.setStackInHand(hand, newStack);

                world.setBlockState(blockPos, blockState.with(Gloopy.GLOOPY, true));
                world.syncWorldEvent(playerEntity, WorldEvents.BLOCK_WAXED, blockPos, 0);
                return ActionResult.success(world.isClient);
            } else if (handStack.getItem() instanceof ShovelItem && blockState.get(Gloopy.GLOOPY)) {
                if (playerEntity instanceof ServerPlayerEntity serverPlayer) {
                    Criteria.ITEM_USED_ON_BLOCK.trigger(serverPlayer, blockPos, playerEntity.getStackInHand(hand));
                }

                playerEntity.getStackInHand(hand).damage(1, playerEntity,
                        entity -> entity.sendToolBreakStatus(hand)
                );
                world.playSound(null, blockPos, SoundEvents.ITEM_AXE_WAX_OFF, SoundCategory.BLOCKS,
                        1.0f, 1.0f
                );
                world.syncWorldEvent(WorldEvents.WAX_REMOVED, blockPos, 0);
                world.setBlockState(blockPos, blockState.with(Gloopy.GLOOPY, false));
                return ActionResult.success(world.isClient);
            }
        }


        return ActionResult.PASS;
    }

}
