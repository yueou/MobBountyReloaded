package com.conventnunnery.MobBountyReloaded.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.conventnunnery.MobBountyReloaded.MobBountyReloaded;

public class MBLoad implements CommandExecutor
{

    private MobBountyReloaded plugin;

    public MBLoad(final MobBountyReloaded plugin)
    {
        setPlugin(plugin);
    }

    public MobBountyReloaded getPlugin()
    {
        return plugin;
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command command,
            final String cmdLbl, final String[] args)
    {
        String message;
        if (!getPlugin().getPermissionManager().hasPermission(sender,
                "mbr.admin.command.load"))
        {
            message = getPlugin().getAPI().formatString(
                    getPlugin().getLocaleManager().getString("NoAccess"), "",
                    "", "", 0.0, 0.0, 0.0, "", "", "mbr.admin.command.load",
                    "", "", 0, "", "", 0);
            if (message != null)
            {
                sender.sendMessage(message);
            }
            return true;
        }
        getPlugin().getConfigManager().loadConfig();
        message = getPlugin().getAPI().formatString(
                getPlugin().getLocaleManager().getString("MBLLoaded"), "", "",
                "", 0.0, 0.0, 0.0, "", "", "mbr.admin.command.load", "", "", 0,
                "", "", 0);
        if (message != null)
        {
            sender.sendMessage(message);
        }
        return true;
    }

    public void setPlugin(final MobBountyReloaded plugin)
    {
        this.plugin = plugin;
    }

}
