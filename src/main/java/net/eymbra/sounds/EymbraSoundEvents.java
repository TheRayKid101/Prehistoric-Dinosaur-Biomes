package net.eymbra.sounds;

import net.eymbra.prehistoric.EymbraPrehistoric;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EymbraSoundEvents {
	public static final SoundEvent AMBIENCE_PREHISTORIC_SNOW_MOUNTAIN = register("music.prehistoric.snow_mountain");
	public static final SoundEvent AMBIENCE_PREHISTORIC_RAINFOREST = register("music.prehistoric.rainforest");
	public static final SoundEvent AMBIENCE_PREHISTORIC_RED_DESERT = register("red_desert");
	public static final SoundEvent AMBIENCE_PREHISTORIC_BOG = register("bog");
	public static final SoundEvent ADDITIONAL_PREHISTORIC_RAINFOREST = register("additional.prehistoric.rainforest");
	public static final SoundEvent HADROSAUR_AMBIENT = register("ambient.hadrosaur.hadrosaur_ambient");
	public static final SoundEvent HADROSAUR_HURT = register("ambient.hadrosaur.hadrosaur_hurt");
	public static final SoundEvent HADROSAUR_DEATH = register("ambient.hadrosaur.hadrosaur_death");
	public static final SoundEvent PACHYCEPALOSAURUS_AMBIENT = register("ambient.pachycepalosaurus.pachycepalosaurus_ambient");
	public static final SoundEvent PACHYCEPALOSAURUS_HURT = register("ambient.pachycepalosaurus.pachycepalosaurus_hurt");
	public static final SoundEvent PACHYCEPALOSAURUS_DEATH = register("ambient.pachycepalosaurus.pachycepalosaurus_death");
	public static final SoundEvent TIME_MACHINE_OPEN = register("time_machine_open");
	public static final SoundEvent TIME_MACHINE_CLOSE = register("time_machine_close");
	public static final SoundEvent ANKYLOSAURUS_AMBIENT = register("ambient.ankylosaurus.ankylosaurus_ambient");
	public static final SoundEvent ANKYLOSAURUS_HURT = register("ambient.ankylosaurus.ankylosaurus_hurt");
	public static final SoundEvent ANKYLOSAURUS_DEATH = register("ambient.ankylosaurus.ankylosaurus_death");

	public static final PositionedSoundInstance TM_OPEN = PositionedSoundInstance.ambient(EymbraSoundEvents.TIME_MACHINE_OPEN);
	public static final PositionedSoundInstance TM_CLOSE = PositionedSoundInstance.ambient(EymbraSoundEvents.TIME_MACHINE_CLOSE);

	private static SoundEvent register(String id) {
		return (SoundEvent) Registry.register(Registry.SOUND_EVENT, new Identifier(EymbraPrehistoric.MODID, id), new SoundEvent(new Identifier(EymbraPrehistoric.MODID, id)));
	}
}