package org.inesgar.MobBountyReloaded.managers;

import net.milkbowl.vault.permission.Permission;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.inesgar.MobBountyReloaded.MobBountyReloaded;
import org.inesgar.MobBountyReloaded.utils.configuration.MobBountyReloadedConfFile;

public class MobBountyPermission
{

    private MobBountyReloaded plugin;
    private Permission perms;

    public MobBountyPermission(MobBountyReloaded plugin)
    {
        setPlugin(plugin);
        setupPermissions();
    }

    public MobBountyReloaded getPlugin()
    {
        return plugin;
    }

    public void setPlugin(MobBountyReloaded plugin)
    {
        this.plugin = plugin;
    }

    public Permission getPermissions()
    {
        return perms;
    }

    public void setPermissions(Permission perms)
    {
        this.perms = perms;
    }

    private boolean setupPermissions()
    {
        RegisteredServiceProvider<Permission> rsp = getPlugin().getServer()
                .getServicesManager().getRegistration(Permission.class);
        setPermissions(rsp.getProvider());
        return getPermissions() != null;
    }

    public boolean hasPermission(CommandSender sender, String node)
    {
        String has = getPlugin().getLocaleManager().getString(
                "DebugHasPermission");
        String lacks = getPlugin().getLocaleManager().getString(
                "DebugLacksPermission");
        String debugMode = getPlugin().getConfigManager().getProperty(
                MobBountyReloadedConfFile.GENERAL, "debugMode");
        if (debugMode == null)
            debugMode = "false";
        boolean debug = Boolean.parseBoolean(debugMode);
        if (sender.hasPermission(node))
        {
            if (debug && sender.hasPermission("mbr.admin.debug"))
            {
                if (has != null)
                    sender.sendMessage(getPlugin().getAPI().formatString(has,
                            sender.getName(), "", "", "", "", "", "", "", node,
                            "", "", "", "", "", ""));
            }
            return true;
        }
        if (debug && sender.hasPermission("mbr.admin.debug"))
        {
            if (lacks != null)
                sender.sendMessage(getPlugin().getAPI().formatString(lacks,
                        sender.getName(), "", "", "", "", "", "", "", node, "",
                        "", "", "", "", ""));
        }
        return false;
    }
}
