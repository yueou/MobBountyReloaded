package org.inesgar.MobBountyReloaded;

import org.bukkit.plugin.java.JavaPlugin;
import org.inesgar.MobBountyReloaded.listeners.MobBountyReloadedEntityListener;
import org.inesgar.MobBountyReloaded.managers.MobBountyCommand;
import org.inesgar.MobBountyReloaded.managers.MobBountyConfigs;
import org.inesgar.MobBountyReloaded.managers.MobBountyEconomy;
import org.inesgar.MobBountyReloaded.managers.MobBountyLocale;
import org.inesgar.MobBountyReloaded.managers.MobBountyPermission;

public class MobBountyReloaded extends JavaPlugin
{

    public static MobBountyReloaded getInstance()
    {
        return instance;
    }

    public static void setInstance(final MobBountyReloaded instance)
    {
        MobBountyReloaded.instance = instance;
    }

    private MobBountyAPI api;
    private static MobBountyReloaded instance;
    private MobBountyConfigs configManager;
    private MobBountyEconomy econManager;
    private MobBountyLocale localeManager;

    private MobBountyCommand commandManager;

    private MobBountyPermission permissionManager;

    public MobBountyAPI getAPI()
    {
        return api;
    }

    public MobBountyCommand getCommandManager()
    {
        return commandManager;
    }

    public MobBountyConfigs getConfigManager()
    {
        return configManager;
    }

    public MobBountyEconomy getEconManager()
    {
        return econManager;
    }

    public MobBountyLocale getLocaleManager()
    {
        return localeManager;
    }

    public MobBountyPermission getPermissionManager()
    {
        return permissionManager;
    }

    @Override
    public void onDisable()
    {
        getAPI().log("Disabled.");
    }

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
    }

    public void setAPI(final MobBountyAPI api)
    {
        this.api = api;
    }

    public void setCommandManager(final MobBountyCommand commandManager)
    {
        this.commandManager = commandManager;
    }

    public void setConfigManager(final MobBountyConfigs configManager)
    {
        this.configManager = configManager;
    }

    public void setEconManager(final MobBountyEconomy econManager)
    {
        this.econManager = econManager;
    }

    public void setLocaleManager(final MobBountyLocale localeManager)
    {
        this.localeManager = localeManager;
    }

    public void setPermissionManager(final MobBountyPermission permissionManager)
    {
        this.permissionManager = permissionManager;
    }

}
