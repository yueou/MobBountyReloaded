package info.hawksharbor.MobBounty.Utils;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class MobBountyUtils
{

	public static boolean containsIgnoreCase(List<String> l, String s)
	{
		Iterator<String> it = l.iterator();
		while (it.hasNext())
		{
			if (it.next().equalsIgnoreCase(s))
				return true;
		}
		return false;
	}

	public static double getDouble(String string, double fallBack)
	{
		double i;
		try
		{
			i = Double.parseDouble(string);
		}
		catch (NumberFormatException e)
		{
			i = fallBack;
		}
		return i;
	}

	public static int getInt(String string, int fallBack)
	{
		int i;
		try
		{
			i = (int) Math.round(getDouble(string, fallBack));
		}
		catch (NumberFormatException e)
		{
			i = fallBack;
		}
		return i;
	}

	public static String getPluginMessage(String localeKey)
	{
		return MobBountyAPI.instance.getLocaleManager().getString(localeKey);
	}

	public static int handleRange(int i1, int i2)
	{
		Random rand = new Random();
		int result;
		int range;
		int loc;
		if (Double.valueOf(i1) > Double.valueOf(i2))
		{
			range = (int) ((Double.valueOf(i1) * 100) - (Double.valueOf(i2) * 100));
			loc = (int) (Double.valueOf(i2) * 100);
		}
		else
		{
			range = (int) ((Double.valueOf(i2) * 100) - (Double.valueOf(i1) * 100));
			loc = (int) (Double.valueOf(i1) * 100);
		}
		result = ((int) (loc + rand.nextInt(range + 1))) / 100;
		return result;
	}

	public static int handleRange(String string, String splitter)
	{
		Random rand = new Random();
		int result;
		int range;
		int loc;
		String[] splitString = string.split(splitter);
		if (getDouble(splitString[0], 0) > getDouble(splitString[1], 0))
		{
			range = (int) ((getDouble(splitString[0], 0) * 100) - (getDouble(
					splitString[1], 0) * 100));
			loc = (int) (getDouble(splitString[1], 0) * 100);
		}
		else
		{
			range = (int) ((getDouble(splitString[1], 0) * 100) - (getDouble(
					splitString[0], 0) * 100));
			loc = (int) (getDouble(splitString[0], 0) * 100);
		}
		result = ((int) (loc + rand.nextInt(range + 1))) / 100;
		return result;
	}

	public static boolean hasPlugin(String pluginName)
	{
		if (Bukkit.getServer().getPluginManager().getPlugin(pluginName) != null)
			return true;
		return false;
	}

	public static boolean nearMobSpawner(Location location)
	{
		int radius = 0;
		World world;
		int x1, x2, y1, y2, z1, z2;

		String radiusTest = MobBountyAPI.instance.getConfigManager()
				.getProperty(MobBountyConfFile.GENERAL,
						"mobSpawnerProtectionRadius");

		if (radiusTest != null && radiusTest.matches("[0-9]+"))
		{
			try
			{
				radius = Integer.valueOf(radiusTest);
			}
			catch (NumberFormatException e)
			{
				radius = 5;
			}
			world = location.getWorld();
			x1 = (int) (location.getX());
			y1 = (int) (location.getY());
			z1 = (int) (location.getZ());

			for (x2 = 0 - radius; x2 <= radius; x2++)
			{
				for (y2 = 0 - radius; y2 <= radius; y2++)
				{
					for (z2 = 0 - radius; z2 <= radius; z2++)
					{
						Block block = world.getBlockAt(x1 + x2, y1 + y2, z1
								+ z2);

						if (block.getType() == Material.MOB_SPAWNER)
						{
							return true;
						}
					}
				}
			}
		}

		return false;
	}

	public static List<String> removeIgnoreCase(List<String> l, String s)
	{
		Iterator<String> iter = l.iterator();
		while (iter.hasNext())
		{
			if (iter.next().equalsIgnoreCase(s))
			{
				iter.remove();
				break;
			}
		}
		return l;
	}

}
