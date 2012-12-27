package com.conventnunnery.MobBountyReloaded.utils;

public class MobBountyPlayerKillData
{

    public double cacheEarned;
    public int cacheSize;
    public long cacheTime;
    public int killStreak;

    public MobBountyPlayerKillData()
    {
        killStreak = 0;
        cacheTime = System.currentTimeMillis();
        cacheEarned = 0.0;
        cacheSize = 0;
    }

}
