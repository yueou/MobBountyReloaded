package info.hawksharbor.MobBounty.Commands;

import info.hawksharbor.MobBounty.Utils.MobBountyConfFile;
import info.hawksharbor.MobBounty.Utils.MobBountyCreature;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MBExperience implements CommandExecutor
{
	private final info.hawksharbor.MobBounty.MobBountyReloaded _plugin;

	public MBExperience(info.hawksharbor.MobBounty.MobBountyReloaded plugin)
	{
		_plugin = plugin;
	}

	private void commandUsage(CommandSender sender, String command)
	{
		String message = _plugin.getAPIManager().getLocaleManager()
				.getString("MBEUsage");
		if (message != null)
		{
			message = message.replace("%C", command);
			sender.sendMessage(message);
		}

		message = _plugin.getAPIManager().getLocaleManager()
				.getString("MBEMobs");
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
				.hasPermission(player, "mbr.command.mbe"))
		{
			if (args.length == 2)
			{
				MobBountyCreature mob = MobBountyCreature.fromName(args[0]);

				if (mob != null)
				{
					if (args[1].matches("[-]?[0-9]+([.][0-9]+)?"))
					{
						Integer amount = Integer.parseInt(args[1]);
						_plugin.getAPIManager()
								.getConfigManager()
								.setProperty(MobBountyConfFile.EXPERIENCE,
										"Default." + mob.getName(),
										amount.toString());

						String message = _plugin.getAPIManager()
								.getLocaleManager().getString("MBEChange");
						if (message != null)
						{
							message = message.replace("%M", mob.getName())
									.replace("%A", amount.toString());
							sender.sendMessage(message);
						}
					}
					else if (args[1]
							.matches("[-]?[0-9]+([.][0-9]+)?[:][-]?[0-9]+([.][0-9]+)?"))
					{
						_plugin.getAPIManager()
								.getConfigManager()
								.setProperty(MobBountyConfFile.REWARDS,
										"Default." + mob.getName(), args[1]);

						String message = _plugin.getAPIManager()
								.getLocaleManager().getString("MBEChange");
						if (message != null)
						{
							message = message.replace("%M", mob.getName())
									.replace("%A", args[1]);
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
