package info.hawksharbor.MobBounty.Utils;

import info.hawksharbor.MobBounty.MetricsLite;
import info.hawksharbor.MobBounty.MobBountyReloaded;
import info.hawksharbor.MobBounty.managers.MobBountyCommands;
import info.hawksharbor.MobBounty.managers.MobBountyConfigs;
import info.hawksharbor.MobBounty.managers.MobBountyEcon;
import info.hawksharbor.MobBounty.managers.MobBountyExternals;
import info.hawksharbor.MobBounty.managers.MobBountyListeners;
import info.hawksharbor.MobBounty.managers.MobBountyLocale;
import info.hawksharbor.MobBounty.managers.MobBountyPermissions;

import java.io.IOException;
import java.util.HashMap;

public class MobBountyAPI
{

	public static MobBountyAPI getInstance()
	{
		return instance;
	}

	private MobBountyReloaded _plugin;
	private MobBountyConfigs _configManager;
	private MobBountyCommands _commandManager;
	private MobBountyLocale _localeManager;
	private MobBountyExternals _externalsManager;
	private MobBountyEcon _econManager;
	private MobBountyPermissions _permsManager;
	private MobBountyListeners _listenerManager;

	private String v;

	private static HashMap<String, Long> loginTimer = new HashMap<String, Long>();

	public static MobBountyAPI instance;

	public MobBountyAPI(MobBountyReloaded plugin)
	{
		_plugin = plugin;
		instance = this;
		v = _plugin.getDescription().getVersion();
		_econManager = new MobBountyEcon(_plugin);
		_configManager = new MobBountyConfigs(_plugin);
		_commandManager = new MobBountyCommands(_plugin);
		_localeManager = new MobBountyLocale(_plugin);
		_permsManager = new MobBountyPermissions(_plugin);
		_listenerManager = new MobBountyListeners(_plugin);
		_externalsManager = new MobBountyExternals(_plugin);
		getLoginTimer().clear();
		startStatistics();
	}

	public MobBountyCommands getCommandManager()
	{
		return _commandManager;
	}

	public MobBountyConfigs getConfigManager()
	{
		return _configManager;
	}

	public MobBountyEcon getEconManager()
	{
		return _econManager;
	}

	public MobBountyExternals getExternalsManager()
	{
		return _externalsManager;
	}

	public MobBountyListeners getListenerManager()
	{
		return _listenerManager;
	}

	public MobBountyLocale getLocaleManager()
	{
		return _localeManager;
	}

	public HashMap<String, Long> getLoginTimer()
	{
		return loginTimer;
	}

	public MobBountyPermissions getPermissionsManager()
	{
		return _permsManager;
	}

	public MobBountyReloaded getPlugin()
	{
		return _plugin;
	}

	public String getPluginVersion()
	{
		return v;
	}

	private void startStatistics()
	{
		try
		{
			MetricsLite metrics = new MetricsLite(_plugin);
			metrics.start();
		}
		catch (IOException ex)
		{
			MobBountyReloaded._logger
					.severe("[MobBountyReloaded] There was an error while submitting statistics.");
		}
	}
}
