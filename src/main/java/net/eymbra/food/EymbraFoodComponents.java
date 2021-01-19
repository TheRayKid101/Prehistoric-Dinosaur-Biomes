package net.eymbra.food;

import net.minecraft.item.FoodComponent;

public class EymbraFoodComponents {
	public static final FoodComponent HADROSAUR = (new FoodComponent.Builder()).hunger(4).saturationModifier(0.4F).meat().build();
	public static final FoodComponent COOKED_HADROSAUR = (new FoodComponent.Builder()).hunger(10).saturationModifier(1.0F).meat().build();
	
	public static final FoodComponent AKYLOSAURUS = (new FoodComponent.Builder()).hunger(3).saturationModifier(0.4F).meat().build();
	public static final FoodComponent COOKED_AKYLOSAURUS = (new FoodComponent.Builder()).hunger(9).saturationModifier(0.9F).meat().build();
}