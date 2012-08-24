package info.hawksharbor.MobBounty.Managers;

import info.hawksharbor.MobBounty.MobBountyReloaded;
import info.hawksharbor.MobBounty.Utils.MobBountyAPI;
import info.hawksharbor.MobBounty.Utils.MobBountyConfFile;
import info.hawksharbor.MobBounty.Utils.MobBountyCreature;
import info.hawksharbor.MobBounty.Utils.MobBountyItemInfo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.LivingEntity;

public class MobBountyConfigs
{

	private final HashMap<MobBountyConfFile, YamlConfiguration> _configurations;

	@SuppressWarnings("unused")
	private final MobBountyReloaded _plugin;
	private final HashMap<String, HashMap<MobBountyCreature, ArrayList<MobBountyItemInfo>>> _worldDropTable;

	public MobBountyConfigs(MobBountyReloaded plugin)
	{
		_plugin = plugin;

		_configurations = new HashMap<MobBountyConfFile, YamlConfiguration>();
		_worldDropTable = new HashMap<String, HashMap<MobBountyCreature, ArrayList<MobBountyItemInfo>>>();

		this.loadConfig();
	}

	private void createConfig(MobBountyConfFile config, File file)
	{
		switch (config)
		{
		case GENERAL:
			YamlConfiguration generalConf = YamlConfiguration
					.loadConfiguration(file);
			generalConf.set("locale", "en");
			generalConf.set("modifyExperienceDrops", false);
			generalConf.set("useMobSpawnerProtection", false);
			generalConf.set("mobSpawnerProtectionRadius", new Integer(5));
			generalConf.set("mobSpawnerProtectionRate", new Double(0.0));
			generalConf.set("spawnedMobProtection", false);
			generalConf.set("useDepreciativeReturn", false);
			generalConf.set("depreciativeReturnRate", new Double(0.1));
			generalConf.set("timeAfterLogin", new Long(10));
			generalConf.set("mobCap.use", false);
			generalConf.set("mobCap.limit", new Integer(30));
			generalConf.set("mobCap.distance", new Integer(30));
			generalConf.set("killCache.use", false);
			generalConf.set("killCache.timeLimit", new Integer(30000));
			generalConf.set("preventCreativeEarning", false);
			generalConf.set("debugMode", false);
			generalConf.set("configsVersion",
					MobBountyAPI.instance.getConfigVersion());
			try
			{
				generalConf.save(file);
			}
			catch (IOException e2)
			{
			}

			_configurations.put(config, generalConf);
			break;

		case LOCALE:
			YamlConfiguration localeConf = YamlConfiguration
					.loadConfiguration(file);
			localeConf.set("en.Awarded",
					"&2You have been awarded &F%A &2for killing a &F%M&2.");
			localeConf.set("en.SpoutAwarded", "Earned %A");
			localeConf
					.set("en.CacheAwarded",
							"&2You have been awarded &F%A &2for killing a &F%K &2mobs in &F%T &2seconds.");
			localeConf.set("en.Fined",
					"&4You have been fined &F%A &4for killing a &F%M&4.");
			localeConf.set("en.SpoutFined", "Fined %A");
			localeConf
					.set("en.CacheFined",
							"&4You have been fined &F%A &4for killing a &F%K &4mobs in &F%T &4seconds.");
			localeConf
					.set("en.PartyAwarded",
							"&2You have been awarded &F%A &2for &F%P &2killing a &F%M&2.");
			localeConf
					.set("en.PartyFined",
							"&4You have been fined &F%A &4for &F%P &4killing a &F%M&4.");
			localeConf.set("en.NoAccess",
					"&CYou do not have access to that command.");
			localeConf.set("en.BroadcastStreak",
					"&F%P &2has a kill streak of &F%A&2!");
			localeConf.set("en.MBReward", "&2%M : &F%A");
			localeConf.set("en.MBRewardRange", "&2%M : &F%1 - %2");
			localeConf.set("en.MBFine", "&4%M : &F%A");
			localeConf.set("en.MBFineRange", "&4%M : &F%A - %2");
			localeConf.set("en.MBCheckDrop", "&2%M : &F%A");
			localeConf.set("en.MBCheckExp", "&2%M : &F%A XP");
			localeConf.set("en.MBGChange",
					"&2General setting &F%S &2has been changed to &F%V&2.");
			localeConf.set("en.MBGUsage", "&CUsage: /%C [property] <amount>");
			localeConf
					.set("en.MBGProperty",
							"&7Property: locale, debug, envmulti, timemulti, worldmulti, fortmulti, mobspawnprot, mobspawnprotrad, mobspawnprotrate, spawnmobprotection, spawnmobdrops, spawnmobexp, deprreturn, deprreturnrate, timeafterlogin, usemobcap, mobcaplimit, mobcapdist, moddrops, modexp");
			localeConf
					.set("en.MBRChange",
							"&2Default reward/fine for mob &F%M &2has been changed to &F%A&2.");
			localeConf.set("en.MBRUsage", "&CUsage: /%C [mob] <amount>");
			localeConf
					.set("en.MBRMobs",
							"&7Mob: Blaze, CaveSpider, Chicken, Cow, Creeper, ElectrifiedCreeper, Enderdragon, Enderman, Ghast, Giant, IronGolem, MagmaCube, Monster, Mooshroom, Ocelot, Pig, PigZombie, Player, SelfTamedCat, SelfTamedWolf, Sheep, Silverfish, Skeleton, Slime, SnowGolem, Spider, Squid, TamedCat, TamedWolf, Unknown, Villager, Wolf, Zombie");
			localeConf
					.set("en.MBWRChange",
							"&2Reward for mob &F%M &2in world &F%W &2has been changed to &F%A&2.");
			localeConf
					.set("en.MBWRReset",
							"&2Reward for mob &F%M &2in world &F%W &2has been reset to default.");
			localeConf.set("en.MBWRUsage",
					"&CUsage: /%C [world] [mob] <amount>");
			localeConf.set("en.MBWRWorlds", "&7World: %W");
			localeConf
					.set("en.MBWRMobs",
							"&7Mob: Blaze, CaveSpider, Chicken, Cow, Creeper, ElectrifiedCreeper, Enderdragon, Enderman, Ghast, Giant, IronGolem, MagmaCube, Monster, Mooshroom, Ocelot, Pig, PigZombie, SelfTamedCat, SelfTamedWolf, Sheep, Silverfish, Skeleton, Slime, SnowGolem, Spider, Squid, TamedCat, TamedWolf, Villager, Wolf, Zombie");
			localeConf
					.set("en.MBEMChange",
							"&2Multiplier for the &F%E &2environment has been changed to &F%A&2.");
			localeConf.set("en.MBEMUsage",
					"&CUsage: /%C [environment] <amount>");
			localeConf.set("en.MBEMEnvs", "&7Environment: Normal, Nether, End");
			localeConf
					.set("en.MBTMChange",
							"&2Multiplier during the &F%T &2has been changed to &F%A&2.");
			localeConf.set("en.MBTMUsage", "&CUsage: /%C [time] <amount>");
			localeConf.set("en.MBTMTimes",
					"&7Time: Day, Sunset, Night, Sunrise");
			localeConf
					.set("en.MBWMChange",
							"&2Multiplier for world &F%W &2has been changed to &F%A&2.");
			localeConf.set("en.MBWMUsage", "&CUsage: /%C [world] <amount>");
			localeConf.set("en.MBWMWorlds", "&7World: %W");
			localeConf
					.set("en.MBGMChange",
							"&2Multiplier for group &F%W &2has been changed to &F%A&2.");
			localeConf.set("en.MBGMUsage", "&CUsage: /%C [group] <amount>");
			localeConf
					.set("en.MBWMDChange",
							"&2Drops for mob &F%M&2 in world &F%W &2have been changed to &F%A&2.");
			localeConf.set("en.MBWMDUsage",
					"&CUsage: /%C [world] [mob] <drop formula>");
			localeConf.set("en.MBWMDWorlds", "&7World: %W");
			localeConf
					.set("en.MBWMDMobs",
							"&7Mob: Blaze, CaveSpider, Chicken, Cow, Creeper, ElectrifiedCreeper, Enderdragon, Enderman, Ghast, Giant, IronGolem, MagmaCube, Monster, Mooshroom, Ocelot, Pig, PigZombie, SelfTamedCat, SelfTamedWolf, Sheep, Silverfish, Skeleton, Slime, SnowGolem, Spider, Squid, TamedCat, TamedWolf, Villager, Wolf, Zombie");
			localeConf
					.set("en.MBMDChange",
							"&2Default drops for mob &F%M&2 &2have been changed to &F%A&2.");
			localeConf.set("en.MBMDUsage", "&CUsage: /%C [mob] <drop formula>");
			localeConf.set("en.MBMDWorlds", "&7World: %W");
			localeConf
					.set("en.MBMDMobs",
							"&7Mob: Blaze, CaveSpider, Chicken, Cow, Creeper, ElectrifiedCreeper, Enderdragon, Enderman, Ghast, Giant, IronGolem, MagmaCube, Monster, Mooshroom, Ocelot, Pig, PigZombie, SelfTamedCat, SelfTamedWolf, Sheep, Silverfish, Skeleton, Slime, SnowGolem, Spider, Squid, TamedCat, TamedWolf, Villager, Wolf, Zombie");
			localeConf
					.set("en.MBEChange",
							"&2Default XP drop for mob &F%M &2has been changed to &F%A&2.");
			localeConf.set("en.MBEUsage", "&CUsage: /%C [mob] <amount>");
			localeConf
					.set("en.MBEMobs",
							"&7Mob: Blaze, CaveSpider, Chicken, Cow, Creeper, ElectrifiedCreeper, Enderdragon, Enderman, Ghast, Giant, IronGolem, MagmaCube, Monster, Mooshroom, Ocelot, Pig, PigZombie, Player, SelfTamedCat, SelfTamedWolf, Sheep, Silverfish, Skeleton, Slime, SnowGolem, Spider, Squid, TamedCat, TamedWolf, Unknown, Villager, Wolf, Zombie");
			localeConf
					.set("en.MBWEChange",
							"&2Reward for mob &F%M &2in world &F%W &2has been changed to &F%A&2.");
			localeConf
					.set("en.MBWEReset",
							"&2Reward for mob &F%M &2in world &F%W &2has been reset to default.");
			localeConf.set("en.MBWEUsage",
					"&CUsage: /%C [world] [mob] <amount>");
			localeConf.set("en.MBWEWorlds", "&7World: %W");
			localeConf
					.set("en.MBWEMobs",
							"&7Mob: Blaze, CaveSpider, Chicken, Cow, Creeper, ElectrifiedCreeper, Enderdragon, Enderman, Ghast, Giant, IronGolem, MagmaCube, Monster, Mooshroom, Ocelot, Pig, PigZombie, SelfTamedCat, SelfTamedWolf, Sheep, Silverfish, Skeleton, Slime, SnowGolem, Spider, Squid, TamedCat, TamedWolf, Villager, Wolf, Zombie");
			localeConf.set("en.MBSSaved",
					"&2MobBountyReloaded config has been saved.");
			localeConf.set("en.MBLLoaded",
					"&2MobBountyReloaded config has been loaded.");
			localeConf.set("en.DebugHasPermission",
					"&F%P &2has permission: &F%D");
			localeConf.set("en.DebugLacksPermission",
					"&F%P &4lacks permission: &F%D");
			localeConf.set("en.DebugGeneral", "%M");
			localeConf.set("en.UpdateAvailable",
					"&2A new version of MobBountyReloaded is available.");
			localeConf.set("en.NewVersion",
					"&2New Version: v%N | Your version: v%O");
			localeConf.set("en.GetItAt", "&2Get it at http://mbr.inesgar.org/");
			try
			{
				localeConf.save(file);
			}
			catch (IOException e1)
			{
			}

			_configurations.put(config, localeConf);
			break;

		case MULTIPLIERS:
			YamlConfiguration multiplierConfig = YamlConfiguration
					.loadConfiguration(file);
			multiplierConfig.set("Environment.Normal", new Double(1.0));
			multiplierConfig.set("Environment.Nether", new Double(1.0));
			multiplierConfig.set("Environment.End", new Double(1.0));
			multiplierConfig.set("Time.Day", new Double(1.0));
			multiplierConfig.set("Time.Sunset", new Double(1.0));
			multiplierConfig.set("Time.Night", new Double(1.0));
			multiplierConfig.set("Time.Sunrise", new Double(1.0));
			multiplierConfig.set("Group.Default", new Double(1.0));
			multiplierConfig.set("World.world", new Double(1.0));
			multiplierConfig.set("fortuneToolsMultiplier", new Double(1.0));
			try
			{
				multiplierConfig.save(file);
			}
			catch (IOException e)
			{
			}

			_configurations.put(config, multiplierConfig);
			break;

		case REWARDS:
			YamlConfiguration rewardConfig = YamlConfiguration
					.loadConfiguration(file);
			rewardConfig.set("Default.Blaze", new Double(40.0));
			rewardConfig.set("Default.CaveSpider", new Double(28.5));
			rewardConfig.set("Default.Chicken", new Double(0.0));
			rewardConfig.set("Default.Cow", new Double(0.0));
			rewardConfig.set("Default.Creeper", new Double(57.0));
			rewardConfig.set("Default.ElectrifiedCreeper", new Double(69.0));
			rewardConfig.set("Default.Enderdragon", new Double(500.0));
			rewardConfig.set("Default.Enderman", new Double(60.0));
			rewardConfig.set("Default.Ghast", new Double(69.0));
			rewardConfig.set("Default.Giant", new Double(85.0));
			rewardConfig.set("Default.IronGolem", new Double(50.0));
			rewardConfig.set("Default.MagmaCube", new Double(0.0));
			rewardConfig.set("Default.Monster", new Double(85.0));
			rewardConfig.set("Default.Mooshroom", new Double(0.0));
			rewardConfig.set("Default.Ocelot", new Double(28.5));
			rewardConfig.set("Default.Pig", new Double(0.0));
			rewardConfig.set("Default.PigZombie", new Double(28.5));
			rewardConfig.set("Default.Player", new Double(50.0));
			rewardConfig.set("Default.SelfTamedCat", new Double(0.0));
			rewardConfig.set("Default.SelfTamedWolf", new Double(0.0));
			rewardConfig.set("Default.Sheep", new Double(0.0));
			rewardConfig.set("Default.Silverfish", new Double(2.0));
			rewardConfig.set("Default.Skeleton", new Double(33.0));
			rewardConfig.set("Default.Slime", new Double(0.0));
			rewardConfig.set("Default.SnowGolem", new Double(0.0));
			rewardConfig.set("Default.Spider", new Double(28.5));
			rewardConfig.set("Default.Squid", new Double(0.0));
			rewardConfig.set("Default.TamedCat", new Double(21.0));
			rewardConfig.set("Default.TamedWolf", new Double(21.0));
			rewardConfig.set("Default.Unknown", new Double(0.0));
			rewardConfig.set("Default.Villager", new Double(0.0));
			rewardConfig.set("Default.Wolf", new Double(28.5));
			rewardConfig.set("Default.Zombie", new Double(21.0));
			try
			{
				rewardConfig.save(file);
			}
			catch (IOException e)
			{
			}

			_configurations.put(config, rewardConfig);
			break;

		case KILLSTREAK:
			YamlConfiguration killConf = YamlConfiguration
					.loadConfiguration(file);
			killConf.set("broadcastKillstreak", true);
			killConf.set("killStreakMultiply", false);
			killConf.set("KillBonus.5", new Double(5.0));
			killConf.set("KillBonus.10", new Double(10.0));
			String[] allowedCreatures =
			{
					"Blaze", "CaveSpider", "Chicken", "Cow", "Creeper",
					"ElectrifiedCreeper", "Enderdragon", "Enderman", "Ghast",
					"Giant", "IronGolem", "MagmaCube", "Monster", "Mooshroom",
					"Ocelot", "Pig", "PigZombie", "Player", "SelfTamedCat",
					"SelfTamedWolf", "Sheep", "Silverfish", "Skeleton",
					"Slime", "SnowGolem", "Spider", "Squid", "TamedCat",
					"TamedWolf", "Unknown", "Villager", "Wolf", "Zombie"
			};
			killConf.set("allowedCreatures", Arrays.asList(allowedCreatures));
			try
			{
				killConf.save(file);
			}
			catch (IOException e)
			{
			}

			_configurations.put(config, killConf);
			break;

		case SPOUT:
			YamlConfiguration spoutConf = YamlConfiguration
					.loadConfiguration(file);
			spoutConf.set("useSpout", false);
			try
			{
				spoutConf.save(file);
			}
			catch (IOException e2)
			{
			}

			_configurations.put(config, spoutConf);
			break;

		case MOBARENA:
			YamlConfiguration mobarenaConf = YamlConfiguration
					.loadConfiguration(file);
			mobarenaConf.set("useMobArena", false);
			mobarenaConf.set("allowPaymentInsideArena", true);
			mobarenaConf.set("MobArenaMultiplierRate", new Double(0.05));
			try
			{
				mobarenaConf.save(file);
			}
			catch (IOException e2)
			{
			}

			_configurations.put(config, mobarenaConf);
			break;

		case TOWNY:
			YamlConfiguration townyConf = YamlConfiguration
					.loadConfiguration(file);
			townyConf.set("useTowny", false);
			townyConf.set("autoDepositInTownRate", new Double(0.0));
			townyConf.set("wilderness.allowPayments", true);
			townyConf.set("wilderness.multiplierRate", new Double(1.0));
			townyConf.set("ownTown.allowPayments", true);
			townyConf.set("ownTown.multiplierRate", new Double(1.0));
			townyConf.set("neutralTown.allowPayments", true);
			townyConf.set("neutralTown.multiplierRate", new Double(1.0));
			townyConf.set("enemyTown.allowPayments", true);
			townyConf.set("enemyTown.multiplierRate", new Double(1.0));
			townyConf.set("allyTown.allowPayments", true);
			townyConf.set("allyTown.multiplierRate", true);
			try
			{
				townyConf.save(file);
			}
			catch (IOException e2)
			{
			}

			_configurations.put(config, townyConf);
			break;

		case FACTIONS:
			YamlConfiguration factionsConf = YamlConfiguration
					.loadConfiguration(file);
			factionsConf.set("useFactions", false);
			factionsConf.set("autoDepositInFactionRate", new Double(0.0));
			factionsConf.set("ownLand.allowPayments", true);
			factionsConf.set("ownLand.multiplierRate", new Double(1.0));
			factionsConf.set("wilderness.allowPayments", true);
			factionsConf.set("wilderness.multiplierRate", new Double(1.0));
			factionsConf.set("warZone.allowPayments", true);
			factionsConf.set("warZone.multiplierRate", new Double(1.0));
			factionsConf.set("alliedLand.allowPayments", true);
			factionsConf.set("alliedLand.multiplierRate", new Double(1.0));
			factionsConf.set("safeZone.allowPayments", true);
			factionsConf.set("safeZone.multiplierRate", new Double(1.0));
			factionsConf.set("peacefulLand.allowPayments", true);
			factionsConf.set("peacefulLand.multiplierRate", new Double(1.0));
			factionsConf.set("enemyLand.allowPayments", true);
			factionsConf.set("enemyLand.multiplierRate", new Double(1.0));
			factionsConf.set("neutralLand.allowPayments", true);
			factionsConf.set("neutralLand.multiplierRate", new Double(1.0));
			try
			{
				factionsConf.save(file);
			}
			catch (IOException e2)
			{
			}

			_configurations.put(config, factionsConf);
			break;

		case WORLDGUARD:
			YamlConfiguration worldguardConf = YamlConfiguration
					.loadConfiguration(file);
			worldguardConf.set("useWorldGuard", false);
			worldguardConf.set("allowPaymentInsideRegions", true);
			worldguardConf.set("regionMultiplierRate", new Double(1.0));
			try
			{
				worldguardConf.save(file);
			}
			catch (IOException e2)
			{
			}

			_configurations.put(config, worldguardConf);
			break;

		case REGIOS:
			YamlConfiguration regiosConf = YamlConfiguration
					.loadConfiguration(file);
			regiosConf.set("useRegios", false);
			regiosConf.set("allowPaymentInsideRegions", true);
			regiosConf.set("regionMultiplierRate", new Double(1.0));
			try
			{
				regiosConf.save(file);
			}
			catch (IOException e2)
			{
			}

			_configurations.put(config, regiosConf);
			break;

		case MCMMO:
			YamlConfiguration mcmmoConf = YamlConfiguration
					.loadConfiguration(file);
			mcmmoConf.set("useMCMMO", false);
			mcmmoConf.set("splitAmongParty", true);
			mcmmoConf.set("killerBonusRate", new Double(1.0));
			try
			{
				mcmmoConf.save(file);
			}
			catch (IOException e2)
			{
			}

			_configurations.put(config, mcmmoConf);
			break;

		case HEROES:
			YamlConfiguration heroesConf = YamlConfiguration
					.loadConfiguration(file);
			heroesConf.set("useHeroes", false);
			heroesConf.set("splitAmongParty", true);
			heroesConf.set("killerBonusRate", new Double(1.0));
			try
			{
				heroesConf.save(file);
			}
			catch (IOException e2)
			{
			}

			_configurations.put(config, heroesConf);
			break;

		case LIKEABOSS:
			YamlConfiguration likeabossConf = YamlConfiguration
					.loadConfiguration(file);
			likeabossConf.set("useLikeaboss", false);
			likeabossConf.set("bossMultiplierRate", new Double(2.0));
			try
			{
				likeabossConf.save(file);
			}
			catch (IOException e2)
			{
			}

			_configurations.put(config, likeabossConf);
			break;

		case GRIEFPREVENTION:
			YamlConfiguration gpConf = YamlConfiguration
					.loadConfiguration(file);
			gpConf.set("useGriefPrevention", false);
			gpConf.set("allowPaymentInsideClaims", true);
			gpConf.set("ClaimMultiplierRate", new Double(1.0));
			try
			{
				gpConf.save(file);
			}
			catch (IOException e2)
			{
			}

			_configurations.put(config, gpConf);
			break;

		case DEITYPROTECT:
			YamlConfiguration dpConf = YamlConfiguration
					.loadConfiguration(file);
			dpConf.set("useDeityProtect", false);
			dpConf.set("allowPaymentInsideRegions", true);
			dpConf.set("RegionMultiplierRate", new Double(1.0));
			try
			{
				dpConf.save(file);
			}
			catch (IOException e2)
			{
			}

			_configurations.put(config, dpConf);
			break;

		case CHUNKOWN:
			YamlConfiguration coConf = YamlConfiguration
					.loadConfiguration(file);
			coConf.set("useChunkOwn", false);
			coConf.set("allowPaymentInsideChunks", true);
			coConf.set("ChunkMultiplierRate", new Double(1.0));
			try
			{
				coConf.save(file);
			}
			catch (IOException e2)
			{
			}

			_configurations.put(config, coConf);
			break;

		case PRECIOUSSTONES:
			YamlConfiguration psConf = YamlConfiguration
					.loadConfiguration(file);
			psConf.set("usePreciousStones", false);
			psConf.set("allowPaymentInsideRegions", true);
			psConf.set("RegionMultiplierRate", new Double(1.0));
			try
			{
				psConf.save(file);
			}
			catch (IOException e2)
			{
			}

			_configurations.put(config, psConf);
			break;

		case HEROSTRONGHOLD:
			YamlConfiguration hsConf = YamlConfiguration
					.loadConfiguration(file);
			hsConf.set("useHeroStronghold", false);
			hsConf.set("allowPaymentInsideRegions", true);
			hsConf.set("RegionMultiplierRate", new Double(1.0));
			try
			{
				hsConf.save(file);
			}
			catch (IOException e2)
			{
			}

			_configurations.put(config, hsConf);
			break;

		case MAZEMANIA:
			YamlConfiguration mmConf = YamlConfiguration
					.loadConfiguration(file);
			mmConf.set("useMazeMania", false);
			mmConf.set("allowPaymentInsideMaze", true);
			mmConf.set("MazeMultiplierRate", new Double(1.0));
			try
			{
				mmConf.save(file);
			}
			catch (IOException e2)
			{
			}

			_configurations.put(config, mmConf);
			break;

		case RESIDENCE:
			YamlConfiguration resConf = YamlConfiguration
					.loadConfiguration(file);
			resConf.set("useResidence", false);
			resConf.set("allowPaymentInsideResidence", true);
			resConf.set("ResidenceMultiplierRate", new Double(1.0));
			try
			{
				resConf.save(file);
			}
			catch (IOException e2)
			{
			}

			_configurations.put(config, resConf);
			break;

		case ROADPROTECTOR:
			YamlConfiguration rpConf = YamlConfiguration
					.loadConfiguration(file);
			rpConf.set("useRoadProtector", false);
			rpConf.set("allowPaymentInsideRegions", true);
			rpConf.set("RegionMultiplierRate", new Double(1.0));
			try
			{
				rpConf.save(file);
			}
			catch (IOException e2)
			{
			}

			_configurations.put(config, rpConf);
			break;

		case ULTIMATEARENA:
			YamlConfiguration uaConf = YamlConfiguration
					.loadConfiguration(file);
			uaConf.set("useUltimateArena", false);
			uaConf.set("allowPaymentInsideArenas", true);
			uaConf.set("ArenaMultiplierRate", new Double(1.0));
			try
			{
				uaConf.save(file);
			}
			catch (IOException e2)
			{
			}

			_configurations.put(config, uaConf);
			break;

		case SIMPLECLANS:
			YamlConfiguration scConf = YamlConfiguration
					.loadConfiguration(file);
			scConf.set("useSimpleClans", true);
			scConf.set("allyClans.allowPayment", true);
			scConf.set("allyClans.multiplierRate", new Double(1.0));
			scConf.set("rivalClans.allowPayment", true);
			scConf.set("rivalClans.multiplierRate", new Double(1.0));
			scConf.set("neutralClans.allowPayment", true);
			scConf.set("neutralClans.multiplierRate", new Double(1.0));
			try
			{
				scConf.save(file);
			}
			catch (IOException e2)
			{
			}

			_configurations.put(config, scConf);
			break;

		case EXPERIENCE:
			YamlConfiguration expConf = YamlConfiguration
					.loadConfiguration(file);
			expConf.set("Default.Blaze", new Integer(10));
			expConf.set("Default.CaveSpider", new Integer(5));
			expConf.set("Default.Chicken", new Integer(2));
			expConf.set("Default.Cow", new Integer(2));
			expConf.set("Default.Creeper", new Integer(5));
			expConf.set("Default.ElectrifiedCreeper", new Integer(5));
			expConf.set("Default.Enderdragon", new Integer(20000));
			expConf.set("Default.Enderman", new Integer(5));
			expConf.set("Default.Ghast", new Integer(5));
			expConf.set("Default.Giant", new Integer(0));
			expConf.set("Default.IronGolem", new Integer(0));
			expConf.set("Default.MagmaCube", new Integer(2));
			expConf.set("Default.Monster", new Integer(0));
			expConf.set("Default.Mooshroom", new Integer(2));
			expConf.set("Default.Ocelot", new Integer(2));
			expConf.set("Default.Pig", new Integer(2));
			expConf.set("Default.PigZombie", new Integer(5));
			expConf.set("Default.Player", new Integer(0));
			expConf.set("Default.SelfTamedCat", new Integer(0));
			expConf.set("Default.SelfTamedWolf", new Integer(0));
			expConf.set("Default.Sheep", new Integer(2));
			expConf.set("Default.Silverfish", new Integer(5));
			expConf.set("Default.Skeleton", new Integer(5));
			expConf.set("Default.Slime", new Integer(2));
			expConf.set("Default.SnowGolem", new Integer(0));
			expConf.set("Default.Spider", new Integer(5));
			expConf.set("Default.Squid", new Integer(2));
			expConf.set("Default.TamedCat", new Integer(0));
			expConf.set("Default.TamedWolf", new Integer(0));
			expConf.set("Default.Unknown", new Integer(0));
			expConf.set("Default.Villager", new Integer(0));
			expConf.set("Default.Wolf", new Integer(2));
			expConf.set("Default.Zombie", new Integer(5));
			try
			{
				expConf.save(file);
			}
			catch (IOException e)
			{
			}

			_configurations.put(config, expConf);
			break;

		case DROPS:
			YamlConfiguration dropConf = YamlConfiguration
					.loadConfiguration(file);
			String[] drop =
			{
				"i:0;d:0;a:0;p:0"
			};
			for (MobBountyCreature creature : MobBountyCreature.values())
			{
				if (creature.equals(MobBountyCreature.PLAYER))
					continue;
				dropConf.set(creature.getName() + ".Default.modifyDrops", false);
				dropConf.set(creature.getName() + ".Default.cancelNormalDrops",
						false);
				dropConf.set(creature.getName() + ".Default.drops",
						Arrays.asList(drop));
			}
			// dropConf.set("Default.Blaze",
			// Arrays.asList(defaultDrops.get("Blaze")));
			// dropConf.set("Default.CaveSpider",
			// Arrays.asList(defaultDrops.get("CaveSpider")));
			// dropConf.set("Default.Chicken",
			// Arrays.asList(defaultDrops.get("Chicken")));
			// dropConf.set("Default.Cow",
			// Arrays.asList(defaultDrops.get("Cow")));
			// dropConf.set("Default.Creeper",
			// Arrays.asList(defaultDrops.get("Creeper")));
			// dropConf.set("Default.ElectrifiedCreeper",
			// Arrays.asList(defaultDrops.get("ElectrifiedCreeper")));
			// dropConf.set("Default.Enderdragon",
			// Arrays.asList(defaultDrops.get("Enderdragon")));
			// dropConf.set("Default.Enderman",
			// Arrays.asList(defaultDrops.get("Enderman")));
			// dropConf.set("Default.Ghast",
			// Arrays.asList(defaultDrops.get("Ghast")));
			// dropConf.set("Default.Giant",
			// Arrays.asList(defaultDrops.get("Giant")));
			// dropConf.set("Default.IronGolem",
			// Arrays.asList(defaultDrops.get("IronGolem")));
			// dropConf.set("Default.MagmaCube",
			// Arrays.asList(defaultDrops.get("MagmaCube")));
			// dropConf.set("Default.Monster",
			// Arrays.asList(defaultDrops.get("Monster")));
			// dropConf.set("Default.Mooshroom",
			// Arrays.asList(defaultDrops.get("Mooshroom")));
			// dropConf.set("Default.Ocelot",
			// Arrays.asList(defaultDrops.get("Ocelot")));
			// dropConf.set("Default.Pig",
			// Arrays.asList(defaultDrops.get("Pig")));
			// dropConf.set("Default.PigZombie",
			// Arrays.asList(defaultDrops.get("PigZombie")));
			// dropConf.set("Default.SelfTamedCat",
			// Arrays.asList(defaultDrops.get("SelfTamedCat")));
			// dropConf.set("Default.SelfTamedWolf",
			// Arrays.asList(defaultDrops.get("SelfTamedWolf")));
			// dropConf.set("Default.Sheep",
			// Arrays.asList(defaultDrops.get("Sheep")));
			// dropConf.set("Default.Silverfish",
			// Arrays.asList(defaultDrops.get("Silverfish")));
			// dropConf.set("Default.Skeleton",
			// Arrays.asList(defaultDrops.get("Skeleton")));
			// dropConf.set("Default.Slime",
			// Arrays.asList(defaultDrops.get("Slime")));
			// dropConf.set("Default.SnowGolem",
			// Arrays.asList(defaultDrops.get("SnowGolem")));
			// dropConf.set("Default.Spider",
			// Arrays.asList(defaultDrops.get("Spider")));
			// dropConf.set("Default.Squid",
			// Arrays.asList(defaultDrops.get("Squid")));
			// dropConf.set("Default.TamedCat",
			// Arrays.asList(defaultDrops.get("TamedCat")));
			// dropConf.set("Default.TamedWolf",
			// Arrays.asList(defaultDrops.get("TamedWolf")));
			// dropConf.set("Default.Unknown",
			// Arrays.asList(defaultDrops.get("Unknown")));
			// dropConf.set("Default.Villager",
			// Arrays.asList(defaultDrops.get("Villager")));
			// dropConf.set("Default.Wolf",
			// Arrays.asList(defaultDrops.get("Wolf")));
			// dropConf.set("Default.Zombie",
			// Arrays.asList(defaultDrops.get("Zombie")));
			try
			{
				dropConf.save(file);
			}
			catch (IOException e)
			{
			}

			_configurations.put(config, dropConf);
			break;
		}

	}

	/**
	 * Gets drop information for creature
	 * 
	 * @param String
	 *            World to get it from
	 * @param MobBountyCreature
	 *            Creature type to get
	 * @param LivingEntity
	 *            Entity that drops are being gotten for
	 * @return
	 */
	public ArrayList<MobBountyItemInfo> getDrop(String worldName,
			MobBountyCreature creature, LivingEntity entity)
	{
		HashMap<MobBountyCreature, ArrayList<MobBountyItemInfo>> dropTable = _worldDropTable
				.get(worldName);

		if (dropTable == null)
		{
			if (propertyExists(MobBountyConfFile.DROPS, creature.getName()
					+ "." + worldName + ".drops"))
				dropTable = loadWorld(worldName, entity);
			else
				dropTable = loadWorld("Default", entity);
		}

		ArrayList<MobBountyItemInfo> drop = dropTable.get(creature);

		if (drop == null || drop.size() == 0)
			return null;
		return drop;
	}

	/**
	 * Gets a value for path in file
	 * 
	 * @param MobBountyConfFile
	 *            File to search in
	 * @param String
	 *            Path to search for
	 * @return String Value contained by path
	 */
	public String getProperty(MobBountyConfFile file, String path)
	{
		FileConfiguration conf = _configurations.get(file);

		if (conf != null)
		{
			String prop = conf.getString(path, "NULL");

			if (!prop.equalsIgnoreCase("NULL"))
				return prop;
			conf.set(path, null);
		}

		return null;
	}

	/**
	 * Gets a value for path in file
	 * 
	 * @param MobBountyConfFile
	 *            File to search in
	 * @param String
	 *            Path to search for
	 * @return List<String> Value contained by path
	 */
	public List<String> getPropertyList(MobBountyConfFile file, String path)
	{
		FileConfiguration conf = _configurations.get(file);

		if (conf != null)
		{
			List<String> prop = conf.getStringList(path);
			if (!prop.contains("NULL"))
				return prop;
			conf.set(path, null);
		}

		return null;
	}

	/**
	 * Loads the plugin's configuration files
	 */
	public void loadConfig()
	{
		for (MobBountyConfFile file : MobBountyConfFile.values())
		{
			File confFile = new File(file.getPath());

			if (confFile.exists())
			{
				if (_configurations.containsKey(file))
					_configurations.remove(file);

				YamlConfiguration conf = YamlConfiguration
						.loadConfiguration(confFile);
				_configurations.put(file, conf);
			}
			else
			{
				File parentFile = confFile.getParentFile();

				if (!parentFile.exists())
					parentFile.mkdirs();

				this.createConfig(file, confFile);
			}
		}

	}

	private HashMap<MobBountyCreature, ArrayList<MobBountyItemInfo>> loadWorld(
			String worldName, LivingEntity entity)
	{
		HashMap<MobBountyCreature, ArrayList<MobBountyItemInfo>> dropTable = new HashMap<MobBountyCreature, ArrayList<MobBountyItemInfo>>();

		for (MobBountyCreature creature : MobBountyCreature.values())
		{
			ArrayList<String> creatureDropData = (ArrayList<String>) getPropertyList(
					MobBountyConfFile.DROPS, creature.getName() + "."
							+ worldName + ".drops");
			ArrayList<MobBountyItemInfo> creatureDrop = new ArrayList<MobBountyItemInfo>();
			Iterator<String> creatureDropDataIterator = creatureDropData
					.iterator();

			while (creatureDropDataIterator.hasNext())
			{
				creatureDrop.add(new MobBountyItemInfo(creatureDropDataIterator
						.next(), entity));
			}

			dropTable.put(creature, creatureDrop);
		}

		_worldDropTable.put(worldName, dropTable);

		return dropTable;
	}

	/**
	 * Checks if path exists in file
	 * 
	 * @param MobBountyConfFile
	 *            File to search in
	 * @param String
	 *            Path to search for
	 * @return boolean Property exists
	 */
	public boolean propertyExists(MobBountyConfFile file, String path)
	{
		FileConfiguration conf = _configurations.get(file);

		if (conf != null)
		{
			if (conf.contains(path))
				return true;
		}

		return false;
	}

	/**
	 * Sets path to null in file
	 * 
	 * @param MobBountyConfFile
	 *            File to set in
	 * @param String
	 *            Path to set null
	 * @return boolean If completed
	 */
	public boolean removeProperty(MobBountyConfFile file, String path)
	{
		FileConfiguration conf = _configurations.get(file);

		if (conf != null)
		{
			conf.set(path, null);
			return true;
		}

		return false;
	}

	/**
	 * Saves the plugin's configs
	 */
	public void saveConfig()
	{
		for (MobBountyConfFile file : MobBountyConfFile.values())
		{
			if (_configurations.containsKey(file))
				try
				{
					_configurations.get(file).save(new File(file.getPath()));
				}
				catch (IOException e)
				{

				}
		}

		MobBountyReloaded._logger.info("[MobBountyReloaded] Config saved.");
	}

	/**
	 * Sets a creature's drop in world
	 * 
	 * @param String
	 *            Name of world to set in
	 * @param MobBountyCreature
	 *            Creature type to set
	 * @param String
	 *            [] Array of strings to set as drops
	 * @param LivingEntity
	 *            Entity of drop change
	 */
	public void setDrop(String worldName, MobBountyCreature creature,
			String[] items, LivingEntity entity)
	{
		HashMap<MobBountyCreature, ArrayList<MobBountyItemInfo>> dropTable = new HashMap<MobBountyCreature, ArrayList<MobBountyItemInfo>>();
		ArrayList<String> itemDropsData = new ArrayList<String>();
		ArrayList<MobBountyItemInfo> itemDrops = new ArrayList<MobBountyItemInfo>();

		for (int i = 0; i < items.length; i++)
		{
			itemDrops.add(new MobBountyItemInfo(items[i], entity));
			itemDropsData.add(items[i]);
		}

		dropTable.put(creature, itemDrops);

		setPropertyList(MobBountyConfFile.DROPS, creature.getName() + "."
				+ worldName + ".drops", itemDropsData);
		_worldDropTable.put(worldName, dropTable);
	}

	/**
	 * Sets path to value in file
	 * 
	 * @param MobBountyConfFile
	 *            File to set in
	 * @param String
	 *            Path to set
	 * @param boolean Value to set
	 * @return boolean If completed
	 */
	public boolean setProperty(MobBountyConfFile file, String path,
			boolean value)
	{
		FileConfiguration conf = _configurations.get(file);

		if (conf != null)
		{
			conf.set(path, value);
			try
			{
				conf.save(new File(file.getPath()));
			}
			catch (IOException e)
			{

			}
			return true;
		}

		return false;
	}

	/**
	 * Sets path to value in file
	 * 
	 * @param MobBountyConfFile
	 *            File to set in
	 * @param String
	 *            Path to set
	 * @param double Value to set
	 * @return boolean If completed
	 */
	public boolean setProperty(MobBountyConfFile file, String path, Double value)
	{
		FileConfiguration conf = _configurations.get(file);

		if (conf != null)
		{
			conf.set(path, value);
			try
			{
				conf.save(new File(file.getPath()));
			}
			catch (IOException e)
			{

			}
			return true;
		}

		return false;
	}

	/**
	 * Sets path to value in file
	 * 
	 * @param MobBountyConfFile
	 *            File to set in
	 * @param String
	 *            Path to set
	 * @param int Value to set
	 * @return boolean If completed
	 */
	public boolean setProperty(MobBountyConfFile file, String path, int value)
	{
		FileConfiguration conf = _configurations.get(file);

		if (conf != null)
		{
			conf.set(path, value);
			try
			{
				conf.save(new File(file.getPath()));
			}
			catch (IOException e)
			{
			}
			return true;
		}

		return false;
	}

	/**
	 * Sets path to value in file
	 * 
	 * @param MobBountyConfFile
	 *            File to set in
	 * @param String
	 *            Path to set
	 * @param String
	 *            Value to set
	 * @return boolean If completed
	 */
	public boolean setProperty(MobBountyConfFile file, String path, String value)
	{
		FileConfiguration conf = _configurations.get(file);

		if (conf != null)
		{
			conf.set(path, value);
			try
			{
				conf.save(new File(file.getPath()));
			}
			catch (IOException e)
			{

			}
			return true;
		}

		return false;
	}

	/**
	 * Sets path to list of values in file
	 * 
	 * @param MobBountyConfFile
	 *            File to set in
	 * @param String
	 *            Path to set
	 * @param ArrayList
	 *            <String> List of values
	 * @return boolean If completed
	 */
	public boolean setPropertyList(MobBountyConfFile file, String path,
			ArrayList<String> list)
	{
		FileConfiguration conf = _configurations.get(file);

		if (conf != null)
		{
			conf.set(path, list);
			try
			{
				conf.save(new File(file.getPath()));
			}
			catch (IOException e)
			{
			}
			return true;
		}

		return false;
	}

	/**
	 * Sets path to list of values in file
	 * 
	 * @param MobBountyConfFile
	 *            File to set in
	 * @param String
	 *            Path to set
	 * @param List
	 *            <String> List of values to set
	 * @return boolean If completed
	 */
	public boolean setPropertyList(MobBountyConfFile file, String path,
			List<String> list)
	{
		FileConfiguration conf = _configurations.get(file);

		if (conf != null)
		{
			conf.set(path, list);
			try
			{
				conf.save(new File(file.getPath()));
			}
			catch (IOException e)
			{
			}
			return true;
		}

		return false;
	}
}
