package info.hawksharbor.MobBountyReloadedCore.MobBounty.Utils.External;

import org.bukkit.Location;

import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.struct.Rel;

public class FactionsUtil
{

	public static Rel getFactionRelation(Faction fac1, Faction fac2)
	{
		return fac1.getRelationTo(fac2);
	}

	public static FLocation getFLocation(Location location)
	{
		return (new FLocation(location));
	}

}
