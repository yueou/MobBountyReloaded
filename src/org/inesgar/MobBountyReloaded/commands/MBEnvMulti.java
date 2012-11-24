package org.inesgar.MobBountyReloaded.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.inesgar.MobBountyReloaded.MobBountyReloaded;
import org.inesgar.MobBountyReloaded.utils.configuration.MobBountyReloadedConfFile;

public class MBEnvMulti implements CommandExecutor
{
	private MobBountyReloaded _plugin;

	public MBEnvMulti(MobBountyReloaded plugin)
	{
		setPlugin(plugin);
	}

	private void commandUsage(CommandSender sender, String command)
	{
		String message = getPlugin().getLocaleManager().getString("MBEMUsage");
		if (message != null)
		{
			message = message.replace("%C", command);
			sender.sendMessage(message);
		}

		message = getPlugin().getLocaleManager().getString("MBEMEnvs");
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
				"mbr.command.mbem"))
		{
			if (args.length == 2)
			{
				if (args[1].matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+"))
				{
					Double amount;
					try
					{
						amount = Double.parseDouble(args[1]);
					}
					catch (NumberFormatException e)
					{
						amount = 1.0;
					}

					if (args[0].equalsIgnoreCase("end"))
					{
						getPlugin().getConfigManager().setProperty(
								MobBountyReloadedConfFile.MULTIPLIERS,
								"Environment.End", amount);
						String message = getPlugin().getLocaleManager()
								.getString("MBEMChange");
						if (message != null)
						{
							message = getPlugin().getAPI().formatString(
									message, player.getName(), "",
									player.getWorld().getName(), amount,
									amount, amount, "", "", "", "end", "", 0,
									"", "");
							sender.sendMessage(message);
						}
					}
					else if (args[0].equalsIgnoreCase("nether"))
					{
						getPlugin().getConfigManager().setProperty(
								MobBountyReloadedConfFile.MULTIPLIERS,
								"Environment.Nether", amount);

						String message = getPlugin().getLocaleManager()
								.getString("MBEMChange");
						if (message != null)
						{
							message = getPlugin().getAPI().formatString(
									message, player.getName(), "",
									player.getWorld().getName(), amount,
									amount, amount, "", "", "", "nether", "",
									0, "", "");
							sender.sendMessage(message);
						}
					}
					else if (args[0].equalsIgnoreCase("normal"))
					{
						getPlugin().getConfigManager().setProperty(
								MobBountyReloadedConfFile.MULTIPLIERS,
								"Environment.Normal", amount);

						String message = getPlugin().getLocaleManager()
								.getString("MBEMChange");
						if (message != null)
						{
							message = getPlugin().getAPI().formatString(
									message, player.getName(), "", args[0],
									amount, amount, amount, "", "", "",
									"normal", "", 0, "", "");
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

	public void setPlugin(MobBountyReloaded _plugin)
	{
		this._plugin = _plugin;
	}
}
