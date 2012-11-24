package org.inesgar.MobBountyReloaded.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.inesgar.MobBountyReloaded.MobBountyReloaded;
import org.inesgar.MobBountyReloaded.utils.MobBountyTime;
import org.inesgar.MobBountyReloaded.utils.configuration.MobBountyReloadedConfFile;

public class MBTimeMulti implements CommandExecutor
{
	private MobBountyReloaded _plugin;

	public MBTimeMulti(MobBountyReloaded plugin)
	{
		setPlugin(plugin);
	}

	private void commandUsage(CommandSender sender, String command)
	{
		String message = getPlugin().getLocaleManager().getString("MBTMUsage");
		if (message != null)
		{
			message = getPlugin().getAPI().formatString(message,
					sender.getName(), "", "", 0.0, 0.0, 0.0, command, "", "",
					"", "", 0, "", "");
			sender.sendMessage(message);
		}

		message = getPlugin().getLocaleManager().getString("MBTMTimes");
		if (message != null)
		{
			message = getPlugin().getAPI().formatString(message,
					sender.getName(), "", "", 0.0, 0.0, 0.0, command, "", "",
					"", "", 0, "", "");
			sender.sendMessage(message);
		}
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
				"mbr.admin.command.mbtm"))
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
					MobBountyTime time = MobBountyTime
							.getTimeFromString(args[0]);

					if (time != null)
					{
						getPlugin().getConfigManager().setProperty(
								MobBountyReloadedConfFile.MULTIPLIERS,
								"Time." + time.getName(), amount);

						String message = getPlugin().getLocaleManager()
								.getString("MBTMChange");
						if (message != null)
						{
							message = getPlugin().getAPI().formatString(
									message, sender.getName(), "", "", amount,
									amount, amount, command.getName(), "", "",
									"", time.getName(), 0, "", "");
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
