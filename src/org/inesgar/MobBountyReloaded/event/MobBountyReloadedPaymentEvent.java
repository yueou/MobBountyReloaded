package org.inesgar.MobBountyReloaded.event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.inesgar.MobBountyReloaded.utils.MobBountyCreature;

public class MobBountyReloadedPaymentEvent extends Event implements Cancellable
{

    private static final HandlerList handlers = new HandlerList();

    private boolean cancel = false;
    private String playerName;
    private MobBountyCreature creature;
    private double amount;

    public MobBountyReloadedPaymentEvent(String name, MobBountyCreature c,
            double amount)
    {
        setPlayerName(name);
        setCreature(c);
        setAmount(amount);
    }

    @Override
    public boolean isCancelled()
    {
        return cancel;
    }

    @Override
    public void setCancelled(boolean bln)
    {
        cancel = bln;
    }

    public static HandlerList getHandlerList()
    {
        return handlers;
    }

    @Override
    public HandlerList getHandlers()
    {
        return handlers;
    }

    public String getPlayerName()
    {
        return playerName;
    }

    public void setPlayerName(String playerName)
    {
        this.playerName = playerName;
    }

    public MobBountyCreature getCreature()
    {
        return creature;
    }

    public void setCreature(MobBountyCreature creature)
    {
        this.creature = creature;
    }

    public double getAmount()
    {
        return amount;
    }

    public void setAmount(double amount)
    {
        this.amount = amount;
    }

}
