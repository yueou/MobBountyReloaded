package com.conventnunnery.MobBountyReloaded.event;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDeathEvent;

import com.conventnunnery.MobBountyReloaded.utils.MobBountyCreature;

public class MobBountyCreatureDeathEvent extends Event
{
    private static final HandlerList handlers = new HandlerList();

    private final LivingEntity entity;
    private final MobBountyCreature creature;
    private final EntityDeathEvent entityDeathEvent;

    public MobBountyCreatureDeathEvent(LivingEntity entity,
            MobBountyCreature creature, EntityDeathEvent entityDeathEvent)
    {
        this.entity = entity;
        this.creature = creature;
        this.entityDeathEvent = entityDeathEvent;
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

    public LivingEntity getEntity()
    {
        return entity;
    }

    public MobBountyCreature getCreature()
    {
        return creature;
    }

    public EntityDeathEvent getEntityDeathEvent()
    {
        return entityDeathEvent;
    }
}
