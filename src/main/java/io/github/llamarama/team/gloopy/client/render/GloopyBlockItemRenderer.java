package io.github.llamarama.team.gloopy.client.render;

import io.github.llamarama.team.gloopy.common.item.GloopyBlockItem;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.NotNull;

public class GloopyBlockItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {

    public static final GloopyBlockItemRenderer INSTANCE = new GloopyBlockItemRenderer();

    private GloopyBlockItemRenderer() {

    }

    @Override
    public void render(@NotNull ItemStack stack, ModelTransformation.Mode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
        matrices.translate(.5d, .5d, .5d);
        var block = Registry.BLOCK
                .getOrEmpty(new Identifier(stack.getOrCreateNbt().getString(GloopyBlockItem.GLOOPY_BLOCK)))
                .orElse(Blocks.AIR);
        MinecraftClient.getInstance().getItemRenderer().renderItem(
                block.asItem().getDefaultStack(), mode,
                light, overlay,
                matrices, vertexConsumers, 0
        );
        matrices.pop();
    }

}
