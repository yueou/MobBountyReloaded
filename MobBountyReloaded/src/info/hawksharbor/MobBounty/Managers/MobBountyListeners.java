package info.hawksharbor.MobBounty.Managers;

import info.hawksharbor.MobBounty.MobBountyReloaded;
import info.hawksharbor.MobBounty.Listeners.MobBountyEntityListener;
import info.hawksharbor.MobBounty.Listeners.MobBountyPlayerListener;

public class MobBountyListeners
{

	private MobBountyEntityListener _entityListener;
	private MobBountyPlayerListener _playerListener;
	private MobBountyReloaded _plugin;

	public MobBountyListeners(MobBountyReloaded plugin)
	{
		_plugin = plugin;

		_entityListener = new MobBountyEntityListener(_plugin);
		_playerListener = new MobBountyPlayerListener(_plugin);
	}

	/**
	 * Gets MobBountyEntityListener
	 * 
	 * @return MobBountyEntityListener Listener that listens for entity events
	 */
	public MobBountyEntityListener getEntityListener()
	{
		return _entityListener;
	}

	/**
	 * Gets MobBountyPlayerListener
	 * 
	 * @return MobBountyPlayerListener Listener that listens for player events
	 */
	public MobBountyPlayerListener getPlayerListener()
	{
		return _playerListener;
	}

}
