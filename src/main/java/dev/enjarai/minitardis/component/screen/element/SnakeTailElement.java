package dev.enjarai.minitardis.component.screen.element;

import dev.enjarai.minitardis.block.console.ConsoleScreenBlockEntity;
import dev.enjarai.minitardis.canvas.ModCanvasUtils;
import dev.enjarai.minitardis.component.TardisControl;
import eu.pb4.mapcanvas.api.core.DrawableCanvas;
import eu.pb4.mapcanvas.api.utils.CanvasUtils;
import eu.pb4.mapcanvas.impl.view.SubView;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ClickType;
import org.jetbrains.annotations.Nullable;

public class SnakeTailElement extends PlacedElement {
    private final SnakeElement snake;
    int tickCount;
    @Nullable
    SnakeTailElement nextSnailTail = null;

    public SnakeTailElement(SnakeElement snake) {
        super(-1, -1, 4, 4);
        this.snake = snake;
    }

    @Override
    protected void drawElement(TardisControl controls, ConsoleScreenBlockEntity blockEntity, DrawableCanvas canvas) {
        CanvasUtils.draw(canvas, 0, 0, ModCanvasUtils.SNAKE_TAIL);
    }

    public void drawAndPush(TardisControl controls, ConsoleScreenBlockEntity blockEntity, DrawableCanvas canvas, DrawableCanvas original) {
        this.drawElement(controls, blockEntity, canvas);
        if(nextSnailTail != null) {
            nextSnailTail.drawAndPush(
                    controls,
                    blockEntity,
                    new SubView(original, nextSnailTail.x, nextSnailTail.y, nextSnailTail.width, nextSnailTail.height),
                    original
            );
        }
    }

    public void moveToAndPush(int x, int y, int length) {
        if(nextSnailTail == null) {
            if(length < snake.tailLength) {
                System.out.println("length: " + length);
                System.out.println("tailLength: " + snake.tailLength);
                nextSnailTail = new SnakeTailElement(snake);
            }
        } else {
            nextSnailTail.moveToAndPush(this.x, this.y, ++length);
        }
        moveToAndPush(x, y);
    }

    public void moveToAndPush(int x, int y) {
        this.x = x;
        this.y = y;
    }



    @Override
    protected boolean onClickElement(TardisControl controls, ConsoleScreenBlockEntity blockEntity, ServerPlayerEntity player, ClickType type, int x, int y) {
        return false;
    }
}
