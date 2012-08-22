package info.hawksharbor.MobBounty.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MBSave implements CommandExecutor
{
	private final info.hawksharbor.MobBounty.MobBountyReloaded _plugin;

	public MBSave(info.hawksharbor.MobBounty.MobBountyReloaded plugin)
	{
		_plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args)
	{
		if (_plugin.getAPIManager().getPermissionsManager()
				.hasPermission(sender, "mbr.command.mbs"))
		{
			_plugin.getAPIManager().getConfigManager().saveConfig();

			String message = _plugin.getAPIManager().getLocaleManager()
					.getString("MBSSaved");
			if (message != null)
				sender.sendMessage(message);
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
