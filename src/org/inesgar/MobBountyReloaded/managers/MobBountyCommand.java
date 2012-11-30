package org.inesgar.MobBountyReloaded.managers;

import org.inesgar.MobBountyReloaded.MobBountyReloaded;
import org.inesgar.MobBountyReloaded.commands.MBCheck;
import org.inesgar.MobBountyReloaded.commands.MBCmd;
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
    private MBLoad loadCMD;
    private MBSave saveCMD;
    private MBCheck checkCMD;
    private MBCmd cmdCMD;
    private MBReward rCMD;
    private MBWorldReward wrCMD;
    private MBGroupMulti gmCMD;
    private MBUserMulti umCMD;
    private MBTimeMulti tmCMD;
    private MBGeneral gCMD;

    public MobBountyCommand(MobBountyReloaded instance)
    {
        setPlugin(instance);
        loadCMD = new MBLoad(getPlugin());
        saveCMD = new MBSave(getPlugin());
        checkCMD = new MBCheck(getPlugin());
        cmdCMD = new MBCmd(getPlugin());
        rCMD = new MBReward(getPlugin());
        wrCMD = new MBWorldReward(getPlugin());
        gmCMD = new MBGroupMulti(getPlugin());
        umCMD = new MBUserMulti(getPlugin());
        tmCMD = new MBTimeMulti(getPlugin());
        gCMD = new MBGeneral(getPlugin());
        getPlugin().getCommand("mobbountyload").setExecutor(loadCMD);
        getPlugin().getCommand("mobbountysave").setExecutor(saveCMD);
        getPlugin().getCommand("mobbountycheck").setExecutor(checkCMD);
        getPlugin().getCommand("mobbounty").setExecutor(cmdCMD);
        getPlugin().getCommand("mobbountyreward").setExecutor(rCMD);
        getPlugin().getCommand("mobbountyworldreward").setExecutor(wrCMD);
        getPlugin().getCommand("mobbountygroupmulti").setExecutor(gmCMD);
        getPlugin().getCommand("mobbountyusermulti").setExecutor(umCMD);
        getPlugin().getCommand("mobbountytimemulti").setExecutor(tmCMD);
        getPlugin().getCommand("mobbountygeneral").setExecutor(gCMD);
    }

    public MobBountyReloaded getPlugin()
    {
        return plugin;
    }

    public void setPlugin(MobBountyReloaded plugin)
    {
        this.plugin = plugin;
    }

}
