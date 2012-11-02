package org.inesgar.MobBountyReloaded;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.inesgar.MobBountyReloaded.utils.MobBountyCreature;
import org.inesgar.MobBountyReloaded.utils.MobBountyPlayerKillData;

public class MobBountyAPI
{

	private MobBountyReloaded mbr;
	private MBI mbi;
	public static Map<String, MobBountyPlayerKillData> playerData;

	private static final Logger log = Logger.getLogger("Minecraft");

	/**
	 * Gets the Logger
	 * 
	 * @return The Logger for the plugin
	 */
	public static Logger getLogger()
	{
		return log;
	}

	public MobBountyAPI(MobBountyReloaded plugin)
	{
		setMobBountyReloaded(plugin);
		setMBI(new MBI(getMobBountyReloaded()));
		playerData = new HashMap<String, MobBountyPlayerKillData>();
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

	/**
	 * Formats and returns a string. No value should ever be null.
	 * 
	 * @param string
	 *            Message to format
	 * @param playerName
	 *            Player's name
	 * @param creatureName
	 *            Creature's name
	 * @param worldName
	 *            World's name
	 * @param amount
	 *            Amount
	 * @param a1
	 *            First amount
	 * @param a2
	 *            Second amount
	 * @param commandName
	 *            Command's name
	 * @param commandHelp
	 *            Command's help
	 * @param permission
	 *            Permission used
	 * @param environment
	 *            Environment
	 * @param time
	 *            Time
	 * @param killCacheAmount
	 *            Amount of kills in player's kill cache
	 * @param setting
	 *            Setting that was changed
	 * @param settingValue
	 *            New value of setting
	 * @return Formatted string
	 */
	public String formatString(String string, String playerName,
			String creatureName, String worldName, double amount, double a1,
			double a2, String commandName, String commandHelp,
			String permission, String environment, String time,
			int killCacheAmount, String setting, String settingValue)
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
		message = message.replace("%K", getMobBountyReloaded().getEconManager()
				.format(Math.abs(killCacheAmount)));
		message = message.replace("%S", setting);
		message = message.replace("%V", settingValue);
		return message;
	}

	/**
	 * Formats and returns a string. No value should ever be null.
	 * 
	 * @param string
	 *            Message to format
	 * @param playerName
	 *            Player's name
	 * @param creatureName
	 *            Creature's name
	 * @param worldName
	 *            World's name
	 * @param amount
	 *            Amount
	 * @param a1
	 *            First amount
	 * @param a2
	 *            Second amount
	 * @param commandName
	 *            Command's name
	 * @param commandHelp
	 *            Command's help
	 * @param permission
	 *            Permission used
	 * @param environment
	 *            Environment
	 * @param time
	 *            Time
	 * @param killCacheAmount
	 *            Amount of kills in player's kill cache
	 * @param setting
	 *            Setting that was changed
	 * @param settingValue
	 *            New value of setting
	 * @return Formatted string
	 */
	public String formatString(String string, String playerName,
			String creatureName, String worldName, String amount, String a1,
			String a2, String commandName, String commandHelp,
			String permission, String environment, String time,
			String killCacheAmount, String setting, String settingValue)
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
		message = message.replace("%K", killCacheAmount);
		message = message.replace("%S", setting);
		message = message.replace("%V", settingValue);
		return message;
	}

	private void setMobBountyReloaded(MobBountyReloaded mbr)
	{
		this.mbr = mbr;
	}

	/**
	 * Gets a creature's value
	 * 
	 * @param playerName
	 *            Killer
	 * @param creature
	 *            Kind of creature
	 * @return Value
	 */
	public double getEntityValue(String playerName, MobBountyCreature creature)
	{
		return getMBI().getValue(playerName, creature);
	}

	/**
	 * Gets a creature's value
	 * 
	 * @param player
	 *            Killer
	 * @param entity
	 *            Kind of creature
	 * @return Value
	 */
	public double getEntityValue(Player player, LivingEntity entity)
	{
		return getMBI().getValue(player, entity);
	}

	/**
	 * Gets a creature's value
	 * 
	 * @param player
	 *            Killer
	 * @param creature
	 *            Kind of creature
	 * @return Value
	 */
	public double getEntityValue(Player player, MobBountyCreature creature)
	{
		return getMBI().getValue(player, creature);
	}

	/**
	 * Makes a transaction to playerName's account of amt
	 * 
	 * @param playerName
	 *            Player's name
	 * @param amt
	 *            Amount to transfer
	 * @return Success
	 */
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

	/**
	 * Gets the MBI instance
	 * 
	 * @return The MBI instance
	 */
	public MBI getMBI()
	{
		return mbi;
	}

	/**
	 * Sets the MBI instance
	 * 
	 * @param mbi
	 *            The MBI instance to set
	 */
	public void setMBI(MBI mbi)
	{
		this.mbi = mbi;
	}

	/**
	 * Gets the multiplier for the player's earnings
	 * 
	 * @param player
	 *            The player to return for
	 * @return The multiplier value
	 */
	public double getMult(Player player)
	{
		return getMBI().getEnvironmentMult(player)
				* getMBI().getTimeMult(player) * getMBI().getWorldMult(player)
				* getMBI().getFortuneMult(player)
				* getMBI().getUserMult(player) * getMBI().getGroupMult(player);
	}
}
