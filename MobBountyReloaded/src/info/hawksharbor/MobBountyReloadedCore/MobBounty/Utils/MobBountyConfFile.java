package info.hawksharbor.MobBountyReloadedCore.MobBounty.Utils;

public enum MobBountyConfFile
{
	CHUNKOWN("plugins/MobBountyReloaded/External/ChunkOwn.yml"), DEITYPROTECT(
			"plugins/MobBountyReloaded/External/DeityProtect.yml"), DROPS(
			"plugins/MobBountyReloaded/Drops.yml"), EXPERIENCE(
			"plugins/MobBountyReloaded/Experience.yml"), FACTIONS(
			"plugins/MobBountyReloaded/External/Factions.yml"), GENERAL(
			"plugins/MobBountyReloaded/General.yml"), GRIEFPREVENTION(
			"plugins/MobBountyReloaded/External/GriefPrevention.yml"), HEROES(
			"plugins/MobBountyReloaded/External/Heroes.yml"), HEROSTRONGHOLD(
			"plugins/MobBountyReloaded/External/HeroStronghold.yml"), KILLSTREAK(
			"plugins/MobBountyReloaded/Killstreak.yml"), LIKEABOSS(
			"plugins/MobBountyReloaded/External/Likeaboss.yml"), LOCALE(
			"plugins/MobBountyReloaded/Locale.yml"), MAZEMANIA(
			"plugins/MobBountyReloaded/External/MazeMania.yml"), MCMMO(
			"plugins/MobBountyReloaded/External/mcMMO.yml"), MOBARENA(
			"plugins/MobBountyReloaded/External/MobArena.yml"), MULTIPLIERS(
			"plugins/MobBountyReloaded/Multiplier.yml"), PRECIOUSSTONES(
			"plugins/MobBountyReloaded/External/PreciousStones.yml"), REGIOS(
			"plugins/MobBountyReloaded/External/Regios.yml"), RESIDENCE(
			"plugins/MobBountyReloaded/External/Residence.yml"), REWARDS(
			"plugins/MobBountyReloaded/Reward.yml"), ROADPROTECTOR(
			"plugins/MobBountyReloaded/External/RoadProtector.yml"), SIMPLECLANS(
			"plugins/MobBountyReloaded/External/SimpleClans.yml"), SPOUT(
			"plugins/MobBountyReloaded/External/Spout.yml"), TOWNY(
			"plugins/MobBountyReloaded/External/Towny.yml"), ULTIMATEARENA(
			"plugins/MobBountyReloaded/External/UltimateArena.yml"), WORLDGUARD(
			"plugins/MobBountyReloaded/External/WorldGuard.yml");

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
