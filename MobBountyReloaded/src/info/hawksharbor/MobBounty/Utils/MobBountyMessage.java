package info.hawksharbor.MobBounty.Utils;

import info.hawksharbor.MobBounty.MobBountyReloaded;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MobBountyMessage
{

	public static void debugMessage(String message)
	{
		if (MobBountyAPI.instance.getConfigManager().getProperty(
				MobBountyConfFile.GENERAL, "debugMode") != null
				&& "true".equalsIgnoreCase(MobBountyAPI.instance
						.getConfigManager().getProperty(
								MobBountyConfFile.GENERAL, "debugMode")))
		{
			String key = MobBountyAPI.instance.getLocaleManager().getString(
					"DebugGeneral");
			if (key != null)
			{
				key = key.replace("%M", message);
				logToConsole(key);
				for (Player p : Bukkit.getServer().getOnlinePlayers())
				{
					if (MobBountyAPI.instance.getPermissionsManager()
							.hasPermission(p, "mbr.admin.debug.general")
							|| p.isOp())
					{
						sendMessage(p, key);
					}
				}
			}
		}
	}

	public static void logWarningToConsole(String message)
	{
		MobBountyReloaded._logger.warning("[MobBountyReloaded] " + message);
	}

	public static void logSevereToConsole(String message)
	{
		MobBountyReloaded._logger.severe("[MobBountyReloaded] " + message);
	}

	public static void logToConsole(String message)
	{
		MobBountyReloaded._logger.info("[MobBountyReloaded] " + message);
	}

	public static void sendMessage(CommandSender sender, String message)
	{
		sender.sendMessage(message);
	}

}
