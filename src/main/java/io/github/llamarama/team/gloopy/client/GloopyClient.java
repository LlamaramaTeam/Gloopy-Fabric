package io.github.llamarama.team.gloopy.client;

import io.github.llamarama.team.gloopy.client.render.GloopyBlockItemRenderer;
import io.github.llamarama.team.gloopy.common.register.ModItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;

@Environment(EnvType.CLIENT)
public class GloopyClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BuiltinItemRendererRegistry.INSTANCE.register(ModItems.GLOOPY_ITEM, GloopyBlockItemRenderer.INSTANCE);
    }

}
