package dev.enjarai.minitardis.component.screen.app;

import com.mojang.serialization.Codec;
import dev.enjarai.minitardis.MiniTardis;
import dev.enjarai.minitardis.block.console.ConsoleScreenBlockEntity;
import dev.enjarai.minitardis.component.TardisControl;
import eu.pb4.mapcanvas.api.core.CanvasColor;
import eu.pb4.mapcanvas.api.core.DrawableCanvas;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ClickType;
import net.minecraft.util.Identifier;
import org.joml.Vector2f;
import org.joml.Vector2fc;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public class RayTracingApp implements ScreenApp {

    public static final Codec<RayTracingApp> CODEC = Codec.unit(RayTracingApp::new);
    public static final Identifier ID = MiniTardis.id("ray_tracing");

    private static final Vector3fc RAY_ORIGIN = new Vector3f(0.0f, 0.0f, -2.0f);

    @Override
    public void draw(TardisControl controls, ConsoleScreenBlockEntity blockEntity, DrawableCanvas canvas) {
        var coord = new Vector2f();
        for (var x = 0; x < 128; x++) {
            for (var y = 0; y < 96; y++) {
                coord.set((float) x / 128 * 2.0f - 1.0f, (float) y / 96 * 2.0f - ((float) 96 / 128));
                canvas.set(x, y, perPixel(coord));
            }
        }
    }

    private CanvasColor perPixel(Vector2fc coord) {
        var rayDirection = new Vector3f(coord.x(), coord.y(), -1.0f);
        var radius = 0.5f;

        var a = rayDirection.dot(rayDirection);
        var b = 2.0f * RAY_ORIGIN.dot(rayDirection);
        var c = RAY_ORIGIN.dot(RAY_ORIGIN) - radius * radius;

        var discriminant = b * b - 4.0f * a * c;

        if (discriminant >= 0.0f)
            return CanvasColor.PINK_NORMAL;

        return CanvasColor.BLACK_NORMAL;
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
