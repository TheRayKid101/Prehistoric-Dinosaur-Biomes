package net.eymbra.gui.screen;

import net.eymbra.prehistoric.EymbraPrehistoric;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class EymbraScreenHanderType {
	public static final ScreenHandlerType<PrehistoricReferenceBookScreenHandler> PREHISTORIC_REFERENCE = ScreenHandlerRegistry.registerSimple(new Identifier(EymbraPrehistoric.MODID, "prehistoric_reference"), PrehistoricReferenceBookScreenHandler::new);

	public static void init() {
		ScreenRegistry.register(EymbraScreenHanderType.PREHISTORIC_REFERENCE, PrehistoricReferenceBookScreen::new);
	}
}