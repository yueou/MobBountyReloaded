package org.inesgar.MobBountyReloaded.commands;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.inesgar.MobBountyReloaded.MobBountyReloaded;
import org.inesgar.MobBountyReloaded.utils.MobBountyCreature;
import org.inesgar.MobBountyReloaded.utils.configuration.MobBountyReloadedConfFile;

public class MBCheck implements CommandExecutor
{

	private MobBountyReloaded plugin;

	public MobBountyReloaded getPlugin()
	{
		return plugin;
	}

	public void setPlugin(MobBountyReloaded plugin)
	{
		this.plugin = plugin;
	}

	public MBCheck(MobBountyReloaded plugin)
	{
		setPlugin(plugin);
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
				"mbr.user.command.check"))
		{
			World world = player.getWorld();

			for (MobBountyCreature creature : MobBountyCreature.values())
			{
				double reward = 0;

				String rewardTest = getPlugin()

				.getConfigManager().getProperty(
						MobBountyReloadedConfFile.REWARDS,
						"Worlds." + world.getName() + "." + creature.getName());

				if (rewardTest == null)
					rewardTest = getPlugin()

					.getConfigManager().getProperty(
							MobBountyReloadedConfFile.REWARDS,
							"Default." + creature.getName());

				if (rewardTest != null)
				{
					if (rewardTest.contains(":"))
					{
						String[] rewardRange = rewardTest.split(":");
						double from = 0;
						double to = 0;

						if (Double.valueOf(rewardRange[0]) > Double
								.valueOf(rewardRange[1]))
						{
							from = Double.valueOf(rewardRange[1]);
							to = Double.valueOf(rewardRange[0]);
						}
						else
						{
							from = Double.valueOf(rewardRange[0]);
							to = Double.valueOf(rewardRange[1]);
						}

						if (getPlugin().getEconManager().getEcon() != null)
						{
							if (from > 0.0)
							{
								String message = getPlugin().getLocaleManager()
										.getString("MBRewardRange");
								if (message != null)
								{
									message = getPlugin().getAPI()
											.formatString(message,
													player.getName(),
													creature.getName(),
													world.getName(),
													(from + to) / 2, from, to,
													"", "",
													"mbr.user.command.check",
													"", "", 0, "", "");
									player.sendMessage(message);
								}
							}
							else if (from < 0.0)
							{
								String message = getPlugin().getLocaleManager()
										.getString("MBFineRange");
								if (message != null)
								{
									message = getPlugin().getAPI()
											.formatString(message,
													player.getName(),
													creature.getName(),
													world.getName(),
													(from + to) / 2, from, to,
													"", "",
													"mbr.user.command.check",
													"", "", 0, "", "");
									player.sendMessage(message);
								}
							}
						}
					}
					else
					{
						reward = Double.valueOf(rewardTest);

						if (getPlugin().getEconManager().getEcon() != null)
						{
							if (reward > 0.0)
							{
								String message = getPlugin().getLocaleManager()
										.getString("MBReward");
								if (message != null)
								{
									message = getPlugin().getAPI()
											.formatString(message,
													player.getName(),
													creature.getName(),
													world.getName(), reward,
													reward, reward, "", "",
													"mbr.user.command.check",
													"", "", 0, "", "");
									player.sendMessage(message);
								}
							}
							else if (reward < 0.0)
							{
								String message = getPlugin().getLocaleManager()
										.getString("MBFine");
								if (message != null)
								{
									message = getPlugin().getAPI()
											.formatString(message,
													player.getName(),
													creature.getName(),
													world.getName(), reward,
													reward, reward, "", "",
													"mbr.user.command.check",
													"", "", 0, "", "");
									player.sendMessage(message);
								}
							}
						}
					}
				}
			}
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
