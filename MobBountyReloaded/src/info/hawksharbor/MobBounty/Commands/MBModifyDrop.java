package info.hawksharbor.MobBounty.Commands;

import info.hawksharbor.MobBounty.Utils.MobBountyConfFile;
import info.hawksharbor.MobBounty.Utils.MobBountyCreature;

import java.util.Iterator;
import java.util.List;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MBModifyDrop implements CommandExecutor
{
	private final info.hawksharbor.MobBounty.MobBountyReloaded _plugin;

	public MBModifyDrop(info.hawksharbor.MobBounty.MobBountyReloaded plugin)
	{
		_plugin = plugin;
	}

	private void commandUsage(CommandSender sender, String command)
	{
		String message = _plugin.getAPIManager().getLocaleManager()
				.getString("MBMDUsage");
		if (message != null)
		{
			message = message.replace("%C", command);
			sender.sendMessage(message);
		}

		message = _plugin.getAPIManager().getLocaleManager()
				.getString("MBMDWorlds");
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
				.getString("MBMDMobs");
		if (message != null)
			sender.sendMessage(message);
	}

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args)
	{
		if (_plugin.getAPIManager().getPermissionsManager()
				.hasPermission(sender, "mbr.command.mbd"))
		{
			if (args.length > 1)
			{
				MobBountyCreature mob = MobBountyCreature.fromName(args[0]);

				if (mob != null)
				{
					List<String> mobDrops = _plugin
							.getAPIManager()
							.getConfigManager()
							.getPropertyList(MobBountyConfFile.DROPS,
									"Default." + mob.getName());
					mobDrops.clear();
					for (String s : args)
					{
						if (!s.equalsIgnoreCase(args[0]))
							mobDrops.add(s);
					}
					_plugin.getAPIManager()
							.getConfigManager()
							.setPropertyList(MobBountyConfFile.DROPS,
									"Default." + mob.getName(), mobDrops);

					String message = _plugin.getAPIManager().getLocaleManager()
							.getString("MBMDChange");
					if (message != null)
					{
						message = message.replace("%M", mob.getName()).replace(
								"%A",
								mobDrops.toString().replace("[", "")
										.replace("]", ""));
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
		{
			String message = _plugin.getAPIManager().getLocaleManager()
					.getString("NoAccess");
			if (message != null)
				sender.sendMessage(message);
		}

		return true;
	}
}
