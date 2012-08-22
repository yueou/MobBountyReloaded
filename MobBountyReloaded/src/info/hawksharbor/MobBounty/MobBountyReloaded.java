package info.hawksharbor.MobBounty;

import info.hawksharbor.MobBounty.Utils.MobBountyAPI;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

public class MobBountyReloaded extends JavaPlugin
{
	public static final Logger _logger = Logger.getLogger("Minecraft");

	private static MobBountyAPI _apiManager;

	public static MobBountyAPI getAPI()
	{
		return _apiManager;
	}

	public MobBountyAPI getAPIManager()
	{
		return _apiManager;
	}

	@Override
	public void onDisable()
	{
		_logger.info("[" + this.getDescription().getName() + "] v"
				+ this.getDescription().getVersion() + " disabled.");
	}

	@Override
	public void onEnable()
	{
		_apiManager = new MobBountyAPI(this);
		_logger.info("[" + getDescription().getName() + "] build "
				+ _apiManager.getPluginVersion() + " enabled.");
	}
}
