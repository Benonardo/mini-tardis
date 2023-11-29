package dev.enjarai.minitardis.component.screen.app;

import com.mojang.serialization.Codec;
import dev.enjarai.minitardis.MiniTardis;
import dev.enjarai.minitardis.block.console.ConsoleScreenBlockEntity;
import dev.enjarai.minitardis.component.TardisControl;
import eu.pb4.mapcanvas.api.core.DrawableCanvas;
import eu.pb4.mapcanvas.api.utils.CanvasUtils;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ClickType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import org.joml.*;
import org.joml.Math;

public class RayTracingApp implements ScreenApp {

    public static final Codec<RayTracingApp> CODEC = Codec.unit(RayTracingApp::new);
    public static final Identifier ID = MiniTardis.id("ray_tracing");

    @Override
    public AppView getView(TardisControl controls) {
        return new View();
    }

    private static class View implements AppView {

        private static final Vector3fc RAY_ORIGIN = new Vector3f(0.0f, 0.0f, -2.0f);
        private final Vector3f rayDirection = new Vector3f();
        private final Vector4f pixelColor = new Vector4f();
        private final Vector3f hitPoint = new Vector3f();

        @Override
        public void draw(ConsoleScreenBlockEntity blockEntity, DrawableCanvas canvas) {
            var coord = new Vector2f();
            for (var x = 0; x < 128; x++) {
                for (var y = 0; y < 128; y++) {
                    // math
                    coord.set((float) x / 128 * 2.0f - 1.0f, (float) y / 128 * 2.0f - 1.0f);
                    perPixel(coord);
                    var color = ColorHelper.Argb.getArgb((int) (pixelColor.w * 255), (int) (pixelColor.x * 255), (int) (pixelColor.y * 255), (int) (pixelColor.z * 255));
                    canvas.set(x, y - (128 - 96) / 2, CanvasUtils.findClosestColorARGB(color));
                }
            }
        }

        private void perPixel(Vector2fc coord) {
            rayDirection.set(coord.x(), coord.y(), -1.0f);
            var radius = 0.5f;

            var a = rayDirection.dot(rayDirection);
            var b = 2.0f * RAY_ORIGIN.dot(rayDirection);
            var c = RAY_ORIGIN.dot(RAY_ORIGIN) - radius * radius;

            var discriminant = b * b - 4.0f * a * c;

            if (discriminant < 0.0f) {
                pixelColor.set(0.0f, 0.0f, 0.0f, 1.0f);
                return;
            }

            var closestT = (-b - Math.sqrt(discriminant)) / (2.0f * a);

            rayDirection.mul(closestT, hitPoint).add(RAY_ORIGIN);

            pixelColor.set(hitPoint.x, hitPoint.y, hitPoint.z, 1.0f);
        }

        @Override
        public boolean onClick(ConsoleScreenBlockEntity blockEntity, ServerPlayerEntity player, ClickType type, int x, int y) {
            return false;
        }
    }

    @Override
    public void drawIcon(TardisControl controls, ConsoleScreenBlockEntity blockEntity, DrawableCanvas canvas) {

    }

    @Override
    public Identifier id() {
        return ID;
    }
}
