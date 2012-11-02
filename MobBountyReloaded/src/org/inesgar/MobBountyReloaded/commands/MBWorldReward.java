package org.inesgar.MobBountyReloaded.commands;

import java.util.Iterator;
import java.util.List;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.inesgar.MobBountyReloaded.MobBountyReloaded;
import org.inesgar.MobBountyReloaded.utils.MobBountyCreature;
import org.inesgar.MobBountyReloaded.utils.configuration.MobBountyReloadedConfFile;

public class MBWorldReward implements CommandExecutor
{

	private MobBountyReloaded plugin;

	public MBWorldReward(MobBountyReloaded plugin)
	{
		setPlugin(plugin);
	}

	public MobBountyReloaded getPlugin()
	{
		return plugin;
	}

	public void setPlugin(MobBountyReloaded plugin)
	{
		this.plugin = plugin;
	}

	private void commandUsage(CommandSender sender, String command)
	{
		String message = getPlugin().getLocaleManager().getString("MBWRUsage");
		if (message != null)
		{
			message = getPlugin().getAPI().formatString(message, "", "", "",
					0.0, 0.0, 0.0, command, "",
					"mbr.admin.command.worldreward", "", "", 0, "", "");
			sender.sendMessage(message);
		}

		message = getPlugin().getLocaleManager().getString("MBWRWorlds");
		if (message != null)
		{
			List<World> worlds = getPlugin().getServer().getWorlds();
			Iterator<World> worldIterator = worlds.iterator();

			String worldsStr = "";

			while (worldIterator.hasNext())
			{
				World world = worldIterator.next();

				worldsStr += world.getName();
				worldsStr += " ";
			}

			message = getPlugin().getAPI().formatString(message, "", "",
					worldsStr, 0.0, 0.0, 0.0, "", "",
					"mbr.admin.command.worldreward", "", "", 0, "", "");
			sender.sendMessage(message);
		}

		message = getPlugin().getLocaleManager().getString("MBWRMobs");
		if (message != null)
			sender.sendMessage(message);
	}

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
				"mbr.admin.command.worldreward"))
		{
			if (args.length == 3)
			{
				MobBountyCreature mob = MobBountyCreature.fromName(args[1]);

				if (mob != null)
				{
					if (args[2].matches("[-]?[0-9]+([.][0-9]+)?"))
					{
						Double amount = Double.parseDouble(args[2]);
						getPlugin().getConfigManager().setProperty(
								MobBountyReloadedConfFile.REWARDS,
								args[0] + "." + mob.getName() + ".value",
								amount.toString());

						String message = getPlugin().getLocaleManager()
								.getString("MBRChange");
						if (message != null)
						{
							sender.sendMessage(getPlugin().getAPI()
									.formatString(message, "", mob.getName(),
											"", Double.valueOf(args[2]),
											Double.valueOf(args[2]),
											Double.valueOf(args[2]), "", "",
											"mbr.admin.command.worldreward",
											"", "", 0, "", ""));
						}
					}
					else if (args[2]
							.matches("[-]?[0-9]+([.][0-9]+)?[:][-]?[0-9]+([.][0-9]+)?"))
					{
						getPlugin().getConfigManager().setProperty(
								MobBountyReloadedConfFile.REWARDS,
								args[0] + "." + mob.getName() + ".value",
								args[2]);

						String message = getPlugin().getLocaleManager()
								.getString("MBRChange");
						if (message != null)
						{
							sender.sendMessage(getPlugin().getAPI()
									.formatString(message, "", mob.getName(),
											"", Double.valueOf(args[2]),
											Double.valueOf(args[2]),
											Double.valueOf(args[2]), "", "",
											"mbr.admin.command.worldreward",
											"", "", 0, "", ""));
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
}
