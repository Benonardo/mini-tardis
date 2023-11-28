package dev.enjarai.minitardis.mixin;

import awt.DoomWindow;
import awt.DoomWindowController;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.awt.*;

@Mixin(value = DoomWindowController.class, remap = false)
public interface DoomWindowControllerAccessor<E extends Component & DoomWindow<E>> {

    @Accessor
    E getComponent();

}
