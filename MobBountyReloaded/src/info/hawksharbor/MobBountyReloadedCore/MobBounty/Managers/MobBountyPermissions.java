package info.hawksharbor.MobBountyReloadedCore.MobBounty.Managers;

import info.hawksharbor.MobBountyReloadedCore.MobBounty.MobBountyReloaded;
import info.hawksharbor.MobBountyReloadedCore.MobBounty.Utils.MobBountyConfFile;
import info.hawksharbor.MobBountyReloadedCore.MobBounty.Utils.MobBountyMessage;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class MobBountyPermissions
{

	private static Permission permission = null;

	private MobBountyReloaded _plugin;
	public boolean permissionsHooked;

	public MobBountyPermissions(MobBountyReloaded plugin)
	{
		_plugin = plugin;
		setupPermissions();
		if (permission.getName().equalsIgnoreCase("SuperPerms")
				|| permission == null)
		{
			permissionsHooked = false;
		}
		else
		{
			permissionsHooked = true;
		}
	}

	/**
	 * Gets player's main group
	 * 
	 * @param Player
	 *            Player being requested
	 * @return String Player's main group
	 */
	public String getGroup(Player player)
	{
		return permission.getPrimaryGroup(player);
	}

	/**
	 * Gets permissions providers
	 * 
	 * @return Permission Permissions provider
	 */
	public Permission getPermission()
	{
		return permission;
	}

	/**
	 * Returns if player has permission
	 * 
	 * @param Player
	 *            Player to check
	 * @param String
	 *            Permission node to check
	 * @return boolean If player has permission
	 */
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
