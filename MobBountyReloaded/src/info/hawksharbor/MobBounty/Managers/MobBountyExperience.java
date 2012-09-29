package info.hawksharbor.MobBounty.Managers;

import info.hawksharbor.MobBounty.Utils.MobBountyAPI;
import info.hawksharbor.MobBounty.Utils.MobBountyConfFile;
import info.hawksharbor.MobBounty.Utils.MobBountyCreature;
import info.hawksharbor.MobBounty.Utils.MobBountyMessage;
import info.hawksharbor.MobBounty.Utils.MobBountyUtils;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;

public class MobBountyExperience
{

	private static String getExperienceDrops(LivingEntity entity,
			MobBountyCreature creature)
	{
		String resultTest = MobBountyAPI.instance.getConfigManager()
				.getProperty(
						MobBountyConfFile.EXPERIENCE,
						creature.getName() + "." + entity.getWorld().getName()
								+ ".exp");
		if (resultTest == null)
		{
			resultTest = MobBountyAPI.instance.getConfigManager().getProperty(
					MobBountyConfFile.EXPERIENCE,
					creature.getName() + ".Default.exp");
		}
		return resultTest;
	}

	/**
	 * Handles all experience modifying required by plugin
	 * 
	 * @param Player
	 *            Killer in EntityDeathEvent
	 * @param LivingEntity
	 *            Dead entity in EntityDeathEvent
	 * @param MobBountyCreature
	 *            Creature of LivingEntity
	 * @param EntityDeathEvent
	 *            Death event being called for
	 */
	public static void handleExperience(Player killer, LivingEntity entity,
			MobBountyCreature creature, EntityDeathEvent event)
	{
		String modifyExpString = MobBountyAPI.instance.getConfigManager()
				.getProperty(
						MobBountyConfFile.EXPERIENCE,
						creature.getName() + "." + entity.getWorld().getName()
								+ ".modifyExp");
		if (modifyExpString == null)
		{
			modifyExpString = MobBountyAPI.instance.getConfigManager()
					.getProperty(MobBountyConfFile.DROPS,
							creature.getName() + ".Default.modifyExp");
			if (modifyExpString == null)
			{
				modifyExpString = "false";
				MobBountyAPI.instance.getConfigManager().setProperty(
						MobBountyConfFile.EXPERIENCE,
						creature.getName() + ".Default.modifyExp", false);
			}
		}
		boolean modifyExp = Boolean.parseBoolean(modifyExpString);
		if (modifyExp)
		{
			MobBountyMessage.debugMessage("Starting experience handling");
			String expDropS = getExperienceDrops(entity, creature);
			int expDrop = readExperienceAmount(expDropS);
			MobBountyMessage.debugMessage("Amount of experience: "
					+ String.valueOf(expDrop));
			event.setDroppedExp(expDrop);
		}
	}

	private static int readExperienceAmount(String expDropS)
	{
		int amt = 0;
		if (expDropS.contains("-"))
		{
			amt = MobBountyUtils.handleRange(expDropS, "-");
		}
		else
		{
			amt = (int) Math.round(MobBountyUtils.getDouble(expDropS, 0.0));
		}
		return amt;
	}

}
