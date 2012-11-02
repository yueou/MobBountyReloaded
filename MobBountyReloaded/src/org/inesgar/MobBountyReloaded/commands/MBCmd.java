package org.inesgar.MobBountyReloaded.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.inesgar.MobBountyReloaded.MobBountyReloaded;

public class MBCmd implements CommandExecutor
{

	private MobBountyReloaded plugin;

	public MBCmd(MobBountyReloaded plugin)
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

	public boolean onCommand(CommandSender sender, Command command,
			String commandLabel, String[] args)
	{
		String message = getPlugin().getLocaleManager().getString("MBInfo");
		sender.sendMessage(ChatColor.DARK_GREEN
				+ "------------------------------------------");
		sender.sendMessage(ChatColor.DARK_GREEN + "MobBountyReloaded Help");
		sender.sendMessage(ChatColor.DARK_GREEN + "Coded by ToppleTheNun");
		if (message == "")
		{
			sender.sendMessage(ChatColor.DARK_GREEN
					+ "------------------------------------------");
			return true;
		}
		if (getPlugin().getPermissionManager().hasPermission(sender,
				"mbr.user.command.check"))
		{
			sender.sendMessage(getPlugin().getAPI().formatString(message, "",
					"", "", 0.0, 0.0, 0.0, "/mobbountycheck",
					"Checks the values of mobs", "", "", "", 0));
		}
		if (getPlugin().getPermissionManager().hasPermission(sender,
				"mbr.admin.command.load"))
		{
			sender.sendMessage(getPlugin().getAPI().formatString(message, "",
					"", "", 0.0, 0.0, 0.0, "/mobbountyload",
					"Reloads the configs", "", "", "", 0));
		}
		if (getPlugin().getPermissionManager().hasPermission(sender,
				"mbr.admin.command.save"))
		{
			sender.sendMessage(getPlugin().getAPI().formatString(message, "",
					"", "", 0.0, 0.0, 0.0, "/mobbountysave",
					"Save the configs", "", "", "", 0));
		}
		if (sender.hasPermission("mbr.admin.command.reward"))
		{
			sender.sendMessage(getPlugin().getAPI().formatString(message, "",
					"", "", 0.0, 0.0, 0.0,
					"/mobbountyreward <creature> <amount>",
					"Change the <creature>'s value to <amount>", "", "", "", 0));
		}
		if (sender.hasPermission("mbr.admin.command.worldreward"))
		{
			sender.sendMessage(getPlugin().getAPI().formatString(message, "",
					"", "", 0.0, 0.0, 0.0,
					"/mobbountyworldreward <world> <creature> [amount]",
					"Change the <creature>'s value to [amount] in <world>", "",
					"", "", 0));
		}
		if (sender.hasPermission("mbr.admin.command.groupmulti"))
		{
			sender.sendMessage(getPlugin().getAPI().formatString(message, "",
					"", "", 0.0, 0.0, 0.0,
					"/mobbountygroupmulti <group> <amount>",
					"Change <group>'s multiplier to <amount>", "", "", "", 0));
		}
		if (sender.hasPermission("mbr.admin.command.usermulti"))
		{
			sender.sendMessage(getPlugin().getAPI().formatString(message, "",
					"", "", 0.0, 0.0, 0.0,
					"/mobbountyusermulti <user> <amount>",
					"Change <user>'s multiplier to <amount>", "", "", "", 0));
		}
		if (sender.hasPermission("mbr.admin.command.envmulti"))
		{
			sender.sendMessage(getPlugin().getAPI().formatString(message, "",
					"", "", 0.0, 0.0, 0.0,
					"/mobbountyenvmulti <environment> <amount>",
					"Change <environment>'s multiplier to <amount>", "", "",
					"", 0));
		}
		if (sender.hasPermission("mbr.admin.command.timemulti"))
		{
			sender.sendMessage(getPlugin().getAPI().formatString(message, "",
					"", "", 0.0, 0.0, 0.0,
					"/mobbountytimemulti <time> <amount>",
					"Change <time>'s multiplier to <amount>", "", "", "", 0));
		}
		sender.sendMessage(ChatColor.DARK_GREEN
				+ "------------------------------------------");
		return true;
	}
}
