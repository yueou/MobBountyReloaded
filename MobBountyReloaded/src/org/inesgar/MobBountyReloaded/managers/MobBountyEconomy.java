package org.inesgar.MobBountyReloaded.managers;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.inesgar.MobBountyReloaded.MobBountyReloaded;

public class MobBountyEconomy
{

	public MobBountyEconomy(MobBountyReloaded mbr)
	{
		setMBR(mbr);
		if (!setupEconomy())
		{
			getMBR().getServer().getPluginManager().disablePlugin(getMBR());
			return;
		}
		getMBR().getAPI().log(getEcon().getName() + " hooked.");
	}

	private MobBountyReloaded mbr;
	private Economy econ;

	/**
	 * Gets the economy plugin used by MBR.
	 * 
	 * @return Server economy plugin
	 */
	public Economy getEcon()
	{
		return econ;
	}

	private void setEcon(Economy econ)
	{
		this.econ = econ;
	}

	/**
	 * @return The copy of MBR used by the plugin.
	 */
	public MobBountyReloaded getMBR()
	{
		return mbr;
	}

	private void setMBR(MobBountyReloaded mbr)
	{
		this.mbr = mbr;
	}

	private boolean setupEconomy()
	{
		if (getMBR().getServer().getPluginManager().getPlugin("Vault") == null)
		{
			getMBR().getAPI().log(
					"Vault was not found. Plugin is being disabled.");
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = getMBR().getServer()
				.getServicesManager().getRegistration(Economy.class);
		if (rsp == null)
		{
			getMBR().getAPI().log(
					"No economy plugin found. Plugin is being disabled.");
			return false;
		}
		setEcon(rsp.getProvider());
		return econ != null;
	}

	public boolean payAccount(String accountName, double amount)
	{
		double amt = Math.abs(amount);
		EconomyResponse r = getEcon().depositPlayer(accountName, amt);
		return (r.transactionSuccess());
	}

	public boolean fineAccount(String accountName, double amount)
	{
		double amt = Math.abs(amount);
		EconomyResponse r = getEcon().withdrawPlayer(accountName, amt);
		return (r.transactionSuccess());
	}

	public String format(double amt)
	{
		return getEcon().format(amt);
	}

}
