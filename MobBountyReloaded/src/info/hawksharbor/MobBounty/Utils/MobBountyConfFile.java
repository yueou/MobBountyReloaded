package info.hawksharbor.MobBounty.Utils;

public enum MobBountyConfFile
{
	GENERAL("plugins/MobBountyReloaded/General.yml"), LOCALE(
			"plugins/MobBountyReloaded/Locale.yml"), MULTIPLIERS(
			"plugins/MobBountyReloaded/Multiplier.yml"), REWARDS(
			"plugins/MobBountyReloaded/Reward.yml"), KILLSTREAK(
			"plugins/MobBountyReloaded/Killstreak.yml"), DROPS(
			"plugins/MobBountyReloaded/Drops.yml"), EXPERIENCE(
			"plugins/MobBountyReloaded/Experience.yml"), LIKEABOSS(
			"plugins/MobBountyReloaded/External/Likeaboss.yml"), MOBARENA(
			"plugins/MobBountyReloaded/External/MobArena.yml"), SPOUT(
			"plugins/MobBountyReloaded/External/Spout.yml"), WORLDGUARD(
			"plugins/MobBountyReloaded/External/WorldGuard.yml"), TOWNY(
			"plugins/MobBountyReloaded/External/Towny.yml"), FACTIONS(
			"plugins/MobBountyReloaded/External/Factions.yml"), MCMMO(
			"plugins/MobBountyReloaded/External/mcMMO.yml"), HEROES(
			"plugins/MobBountyReloaded/External/Heroes.yml"), REGIOS(
			"plugins/MobBountyReloaded/External/Regios.yml"), GRIEFPREVENTION(
			"plugins/MobBountyReloaded/External/GriefPrevention.yml"), DEITYPROTECT(
			"plugins/MobBountyReloaded/External/DeityProtect.yml"), CHUNKOWN(
			"plugins/MobBountyReloaded/External/ChunkOwn.yml"), PRECIOUSSTONES(
			"plugins/MobBountyReloaded/External/PreciousStones.yml"), HEROSTRONGHOLD(
			"plugins/MobBountyReloaded/External/HeroStronghold.yml"), MAZEMANIA(
			"plugins/MobBountyReloaded/External/MazeMania.yml"), RESIDENCE(
			"plugins/MobBountyReloaded/External/Residence.yml"), ROADPROTECTOR(
			"plugins/MobBountyReloaded/External/RoadProtector.yml"), ULTIMATEARENA(
			"plugins/MobBountyReloaded/External/UltimateArena.yml"), SIMPLECLANS(
			"plugins/MobBountyReloaded/External/SimpleClans.yml");

	public static MobBountyConfFile fromName(String name)
	{
		for (MobBountyConfFile id : MobBountyConfFile.values())
		{
			if (id.name().equalsIgnoreCase(name))
				return id;
		}

		return null;
	}

	private final String _path;

	private MobBountyConfFile(final String path)
	{
		_path = path;
	}

	public String getPath()
	{
		return _path;
	}

}
