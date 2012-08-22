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
import java.net.URL;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilderFactory;

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
	private double newVersion;
	private double currentVersion;

	private String v;

	private static HashMap<String, Long> loginTimer = new HashMap<String, Long>();

	public static MobBountyAPI instance;

	public MobBountyAPI(MobBountyReloaded plugin)
	{
		_plugin = plugin;
		currentVersion = Double.valueOf(_plugin.getDescription().getVersion()
				.split("-")[0].replaceFirst("\\.", ""));
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
										.logToConsole("Update MobBountyReloaded at: http://dev.bukkit.org/server-mods/mobbountyreloaded");
							}
						}
						catch (Exception e)
						{
							// ignore exceptions
						}
					}

				}, 0, 432000);
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

	public double updateCheck(double currentVersion) throws Exception
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
				return Double.valueOf(firstNodes.item(0).getNodeValue()
						.replace("MobBountyReloaded", "").replaceFirst(".", "")
						.trim());
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
}
