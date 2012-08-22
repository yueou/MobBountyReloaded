package info.hawksharbor.MobBounty.managers;

import info.hawksharbor.MobBounty.MobBountyReloaded;
import info.hawksharbor.MobBounty.Listeners.MobBountyEntityListener;
import info.hawksharbor.MobBounty.Listeners.MobBountyPlayerListener;
import info.hawksharbor.MobBounty.Utils.MobBountyPlayerKillData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class MobBountyListeners
{

	private MobBountyReloaded _plugin;
	private MobBountyEntityListener _entityListener;
	private MobBountyPlayerListener _playerListener;

	private Map<String, MobBountyPlayerKillData> _playerData;
	private Map<LivingEntity, String> _deathNote;
	private Map<UUID, SpawnReason> _spawnReason;

	public MobBountyListeners(MobBountyReloaded plugin)
	{
		_plugin = plugin;

		_playerData = new HashMap<String, MobBountyPlayerKillData>();
		_deathNote = new HashMap<LivingEntity, String>();
		_spawnReason = new HashMap<UUID, SpawnReason>();

		_entityListener = new MobBountyEntityListener(_plugin);
		_playerListener = new MobBountyPlayerListener(_plugin);
	}

	public Map<LivingEntity, String> getDeathNote()
	{
		return _deathNote;
	}

	public MobBountyEntityListener getEntityListener()
	{
		return _entityListener;
	}

	public Map<String, MobBountyPlayerKillData> getPlayerData()
	{
		return _playerData;
	}

	public MobBountyPlayerListener getPlayerListener()
	{
		return _playerListener;
	}

	public Map<UUID, SpawnReason> getSpawnReason()
	{
		return _spawnReason;
	}

}
