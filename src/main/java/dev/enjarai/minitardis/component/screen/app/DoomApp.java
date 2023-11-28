package dev.enjarai.minitardis.component.screen.app;

import com.mojang.serialization.Codec;
import dev.enjarai.minitardis.MiniTardis;
import dev.enjarai.minitardis.block.console.ConsoleScreenBlockEntity;
import dev.enjarai.minitardis.component.TardisControl;
import dev.enjarai.minitardis.mixin.DoomWindowControllerAccessor;
import eu.pb4.mapcanvas.api.core.CanvasImage;
import eu.pb4.mapcanvas.api.core.DrawableCanvas;
import eu.pb4.mapcanvas.api.utils.CanvasUtils;
import mochadoom.Engine;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ClickType;
import net.minecraft.util.Identifier;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DoomApp implements ScreenApp {

    public static final Codec<DoomApp> CODEC = Codec.unit(DoomApp::new);
    public static final Identifier ID = MiniTardis.id("doom");

    private static final Engine doom = Engine.getEngine();
    private static final Canvas doomCanvas = (Canvas)((DoomWindowControllerAccessor<?>)doom.windowController).getComponent();

    @Override
    public void draw(TardisControl controls, ConsoleScreenBlockEntity blockEntity, DrawableCanvas canvas) {
        var image = new BufferedImage(canvas.getWidth(), canvas.getHeight(), BufferedImage.TYPE_INT_RGB);
        var g2D = image.createGraphics();
        doomCanvas.printAll(g2D);
        g2D.dispose();
        var drawableImage = CanvasImage.from(image);
        CanvasUtils.draw(canvas, 0, 0, drawableImage);
    }

    @Override
    public boolean onClick(TardisControl controls, ConsoleScreenBlockEntity blockEntity, ServerPlayerEntity player, ClickType type, int x, int y) {
        return false;
    }

    @Override
    public void drawIcon(TardisControl controls, ConsoleScreenBlockEntity blockEntity, DrawableCanvas canvas) {

    }

    @Override
    public Identifier id() {
        return ID;
    }
}
