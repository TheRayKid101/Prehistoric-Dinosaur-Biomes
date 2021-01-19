package net.eymbra.items;

import java.util.List;
import net.eymbra.gui.screen.PrehistoricReferenceBookScreenHandler;
import net.eymbra.prehistoric.EymbraPrehistoric;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.Util;
import net.minecraft.world.World;

public class PrehistoricReferenceBookItem extends Item {
	public PrehistoricReferenceBookItem(Settings settings) {
		super(settings);
	}

	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
		user.openHandledScreen(new SimpleNamedScreenHandlerFactory((ix, playerInventory, playerEntityx) -> {
			return new PrehistoricReferenceBookScreenHandler(ix, playerInventory);
		}, new TranslatableText("")));
		user.incrementStat(Stats.USED.getOrCreateStat(this));
		return TypedActionResult.success(itemStack, world.isClient());
	}
	
	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add((new TranslatableText("item.eymbra.ph_book.reference")).formatted(Formatting.GRAY));
		tooltip.add((new TranslatableText("item.eymbra.ph_book.reference2")).formatted(Formatting.GRAY));
	}

	public String getTranslationKey() {
		return Util.createTranslationKey("item", new Identifier(EymbraPrehistoric.MODID, "ph_book"));
	}
}