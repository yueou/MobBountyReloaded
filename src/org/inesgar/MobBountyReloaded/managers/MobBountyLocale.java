package org.inesgar.MobBountyReloaded.managers;

import org.inesgar.MobBountyReloaded.MobBountyReloaded;
import org.inesgar.MobBountyReloaded.utils.configuration.MobBountyReloadedConfFile;

public class MobBountyLocale
{
    private final MobBountyReloaded _plugin;

    public MobBountyLocale(MobBountyReloaded plugin)
    {
        _plugin = plugin;
    }

    /**
     * Gets a string from Locale.yml
     * 
     * @param String
     *            Key to look for
     * @return String String requested
     */
    public String getString(String key)
    {
        String locale = _plugin.getConfigManager().getProperty(
                MobBountyReloadedConfFile.GENERAL, "locale");

        if (locale != null)
            locale = locale.toLowerCase();
        else
            locale = "en";

        String value = _plugin.getConfigManager().getProperty(
                MobBountyReloadedConfFile.LOCALE, locale + "." + key);

        if (value == null)
            value = _plugin.getConfigManager().getProperty(
                    MobBountyReloadedConfFile.LOCALE, "en." + key);

        if (value != null)
            value = value.replace('&', '\u00A7').replace("\u00A7\u00A7", "&");

        return value;
    }
}
