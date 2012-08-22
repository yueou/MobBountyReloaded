package info.hawksharbor.MobBounty.Listeners;

import info.hawksharbor.MobBounty.MobBountyReloaded;
import info.hawksharbor.MobBounty.Utils.MobBountyPlayerKillData;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
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

	@EventHandler
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args)
	{
		return _plugin.getAPIManager().getCommandManager()
				.onCommand(sender, command, label, args);
	}

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
