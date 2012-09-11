package info.hawksharbor.MobBountyReloadedCore.MobBounty.Commands;

import info.hawksharbor.MobBountyReloadedCore.MobBounty.Utils.MobBountyAPI;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class MBLoad implements CommandExecutor
{
	private final info.hawksharbor.MobBountyReloadedCore.MobBounty.MobBountyReloaded _plugin;

	public MBLoad(info.hawksharbor.MobBountyReloadedCore.MobBounty.MobBountyReloaded plugin)
	{
		_plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args)
	{
		if (!(sender instanceof Player))
		{
			if (sender instanceof ConsoleCommandSender)
			{
				_plugin.getAPIManager().getConfigManager().loadConfig();

				String message = _plugin.getAPIManager().getLocaleManager()
						.getString("MBLLoaded");
				if (message != null)
					sender.sendMessage(message);
				return true;
			}
			sender.sendMessage("Commands are designed to be run by players only.");
			return true;
		}
		Player player = ((Player) sender);
		if (MobBountyAPI.instance.getPermissionsManager().hasPermission(player,
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
