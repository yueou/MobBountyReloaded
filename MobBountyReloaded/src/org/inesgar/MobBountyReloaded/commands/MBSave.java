package org.inesgar.MobBountyReloaded.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.inesgar.MobBountyReloaded.MobBountyReloaded;

public class MBSave implements CommandExecutor
{

	private MobBountyReloaded plugin;

	public MBSave(MobBountyReloaded plugin)
	{
		setPlugin(plugin);
	}

	public boolean onCommand(CommandSender sender, Command command,
			String cmdLbl, String[] args)
	{
		String message;
		if (!getPlugin().getPermissionManager().hasPermission(sender,
				"mbr.admin.command.save"))
		{
			message = getPlugin().getAPI().formatString(
					getPlugin().getLocaleManager().getString("NoAccess"), "",
					"", "", 0.0, 0.0, 0.0, "", "", "mbr.admin.command.save",
					"", "", 0, "", "");
			if (message != null)
			{
				sender.sendMessage(message);
			}
			return true;
		}
		getPlugin().getConfigManager().saveConfig();
		message = getPlugin().getAPI().formatString(
				getPlugin().getLocaleManager().getString("MBSSaved"), "", "",
				"", 0.0, 0.0, 0.0, "", "", "mbr.admin.command.save", "", "", 0,
				"", "");
		if (message != null)
		{
			sender.sendMessage(message);
		}
		return true;
	}

	public MobBountyReloaded getPlugin()
	{
		return plugin;
	}

	public void setPlugin(MobBountyReloaded plugin)
	{
		this.plugin = plugin;
	}

}
