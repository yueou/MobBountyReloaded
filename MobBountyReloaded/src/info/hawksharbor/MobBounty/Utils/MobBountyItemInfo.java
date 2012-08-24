package info.hawksharbor.MobBounty.Utils;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Sheep;

public class MobBountyItemInfo
{

	public double chance;
	public byte dataID;
	public int itemID;
	public int quantity;

	public MobBountyItemInfo(int item, byte data, int amount, int dropChance)
	{
		itemID = item;
		dataID = data;
		quantity = amount;
		chance = dropChance;
	}

	public MobBountyItemInfo(int item, int amount, int dropChance)
	{
		itemID = item;
		dataID = 0;
		quantity = amount;
		chance = dropChance;
	}

	public MobBountyItemInfo(String itemInfo)
	{
		int idValue = 0;
		boolean setId = false;
		byte dmgValue = 0;
		boolean setDmg = false;
		int amtValue = 0;
		boolean setAmt = false;
		int perValue = 0;
		boolean setPer = false;
		String[] split = itemInfo.split(";");
		if (split.length == 1)
		{
			String split0 = split[0];
			if (!split0.contains("i:"))
			{
				idValue = 19;
				setId = true;
				dmgValue = 0;
				setDmg = true;
				amtValue = 1;
				setAmt = true;
				perValue = 100;
				setPer = true;
			}
			else
			{
				if (split0.contains("-"))
					idValue = MobBountyUtils.handleRange(
							split[0].replace("i:", ""), "-");
				else
					idValue = MobBountyUtils.getInt(split0.replace("i:", ""),
							19);
				setId = true;
				dmgValue = 0;
				setDmg = true;
				amtValue = 1;
				setAmt = true;
				perValue = 100;
				setPer = true;
			}
		}
		else if (split.length == 2)
		{
			String split0 = split[0];
			String split1 = split[1];
			if (split0.contains("i:") && !setId)
			{
				if (split0.contains("-"))
					idValue = MobBountyUtils.handleRange(
							split0.replace("i:", ""), "-");
				else
					idValue = MobBountyUtils.getInt(split0.replace("i:", ""),
							19);
				setId = true;
			}
			else if (split0.contains("d:") && !setDmg)
			{
				String dmgS = split0.replace("d:", "");
				if (dmgS.equalsIgnoreCase("C"))
				{
					dmgValue = 0;
				}
				else
				{
					if (dmgS.contains("-"))
						dmgValue = (byte) MobBountyUtils.handleRange(dmgS, "-");
					else
						dmgValue = (byte) MobBountyUtils.getInt(dmgS, 0);
				}
				setDmg = true;
			}
			else if (split0.contains("a:") && !setAmt)
			{
				if (split0.contains("-"))
					amtValue = MobBountyUtils.handleRange(
							split0.replace("a:", ""), "-");
				else
					amtValue = MobBountyUtils.getInt(split0.replace("a:", ""),
							1);
				setAmt = true;
			}
			else if (split0.contains("p:") && !setPer)
			{
				if (split0.contains("-"))
					perValue = MobBountyUtils.handleRange(
							split0.replace("p:", ""), "-");
				else
					perValue = (int) Math.round(MobBountyUtils.getDouble(
							split0.replace("p:", ""), 100));
				setPer = true;
			}

			if (split1.contains("i:") && !setId)
			{
				if (split1.contains("-"))
					idValue = MobBountyUtils.handleRange(
							split1.replace("i:", ""), "-");
				else
					idValue = MobBountyUtils.getInt(split1.replace("i:", ""),
							19);
				setId = true;
			}
			else if (split1.contains("d:") && !setDmg)
			{
				String dmgS = split1.replace("d:", "");
				if (dmgS.equalsIgnoreCase("C"))
				{
					dmgValue = 0;
				}
				else
				{
					if (dmgS.contains("-"))
						dmgValue = (byte) MobBountyUtils.handleRange(dmgS, "-");
					else
						dmgValue = (byte) MobBountyUtils.getInt(dmgS, 0);
				}
				setDmg = true;
			}
			else if (split1.contains("a:") && !setAmt)
			{
				if (split1.contains("-"))
					amtValue = MobBountyUtils.handleRange(
							split[0].replace("i:", ""), "-");
				else
					amtValue = MobBountyUtils.getInt(split1.replace("a:", ""),
							1);
				setAmt = true;
			}
			else if (split1.contains("p:") && !setPer)
			{
				if (split[0].contains("-"))
					perValue = MobBountyUtils.handleRange(
							split[0].replace("p:", ""), "-");
				else
					perValue = (int) Math.round(MobBountyUtils.getDouble(
							split1.replace("p:", ""), 100));
				setPer = true;
			}
		}
		else if (split.length == 3)
		{
			String split0 = split[0];
			String split1 = split[1];
			String split2 = split[2];
			if (split0.contains("i:") && !setId)
			{
				if (split0.contains("-"))
					idValue = MobBountyUtils.handleRange(
							split0.replace("i:", ""), "-");
				else
					idValue = MobBountyUtils.getInt(split0.replace("i:", ""),
							19);
				setId = true;
			}
			else if (split0.contains("d:") && !setDmg)
			{
				String dmgS = split0.replace("d:", "");
				if (dmgS.equalsIgnoreCase("C"))
				{
					dmgValue = 0;
				}
				else
				{
					if (dmgS.contains("-"))
						dmgValue = (byte) MobBountyUtils.handleRange(dmgS, "-");
					else
						dmgValue = (byte) MobBountyUtils.getInt(dmgS, 0);
				}
				setDmg = true;
			}
			else if (split0.contains("a:") && !setAmt)
			{
				if (split0.contains("-"))
					amtValue = MobBountyUtils.handleRange(
							split0.replace("a:", ""), "-");
				else
					amtValue = MobBountyUtils.getInt(split0.replace("a:", ""),
							1);
				setAmt = true;
			}
			else if (split0.contains("p:") && !setPer)
			{
				if (split0.contains("-"))
					perValue = MobBountyUtils.handleRange(
							split0.replace("p:", ""), "-");
				else
					perValue = (int) Math.round(MobBountyUtils.getDouble(
							split0.replace("p:", ""), 100));
				setPer = true;
			}

			if (split1.contains("i:") && !setId)
			{
				if (split1.contains("-"))
					idValue = MobBountyUtils.handleRange(
							split1.replace("i:", ""), "-");
				else
					idValue = MobBountyUtils.getInt(split1.replace("i:", ""),
							19);
				setId = true;
			}
			else if (split1.contains("d:") && !setDmg)
			{
				String dmgS = split1.replace("d:", "");
				if (dmgS.equalsIgnoreCase("C"))
				{
					dmgValue = 0;
				}
				else
				{
					if (dmgS.contains("-"))
						dmgValue = (byte) MobBountyUtils.handleRange(dmgS, "-");
					else
						dmgValue = (byte) MobBountyUtils.getInt(dmgS, 0);
				}
				setDmg = true;
			}
			else if (split1.contains("a:") && !setAmt)
			{
				if (split1.contains("-"))
					amtValue = MobBountyUtils.handleRange(
							split0.replace("i:", ""), "-");
				else
					amtValue = MobBountyUtils.getInt(split1.replace("a:", ""),
							1);
				setAmt = true;
			}
			else if (split1.contains("p:") && !setPer)
			{
				if (split0.contains("-"))
					perValue = MobBountyUtils.handleRange(
							split0.replace("p:", ""), "-");
				else
					perValue = (int) Math.round(MobBountyUtils.getDouble(
							split1.replace("p:", ""), 100));
				setPer = true;
			}

			if (split2.contains("i:") && !setId)
			{
				if (split2.contains("-"))
					idValue = MobBountyUtils.handleRange(
							split2.replace("i:", ""), "-");
				else
					idValue = MobBountyUtils.getInt(split2.replace("i:", ""),
							19);
				setId = true;
			}
			else if (split2.contains("d:") && !setDmg)
			{
				String dmgS = split2.replace("d:", "");
				if (dmgS.equalsIgnoreCase("C"))
				{
					dmgValue = 0;
				}
				else
				{
					if (dmgS.contains("-"))
						dmgValue = (byte) MobBountyUtils.handleRange(dmgS, "-");
					else
						dmgValue = (byte) MobBountyUtils.getInt(dmgS, 0);
				}
				setDmg = true;
			}
			else if (split2.contains("a:") && !setAmt)
			{
				if (split2.contains("-"))
					amtValue = MobBountyUtils.handleRange(
							split2.replace("a:", ""), "-");
				else
					amtValue = MobBountyUtils.getInt(split2.replace("a:", ""),
							1);
				setAmt = true;
			}
			else if (split2.contains("p:") && !setPer)
			{
				if (split2.contains("-"))
					perValue = MobBountyUtils.handleRange(
							split2.replace("p:", ""), "-");
				else
					perValue = (int) Math.round(MobBountyUtils.getDouble(
							split2.replace("p:", ""), 100));
				setPer = true;
			}
		}
		else if (split.length == 4)
		{
			String split0 = split[0];
			String split1 = split[1];
			String split2 = split[2];
			String split3 = split[3];
			if (split0.contains("i:") && !setId)
			{
				if (split0.contains("-"))
					idValue = MobBountyUtils.handleRange(
							split0.replace("i:", ""), "-");
				else
					idValue = MobBountyUtils.getInt(split0.replace("i:", ""),
							19);
				setId = true;
			}
			else if (split0.contains("d:") && !setDmg)
			{
				String dmgS = split0.replace("d:", "");
				if (dmgS.equalsIgnoreCase("C"))
				{
					dmgValue = 0;
				}
				else
				{
					if (dmgS.contains("-"))
						dmgValue = (byte) MobBountyUtils.handleRange(dmgS, "-");
					else
						dmgValue = (byte) MobBountyUtils.getInt(dmgS, 0);
				}
				setDmg = true;
			}
			else if (split0.contains("a:") && !setAmt)
			{
				if (split0.contains("-"))
					amtValue = MobBountyUtils.handleRange(
							split0.replace("a:", ""), "-");
				else
					amtValue = MobBountyUtils.getInt(split0.replace("a:", ""),
							1);
				setAmt = true;
			}
			else if (split0.contains("p:") && !setPer)
			{
				if (split0.contains("-"))
					perValue = MobBountyUtils.handleRange(
							split0.replace("p:", ""), "-");
				else
					perValue = (int) Math.round(MobBountyUtils.getDouble(
							split0.replace("p:", ""), 100));
				setPer = true;
			}

			if (split1.contains("i:") && !setId)
			{
				if (split1.contains("-"))
					idValue = MobBountyUtils.handleRange(
							split1.replace("i:", ""), "-");
				else
					idValue = MobBountyUtils.getInt(split1.replace("i:", ""),
							19);
				setId = true;
			}
			else if (split1.contains("d:") && !setDmg)
			{
				String dmgS = split1.replace("d:", "");
				if (dmgS.equalsIgnoreCase("C"))
				{
					dmgValue = 0;
				}
				else
				{
					if (dmgS.contains("-"))
						dmgValue = (byte) MobBountyUtils.handleRange(dmgS, "-");
					else
						dmgValue = (byte) MobBountyUtils.getInt(dmgS, 0);
				}
				setDmg = true;
			}
			else if (split1.contains("a:") && !setAmt)
			{
				if (split1.contains("-"))
					amtValue = MobBountyUtils.handleRange(
							split0.replace("i:", ""), "-");
				else
					amtValue = MobBountyUtils.getInt(split1.replace("a:", ""),
							1);
				setAmt = true;
			}
			else if (split1.contains("p:") && !setPer)
			{
				if (split0.contains("-"))
					perValue = MobBountyUtils.handleRange(
							split0.replace("p:", ""), "-");
				else
					perValue = (int) Math.round(MobBountyUtils.getDouble(
							split1.replace("p:", ""), 100));
				setPer = true;
			}

			if (split2.contains("i:") && !setId)
			{
				if (split2.contains("-"))
					idValue = MobBountyUtils.handleRange(
							split2.replace("i:", ""), "-");
				else
					idValue = MobBountyUtils.getInt(split2.replace("i:", ""),
							19);
				setId = true;
			}
			else if (split2.contains("d:") && !setDmg)
			{
				String dmgS = split2.replace("d:", "");
				if (dmgS.equalsIgnoreCase("C"))
				{
					dmgValue = 0;
				}
				else
				{
					if (dmgS.contains("-"))
						dmgValue = (byte) MobBountyUtils.handleRange(dmgS, "-");
					else
						dmgValue = (byte) MobBountyUtils.getInt(dmgS, 0);
				}
				setDmg = true;
			}
			else if (split2.contains("a:") && !setAmt)
			{
				if (split2.contains("-"))
					amtValue = MobBountyUtils.handleRange(
							split2.replace("a:", ""), "-");
				else
					amtValue = MobBountyUtils.getInt(split2.replace("a:", ""),
							1);
				setAmt = true;
			}
			else if (split2.contains("p:") && !setPer)
			{
				if (split2.contains("-"))
					perValue = MobBountyUtils.handleRange(
							split2.replace("p:", ""), "-");
				else
					perValue = (int) Math.round(MobBountyUtils.getDouble(
							split2.replace("p:", ""), 100));
				setPer = true;
			}

			if (split3.contains("i:") && !setId)
			{
				if (split3.contains("-"))
					idValue = MobBountyUtils.handleRange(
							split3.replace("i:", ""), "-");
				else
					idValue = MobBountyUtils.getInt(split3.replace("i:", ""),
							19);
				setId = true;
			}
			else if (split3.contains("d:") && !setDmg)
			{
				String dmgS = split3.replace("d:", "");
				if (dmgS.equalsIgnoreCase("C"))
				{
					dmgValue = 0;
				}
				else
				{
					if (dmgS.contains("-"))
						dmgValue = (byte) MobBountyUtils.handleRange(dmgS, "-");
					else
						dmgValue = (byte) MobBountyUtils.getInt(dmgS, 0);
				}
				setDmg = true;
			}
			else if (split3.contains("a:") && !setAmt)
			{
				if (split3.contains("-"))
					amtValue = MobBountyUtils.handleRange(
							split3.replace("i:", ""), "-");
				else
					amtValue = MobBountyUtils.getInt(split3.replace("a:", ""),
							1);
				setAmt = true;
			}
			else if (split3.contains("p:") && !setPer)
			{
				if (split3.contains("-"))
					perValue = MobBountyUtils.handleRange(
							split3.replace("p:", ""), "-");
				else
					perValue = (int) Math.round(MobBountyUtils.getDouble(
							split3.replace("p:", ""), 100));
				setPer = true;
			}
		}
		if (setId = false)
		{
			idValue = 0;
		}
		if (setDmg = false)
		{
			dmgValue = 0;
		}
		if (setAmt = false)
		{
			if (amtValue == 0)
				amtValue = 1;
		}
		if (setPer = false)
		{
			perValue = 100;
		}
		itemID = idValue;
		dataID = dmgValue;
		quantity = amtValue;
		chance = perValue;
	}

	public MobBountyItemInfo(String itemInfo, LivingEntity entity)
	{
		Sheep sheep = null;
		if (entity.getType().equals(EntityType.SHEEP))
		{
			sheep = ((Sheep) entity);
		}
		int idValue = 0;
		boolean setId = false;
		byte dmgValue = 0;
		boolean setDmg = false;
		int amtValue = 0;
		boolean setAmt = false;
		int perValue = 0;
		boolean setPer = false;
		String[] split = itemInfo.split(";");
		if (split.length == 1)
		{
			String split0 = split[0];
			if (!split0.contains("i:"))
			{
				idValue = 19;
				setId = true;
				if (sheep == null)
					dmgValue = 0;
				else
					dmgValue = sheep.getColor().getData();
				setDmg = true;
				amtValue = 1;
				setAmt = true;
				perValue = 100;
				setPer = true;
			}
			else
			{
				if (split0.contains("-"))
					idValue = MobBountyUtils.handleRange(
							split[0].replace("i:", ""), "-");
				else
					idValue = MobBountyUtils.getInt(split0.replace("i:", ""),
							19);
				setId = true;
				if (sheep == null)
					dmgValue = 0;
				else
					dmgValue = sheep.getColor().getData();
				setDmg = true;
				amtValue = 1;
				setAmt = true;
				perValue = 100;
				setPer = true;
			}
		}
		else if (split.length == 2)
		{
			String split0 = split[0];
			String split1 = split[1];
			if (split0.contains("i:") && !setId)
			{
				if (split0.contains("-"))
					idValue = MobBountyUtils.handleRange(
							split0.replace("i:", ""), "-");
				else
					idValue = MobBountyUtils.getInt(split0.replace("i:", ""),
							19);
				setId = true;
			}
			else if (split0.contains("d:") && !setDmg)
			{
				String dmgS = split0.replace("d:", "");
				if (dmgS.equalsIgnoreCase("C"))
				{
					if (sheep == null)
						dmgValue = 0;
					else
						dmgValue = sheep.getColor().getData();
				}
				else
				{
					if (dmgS.contains("-"))
						dmgValue = (byte) MobBountyUtils.handleRange(dmgS, "-");
					else
						dmgValue = (byte) MobBountyUtils.getInt(dmgS, 0);
				}
				setDmg = true;
			}
			else if (split0.contains("a:") && !setAmt)
			{
				if (split0.contains("-"))
					amtValue = MobBountyUtils.handleRange(
							split0.replace("a:", ""), "-");
				else
					amtValue = MobBountyUtils.getInt(split0.replace("a:", ""),
							1);
				setAmt = true;
			}
			else if (split0.contains("p:") && !setPer)
			{
				if (split0.contains("-"))
					perValue = MobBountyUtils.handleRange(
							split0.replace("p:", ""), "-");
				else
					perValue = (int) Math.round(MobBountyUtils.getDouble(
							split0.replace("p:", ""), 100));
				setPer = true;
			}

			if (split1.contains("i:") && !setId)
			{
				if (split1.contains("-"))
					idValue = MobBountyUtils.handleRange(
							split1.replace("i:", ""), "-");
				else
					idValue = MobBountyUtils.getInt(split1.replace("i:", ""),
							19);
				setId = true;
			}
			else if (split1.contains("d:") && !setDmg)
			{
				String dmgS = split1.replace("d:", "");
				if (dmgS.equalsIgnoreCase("C"))
				{
					if (sheep == null)
						dmgValue = 0;
					else
						dmgValue = sheep.getColor().getData();
				}
				else
				{
					if (dmgS.contains("-"))
						dmgValue = (byte) MobBountyUtils.handleRange(dmgS, "-");
					else
						dmgValue = (byte) MobBountyUtils.getInt(dmgS, 0);
				}
				setDmg = true;
			}
			else if (split1.contains("a:") && !setAmt)
			{
				if (split1.contains("-"))
					amtValue = MobBountyUtils.handleRange(
							split[0].replace("i:", ""), "-");
				else
					amtValue = MobBountyUtils.getInt(split1.replace("a:", ""),
							1);
				setAmt = true;
			}
			else if (split1.contains("p:") && !setPer)
			{
				if (split[0].contains("-"))
					perValue = MobBountyUtils.handleRange(
							split[0].replace("p:", ""), "-");
				else
					perValue = (int) Math.round(MobBountyUtils.getDouble(
							split1.replace("p:", ""), 100));
				setPer = true;
			}
		}
		else if (split.length == 3)
		{
			String split0 = split[0];
			String split1 = split[1];
			String split2 = split[2];
			if (split0.contains("i:") && !setId)
			{
				if (split0.contains("-"))
					idValue = MobBountyUtils.handleRange(
							split0.replace("i:", ""), "-");
				else
					idValue = MobBountyUtils.getInt(split0.replace("i:", ""),
							19);
				setId = true;
			}
			else if (split0.contains("d:") && !setDmg)
			{
				String dmgS = split0.replace("d:", "");
				if (dmgS.equalsIgnoreCase("C"))
				{
					if (sheep == null)
						dmgValue = 0;
					else
						dmgValue = sheep.getColor().getData();
				}
				else
				{
					if (dmgS.contains("-"))
						dmgValue = (byte) MobBountyUtils.handleRange(dmgS, "-");
					else
						dmgValue = (byte) MobBountyUtils.getInt(dmgS, 0);
				}
				setDmg = true;
			}
			else if (split0.contains("a:") && !setAmt)
			{
				if (split0.contains("-"))
					amtValue = MobBountyUtils.handleRange(
							split0.replace("a:", ""), "-");
				else
					amtValue = MobBountyUtils.getInt(split0.replace("a:", ""),
							1);
				setAmt = true;
			}
			else if (split0.contains("p:") && !setPer)
			{
				if (split0.contains("-"))
					perValue = MobBountyUtils.handleRange(
							split0.replace("p:", ""), "-");
				else
					perValue = (int) Math.round(MobBountyUtils.getDouble(
							split0.replace("p:", ""), 100));
				setPer = true;
			}

			if (split1.contains("i:") && !setId)
			{
				if (split1.contains("-"))
					idValue = MobBountyUtils.handleRange(
							split1.replace("i:", ""), "-");
				else
					idValue = MobBountyUtils.getInt(split1.replace("i:", ""),
							19);
				setId = true;
			}
			else if (split1.contains("d:") && !setDmg)
			{
				String dmgS = split1.replace("d:", "");
				if (dmgS.equalsIgnoreCase("C"))
				{
					if (sheep == null)
						dmgValue = 0;
					else
						dmgValue = sheep.getColor().getData();
				}
				else
				{
					if (dmgS.contains("-"))
						dmgValue = (byte) MobBountyUtils.handleRange(dmgS, "-");
					else
						dmgValue = (byte) MobBountyUtils.getInt(dmgS, 0);
				}
				setDmg = true;
			}
			else if (split1.contains("a:") && !setAmt)
			{
				if (split1.contains("-"))
					amtValue = MobBountyUtils.handleRange(
							split0.replace("i:", ""), "-");
				else
					amtValue = MobBountyUtils.getInt(split1.replace("a:", ""),
							1);
				setAmt = true;
			}
			else if (split1.contains("p:") && !setPer)
			{
				if (split0.contains("-"))
					perValue = MobBountyUtils.handleRange(
							split0.replace("p:", ""), "-");
				else
					perValue = (int) Math.round(MobBountyUtils.getDouble(
							split1.replace("p:", ""), 100));
				setPer = true;
			}

			if (split2.contains("i:") && !setId)
			{
				if (split2.contains("-"))
					idValue = MobBountyUtils.handleRange(
							split2.replace("i:", ""), "-");
				else
					idValue = MobBountyUtils.getInt(split2.replace("i:", ""),
							19);
				setId = true;
			}
			else if (split2.contains("d:") && !setDmg)
			{
				String dmgS = split2.replace("d:", "");
				if (dmgS.equalsIgnoreCase("C"))
				{
					if (sheep == null)
						dmgValue = 0;
					else
						dmgValue = sheep.getColor().getData();
				}
				else
				{
					if (dmgS.contains("-"))
						dmgValue = (byte) MobBountyUtils.handleRange(dmgS, "-");
					else
						dmgValue = (byte) MobBountyUtils.getInt(dmgS, 0);
				}
				setDmg = true;
			}
			else if (split2.contains("a:") && !setAmt)
			{
				if (split2.contains("-"))
					amtValue = MobBountyUtils.handleRange(
							split2.replace("a:", ""), "-");
				else
					amtValue = MobBountyUtils.getInt(split2.replace("a:", ""),
							1);
				setAmt = true;
			}
			else if (split2.contains("p:") && !setPer)
			{
				if (split2.contains("-"))
					perValue = MobBountyUtils.handleRange(
							split2.replace("p:", ""), "-");
				else
					perValue = (int) Math.round(MobBountyUtils.getDouble(
							split2.replace("p:", ""), 100));
				setPer = true;
			}
		}
		else if (split.length == 4)
		{
			String split0 = split[0];
			String split1 = split[1];
			String split2 = split[2];
			String split3 = split[3];
			if (split0.contains("i:") && !setId)
			{
				if (split0.contains("-"))
					idValue = MobBountyUtils.handleRange(
							split0.replace("i:", ""), "-");
				else
					idValue = MobBountyUtils.getInt(split0.replace("i:", ""),
							19);
				setId = true;
			}
			else if (split0.contains("d:") && !setDmg)
			{
				String dmgS = split0.replace("d:", "");
				if (dmgS.equalsIgnoreCase("C"))
				{
					if (sheep == null)
						dmgValue = 0;
					else
						dmgValue = sheep.getColor().getData();
				}
				else
				{
					if (dmgS.contains("-"))
						dmgValue = (byte) MobBountyUtils.handleRange(dmgS, "-");
					else
						dmgValue = (byte) MobBountyUtils.getInt(dmgS, 0);
				}
				setDmg = true;
			}
			else if (split0.contains("a:") && !setAmt)
			{
				if (split0.contains("-"))
					amtValue = MobBountyUtils.handleRange(
							split0.replace("a:", ""), "-");
				else
					amtValue = MobBountyUtils.getInt(split0.replace("a:", ""),
							1);
				setAmt = true;
			}
			else if (split0.contains("p:") && !setPer)
			{
				if (split0.contains("-"))
					perValue = MobBountyUtils.handleRange(
							split0.replace("p:", ""), "-");
				else
					perValue = (int) Math.round(MobBountyUtils.getDouble(
							split0.replace("p:", ""), 100));
				setPer = true;
			}

			if (split1.contains("i:") && !setId)
			{
				if (split1.contains("-"))
					idValue = MobBountyUtils.handleRange(
							split1.replace("i:", ""), "-");
				else
					idValue = MobBountyUtils.getInt(split1.replace("i:", ""),
							19);
				setId = true;
			}
			else if (split1.contains("d:") && !setDmg)
			{
				String dmgS = split1.replace("d:", "");
				if (dmgS.equalsIgnoreCase("C"))
				{
					if (sheep == null)
						dmgValue = 0;
					else
						dmgValue = sheep.getColor().getData();
				}
				else
				{
					if (dmgS.contains("-"))
						dmgValue = (byte) MobBountyUtils.handleRange(dmgS, "-");
					else
						dmgValue = (byte) MobBountyUtils.getInt(dmgS, 0);
				}
				setDmg = true;
			}
			else if (split1.contains("a:") && !setAmt)
			{
				if (split1.contains("-"))
					amtValue = MobBountyUtils.handleRange(
							split0.replace("i:", ""), "-");
				else
					amtValue = MobBountyUtils.getInt(split1.replace("a:", ""),
							1);
				setAmt = true;
			}
			else if (split1.contains("p:") && !setPer)
			{
				if (split0.contains("-"))
					perValue = MobBountyUtils.handleRange(
							split0.replace("p:", ""), "-");
				else
					perValue = (int) Math.round(MobBountyUtils.getDouble(
							split1.replace("p:", ""), 100));
				setPer = true;
			}

			if (split2.contains("i:") && !setId)
			{
				if (split2.contains("-"))
					idValue = MobBountyUtils.handleRange(
							split2.replace("i:", ""), "-");
				else
					idValue = MobBountyUtils.getInt(split2.replace("i:", ""),
							19);
				setId = true;
			}
			else if (split2.contains("d:") && !setDmg)
			{
				String dmgS = split2.replace("d:", "");
				if (dmgS.equalsIgnoreCase("C"))
				{
					if (sheep == null)
						dmgValue = 0;
					else
						dmgValue = sheep.getColor().getData();
				}
				else
				{
					if (dmgS.contains("-"))
						dmgValue = (byte) MobBountyUtils.handleRange(dmgS, "-");
					else
						dmgValue = (byte) MobBountyUtils.getInt(dmgS, 0);
				}
				setDmg = true;
			}
			else if (split2.contains("a:") && !setAmt)
			{
				if (split2.contains("-"))
					amtValue = MobBountyUtils.handleRange(
							split2.replace("a:", ""), "-");
				else
					amtValue = MobBountyUtils.getInt(split2.replace("a:", ""),
							1);
				setAmt = true;
			}
			else if (split2.contains("p:") && !setPer)
			{
				if (split2.contains("-"))
					perValue = MobBountyUtils.handleRange(
							split2.replace("p:", ""), "-");
				else
					perValue = (int) Math.round(MobBountyUtils.getDouble(
							split2.replace("p:", ""), 100));
				setPer = true;
			}

			if (split3.contains("i:") && !setId)
			{
				if (split3.contains("-"))
					idValue = MobBountyUtils.handleRange(
							split3.replace("i:", ""), "-");
				else
					idValue = MobBountyUtils.getInt(split3.replace("i:", ""),
							19);
				setId = true;
			}
			else if (split3.contains("d:") && !setDmg)
			{
				String dmgS = split3.replace("d:", "");
				if (dmgS.equalsIgnoreCase("C"))
				{
					if (sheep == null)
						dmgValue = 0;
					else
						dmgValue = sheep.getColor().getData();
				}
				else
				{
					if (dmgS.contains("-"))
						dmgValue = (byte) MobBountyUtils.handleRange(dmgS, "-");
					else
						dmgValue = (byte) MobBountyUtils.getInt(dmgS, 0);
				}
				setDmg = true;
			}
			else if (split3.contains("a:") && !setAmt)
			{
				if (split3.contains("-"))
					amtValue = MobBountyUtils.handleRange(
							split3.replace("i:", ""), "-");
				else
					amtValue = MobBountyUtils.getInt(split3.replace("a:", ""),
							1);
				setAmt = true;
			}
			else if (split3.contains("p:") && !setPer)
			{
				if (split3.contains("-"))
					perValue = MobBountyUtils.handleRange(
							split3.replace("p:", ""), "-");
				else
					perValue = (int) Math.round(MobBountyUtils.getDouble(
							split3.replace("p:", ""), 100));
				setPer = true;
			}
		}
		if (setId = false)
		{
			idValue = 0;
		}
		if (setDmg = false)
		{
			dmgValue = 0;
		}
		if (setAmt = false)
		{
			if (amtValue == 0)
				amtValue = 1;
		}
		if (setPer = false)
		{
			perValue = 100;
		}
		itemID = idValue;
		dataID = dmgValue;
		quantity = amtValue;
		chance = perValue;
	}

	public boolean hasData(int item, int data)
	{
		if (data == 0)
			return true;

		// Pressure Plates and Coal/Charcoal
		if (item == 70 || item == 72 || item == 263)
		{
			if (data == 0 || data == 1)
				return true;
			return false;
		}
		// Tall Grass, and Silverfish
		if (item == 31 || item == 97)
		{
			if (data >= 0 && data <= 2)
				return true;
			return false;
		}
		// Wood, Leaves, Ladders, Nether Wart, Cauldron
		else if (item == 17 || item == 18 || item == 65 || item == 86
				|| item == 91 || item == 98 || item == 115 || item == 118)
		{
			if (data >= 0 && data <= 3)
				return true;
			return false;
		}
		// Stairs, Brewing Stand, End Portal Frame
		else if (item == 53 || item == 67 || item == 108 || item == 109
				|| item == 114 || item == 117 || item == 120)
		{
			if (data >= 0 && data <= 4)
				return true;
			return false;
		}
		// Double Slabs, Torches, Redstone Torches, and Cake
		else if (item == 43 || item == 44 || item == 50 || item == 75
				|| item == 76 || item == 92)
		{
			if (data >= 0 && data <= 5)
				return true;
			return false;
		}
		// Wheat, Trapdoor, Pumpkin, Melon, Fence Gate
		else if (item == 59 || item == 96 || item == 104 || item == 105
				|| item == 107)
		{
			if (data >= 0 && data <= 7)
				return true;
			return false;
		}
		// Farmland, Vines
		else if (item == 60 || item == 106)
		{
			if (data >= 0 && data <= 8)
				return true;
			return false;
		}
		// Rails
		else if (item == 27 || item == 28 || item == 66)
		{
			if (data >= 0 && data <= 9)
				return true;
			return false;
		}
		// Mushrooms
		else if (item == 99 || item == 100)
		{
			if (data >= 0 && data <= 10)
				return true;
			return false;
		}
		// Jukebox
		else if (item == 84)
		{
			if (data >= 0 && data <= 11)
				return true;
			return false;
		}
		else if (item == 6 || item == 8 || item == 9 || item == 11
				|| item == 35 || item == 51 || item == 55 || item == 63
				|| item == 64 || item == 71 || item == 81 || item == 83
				|| item == 93 || item == 94 || item == 106 || item == 351)
		{
			if (data >= 0 && data <= 15)
				return true;
			return false;
		}
		else if (item == 23 || item == 54 || item == 61 || item == 62
				|| item == 68)
		{
			if (data >= 2 && data <= 5)
				return true;
			return false;
		}
		else if (item == 69)
		{
			if (data >= 6 && data <= 14)
				return true;
			return false;
		}
		else if (item == 26)
		{
			if (data >= 0 && data <= 3)
				return true;
			else if (data >= 8 && data <= 11)
				return true;
			else
				return false;
		}
		// Pistons
		else if (item == 29 || item == 33 || item == 34)
		{
			if (data >= 0 && data <= 5)
				return true;
			else if (data >= 8 && data <= 13)
				return true;
			else
				return false;
		}
		// Spawn Eggs
		else if (item == 383)
		{
			if (data >= 50 && data <= 63)
				return true;
			else if (data >= 90 && data <= 99)
				return true;
			else if (data == 120)
				return true;
			else
				return false;
		}
		// Potions - Screw validation that.
		else if (item == 373)
		{
			return true;
		}
		// Items with Damage
		else if ((item >= 256 && item <= 259) || item == 261
				|| (item >= 267 && item <= 279) || (item >= 283 && item <= 286)
				|| (item >= 290 && item <= 294) || (item >= 298 && item <= 317)
				|| item == 346 || item == 359)
		{
			return true;
		}

		return false;
	}

	public void invalidate()
	{
		itemID = -1;
		dataID = -1;
		quantity = -1;
		chance = -1;
	}

	public boolean isItem(int item)
	{
		if (item >= 0 && item <= 124)
			return true;
		else if (item >= 256 && item <= 385)
			return true;
		else if (item >= 2256 && item <= 2266)
			return true;

		return false;
	}

	public boolean isValid()
	{
		return (itemID != -1 && dataID != -1 && quantity != -1 && chance != -1);
	}

	public String toString()
	{
		return "i:" + String.valueOf(itemID) + ";d:" + String.valueOf(dataID)
				+ ";a:" + String.valueOf(quantity) + ";p:"
				+ String.valueOf(chance);
	}

}
