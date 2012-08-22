package info.hawksharbor.MobBounty.managers;

import info.hawksharbor.MobBounty.MobBountyReloaded;
import info.hawksharbor.MobBounty.Utils.MobBountyConfFile;
import info.hawksharbor.MobBounty.Utils.MobBountyMessage;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class MobBountyPermissions
{

	private MobBountyReloaded _plugin;

	private static Permission permission = null;

	public MobBountyPermissions(MobBountyReloaded plugin)
	{
		_plugin = plugin;
		setupPermissions();
	}

	public String getGroup(Player player)
	{
		return permission.getPrimaryGroup(player);
	}

	public Permission getPermission()
	{
		return permission;
	}

	public boolean hasPermission(Player player, String node)
	{
		if (player.hasPermission(node))
		{
			if (_plugin.getAPIManager().getConfigManager()
					.getProperty(MobBountyConfFile.GENERAL, "debugMode") != null
					&& "true"
							.equalsIgnoreCase(_plugin
									.getAPIManager()
									.getConfigManager()
									.getProperty(MobBountyConfFile.GENERAL,
											"debugMode")))
			{
				String message = _plugin.getAPIManager().getLocaleManager()
						.getString("DebugHasPermission");
				if (message != null
						&& player.hasPermission("mbr.admin.debug.permissions"))
				{
					message = message.replace("%P", player.getName()).replace(
							"%D", node);
					MobBountyMessage.sendMessage(player, message);
				}
			}
			return true;
		}
		if (_plugin.getAPIManager().getConfigManager()
				.getProperty(MobBountyConfFile.GENERAL, "debugMode") != null
				&& "true".equalsIgnoreCase(_plugin.getAPIManager()
						.getConfigManager()
						.getProperty(MobBountyConfFile.GENERAL, "debugMode")))
		{
			String message = _plugin.getAPIManager().getLocaleManager()
					.getString("DebugLacksPermission");
			if (message != null
					&& player.hasPermission("mbr.admin.debug.permissions"))
			{
				message = message.replace("%P", player.getName()).replace("%D",
						node);
				MobBountyMessage.sendMessage(player, message);
			}
		}
		return false;
	}

	private boolean setupPermissions()
	{
		RegisteredServiceProvider<Permission> permissionProvider = _plugin
				.getServer()
				.getServicesManager()
				.getRegistration(net.milkbowl.vault.permission.Permission.class);
		if (permissionProvider != null)
		{
			permission = permissionProvider.getProvider();
		}
		return (permission != null);
	}

}
