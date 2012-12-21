package org.inesgar.MobBountyReloaded.managers;

import org.inesgar.MobBountyReloaded.MobBountyReloaded;
import org.inesgar.MobBountyReloaded.commands.MBCheck;
import org.inesgar.MobBountyReloaded.commands.MBCmd;
import org.inesgar.MobBountyReloaded.commands.MBEnvMulti;
import org.inesgar.MobBountyReloaded.commands.MBGeneral;
import org.inesgar.MobBountyReloaded.commands.MBGroupMulti;
import org.inesgar.MobBountyReloaded.commands.MBLoad;
import org.inesgar.MobBountyReloaded.commands.MBReward;
import org.inesgar.MobBountyReloaded.commands.MBSave;
import org.inesgar.MobBountyReloaded.commands.MBTimeMulti;
import org.inesgar.MobBountyReloaded.commands.MBUserMulti;
import org.inesgar.MobBountyReloaded.commands.MBWorldReward;

public class MobBountyCommand
{

    private MobBountyReloaded plugin;

    public MobBountyCommand(final MobBountyReloaded instance)
    {
        setPlugin(instance);
        getPlugin().getCommand("mobbountyload").setExecutor(
                new MBLoad(getPlugin()));
        getPlugin().getCommand("mobbountysave").setExecutor(
                new MBSave(getPlugin()));
        getPlugin().getCommand("mobbountycheck").setExecutor(
                new MBCheck(getPlugin()));
        getPlugin().getCommand("mobbounty").setExecutor(new MBCmd(getPlugin()));
        getPlugin().getCommand("mobbountyreward").setExecutor(
                new MBReward(getPlugin()));
        getPlugin().getCommand("mobbountyworldreward").setExecutor(
                new MBWorldReward(getPlugin()));
        getPlugin().getCommand("mobbountygroupmulti").setExecutor(
                new MBGroupMulti(getPlugin()));
        getPlugin().getCommand("mobbountyusermulti").setExecutor(
                new MBUserMulti(getPlugin()));
        getPlugin().getCommand("mobbountytimemulti").setExecutor(
                new MBTimeMulti(getPlugin()));
        getPlugin().getCommand("mobbountyenvmulti").setExecutor(
                new MBEnvMulti(getPlugin()));
        getPlugin().getCommand("mobbountygeneral").setExecutor(
                new MBGeneral(getPlugin()));
    }

    public MobBountyReloaded getPlugin()
    {
        return plugin;
    }

    public void setPlugin(final MobBountyReloaded plugin)
    {
        this.plugin = plugin;
    }

}
