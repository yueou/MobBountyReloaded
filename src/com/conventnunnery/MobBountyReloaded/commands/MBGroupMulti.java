package com.conventnunnery.MobBountyReloaded.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.conventnunnery.MobBountyReloaded.MobBountyReloaded;
import com.conventnunnery.MobBountyReloaded.utils.configuration.MobBountyReloadedConfFile;

public class MBGroupMulti implements CommandExecutor
{
    private MobBountyReloaded _plugin;

    public MBGroupMulti(MobBountyReloaded plugin)
    {
        setPlugin(plugin);
    }

    private void commandUsage(CommandSender sender, String command)
    {
        String message = getPlugin().getLocaleManager().getString("MBGMUsage");
        if (message != null)
        {
            message = message.replace("%C", command);
            sender.sendMessage(message);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command,
            String label, String[] args)
    {
        if (!(sender instanceof Player))
        {
            sender.sendMessage("Commands are designed to be run by players only.");
            return true;
        }
        Player player = ((Player) sender);
        if (getPlugin().getPermissionManager().hasPermission(player,
                "mbr.admin.command.mbgm"))
        {
            if (args.length == 2)
            {
                if (args[1].matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+"))
                {
                    Double amount;
                    try
                    {
                        amount = Double.parseDouble(args[1]);
                    }
                    catch (NumberFormatException e)
                    {
                        amount = 1.0;
                    }

                    if (getPlugin().getPermissionManager().getPermissions()
                            .getGroups().toString().contains(args[0]))
                    {
                        getPlugin().getConfigManager().setProperty(
                                MobBountyReloadedConfFile.MULTIPLIERS,
                                "Group." + args[0], amount);

                        String message = getPlugin().getLocaleManager()
                                .getString("MBGMChange");
                        if (message != null)
                        {
                            message = getPlugin().getAPI().formatString(
                                    message, player.getName(), "", args[0],
                                    amount, amount, amount, "", "", "", "", "",
                                    0, "", "", 0);
                            sender.sendMessage(message);
                        }
                    }
                    else
                        this.commandUsage(sender, label);
                }
                else
                    this.commandUsage(sender, label);
            }
            else
                this.commandUsage(sender, label);
        }
        else
        {
            String message = getPlugin().getLocaleManager().getString(
                    "NoAccess");
            if (message != null)
                sender.sendMessage(message);
        }

        return true;
    }

    public MobBountyReloaded getPlugin()
    {
        return _plugin;
    }

    public void setPlugin(MobBountyReloaded _plugin)
    {
        this._plugin = _plugin;
    }
}
