package org.inesgar.MobBountyReloaded;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.World.Environment;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.inesgar.MobBountyReloaded.utils.MobBountyCreature;
import org.inesgar.MobBountyReloaded.utils.MobBountyTime;
import org.inesgar.MobBountyReloaded.utils.configuration.MobBountyReloadedConfFile;

public class MBI
{

	private MobBountyReloaded mbr;

	public MBI(MobBountyReloaded mbr)
	{
		setMBR(mbr);
	}

	public double getValue(String playerName, MobBountyCreature creature)
	{
		Player player = Bukkit.getServer().getPlayer(playerName);
		if (player == null)
		{
			return 0.0;
		}
		String wn = player.getWorld().getName();
		String string = getMBR().getConfigManager().getProperty(
				MobBountyReloadedConfFile.REWARDS,
				wn + "." + creature.getName() + ".value");
		if (string == null)
		{
			string = getMBR().getConfigManager().getProperty(
					MobBountyReloadedConfFile.REWARDS,
					"Default." + creature.getName() + ".value");
			if (string == null)
			{
				string = "0.0";
				getMBR().getConfigManager().setProperty(
						MobBountyReloadedConfFile.REWARDS,
						"Default." + creature.getName() + ".value",
						new Double(0.0));
			}
		}
		double baseReward = 0.0;
		if (string != null)
		{
			if (string.contains(":"))
			{
				String[] resultRange = string.split(":");

				Random rand = new Random();
				int range;
				int loc;

				if (Double.valueOf(resultRange[0]) > Double
						.valueOf(resultRange[1]))
				{
					range = (int) ((Double.valueOf(resultRange[0]) * 100) - (Double
							.valueOf(resultRange[1]) * 100));
					loc = (int) (Double.valueOf(resultRange[1]) * 100);
				}
				else
				{
					range = (int) ((Double.valueOf(resultRange[1]) * 100) - (Double
							.valueOf(resultRange[0]) * 100));
					loc = (int) (Double.valueOf(resultRange[0]) * 100);
				}

				baseReward = (((double) (loc + rand.nextInt(range + 1))) / 100);
			}
			else
			{
				baseReward = Double.valueOf(string);
			}
		}
		return baseReward;
	}

	public double getValue(Player player, MobBountyCreature creature)
	{
		String wn = player.getWorld().getName();
		String string = getMBR().getConfigManager().getProperty(
				MobBountyReloadedConfFile.REWARDS,
				wn + "." + creature.getName() + ".value");
		if (string == null)
		{
			string = getMBR().getConfigManager().getProperty(
					MobBountyReloadedConfFile.REWARDS,
					"Default." + creature.getName() + ".value");
			if (string == null)
			{
				string = "0.0";
				getMBR().getConfigManager().setProperty(
						MobBountyReloadedConfFile.REWARDS,
						"Default." + creature.getName() + ".value",
						new Double(0.0));
			}
		}
		double baseReward = 0.0;
		if (string != null)
		{
			if (string.contains(":"))
			{
				String[] resultRange = string.split(":");

				Random rand = new Random();
				int range;
				int loc;

				if (Double.valueOf(resultRange[0]) > Double
						.valueOf(resultRange[1]))
				{
					range = (int) ((Double.valueOf(resultRange[0]) * 100) - (Double
							.valueOf(resultRange[1]) * 100));
					loc = (int) (Double.valueOf(resultRange[1]) * 100);
				}
				else
				{
					range = (int) ((Double.valueOf(resultRange[1]) * 100) - (Double
							.valueOf(resultRange[0]) * 100));
					loc = (int) (Double.valueOf(resultRange[0]) * 100);
				}

				baseReward = (((double) (loc + rand.nextInt(range + 1))) / 100);
			}
			else
			{
				baseReward = Double.valueOf(string);
			}
		}
		return baseReward;
	}

	public double getValue(Player player, LivingEntity entity)
	{
		MobBountyCreature creature = MobBountyCreature.valueOf(entity, "");
		String wn = entity.getWorld().getName();
		String string = getMBR().getConfigManager().getProperty(
				MobBountyReloadedConfFile.REWARDS,
				wn + "." + creature.getName() + ".value");
		if (string == null)
		{
			string = getMBR().getConfigManager().getProperty(
					MobBountyReloadedConfFile.REWARDS,
					"Default." + creature.getName() + ".value");
			if (string == null)
			{
				string = "0.0";
				getMBR().getConfigManager().setProperty(
						MobBountyReloadedConfFile.REWARDS,
						"Default." + creature.getName() + ".value",
						new Double(0.0));
			}
		}
		double baseReward = 0.0;
		if (string != null)
		{
			if (string.contains(":"))
			{
				String[] resultRange = string.split(":");

				Random rand = new Random();
				int range;
				int loc;

				if (Double.valueOf(resultRange[0]) > Double
						.valueOf(resultRange[1]))
				{
					range = (int) ((Double.valueOf(resultRange[0]) * 100) - (Double
							.valueOf(resultRange[1]) * 100));
					loc = (int) (Double.valueOf(resultRange[1]) * 100);
				}
				else
				{
					range = (int) ((Double.valueOf(resultRange[1]) * 100) - (Double
							.valueOf(resultRange[0]) * 100));
					loc = (int) (Double.valueOf(resultRange[0]) * 100);
				}

				baseReward = (((double) (loc + rand.nextInt(range + 1))) / 100);
			}
			else
			{
				baseReward = Double.valueOf(string);
			}
		}
		return baseReward;
	}

	public MobBountyReloaded getMBR()
	{
		return mbr;
	}

	public void setMBR(MobBountyReloaded mbr)
	{
		this.mbr = mbr;
	}

	public double getEnvironmentMult(Player player)
	{
		Environment env = player.getWorld().getEnvironment();
		String envString;
		double envMult = 1.0;
		if (env.equals(Environment.NORMAL))
		{
			envString = getMBR().getConfigManager()
					.getProperty(MobBountyReloadedConfFile.MULTIPLIERS,
							"Environment.Normal");
			if (envString == null)
				envString = "1.0";
			try
			{
				envMult = Double.parseDouble(envString);
			}
			catch (NumberFormatException e)
			{
				envMult = 1.0;
			}
		}
		else if (env.equals(Environment.NETHER))
		{
			envString = getMBR().getConfigManager()
					.getProperty(MobBountyReloadedConfFile.MULTIPLIERS,
							"Environment.Nether");
			if (envString == null)
				envString = "1.0";
			try
			{
				envMult = Double.parseDouble(envString);
			}
			catch (NumberFormatException e)
			{
				envMult = 1.0;
			}
		}
		else if (env.equals(Environment.THE_END))
		{
			envString = getMBR().getConfigManager().getProperty(
					MobBountyReloadedConfFile.MULTIPLIERS, "Environment.End");
			if (envString == null)
				envString = "1.0";
			try
			{
				envMult = Double.parseDouble(envString);
			}
			catch (NumberFormatException e)
			{
				envMult = 1.0;
			}
		}
		return envMult;
	}

	public double getTimeMult(Player player)
	{
		MobBountyTime time = MobBountyTime.getTimeOfDay(player.getWorld()
				.getTime());
		String timeString;
		double timeMult = 1.0;
		if (time.equals(MobBountyTime.SUNRISE))
		{
			timeString = getMBR().getConfigManager().getProperty(
					MobBountyReloadedConfFile.MULTIPLIERS, "Time.Sunrise");
			if (timeString == null)
				timeString = "1.0";
			try
			{
				timeMult = Double.parseDouble(timeString);
			}
			catch (NumberFormatException e)
			{
				timeMult = 1.0;
			}
		}
		else if (time.equals(MobBountyTime.DAY))
		{
			timeString = getMBR().getConfigManager().getProperty(
					MobBountyReloadedConfFile.MULTIPLIERS, "Time.Day");
			if (timeString == null)
				timeString = "1.0";
			try
			{
				timeMult = Double.parseDouble(timeString);
			}
			catch (NumberFormatException e)
			{
				timeMult = 1.0;
			}
		}
		else if (time.equals(MobBountyTime.SUNSET))
		{
			timeString = getMBR().getConfigManager().getProperty(
					MobBountyReloadedConfFile.MULTIPLIERS, "Time.Sunset");
			if (timeString == null)
				timeString = "1.0";
			try
			{
				timeMult = Double.parseDouble(timeString);
			}
			catch (NumberFormatException e)
			{
				timeMult = 1.0;
			}
		}
		if (time.equals(MobBountyTime.NIGHT))
		{
			timeString = getMBR().getConfigManager().getProperty(
					MobBountyReloadedConfFile.MULTIPLIERS, "Time.Night");
			if (timeString == null)
				timeString = "1.0";
			try
			{
				timeMult = Double.parseDouble(timeString);
			}
			catch (NumberFormatException e)
			{
				timeMult = 1.0;
			}
		}
		return timeMult;
	}

	public double getWorldMult(Player player)
	{
		double worldMult = 1.0;
		String worldS = getMBR().getConfigManager().getProperty(
				MobBountyReloadedConfFile.MULTIPLIERS,
				"World." + player.getWorld().getName());
		if (worldS == null)
			worldS = "1.0";
		try
		{
			worldMult = Double.parseDouble(worldS);
		}
		catch (NumberFormatException e)
		{
			worldMult = 1.0;
		}
		return worldMult;
	}

	public double getFortuneMult(Player player)
	{
		if (!(player.getItemInHand().containsEnchantment(
				Enchantment.LOOT_BONUS_BLOCKS) || player.getItemInHand()
				.containsEnchantment(Enchantment.LOOT_BONUS_MOBS)))
		{
			return 1.0;
		}
		double fortMult = 1.0;
		String fortS = getMBR().getConfigManager()
				.getProperty(MobBountyReloadedConfFile.MULTIPLIERS,
						"fortuneToolsMultiplier");
		if (fortS == null)
			fortS = "1.0";
		try
		{
			fortMult = Double.parseDouble(fortS);
		}
		catch (NumberFormatException e)
		{
			fortMult = 1.0;
		}
		return fortMult;
	}

	public double getUserMult(Player player)
	{
		double userMult = 1.0;
		String userS = getMBR().getConfigManager().getProperty(
				MobBountyReloadedConfFile.MULTIPLIERS,
				"User." + player.getName());
		if (userS == null)
			userS = "1.0";
		try
		{
			userMult = Double.parseDouble(userS);
		}
		catch (NumberFormatException e)
		{
			userMult = 1.0;
		}
		return userMult;
	}

	public double getGroupMult(Player player)
	{
		double groupMult = 1.0;
		if (getMBR().getPermissionManager().getPermissions()
				.equals(Permission.DEFAULT_PERMISSION)
				|| getMBR().getPermissionManager().getPermissions().getName()
						.equalsIgnoreCase("SuperPerms"))
		{
			return groupMult;
		}
		String groupS = getMBR().getConfigManager().getProperty(
				MobBountyReloadedConfFile.MULTIPLIERS,
				"Group."
						+ getMBR().getPermissionManager().getPermissions()
								.getPrimaryGroup(player));
		if (groupS == null)
			groupS = "1.0";
		try
		{
			groupMult = Double.parseDouble(groupS);
		}
		catch (NumberFormatException e)
		{
			groupMult = 1.0;
		}
		return groupMult;
	}
}
