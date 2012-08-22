package info.hawksharbor.MobBounty.Utils;

public enum MobBountyConfFile
{
	GENERAL("plugins/MobBountyReloaded/" + getPluginVersion() + "/General.yml"), LOCALE(
			"plugins/MobBountyReloaded/" + getPluginVersion() + "/Locale.yml"), MULTIPLIERS(
			"plugins/MobBountyReloaded/" + getPluginVersion()
					+ "/Multiplier.yml"), REWARDS("plugins/MobBountyReloaded/"
			+ getPluginVersion() + "/Reward.yml"), KILLSTREAK(
			"plugins/MobBountyReloaded/" + getPluginVersion()
					+ "/Killstreak.yml"), DROPS("plugins/MobBountyReloaded/"
			+ getPluginVersion() + "/Drops.yml"), EXPERIENCE(
			"plugins/MobBountyReloaded/" + getPluginVersion()
					+ "/Experience.yml"), LIKEABOSS(
			"plugins/MobBountyReloaded/" + getPluginVersion()
					+ "/External/Likeaboss.yml"), MOBARENA(
			"plugins/MobBountyReloaded/" + getPluginVersion()
					+ "/External/MobArena.yml"), SPOUT(
			"plugins/MobBountyReloaded/" + getPluginVersion()
					+ "/External/Spout.yml"), WORLDGUARD(
			"plugins/MobBountyReloaded/" + getPluginVersion()
					+ "/External/WorldGuard.yml"), TOWNY(
			"plugins/MobBountyReloaded/" + getPluginVersion()
					+ "/External/Towny.yml"), FACTIONS(
			"plugins/MobBountyReloaded/" + getPluginVersion()
					+ "/External/Factions.yml"), MCMMO(
			"plugins/MobBountyReloaded/" + getPluginVersion()
					+ "/External/mcMMO.yml"), HEROES(
			"plugins/MobBountyReloaded/" + getPluginVersion()
					+ "/External/Heroes.yml"), REGIOS(
			"plugins/MobBountyReloaded/" + getPluginVersion()
					+ "/External/Regios.yml"), GRIEFPREVENTION(
			"plugins/MobBountyReloaded/" + getPluginVersion()
					+ "/External/GriefPrevention.yml"), DEITYPROTECT(
			"plugins/MobBountyReloaded/" + getPluginVersion()
					+ "/External/DeityProtect.yml"), CHUNKOWN(
			"plugins/MobBountyReloaded/" + getPluginVersion()
					+ "/External/ChunkOwn.yml"), PRECIOUSSTONES(
			"plugins/MobBountyReloaded/" + getPluginVersion()
					+ "/External/PreciousStones.yml"), HEROSTRONGHOLD(
			"plugins/MobBountyReloaded/" + getPluginVersion()
					+ "/External/HeroStronghold.yml"), MAZEMANIA(
			"plugins/MobBountyReloaded/" + getPluginVersion()
					+ "/External/MazeMania.yml"), RESIDENCE(
			"plugins/MobBountyReloaded/" + getPluginVersion()
					+ "/External/Residence.yml"), ROADPROTECTOR(
			"plugins/MobBountyReloaded/" + getPluginVersion()
					+ "/External/RoadProtector.yml"), ULTIMATEARENA(
			"plugins/MobBountyReloaded/" + getPluginVersion()
					+ "/External/UltimateArena.yml"), SIMPLECLANS(
			"plugins/MobBountyReloaded/" + getPluginVersion()
					+ "/External/SimpleClans.yml");

	public static MobBountyConfFile fromName(String name)
	{
		for (MobBountyConfFile id : MobBountyConfFile.values())
		{
			if (id.name().equalsIgnoreCase(name))
				return id;
		}

		return null;
	}

	private static String getPluginVersion()
	{
		return "v" + MobBountyAPI.instance.getPluginVersion();
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
