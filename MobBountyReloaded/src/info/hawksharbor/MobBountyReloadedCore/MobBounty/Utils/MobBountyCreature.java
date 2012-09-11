package info.hawksharbor.MobBountyReloadedCore.MobBounty.Utils;

import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Giant;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Pig;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Snowman;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Squid;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;

public enum MobBountyCreature
{
	BLAZE("Blaze"), CAVESPIDER("CaveSpider"), CHICKEN("Chicken"), COW("Cow"), CREEPER(
			"Creeper"), ELECTRIFIED_CREEPER("ElectrifiedCreeper"), ENDERDRAGON(
			"Enderdragon"), ENDERMAN("Enderman"), GHAST("Ghast"), GIANT("Giant"), IRONGOLEM(
			"IronGolem"), MAGMACUBE("MagmaCube"), MONSTER("Monster"), MOOSHROOM(
			"Mooshroom"), OCELOT("Ocelot"), PIG("Pig"), PIG_ZOMBIE("PigZombie"), PLAYER(
			"Player"), SELF_TAMED_CAT("SelfTamedCat"), SELF_TAMED_WOLF(
			"SelfTamedWolf"), SHEEP("Sheep"), SILVERFISH("Silverfish"), SKELETON(
			"Skeleton"), SLIME("Slime"), SNOWGOLEM("SnowGolem"), SPIDER(
			"Spider"), SQUID("Squid"), TAMED_CAT("TamedCat"), TAMED_WOLF(
			"TamedWolf"), UNKNOWN("Unknown"), VILLAGER("Villager"), WOLF("Wolf"), ZOMBIE(
			"Zombie");

	public static MobBountyCreature fromName(String name)
	{
		for (MobBountyCreature id : MobBountyCreature.values())
		{
			if (id.name().equalsIgnoreCase(name))
				return id;
		}

		return null;
	}

	public static EntityType getEntityType(MobBountyCreature creature)
	{
		if (creature.equals(CHICKEN))
			return EntityType.CHICKEN;
		else if (creature.equals(PLAYER))
			return EntityType.PLAYER;
		else if (creature.equals(COW))
			return EntityType.COW;
		else if (creature.equals(MOOSHROOM))
			return EntityType.MUSHROOM_COW;
		else if (creature.equals(CREEPER))
			return EntityType.CREEPER;
		else if (creature.equals(ELECTRIFIED_CREEPER))
			return EntityType.CREEPER;
		else if (creature.equals(GHAST))
			return EntityType.GHAST;
		else if (creature.equals(GIANT))
			return EntityType.GIANT;
		else if (creature.equals(IRONGOLEM))
			return EntityType.IRON_GOLEM;
		else if (creature.equals(PIG))
			return EntityType.PIG;
		else if (creature.equals(PIG_ZOMBIE))
			return EntityType.PIG_ZOMBIE;
		else if (creature.equals(SHEEP))
			return EntityType.SHEEP;
		else if (creature.equals(SKELETON))
			return EntityType.SKELETON;
		else if (creature.equals(SLIME))
			return EntityType.SLIME;
		else if (creature.equals(EntityType.MAGMA_CUBE))
			return EntityType.MAGMA_CUBE;
		else if (creature.equals(SPIDER))
			return EntityType.SPIDER;
		else if (creature.equals(CAVESPIDER))
			return EntityType.CAVE_SPIDER;
		else if (creature.equals(OCELOT))
			return EntityType.OCELOT;
		else if (creature.equals(TAMED_CAT))
			return EntityType.OCELOT;
		else if (creature.equals(SELF_TAMED_CAT))
			return EntityType.OCELOT;
		else if (creature.equals(SQUID))
			return EntityType.SQUID;
		else if (creature.equals(WOLF))
			return EntityType.WOLF;
		else if (creature.equals(TAMED_WOLF))
			return EntityType.WOLF;
		else if (creature.equals(SELF_TAMED_WOLF))
			return EntityType.WOLF;
		else if (creature.equals(ZOMBIE))
			return EntityType.ZOMBIE;
		else if (creature.equals(SILVERFISH))
			return EntityType.SILVERFISH;
		else if (creature.equals(ENDERMAN))
			return EntityType.ENDERMAN;
		else if (creature.equals(BLAZE))
			return EntityType.BLAZE;
		else if (creature.equals(SNOWGOLEM))
			return EntityType.SNOWMAN;
		else if (creature.equals(VILLAGER))
			return EntityType.VILLAGER;
		else if (creature.equals(ENDERDRAGON))
			return EntityType.ENDER_DRAGON;

		return EntityType.UNKNOWN;
	}

	public static MobBountyCreature valueOf(Entity entity, String playerName)
	{
		if (entity instanceof Chicken)
			return MobBountyCreature.CHICKEN;
		else if (entity instanceof Player)
			return MobBountyCreature.PLAYER;
		else if (entity instanceof Cow)
		{
			if (entity.getType().equals(EntityType.MUSHROOM_COW))
				return MobBountyCreature.MOOSHROOM;
			return MobBountyCreature.COW;
		}
		else if (entity instanceof Creeper)
		{
			Creeper creeper = (Creeper) entity;

			if (creeper.isPowered())
				return MobBountyCreature.ELECTRIFIED_CREEPER;
			return MobBountyCreature.CREEPER;
		}
		else if (entity instanceof Ghast)
			return MobBountyCreature.GHAST;
		else if (entity instanceof Giant)
			return MobBountyCreature.GIANT;
		else if (entity instanceof IronGolem)
			return MobBountyCreature.IRONGOLEM;
		else if (entity instanceof Pig)
			return MobBountyCreature.PIG;
		else if (entity instanceof PigZombie)
			return MobBountyCreature.PIG_ZOMBIE;
		else if (entity instanceof Sheep)
			return MobBountyCreature.SHEEP;
		else if (entity instanceof Skeleton)
			return MobBountyCreature.SKELETON;
		else if (entity instanceof Slime)
		{
			if (entity.getType().equals(EntityType.MAGMA_CUBE))
				return MobBountyCreature.MAGMACUBE;
			return MobBountyCreature.SLIME;
		}
		else if (entity instanceof Spider)
		{
			if (entity.getType().equals(EntityType.CAVE_SPIDER))
				return MobBountyCreature.CAVESPIDER;
			return MobBountyCreature.SPIDER;
		}
		else if (entity instanceof Ocelot)
		{
			Ocelot ocelot = (Ocelot) entity;
			if (!ocelot.isTamed())
				return MobBountyCreature.OCELOT;
			else if (ocelot.getOwner() instanceof Player
					&& ((Player) ocelot.getOwner()).getName().equalsIgnoreCase(
							playerName))
				return MobBountyCreature.SELF_TAMED_CAT;
			else
				return MobBountyCreature.TAMED_CAT;
		}
		else if (entity instanceof Squid)
			return MobBountyCreature.SQUID;
		else if (entity instanceof Wolf)
		{
			Wolf wolf = (Wolf) entity;
			if (!wolf.isTamed())
				return MobBountyCreature.WOLF;
			else if (wolf.getOwner() instanceof Player
					&& ((Player) wolf.getOwner()).getName().equalsIgnoreCase(
							playerName))
				return MobBountyCreature.SELF_TAMED_WOLF;
			else
				return MobBountyCreature.TAMED_WOLF;
		}
		else if (entity instanceof Zombie)
			return MobBountyCreature.ZOMBIE;
		else if (entity instanceof Monster)
		{
			if (entity.getType().equals(EntityType.SILVERFISH))
				return MobBountyCreature.SILVERFISH;
			else if (entity.getType().equals(EntityType.ENDERMAN))
				return MobBountyCreature.ENDERMAN;
			else if (entity.getType().equals(EntityType.BLAZE))
				return MobBountyCreature.BLAZE;
			return MobBountyCreature.MONSTER;
		}
		else if (entity instanceof Ocelot)
			return MobBountyCreature.OCELOT;
		else if (entity instanceof Snowman)
			return MobBountyCreature.SNOWGOLEM;
		else if (entity instanceof Villager)
			return MobBountyCreature.VILLAGER;
		else if (entity instanceof EnderDragon)
			return MobBountyCreature.ENDERDRAGON;

		return MobBountyCreature.UNKNOWN;
	}

	private final String _name;

	private MobBountyCreature(final String name)
	{
		_name = name;
	}

	public String getName()
	{
		return _name;
	}
}
