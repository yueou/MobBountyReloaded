package info.hawksharbor.MobBounty.Commands;

import info.hawksharbor.MobBounty.Utils.MobBountyConfFile;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MBEnvMulti implements CommandExecutor
{
	private final info.hawksharbor.MobBounty.MobBountyReloaded _plugin;

	public MBEnvMulti(info.hawksharbor.MobBounty.MobBountyReloaded plugin)
	{
		_plugin = plugin;
	}

	private void commandUsage(CommandSender sender, String command)
	{
		String message = _plugin.getAPIManager().getLocaleManager()
				.getString("MBEMUsage");
		if (message != null)
		{
			message = message.replace("%C", command);
			sender.sendMessage(message);
		}

		message = _plugin.getAPIManager().getLocaleManager()
				.getString("MBEMEnvs");
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
				.hasPermission(player, "mbr.command.mbem"))
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

					if (args[0].equalsIgnoreCase("end"))
					{
						_plugin.getAPIManager()
								.getConfigManager()
								.setProperty(MobBountyConfFile.MULTIPLIERS,
										"Environment.End", amount);
						String message = _plugin.getAPIManager()
								.getLocaleManager().getString("MBEMChange");
						if (message != null)
						{
							message = message.replace("%E", "end").replace(
									"%A", amount.toString());
							sender.sendMessage(message);
						}
					}
					else if (args[0].equalsIgnoreCase("nether"))
					{
						_plugin.getAPIManager()
								.getConfigManager()
								.setProperty(MobBountyConfFile.MULTIPLIERS,
										"Environment.Nether", amount);

						String message = _plugin.getAPIManager()
								.getLocaleManager().getString("MBEMChange");
						if (message != null)
						{
							message = message.replace("%E", "nether").replace(
									"%A", amount.toString());
							sender.sendMessage(message);
						}
					}
					else if (args[0].equalsIgnoreCase("normal"))
					{
						_plugin.getAPIManager()
								.getConfigManager()
								.setProperty(MobBountyConfFile.MULTIPLIERS,
										"Environment.Normal", amount);

						String message = _plugin.getAPIManager()
								.getLocaleManager().getString("MBEMChange");
						if (message != null)
						{
							message = message.replace("%E", "normal").replace(
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
