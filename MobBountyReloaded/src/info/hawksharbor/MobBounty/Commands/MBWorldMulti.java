package info.hawksharbor.MobBounty.Commands;

import info.hawksharbor.MobBounty.Utils.MobBountyConfFile;

import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MBWorldMulti implements CommandExecutor
{
	private final info.hawksharbor.MobBounty.MobBountyReloaded _plugin;

	public MBWorldMulti(info.hawksharbor.MobBounty.MobBountyReloaded plugin)
	{
		_plugin = plugin;
	}

	private void commandUsage(CommandSender sender, String command)
	{
		String message = _plugin.getAPIManager().getLocaleManager()
				.getString("MBWMUsage");
		if (message != null)
		{
			message = message.replace("%C", command);
			sender.sendMessage(message);
		}

		message = _plugin.getAPIManager().getLocaleManager()
				.getString("MBWMWorlds");
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
	}

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args)
	{

		if (_plugin.getAPIManager().getPermissionsManager()
				.hasPermission(sender, "mbr.command.mbwm"))
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

					World world = Bukkit.getServer().getWorld(args[0]);

					if (world != null)
					{
						_plugin.getAPIManager()
								.getConfigManager()
								.setProperty(MobBountyConfFile.MULTIPLIERS,
										"World." + world.getName(), amount);

						String message = _plugin.getAPIManager()
								.getLocaleManager().getString("MBWMChange");
						if (message != null)
						{
							message = message.replace("%W", world.getName())
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
