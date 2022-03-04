package io.github.llamarama.team.gloopy.client.render;

import io.github.llamarama.team.gloopy.common.util.BlockStateEncode;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class GloopyBlockItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {

    public static final GloopyBlockItemRenderer INSTANCE = new GloopyBlockItemRenderer();

    private GloopyBlockItemRenderer() {

    }

    @Override
    public void render(@NotNull ItemStack stack, ModelTransformation.Mode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
        matrices.translate(.5d, .5d, .5d);
        BlockStateEncode.decode(stack.getOrCreateSubNbt("GloopyState")).ifPresent(it ->
                MinecraftClient.getInstance().getItemRenderer().renderItem(
                        it.getBlock().asItem().getDefaultStack(),
                        mode,
                        light,
                        overlay,
                        matrices,
                        vertexConsumers,
                        0
                ));
        matrices.pop();
    }

}
