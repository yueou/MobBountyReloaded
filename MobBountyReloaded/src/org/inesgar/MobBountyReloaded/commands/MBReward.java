package org.inesgar.MobBountyReloaded.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.inesgar.MobBountyReloaded.MobBountyReloaded;
import org.inesgar.MobBountyReloaded.utils.MobBountyCreature;
import org.inesgar.MobBountyReloaded.utils.configuration.MobBountyReloadedConfFile;

public class MBReward implements CommandExecutor
{

	private MobBountyReloaded plugin;

	public MBReward(MobBountyReloaded plugin)
	{
		setPlugin(plugin);
	}

	public MobBountyReloaded getPlugin()
	{
		return plugin;
	}

	public void setPlugin(MobBountyReloaded plugin)
	{
		this.plugin = plugin;
	}

	private void commandUsage(CommandSender sender, String command)
	{
		String message = getPlugin().getLocaleManager().getString("MBRUsage");
		if (message != null)
		{
			message = message.replace("%C", command);
			sender.sendMessage(message);
		}

		message = getPlugin().getLocaleManager().getString("MBRMobs");
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
				"mbr.admin.command.reward"))
		{
			if (args.length == 2)
			{
				MobBountyCreature mob = MobBountyCreature.fromName(args[0]);

				if (mob != null)
				{
					if (args[1].matches("[-]?[0-9]+([.][0-9]+)?"))
					{
						Double amount = Double.parseDouble(args[1]);
						getPlugin().getConfigManager().setProperty(
								MobBountyReloadedConfFile.REWARDS,
								"Default." + mob.getName(), amount.toString());

						String message = getPlugin().getLocaleManager()
								.getString("MBRChange");
						if (message != null)
						{
							sender.sendMessage(getPlugin().getAPI()
									.formatString(message, "", mob.getName(),
											"", Double.valueOf(args[1]),
											Double.valueOf(args[1]),
											Double.valueOf(args[1]), "", "",
											"mbr.admin.command.reward", ""));
						}
					}
					else if (args[1]
							.matches("[-]?[0-9]+([.][0-9]+)?[:][-]?[0-9]+([.][0-9]+)?"))
					{
						getPlugin().getConfigManager().setProperty(
								MobBountyReloadedConfFile.REWARDS,
								"Default." + mob.getName(), args[1]);

						String message = getPlugin().getLocaleManager()
								.getString("MBRChange");
						if (message != null)
						{
							sender.sendMessage(getPlugin().getAPI()
									.formatString(message, "", mob.getName(),
											"", args[1], args[1], args[1], "",
											"", "mbr.admin.command.reward", ""));
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

}
