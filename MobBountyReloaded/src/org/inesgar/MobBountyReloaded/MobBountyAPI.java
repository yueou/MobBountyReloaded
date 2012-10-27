package org.inesgar.MobBountyReloaded;

import java.util.logging.Logger;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.inesgar.MobBountyReloaded.utils.MobBountyCreature;

public class MobBountyAPI
{

	private MobBountyReloaded mbr;
	private MBI mbi;

	private static final Logger log = Logger.getLogger("Minecraft");

	public static Logger getLogger()
	{
		return log;
	}

	public MobBountyAPI(MobBountyReloaded plugin)
	{
		setMobBountyReloaded(plugin);
		setMBI(new MBI(getMobBountyReloaded()));
	}

	/**
	 * Gets the copy of MBR.
	 * 
	 * @return The copy of MobBountyReloaded that this API references.
	 */
	public MobBountyReloaded getMobBountyReloaded()
	{
		return mbr;
	}

	public String formatString(String string, String playerName,
			String creatureName, String worldName, double amount, double a1,
			double a2, String commandName, String commandHelp,
			String permission, String environment, String time)
	{
		String message = string;
		message = message.replace("%P", playerName);
		message = message.replace("%M", creatureName);
		message = message.replace("%W", worldName);
		message = message.replace("%A", getMobBountyReloaded().getEconManager()
				.format(Math.abs(amount)));
		message = message.replace("%1", getMobBountyReloaded().getEconManager()
				.format(Math.abs(a1)));
		message = message.replace("%2", getMobBountyReloaded().getEconManager()
				.format(Math.abs(a2)));
		message = message.replace("%C", commandName);
		message = message.replace("%H", commandHelp);
		message = message.replace("%D", permission);
		message = message.replace("%E", environment);
		message = message.replace("%T", time);
		return message;
	}

	public String formatString(String string, String playerName,
			String creatureName, String worldName, String amount, String a1,
			String a2, String commandName, String commandHelp,
			String permission, String environment, String time)
	{
		String message = string;
		message = message.replace("%P", playerName);
		message = message.replace("%M", creatureName);
		message = message.replace("%W", worldName);
		message = message.replace("%A", amount);
		message = message.replace("%1", a1);
		message = message.replace("%2", a2);
		message = message.replace("%C", commandName);
		message = message.replace("%H", commandHelp);
		message = message.replace("%D", permission);
		message = message.replace("%E", environment);
		message = message.replace("%T", environment);
		return message;
	}

	private void setMobBountyReloaded(MobBountyReloaded mbr)
	{
		this.mbr = mbr;
	}

	public double getEntityValue(String playerName, MobBountyCreature creature)
	{
		return getMBI().getValue(playerName, creature);
	}

	public double getEntityValue(Player player, LivingEntity entity)
	{
		return getMBI().getValue(player, entity);
	}

	public double getEntityValue(Player player, MobBountyCreature creature)
	{
		return getMBI().getValue(player, creature);
	}

	public boolean makeTransaction(String playerName, double amt)
	{
		if (amt == 0.0)
		{
			return true;
		}
		else if (amt > 0.0)
		{
			return getMobBountyReloaded().getEconManager().payAccount(
					playerName, amt);
		}
		else if (amt < 0.0)
		{
			return getMobBountyReloaded().getEconManager().fineAccount(
					playerName, amt);
		}
		else
		{
			return false;
		}
	}

	/**
	 * Prints out a message to the console.
	 * 
	 * @param message
	 *            A message that is logged by the plugin.
	 */
	public void log(String message)
	{
		log.info("[MobBountyReloaded] " + message);
	}

	public MBI getMBI()
	{
		return mbi;
	}

	public void setMBI(MBI mbi)
	{
		this.mbi = mbi;
	}

	public double getMult(Player player)
	{
		return getMBI().getEnvironmentMult(player)
				* getMBI().getTimeMult(player) * getMBI().getWorldMult(player)
				* getMBI().getFortuneMult(player)
				* getMBI().getUserMult(player) * getMBI().getGroupMult(player);
	}
}
