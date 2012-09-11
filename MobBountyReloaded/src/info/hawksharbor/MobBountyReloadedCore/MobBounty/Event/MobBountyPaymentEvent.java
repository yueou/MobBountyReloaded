package info.hawksharbor.MobBountyReloadedCore.MobBounty.Event;

import info.hawksharbor.MobBountyReloadedCore.MobBounty.Utils.MobBountyCreature;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MobBountyPaymentEvent extends Event implements Cancellable
{

	private static final HandlerList handlers = new HandlerList();

	private boolean cancelled;
	private String accountName;
	private double amount;
	private MobBountyCreature creature;

	public MobBountyPaymentEvent(String accountName, double amount,
			MobBountyCreature creature)
	{
		setAccountName(accountName);
		setAmount(amount);
		setCreature(creature);
	}

	@Override
	public boolean isCancelled()
	{
		return cancelled;
	}

	@Override
	public void setCancelled(boolean value)
	{
		cancelled = value;
	}

	@Override
	public HandlerList getHandlers()
	{
		return handlers;
	}

	public static HandlerList getHandlerList()
	{
		return handlers;
	}

	public String getAccountName()
	{
		return accountName;
	}

	public void setAccountName(String accountName)
	{
		this.accountName = accountName;
	}

	public double getAmount()
	{
		return amount;
	}

	public void setAmount(double amount)
	{
		this.amount = amount;
	}

	public MobBountyCreature getCreature()
	{
		return creature;
	}

	public void setCreature(MobBountyCreature creature)
	{
		this.creature = creature;
	}

}
