package com.conventnunnery.MobBountyReloaded.managers;

import com.conventnunnery.MobBountyReloaded.MobBountyReloaded;
import com.conventnunnery.MobBountyReloaded.commands.MBCheck;
import com.conventnunnery.MobBountyReloaded.commands.MBCmd;
import com.conventnunnery.MobBountyReloaded.commands.MBEnvMulti;
import com.conventnunnery.MobBountyReloaded.commands.MBGeneral;
import com.conventnunnery.MobBountyReloaded.commands.MBGroupMulti;
import com.conventnunnery.MobBountyReloaded.commands.MBLoad;
import com.conventnunnery.MobBountyReloaded.commands.MBReward;
import com.conventnunnery.MobBountyReloaded.commands.MBSave;
import com.conventnunnery.MobBountyReloaded.commands.MBTimeMulti;
import com.conventnunnery.MobBountyReloaded.commands.MBUserMulti;
import com.conventnunnery.MobBountyReloaded.commands.MBWorldReward;

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
