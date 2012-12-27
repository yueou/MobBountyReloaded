package com.conventnunnery.MobBountyReloaded;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.conventnunnery.MobBountyReloaded.utils.MobBountyCreature;
import com.conventnunnery.MobBountyReloaded.utils.MobBountyPlayerKillData;

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

    public MobBountyAPI(final MobBountyReloaded plugin)
    {
        setMobBountyReloaded(plugin);
        setMBI(new MBI(getMobBountyReloaded()));
        playerData = new HashMap<String, MobBountyPlayerKillData>();
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
     * @param killStreak
     *            Amount of killstreak
     * @return Formatted string
     */
    public String formatString(final String string, final String playerName,
            final String creatureName, final String worldName,
            final double amount, final double a1, final double a2,
            final String commandName, final String commandHelp,
            final String permission, final String environment,
            final String time, final int killCacheAmount, final String setting,
            final String settingValue, final int killStreak)
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
        message = message.replace("%K",
                String.valueOf(Math.abs(killCacheAmount)));
        message = message.replace("%S", setting);
        message = message.replace("%V", settingValue);
        message = message.replace("%X", String.valueOf(killStreak));
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
     * @param killStreak
     *            value for killstreak amount
     * @return Formatted string
     */
    public String formatString(final String string, final String playerName,
            final String creatureName, final String worldName,
            final String amount, final String a1, final String a2,
            final String commandName, final String commandHelp,
            final String permission, final String environment,
            final String time, final String killCacheAmount,
            final String setting, final String settingValue,
            final String killStreak)
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
        message = message.replace("%X", killStreak);
        return message;
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
    public double getEntityValue(final Player player, final LivingEntity entity)
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
    public double getEntityValue(final Player player,
            final MobBountyCreature creature)
    {
        return getMBI().getValue(player, creature);
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
    public double getEntityValue(final String playerName,
            final MobBountyCreature creature)
    {
        return getMBI().getValue(playerName, creature);
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
     * Gets the copy of MBR.
     * 
     * @return The copy of MobBountyReloaded that this API references.
     */
    public MobBountyReloaded getMobBountyReloaded()
    {
        return mbr;
    }

    /**
     * Gets the multiplier for the player's earnings
     * 
     * @param player
     *            The player to return for
     * @return The multiplier value
     */
    public double getMult(final Player player)
    {
        return getMBI().getEnvironmentMult(player)
                * getMBI().getTimeMult(player) * getMBI().getWorldMult(player)
                * getMBI().getFortuneMult(player)
                * getMBI().getUserMult(player) * getMBI().getGroupMult(player);
    }

    /**
     * Prints out a message to the console.
     * 
     * @param message
     *            A message that is logged by the plugin.
     */
    public void log(final String message)
    {
        log.info("[MobBountyReloaded] " + message);
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
    public boolean makeTransaction(final String playerName, final double amt)
    {
        if (amt == 0.0)
            return true;
        else if (amt > 0.0)
            return getMobBountyReloaded().getEconManager().payAccount(
                    playerName, amt);
        else if (amt < 0.0)
            return getMobBountyReloaded().getEconManager().fineAccount(
                    playerName, amt);
        else
            return false;
    }

    /**
     * Sets the MBI instance
     * 
     * @param mbi
     *            The MBI instance to set
     */
    public void setMBI(final MBI mbi)
    {
        this.mbi = mbi;
    }

    private void setMobBountyReloaded(final MobBountyReloaded mbr)
    {
        this.mbr = mbr;
    }
}
