package hu.szviktor.permissions.handlers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.permissions.PermissionAttachment;
import org.jetbrains.annotations.NotNull;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import hu.szviktor.permissions.PluginBootstrap;
import hu.szviktor.permissions.action.ActionExecutor;
import hu.szviktor.permissions.action.ActionResponse;
import hu.szviktor.permissions.action.ActionResult;
import hu.szviktor.permissions.action.PermissionAction;
import hu.szviktor.permissions.permissible.Group;

public class PermissionHandler implements ActionExecutor {

	@NotNull
	private final HashMap<UUID, PermissionAttachment> permissions = Maps.newHashMap();

	@NotNull
	private final List<Group> groups = Lists.newArrayList();

	@NotNull
	private PluginBootstrap plugin;

	@NotNull
	private FileConfiguration config;

	@NotNull
	private FileConfiguration groupsConfig;

	@NotNull
	private FileConfiguration usersConfig;

	@NotNull
	private Logger logger;

	public PermissionHandler(@NotNull PluginBootstrap plugin) {
		Preconditions.checkArgument(plugin != null, "A Plugin nem lehet null!");

		this.plugin = plugin;
		this.config = plugin.getConfig();

		this.groupsConfig = plugin.getGroupsConfiguration();
		this.usersConfig = plugin.getUsersConfiguration();

		this.logger = plugin.getLogger();
	}

	/**
	 * Rangok adatainak betöltése a szerver indításakor.
	 */

	public void loadGroups() {
		this.logger.info("A rangok betöltése folyamatban!");

		ConfigurationSection groups = this.groupsConfig.getConfigurationSection("groups");
		Iterator<String> keys = groups.getKeys(false).iterator();

		while (keys.hasNext()) {
			String groupName = keys.next();
			ConfigurationSection group = groups.getConfigurationSection(groupName);

			String prefix = group.getString("prefix");
			String suffix = group.getString("suffix");
			boolean isDefault = group.getBoolean("default");
			List<String> permissions = group.getStringList("permissions");
			List<String> inheritances = group.getStringList("inheritances");

			Group groupObject = new Group(groupName, prefix, suffix, isDefault, permissions, inheritances);

			boolean success = this.groups.add(groupObject);

			if (!success) {
				this.logger.severe("Nem sikerült a rang '" + groupName + "' betöltése, mivel már be van töltve!");

				continue;
			}

			this.logger.info("Rang '" + groupName + "' sikeresen betöltve!");
		}

		this.logger.info("A rangok betöltése véget ért!");
	}

	/**
	 * Adatok automatikus mentése csendes módban.
	 */

	public void initAutosave() {
		long autosavePeriods = this.config.getLong("settings.autosave");

		Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, () -> {
			this.plugin.saveData(true);
		}, 0, 20 * autosavePeriods);
	}

	@Override
	public <T extends PermissionAction> void executeAction(@NotNull CommandSender executor,
			@NotNull CompletableFuture<T> source) {
		Preconditions.checkArgument(executor != null, "A művelet végrehajtója nem lehet null!");
		Preconditions.checkArgument(source != null, "A művelet nem lehet null!");

		source.thenAccept(action -> {
			ActionResponse response = action.getResponse();
			ActionResult result = response.getResult();
			ChatColor actionColor = ChatColor.GREEN;
			ChatColor messageColor = ChatColor.YELLOW;

			if (result == ActionResult.FAILURE)
				actionColor = messageColor = ChatColor.RED;

			executor.sendMessage(actionColor + result.getResultMessage());
			executor.sendMessage(messageColor + response.getMessage());
		});
	}

}
