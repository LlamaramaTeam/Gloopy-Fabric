package io.github.llamarama.team.gloopy.mixin;

import io.github.llamarama.team.gloopy.Gloopy;
import io.github.llamarama.team.gloopy.common.item.GloopyBlockItem;
import io.github.llamarama.team.gloopy.common.register.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.function.LootFunction;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;
import java.util.function.Consumer;

@Mixin(ItemEntry.class)
public abstract class MixinItemEntry extends LeafEntry {

    protected MixinItemEntry(int weight, int quality, LootCondition[] conditions, LootFunction[] functions) {
        super(weight, quality, conditions, functions);
    }

    @Inject(method = "generateLoot", at = @At("HEAD"), cancellable = true)
    private void createGloopyItems(Consumer<ItemStack> lootConsumer, @NotNull LootContext context, CallbackInfo ci) {
        BlockState state = context.get(LootContextParameters.BLOCK_STATE);
        Optional.ofNullable(context.get(LootContextParameters.TOOL))
                .filter(it -> EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, it) > 0)
                .ifPresent(it -> {
                    if (state != null && state.getBlock() instanceof FallingBlock && state.get(Gloopy.GLOOPY)) {
                        lootConsumer.accept(GloopyBlockItem.createBlockItemStack(state, ModItems.GLOOPY_ITEM));
                        ci.cancel();
                    }
                });
    }


}
