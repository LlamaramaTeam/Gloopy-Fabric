package io.github.llamarama.team.gloopy;

import com.mojang.logging.LogUtils;
import io.github.llamarama.team.gloopy.common.event.EventHandler;
import io.github.llamarama.team.gloopy.common.register.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

public class Gloopy implements ModInitializer {

    public static final String MOD_ID = "gloopy";
    public static final BooleanProperty GLOOPY = BooleanProperty.of("gloopy");
    private static final Logger LOGGER = LogUtils.getLogger();

    @Contract("_ -> new")
    public static @NotNull Identifier mod(String path) {
        return new Identifier(MOD_ID, path);
    }

    public static Logger getLogger() {
        return LOGGER;
    }

    @Override
    public void onInitialize() {
        ModItems.init();
        UseBlockCallback.EVENT.register(EventHandler::onBlockUse);

        LOGGER.info("Let the gloop commence!");
    }

}
