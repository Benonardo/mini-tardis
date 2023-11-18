package dev.enjarai.minitardis.canvas;

import dev.enjarai.minitardis.MiniTardis;
import eu.pb4.mapcanvas.api.core.CanvasImage;
import net.fabricmc.loader.api.FabricLoader;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.nio.file.Files;
import java.util.NoSuchElementException;

public class ModCanvasUtils {
    public static final CanvasImage SCREEN_BACKGROUND = loadImage("screen_background.png");

    private static CanvasImage loadImage(String filename) {
        try (var stream = Files.newInputStream(FabricLoader.getInstance().getModContainer(MiniTardis.MOD_ID).orElseThrow()
                .findPath("assets/" + MiniTardis.MOD_ID + "/textures/map/" + filename).orElseThrow())) {
            return CanvasImage.from(ImageIO.read(stream));
        } catch (IOException | NoSuchElementException e) {
            MiniTardis.LOGGER.error("Failed to load canvas image " + filename, e);
            return new CanvasImage(16, 16);
        }
    }

    public static void load() {
    }
}