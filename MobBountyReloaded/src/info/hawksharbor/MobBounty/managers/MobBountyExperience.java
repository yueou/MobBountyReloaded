package info.hawksharbor.MobBounty.managers;

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
						"Worlds." + entity.getWorld().getName() + "."
								+ creature.getName());
		if (resultTest == null)
		{
			resultTest = MobBountyAPI.instance.getConfigManager().getProperty(
					MobBountyConfFile.EXPERIENCE,
					"Default." + creature.getName());
		}
		return resultTest;
	}

	public static void handleExperience(Player killer, LivingEntity entity,
			MobBountyCreature creature, EntityDeathEvent event)
	{
		if (MobBountyAPI.instance.getConfigManager().getProperty(
				MobBountyConfFile.GENERAL, "modifyExperienceDrops") != null
				&& MobBountyAPI.instance
						.getConfigManager()
						.getProperty(MobBountyConfFile.GENERAL,
								"modifyExperienceDrops")
						.equalsIgnoreCase("true"))
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
