package net.eymbra.biomes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.UniformIntDistribution;
import net.minecraft.world.gen.feature.FeatureConfig;

public class RedDesertReplaceBlobsFeatureConfig implements FeatureConfig {
	public static final Codec<RedDesertReplaceBlobsFeatureConfig> CODEC = RecordCodecBuilder.create((instance) -> {
		return instance.group(BlockState.CODEC.fieldOf("target").forGetter((netherrackReplaceBlobsFeatureConfig) -> {
			return netherrackReplaceBlobsFeatureConfig.target;
		}), BlockState.CODEC.fieldOf("state").forGetter((netherrackReplaceBlobsFeatureConfig) -> {
			return netherrackReplaceBlobsFeatureConfig.state;
		}), UniformIntDistribution.CODEC.fieldOf("radius").forGetter((netherrackReplaceBlobsFeatureConfig) -> {
			return netherrackReplaceBlobsFeatureConfig.radius;
		})).apply(instance, RedDesertReplaceBlobsFeatureConfig::new);
	});
	public final BlockState target;
	public final BlockState state;
	private final UniformIntDistribution radius;

	public RedDesertReplaceBlobsFeatureConfig(BlockState target, BlockState state, UniformIntDistribution radius) {
		this.target = target;
		this.state = state;
		this.radius = radius;
	}

	public UniformIntDistribution getRadius() {
		return this.radius;
	}
}
