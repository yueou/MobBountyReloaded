package info.hawksharbor.MobBounty.Managers;

import info.hawksharbor.MobBounty.MobBountyReloaded;
import info.hawksharbor.MobBounty.Utils.MobBountyAPI;
import info.hawksharbor.MobBounty.Utils.MobBountyConfFile;
import info.hawksharbor.MobBounty.Utils.MobBountyCreature;
import info.hawksharbor.MobBounty.Utils.MobBountyItemInfo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

public class MobBountyDrops
{

	/**
	 * Handles all drop modifying for the plugin
	 * 
	 * @param Player
	 *            Killer in EntityDeathEvent
	 * @param LivingEntity
	 *            Entity that died in EntityDeathEvent
	 * @param MobBountyCreature
	 *            Creature of Entity
	 * @param EntityDeathEvent
	 *            Event being called for
	 */
	public static void handleDrops(Player killer, LivingEntity entity,
			MobBountyCreature creature, EntityDeathEvent event)
	{
		String modifyDropString = MobBountyAPI.instance.getConfigManager()
				.getProperty(
						MobBountyConfFile.DROPS,
						creature.getName() + "." + entity.getWorld().getName()
								+ ".modifyDrops");
		if (modifyDropString == null)
		{
			modifyDropString = MobBountyAPI.instance.getConfigManager()
					.getProperty(MobBountyConfFile.DROPS,
							creature.getName() + ".Default.modifyDrops");
			if (modifyDropString == null)
			{
				modifyDropString = "false";
				MobBountyAPI.instance.getConfigManager().setProperty(
						MobBountyConfFile.DROPS,
						creature.getName() + ".Default.modifyDrops", false);
			}
		}
		boolean modifyDrops = Boolean.parseBoolean(modifyDropString);
		String clearDropString = MobBountyAPI.instance.getConfigManager()
				.getProperty(
						MobBountyConfFile.DROPS,
						creature.getName() + "." + entity.getWorld().getName()
								+ ".cancelNormalDrops");
		if (clearDropString == null)
		{
			clearDropString = MobBountyAPI.instance.getConfigManager()
					.getProperty(MobBountyConfFile.DROPS,
							creature.getName() + ".Default.cancelNormalDrops");
			if (clearDropString == null)
			{
				clearDropString = "false";
				MobBountyAPI.instance.getConfigManager().setProperty(
						MobBountyConfFile.DROPS,
						creature.getName() + ".Default.cancelNormalDrops",
						false);
			}
		}
		boolean cancelDrops = Boolean.parseBoolean(clearDropString);
		if (modifyDrops)
		{
			if (creature != null)
			{
				ArrayList<MobBountyItemInfo> creatureDrops = MobBountyAPI.instance
						.getConfigManager().getDrop(
								killer.getWorld().getName(), creature, entity);
				if (creatureDrops != null)
				{
					List<ItemStack> drops = event.getDrops();
					if (drops != null)
					{
						if (cancelDrops)
						{
							drops.clear();
						}
						Iterator<MobBountyItemInfo> creatureDropIterator = creatureDrops
								.iterator();
						while (creatureDropIterator.hasNext())
						{
							MobBountyItemInfo dropInfo = creatureDropIterator
									.next();

							if (dropInfo != null && dropInfo.isValid())
							{
								if (dropInfo.itemID == 0)
								{
									break;
								}
								if (dropInfo.quantity <= 0)
								{
									break;
								}
								if (dropInfo.chance == 100)
								{
									if (dropInfo.dataID == 0)
									{
										drops.add(new ItemStack(
												dropInfo.itemID,
												dropInfo.quantity));
									}
									else
									{
										MaterialData matData = new MaterialData(
												dropInfo.itemID,
												dropInfo.dataID);
										drops.add(matData
												.toItemStack(dropInfo.quantity));
									}
								}
								else
								{
									Random rand = new Random();

									if (rand.nextInt(100) <= dropInfo.chance)
									{
										if (dropInfo.dataID == 0)
										{
											drops.add(new ItemStack(
													dropInfo.itemID,
													dropInfo.quantity));
										}
										else
										{
											MaterialData matData = new MaterialData(
													dropInfo.itemID,
													dropInfo.dataID);
											drops.add(matData
													.toItemStack(dropInfo.quantity));
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	@SuppressWarnings("unused")
	private MobBountyReloaded _plugin;

	public MobBountyDrops(MobBountyReloaded plugin)
	{
		_plugin = plugin;
	}
}
