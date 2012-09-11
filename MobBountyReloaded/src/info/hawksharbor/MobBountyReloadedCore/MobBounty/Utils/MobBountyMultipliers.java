package info.hawksharbor.MobBountyReloadedCore.MobBounty.Utils;

import info.hawksharbor.MobBountyReloadedCore.MobBounty.Managers.MobBountyEcon;

import org.bukkit.Location;
import org.bukkit.World.Environment;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

public class MobBountyMultipliers
{

	public static double getEnvironmentMult(Player player)
	{
		double result = 1.0;
		if (MobBountyAPI.instance.getPermissionsManager().hasPermission(player,
				"mbr.user.multiplier.environment"))
		{
			String wrldEnv = null;
			if (player.getWorld().getEnvironment().equals(Environment.NORMAL))
			{
				wrldEnv = "Normal";
			}
			else if (player.getWorld().getEnvironment()
					.equals(Environment.NETHER))
			{
				wrldEnv = "Nether";
			}
			else if (player.getWorld().getEnvironment()
					.equals(Environment.THE_END))
			{
				wrldEnv = "End";
			}
			String multiTest = MobBountyAPI.instance.getConfigManager()
					.getProperty(MobBountyConfFile.MULTIPLIERS,
							"Environment." + wrldEnv);
			if (multiTest != null)
			{
				result *= Double.valueOf(multiTest);
			}
		}
		return result;
	}

	public static double getFortuneMult(Player player)
	{
		double result = 1.0;
		if (MobBountyAPI.instance.getPermissionsManager().hasPermission(player,
				"mbr.user.multiplier.fortune"))
		{
			String fortRateS = MobBountyAPI.instance.getConfigManager()
					.getProperty(MobBountyConfFile.MULTIPLIERS,
							"fortuneToolsMultiplier");
			if (fortRateS != null)
			{
				double fortRate = 1.0;
				try
				{
					fortRate = Double.parseDouble(fortRateS);
				}
				catch (NumberFormatException e)
				{
					MobBountyAPI.instance.getConfigManager().setProperty(
							MobBountyConfFile.MULTIPLIERS,
							"fortuneToolsMultiplier", "1.0");
				}
				result *= fortRate;
			}
		}
		return result;
	}

	public static double getInternalMultipliers(Player player)
	{
		double environment = getEnvironmentMult(player);
		double fortune = getFortuneMult(player);
		double perm = getPermMult(player);
		double time = getTimeMult(player);
		double world = getWorldMult(player);
		double timer = loginTimer(player);
		double cap = mobCap(player);
		double spawner = mobSpawner(player);
		double intMult = environment *= fortune *= perm *= time *= world *= timer *= cap *= spawner;
		MobBountyMessage.debugMessage("Internal Multipliers: "
				+ String.valueOf(intMult));
		return intMult;
	}

	public static double getPermMult(Player player)
	{
		double result = 1.0;
		if (!MobBountyAPI.instance.getPermissionsManager().permissionsHooked
				|| !MobBountyAPI.instance.getPermissionsManager()
						.hasPermission(player, "mbr.user.multiplier.group"))
		{
			return result;
		}
		String group = MobBountyAPI.instance.getPermissionsManager()
				.getPermission().getPrimaryGroup(player);
		boolean multContains = MobBountyAPI.instance
				.getConfigManager()
				.propertyExists(MobBountyConfFile.MULTIPLIERS, "Group." + group);
		if (!multContains)
		{
			return result;
		}
		String permS = MobBountyAPI.instance.getConfigManager().getProperty(
				MobBountyConfFile.MULTIPLIERS, "Group." + group);
		if (permS == null)
		{
			return result;
		}
		double permMult = 1.0;
		try
		{
			permMult = Double.parseDouble(permS);
		}
		catch (NumberFormatException e)
		{
			MobBountyAPI.instance.getConfigManager().setProperty(
					MobBountyConfFile.MULTIPLIERS, "Group." + group, 1.0);
		}
		result *= Double.valueOf(permMult);
		return result;
	}

	public static double getTimeMult(Player player)
	{
		double result = 1.0;
		if (MobBountyAPI.instance.getPermissionsManager().hasPermission(player,
				"mbr.user.multiplier.time"))
		{
			String timeTest = MobBountyAPI.instance.getConfigManager()
					.getProperty(
							MobBountyConfFile.MULTIPLIERS,
							"Time."
									+ MobBountyTime.getTimeOfDay(
											player.getWorld().getTime())
											.getName());

			if (timeTest != null)
			{
				result *= Double.valueOf(timeTest);
			}
		}
		return result;
	}

	public static double getWorldMult(Player player)
	{
		double result = 1.0;
		if (MobBountyAPI.instance.getPermissionsManager().hasPermission(player,
				"mbr.user.multiplier.world"))
		{
			String worldTest = MobBountyAPI.instance.getConfigManager()
					.getProperty(MobBountyConfFile.MULTIPLIERS,
							"World." + player.getWorld().getName());

			if (worldTest != null)
			{
				result *= Double.valueOf(worldTest);
			}
		}
		return result;
	}

	public static boolean isFortune(Player player)
	{
		return player.getItemInHand().containsEnchantment(
				Enchantment.LOOT_BONUS_MOBS)
				|| player.getItemInHand().containsEnchantment(
						Enchantment.LOOT_BONUS_BLOCKS);
	}

	public static double loginTimer(Player player)
	{
		double result = 1.0;
		if (MobBountyAPI.instance.getLoginTimer().containsKey(player.getName()))
		{
			long lTime = MobBountyAPI.instance.getLoginTimer().get(
					player.getName());
			long curTime = System.currentTimeMillis();
			long pTime = (curTime - lTime) / 1000;
			String configTime = MobBountyAPI.instance.getConfigManager()
					.getProperty(MobBountyConfFile.GENERAL, "timeAfterLogin");
			if (configTime == null)
			{
				return result;
			}
			long cTime = 0;
			try
			{
				cTime = Long.parseLong(configTime);
			}
			catch (NumberFormatException e)
			{
				cTime = 10;
				MobBountyAPI.instance.getConfigManager().setProperty(
						MobBountyConfFile.GENERAL, "timeAfterLogin",
						String.valueOf(cTime));
			}
			if (pTime < cTime)
			{
				result = 0.0;
				return result;
			}
			MobBountyAPI.instance.getLoginTimer().remove(player.getName());
		}
		return result;
	}

	public static double mobCap(Player player)
	{
		double result = 1.0;
		String mC = MobBountyAPI.instance.getConfigManager().getProperty(
				MobBountyConfFile.GENERAL, "mobCap.use");
		if (mC == null)
		{
			return result;
		}
		boolean mobCap = "true".equalsIgnoreCase(mC);
		if (!mobCap)
		{
			return result;
		}
		int mCL;
		int mCD;
		try
		{
			mCL = Integer.parseInt(MobBountyAPI.instance.getConfigManager()
					.getProperty(MobBountyConfFile.GENERAL, "mobCap.limit"));
		}
		catch (NumberFormatException e)
		{
			MobBountyAPI.instance.getConfigManager().setProperty(
					MobBountyConfFile.GENERAL, "mobCap.limit", "30");
			mCL = 30;
		}
		try
		{
			mCD = Integer.parseInt(MobBountyAPI.instance.getConfigManager()
					.getProperty(MobBountyConfFile.GENERAL, "mobCap.distance"));
		}
		catch (NumberFormatException e)
		{
			MobBountyAPI.instance.getConfigManager().setProperty(
					MobBountyConfFile.GENERAL, "mobCap.distance", "30");
			mCD = 30;
		}

		MobBountyPlayerKillData playerData = MobBountyEcon._playerData
				.get(player.getName());

		if (playerData == null)
		{
			playerData = new MobBountyPlayerKillData();
		}
		else
		{
			Location loc1 = playerData.lastKillLoc;
			if (loc1 == null)
			{
				loc1 = new Location(player.getWorld(), 0.0, 0.0, 0.0);
			}
			Location loc2 = player.getLocation();
			if (loc1.getWorld() == loc2.getWorld())
			{
				int dist = (int) Math.round(loc1.distance(loc2));
				if (dist > mCD)
				{
					playerData.lastKillLoc = loc2;
					playerData.lastKillAmount = 0;
					MobBountyEcon._playerData.put(player.getName(), playerData);
				}
				int killed = playerData.lastKillAmount;
				if (killed >= mCL)
				{
					result = 0.0;
					return result;
				}
			}
			else
			{
				loc1 = loc2;
				playerData.lastKillLoc = loc2;
				int dist = (int) Math.round(loc1.distance(loc2));
				if (dist > mCD)
				{
					playerData.lastKillLoc = loc2;
					playerData.lastKillAmount = 0;
					MobBountyEcon._playerData.put(player.getName(), playerData);
				}
				int killed = playerData.lastKillAmount;
				if (killed >= mCL)
				{
					result = 0.0;
					return result;
				}
			}
		}
		playerData.lastKillAmount++;
		MobBountyEcon._playerData.put(player.getName(), playerData);
		return result;
	}

	public static double mobSpawner(Player player)
	{
		double result = 1.0;
		String booleanTest = null;

		booleanTest = MobBountyAPI.instance.getConfigManager().getProperty(
				MobBountyConfFile.GENERAL, "useMobSpawnerProtection");

		if (booleanTest != null
				&& (booleanTest.equalsIgnoreCase("true")
						|| booleanTest.equalsIgnoreCase("yes") || booleanTest
							.equalsIgnoreCase("1")))
		{
			if (MobBountyUtils.nearMobSpawner(player.getLocation()))
			{
				result = MobBountyUtils.getDouble(
						MobBountyAPI.instance.getConfigManager().getProperty(
								MobBountyConfFile.GENERAL,
								"mobSpawnerProtectionRate"), 1.0);
			}
		}

		return result;
	}

}
