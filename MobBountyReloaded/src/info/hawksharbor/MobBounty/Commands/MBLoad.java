package info.hawksharbor.MobBounty.Commands;

import info.hawksharbor.MobBounty.Utils.MobBountyAPI;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MBLoad implements CommandExecutor
{
	@SuppressWarnings("unused")
	private final info.hawksharbor.MobBounty.MobBountyReloaded _plugin;

	public MBLoad(info.hawksharbor.MobBounty.MobBountyReloaded plugin)
	{
		_plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args)
	{

		if (MobBountyAPI.instance.getPermissionsManager().hasPermission(sender,
				"mbr.command.mbl"))
		{
			MobBountyAPI.instance.getConfigManager().loadConfig();

			String message = MobBountyAPI.instance.getLocaleManager()
					.getString("MBLLoaded");
			if (message != null)
				sender.sendMessage(message);
		}
		else
		{
			String message = MobBountyAPI.instance.getLocaleManager()
					.getString("NoAccess");
			if (message != null)
				sender.sendMessage(message);
		}

		return true;
	}
}
