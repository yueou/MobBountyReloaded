package info.hawksharbor.MobBounty.Utils;

import info.hawksharbor.MobBounty.MetricsLite;
import info.hawksharbor.MobBounty.MobBountyReloaded;
import info.hawksharbor.MobBounty.Managers.MobBountyCommands;
import info.hawksharbor.MobBounty.Managers.MobBountyConfigs;
import info.hawksharbor.MobBounty.Managers.MobBountyEcon;
import info.hawksharbor.MobBounty.Managers.MobBountyExternals;
import info.hawksharbor.MobBounty.Managers.MobBountyListeners;
import info.hawksharbor.MobBounty.Managers.MobBountyLocale;
import info.hawksharbor.MobBounty.Managers.MobBountyPermissions;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilderFactory;

import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
	private int newVersion;
	private int currentVersion;
	private double configVersion;

	private String v;

	private static HashMap<String, Long> loginTimer = new HashMap<String, Long>();

	public Map<UUID, SpawnReason> _spawnReason;

	public static MobBountyAPI instance;

	public MobBountyAPI(MobBountyReloaded plugin)
	{
		_plugin = plugin;
		currentVersion = (int) Math.round(Double.valueOf(_plugin
				.getDescription().getVersion().split("-")[0].replaceFirst(
				"\\.", "")));
		instance = this;
		v = _plugin.getDescription().getVersion();
		_econManager = new MobBountyEcon(_plugin);
		configVersion = new Double(2.0);
		_configManager = new MobBountyConfigs(_plugin);
		_commandManager = new MobBountyCommands(_plugin);
		_localeManager = new MobBountyLocale(_plugin);
		_permsManager = new MobBountyPermissions(_plugin);
		_listenerManager = new MobBountyListeners(_plugin);
		_externalsManager = new MobBountyExternals(_plugin);
		_spawnReason = new HashMap<UUID, SpawnReason>();
		getLoginTimer().clear();
		startStatistics();
		_plugin.getServer().getScheduler()
				.scheduleAsyncRepeatingTask(_plugin, new Runnable()
				{

					@Override
					public void run()
					{
						try
						{
							newVersion = updateCheck(currentVersion);
							if (newVersion > currentVersion)
							{
								MobBountyMessage
										.logToConsole("MobBountyReloaded v"
												+ newVersion
												+ " is out! You are running: MobBountyReloaded v"
												+ currentVersion);
								MobBountyMessage
										.logToConsole("Update MobBountyReloaded at: http://mbr.inesgar.org/");
							}
						}
						catch (Exception e)
						{
							// ignore exceptions
						}
					}

				}, 0, 432000);
		String confs = _configManager.getProperty(MobBountyConfFile.GENERAL,
				"configsVersion");
		if (confs != null
				&& (configVersion > MobBountyUtils.getDouble(confs,
						configVersion)))
		{
			MobBountyMessage
					.logWarningToConsole("Update your configuration files!");
		}
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

	public int updateCheck(int currentVersion) throws Exception
	{
		String pluginUrlString = "http://dev.bukkit.org/server-mods/mobbountyreloaded/files.rss";
		try
		{
			URL url = new URL(pluginUrlString);
			Document doc = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder()
					.parse(url.openConnection().getInputStream());
			doc.getDocumentElement().normalize();
			NodeList nodes = doc.getElementsByTagName("item");
			Node firstNode = nodes.item(0);
			if (firstNode.getNodeType() == 1)
			{
				Element firstElement = (Element) firstNode;
				NodeList firstElementTagName = firstElement
						.getElementsByTagName("title");
				Element firstNameElement = (Element) firstElementTagName
						.item(0);
				NodeList firstNodes = firstNameElement.getChildNodes();
				return (int) Math.round(Double.valueOf(firstNodes.item(0)
						.getNodeValue().replace("MobBountyReloaded", "")
						.replaceFirst(".", "").trim()));
			}
		}
		catch (Exception localException)
		{
		}
		return currentVersion;
	}

	public double getCurrentVersion()
	{
		return currentVersion;
	}

	public double getNewVersion()
	{
		return newVersion;
	}

	public double getConfigVersion()
	{
		return configVersion;
	}
}
