package org.inesgar.MobBountyReloaded.utils;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;

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
		double d;
		try
		{
			d = Double.parseDouble(string);
		}
		catch (NumberFormatException e)
		{
			d = fallBack;
		}
		catch (NullPointerException e)
		{
			d = fallBack;
		}
		return d;
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
		catch (NullPointerException e)
		{
			i = fallBack;
		}
		return i;
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
