package info.hawksharbor.MobBountyReloadedCore.MobBounty.Commands;

import info.hawksharbor.MobBountyReloadedCore.MobBounty.Utils.MobBountyConfFile;
import info.hawksharbor.MobBountyReloadedCore.MobBounty.Utils.MobBountyCreature;

import java.util.List;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MBCheckDrop implements CommandExecutor
{
	private final info.hawksharbor.MobBountyReloadedCore.MobBounty.MobBountyReloaded _plugin;

	public MBCheckDrop(info.hawksharbor.MobBountyReloadedCore.MobBounty.MobBountyReloaded plugin)
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
				.hasPermission(player, "mbr.command.mbcd"))
		{
			World world = player.getWorld();

			for (MobBountyCreature creature : MobBountyCreature.values())
			{
				if (creature.equals(MobBountyCreature.PLAYER))
					continue;
				List<String> rewards = _plugin
						.getAPIManager()
						.getConfigManager()
						.getPropertyList(MobBountyConfFile.DROPS,
								world.getName() + "." + creature.getName());
				if (rewards == null || rewards.contains("")
						|| rewards.toString().equalsIgnoreCase("[]"))
				{
					rewards = _plugin
							.getAPIManager()
							.getConfigManager()
							.getPropertyList(MobBountyConfFile.DROPS,
									"Default." + creature.getName());
				}

				String rewardTest = rewards.toString().replace("[", "")
						.replace("]", "");
				if (rewardTest != null && !rewardTest.contains("0:0x0")
						&& !rewardTest.equalsIgnoreCase("normal"))
				{
					String message = _plugin.getAPIManager().getLocaleManager()
							.getString("MBCheckDrop");
					if (message != null)
					{
						message = message.replace("%A", rewardTest).replace(
								"%M", creature.getName());
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
