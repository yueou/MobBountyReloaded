package info.hawksharbor.MobBountyReloadedCore.MobBounty.Commands;

import info.hawksharbor.MobBountyReloadedCore.MobBounty.Utils.MobBountyConfFile;
import info.hawksharbor.MobBountyReloadedCore.MobBounty.Utils.MobBountyCreature;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class MobBounty implements CommandExecutor
{
	private final info.hawksharbor.MobBountyReloadedCore.MobBounty.MobBountyReloaded _plugin;

	public MobBounty(info.hawksharbor.MobBountyReloadedCore.MobBounty.MobBountyReloaded plugin)
	{
		_plugin = plugin;
	}

	@EventHandler
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
				.hasPermission(player, "mbr.command.mb"))
		{
			World world = player.getWorld();

			for (MobBountyCreature creature : MobBountyCreature.values())
			{
				double reward = 0;

				String rewardTest = _plugin
						.getAPIManager()
						.getConfigManager()
						.getProperty(
								MobBountyConfFile.REWARDS,
								"Worlds." + world.getName() + "."
										+ creature.getName());

				if (rewardTest == null)
					rewardTest = _plugin
							.getAPIManager()
							.getConfigManager()
							.getProperty(MobBountyConfFile.REWARDS,
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

						if (_plugin.getAPIManager().getEconManager()
								.getEconomy() != null)
						{
							if (from > 0.0)
							{
								String message = _plugin.getAPIManager()
										.getLocaleManager()
										.getString("MBRewardRange");
								if (message != null)
								{
									message = message
											.replace(
													"%1",
													_plugin.getAPIManager()
															.getEconManager()
															.getEconomy()
															.format(from))
											.replace(
													"%2",
													_plugin.getAPIManager()
															.getEconManager()
															.getEconomy()
															.format(to))
											.replace("%M", creature.getName());
									player.sendMessage(message);
								}
							}
							else if (from < 0.0)
							{
								String message = _plugin.getAPIManager()
										.getLocaleManager()
										.getString("MBFineRange");
								if (message != null)
								{
									message = message
											.replace(
													"%1",
													_plugin.getAPIManager()
															.getEconManager()
															.getEconomy()
															.format(from))
											.replace(
													"%2",
													_plugin.getAPIManager()
															.getEconManager()
															.getEconomy()
															.format(to))
											.replace("%M", creature.getName());
									player.sendMessage(message);
								}
							}
						}
					}
					else
					{
						reward = Double.valueOf(rewardTest);

						if (_plugin.getAPIManager().getEconManager()
								.getEconomy() != null)
						{
							if (reward > 0.0)
							{
								String message = _plugin.getAPIManager()
										.getLocaleManager()
										.getString("MBReward");
								if (message != null)
								{
									message = message.replace(
											"%A",
											_plugin.getAPIManager()
													.getEconManager()
													.getEconomy()
													.format(reward)).replace(
											"%M", creature.getName());
									player.sendMessage(message);
								}
							}
							else if (reward < 0.0)
							{
								String message = _plugin.getAPIManager()
										.getLocaleManager().getString("MBFine");
								if (message != null)
								{
									message = message.replace(
											"%A",
											_plugin.getAPIManager()
													.getEconManager()
													.getEconomy()
													.format(reward)).replace(
											"%M", creature.getName());
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
			String message = _plugin.getAPIManager().getLocaleManager()
					.getString("NoAccess");
			if (message != null)
				sender.sendMessage(message);
		}

		return true;
	}
}
