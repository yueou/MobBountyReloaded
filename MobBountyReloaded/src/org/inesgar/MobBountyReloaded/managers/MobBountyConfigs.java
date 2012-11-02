package org.inesgar.MobBountyReloaded.managers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.inesgar.MobBountyReloaded.MobBountyReloaded;
import org.inesgar.MobBountyReloaded.utils.configuration.CommentedYamlConfiguration;
import org.inesgar.MobBountyReloaded.utils.configuration.MobBountyReloadedConfFile;

public class MobBountyConfigs
{
	private final HashMap<MobBountyReloadedConfFile, YamlConfiguration> _configurations;

	private final MobBountyReloaded _plugin;

	public MobBountyConfigs(MobBountyReloaded plugin)
	{
		_plugin = plugin;

		_configurations = new HashMap<MobBountyReloadedConfFile, YamlConfiguration>();

		this.loadConfig();
	}

	private void createConfig(MobBountyReloadedConfFile config, File file)
	{
		switch (config)
		{
		case GENERAL:
			CommentedYamlConfiguration generalConf = new CommentedYamlConfiguration();
			getPlugin().getAPI().log("Creating General.yml");
			generalConf.addComment("locale",
					"The locale version to use in locale.yml");
			generalConf.set("locale", "en");
			generalConf.addComment("killCache.use",
					"Whether or not to use killCache");
			generalConf.set("killCache.use", false);
			generalConf.addComment("killCache.timeLimit",
					"Milliseconds between earning updates");
			generalConf.set("killCache.timeLimit", new Integer(30000));
			generalConf.addComment("debugMode",
					"Whether or not the plugin is in debugMode");
			generalConf.set("debugMode", false);
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
			CommentedYamlConfiguration localeConf = new CommentedYamlConfiguration();
			getPlugin().getAPI().log("Creating Locale.yml");
			localeConf.addComment("en",
					"You may use various symbols in your strings here",
					"and the plugin will automatically convert them.",
					"  %P - the player who killed",
					"  %M - the type of mob killed", "  %A - the reward/fine",
					"  %W - the world it occured in",
					"  %1 - the first number in a reward range",
					"  %2 - the second number in a reward range",
					"  %C - command", "  %H - help for command",
					"  %D - permission", "  %E - environment",
					"  %K - kill cache amount", "%T - kill cache time");
			localeConf.set("en.Awarded",
					"&2You have been awarded &F%A &2for killing a &F%M&2.");
			localeConf
					.set("en.CacheAwarded",
							"&2You have been awarded &F%A &2for killing a &F%K &2mobs in &F%T &2seconds.");
			localeConf.set("en.Fined",
					"&4You have been fined &F%A &4for killing a &F%M&4.");
			localeConf
					.set("en.CacheFined",
							"&4You have been fined &F%A &4for killing a &F%K &4mobs in &F%T &4seconds.");
			localeConf.set("en.NoAccess",
					"&CYou do not have access to that command.");
			localeConf.set("en.BroadcastStreak",
					"&F%P &2has a kill streak of &F%A&2!");
			localeConf.set("en.MBReward", "&2%M : &F%A");
			localeConf.set("en.MBRewardRange", "&2%M : &F%1 - %2");
			localeConf.set("en.MBFine", "&4%M : &F%A");
			localeConf.set("en.MBFineRange", "&4%M : &F%A - %2");
			localeConf.set("en.MBInfo", "&d%C &F- &2%H");
			localeConf.set("en.MBGChange",
					"&2General setting &F%S &2has been changed to &F%V&2.");
			localeConf.set("en.MBGUsage", "&CUsage: /%C [property] <amount>");
			localeConf
					.set("en.MBGProperty",
							"&7Property: locale, debug, mobspawnprot, mobspawnprotrad, mobspawnprotrate, spawnmobprotection, deprreturn, deprreturnrate, timeafterlogin, usemobcap, mobcaplimit, mobcapdist");
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
			localeConf.set("en.MBUMChange",
					"&2Multiplier for user &F%P &2has been changed to &F%A&2.");
			localeConf.set("en.MBUMUsage", "&CUsage: /%C <group> <amount>");
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
					"&2A new version of MobBountyReloadedCore is available.");
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
			CommentedYamlConfiguration multiplierConfig = new CommentedYamlConfiguration();
			getPlugin().getAPI().log("Creating Multipliers.yml");
			multiplierConfig.set("Environment.Normal", new Double(1.0));
			multiplierConfig.set("Environment.Nether", new Double(1.0));
			multiplierConfig.set("Environment.End", new Double(1.0));
			multiplierConfig.set("Time.Day", new Double(1.0));
			multiplierConfig.set("Time.Sunset", new Double(1.0));
			multiplierConfig.set("Time.Night", new Double(1.0));
			multiplierConfig.set("Time.Sunrise", new Double(1.0));
			multiplierConfig.set("Group.Default", new Double(1.0));
			multiplierConfig.set("User.Default", new Double(1.0));
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
			CommentedYamlConfiguration rewardConfig = new CommentedYamlConfiguration();
			getPlugin().getAPI().log("Creating Rewards.yml");
			rewardConfig
					.addComment(
							"Default",
							"You can do some cool things with the MBR reward system.",
							"If you add a colon (:) to your reward, it will produce a range.",
							"Having '28.5:30.0' will produce a reward between those two numbers.",
							"If you'd like to make a creature not give a reward, set its value to '0.0'.",
							"",
							"To give a value to a world, give it a format like so:",
							"worldnamehere:", "  creaturenamehere: valuehere");
			rewardConfig.set("Default.Bat", new Double(0.0));
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
			rewardConfig.set("Default.Witch", new Double(0.0));
			rewardConfig.set("Default.Wither", new Double(750.0));
			rewardConfig.set("Default.WitherSkeleton", new Double(57.0));
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
			CommentedYamlConfiguration killConf = new CommentedYamlConfiguration();
			getPlugin().getAPI().log("Creating Killstreak.yml");
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
		}

	}

	/**
	 * Gets a value for path in file
	 * 
	 * @param MobBountyReloadedConfFile
	 *            File to search in
	 * @param String
	 *            Path to search for
	 * @return String Value contained by path
	 */
	public String getProperty(MobBountyReloadedConfFile file, String path)
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
	 * @param MobBountyReloadedConfFile
	 *            File to search in
	 * @param String
	 *            Path to search for
	 * @return List<String> Value contained by path
	 */
	public List<String> getPropertyList(MobBountyReloadedConfFile file,
			String path)
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
		for (MobBountyReloadedConfFile file : MobBountyReloadedConfFile
				.values())
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

	/**
	 * Checks if path exists in file
	 * 
	 * @param MobBountyReloadedConfFile
	 *            File to search in
	 * @param String
	 *            Path to search for
	 * @return boolean Property exists
	 */
	public boolean propertyExists(MobBountyReloadedConfFile file, String path)
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
	 * @param MobBountyReloadedConfFile
	 *            File to set in
	 * @param String
	 *            Path to set null
	 * @return boolean If completed
	 */
	public boolean removeProperty(MobBountyReloadedConfFile file, String path)
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
		for (MobBountyReloadedConfFile file : MobBountyReloadedConfFile
				.values())
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
	}

	/**
	 * Sets path to value in file
	 * 
	 * @param MobBountyReloadedConfFile
	 *            File to set in
	 * @param String
	 *            Path to set
	 * @param boolean Value to set
	 * @return boolean If completed
	 */
	public boolean setProperty(MobBountyReloadedConfFile file, String path,
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
	 * @param MobBountyReloadedConfFile
	 *            File to set in
	 * @param String
	 *            Path to set
	 * @param double Value to set
	 * @return boolean If completed
	 */
	public boolean setProperty(MobBountyReloadedConfFile file, String path,
			Double value)
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
	 * @param MobBountyReloadedConfFile
	 *            File to set in
	 * @param String
	 *            Path to set
	 * @param int Value to set
	 * @return boolean If completed
	 */
	public boolean setProperty(MobBountyReloadedConfFile file, String path,
			int value)
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
	 * @param MobBountyReloadedConfFile
	 *            File to set in
	 * @param String
	 *            Path to set
	 * @param String
	 *            Value to set
	 * @return boolean If completed
	 */
	public boolean setProperty(MobBountyReloadedConfFile file, String path,
			String value)
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
	 * @param MobBountyReloadedConfFile
	 *            File to set in
	 * @param String
	 *            Path to set
	 * @param ArrayList
	 *            <String> List of values
	 * @return boolean If completed
	 */
	public boolean setPropertyList(MobBountyReloadedConfFile file, String path,
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
	 * @param MobBountyReloadedConfFile
	 *            File to set in
	 * @param String
	 *            Path to set
	 * @param List
	 *            <String> List of values to set
	 * @return boolean If completed
	 */
	public boolean setPropertyList(MobBountyReloadedConfFile file, String path,
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

	public MobBountyReloaded getPlugin()
	{
		return _plugin;
	}
}
