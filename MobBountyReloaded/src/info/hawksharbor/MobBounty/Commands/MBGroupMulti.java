package info.hawksharbor.MobBounty.Commands;

import info.hawksharbor.MobBounty.Utils.MobBountyConfFile;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MBGroupMulti implements CommandExecutor
{
	private final info.hawksharbor.MobBounty.MobBountyReloaded _plugin;

	public MBGroupMulti(info.hawksharbor.MobBounty.MobBountyReloaded plugin)
	{
		_plugin = plugin;
	}

	private void commandUsage(CommandSender sender, String command)
	{
		String message = _plugin.getAPIManager().getLocaleManager()
				.getString("MBGMUsage");
		if (message != null)
		{
			message = message.replace("%C", command);
			sender.sendMessage(message);
		}
	}

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args)
	{

		if (_plugin.getAPIManager().getPermissionsManager()
				.hasPermission(sender, "mbr.command.mbgm"))
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

					if (_plugin.getAPIManager().getPermissionsManager()
							.getPermission().getGroups().toString()
							.contains(args[0]))
					{
						_plugin.getAPIManager()
								.getConfigManager()
								.setProperty(MobBountyConfFile.MULTIPLIERS,
										"Group." + args[0], amount);

						String message = _plugin.getAPIManager()
								.getLocaleManager().getString("MBGMChange");
						if (message != null)
						{
							message = message.replace("%W", args[0]).replace(
									"%A", amount.toString());
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
