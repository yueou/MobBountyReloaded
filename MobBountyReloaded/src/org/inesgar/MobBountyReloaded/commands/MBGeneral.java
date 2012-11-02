package org.inesgar.MobBountyReloaded.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.inesgar.MobBountyReloaded.MobBountyReloaded;
import org.inesgar.MobBountyReloaded.utils.configuration.MobBountyReloadedConfFile;

public class MBGeneral implements CommandExecutor
{
	private MobBountyReloaded _plugin;

	public MBGeneral(MobBountyReloaded plugin)
	{
		setPlugin(plugin);
	}

	private void commandUsage(CommandSender sender, String command)
	{
		String message = getPlugin().getLocaleManager().getString("MBGUsage");
		if (message != null)
		{
			message = message.replace("%C", command);
			sender.sendMessage(message);
		}

		message = getPlugin().getLocaleManager().getString("MBGProperty");
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
		if (getPlugin().getPermissionManager().hasPermission(player,
				"mbr.admin.command.mbg"))
		{
			if (args.length == 2)
			{
				if (args[0].equalsIgnoreCase("locale"))
				{
					getPlugin().getConfigManager().setProperty(
							MobBountyReloadedConfFile.GENERAL, "locale",
							args[1].toLowerCase());

					String message = getPlugin().getLocaleManager().getString(
							"MBGChange");
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
					if (args[0].equalsIgnoreCase("debug"))
					{
						getPlugin().getConfigManager().setProperty(
								MobBountyReloadedConfFile.GENERAL, "debugMode",
								true);

						String message = getPlugin().getLocaleManager()
								.getString("MBGChange");
						if (message != null)
						{
							message = message.replace("%S", "debugMode")
									.replace("%V", "true");
							sender.sendMessage(message);
						}
					}
					else if (args[0].equalsIgnoreCase("usekillcache"))
					{
						getPlugin().getConfigManager().setProperty(
								MobBountyReloadedConfFile.GENERAL,
								"killCache.use", true);

						String message = getPlugin().getLocaleManager()
								.getString("MBGChange");
						if (message != null)
						{
							message = message.replace("%S", "usekillcache")
									.replace("%V", "true");
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
					if (args[0].equalsIgnoreCase("debug"))
					{
						getPlugin().getConfigManager().setProperty(
								MobBountyReloadedConfFile.GENERAL, "debugMode",
								false);

						String message = getPlugin().getLocaleManager()
								.getString("MBGChange");
						if (message != null)
						{
							message = message.replace("%S", "debugMode")
									.replace("%V", "false");
							sender.sendMessage(message);
						}
					}
					else if (args[0].equalsIgnoreCase("usekillcache"))
					{
						getPlugin().getConfigManager().setProperty(
								MobBountyReloadedConfFile.GENERAL,
								"killCache.use", false);

						String message = getPlugin().getLocaleManager()
								.getString("MBGChange");
						if (message != null)
						{
							message = message.replace("%S", "usekillcache")
									.replace("%V", "false");
							sender.sendMessage(message);
						}
					}
					else
						this.commandUsage(sender, label);
				}
				else if (args[1].matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+"))
				{
					if (args[0].equalsIgnoreCase("killcachetimelimit"))
					{
						getPlugin().getConfigManager().setProperty(
								MobBountyReloadedConfFile.GENERAL,
								"killCache.timeLimit", args[1]);

						String message = getPlugin().getLocaleManager()
								.getString("MBGChange");
						if (message != null)
						{
							message = message.replace("%S",
									"killcachetimelimit")
									.replace("%V", args[1]);
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
			String message = getPlugin().getLocaleManager().getString(
					"NoAccess");
			if (message != null)
				sender.sendMessage(message);
		}

		return true;
	}

	public MobBountyReloaded getPlugin()
	{
		return _plugin;
	}

	public void setPlugin(MobBountyReloaded plugin)
	{
		_plugin = plugin;
	}
}
