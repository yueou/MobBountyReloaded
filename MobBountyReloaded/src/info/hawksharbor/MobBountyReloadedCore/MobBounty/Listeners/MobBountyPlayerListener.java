package info.hawksharbor.MobBountyReloadedCore.MobBounty.Listeners;

import info.hawksharbor.MobBountyReloadedCore.MobBounty.MobBountyReloaded;
import info.hawksharbor.MobBountyReloadedCore.MobBounty.Managers.MobBountyEcon;
import info.hawksharbor.MobBountyReloadedCore.MobBounty.Utils.MobBountyPlayerKillData;

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
		_plugin.getServer().getPluginManager().registerEvents(this, _plugin);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerDeath(PlayerDeathEvent event)
	{
		Player p = event.getEntity();
		MobBountyPlayerKillData playerData = MobBountyEcon._playerData.get(p
				.getName());
		if (playerData == null)
		{
			playerData = new MobBountyPlayerKillData();
		}
		playerData.killStreak = 0;
		MobBountyEcon._playerData.put(p.getName(), playerData);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		String name = event.getPlayer().getName();
		long cT = System.currentTimeMillis();
		_plugin.getAPIManager().getLoginTimer().put(name, cT);
		MobBountyPlayerKillData playerData = MobBountyEcon._playerData
				.get(name);
		if (playerData == null)
		{
			playerData = new MobBountyPlayerKillData();
		}
		playerData.killStreak = 0;
		MobBountyEcon._playerData.put(name, playerData);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		String name = event.getPlayer().getName();
		MobBountyPlayerKillData playerData = MobBountyEcon._playerData
				.get(name);
		if (playerData == null)
		{
			playerData = new MobBountyPlayerKillData();
		}
		playerData.killStreak = 0;
		MobBountyEcon._playerData.put(name, playerData);
	}
}
