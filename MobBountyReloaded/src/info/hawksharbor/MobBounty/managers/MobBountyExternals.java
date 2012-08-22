package info.hawksharbor.MobBounty.managers;

import fromgate.roadprotector.RoadProtector;
import info.hawksharbor.MobBounty.MobBountyReloaded;
import info.hawksharbor.MobBounty.Utils.MobBountyAPI;
import info.hawksharbor.MobBounty.Utils.MobBountyConfFile;
import info.hawksharbor.MobBounty.Utils.MobBountyMessage;
import info.hawksharbor.MobBounty.Utils.MobBountyMultipliers;
import info.hawksharbor.MobBounty.Utils.MobBountyUtils;
import info.hawksharbor.MobBounty.Utils.external.FactionsUtil;
import info.hawksharbor.MobBounty.Utils.external.TownyUtil;
import info.plugmania.mazemania.MazeMania;

import java.util.ArrayList;
import java.util.List;

import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import multitallented.redcastlemedia.bukkit.herostronghold.HeroStronghold;
import net.sacredlabyrinth.Phaed.PreciousStones.FieldFlag;
import net.sacredlabyrinth.Phaed.PreciousStones.PreciousStones;
import net.sacredlabyrinth.Phaed.PreciousStones.vectors.Field;
import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.getspout.spoutapi.Spout;

import cam.Likeaboss;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.codisimus.plugins.chunkown.ChunkOwn;
import com.garbagemule.MobArena.MobArena;
import com.garbagemule.MobArena.MobArenaHandler;
import com.gmail.nossr50.mcMMO;
import com.herocraftonline.heroes.Heroes;
import com.imdeity.protect.DeityProtect;
import com.massivecraft.factions.Board;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.P;
import com.massivecraft.factions.struct.FFlag;
import com.massivecraft.factions.struct.Rel;
import com.orange451.UltimateArena.UltimateArena;
import com.palmergames.bukkit.towny.Towny;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownyUniverse;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

import couk.Adamki11s.Regios.API.RegiosAPI;
import couk.Adamki11s.Regios.Main.Regios;
import couk.Adamki11s.Regios.Regions.GlobalRegionManager;

public class MobBountyExternals
{

	private MobBountyReloaded _plugin;

	private Towny towny;
	private P factions_17;
	private mcMMO mcmmo;
	private Heroes heroes;
	private WorldGuardPlugin worldguard;
	private Regios regios;
	private RegiosAPI regiosAPI;
	private Likeaboss likeaboss;
	private MobArenaHandler mobArenaHandler;
	private MobArena mobArena;
	private Spout spout;
	private GriefPrevention griefPrevention;
	private DeityProtect deityProtect;
	private ChunkOwn chunkOwn;
	private PreciousStones preciousStones;
	private RoadProtector roadProtector;
	private MazeMania mazeMania;
	private UltimateArena ultimateArena;
	private HeroStronghold heroStronghold;
	private Residence residence;
	private SimpleClans simpleClans;

	private MobBountyConfigs configs;
	private PluginManager pm;
	private MobBountyPermissions perms;

	private ArrayList<String> hooks;

	public MobBountyExternals(MobBountyReloaded plugin)
	{
		_plugin = plugin;
		pm = _plugin.getServer().getPluginManager();
		configs = MobBountyAPI.getInstance().getConfigManager();
		perms = MobBountyAPI.getInstance().getPermissionsManager();
		hooks = new ArrayList<String>();
		setupSupport();
	}

	private ChunkOwn checkChunkOwn()
	{
		Plugin plugin = pm.getPlugin("ChunkOwn");

		if (plugin == null || !(plugin instanceof ChunkOwn))
		{
			return null;
		}

		MobBountyMessage.logToConsole("ChunkOwn hooked.");
		hooks.add("ChunkOwn");
		return (ChunkOwn) plugin;
	}

	private double checkChunkOwnMult(Player player, Location location)
	{
		if (chunkOwn == null)
		{
			return 1.0;
		}
		String cNS = configs.getProperty(MobBountyConfFile.CHUNKOWN,
				"ChunkMultiplierRate");
		double cN = MobBountyUtils.getDouble(cNS, 1.0);
		if (perms.hasPermission(player, "mbr.user.multiplier.chunkown"))
			return cN;
		return 1.0;
	}

	private DeityProtect checkDeityProtect()
	{
		Plugin plugin = pm.getPlugin("DeityProtect");

		if (plugin == null || !(plugin instanceof DeityProtect))
		{
			return null;
		}

		MobBountyMessage.logToConsole("DeityProtect hooked.");
		hooks.add("DeityProtect");
		return (DeityProtect) plugin;
	}

	private double checkDeityProtectMult(Player player, Location location)
	{
		if (deityProtect == null)
		{
			return 1.0;
		}
		String cNS = configs.getProperty(MobBountyConfFile.DEITYPROTECT,
				"RegionMultiplierRate");
		double cN = MobBountyUtils.getDouble(cNS, 1.0);
		if (perms.hasPermission(player, "mbr.user.multiplier.deityprotect"))
			return cN;
		return 1.0;
	}

	public double checkEarnMultiplier(Player player, Location location,
			Entity entity)
	{
		double townMult = 1.0;
		if (towny != null && useTowny())
			townMult = checkTownMult(player, location);
		double factionMult17 = 1.0;
		if (factions_17 != null && useFactions())
			factionMult17 = checkFactionMult17(player, location);
		double worldGuardMult = 1.0;
		if (worldguard != null && useWorldGuard())
			worldGuardMult = checkWorldGuardMult(player, location);
		double regiosMult = 1.0;
		if (regios != null && useWorldGuard())
			regiosMult = checkRegiosMult(player, location);
		double mobArenaMult = 1.0;
		if (mobArena != null && mobArenaHandler != null && useMobArena())
			mobArenaMult = checkMobArenaMult(player, location);
		double griefPreventionMult = 1.0;
		if (griefPrevention != null && useGriefPrevention())
			griefPreventionMult = checkGriefPreventionMult(player, location);
		double deityProtectMult = 1.0;
		if (deityProtect != null && useGriefPrevention())
			deityProtectMult = checkDeityProtectMult(player, location);
		double chunkOwnMult = 1.0;
		if (chunkOwn != null && useChunkOwn())
			chunkOwnMult = checkChunkOwnMult(player, location);
		double preciousStonesMult = 1.0;
		if (preciousStones != null && usePreciousStones())
			preciousStonesMult = checkPreciousStonesMult(player, location);
		double roadProtectorMult = 1.0;
		if (roadProtector != null && useRoadProtector())
			roadProtectorMult = checkRoadProtectorMult(player, location);
		double mazeManiaMult = 1.0;
		if (mazeMania != null && useMazeMania())
			mazeManiaMult = checkMazeManiaMult(player, location);
		double ultimateArenaMult = 1.0;
		if (ultimateArena != null && useUltimateArena())
			ultimateArenaMult = checkUltimateArenaMult(player, location);
		double heroStrongholdMult = 1.0;
		if (heroStronghold != null && useHeroStronghold())
			heroStrongholdMult = checkHeroStrongholdMult(player, location);
		double residenceMult = 1.0;
		if (residence != null && useResidence())
			residenceMult = checkResidenceMult(player, location);
		double heroesMult = 1.0;
		if (heroes != null && useHeroes())
			heroesMult = checkHeroesMult(player, location);
		double mcMMOMult = 1.0;
		if (mcmmo != null && useMCMMO())
			mcMMOMult = checkMCMMOMult(player, location);
		double simpleClansMult = 1.0;
		if (simpleClans != null && useSimpleClans())
			simpleClansMult = checkSimpleClansMult(player, location, entity);
		double likeabossMult = 1.0;
		if (likeaboss != null && useLikeaboss())
			likeabossMult = checkLikeabossMult(player, location, entity);
		return (townMult * factionMult17 * worldGuardMult * regiosMult
				* mobArenaMult * griefPreventionMult * deityProtectMult
				* chunkOwnMult * preciousStonesMult * roadProtectorMult
				* mazeManiaMult * ultimateArenaMult * heroStrongholdMult
				* residenceMult * heroesMult * mcMMOMult * simpleClansMult
				* likeabossMult * MobBountyMultipliers.loginTimer(player)
				* MobBountyMultipliers.getTimeMult(player)
				* MobBountyMultipliers.getEnvironmentMult(player)
				* MobBountyMultipliers.getWorldMult(player)
				* MobBountyMultipliers.getPermMult(player)
				* MobBountyMultipliers.mobCap(player)
				* MobBountyMultipliers.getFortuneMult(player)
				* MobBountyMultipliers.mobSpawner(player) * MobBountyMultipliers
					.getCreative(player));
	}

	public boolean checkEarnPermission(Player player, Location location,
			Entity entity)
	{
		boolean earnTown = true;
		boolean earnFaction = true;
		boolean earnWorldGuard = true;
		boolean earnRegios = true;
		boolean earnMobArena = true;
		boolean earnGriefPrevention = true;
		boolean earnDeityProtect = true;
		boolean earnChunkOwn = true;
		boolean earnPreciousStones = true;
		boolean earnRoadProtector = true;
		boolean earnMazeMania = true;
		boolean earnUltimateArena = true;
		boolean earnHeroStronghold = true;
		boolean earnResidence = true;
		boolean earnSimpleClans = true;
		if (towny != null && useTowny())
		{
			earnTown = earnTown(player, location);
		}
		if (factions_17 != null && useFactions())
		{
			earnFaction = earnFaction(player, location);
		}
		if (worldguard != null && useWorldGuard())
		{
			earnWorldGuard = earnWorldGuard(player, location);
		}
		if (regios != null && useRegios())
		{
			earnRegios = earnRegios(player, location);
		}
		if (mobArena != null && mobArenaHandler != null && useMobArena())
		{
			earnMobArena = earnMobArena(player, location);
		}
		if (griefPrevention != null && useGriefPrevention())
		{
			earnGriefPrevention = earnGriefPrevention(player, location);
		}
		if (deityProtect != null && useDeityProtect())
		{
			earnDeityProtect = earnDeityProtect(player, location);
		}
		if (chunkOwn != null && useChunkOwn())
		{
			earnChunkOwn = earnChunkOwn(player, location);
		}
		if (preciousStones != null && usePreciousStones())
		{
			earnPreciousStones = earnPreciousStones(player, location);
		}
		if (roadProtector != null && useRoadProtector())
		{
			earnRoadProtector = earnRoadProtector(player, location);
		}
		if (mazeMania != null && useMazeMania())
		{
			earnMazeMania = earnMazeMania(player, location);
		}
		if (ultimateArena != null && useUltimateArena())
		{
			earnUltimateArena = earnUltimateArena(player, location);
		}
		if (heroStronghold != null && useHeroStronghold())
		{
			earnHeroStronghold = earnHeroStronghold(player, location);
		}
		if (residence != null && useResidence())
		{
			earnResidence = earnResidence(player, location);
		}
		if (simpleClans != null && useSimpleClans())
		{
			earnSimpleClans = earnSimpleClans(player, location, entity);
		}
		return (earnTown && earnFaction && earnWorldGuard && earnRegios
				&& earnMobArena && earnGriefPrevention && earnDeityProtect
				&& earnChunkOwn && earnPreciousStones && earnRoadProtector
				&& earnMazeMania && earnUltimateArena && earnHeroStronghold
				&& earnResidence && earnSimpleClans);
	}

	private double checkFactionMult17(Player player, Location location)
	{
		if (factions_17 == null)
		{
			return 1.0;
		}
		String cOS = configs.getProperty(MobBountyConfFile.FACTIONS,
				"ownLand.multiplierRate");
		String cES = configs.getProperty(MobBountyConfFile.FACTIONS,
				"enemyLand.multiplierRate");
		String cAS = configs.getProperty(MobBountyConfFile.FACTIONS,
				"alliedLand.multiplierRate");
		String cWS = configs.getProperty(MobBountyConfFile.FACTIONS,
				"wilderness.multiplierRate");
		String cSZS = configs.getProperty(MobBountyConfFile.FACTIONS,
				"safeZone.multiplierRate");
		String cWZS = configs.getProperty(MobBountyConfFile.FACTIONS,
				"warZone.multiplierRate");
		String cPS = configs.getProperty(MobBountyConfFile.FACTIONS,
				"peacefulLand.multiplierRate");
		String cNS = configs.getProperty(MobBountyConfFile.FACTIONS,
				"neutralLand.multiplierRate");
		double cW = MobBountyUtils.getDouble(cWS, 1.0);
		double cO = MobBountyUtils.getDouble(cOS, 1.0);
		double cN = MobBountyUtils.getDouble(cNS, 1.0);
		double cE = MobBountyUtils.getDouble(cES, 1.0);
		double cA = MobBountyUtils.getDouble(cAS, 1.0);
		double cP = MobBountyUtils.getDouble(cPS, 1.0);
		double cWZ = MobBountyUtils.getDouble(cWZS, 1.0);
		double cSZ = MobBountyUtils.getDouble(cSZS, 1.0);
		Faction faction = Board.getFactionAt(location);
		FPlayer fPlayer = FPlayers.i.get(player);
		if (faction.getId().equalsIgnoreCase("0"))
		{
			if (perms
					.hasPermission(player, "mbr.user.multiplier.factions.wild"))
				return cW;
			return 1.0;
		}
		if (faction.getId().equalsIgnoreCase("-1"))
		{
			if (perms
					.hasPermission(player, "mbr.user.multiplier.factions.safe"))
				return cSZ;
			return 1.0;
		}
		if (faction.getId().equalsIgnoreCase("-2"))
		{
			if (perms.hasPermission(player, "mbr.user.multiplier.factions.war"))
				return cWZ;
			return 1.0;
		}
		if (faction.equals(fPlayer.getFaction()))
		{
			if (perms.hasPermission(player, "mbr.user.multiplier.factions.own"))
				return cO;
			return 1.0;
		}
		if (faction.getFlag(FFlag.PEACEFUL))
		{
			if (perms.hasPermission(player,
					"mbr.user.multiplier.factions.peaceful"))
				return cP;
			return 1.0;
		}
		if (FactionsUtil.getFactionRelation(fPlayer.getFaction(), faction)
				.equals(Rel.ALLY))
		{
			if (perms
					.hasPermission(player, "mbr.user.multiplier.factions.ally"))
				return cA;
			return 1.0;
		}
		if (FactionsUtil.getFactionRelation(fPlayer.getFaction(), faction)
				.equals(Rel.ENEMY))
		{
			if (perms.hasPermission(player,
					"mbr.user.multiplier.factions.enemy"))
				return cE;
			return 1.0;
		}
		if (perms.hasPermission(player, "mbr.user.multiplier.factions.neutral"))
			return cN;
		return 1.0;
	}

	private P checkFactions17()
	{
		Plugin plugin = pm.getPlugin("Factions");

		if (plugin == null || !(plugin instanceof P))
		{
			return null;
		}
		if (!StringUtils.containsIgnoreCase(plugin.getDescription()
				.getVersion(), "1.7"))
		{
			return null;
		}

		MobBountyMessage.logToConsole("Factions v1.7.x hooked.");
		hooks.add("Factions");
		return (P) plugin;
	}

	private GriefPrevention checkGriefPrevention()
	{
		Plugin plugin = pm.getPlugin("GriefPrevention");

		if (plugin == null || !(plugin instanceof GriefPrevention))
		{
			return null;
		}

		MobBountyMessage.logToConsole("GriefPrevention hooked.");
		hooks.add("GriefPrevention");
		return (GriefPrevention) plugin;
	}

	private double checkGriefPreventionMult(Player player, Location location)
	{
		if (griefPrevention == null)
		{
			return 1.0;
		}
		String cNS = configs.getProperty(MobBountyConfFile.GRIEFPREVENTION,
				"ClaimMultiplierRate");
		double cN = MobBountyUtils.getDouble(cNS, 1.0);
		if (perms.hasPermission(player, "mbr.user.multiplier.griefprevention"))
			return cN;
		return 1.0;
	}

	private Heroes checkHeroes()
	{
		Plugin plugin = pm.getPlugin("Heroes");

		if (plugin == null || !(plugin instanceof Heroes))
		{
			return null;
		}

		MobBountyMessage.logToConsole("Heroes hooked.");
		hooks.add("Heroes");
		return (Heroes) plugin;
	}

	private double checkHeroesMult(Player player, Location location)
	{
		if (heroes == null)
		{
			return 1.0;
		}
		String cNS = configs.getProperty(MobBountyConfFile.HEROES,
				"killerBonusRate");
		double cN = MobBountyUtils.getDouble(cNS, 1.0);
		if (perms.hasPermission(player, "mbr.user.multiplier.heroes"))
			return cN;
		return 1.0;
	}

	private HeroStronghold checkHeroStronghold()
	{
		Plugin plugin = pm.getPlugin("HeroStronghold");

		if (plugin == null || !(plugin instanceof HeroStronghold))
		{
			return null;
		}

		MobBountyMessage.logToConsole("HeroStronghold hooked.");
		hooks.add("HeroStronghold");
		return (HeroStronghold) plugin;
	}

	private double checkHeroStrongholdMult(Player player, Location location)
	{
		if (heroStronghold == null)
		{
			return 1.0;
		}
		String cNS = configs.getProperty(MobBountyConfFile.HEROSTRONGHOLD,
				"RegionMultiplierRate");
		double cN = MobBountyUtils.getDouble(cNS, 1.0);
		if (perms.hasPermission(player, "mbr.user.multiplier.herostronghold"))
			return cN;
		return 1.0;
	}

	private Likeaboss checkLikeaboss()
	{
		Plugin plugin = pm.getPlugin("Likeaboss");

		if (plugin == null || !(plugin instanceof Likeaboss))
		{
			return null;
		}

		MobBountyMessage.logToConsole("Likeaboss hooked.");
		hooks.add("Likeaboss");
		return (Likeaboss) plugin;
	}

	private double checkLikeabossMult(Player player, Location location,
			Entity entity)
	{
		if (likeaboss == null)
		{
			return 1.0;
		}
		String cBS = configs.getProperty(MobBountyConfFile.LIKEABOSS,
				"bossMultiplierRate");
		double cB = MobBountyUtils.getDouble(cBS, 1.0);
		if (likeaboss.getBossManager().getBoss(entity) != null)
		{
			if (perms.hasPermission(player, "mbr.user.multiplier.likeaboss"))
				return cB;
			return 1.0;
		}
		return 1.0;
	}

	private MazeMania checkMazeMania()
	{
		Plugin plugin = pm.getPlugin("MazeMania");

		if (plugin == null || !(plugin instanceof MazeMania))
		{
			return null;
		}

		MobBountyMessage.logToConsole("MazeMania hooked.");
		hooks.add("MazeMania");
		return (MazeMania) plugin;
	}

	private double checkMazeManiaMult(Player player, Location location)
	{
		if (mazeMania == null)
		{
			return 1.0;
		}
		String cNS = configs.getProperty(MobBountyConfFile.MAZEMANIA,
				"MazeMultiplierRate");
		double cN = MobBountyUtils.getDouble(cNS, 1.0);
		if (perms.hasPermission(player, "mbr.user.multiplier.mazemania"))
			return cN;
		return 1.0;
	}

	private mcMMO checkMCMMO()
	{
		Plugin plugin = pm.getPlugin("mcMMO");

		if (plugin == null || !(plugin instanceof mcMMO))
		{
			return null;
		}

		MobBountyMessage.logToConsole("mcMMO hooked.");
		hooks.add("mcMMO");
		return (mcMMO) plugin;
	}

	private double checkMCMMOMult(Player player, Location location)
	{
		if (mcmmo == null)
		{
			return 1.0;
		}
		String cNS = configs.getProperty(MobBountyConfFile.MCMMO,
				"killerBonusRate");
		double cN = MobBountyUtils.getDouble(cNS, 1.0);
		if (perms.hasPermission(player, "mbr.user.multiplier.mcmmo"))
			return cN;
		return 1.0;
	}

	private MobArena checkMobArena()
	{
		Plugin plugin = pm.getPlugin("MobArena");

		if (plugin == null || !(plugin instanceof MobArena))
		{
			return null;
		}

		MobBountyMessage.logToConsole("MobArena hooked.");
		hooks.add("MobArena");
		return (MobArena) plugin;
	}

	private double checkMobArenaMult(Player player, Location location)
	{
		if (mobArena == null)
		{
			return 1.0;
		}
		String cNS = configs.getProperty(MobBountyConfFile.MOBARENA,
				"MobArenaMultiplierRate");
		double cN = MobBountyUtils.getDouble(cNS, 1.0);
		if (perms.hasPermission(player, "mbr.user.multiplier.mobarena"))
			return cN;
		return 1.0;
	}

	private PreciousStones checkPreciousStones()
	{
		Plugin plugin = pm.getPlugin("PreciousStones");

		if (plugin == null || !(plugin instanceof PreciousStones))
		{
			return null;
		}

		MobBountyMessage.logToConsole("PreciousStones hooked.");
		hooks.add("PreciousStones");
		return (PreciousStones) plugin;
	}

	private double checkPreciousStonesMult(Player player, Location location)
	{
		if (preciousStones == null)
		{
			return 1.0;
		}
		String cNS = configs.getProperty(MobBountyConfFile.PRECIOUSSTONES,
				"RegionMultiplierRate");
		double cN = MobBountyUtils.getDouble(cNS, 1.0);
		if (perms.hasPermission(player, "mbr.user.multiplier.preciousstones"))
			return cN;
		return 1.0;
	}

	private Regios checkRegios()
	{
		Plugin plugin = pm.getPlugin("Regios");

		if (plugin == null || !(plugin instanceof Regios))
		{
			return null;
		}

		MobBountyMessage.logToConsole("Regios hooked.");
		hooks.add("Regios");
		return (Regios) plugin;
	}

	private double checkRegiosMult(Player player, Location location)
	{
		if (regios == null)
		{
			return 1.0;
		}
		String cNS = configs.getProperty(MobBountyConfFile.REGIOS,
				"regionMultiplierRate");
		double cN = MobBountyUtils.getDouble(cNS, 1.0);
		if (perms.hasPermission(player, "mbr.user.multiplier.regios"))
			return cN;
		return 1.0;
	}

	private Residence checkResidence()
	{
		Plugin plugin = pm.getPlugin("Residence");

		if (plugin == null || !(plugin instanceof Residence))
		{
			return null;
		}

		MobBountyMessage.logToConsole("Residence hooked.");
		hooks.add("Residence");
		return (Residence) plugin;
	}

	private double checkResidenceMult(Player player, Location location)
	{
		if (residence == null)
		{
			return 1.0;
		}
		String cNS = configs.getProperty(MobBountyConfFile.RESIDENCE,
				"ResidenceMultiplierRate");
		double cN = MobBountyUtils.getDouble(cNS, 1.0);
		if (perms.hasPermission(player, "mbr.user.multiplier.residence"))
			return cN;
		return 1.0;
	}

	private RoadProtector checkRoadProtector()
	{
		Plugin plugin = pm.getPlugin("RoadProtector");

		if (plugin == null || !(plugin instanceof RoadProtector))
		{
			return null;
		}

		MobBountyMessage.logToConsole("RoadProtector hooked.");
		hooks.add("RoadProtector");
		return (RoadProtector) plugin;
	}

	private double checkRoadProtectorMult(Player player, Location location)
	{
		if (roadProtector == null)
		{
			return 1.0;
		}
		String cNS = configs.getProperty(MobBountyConfFile.ROADPROTECTOR,
				"RegionMultiplierRate");
		double cN = MobBountyUtils.getDouble(cNS, 1.0);
		if (perms.hasPermission(player, "mbr.user.multiplier.roadprotector"))
			return cN;
		return 1.0;
	}

	private SimpleClans checkSimpleClans()
	{
		Plugin plugin = pm.getPlugin("SimpleClans");

		if (plugin == null || !(plugin instanceof SimpleClans))
		{
			return null;
		}

		MobBountyMessage.logToConsole("SimpleClans hooked.");
		hooks.add("SimpleClans");
		return (SimpleClans) plugin;
	}

	private double checkSimpleClansMult(Player player, Location location,
			Entity entity)
	{
		if (simpleClans == null)
		{
			return 1.0;
		}
		String cRS = configs.getProperty(MobBountyConfFile.SIMPLECLANS,
				"rivalClans.multiplierRate");
		String cAS = configs.getProperty(MobBountyConfFile.SIMPLECLANS,
				"allyClans.multiplierRate");
		String cNS = configs.getProperty(MobBountyConfFile.SIMPLECLANS,
				"neutralClans.multiplierRate");
		double cN = MobBountyUtils.getDouble(cNS, 1.0);
		double cR = MobBountyUtils.getDouble(cRS, 1.0);
		double cA = MobBountyUtils.getDouble(cAS, 1.0);
		if (!(entity instanceof Player))
		{
			return cN;
		}
		Player player2 = ((Player) entity);
		ClanPlayer cPlayer1 = simpleClans.getClanManager()
				.getClanPlayer(player);
		Clan clan1 = cPlayer1.getClan();
		if (clan1 == null)
		{
			if (perms.hasPermission(player,
					"mbr.user.multiplier.simpleclans.neutral"))
				return cN;
			return 1.0;
		}
		ClanPlayer cPlayer2 = simpleClans.getClanManager().getClanPlayer(
				player2);
		Clan clan2 = cPlayer2.getClan();
		if (clan2 == null)
		{
			if (perms.hasPermission(player,
					"mbr.user.multiplier.simpleclans.neutral"))
				return cN;
			return 1.0;
		}
		if (clan1.isAlly(clan2.getTag()))
		{
			if (perms.hasPermission(player,
					"mbr.user.multiplier.simpleclans.ally"))
				return cA;
			return 1.0;
		}
		if (clan1.isRival(clan2.getTag()))
		{
			if (perms.hasPermission(player,
					"mbr.user.multiplier.simpleclans.rival"))
				return cR;
			return 1.0;
		}
		return cN;
	}

	private Spout checkSpout()
	{
		Plugin plugin = pm.getPlugin("Spout");

		if (plugin == null || !(plugin instanceof Spout))
		{
			return null;
		}

		MobBountyMessage.logToConsole("Spout hooked.");
		hooks.add("Spout");
		return (Spout) plugin;
	}

	private double checkTownMult(Player player, Location location)
	{
		if (towny == null)
		{
			return 1.0;
		}
		String cWS = configs.getProperty(MobBountyConfFile.TOWNY,
				"wilderness.multiplierRate");
		String cOS = configs.getProperty(MobBountyConfFile.TOWNY,
				"ownTown.multiplierRate");
		String cNS = configs.getProperty(MobBountyConfFile.TOWNY,
				"neutralTown.multiplierRate");
		String cES = configs.getProperty(MobBountyConfFile.TOWNY,
				"enemyTown.multiplierRate");
		String cAS = configs.getProperty(MobBountyConfFile.TOWNY,
				"allyTown.multiplierRate");
		double cW = MobBountyUtils.getDouble(cWS, 1.0);
		double cO = MobBountyUtils.getDouble(cOS, 1.0);
		double cN = MobBountyUtils.getDouble(cNS, 1.0);
		double cE = MobBountyUtils.getDouble(cES, 1.0);
		double cA = MobBountyUtils.getDouble(cAS, 1.0);
		if (TownyUniverse.isWilderness(location.getBlock()))
		{
			if (perms.hasPermission(player, "mbr.user.multiplier.towny.wild"))
				return cW;
			return 1.0;
		}
		Resident resident = TownyUtil.getResident(player.getName());
		Town town1 = TownyUtil.getResidentTown(resident);
		Town town2 = TownyUtil.getTownAtLocation(location);
		if (town1.equals(town2))
		{
			if (perms.hasPermission(player, "mbr.user.multiplier.towny.own"))
				return cO;
			return 1.0;
		}
		if (TownyUtil.areTownsAllied(town1, town2))
		{
			if (perms.hasPermission(player, "mbr.user.multiplier.towny.ally"))
				return cA;
			return 1.0;
		}
		if (TownyUtil.areTownsEnemies(town1, town2))
		{
			if (perms.hasPermission(player, "mbr.user.multiplier.towny.enemy"))
				return cE;
			return 1.0;
		}
		if (perms.hasPermission(player, "mbr.user.multiplier.towny.neutral"))
			return cN;
		return 1.0;
	}

	private Towny checkTowny()
	{
		Plugin plugin = pm.getPlugin("Towny");

		if (plugin == null || !(plugin instanceof Towny))
		{
			return null;
		}

		MobBountyMessage.logToConsole("Towny hooked.");
		hooks.add("Towny");
		return (Towny) plugin;
	}

	private UltimateArena checkUltimateArena()
	{
		Plugin plugin = pm.getPlugin("UltimateArena");

		if (plugin == null || !(plugin instanceof UltimateArena))
		{
			return null;
		}

		MobBountyMessage.logToConsole("UltimateArena hooked.");
		hooks.add("UltimateArena");
		return (UltimateArena) plugin;
	}

	private double checkUltimateArenaMult(Player player, Location location)
	{
		if (ultimateArena == null)
		{
			return 1.0;
		}
		String cNS = configs.getProperty(MobBountyConfFile.ULTIMATEARENA,
				"ArenaMultiplierRate");
		double cN = MobBountyUtils.getDouble(cNS, 1.0);
		if (perms.hasPermission(player, "mbr.user.multiplier.ultimatearena"))
			return cN;
		return 1.0;
	}

	private WorldGuardPlugin checkWorldGuard()
	{
		Plugin plugin = pm.getPlugin("WorldGuard");

		if (plugin == null || !(plugin instanceof WorldGuardPlugin))
		{
			return null;
		}

		MobBountyMessage.logToConsole("WorldGuard hooked.");
		hooks.add("WorldGuard");
		return (WorldGuardPlugin) plugin;
	}

	private double checkWorldGuardMult(Player player, Location location)
	{
		if (worldguard == null)
		{
			return 1.0;
		}
		String cNS = configs.getProperty(MobBountyConfFile.WORLDGUARD,
				"regionMultiplierRate");
		double cN = MobBountyUtils.getDouble(cNS, 1.0);
		if (perms.hasPermission(player, "mbr.user.multiplier.worldguard"))
			return cN;
		return 1.0;
	}

	private boolean earnChunkOwn(Player player, Location location)
	{
		String cCS = configs.getProperty(MobBountyConfFile.CHUNKOWN,
				"allowPaymentInsideChunks");
		boolean cC = Boolean.parseBoolean(cCS);
		if (ChunkOwn.findOwnedChunk(location.getBlock()) != null)
		{
			if (perms.hasPermission(player, "mbr.user.collect.chunkown"))
				return cC;
			return false;
		}
		return true;
	}

	private boolean earnDeityProtect(Player player, Location location)
	{
		String cCS = configs.getProperty(MobBountyConfFile.DEITYPROTECT,
				"allowPaymentInsideRegions");
		boolean cC = Boolean.parseBoolean(cCS);
		if (ChunkOwn.findOwnedChunk(location.getBlock()) != null)
		{
			if (perms.hasPermission(player, "mbr.user.collect.deityprotect"))
				return cC;
			return false;
		}
		return true;
	}

	private boolean earnFaction(Player player, Location location)
	{
		String cOS = configs.getProperty(MobBountyConfFile.FACTIONS,
				"ownLand.allowPayments");
		String cES = configs.getProperty(MobBountyConfFile.FACTIONS,
				"enemyLand.allowPayments");
		String cAS = configs.getProperty(MobBountyConfFile.FACTIONS,
				"allyLand.allowPayments");
		String cWS = configs.getProperty(MobBountyConfFile.FACTIONS,
				"wilderness.allowPayments");
		String cSZS = configs.getProperty(MobBountyConfFile.FACTIONS,
				"safeZone.allowPayments");
		String cWZS = configs.getProperty(MobBountyConfFile.FACTIONS,
				"warZone.allowPayments");
		String cPS = configs.getProperty(MobBountyConfFile.FACTIONS,
				"peacefulLand.allowPayments");
		String cNS = configs.getProperty(MobBountyConfFile.FACTIONS,
				"neutralLand.allowPayments");
		boolean cW = Boolean.parseBoolean(cWS);
		boolean cO = Boolean.parseBoolean(cOS);
		boolean cN = Boolean.parseBoolean(cNS);
		boolean cE = Boolean.parseBoolean(cES);
		boolean cA = Boolean.parseBoolean(cAS);
		boolean cP = Boolean.parseBoolean(cPS);
		boolean cWZ = Boolean.parseBoolean(cWZS);
		boolean cSZ = Boolean.parseBoolean(cSZS);
		Faction faction = Board.getFactionAt(location);
		FPlayer fPlayer = FPlayers.i.get(player);
		if (faction.getId().equalsIgnoreCase("0"))
		{
			if (perms.hasPermission(player, "mbr.user.collect.factions.wild"))
				return cW;
			return false;
		}
		if (faction.getId().equalsIgnoreCase("-1"))
		{
			if (perms.hasPermission(player, "mbr.user.collect.factions.safe"))
				return cSZ;
			return false;
		}
		if (faction.getId().equalsIgnoreCase("-2"))
		{
			if (perms.hasPermission(player, "mbr.user.collect.factions.war"))
				return cWZ;
			return false;
		}
		if (faction.equals(fPlayer.getFaction()))
		{
			if (perms.hasPermission(player, "mbr.user.collect.factions.own"))
				return cO;
			return false;
		}
		if (faction.getFlag(FFlag.PEACEFUL))
		{
			if (perms.hasPermission(player,
					"mbr.user.collect.factions.peaceful"))
				return cP;
			return false;
		}
		if (FactionsUtil.getFactionRelation(fPlayer.getFaction(), faction)
				.equals(Rel.ALLY))
		{
			if (perms.hasPermission(player, "mbr.user.collect.factions.ally"))
				return cA;
			return false;
		}
		if (FactionsUtil.getFactionRelation(fPlayer.getFaction(), faction)
				.equals(Rel.ENEMY))
		{
			if (perms.hasPermission(player, "mbr.user.collect.factions.enemy"))
				return cE;
			return false;
		}
		if (perms.hasPermission(player, "mbr.user.collect.factions.neutral"))
			return cN;
		return false;
	}

	private boolean earnGriefPrevention(Player player, Location location)
	{
		String cCS = configs.getProperty(MobBountyConfFile.GRIEFPREVENTION,
				"allowPaymentInsideClaims");
		boolean cC = Boolean.parseBoolean(cCS);
		Claim claim = griefPrevention.dataStore.getClaimAt(location, false,
				null);
		if (claim != null)
		{
			if (perms.hasPermission(player, "mbr.user.collect.griefprevention"))
				return cC;
			return false;
		}
		return true;
	}

	private boolean earnHeroStronghold(Player player, Location location)
	{
		String cRS = configs.getProperty(MobBountyConfFile.HEROSTRONGHOLD,
				"allowPaymentInsideRegions");
		boolean cR = Boolean.parseBoolean(cRS);
		if (heroStronghold.getRegionManager().canBreakHere(location, player)
				|| heroStronghold.getRegionManager().canBuildHere(player,
						location))
		{
			if (perms.hasPermission(player, "mbr.user.collect.herostronghold"))
				return cR;
			return false;
		}
		return true;
	}

	private boolean earnMazeMania(Player player, Location location)
	{
		String cMS = configs.getProperty(MobBountyConfFile.MAZEMANIA,
				"allowPaymentInsideMaze");
		boolean cM = Boolean.parseBoolean(cMS);
		if (mazeMania.arena.isInArena(location))
		{
			if (perms.hasPermission(player, "mbr.user.collect.mazemania"))
				return cM;
			return false;
		}
		return true;
	}

	private boolean earnMobArena(Player player, Location location)
	{
		String cAS = configs.getProperty(MobBountyConfFile.MOBARENA,
				"allowPaymentInsideArena");
		boolean cA = Boolean.parseBoolean(cAS);
		if (mobArenaHandler.isPlayerInArena(player))
		{
			if (perms.hasPermission(player, "mbr.user.collect.mobarena"))
				return cA;
			return false;
		}
		return true;
	}

	private boolean earnPreciousStones(Player player, Location location)
	{
		String cRS = configs.getProperty(MobBountyConfFile.PRECIOUSSTONES,
				"allowPaymentInsideRegions");
		boolean cR = Boolean.parseBoolean(cRS);
		Field field = preciousStones.getForceFieldManager().getField(
				location.getBlock());
		if (field != null)
		{
			if (field.hasFlag(FieldFlag.ALLOW_DESTROY)
					|| field.hasFlag(FieldFlag.ALLOW_PLACE))
			{
				if (preciousStones.getForceFieldManager().isApplyToAllowed(
						field, player.getName()))
				{
					if (perms.hasPermission(player,
							"mbr.user.collect.preciousstones"))
						return cR;
					return false;
				}
				return false;
			}
			return false;
		}
		return true;
	}

	private boolean earnRegios(Player player, Location location)
	{
		String cRS = configs.getProperty(MobBountyConfFile.REGIOS,
				"allowPaymentInRegion");
		boolean cR = Boolean.parseBoolean(cRS);
		if (GlobalRegionManager.getRegion(location) != null)
		{
			if (perms.hasPermission(player, "mbr.user.collect.regios"))
				return cR;
			return false;
		}
		return true;
	}

	private boolean earnResidence(Player player, Location location)
	{
		String cRS = configs.getProperty(MobBountyConfFile.RESIDENCE,
				"allowPaymentInsideResidence");
		boolean cR = Boolean.parseBoolean(cRS);
		ClaimedResidence cRes = Residence.getResidenceManager().getByLoc(
				location);
		if (cRes.getPermissions().playerHas(player.getName(), "build", true))
		{
			if (perms.hasPermission(player, "mbr.user.collect.residence"))
				return cR;
			return false;
		}
		return true;
	}

	private boolean earnRoadProtector(Player player, Location location)
	{
		String cRS = configs.getProperty(MobBountyConfFile.ROADPROTECTOR,
				"allowPaymentInsideRegions");
		boolean cR = Boolean.parseBoolean(cRS);
		if (roadProtector.isProtected(location))
		{
			if (perms.hasPermission(player, "mbr.user.collect.roadprotector"))
				return cR;
			return false;
		}
		return true;
	}

	private boolean earnSimpleClans(Player player, Location location,
			Entity entity)
	{
		String cRS = configs.getProperty(MobBountyConfFile.FACTIONS,
				"rivalClans.allowPayment");
		String cAS = configs.getProperty(MobBountyConfFile.FACTIONS,
				"allyClans.allowPayment");
		String cNS = configs.getProperty(MobBountyConfFile.FACTIONS,
				"neutralClans.allowPayment");
		boolean cN = Boolean.parseBoolean(cNS);
		boolean cR = Boolean.parseBoolean(cRS);
		boolean cA = Boolean.parseBoolean(cAS);
		if (!(entity instanceof Player))
		{
			return true;
		}
		Player player2 = ((Player) entity);
		ClanPlayer cPlayer1 = simpleClans.getClanManager()
				.getClanPlayer(player);
		Clan clan1 = cPlayer1.getClan();
		if (clan1 == null)
		{
			if (perms.hasPermission(player,
					"mbr.user.collect.simpleclans.neutral"))
				return cN;
			return false;
		}
		ClanPlayer cPlayer2 = simpleClans.getClanManager().getClanPlayer(
				player2);
		Clan clan2 = cPlayer2.getClan();
		if (clan2 == null)
		{
			if (perms.hasPermission(player,
					"mbr.user.collect.simpleclans.neutral"))
				return cN;
			return false;
		}
		if (clan1.isAlly(clan2.getTag()))
		{
			if (perms
					.hasPermission(player, "mbr.user.collect.simpleclans.ally"))
				return cA;
			return false;
		}
		if (clan1.isRival(clan2.getTag()))
		{
			if (perms.hasPermission(player,
					"mbr.user.collect.simpleclans.rival"))
				return cR;
			return false;
		}
		if (perms.hasPermission(player, "mbr.user.collect.simpleclans.neutral"))
			return cN;
		return false;
	}

	private boolean earnTown(Player player, Location location)
	{
		String cWS = configs.getProperty(MobBountyConfFile.TOWNY,
				"wilderness.allowPayments");
		String cOS = configs.getProperty(MobBountyConfFile.TOWNY,
				"ownTown.allowPayments");
		String cNS = configs.getProperty(MobBountyConfFile.TOWNY,
				"neutralTown.allowPayments");
		String cES = configs.getProperty(MobBountyConfFile.TOWNY,
				"enemyTown.allowPayments");
		String cAS = configs.getProperty(MobBountyConfFile.TOWNY,
				"allyTown.allowPayments");
		boolean cW = Boolean.parseBoolean(cWS);
		boolean cO = Boolean.parseBoolean(cOS);
		boolean cN = Boolean.parseBoolean(cNS);
		boolean cE = Boolean.parseBoolean(cES);
		boolean cA = Boolean.parseBoolean(cAS);
		if (TownyUniverse.isWilderness(location.getBlock()))
		{
			if (perms.hasPermission(player, "mbr.user.collect.towny.wild"))
				return cW;
			return false;
		}
		Resident resident = TownyUtil.getResident(player.getName());
		Town town1 = TownyUtil.getResidentTown(resident);
		Town town2 = TownyUtil.getTownAtLocation(location);
		if (town1.equals(town2))
		{
			if (perms.hasPermission(player, "mbr.user.collect.towny.own"))
				return cO;
			return false;
		}
		if (TownyUtil.areTownsAllied(town1, town2))
		{
			if (perms.hasPermission(player, "mbr.user.multiplier.towny.ally"))
				return cA;
			return false;
		}
		if (TownyUtil.areTownsEnemies(town1, town2))
		{
			if (perms.hasPermission(player, "mbr.user.multiplier.towny.enemy"))
				return cE;
			return false;
		}
		if (perms.hasPermission(player, "mbr.user.multiplier.towny.neutral"))
			return cN;
		return false;
	}

	private boolean earnUltimateArena(Player player, Location location)
	{
		String cAS = configs.getProperty(MobBountyConfFile.ULTIMATEARENA,
				"allowPaymentInsideArenas");
		boolean cA = Boolean.parseBoolean(cAS);
		if (ultimateArena.isInArena(location))
		{
			if (perms.hasPermission(player, "mbr.user.collect.ultimatearena"))
				return cA;
			return false;
		}
		return true;
	}

	private boolean earnWorldGuard(Player player, Location location)
	{
		String cRS = configs.getProperty(MobBountyConfFile.WORLDGUARD,
				"allowPaymentInsideRegions");
		boolean cR = Boolean.parseBoolean(cRS);
		if (worldguard.getRegionManager(location.getWorld())
				.getApplicableRegions(location).size() != 0)
		{
			if (perms.hasPermission(player, "mbr.user.collect.worldguard"))
				return cR;
			return false;
		}
		return true;
	}

	public ChunkOwn getChunkOwn()
	{
		return chunkOwn;
	}

	public DeityProtect getDeityProtect()
	{
		return deityProtect;
	}

	public P getFactions()
	{
		return factions_17;
	}

	public GriefPrevention getGriefPrevention()
	{
		return griefPrevention;
	}

	public Heroes getHeroes()
	{
		return heroes;
	}

	public HeroStronghold getHeroStronghold()
	{
		return heroStronghold;
	}

	public List<String> getHookedPlugins()
	{
		return hooks;
	}

	public Likeaboss getLikeaboss()
	{
		return likeaboss;
	}

	public MazeMania getMazeMania()
	{
		return mazeMania;
	}

	public mcMMO getMCMMO()
	{
		return mcmmo;
	}

	public MobArena getMobArena()
	{
		return mobArena;
	}

	public MobArenaHandler getMobArenaHandler()
	{
		return mobArenaHandler;
	}

	public PreciousStones getPreciousStones()
	{
		return preciousStones;
	}

	public Regios getRegios()
	{
		return regios;
	}

	public RegiosAPI getRegiosAPI()
	{
		return regiosAPI;
	}

	public Residence getResidence()
	{
		return residence;
	}

	public RoadProtector getRoadProtector()
	{
		return roadProtector;
	}

	public SimpleClans getSimpleClans()
	{
		return simpleClans;
	}

	public Spout getSpout()
	{
		return spout;
	}

	public Towny getTowny()
	{
		return towny;
	}

	public UltimateArena getUltimateArena()
	{
		return ultimateArena;
	}

	public WorldGuardPlugin getWorldGuard()
	{
		return worldguard;
	}

	private void setupSupport()
	{
		towny = checkTowny();
		factions_17 = checkFactions17();
		mcmmo = checkMCMMO();
		heroes = checkHeroes();
		worldguard = checkWorldGuard();
		regios = checkRegios();
		likeaboss = checkLikeaboss();
		mobArena = checkMobArena();
		spout = checkSpout();
		griefPrevention = checkGriefPrevention();
		deityProtect = checkDeityProtect();
		chunkOwn = checkChunkOwn();
		preciousStones = checkPreciousStones();
		roadProtector = checkRoadProtector();
		mazeMania = checkMazeMania();
		ultimateArena = checkUltimateArena();
		heroStronghold = checkHeroStronghold();
		residence = checkResidence();
		simpleClans = checkSimpleClans();
	}

	private boolean useChunkOwn()
	{
		if (chunkOwn == null)
		{
			return false;
		}
		String bleh = configs.getProperty(MobBountyConfFile.CHUNKOWN,
				"useChunkOwn");
		if (bleh == null)
		{
			return false;
		}
		boolean use = Boolean.parseBoolean(bleh);
		return use;
	}

	private boolean useDeityProtect()
	{
		if (deityProtect == null)
		{
			return false;
		}
		String bleh = configs.getProperty(MobBountyConfFile.DEITYPROTECT,
				"useDeityProtect");
		if (bleh == null)
		{
			return false;
		}
		boolean use = Boolean.parseBoolean(bleh);
		return use;
	}

	private boolean useFactions()
	{
		if (factions_17 == null)
		{
			return false;
		}
		String bleh = configs.getProperty(MobBountyConfFile.FACTIONS,
				"useFactions");
		if (bleh == null)
		{
			return false;
		}
		boolean use = Boolean.parseBoolean(bleh);
		return use;
	}

	private boolean useGriefPrevention()
	{
		if (griefPrevention == null)
		{
			return false;
		}
		String bleh = configs.getProperty(MobBountyConfFile.GRIEFPREVENTION,
				"useGriefPrevention");
		if (bleh == null)
		{
			return false;
		}
		boolean use = Boolean.parseBoolean(bleh);
		return use;
	}

	private boolean useHeroes()
	{
		if (heroes == null)
		{
			return false;
		}
		String bleh = configs
				.getProperty(MobBountyConfFile.HEROES, "useHeroes");
		if (bleh == null)
		{
			return false;
		}
		boolean use = Boolean.parseBoolean(bleh);
		return use;
	}

	private boolean useHeroStronghold()
	{
		if (heroStronghold == null)
		{
			return false;
		}
		String bleh = configs.getProperty(MobBountyConfFile.HEROSTRONGHOLD,
				"useHeroStronghold");
		if (bleh == null)
		{
			return false;
		}
		boolean use = Boolean.parseBoolean(bleh);
		return use;
	}

	private boolean useLikeaboss()
	{
		if (likeaboss == null)
		{
			return false;
		}
		String bleh = configs.getProperty(MobBountyConfFile.LIKEABOSS,
				"useLikeaboss");
		if (bleh == null)
		{
			return false;
		}
		boolean use = Boolean.parseBoolean(bleh);
		return use;
	}

	private boolean useMazeMania()
	{
		if (mazeMania == null)
		{
			return false;
		}
		String bleh = configs.getProperty(MobBountyConfFile.MAZEMANIA,
				"useMazeMania");
		if (bleh == null)
		{
			return false;
		}
		boolean use = Boolean.parseBoolean(bleh);
		return use;
	}

	private boolean useMCMMO()
	{
		if (mcmmo == null)
		{
			return false;
		}
		String bleh = configs.getProperty(MobBountyConfFile.MCMMO, "useMCMMO");
		if (bleh == null)
		{
			return false;
		}
		boolean use = Boolean.parseBoolean(bleh);
		return use;
	}

	private boolean useMobArena()
	{
		if (mobArena == null || mobArenaHandler == null)
		{
			return false;
		}
		String bleh = configs.getProperty(MobBountyConfFile.MOBARENA,
				"useMobArena");
		if (bleh == null)
		{
			return false;
		}
		boolean use = Boolean.parseBoolean(bleh);
		return use;
	}

	private boolean usePreciousStones()
	{
		if (preciousStones == null)
		{
			return false;
		}
		String bleh = configs.getProperty(MobBountyConfFile.PRECIOUSSTONES,
				"usePreciousStones");
		if (bleh == null)
		{
			return false;
		}
		boolean use = Boolean.parseBoolean(bleh);
		return use;
	}

	private boolean useRegios()
	{
		if (regios == null)
		{
			return false;
		}
		String bleh = configs
				.getProperty(MobBountyConfFile.REGIOS, "useRegios");
		if (bleh == null)
		{
			return false;
		}
		boolean use = Boolean.parseBoolean(bleh);
		return use;
	}

	private boolean useResidence()
	{
		if (residence == null)
		{
			return false;
		}
		String bleh = configs.getProperty(MobBountyConfFile.RESIDENCE,
				"useResidence");
		if (bleh == null)
		{
			return false;
		}
		boolean use = Boolean.parseBoolean(bleh);
		return use;
	}

	private boolean useRoadProtector()
	{
		if (roadProtector == null)
		{
			return false;
		}
		String bleh = configs.getProperty(MobBountyConfFile.ROADPROTECTOR,
				"useRoadProtector");
		if (bleh == null)
		{
			return false;
		}
		boolean use = Boolean.parseBoolean(bleh);
		return use;
	}

	private boolean useSimpleClans()
	{
		if (simpleClans == null)
		{
			return false;
		}
		String bleh = configs.getProperty(MobBountyConfFile.SIMPLECLANS,
				"useSimpleClans");
		if (bleh == null)
		{
			return false;
		}
		boolean use = Boolean.parseBoolean(bleh);
		return use;
	}

	public boolean useSpout()
	{
		if (spout == null)
		{
			return false;
		}
		String bleh = configs.getProperty(MobBountyConfFile.SPOUT, "useSpout");
		if (bleh == null)
		{
			return false;
		}
		boolean use = Boolean.parseBoolean(bleh);
		return use;
	}

	private boolean useTowny()
	{
		if (towny == null)
		{
			return false;
		}
		String bleh = configs.getProperty(MobBountyConfFile.TOWNY, "useTowny");
		if (bleh == null)
		{
			return false;
		}
		boolean use = Boolean.parseBoolean(bleh);
		return use;
	}

	private boolean useUltimateArena()
	{
		if (ultimateArena == null)
		{
			return false;
		}
		String bleh = configs.getProperty(MobBountyConfFile.ULTIMATEARENA,
				"useUltimateArena");
		if (bleh == null)
		{
			return false;
		}
		boolean use = Boolean.parseBoolean(bleh);
		return use;
	}

	private boolean useWorldGuard()
	{
		if (worldguard == null)
		{
			return false;
		}
		String bleh = configs.getProperty(MobBountyConfFile.WORLDGUARD,
				"useWorldGuard");
		if (bleh == null)
		{
			return false;
		}
		boolean use = Boolean.parseBoolean(bleh);
		return use;
	}

}
