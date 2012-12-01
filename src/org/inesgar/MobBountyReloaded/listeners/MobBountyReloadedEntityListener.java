package org.inesgar.MobBountyReloaded.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.inesgar.MobBountyReloaded.MobBountyAPI;
import org.inesgar.MobBountyReloaded.MobBountyReloaded;
import org.inesgar.MobBountyReloaded.event.MobBountyCreatureDeathEvent;
import org.inesgar.MobBountyReloaded.event.MobBountyReloadedKillstreakEvent;
import org.inesgar.MobBountyReloaded.event.MobBountyReloadedPaymentEvent;
import org.inesgar.MobBountyReloaded.utils.MobBountyCreature;
import org.inesgar.MobBountyReloaded.utils.MobBountyPlayerKillData;
import org.inesgar.MobBountyReloaded.utils.MobBountyUtils;
import org.inesgar.MobBountyReloaded.utils.configuration.MobBountyReloadedConfFile;

public class MobBountyReloadedEntityListener implements Listener
{

    private MobBountyReloaded plugin;

    public MobBountyReloadedEntityListener(MobBountyReloaded mbr)
    {
        setPlugin(mbr);
        getPlugin().getServer().getPluginManager()
                .registerEvents(this, getPlugin());
    }

    public MobBountyReloaded getPlugin()
    {
        return plugin;
    }

    private void setPlugin(MobBountyReloaded mbr)
    {
        this.plugin = mbr;
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
        MobBountyCreatureDeathEvent mbcde = new MobBountyCreatureDeathEvent(
                event.getEntity(), creature, event);
        getPlugin().getServer().getPluginManager().callEvent(mbcde);
        if (!getPlugin().getPermissionManager().hasPermission(player,
                "mbr.user.collect.normal"))
        {
            return;
        }
        if (!getPlugin().getPermissionManager().hasPermission(player,
                "mbr.user.collect." + creature.getName().toLowerCase()))
        {
            return;
        }
        double amount = getPlugin().getAPI().getEntityValue(player.getName(),
                creature);
        if (amount < 0.0
                && !getPlugin().getPermissionManager().hasPermission(player,
                        "mbr.user.finebypass"))
        {
            return;
        }
        MobBountyReloadedPaymentEvent mbrpe = new MobBountyReloadedPaymentEvent(
                player.getName(), creature, amount);
        getPlugin().getServer().getPluginManager().callEvent(mbrpe);
        MobBountyReloadedKillstreakEvent mbrke = new MobBountyReloadedKillstreakEvent(
                player.getName(), creature);
        List<String> creatureNames = getPlugin().getConfigManager()
                .getPropertyList(MobBountyReloadedConfFile.KILLSTREAK,
                        "allowedCreatures");
        if (creatureNames == null)
        {
            creatureNames = new ArrayList<String>();
        }
        if (creatureNames.contains(creature.getName()))
        {
            getPlugin().getServer().getPluginManager().callEvent(mbrke);
        }
        if (!mbrpe.isCancelled())
        {
            Player playerEvent = Bukkit.getServer().getPlayer(
                    mbrpe.getPlayerName());
            MobBountyPlayerKillData playerKillData = MobBountyAPI.playerData
                    .get(playerEvent.getName());
            if (playerKillData == null)
            {
                playerKillData = new MobBountyPlayerKillData();
                MobBountyAPI.playerData.put(playerEvent.getName(),
                        playerKillData);
            }
            double amountEvent = mbrpe.getAmount();
            if (amountEvent != 0.0)
            {
                double mult = getPlugin().getAPI().getMult(playerEvent);
                double amt = mult * amountEvent;
                getPlugin().getAPI().makeTransaction(mbrpe.getPlayerName(),
                        mult * amountEvent);
                String blnTest = getPlugin().getConfigManager().getProperty(
                        MobBountyReloadedConfFile.GENERAL, "killCache.use");
                boolean bln = Boolean.parseBoolean(blnTest);
                if (bln)
                {
                    handleKillCache(playerEvent, playerKillData, amt);
                }
                else
                {
                    sendNonKillCache(mbrpe, playerEvent, amountEvent, amt);
                }
            }
        }
        if (!mbrke.isCancelled())
        {
            MobBountyPlayerKillData playerData = MobBountyAPI.playerData
                    .get(mbrke.getPlayerName());
            if (playerData == null)
            {
                playerData = new MobBountyPlayerKillData();
                MobBountyAPI.playerData.put(mbrke.getPlayerName(), playerData);
            }
            playerData.killStreak++;
            String confBonusS = getPlugin().getConfigManager().getProperty(
                    MobBountyReloadedConfFile.KILLSTREAK,
                    "KillBonus." + playerData.killStreak);
            if (confBonusS != null)
            {
                double confBonus = 0.0;
                try
                {
                    confBonus = Double.parseDouble(confBonusS);
                }
                catch (NumberFormatException e)
                {
                    confBonus = 0.0;
                    getPlugin().getConfigManager().setProperty(
                            MobBountyReloadedConfFile.KILLSTREAK,
                            "KillBonus." + playerData.killStreak, "0.0");
                }
                if (confBonus == 0.0)
                {
                    return;
                }
                getPlugin().getEconManager().payAccount(mbrke.getPlayerName(),
                        confBonus);
                Player playerEvent = getPlugin().getServer().getPlayer(
                        mbrke.getPlayerName());
                String string = getPlugin().getLocaleManager().getString(
                        "Killstreak");
                if (string != null)
                {
                    playerEvent.sendMessage(getPlugin().getAPI().formatString(
                            string, playerEvent.getName(),
                            mbrke.getCreature().getName(),
                            playerEvent.getWorld().getName(), confBonus,
                            confBonus, confBonus, "", "", "", "", "", 0, "",
                            "", playerData.killStreak));
                }
                MobBountyAPI.playerData.put(playerEvent.getName(), playerData);
            }
        }
    }

    private void sendNonKillCache(MobBountyReloadedPaymentEvent mbrpe,
            Player player, double amount, double amt)
    {
        if (amount > 0.0)
        {
            sendPayMessage(mbrpe, player, amt);
        }
        else if (amount < 0.0)
        {
            sendFineMessage(mbrpe, player, amt);
        }
    }

    private void sendPayMessage(MobBountyReloadedPaymentEvent mbrpe,
            Player player, double amt)
    {
        String string = getPlugin().getLocaleManager().getString("Awarded");
        if (string == null)
        {
            return;
        }
        player.sendMessage(getPlugin().getAPI().formatString(
                string,
                player.getName(),
                mbrpe.getCreature().getName(),
                player.getWorld().getName(),
                amt,
                amt,
                amt,
                "",
                "",
                "mbr.user.collect."
                        + mbrpe.getCreature().getName().toLowerCase(), "", "",
                0, "", "",
                MobBountyAPI.playerData.get(player.getName()).killStreak));
    }

    private void sendFineMessage(MobBountyReloadedPaymentEvent mbrpe,
            Player player, double amt)
    {
        String string = getPlugin().getLocaleManager().getString("Fined");
        if (string == null)
        {
            return;
        }
        player.sendMessage(getPlugin().getAPI().formatString(
                string,
                player.getName(),
                mbrpe.getCreature().getName(),
                player.getWorld().getName(),
                amt,
                amt,
                amt,
                "",
                "",
                "mbr.user.collect."
                        + mbrpe.getCreature().getName().toLowerCase(), "", "",
                0, "", "",
                MobBountyAPI.playerData.get(player.getName()).killStreak));
    }

    private void handleKillCache(Player killer,
            MobBountyPlayerKillData playerData, double amount)
    {
        playerData.cacheSize++;
        if (amount >= 0.0)
            playerData.cacheEarned = playerData.cacheEarned + Math.abs(amount);
        else
            playerData.cacheEarned = playerData.cacheEarned - Math.abs(amount);
        int timeLimit = MobBountyUtils.getInt(
                getPlugin().getConfigManager().getProperty(
                        MobBountyReloadedConfFile.GENERAL,
                        "killCache.timeLimit"), 30000);
        long cTime = System.currentTimeMillis() - playerData.cacheTime;
        if (cTime >= timeLimit)
        {
            if (playerData.cacheEarned > 0.0)
            {
                String message = getPlugin().getLocaleManager().getString(
                        "CacheAwarded");
                if (message != null)
                {
                    message = getPlugin()
                            .getAPI()
                            .formatString(
                                    message,
                                    killer.getName(),
                                    "",
                                    killer.getWorld().getName(),
                                    playerData.cacheEarned,
                                    playerData.cacheEarned,
                                    playerData.cacheEarned,
                                    "",
                                    "",
                                    "",
                                    "",
                                    String.valueOf(String.valueOf(Math
                                            .round((System.currentTimeMillis() - playerData.cacheTime) / 1000))),
                                    playerData.cacheSize, "", "",
                                    playerData.killStreak);
                    killer.sendMessage(message);
                }
            }
            else if (playerData.cacheEarned < 0.0)
            {
                String message = getPlugin().getLocaleManager().getString(
                        "CacheFined");
                if (message != null)
                {
                    message = getPlugin().getAPI()
                            .formatString(
                                    message,
                                    killer.getName(),
                                    "",
                                    killer.getWorld().getName(),
                                    playerData.cacheEarned,
                                    playerData.cacheEarned,
                                    playerData.cacheEarned,
                                    "",
                                    "",
                                    "",
                                    "",
                                    String.valueOf(String.valueOf(Math
                                            .round(cTime / 1000))),
                                    playerData.cacheSize, "", "",
                                    playerData.killStreak);
                    killer.sendMessage(message);
                }
            }
        }
        playerData.cacheTime = System.currentTimeMillis();
        MobBountyAPI.playerData.put(killer.getName(), playerData);
    }
}
