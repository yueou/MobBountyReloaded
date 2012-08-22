package info.hawksharbor.MobBounty.Listeners;

import info.hawksharbor.MobBounty.MobBountyReloaded;
import info.hawksharbor.MobBounty.Utils.MobBountyAPI;
import info.hawksharbor.MobBounty.Utils.MobBountyConfFile;
import info.hawksharbor.MobBounty.Utils.MobBountyCreature;
import info.hawksharbor.MobBounty.managers.MobBountyDrops;
import info.hawksharbor.MobBounty.managers.MobBountyEcon;
import info.hawksharbor.MobBounty.managers.MobBountyExperience;

import org.bukkit.GameMode;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class MobBountyEntityListener implements Listener
{
	private final MobBountyReloaded _plugin;

	public MobBountyEntityListener(MobBountyReloaded plugin)
	{
		_plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onEntityDeath(EntityDeathEvent event)
	{
		LivingEntity entity = event.getEntity();
		Player killer = entity.getKiller();

		if (killer != null
				&& _plugin.getAPIManager().getPermissionsManager()
						.hasPermission(killer, "mbr.user.collect.normal"))
		{
			String mC = MobBountyAPI.instance.getConfigManager().getProperty(
					MobBountyConfFile.GENERAL, "preventCreativeEarning");
			boolean prevent = Boolean.parseBoolean(mC);
			if (!prevent
					|| (prevent && !killer.getGameMode().equals(
							GameMode.CREATIVE)))
			{
				if (_plugin
						.getAPIManager()
						.getExternalsManager()
						.checkEarnPermission(killer, entity.getLocation(),
								entity)
						&& !MobBountyAPI.instance.getListenerManager()
								.getSpawnReason()
								.containsKey(entity.getUniqueId()))
				{
					MobBountyCreature creature = MobBountyCreature.valueOf(
							entity, "");
					MobBountyEcon.handleMobBountyTransaction(killer, entity,
							creature);
					MobBountyDrops.handleDrops(killer, entity, creature, event);
					MobBountyExperience.handleExperience(killer, entity,
							creature, event);
				}
			}
		}
	}

	public static double getCreative(Player player)
	{
		String mC = MobBountyAPI.instance.getConfigManager().getProperty(
				MobBountyConfFile.GENERAL, "preventCreativeEarning");
		boolean prevent = Boolean.parseBoolean(mC);
		if (prevent && player.getGameMode().equals(GameMode.CREATIVE))
		{
			return 0.0;
		}
		return 1.0;
	}
}
