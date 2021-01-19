package net.eymbra.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;

import net.eymbra.prehistoric.EymbraPrehistoric;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

public class PrehistoricReferenceBookScreen extends HandledScreen<PrehistoricReferenceBookScreenHandler> {
	private static final Identifier BOOK_LEFT = new Identifier(EymbraPrehistoric.MODID, "textures/gui/container/book_left.png");
	private static final Identifier BOOK_RIGHT = new Identifier(EymbraPrehistoric.MODID, "textures/gui/container/book_right.png");
	private static final Identifier CRAFTING_BANNER = new Identifier(EymbraPrehistoric.MODID, "textures/gui/container/crafting_banner.png");
	private static final Identifier CRAFTING_TABLE = new Identifier("textures/gui/container/crafting_table.png");

	public PrehistoricReferenceBookScreen(PrehistoricReferenceBookScreenHandler handler, PlayerInventory playerInventory, Text title) {
		super(handler, playerInventory, title);
	}

	protected void init() {
		super.init();

		int m = (this.width - 359) / 2;
		int n = (this.height - 228) / 2;
		this.addButton(new ButtonWidget(m + 271, n + 27, 80, 20, new TranslatableText("Next Page"), (buttonWidget) -> {

		}));

		this.addButton(new ButtonWidget(m + 5, n + 27, 80, 20, new TranslatableText("Back Page"), (buttonWidget) -> {

		}));
	}

	@Override
	protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
	}

	protected void drawMouseoverTooltip(MatrixStack matrices, int x, int y) {
		if (this.client.player.inventory.getCursorStack().isEmpty() && this.focusedSlot != null && this.focusedSlot.hasStack()) {
			this.renderTooltip(matrices, this.focusedSlot.getStack(), x, y);
		}
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		super.render(matrices, mouseX, mouseY, delta);

		this.drawMouseoverTooltip(matrices, mouseX, mouseY);
		
		//int ui = (this.width - 540) / 2;
		//int uo = (this.height - 256) / 2 - 15;
		
		//ui += 182;
		//uo += 60;
		
		//int r;
		for (int m = 0; m < this.handler.slots.size(); ++m) {
			Slot slot = (Slot) this.handler.slots.get(m);
			
			if (this.isPointOverSlot(slot, (double) mouseX, (double) mouseY) && slot.doDrawHoveringEffect()) {
				this.focusedSlot = slot;
				RenderSystem.disableDepthTest();
				//int n = slot.x;
				//r = slot.y;
//				RenderSystem.colorMask(true, true, true, false);
//				this.fillGradient(matrices, n + ui, r + uo, n + 16 + ui, r + 16 + uo, -2130706433, -2130706433);
//				RenderSystem.colorMask(true, true, true, true);
//				RenderSystem.enableDepthTest();
			}
		}
	}

	private boolean isPointOverSlot(Slot slot, double pointX, double pointY) {
		return this.isPointWithinBounds(slot.x, slot.y, 16, 16, pointX, pointY);
	}

	@Override
	protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.client.getTextureManager().bindTexture(BOOK_LEFT);

		int ui = (this.width - 540) / 2;
		int uo = (this.height - 256) / 2 - 15;

		int var5 = this.width - 359 >> 1;
		int var6 = this.height - 228 >> 1;

		this.drawTexture(matrices, ui + 68, uo + 16, 0, 0, 202, 256);

		this.client.getTextureManager().bindTexture(BOOK_RIGHT);
		this.drawTexture(matrices, ui + 270, uo + 16, 0, 0, 202, 256);

		this.client.getTextureManager().bindTexture(CRAFTING_BANNER);

		this.drawTexture(matrices, var5 + 79 + 20, var6 + 10, 0, 0, 157, 32);

		this.textRenderer.draw(matrices, new TranslatableText("Pre-Historic Mod"), var5 + 79 + 28, var6 + 20, 16777215);

		this.textRenderer.draw(matrices, new TranslatableText("Crafting Recipes"), var5 + 79 + 29, var6 + 30, 16777215);

		this.client.getTextureManager().bindTexture(CRAFTING_TABLE);

		int var8 = (359 - 8) / 3;
		int var9;
		for (var9 = 0; var9 < 3; var9++) {
			this.drawTexture(matrices, var5 + 4 + var9 * var8, var6 + 55, 4, 0, var8, 4);
			this.drawTexture(matrices, var5 + 4 + var9 * var8, var6 + 228 - 4, 4, 162, var8, 4);
		}

		var9 = (228 - 8) / 4;
		int var10;
		for (var10 = 0; var10 < 3; var10++) {
			this.drawTexture(matrices, var5, var6 + 4 + (var10 + 1) * var9, 0, 4, 4, var9);
			this.drawTexture(matrices, var5 + 359 - 4, var6 + 4 + (var10 + 1) * var9, 172, 4, 4, var9);
		}

		this.drawTexture(matrices, var5, var6 + 55, 0, 0, 4, 4);
		this.drawTexture(matrices, var5 + 4 + 351, var6 + 55, 172, 0, 4, 4);
		this.drawTexture(matrices, var5, var6 + 228 - 4, 0, 162, 4, 4);
		this.drawTexture(matrices, var5 + 359 - 4, var6 + 228 - 4, 172, 162, 4, 4);

		for (var10 = 0; var10 < 3; var10++) {
			for (int var11 = 0; var11 < 3; var11++)
				this.drawTexture(matrices, var5 + 4 + var10 * 117, var6 + 4 + (var11 + 1) * 55, 29, 15, 117, 55);
		}

//		this.drawTexture(matrices, var5 + 359 + 17, var6, 172, 0, 4, 4);
//		this.drawTexture(matrices, var5 + 359 + 17, var6 + 21, 172, 162, 4, 4);
//		this.drawTexture(matrices, var5 + 359 + 17, var6 + 4, 172, 4, 4, 17);
//		this.drawTexture(matrices, var5 + 359 - 4, var6, 4, 0, 22, 4);
//		this.drawTexture(matrices, var5 + 359, var6 + 21, 4, 162, 18, 4);
//		this.drawTexture(matrices, var5 + 359 - 1, var6 + 3, 29, 16, 18, 18);
	}
}