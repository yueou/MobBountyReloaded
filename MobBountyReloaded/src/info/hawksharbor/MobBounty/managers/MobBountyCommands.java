package info.hawksharbor.MobBounty.managers;

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

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public class MobBountyCommands
{

	private final MobBounty _mobBounty;
	private final MBEnvMulti _mbEnvMulti;
	private final MBGeneral _mbGeneral;
	private final MBLoad _mbLoad;
	private final MBReward _mbReward;
	private final MBSave _mbSave;
	private final MBTimeMulti _mbTimeMulti;
	private final MBWorldReward _mbWorldReward;
	private final MBWorldMulti _mbWorldMulti;
	private final MBGroupMulti _mbGroupMulti;
	private final MBWorldModifyDrop _mbModifyWorldDrop;
	private final MBCheckDrop _mbCheckDrop;
	private final MBModifyDrop _mbModifyDrop;
	private final MBCheckExperience _mbCheckXP;
	private final MBExperience _mbXP;
	private final MBWorldExperience _mbWXP;

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

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args)
	{
		if (sender instanceof ConsoleCommandSender)
		{
			sender.sendMessage("Commands are designed for in-game only.");
			return true;
		}
		if (command.getName().equalsIgnoreCase("mobbounty"))
		{
			return _mobBounty.onCommand(sender, command, label, args);
		}
		else if (command.getName().equalsIgnoreCase("mbenvmulti"))
		{
			return _mbEnvMulti.onCommand(sender, command, label, args);
		}
		else if (command.getName().equalsIgnoreCase("mbgeneral"))
		{
			return _mbGeneral.onCommand(sender, command, label, args);
		}
		else if (command.getName().equalsIgnoreCase("mbload"))
		{
			return _mbLoad.onCommand(sender, command, label, args);
		}
		else if (command.getName().equalsIgnoreCase("mbreward"))
		{
			return _mbReward.onCommand(sender, command, label, args);
		}
		else if (command.getName().equalsIgnoreCase("mbsave"))
		{
			return _mbSave.onCommand(sender, command, label, args);
		}
		else if (command.getName().equalsIgnoreCase("mbtimemulti"))
		{
			return _mbTimeMulti.onCommand(sender, command, label, args);
		}
		else if (command.getName().equalsIgnoreCase("mbworldreward"))
		{
			return _mbWorldReward.onCommand(sender, command, label, args);
		}
		else if (command.getName().equalsIgnoreCase("mbworldmulti"))
		{
			return _mbWorldMulti.onCommand(sender, command, label, args);
		}
		else if (command.getName().equalsIgnoreCase("mbgroupmulti"))
		{
			return _mbGroupMulti.onCommand(sender, command, label, args);
		}
		else if (command.getName().equalsIgnoreCase("mbcheckdrop"))
		{
			return _mbCheckDrop.onCommand(sender, command, label, args);
		}
		else if (command.getName().equalsIgnoreCase("mbworldmodifydrop"))
		{
			return _mbModifyWorldDrop.onCommand(sender, command, label, args);
		}
		else if (command.getName().equalsIgnoreCase("mbmodifydrop"))
		{
			return _mbModifyDrop.onCommand(sender, command, label, args);
		}
		else if (command.getName().equalsIgnoreCase("mbexperience"))
		{
			return _mbXP.onCommand(sender, command, label, args);
		}
		else if (command.getName().equalsIgnoreCase("mbworldexperience"))
		{
			return _mbWXP.onCommand(sender, command, label, args);
		}
		else if (command.getName().equalsIgnoreCase("mbcheckexperience"))
		{
			return _mbCheckXP.onCommand(sender, command, label, args);
		}

		return false;
	}
}
