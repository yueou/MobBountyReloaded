package info.hawksharbor.MobBounty.Commands;

import info.hawksharbor.MobBounty.Utils.MobBountyConfFile;
import info.hawksharbor.MobBounty.Utils.MobBountyCreature;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MBCheckExperience implements CommandExecutor
{
	private final info.hawksharbor.MobBounty.MobBountyReloaded _plugin;

	public MBCheckExperience(info.hawksharbor.MobBounty.MobBountyReloaded plugin)
	{
		_plugin = plugin;
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
				.hasPermission(player, "mbr.command.mbce"))
		{
			World world = player.getWorld();

			for (MobBountyCreature creature : MobBountyCreature.values())
			{
				String rewards = _plugin
						.getAPIManager()
						.getConfigManager()
						.getProperty(MobBountyConfFile.EXPERIENCE,
								world.getName() + "." + creature.getName());
				if (rewards == null || rewards.contains("")
						|| rewards.toString().equalsIgnoreCase("[]"))
				{
					rewards = _plugin
							.getAPIManager()
							.getConfigManager()
							.getProperty(MobBountyConfFile.EXPERIENCE,
									"Default." + creature.getName());
				}

				if (rewards != null && !rewards.equalsIgnoreCase("0"))
				{
					String message = _plugin.getAPIManager().getLocaleManager()
							.getString("MBCheckExp");
					if (message != null)
					{
						message = message.replace("%A", rewards).replace("%M",
								creature.getName());
						player.sendMessage(message);
					}
				}
			}
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
