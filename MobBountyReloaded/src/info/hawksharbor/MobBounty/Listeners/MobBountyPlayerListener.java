package info.hawksharbor.MobBounty.Listeners;

import info.hawksharbor.MobBounty.MobBountyReloaded;
import info.hawksharbor.MobBounty.Utils.MobBountyMessage;
import info.hawksharbor.MobBounty.Utils.MobBountyPlayerKillData;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class MobBountyPlayerListener implements Listener
{

	private MobBountyReloaded _plugin;

	public MobBountyPlayerListener(MobBountyReloaded plugin)
	{
		_plugin = plugin;
	}

	// @EventHandler
	// public boolean onCommand(CommandSender sender, Command command,
	// String label, String[] args)
	// {
	// return _plugin.getAPIManager().getCommandManager()
	// .onCommand(sender, command, label, args);
	// }

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerDeath(PlayerDeathEvent event)
	{
		Player p = event.getEntity();
		MobBountyPlayerKillData playerData = _plugin.getAPIManager()
				.getListenerManager().getPlayerData().get(p.getName());
		if (playerData == null)
		{
			playerData = new MobBountyPlayerKillData();
		}
		playerData.killStreak = 0;
		_plugin.getAPIManager().getListenerManager().getPlayerData()
				.put(p.getName(), playerData);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		String name = event.getPlayer().getName();
		long cT = System.currentTimeMillis();
		_plugin.getAPIManager().getLoginTimer().put(name, cT);
		MobBountyPlayerKillData playerData = _plugin.getAPIManager()
				.getListenerManager().getPlayerData().get(name);
		if (playerData == null)
		{
			playerData = new MobBountyPlayerKillData();
		}
		playerData.killStreak = 0;
		_plugin.getAPIManager().getListenerManager().getPlayerData()
				.put(name, playerData);
		if (_plugin.getAPIManager().getPermissionsManager()
				.hasPermission(event.getPlayer(), "mbr.admin.update"))
		{
			try
			{
				if (_plugin.getAPIManager().getNewVersion() > _plugin
						.getAPIManager().getCurrentVersion())
				{
					String available = _plugin.getAPIManager()
							.getLocaleManager().getString("UpdateAvailable");
					String newNumber = _plugin.getAPIManager()
							.getLocaleManager().getString("NewVersion");
					String getItAt = _plugin.getAPIManager().getLocaleManager()
							.getString("GetItAt");
					if (available != null)
						MobBountyMessage.sendMessage(event.getPlayer(),
								available);
					if (newNumber != null)
						MobBountyMessage.sendMessage(
								event.getPlayer(),
								newNumber.replace(
										"%N",
										String.valueOf(_plugin.getAPIManager()
												.getNewVersion())).replace(
										"%O",
										String.valueOf(_plugin.getAPIManager()
												.getCurrentVersion())));
					if (getItAt != null)
						MobBountyMessage
								.sendMessage(event.getPlayer(), getItAt);
				}
			}
			catch (Exception e)
			{
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		String name = event.getPlayer().getName();
		MobBountyPlayerKillData playerData = _plugin.getAPIManager()
				.getListenerManager().getPlayerData().get(name);
		if (playerData == null)
		{
			playerData = new MobBountyPlayerKillData();
		}
		playerData.killStreak = 0;
		_plugin.getAPIManager().getListenerManager().getPlayerData()
				.put(name, playerData);
	}
}
