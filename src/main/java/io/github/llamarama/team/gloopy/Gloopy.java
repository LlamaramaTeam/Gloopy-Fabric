package io.github.llamarama.team.gloopy;

import io.github.llamarama.team.gloopy.common.event.EventHandler;
import io.github.llamarama.team.gloopy.common.register.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class Gloopy implements ModInitializer {

    public static final String MOD_ID = "gloopy";
    public static final BooleanProperty GLOOPY = BooleanProperty.of("gloopy");
    private static final Logger LOGGER = LogManager.getLogger("Gloopy");

    @Contract("_ -> new")
    public static @NotNull Identifier mod(String path) {
        return new Identifier(MOD_ID, path);
    }

    @Override
    public void onInitialize() {
        ModItems.init();
        UseBlockCallback.EVENT.register(EventHandler::onBlockUse);

        LOGGER.info("Let the gloop commence!");
    }

}
