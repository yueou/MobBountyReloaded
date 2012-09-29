package info.hawksharbor.MobBounty.Utils.External;

import org.bukkit.Location;

import com.palmergames.bukkit.towny.exceptions.EconomyException;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownBlock;
import com.palmergames.bukkit.towny.object.TownyUniverse;

public class TownyUtil
{

	public static boolean areNationsAllied(Nation nation1, Nation nation2)
	{
		return nation1.hasAlly(nation2);
	}

	public static boolean areNationsEnemies(Nation nation1, Nation nation2)
	{
		return nation1.hasEnemy(nation2);
	}

	public static boolean areTownsAllied(Town town1, Town town2)
	{
		return areNationsAllied(getTownNation(town1), getTownNation(town2))
				|| areTownsInSameNation(town1, town2);
	}

	public static boolean areTownsEnemies(Town town1, Town town2)
	{
		return areNationsEnemies(getTownNation(town1), getTownNation(town2));
	}

	public static boolean areTownsInSameNation(Town town1, Town town2)
	{
		return getTownNation(town1).hasTown(town2);
	}

	public static Nation getNation(String name)
	{
		Nation nation;
		try
		{
			nation = TownyUniverse.getDataSource().getNation(name);
		}
		catch (NotRegisteredException e)
		{
			return null;
		}
		return nation;
	}

	public static Resident getResident(String name)
	{
		Resident resident;
		try
		{
			resident = TownyUniverse.getDataSource().getResident(name);
		}
		catch (NotRegisteredException e)
		{
			return null;
		}
		return resident;
	}

	public static Town getResidentTown(Resident resident)
	{
		Town town;
		try
		{
			town = resident.getTown();
		}
		catch (NotRegisteredException e)
		{
			return null;
		}
		return town;
	}

	public static Town getTown(String name)
	{
		Town town;
		try
		{
			town = TownyUniverse.getDataSource().getTown(name);
		}
		catch (NotRegisteredException e)
		{
			return null;
		}
		return town;
	}

	public static Town getTownAtLocation(Location location)
	{
		Town town;
		try
		{
			town = TownyUniverse.getTownBlock(location).getTown();
		}
		catch (NotRegisteredException e)
		{
			return null;
		}
		return town;
	}

	public static double getTownBalance(Town town)
	{
		double bal;
		try
		{
			bal = town.getHoldingBalance();
		}
		catch (EconomyException e)
		{
			return 0.0;
		}
		return bal;
	}

	public static TownBlock getTownBlock(Location location)
	{
		return TownyUniverse.getTownBlock(location);
	}

	public static Nation getTownNation(Town town)
	{
		Nation nation;
		if (!town.hasNation())
			return null;
		try
		{
			nation = town.getNation();
		}
		catch (NotRegisteredException e)
		{
			return null;
		}
		return nation;
	}

}
