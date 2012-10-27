package org.inesgar.MobBountyReloaded.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.inesgar.MobBountyReloaded.MobBountyReloaded;
import org.inesgar.MobBountyReloaded.event.CreatureDeathEvent;
import org.inesgar.MobBountyReloaded.utils.MobBountyCreature;

public class MobBountyReloadedEntityListener implements Listener
{

	private MobBountyReloaded mbr;

	public MobBountyReloadedEntityListener(MobBountyReloaded mbr)
	{
		setMBR(mbr);
		getMBR().getServer().getPluginManager().registerEvents(this, getMBR());
	}

	public MobBountyReloaded getMBR()
	{
		return mbr;
	}

	private void setMBR(MobBountyReloaded mbr)
	{
		this.mbr = mbr;
	}

	@EventHandler
	public void onEntityDeath(EntityDeathEvent event)
	{
		if (!(event.getEntity().getKiller() instanceof Player))
		{
			return;
		}
		Player player = event.getEntity().getKiller();
		MobBountyCreature creature = MobBountyCreature.valueOf(
				event.getEntity(), "");
		CreatureDeathEvent cde = new CreatureDeathEvent(player.getName(),
				creature);
		getMBR().getServer().getPluginManager().callEvent(cde);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onCreatureDeath(CreatureDeathEvent cde)
	{
		if (cde.isCancelled())
		{
			return;
		}
		Player player = Bukkit.getServer().getPlayer(cde.getPlayerName());
		if (player == null)
			return;
		if (!getMBR().getPermissionManager().hasPermission(player,
				"mbr.user.collect.normal"))
		{
			return;
		}
		if (!getMBR().getPermissionManager()
				.hasPermission(
						player,
						"mbr.user.collect."
								+ cde.getCreature().getName().toLowerCase()))
		{
			return;
		}
		double amount = getMBR().getAPI().getEntityValue(cde.getPlayerName(),
				cde.getCreature());
		if (amount == 0.0)
		{
			return;
		}
		double mult = getMBR().getAPI().getMult(player);
		double amt = mult * amount;
		getMBR().getAPI().makeTransaction(cde.getPlayerName(), mult * amount);
		if (amount > 0.0)
		{
			String string = getMBR().getLocaleManager().getString("Awarded");
			if (string == null)
			{
				return;
			}
			player.sendMessage(getMBR()
					.getAPI()
					.formatString(
							string,
							player.getName(),
							cde.getCreature().getName(),
							player.getWorld().getName(),
							amt,
							amt,
							amt,
							"",
							"",
							"mbr.user.collect."
									+ cde.getCreature().getName().toLowerCase(),
							"", ""));
		}
		else if (amount < 0.0)
		{
			String string = getMBR().getLocaleManager().getString("Fined");
			if (string == null)
			{
				return;
			}
			player.sendMessage(getMBR()
					.getAPI()
					.formatString(
							string,
							player.getName(),
							cde.getCreature().getName(),
							player.getWorld().getName(),
							amt,
							amt,
							amt,
							"",
							"",
							"mbr.user.collect."
									+ cde.getCreature().getName().toLowerCase(),
							"", ""));
		}
	}
}
