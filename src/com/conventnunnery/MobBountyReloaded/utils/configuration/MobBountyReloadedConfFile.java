package com.conventnunnery.MobBountyReloaded.utils.configuration;

public enum MobBountyReloadedConfFile
{
    GENERAL("plugins/MobBountyReloaded/Core/General.yml"), KILLSTREAK(
            "plugins/MobBountyReloaded/Core/Killstreak.yml"), LOCALE(
            "plugins/MobBountyReloaded/Core/Locale.yml"), MULTIPLIERS(
            "plugins/MobBountyReloaded/Core/Multiplier.yml"), REWARDS(
            "plugins/MobBountyReloaded/Core/Reward.yml");

    public static MobBountyReloadedConfFile fromName(String name)
    {
        for (MobBountyReloadedConfFile id : MobBountyReloadedConfFile.values())
        {
            if (id.name().equalsIgnoreCase(name))
                return id;
        }

        return null;
    }

    private final String _path;

    private MobBountyReloadedConfFile(final String path)
    {
        _path = path;
    }

    public String getPath()
    {
        return _path;
    }
}
