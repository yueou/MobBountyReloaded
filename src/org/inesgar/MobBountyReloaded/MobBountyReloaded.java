package org.inesgar.MobBountyReloaded;

import java.io.IOException;

import org.bukkit.plugin.java.JavaPlugin;
import org.inesgar.MobBountyReloaded.listeners.MobBountyReloadedEntityListener;
import org.inesgar.MobBountyReloaded.managers.MobBountyCommand;
import org.inesgar.MobBountyReloaded.managers.MobBountyConfigs;
import org.inesgar.MobBountyReloaded.managers.MobBountyEconomy;
import org.inesgar.MobBountyReloaded.managers.MobBountyLocale;
import org.inesgar.MobBountyReloaded.managers.MobBountyPermission;
import org.mcstats.MetricsLite;

public class MobBountyReloaded extends JavaPlugin
{

    private MobBountyAPI api;

    private static MobBountyReloaded instance;
    private MobBountyConfigs configManager;
    private MobBountyEconomy econManager;
    private MobBountyLocale localeManager;
    private MobBountyCommand commandManager;
    private MobBountyPermission permissionManager;

    @Override
    public void onEnable()
    {
        setInstance(this);
        setAPI(new MobBountyAPI(getInstance()));
        setPermissionManager(new MobBountyPermission(getInstance()));
        setConfigManager(new MobBountyConfigs(getInstance()));
        setEconManager(new MobBountyEconomy(getInstance()));
        new MobBountyReloadedEntityListener(getInstance());
        setLocaleManager(new MobBountyLocale(getInstance()));
        setCommandManager(new MobBountyCommand(getInstance()));
        getAPI().log("Enabled.");
        startStatistics();
    }

    @Override
    public void onDisable()
    {
        getAPI().log("Disabled.");
    }

    public MobBountyAPI getAPI()
    {
        return api;
    }

    public void setAPI(MobBountyAPI api)
    {
        this.api = api;
    }

    public static MobBountyReloaded getInstance()
    {
        return instance;
    }

    public static void setInstance(MobBountyReloaded instance)
    {
        MobBountyReloaded.instance = instance;
    }

    public MobBountyConfigs getConfigManager()
    {
        return configManager;
    }

    public void setConfigManager(MobBountyConfigs configManager)
    {
        this.configManager = configManager;
    }

    public MobBountyEconomy getEconManager()
    {
        return econManager;
    }

    public void setEconManager(MobBountyEconomy econManager)
    {
        this.econManager = econManager;
    }

    public MobBountyLocale getLocaleManager()
    {
        return localeManager;
    }

    public void setLocaleManager(MobBountyLocale localeManager)
    {
        this.localeManager = localeManager;
    }

    public MobBountyCommand getCommandManager()
    {
        return commandManager;
    }

    public void setCommandManager(MobBountyCommand commandManager)
    {
        this.commandManager = commandManager;
    }

    public MobBountyPermission getPermissionManager()
    {
        return permissionManager;
    }

    public void setPermissionManager(MobBountyPermission permissionManager)
    {
        this.permissionManager = permissionManager;
    }

    private void startStatistics()
    {
        try
        {
            MetricsLite metrics = new MetricsLite(this);
            metrics.start();
        }
        catch (IOException e)
        {
        }
    }

}
