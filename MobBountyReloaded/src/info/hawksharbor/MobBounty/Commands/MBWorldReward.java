package info.hawksharbor.MobBounty.Commands;

import info.hawksharbor.MobBounty.Utils.MobBountyConfFile;
import info.hawksharbor.MobBounty.Utils.MobBountyCreature;

import java.util.Iterator;
import java.util.List;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MBWorldReward implements CommandExecutor
{
	private final info.hawksharbor.MobBounty.MobBountyReloaded _plugin;

	public MBWorldReward(info.hawksharbor.MobBounty.MobBountyReloaded plugin)
	{
		_plugin = plugin;
	}

	private void commandUsage(CommandSender sender, String command)
	{
		String message = _plugin.getAPIManager().getLocaleManager()
				.getString("MBWRUsage");
		if (message != null)
		{
			message = message.replace("%C", command);
			sender.sendMessage(message);
		}

		message = _plugin.getAPIManager().getLocaleManager()
				.getString("MBWRWorlds");
		if (message != null)
		{
			List<World> worlds = _plugin.getServer().getWorlds();
			Iterator<World> worldIterator = worlds.iterator();

			String worldsStr = "";

			while (worldIterator.hasNext())
			{
				World world = worldIterator.next();

				worldsStr += world.getName();
				worldsStr += " ";
			}

			message = message.replace("%W", worldsStr);
			sender.sendMessage(message);
		}

		message = _plugin.getAPIManager().getLocaleManager()
				.getString("MBWRMobs");
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
		if (_plugin.getAPIManager().getPermissionsManager()
				.hasPermission(player, "mbr.command.mbwr"))
		{
			if (args.length == 3)
			{
				if (args[2].matches("[-]?[0-9]+([.][0-9]+)?"))
				{
					World world = _plugin.getServer().getWorld(args[0]);

					if (world != null)
					{
						MobBountyCreature mob = MobBountyCreature
								.fromName(args[1]);

						if (mob != null)
						{
							Double amount = Double.parseDouble(args[2]);

							_plugin.getAPIManager()
									.getConfigManager()
									.setProperty(
											MobBountyConfFile.REWARDS,
											"Worlds." + world.getName() + "."
													+ mob.getName(),
											amount.toString());

							String message = _plugin.getAPIManager()
									.getLocaleManager().getString("MBWRChange");
							if (message != null)
							{
								message = message.replace("%M", mob.getName())
										.replace("%W", world.getName())
										.replace("%A", amount.toString());
								sender.sendMessage(message);
							}
						}
						else
							this.commandUsage(sender, label);
					}
					else
						this.commandUsage(sender, label);
				}
				else if (args[2]
						.matches("[-]?[0-9]+([.][0-9]+)?[:][-]?[0-9]+([.][0-9]+)?"))
				{
					World world = _plugin.getServer().getWorld(args[0]);

					if (world != null)
					{
						MobBountyCreature mob = MobBountyCreature
								.fromName(args[1]);

						if (mob != null)
						{
							_plugin.getAPIManager()
									.getConfigManager()
									.setProperty(
											MobBountyConfFile.REWARDS,
											"Worlds." + world.getName() + "."
													+ mob.getName(), args[2]);

							String message = _plugin.getAPIManager()
									.getLocaleManager().getString("MBWRChange");
							if (message != null)
							{
								message = message.replace("%M", mob.getName())
										.replace("%W", world.getName())
										.replace("%A", args[2]);
								sender.sendMessage(message);
							}

						}
					}
				}
				else if (args[2].equalsIgnoreCase("default"))
				{
					World world = _plugin.getServer().getWorld(args[0]);

					if (world != null)
					{
						MobBountyCreature mob = MobBountyCreature
								.fromName(args[1]);

						if (mob != null)
						{
							_plugin.getAPIManager()
									.getConfigManager()
									.removeProperty(
											MobBountyConfFile.REWARDS,
											"Worlds." + world.getName() + "."
													+ mob.getName());

							String message = _plugin.getAPIManager()
									.getLocaleManager().getString("MBWRReset");
							if (message != null)
							{
								message = message.replace("%M", mob.getName())
										.replace("%W", world.getName());
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
				this.commandUsage(sender, label);
		}
		else
		{
			String message = _plugin.getAPIManager().getLocaleManager()
					.getString("NoAccess");
			if (message != null)
				sender.sendMessage(message);
		}

		return true;
	}
}
