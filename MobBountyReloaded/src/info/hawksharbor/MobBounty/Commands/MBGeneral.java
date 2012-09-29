package info.hawksharbor.MobBounty.Commands;

import info.hawksharbor.MobBounty.Utils.MobBountyConfFile;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MBGeneral implements CommandExecutor
{
	private final info.hawksharbor.MobBounty.MobBountyReloaded _plugin;

	public MBGeneral(info.hawksharbor.MobBounty.MobBountyReloaded plugin)
	{
		_plugin = plugin;
	}

	private void commandUsage(CommandSender sender, String command)
	{
		String message = _plugin.getAPIManager().getLocaleManager()
				.getString("MBGUsage");
		if (message != null)
		{
			message = message.replace("%C", command);
			sender.sendMessage(message);
		}

		message = _plugin.getAPIManager().getLocaleManager()
				.getString("MBGProperty");
		if (message != null)
			sender.sendMessage(message);
	}

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args)
	{
		if (!(sender instanceof Player))
		{
			sender.sendMessage("Commands are designed to be run by players only.");
			return true;
		}
		Player player = ((Player) sender);
		if (_plugin.getAPIManager().getPermissionsManager()
				.hasPermission(player, "mbr.command.mbg"))
		{
			if (args.length == 2)
			{
				if (args[0].equalsIgnoreCase("locale"))
				{
					_plugin.getAPIManager()
							.getConfigManager()
							.setProperty(MobBountyConfFile.GENERAL, "locale",
									args[1].toLowerCase());

					String message = _plugin.getAPIManager().getLocaleManager()
							.getString("MBGChange");
					if (message != null)
					{
						message = message.replace("%S", "locale").replace("%V",
								args[1].toLowerCase());
						sender.sendMessage(message);
					}
				}
				else if (args[1].equalsIgnoreCase("true")
						|| args[1].equalsIgnoreCase("yes")
						|| args[1].equalsIgnoreCase("1"))
				{
					if (args[0].equalsIgnoreCase("envmulti"))
					{
						_plugin.getAPIManager()
								.getConfigManager()
								.setProperty(MobBountyConfFile.GENERAL,
										"useEnvironmentMultiplier", "true");

						String message = _plugin.getAPIManager()
								.getLocaleManager().getString("MBGChange");
						if (message != null)
						{
							message = message.replace("%S",
									"useEnvironmentMultiplier").replace("%V",
									"true");
							sender.sendMessage(message);
						}
					}
					else if (args[0].equalsIgnoreCase("debug"))
					{
						_plugin.getAPIManager()
								.getConfigManager()
								.setProperty(MobBountyConfFile.GENERAL,
										"debugMode", true);

						String message = _plugin.getAPIManager()
								.getLocaleManager().getString("MBGChange");
						if (message != null)
						{
							message = message.replace("%S", "debugMode")
									.replace("%V", "true");
							sender.sendMessage(message);
						}
					}
					else if (args[0].equalsIgnoreCase("timemulti"))
					{
						_plugin.getAPIManager()
								.getConfigManager()
								.setProperty(MobBountyConfFile.GENERAL,
										"useTimeMultiplier", "true");

						String message = _plugin.getAPIManager()
								.getLocaleManager().getString("MBGChange");
						if (message != null)
						{
							message = message
									.replace("%S", "useTimeMultiplier")
									.replace("%V", "true");
							sender.sendMessage(message);
						}
					}
					else if (args[0].equalsIgnoreCase("worldmulti"))
					{
						_plugin.getAPIManager()
								.getConfigManager()
								.setProperty(MobBountyConfFile.GENERAL,
										"useWorldMultiplier", "true");

						String message = _plugin.getAPIManager()
								.getLocaleManager().getString("MBGChange");
						if (message != null)
						{
							message = message.replace("%S",
									"useWorldMultiplier").replace("%V", "true");
							sender.sendMessage(message);
						}
					}
					else if (args[0].equalsIgnoreCase("fortmulti"))
					{
						_plugin.getAPIManager()
								.getConfigManager()
								.setProperty(MobBountyConfFile.GENERAL,
										"useFortuneToolMultiplier", "true");

						String message = _plugin.getAPIManager()
								.getLocaleManager().getString("MBGChange");
						if (message != null)
						{
							message = message.replace("%S",
									"useFortuneToolMultiplier").replace("%V",
									"true");
							sender.sendMessage(message);
						}
					}
					else if (args[0].equalsIgnoreCase("deprreturn"))
					{
						_plugin.getAPIManager()
								.getConfigManager()
								.setProperty(MobBountyConfFile.GENERAL,
										"useDepreciativeReturn", "true");

						String message = _plugin.getAPIManager()
								.getLocaleManager().getString("MBGChange");
						if (message != null)
						{
							message = message.replace("%S",
									"useDepreciativeReturn").replace("%V",
									"true");
							sender.sendMessage(message);
						}
					}
					else if (args[0].equalsIgnoreCase("usemobcap"))
					{
						_plugin.getAPIManager()
								.getConfigManager()
								.setProperty(MobBountyConfFile.GENERAL,
										"mobCap.use", "true");

						String message = _plugin.getAPIManager()
								.getLocaleManager().getString("MBGChange");
						if (message != null)
						{
							message = message.replace("%S", "mopCap.use")
									.replace("%V", "true");
							sender.sendMessage(message);
						}
					}
					else if (args[0].equalsIgnoreCase("mobspawnprot"))
					{
						_plugin.getAPIManager()
								.getConfigManager()
								.setProperty(MobBountyConfFile.GENERAL,
										"useMobSpawnerProtection", "true");

						String message = _plugin.getAPIManager()
								.getLocaleManager().getString("MBGChange");
						if (message != null)
						{
							message = message.replace("%S",
									"useMobSpawnerProtection").replace("%V",
									"true");
							sender.sendMessage(message);
						}
					}
					else if (args[0].equalsIgnoreCase("spawnmobprotection"))
					{
						_plugin.getAPIManager()
								.getConfigManager()
								.setProperty(MobBountyConfFile.GENERAL,
										"spawnedMobProtection", "true");

						String message = _plugin.getAPIManager()
								.getLocaleManager().getString("MBGChange");
						if (message != null)
						{
							message = message.replace("%S",
									"spawnedMobProtection").replace("%V",
									"true");
							sender.sendMessage(message);
						}
					}
					else if (args[0].equalsIgnoreCase("spawnmobdrops"))
					{
						_plugin.getAPIManager()
								.getConfigManager()
								.setProperty(MobBountyConfFile.GENERAL,
										"spawnedMobDrops", "true");

						String message = _plugin.getAPIManager()
								.getLocaleManager().getString("MBGChange");
						if (message != null)
						{
							message = message.replace("%S", "spawnedMobDrops")
									.replace("%V", "true");
							sender.sendMessage(message);
						}
					}
					else if (args[0].equalsIgnoreCase("spawnmobexp"))
					{
						_plugin.getAPIManager()
								.getConfigManager()
								.setProperty(MobBountyConfFile.GENERAL,
										"spawnedMobExperience", "true");

						String message = _plugin.getAPIManager()
								.getLocaleManager().getString("MBGChange");
						if (message != null)
						{
							message = message.replace("%S",
									"spawnedMobExperience").replace("%V",
									"true");
							sender.sendMessage(message);
						}
					}
					else if (args[0].equalsIgnoreCase("moddrops"))
					{
						_plugin.getAPIManager()
								.getConfigManager()
								.setProperty(MobBountyConfFile.GENERAL,
										"modifyItemDrops", "true");

						String message = _plugin.getAPIManager()
								.getLocaleManager().getString("MBGChange");
						if (message != null)
						{
							message = message.replace("%S", "modifyItemDrops")
									.replace("%V", "true");
							sender.sendMessage(message);
						}
					}
					else if (args[0].equalsIgnoreCase("modexp"))
					{
						_plugin.getAPIManager()
								.getConfigManager()
								.setProperty(MobBountyConfFile.GENERAL,
										"modifyExperienceDrops", "true");

						String message = _plugin.getAPIManager()
								.getLocaleManager().getString("MBGChange");
						if (message != null)
						{
							message = message.replace("%S",
									"modifyExperienceDrops").replace("%V",
									"true");
							sender.sendMessage(message);
						}
					}
					else
						this.commandUsage(sender, label);
				}
				else if (args[1].equalsIgnoreCase("false")
						|| args[1].equalsIgnoreCase("no")
						|| args[1].equalsIgnoreCase("0"))
				{
					if (args[0].equalsIgnoreCase("envmulti"))
					{
						_plugin.getAPIManager()
								.getConfigManager()
								.setProperty(MobBountyConfFile.GENERAL,
										"useEnvironmentMultiplier", "false");

						String message = _plugin.getAPIManager()
								.getLocaleManager().getString("MBGChange");
						if (message != null)
						{
							message = message.replace("%S",
									"useEnvironmentMultiplier").replace("%V",
									"false");
							sender.sendMessage(message);
						}
					}
					else if (args[0].equalsIgnoreCase("debug"))
					{
						_plugin.getAPIManager()
								.getConfigManager()
								.setProperty(MobBountyConfFile.GENERAL,
										"debugMode", false);

						String message = _plugin.getAPIManager()
								.getLocaleManager().getString("MBGChange");
						if (message != null)
						{
							message = message.replace("%S", "debugMode")
									.replace("%V", "false");
							sender.sendMessage(message);
						}
					}
					else if (args[0].equalsIgnoreCase("timemulti"))
					{
						_plugin.getAPIManager()
								.getConfigManager()
								.setProperty(MobBountyConfFile.GENERAL,
										"useTimeMultiplier", "false");

						String message = _plugin.getAPIManager()
								.getLocaleManager().getString("MBGChange");
						if (message != null)
						{
							message = message
									.replace("%S", "useTimeMultiplier")
									.replace("%V", "false");
							sender.sendMessage(message);
						}
					}
					else if (args[0].equalsIgnoreCase("worldmulti"))
					{
						_plugin.getAPIManager()
								.getConfigManager()
								.setProperty(MobBountyConfFile.GENERAL,
										"useWorldMultiplier", "false");

						String message = _plugin.getAPIManager()
								.getLocaleManager().getString("MBGChange");
						if (message != null)
						{
							message = message.replace("%S",
									"useWorldMultiplier")
									.replace("%V", "false");
							sender.sendMessage(message);
						}
					}
					else if (args[0].equalsIgnoreCase("fortmulti"))
					{
						_plugin.getAPIManager()
								.getConfigManager()
								.setProperty(MobBountyConfFile.GENERAL,
										"useFortuneToolMultiplier", "false");

						String message = _plugin.getAPIManager()
								.getLocaleManager().getString("MBGChange");
						if (message != null)
						{
							message = message.replace("%S",
									"useFortuneToolMultiplier").replace("%V",
									"false");
							sender.sendMessage(message);
						}
					}
					else if (args[0].equalsIgnoreCase("deprreturn"))
					{
						_plugin.getAPIManager()
								.getConfigManager()
								.setProperty(MobBountyConfFile.GENERAL,
										"useDepreciativeReturn", "false");

						String message = _plugin.getAPIManager()
								.getLocaleManager().getString("MBGChange");
						if (message != null)
						{
							message = message.replace("%S",
									"useDepreciativeReturn").replace("%V",
									"false");
							sender.sendMessage(message);
						}
					}
					else if (args[0].equalsIgnoreCase("usemobcap"))
					{
						_plugin.getAPIManager()
								.getConfigManager()
								.setProperty(MobBountyConfFile.GENERAL,
										"mobCap.use", "false");

						String message = _plugin.getAPIManager()
								.getLocaleManager().getString("MBGChange");
						if (message != null)
						{
							message = message.replace("%S", "mopCap.use")
									.replace("%V", "false");
							sender.sendMessage(message);
						}
					}
					else if (args[0].equalsIgnoreCase("mobspawnprot"))
					{
						_plugin.getAPIManager()
								.getConfigManager()
								.setProperty(MobBountyConfFile.GENERAL,
										"useMobSpawnerProtection", "false");

						String message = _plugin.getAPIManager()
								.getLocaleManager().getString("MBGChange");
						if (message != null)
						{
							message = message.replace("%S",
									"useMobSpawnerProtection").replace("%V",
									"false");
							sender.sendMessage(message);
						}
					}
					else if (args[0].equalsIgnoreCase("spawnmobprotection"))
					{
						_plugin.getAPIManager()
								.getConfigManager()
								.setProperty(MobBountyConfFile.GENERAL,
										"spawnedMobProtection", "false");

						String message = _plugin.getAPIManager()
								.getLocaleManager().getString("MBGChange");
						if (message != null)
						{
							message = message.replace("%S",
									"spawnedMobProtection").replace("%V",
									"false");
							sender.sendMessage(message);
						}
					}
					else if (args[0].equalsIgnoreCase("spawnmobdrops"))
					{
						_plugin.getAPIManager()
								.getConfigManager()
								.setProperty(MobBountyConfFile.GENERAL,
										"spawnedMobDrops", "false");

						String message = _plugin.getAPIManager()
								.getLocaleManager().getString("MBGChange");
						if (message != null)
						{
							message = message.replace("%S", "spawnedMobDrops")
									.replace("%V", "false");
							sender.sendMessage(message);
						}
					}
					else if (args[0].equalsIgnoreCase("spawnmobexp"))
					{
						_plugin.getAPIManager()
								.getConfigManager()
								.setProperty(MobBountyConfFile.GENERAL,
										"spawnedMobExperience", "false");

						String message = _plugin.getAPIManager()
								.getLocaleManager().getString("MBGChange");
						if (message != null)
						{
							message = message.replace("%S",
									"spawnedMobExperience").replace("%V",
									"false");
							sender.sendMessage(message);
						}
					}
					else if (args[0].equalsIgnoreCase("moddrops"))
					{
						_plugin.getAPIManager()
								.getConfigManager()
								.setProperty(MobBountyConfFile.GENERAL,
										"modifyItemDrops", "false");

						String message = _plugin.getAPIManager()
								.getLocaleManager().getString("MBGChange");
						if (message != null)
						{
							message = message.replace("%S", "modifyItemDrops")
									.replace("%V", "false");
							sender.sendMessage(message);
						}
					}
					else if (args[0].equalsIgnoreCase("modexp"))
					{
						_plugin.getAPIManager()
								.getConfigManager()
								.setProperty(MobBountyConfFile.GENERAL,
										"modifyExperienceDrops", "false");

						String message = _plugin.getAPIManager()
								.getLocaleManager().getString("MBGChange");
						if (message != null)
						{
							message = message.replace("%S",
									"modifyExperienceDrops").replace("%V",
									"false");
							sender.sendMessage(message);
						}
					}
					else
						this.commandUsage(sender, label);
				}
				else if (args[1].matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+"))
				{
					if (args[0].equalsIgnoreCase("deprreturnrate"))
					{
						_plugin.getAPIManager()
								.getConfigManager()
								.setProperty(MobBountyConfFile.GENERAL,
										"depreciativeReturnRate", args[1]);

						String message = _plugin.getAPIManager()
								.getLocaleManager().getString("MBGChange");
						if (message != null)
						{
							message = message.replace("%S",
									"depreciativeReturnRate").replace("%V",
									args[1]);
							sender.sendMessage(message);
						}
					}
					else if (args[0].equalsIgnoreCase("timeafterlogin"))
					{
						_plugin.getAPIManager()
								.getConfigManager()
								.setProperty(MobBountyConfFile.GENERAL,
										"timeAfterLogin", args[1]);

						String message = _plugin.getAPIManager()
								.getLocaleManager().getString("MBGChange");
						if (message != null)
						{
							message = message.replace("%S", "timeAfterLogin")
									.replace("%V", args[1]);
							sender.sendMessage(message);
						}
					}
					else if (args[0].equalsIgnoreCase("mobcaplimit"))
					{
						_plugin.getAPIManager()
								.getConfigManager()
								.setProperty(MobBountyConfFile.GENERAL,
										"mobCap.limit", args[1]);

						String message = _plugin.getAPIManager()
								.getLocaleManager().getString("MBGChange");
						if (message != null)
						{
							message = message.replace("%S", "mobCap.limit")
									.replace("%V", args[1]);
							sender.sendMessage(message);
						}
					}
					else if (args[0].equalsIgnoreCase("mobcapdistance"))
					{
						_plugin.getAPIManager()
								.getConfigManager()
								.setProperty(MobBountyConfFile.GENERAL,
										"mobCap.distance", args[1]);

						String message = _plugin.getAPIManager()
								.getLocaleManager().getString("MBGChange");
						if (message != null)
						{
							message = message.replace("%S", "mobCap.distance")
									.replace("%V", args[1]);
							sender.sendMessage(message);
						}
					}
					else if (args[0].equalsIgnoreCase("mobspawnprotrad"))
					{
						_plugin.getAPIManager()
								.getConfigManager()
								.setProperty(MobBountyConfFile.GENERAL,
										"mobSpawnerProtectionRadius", args[1]);

						String message = _plugin.getAPIManager()
								.getLocaleManager().getString("MBGChange");
						if (message != null)
						{
							message = message.replace("%S",
									"mobSpawnerProtectionRadius").replace("%V",
									args[1]);
							sender.sendMessage(message);
						}
					}
					else if (args[0].equalsIgnoreCase("mobspawnprotrate"))
					{
						_plugin.getAPIManager()
								.getConfigManager()
								.setProperty(MobBountyConfFile.GENERAL,
										"mobSpawnerProtectionRate", args[1]);

						String message = _plugin.getAPIManager()
								.getLocaleManager().getString("MBGChange");
						if (message != null)
						{
							message = message.replace("%S",
									"mobSpawnerProtectionRate").replace("%V",
									args[1]);
							sender.sendMessage(message);
						}
					}
					else
						this.commandUsage(sender, label);
				}
				else
					this.commandUsage(sender, label);
			}
			else
				this.commandUsage(sender, label);

		}
		else
		{
			String message = _plugin.getAPIManager().getLocaleManager()
					.getString("NoAccess");
			if (message != null)
				sender.sendMessage(message);
		}

		return true;
	}
}
