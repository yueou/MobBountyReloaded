package info.hawksharbor.MobBounty.managers;

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

	public static void handleDrops(Player killer, LivingEntity entity,
			MobBountyCreature creature, EntityDeathEvent event)
	{
		if (MobBountyAPI.instance.getConfigManager().getProperty(
				MobBountyConfFile.GENERAL, "modifyItemDrops") != null
				&& MobBountyAPI.instance
						.getConfigManager()
						.getProperty(MobBountyConfFile.GENERAL,
								"modifyItemDrops").equalsIgnoreCase("true"))
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
						drops.clear();
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
									drops.clear();
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
