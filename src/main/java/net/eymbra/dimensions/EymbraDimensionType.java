/*
 * Copyright (c) 2020, 2021 Raymond Bennett. All rights reserved.
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */
package net.eymbra.dimensions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.OptionalLong;
import java.util.function.BiFunction;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeAccessType;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.biome.source.VoronoiBiomeAccessType;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;

/**
 * <p>
 * Custom {@code DimensionType} carrier used to pass along dimension object data
 * to the {@code Mixin} to create a custom dimension. Requires manual
 * registration of the registry key and the dimension generator by using the
 * newly created {@code DimensionType} as the identifier.
 * </p>
 * 
 * @author TheRayKid101
 */
public class EymbraDimensionType extends DimensionType {
	public static final ArrayList<Pair<EymbraDimensionType, RegistryKey<DimensionType>>> DIMENSION_REGISTRY = new ArrayList<Pair<EymbraDimensionType, RegistryKey<DimensionType>>>();
	public static final HashMap<RegistryKey<DimensionType>, BiFunction<Registry<Biome>, Long, BiomeSource>> DIMENSION_PRESET_REGISTRY = new HashMap<RegistryKey<DimensionType>, BiFunction<Registry<Biome>, Long, BiomeSource>>();
	public static final HashMap<RegistryKey<DimensionType>, RegistryKey<DimensionOptions>> DIMENSION_OPTIONS_REGISTRY = new HashMap<RegistryKey<DimensionType>, RegistryKey<DimensionOptions>>();
	public static final HashMap<RegistryKey<DimensionType>, RegistryKey<World>> DIMENSION_WORLD_REGISTRY = new HashMap<RegistryKey<DimensionType>, RegistryKey<World>>();
	public static final HashMap<RegistryKey<DimensionType>, DimensionType> DIMENSION_TYPE_REGISTRY = new HashMap<RegistryKey<DimensionType>, DimensionType>();
	private final OptionalLong fixedTime;
	private final boolean hasSkyLight;
	private final boolean hasCeiling;
	private final boolean ultrawarm;
	private final boolean natural;
	private final double coordinateScale;
	private final boolean hasEnderDragonFight;
	private final boolean piglinSafe;
	private final boolean bedWorks;
	private final boolean respawnAnchorWorks;
	private final boolean hasRaids;
	private final int logicalHeight;
	private final BiomeAccessType biomeAccessType;
	private final Identifier infiniburn;
	private final Identifier skyProperties;
	private final float ambientLight;
	private final transient float[] field_24767;

	/**
	 * Constructs a dimension type with the specified initial conditions.
	 *
	 * @param fixedTime an {@code OptionalLong} of the time the dimension will rest at permanently, leave void to follow the time of the surface world
	 * @param hasSkylight the condition on whether to keep the world lit by the sun or an artificial brightness for the player
	 * @param hasCeiling the condition on whether the world has a roof over the player
	 * @param ultrawarm the condition on whether the world is warmer than the surface world, prevents water from accumulating, instant evaporation
	 * @param natural the condition that determines whether the dimension will follow the same rules and laws of the surface world
	 * @param coordinateScale the jump one block in this custom dimension will result in the surface world, e.g. the <i>Nether</i> is 8
	 * @param piglinSafe the condition that determines whether <i>Piglins</i> will turn to <i>Zombies</i> in this dimension
	 * @param bedWorks the condition that determines whether beds will explode
	 * @param respawnAnchorWorks the condition that determines whether respawn anchors work in this dimension
	 * @param hasRaids the condition that determines whether the dimension is capable of raids
	 * @param logicalHeight the intial parameter for the height of this world
	 * @param infiniburn the parameter that determines whether fire will burn indefinetly
	 * @param skyProperties the identifier for the sky properties registry key
	 * @param ambientLight the intial parameter for the world's natural light
	 */
	protected EymbraDimensionType(OptionalLong fixedTime, boolean hasSkylight, boolean hasCeiling, boolean ultrawarm, boolean natural, double coordinateScale, boolean piglinSafe, boolean bedWorks, boolean respawnAnchorWorks, boolean hasRaids, int logicalHeight, Identifier infiniburn, Identifier skyProperties, float ambientLight) {
		this(fixedTime, hasSkylight, hasCeiling, ultrawarm, natural, coordinateScale, false, piglinSafe, bedWorks, respawnAnchorWorks, hasRaids, logicalHeight, VoronoiBiomeAccessType.INSTANCE, infiniburn, skyProperties, ambientLight);
	}

	/**
	 * Constructs a dimension type with the specified initial conditions.
	 *
	 * @param fixedTime an {@code OptionalLong} of the time the dimension will rest at permanently, leave void to follow the time of the surface world
	 * @param hasSkylight the condition on whether to keep the world lit by the sun or an artificial brightness for the player
	 * @param hasCeiling the condition on whether the world has a roof over the player
	 * @param ultrawarm the condition on whether the world is warmer than the surface world, prevents water from accumulating, instant evaporation
	 * @param natural the condition that determines whether the dimension will follow the same rules and laws of the surface world
	 * @param coordinateScale the jump one block in this custom dimension will result in the surface world, e.g. the <i>Nether</i> is 8
	 * @param hasEnderDragonFight the condition that determines whether there will be an ender dragon fight
	 * @param piglinSafe the condition that determines whether <i>Piglins</i> will turn to <i>Zombies</i> in this dimension
	 * @param bedWorks the condition that determines whether beds will explode
	 * @param respawnAnchorWorks the condition that determines whether respawn anchors work in this dimension
	 * @param hasRaids the condition that determines whether the dimension is capable of raids
	 * @param logicalHeight the intial parameter for the height of this world
	 * @param biomeAccessType the parameter for the <i>Minecraft</i> {@code VoronoiBiomeAccessType} instance
	 * @param infiniburn the parameter that determines whether fire will burn indefinetly
	 * @param skyProperties the identifier for the sky properties registry key
	 * @param ambientLight the intial parameter for the world's natural light
	 */
	protected EymbraDimensionType(OptionalLong fixedTime, boolean hasSkylight, boolean hasCeiling, boolean ultrawarm, boolean natural, double coordinateScale, boolean hasEnderDragonFight, boolean piglinSafe, boolean bedWorks, boolean respawnAnchorWorks, boolean hasRaids, int logicalHeight, BiomeAccessType biomeAccessType, Identifier infiniburn, Identifier skyProperties, float ambientLight) {
		super(fixedTime, hasSkylight, hasCeiling, ultrawarm, natural, coordinateScale, hasEnderDragonFight, piglinSafe, bedWorks, respawnAnchorWorks, hasRaids, logicalHeight, biomeAccessType, infiniburn, skyProperties, ambientLight);
		this.fixedTime = fixedTime;
		this.hasSkyLight = hasSkylight;
		this.hasCeiling = hasCeiling;
		this.ultrawarm = ultrawarm;
		this.natural = natural;
		this.coordinateScale = coordinateScale;
		this.hasEnderDragonFight = hasEnderDragonFight;
		this.piglinSafe = piglinSafe;
		this.bedWorks = bedWorks;
		this.respawnAnchorWorks = respawnAnchorWorks;
		this.hasRaids = hasRaids;
		this.logicalHeight = logicalHeight;
		this.biomeAccessType = biomeAccessType;
		this.infiniburn = infiniburn;
		this.skyProperties = skyProperties;
		this.ambientLight = ambientLight;
		this.field_24767 = method_28515(ambientLight);
	}

	/**
	 * The static registration that ties the mandatory registry key to the dimension type created using this
	 * facade carrier.
	 * 
	 * @param dimensionType the dimension type that acts as a key for the modifier
	 * @param dimensionRegistryKey the registry key that will tie with this newly created dimension type
	 */
	public static void registerDimensionType(EymbraDimensionType dimensionType, RegistryKey<DimensionType> dimensionRegistryKey) {
		DIMENSION_REGISTRY.add(new Pair<EymbraDimensionType, RegistryKey<DimensionType>>(dimensionType, dimensionRegistryKey));
	}

	/**
	 * The static registration that ties the mandatory generation object to the dimension type created using this
	 * facade carrier.
	 * 
	 * @param dimensionRegistryKey the dimension type that acts as a key for the modifier
	 * @param dimensionGenerator the dimension generator that will tie with this newly created dimension type
	 */
	public static void registerDimensionGenerator(RegistryKey<DimensionType> dimensionRegistryKey, BiFunction<Registry<Biome>, Long, BiomeSource> dimensionGenerator) {
		DIMENSION_PRESET_REGISTRY.put(dimensionRegistryKey, dimensionGenerator);
	}

	private static float[] method_28515(float f) {
		float[] fs = new float[16];

		for (int i = 0; i <= 15; ++i) {
			float g = (float) i / 15.0F;
			float h = g / (4.0F - 3.0F * g);
			fs[i] = MathHelper.lerp(f, h, 1.0F);
		}

		return fs;
	}

	// Accessor for the private ambient light field
	public float getAmbientLight() {
		return this.ambientLight;
	}
	
	/*
	 * * * * * * * * * * * * * * * * * * * * * *
	 * 
	 * OVERRIDEN ACCESSOR METHODS FOR PRIVATE FIELDS
	 * 
	 * * * * * * * * * * * * * * * * * * * * * *
	 */

	@Override
	public boolean hasSkyLight() {
		return this.hasSkyLight;
	}

	@Override
	public boolean hasCeiling() {
		return this.hasCeiling;
	}

	@Override
	public boolean isUltrawarm() {
		return this.ultrawarm;
	}

	@Override
	public boolean isNatural() {
		return this.natural;
	}

	@Override
	public double getCoordinateScale() {
		return this.coordinateScale;
	}

	@Override
	public boolean isPiglinSafe() {
		return this.piglinSafe;
	}

	@Override
	public boolean isBedWorking() {
		return this.bedWorks;
	}

	@Override
	public boolean isRespawnAnchorWorking() {
		return this.respawnAnchorWorks;
	}

	@Override
	public boolean hasRaids() {
		return this.hasRaids;
	}

	@Override
	public int getLogicalHeight() {
		return this.logicalHeight;
	}

	@Override
	public boolean hasEnderDragonFight() {
		return this.hasEnderDragonFight;
	}

	@Override
	public BiomeAccessType getBiomeAccessType() {
		return this.biomeAccessType;
	}

	@Override
	public boolean hasFixedTime() {
		return this.fixedTime.isPresent();
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Tag<Block> getInfiniburnBlocks() {
		Tag<Block> tag = BlockTags.getTagGroup().getTag(this.infiniburn);
		return (Tag) (tag != null ? tag : BlockTags.INFINIBURN_OVERWORLD);
	}

	@Override
	public float method_28516(int i) {
		return this.field_24767[i];
	}

	@Override
	@Environment(EnvType.CLIENT)
	public Identifier getSkyProperties() {
		return this.skyProperties;
	}
}