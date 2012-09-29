package info.hawksharbor.MobBounty.Managers;

import info.hawksharbor.MobBounty.MobBountyReloaded;
import info.hawksharbor.MobBounty.Commands.MBCheckDrop;
import info.hawksharbor.MobBounty.Commands.MBCheckExperience;
import info.hawksharbor.MobBounty.Commands.MBEnvMulti;
import info.hawksharbor.MobBounty.Commands.MBExperience;
import info.hawksharbor.MobBounty.Commands.MBGeneral;
import info.hawksharbor.MobBounty.Commands.MBGroupMulti;
import info.hawksharbor.MobBounty.Commands.MBLoad;
import info.hawksharbor.MobBounty.Commands.MBModifyDrop;
import info.hawksharbor.MobBounty.Commands.MBReward;
import info.hawksharbor.MobBounty.Commands.MBSave;
import info.hawksharbor.MobBounty.Commands.MBTimeMulti;
import info.hawksharbor.MobBounty.Commands.MBWorldExperience;
import info.hawksharbor.MobBounty.Commands.MBWorldModifyDrop;
import info.hawksharbor.MobBounty.Commands.MBWorldMulti;
import info.hawksharbor.MobBounty.Commands.MBWorldReward;
import info.hawksharbor.MobBounty.Commands.MobBounty;

public class MobBountyCommands
{

	private final MBCheckDrop _mbCheckDrop;
	private final MBCheckExperience _mbCheckXP;
	private final MBEnvMulti _mbEnvMulti;
	private final MBGeneral _mbGeneral;
	private final MBGroupMulti _mbGroupMulti;
	private final MBLoad _mbLoad;
	private final MBModifyDrop _mbModifyDrop;
	private final MBWorldModifyDrop _mbModifyWorldDrop;
	private final MBReward _mbReward;
	private final MBSave _mbSave;
	private final MBTimeMulti _mbTimeMulti;
	private final MBWorldMulti _mbWorldMulti;
	private final MBWorldReward _mbWorldReward;
	private final MBWorldExperience _mbWXP;
	private final MBExperience _mbXP;
	private final MobBounty _mobBounty;

	public MobBountyCommands(MobBountyReloaded plugin)
	{
		_mobBounty = new MobBounty(plugin);
		plugin.getCommand("mobbounty").setExecutor(_mobBounty);
		_mbEnvMulti = new MBEnvMulti(plugin);
		plugin.getCommand("mbenvmulti").setExecutor(_mbEnvMulti);
		_mbGeneral = new MBGeneral(plugin);
		plugin.getCommand("mbgeneral").setExecutor(_mbGeneral);
		_mbLoad = new MBLoad(plugin);
		plugin.getCommand("mbload").setExecutor(_mbLoad);
		_mbReward = new MBReward(plugin);
		plugin.getCommand("mbreward").setExecutor(_mbReward);
		_mbSave = new MBSave(plugin);
		plugin.getCommand("mbsave").setExecutor(_mbSave);
		_mbTimeMulti = new MBTimeMulti(plugin);
		plugin.getCommand("mbtimemulti").setExecutor(_mbTimeMulti);
		_mbWorldReward = new MBWorldReward(plugin);
		plugin.getCommand("mbworldreward").setExecutor(_mbWorldReward);
		_mbWorldMulti = new MBWorldMulti(plugin);
		plugin.getCommand("mbworldmulti").setExecutor(_mbWorldMulti);
		_mbGroupMulti = new MBGroupMulti(plugin);
		plugin.getCommand("mbgroupmulti").setExecutor(_mbGroupMulti);
		_mbModifyWorldDrop = new MBWorldModifyDrop(plugin);
		plugin.getCommand("mbworldmodifydrop").setExecutor(_mbModifyWorldDrop);
		_mbModifyDrop = new MBModifyDrop(plugin);
		plugin.getCommand("mbmodifydrop").setExecutor(_mbModifyDrop);
		_mbCheckDrop = new MBCheckDrop(plugin);
		plugin.getCommand("mbcheckdrop").setExecutor(_mbCheckDrop);
		_mbCheckXP = new MBCheckExperience(plugin);
		plugin.getCommand("mbcheckexperience").setExecutor(_mbCheckXP);
		_mbXP = new MBExperience(plugin);
		plugin.getCommand("mbexperience").setExecutor(_mbXP);
		_mbWXP = new MBWorldExperience(plugin);
		plugin.getCommand("mbworldexperience").setExecutor(_mbWXP);
	}
}
