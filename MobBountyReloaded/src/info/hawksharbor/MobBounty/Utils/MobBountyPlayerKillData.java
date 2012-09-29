package info.hawksharbor.MobBounty.Utils;

import org.bukkit.Location;

public class MobBountyPlayerKillData
{
	public double cacheEarned;
	public int cacheSize;
	public long cacheTime;
	public int killStreak;
	public MobBountyCreature lastKill;
	public int lastKillAmount;
	public Location lastKillLoc;
	public double lastRewardPercentage;

	public MobBountyPlayerKillData()
	{
		lastKill = null;
		lastRewardPercentage = 1;
		lastKillLoc = null;
		lastKillAmount = 0;
		killStreak = 0;
		cacheTime = 0;
		cacheEarned = 0.0;
		cacheSize = 0;
	}
}
