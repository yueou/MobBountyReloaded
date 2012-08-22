package info.hawksharbor.MobBounty.Utils;

import org.bukkit.Location;

public class MobBountyPlayerKillData
{
	public MobBountyCreature lastKill;
	public double lastRewardPercentage;
	public Location lastKillLoc;
	public int lastKillAmount;
	public int killStreak;
	public long cacheTime;
	public double cacheEarned;
	public int cacheSize;

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
