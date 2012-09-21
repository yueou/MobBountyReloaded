package info.hawksharbor.MobBountyReloadedCore.MobBounty.Managers;

import info.hawksharbor.MobBountyReloadedCore.MobBounty.MobBountyReloaded;
import info.hawksharbor.MobBountyReloadedCore.MobBounty.Event.MobBountyPaymentEvent;
import info.hawksharbor.MobBountyReloadedCore.MobBounty.Utils.MobBountyAPI;
import info.hawksharbor.MobBountyReloadedCore.MobBounty.Utils.MobBountyConfFile;
import info.hawksharbor.MobBountyReloadedCore.MobBounty.Utils.MobBountyCreature;
import info.hawksharbor.MobBountyReloadedCore.MobBounty.Utils.MobBountyMessage;
import info.hawksharbor.MobBountyReloadedCore.MobBounty.Utils.MobBountyPlayerKillData;
import info.hawksharbor.MobBountyReloadedCore.MobBounty.Utils.MobBountyUtils;
import info.hawksharbor.MobBountyReloadedCore.MobBounty.Utils.External.TownyUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.gmail.nossr50.mcMMO;
import com.gmail.nossr50.party.Party;
import com.herocraftonline.heroes.Heroes;
import com.herocraftonline.heroes.characters.Hero;
import com.herocraftonline.heroes.characters.party.HeroParty;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;

public class MobBountyEcon
{

	public static Map<String, MobBountyPlayerKillData> _playerData;
	private static MobBountyReloaded _plugin;

	private static Economy econ = null;

	public static boolean fineAccount(String accountName, double amount)
	{
		double absAmount = Math.abs(amount);
		EconomyResponse r = econ.withdrawPlayer(accountName, absAmount);
		if (!r.transactionSuccess())
		{
			MobBountyMessage
					.logSevereToConsole("Unable to fine " + accountName);
			return false;
		}
		return true;
	}

	private static boolean finePlayer(Player player, double amount,
			MobBountyCreature creature)
	{
		double absAmount = Math.abs(amount);
		EconomyResponse r = econ.withdrawPlayer(player.getName(), absAmount);
		if (!r.transactionSuccess())
		{
			MobBountyMessage.logSevereToConsole("Unable to fine "
					+ player.getName());
			return false;
		}
		String useKillCache = MobBountyAPI.instance.getConfigManager()
				.getProperty(MobBountyConfFile.GENERAL, "killCache.use");
		if (useKillCache != null && useKillCache.equalsIgnoreCase("true"))
		{
			MobBountyPlayerKillData killData = _playerData
					.get(player.getName());
			if (killData == null)
				killData = new MobBountyPlayerKillData();
			handleKillCache(player, killData, amount);
		}
		else
		{
			if (MobBountyAPI.instance.getExternalsManager().getSpout() != null
					&& ((SpoutPlayer) player) != null
					&& MobBountyAPI.instance.getExternalsManager().useSpout())
			{
				String message = MobBountyAPI.instance.getLocaleManager()
						.getString("SpoutFined");
				if (message != null)
				{
					message = message.replace("%A", format(Math.abs(r.amount)));
					((SpoutPlayer) player)
							.sendNotification(_plugin.getDescription()
									.getName(), message, Material.BONE);
				}
			}
			String message = MobBountyUtils.getPluginMessage("Fined");
			if (message != null)
			{
				message = message.replace("%M",
						creature.toString().toLowerCase()).replace("%A",
						format(absAmount));
				MobBountyMessage.sendMessage(player, message);
			}
		}
		return true;
	}

	private static boolean finePlayerParty(Player player, double amount,
			Player killer, MobBountyCreature creature)
	{
		double absAmount = Math.abs(amount);
		EconomyResponse r = econ.withdrawPlayer(player.getName(), absAmount);
		if (!r.transactionSuccess())
		{
			MobBountyMessage.logSevereToConsole("Unable to fine "
					+ player.getName());
			return false;
		}
		String useKillCache = MobBountyAPI.instance.getConfigManager()
				.getProperty(MobBountyConfFile.GENERAL, "killCache.use");
		if (useKillCache != null && useKillCache.equalsIgnoreCase("true"))
		{
			MobBountyPlayerKillData killData = _playerData
					.get(player.getName());
			if (killData == null)
				killData = new MobBountyPlayerKillData();
			handleKillCache(player, killData, amount);
		}
		else
		{
			if (MobBountyAPI.instance.getExternalsManager().getSpout() != null
					&& ((SpoutPlayer) player) != null
					&& MobBountyAPI.instance.getExternalsManager().useSpout())
			{
				String message = MobBountyAPI.instance.getLocaleManager()
						.getString("SpoutAwarded");
				if (message != null)
				{
					message = message.replace("%A", format(Math.abs(r.amount)));
					((SpoutPlayer) player)
							.sendNotification(_plugin.getDescription()
									.getName(), message, Material.BONE);
				}
			}
			String message = MobBountyUtils.getPluginMessage("PartyFined");
			if (message != null)
			{
				message = message.replace("%M",
						creature.toString().toLowerCase()).replace("%A",
						format(absAmount).replace("%P", killer.getName()));
				MobBountyMessage.sendMessage(player, message);
			}
		}
		return true;
	}

	private static String format(double amount)
	{
		return econ.format(amount);
	}

	private static double getBaseReward(Player player,
			MobBountyCreature creature)
	{
		double baseReward = 0.0;
		String resultTest = MobBountyAPI.instance.getConfigManager()
				.getProperty(
						MobBountyConfFile.REWARDS,
						"Worlds." + player.getWorld().getName() + "."
								+ creature.getName());

		if (resultTest == null)
		{
			resultTest = MobBountyAPI.instance.getConfigManager().getProperty(
					MobBountyConfFile.REWARDS, "Default." + creature.getName());
		}

		if (resultTest != null)
		{
			if (resultTest.contains(":"))
			{
				String[] resultRange = resultTest.split(":");

				Random rand = new Random();
				int range;
				int loc;

				if (Double.valueOf(resultRange[0]) > Double
						.valueOf(resultRange[1]))
				{
					range = (int) ((Double.valueOf(resultRange[0]) * 100) - (Double
							.valueOf(resultRange[1]) * 100));
					loc = (int) (Double.valueOf(resultRange[1]) * 100);
				}
				else
				{
					range = (int) ((Double.valueOf(resultRange[1]) * 100) - (Double
							.valueOf(resultRange[0]) * 100));
					loc = (int) (Double.valueOf(resultRange[0]) * 100);
				}

				baseReward = (((double) (loc + rand.nextInt(range + 1))) / 100);
			}
			else
			{
				baseReward = Double.valueOf(resultTest);
			}
		}
		return baseReward;
	}

	private static double handleDepreciativeReturn(Player player,
			MobBountyCreature creature, double amount)
	{
		double result = amount;
		String booleanTest;
		booleanTest = MobBountyAPI.instance.getConfigManager().getProperty(
				MobBountyConfFile.GENERAL, "useDepreciativeReturn");
		boolean useDeprReturn = Boolean.valueOf(booleanTest);
		if (!useDeprReturn)
			return result;
		MobBountyPlayerKillData playerData = _playerData.get(player.getName());
		if (playerData == null)
		{
			playerData = new MobBountyPlayerKillData();
			playerData.lastKill = creature;
			_playerData.put(player.getName(), playerData);
			return result;
		}
		if (playerData.lastKill != null && playerData.lastKill.equals(creature))
		{
			double originalReward = result;
			String returnRate = MobBountyAPI.instance.getConfigManager()
					.getProperty(MobBountyConfFile.GENERAL,
							"depreciativeReturnRate");
			playerData.lastRewardPercentage = playerData.lastRewardPercentage -= Double
					.valueOf(returnRate);
			double reward = result * playerData.lastRewardPercentage;
			MobBountyMessage.logToConsole("result: " + String.valueOf(reward));
			if (reward <= 0.0 && originalReward >= 0.0)
			{
				reward = 0.0;
			}
			else if (reward >= 0.0 && originalReward <= 0.0)
			{
				reward = 0.0;
			}
			playerData.lastKill = creature;
			_playerData.put(player.getName(), playerData);
			return reward;
		}
		playerData.lastRewardPercentage = 1;
		playerData.lastKill = creature;
		_playerData.put(player.getName(), playerData);
		return result;
	}

	private static double handleFactionEarning(Player killer, double base)
	{
		double baseReward = base;
		if (MobBountyAPI.instance.getExternalsManager().getFactions() != null
				&& base > 0.0)
		{
			FPlayer fPlayer = FPlayers.i.get(killer);
			if (fPlayer.hasFaction())
			{
				double bR = baseReward;
				String cFS = MobBountyAPI.instance.getConfigManager()
						.getProperty(MobBountyConfFile.FACTIONS,
								"autoDepositInFactionRate");
				double cF = MobBountyUtils.getDouble(cFS, 0.0);
				if (cF > 1.0)
				{
					cF = 1.0;
					MobBountyAPI.instance.getConfigManager().setProperty(
							MobBountyConfFile.FACTIONS,
							"autoDepositInFactionRate", cF);
				}
				else if (cF < 0.0)
				{
					cF = 0.0;
					MobBountyAPI.instance.getConfigManager().setProperty(
							MobBountyConfFile.FACTIONS,
							"autoDepositInFactionRate", cF);
				}
				double portionF = bR *= cF;
				monetaryTransactionAccount(fPlayer.getFaction().getAccountId(),
						portionF);
				return (baseReward - portionF);
			}
			return baseReward;
		}
		return baseReward;
	}

	private static void handleKillCache(Player killer,
			MobBountyPlayerKillData playerData, double amount)
	{
		playerData.cacheSize++;
		if (amount >= 0.0)
			playerData.cacheEarned += amount;
		else
			playerData.cacheEarned -= amount;
		int timeLimit = MobBountyUtils.getInt(
				MobBountyAPI.instance.getConfigManager().getProperty(
						MobBountyConfFile.GENERAL, "killCache.timeLimit"),
				30000);
		if (System.currentTimeMillis() - playerData.cacheTime >= timeLimit)
		{
			if (playerData.cacheEarned > 0.0)
			{
				if (MobBountyAPI.instance.getExternalsManager().getSpout() != null
						&& ((SpoutPlayer) killer) != null)
				{
					String message = MobBountyAPI.instance.getLocaleManager()
							.getString("SpoutAwarded");
					if (message != null)
					{
						message = message.replace("%A",
								format(Math.abs(playerData.cacheEarned)));
						((SpoutPlayer) killer).sendNotification(_plugin
								.getDescription().getName(), message,
								Material.BONE);
					}
				}
				else
				{
					String message = MobBountyUtils
							.getPluginMessage("CacheAwarded");
					if (message != null)
					{
						message = message
								.replace("%K",
										String.valueOf(playerData.cacheSize))
								.replace(
										"%A",
										format(Math.abs(playerData.cacheEarned)))
								.replace(
										"%T",
										String.valueOf(Math.round((System
												.currentTimeMillis() - playerData.cacheTime) / 1000)));
						MobBountyMessage.sendMessage(killer, message);
					}
				}
			}
			else if (playerData.cacheEarned < 0.0)
			{
				if (MobBountyAPI.instance.getExternalsManager().getSpout() != null
						&& ((SpoutPlayer) killer) != null)
				{
					String message = MobBountyAPI.instance.getLocaleManager()
							.getString("SpoutFined");
					if (message != null)
					{
						message = message.replace("%A",
								format(Math.abs(playerData.cacheEarned)));
						((SpoutPlayer) killer).sendNotification(_plugin
								.getDescription().getName(), message,
								Material.BONE);
					}
				}
				else
				{
					String message = MobBountyUtils
							.getPluginMessage("CacheFined");
					if (message != null)
					{
						message = message
								.replace("%K",
										String.valueOf(playerData.cacheSize))
								.replace(
										"%A",
										format(Math.abs(playerData.cacheEarned)))
								.replace(
										"%T",
										String.valueOf(Math.round((System
												.currentTimeMillis() - playerData.cacheTime) / 1000)));
						MobBountyMessage.sendMessage(killer, message);
					}
				}
			}
		}
		_playerData.put(killer.getName(), playerData);
	}

	private static double handleKillstreak(Player killer, LivingEntity entity,
			double killerReward)
	{
		double reward = killerReward;
		if (MobBountyUtils.nearMobSpawner(killer.getLocation())
				|| MobBountyAPI.instance._spawnReason.containsKey(entity
						.getUniqueId())
				|| !MobBountyAPI.instance.getPermissionsManager()
						.hasPermission(killer, "mbr.user.killstreak"))
		{
			return reward;
		}
		double result = reward;
		List<String> allowedCreatures = MobBountyAPI.instance
				.getConfigManager().getPropertyList(
						MobBountyConfFile.KILLSTREAK, "allowedCreatures");
		if (allowedCreatures.isEmpty()
				|| !MobBountyUtils.containsIgnoreCase(allowedCreatures,
						MobBountyCreature.valueOf(entity, "").getName()))
		{
			return result;
		}
		MobBountyPlayerKillData playerData = _playerData.get(killer.getName());
		if (playerData == null)
		{
			playerData = new MobBountyPlayerKillData();
		}
		playerData.killStreak++;
		boolean confContains = MobBountyAPI.instance.getConfigManager()
				.propertyExists(MobBountyConfFile.KILLSTREAK,
						"KillBonus." + String.valueOf(playerData.killStreak));
		if (!confContains)
		{
			return result;
		}
		String confBonusS = MobBountyAPI.instance.getConfigManager()
				.getProperty(MobBountyConfFile.KILLSTREAK,
						"KillBonus." + playerData.killStreak);
		if (confBonusS == null)
		{
			return result;
		}
		double confBonus = 0.0;
		try
		{
			confBonus = Double.parseDouble(confBonusS);
		}
		catch (NumberFormatException e)
		{
			MobBountyAPI.instance.getConfigManager().setProperty(
					MobBountyConfFile.KILLSTREAK,
					"KillBonus." + playerData.killStreak, "0.0");
		}
		String checkMult = MobBountyAPI.instance
				.getConfigManager()
				.getProperty(MobBountyConfFile.KILLSTREAK, "killStreakMultiply");
		if (checkMult != null && checkMult.equalsIgnoreCase("true"))
			result = result *= confBonus;
		else
			result = result + confBonus;
		sendStreakMessage(killer, confBonus, playerData);
		_playerData.put(killer.getName(), playerData);
		return result;
	}

	/**
	 * Handles any transaction required by plugin
	 * 
	 * @param Player
	 *            Killer in EntityDeathEvent
	 * @param LivingEntity
	 *            Dead entity in EntityDeathEvent
	 * @param MobBountyCreature
	 *            Creature of LivingEntity
	 */
	public static void handleMobBountyTransaction(Player killer,
			LivingEntity entity, MobBountyCreature creature)
	{
		double baseReward = getBaseReward(killer, creature);
		double deprReward = handleDepreciativeReturn(killer, creature,
				baseReward);
		double multiplier = MobBountyAPI.instance.getExternalsManager()
				.checkEarnMultiplier(killer, entity.getLocation(), entity);
		double killerReward = deprReward *= multiplier;
		handlePartyEarnings(killer, killerReward, creature);
		double finalReward = handlePluginEarnings(killer, killerReward);
		double payReward = handleKillstreak(killer, entity, finalReward);
		MobBountyPaymentEvent paymentEvent = new MobBountyPaymentEvent(
				killer.getName(), payReward, creature);
		MobBountyAPI.instance.getPlugin().getServer().getPluginManager()
				.callEvent(paymentEvent);
		if (paymentEvent.isCancelled())
		{
			return;
		}
		monetaryTransaction(killer, paymentEvent.getAmount(), creature);
	}

	private static void handlePartyEarnings(Player killer, double baseReward,
			MobBountyCreature creature)
	{
		double base = baseReward;
		splitHeroes(killer, base, creature);
		splitMCMMO(killer, base, creature);
	}

	private static double handlePluginEarnings(Player killer, double baseReward)
	{
		double base = baseReward;
		double baseT = base;
		double baseF = base;
		baseT = handleTownEarnings(killer, base);
		baseF = handleFactionEarning(killer, baseT);
		return baseF;
	}

	private static double handleTownEarnings(Player killer, double base)
	{
		double baseReward = base;
		if (MobBountyAPI.instance.getExternalsManager().getTowny() != null
				&& base > 0.0)
		{
			if (TownyUtil.getResident(killer.getName()).hasTown())
			{
				double bR = baseReward;
				String cTS = MobBountyAPI.instance.getConfigManager()
						.getProperty(MobBountyConfFile.TOWNY,
								"autoDepositInTownRate");
				double cT = MobBountyUtils.getDouble(cTS, 0.0);
				if (cT > 1.0)
				{
					cT = 1.0;
					MobBountyAPI.instance.getConfigManager().setProperty(
							MobBountyConfFile.TOWNY, "autoDepositInTownRate",
							cT);
				}
				else if (cT < 0.0)
				{
					cT = 0.0;
					MobBountyAPI.instance.getConfigManager().setProperty(
							MobBountyConfFile.TOWNY,
							"autoDepositInFactionRate", cT);
				}
				double portionT = bR *= cT;
				TownyUtil.getResidentTown(
						TownyUtil.getResident(killer.getName())).setBalance(
						TownyUtil.getTownBalance(TownyUtil
								.getResidentTown(TownyUtil.getResident(killer
										.getName())))
								+ portionT, "MobBountyReloaded earnings");
				return (baseReward - portionT);
			}
			return baseReward;
		}
		return baseReward;
	}

	public static boolean hasAccount(Player player)
	{
		return econ.hasAccount(player.getName());
	}

	private static boolean hasAccount(String accountName)
	{
		return econ.hasAccount(accountName);
	}

	private static boolean monetaryTransaction(Player player, double amount,
			MobBountyCreature creature)
	{
		if (!hasAccount(player))
		{
			MobBountyReloaded._logger.info("[MobBountyReloaded] "
					+ player.getName() + " does not have an economy account.");
			return false;
		}
		if (amount > 0.0)
			return payPlayer(player, amount, creature);
		else if (amount < 0.0)
			return finePlayer(player, amount, creature);
		else
			return true;
	}

	private static boolean monetaryTransactionAccount(String accountName,
			double amount)
	{
		if (!hasAccount(accountName))
		{
			MobBountyMessage.logToConsole(accountName
					+ " does not have an economy account.");
			return false;
		}
		if (amount > 0.0)
			return payAccount(accountName, amount);
		else if (amount < 0.0)
			return fineAccount(accountName, amount);
		else
			return true;
	}

	private static boolean monetaryTransactionParty(Player player,
			double amount, Player killer, MobBountyCreature creature)
	{
		if (!hasAccount(player))
		{
			MobBountyMessage.logToConsole(player.getName()
					+ " does not have an economy account.");
			return false;
		}
		if (player.equals(killer))
			return false;
		if (amount > 0.0)
			return payPlayerParty(player, amount, killer, creature);
		else if (amount < 0.0)
			return finePlayerParty(player, amount, killer, creature);
		else
			return true;
	}

	public static boolean payAccount(String accountName, double amount)
	{
		double absAmount = Math.abs(amount);
		EconomyResponse r = econ.depositPlayer(accountName, absAmount);
		if (!r.transactionSuccess())
		{
			MobBountyMessage.logSevereToConsole("Unable to pay " + accountName);
			return false;
		}
		return true;
	}

	private static boolean payPlayer(Player player, double amount,
			MobBountyCreature creature)
	{
		double absAmount = Math.abs(amount);
		EconomyResponse r = econ.depositPlayer(player.getName(), absAmount);
		if (!r.transactionSuccess())
		{
			MobBountyMessage.logSevereToConsole("Unable to pay "
					+ player.getName());
			return false;
		}
		String useKillCache = MobBountyAPI.instance.getConfigManager()
				.getProperty(MobBountyConfFile.GENERAL, "killCache.use");
		if (useKillCache != null && useKillCache.equalsIgnoreCase("true"))
		{
			MobBountyPlayerKillData killData = _playerData
					.get(player.getName());
			if (killData == null)
				killData = new MobBountyPlayerKillData();
			handleKillCache(player, killData, amount);
		}
		else
		{
			if (MobBountyAPI.instance.getExternalsManager().getSpout() != null
					&& ((SpoutPlayer) player) != null
					&& MobBountyAPI.instance.getExternalsManager().useSpout())
			{
				String message = MobBountyAPI.instance.getLocaleManager()
						.getString("SpoutAwarded");
				if (message != null)
				{
					message = message.replace("%A", format(Math.abs(r.amount)));
					((SpoutPlayer) player)
							.sendNotification(_plugin.getDescription()
									.getName(), message, Material.BONE);
				}
			}
			else
			{
				String message = MobBountyUtils.getPluginMessage("Awarded");
				if (message != null)
				{
					message = message.replace("%M",
							creature.toString().toLowerCase()).replace("%A",
							format(absAmount));
					MobBountyMessage.sendMessage(player, message);
				}
			}
		}
		return true;
	}

	private static boolean payPlayerParty(Player player, double amount,
			Player killer, MobBountyCreature creature)
	{
		double absAmount = Math.abs(amount);
		EconomyResponse r = econ.depositPlayer(player.getName(), absAmount);
		if (!r.transactionSuccess())
		{
			MobBountyMessage.logSevereToConsole("Unable to pay "
					+ player.getName());
			return false;
		}
		String useKillCache = MobBountyAPI.instance.getConfigManager()
				.getProperty(MobBountyConfFile.GENERAL, "killCache.use");
		if (useKillCache != null && useKillCache.equalsIgnoreCase("true"))
		{
			MobBountyPlayerKillData killData = _playerData
					.get(player.getName());
			if (killData == null)
				killData = new MobBountyPlayerKillData();
			handleKillCache(player, killData, amount);
		}
		else
		{
			if (MobBountyAPI.instance.getExternalsManager().getSpout() != null
					&& ((SpoutPlayer) player) != null
					&& MobBountyAPI.instance.getExternalsManager().useSpout())
			{
				String message = MobBountyAPI.instance.getLocaleManager()
						.getString("SpoutAwarded");
				if (message != null)
				{
					message = message.replace("%A", format(Math.abs(r.amount)));
					((SpoutPlayer) player)
							.sendNotification(_plugin.getDescription()
									.getName(), message, Material.BONE);
				}
			}
			String message = MobBountyUtils.getPluginMessage("PartyAwarded");
			if (message != null)
			{
				message = message.replace("%M",
						creature.toString().toLowerCase()).replace("%A",
						format(absAmount).replace("%P", killer.getName()));
				MobBountyMessage.sendMessage(player, message);
			}
		}
		return true;
	}

	private static void sendStreakMessage(Player killer, double confBonus,
			MobBountyPlayerKillData playerData)
	{
		String booleanTest = MobBountyAPI.instance.getConfigManager()
				.getProperty(MobBountyConfFile.KILLSTREAK,
						"broadcastKillstreak");
		if (booleanTest == null)
		{
			MobBountyAPI.instance.getConfigManager().setProperty(
					MobBountyConfFile.KILLSTREAK, "broadcastKillstreak", true);
			booleanTest = "true";
		}
		if (!"true".equalsIgnoreCase(booleanTest))
			return;
		String broadcast = MobBountyAPI.instance.getLocaleManager().getString(
				"BroadcastStreak");
		broadcast = broadcast.replace("%P", killer.getName()).replace("%A",
				String.valueOf(playerData.killStreak));
		booleanTest = MobBountyAPI.instance.getConfigManager().getProperty(
				MobBountyConfFile.KILLSTREAK, "killStreakRange.use");
		if (booleanTest == null)
		{
			MobBountyAPI.instance.getConfigManager().setProperty(
					MobBountyConfFile.KILLSTREAK, "killStreakRange.use", false);
			booleanTest = "true";
		}
		if (!"true".equalsIgnoreCase(booleanTest))
		{
			String di = MobBountyAPI.instance.getConfigManager().getProperty(
					MobBountyConfFile.KILLSTREAK, "killStreakRange.blockRange");
			int dist = MobBountyUtils.getInt(di, 50);
			if (broadcast != null)
			{
				for (Player p : _plugin.getServer().getOnlinePlayers())
				{
					if (_plugin.getAPIManager().nearby(killer, p, dist))
						p.sendMessage(broadcast);
				}
			}
		}
		else
		{
			if (broadcast != null)
			{
				_plugin.getServer().broadcastMessage(broadcast);
			}
		}
	}

	private static void splitHeroes(Player killer, double base,
			MobBountyCreature creature)
	{
		String splitString = MobBountyAPI.instance.getConfigManager()
				.getProperty(MobBountyConfFile.HEROES, "splitAmongParty");
		if (splitString == null)
		{
			splitString = "true";
			MobBountyAPI.instance.getConfigManager().setProperty(
					MobBountyConfFile.HEROES, "splitAmongParty", true);
		}
		boolean canSplit = Boolean.parseBoolean(splitString);
		if (canSplit)
		{
			if (MobBountyAPI.instance.getExternalsManager().getHeroes() != null)
			{
				Heroes heroes = MobBountyAPI.instance.getExternalsManager()
						.getHeroes();
				HeroParty party = heroes.getCharacterManager().getHero(killer)
						.getParty();
				if (party != null)
				{
					double split = (base / (party.getMembers().size()));
					for (Hero h : party.getMembers())
					{
						if (!h.getPlayer().equals(killer))
							monetaryTransactionParty(h.getPlayer(), split,
									killer, creature);
					}
				}
			}
		}
	}

	private static void splitMCMMO(Player killer, double base,
			MobBountyCreature creature)
	{
		String splitString = MobBountyAPI.instance.getConfigManager()
				.getProperty(MobBountyConfFile.MCMMO, "splitAmongParty");
		if (splitString == null)
		{
			splitString = "true";
			MobBountyAPI.instance.getConfigManager().setProperty(
					MobBountyConfFile.MCMMO, "splitAmongParty", true);
		}
		boolean canSplit = Boolean.parseBoolean(splitString);
		if (canSplit)
		{
			if (MobBountyAPI.instance.getExternalsManager().getMCMMO() != null)
			{
				Party party = mcMMO.p.getPlayerProfile(killer.getName())
						.getParty();
				if (party != null)
				{
					double split = (base / (party.getOnlineMembers().size()));
					for (Player p : party.getOnlineMembers())
					{
						if (!p.equals(killer))
							monetaryTransactionParty(p, split, killer, creature);
					}
				}
			}
		}
	}

	public MobBountyEcon(MobBountyReloaded plugin)
	{
		_plugin = plugin;
		if (!setupEconomy())
		{
			_plugin.getServer().getPluginManager().disablePlugin(_plugin);
			return;
		}
		_playerData = new HashMap<String, MobBountyPlayerKillData>();
	}

	public Economy getEconomy()
	{
		return econ;
	}

	private boolean setupEconomy()
	{
		if (_plugin.getServer().getPluginManager().getPlugin("Vault") == null)
		{
			MobBountyMessage.logSevereToConsole(String.format(
					"[%s] - Vault was not found.", _plugin.getDescription()
							.getName()));
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = _plugin.getServer()
				.getServicesManager().getRegistration(Economy.class);
		if (rsp == null)
		{
			MobBountyMessage.logSevereToConsole(String.format(
					"[%s] - No economy plugin found.", _plugin.getDescription()
							.getName()));
			return false;
		}
		econ = rsp.getProvider();
		return econ != null;
	}
}
