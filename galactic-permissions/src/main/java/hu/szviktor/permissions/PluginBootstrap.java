package hu.szviktor.permissions;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import hu.szviktor.permissions.handlers.PermissionHandler;

/**
 * A galactic-permissions egy "bare minimum" permission-kezelő plugin Bukkithoz.
 * Elsősorban saját részre készült, az én igényeim kielégítésére.
 * 
 * Biztosan kompatibilis verziók: (MC 1.4.7 - 1.12.2)<br>
 * Általam használt verzió: MC 1.7.9
 * 
 * @author szviktor
 *
 */

public class PluginBootstrap extends JavaPlugin {

	@NotNull
	private Logger logger;

	@NotNull
	private File groupsFile;

	@NotNull
	private File usersFile;

	@NotNull
	private FileConfiguration groupsConfig;

	@NotNull
	private FileConfiguration usersConfig;

	@NotNull
	private PermissionHandler permissionHandler;

	@Override
	public void onLoad() {
		this.logger = this.getLogger();

		this.groupsFile = new File(this.getDataFolder(), "groups.yml");
		this.usersFile = new File(this.getDataFolder(), "users.yml");

		this.logger.info("A plugin betöltése megkezdődött!");

		this.saveDefaultConfig();
		this.reloadConfig();

		if (!this.groupsFile.exists())
			this.saveResource(this.groupsFile.getName(), false);

		if (!this.usersFile.exists())
			this.saveResource(this.usersFile.getName(), false);

		this.groupsConfig = YamlConfiguration.loadConfiguration(this.groupsFile);
		this.usersConfig = YamlConfiguration.loadConfiguration(this.usersFile);

		this.permissionHandler = new PermissionHandler(this);
	}

	@Override
	public void onEnable() {
		this.permissionHandler.loadGroups();

		this.permissionHandler.initAutosave();

		this.logger.info("A plugin betöltése befejeződött!");
	}

	@Override
	public void onDisable() {
		this.logger.info("A plugin leállítása megkezdődött!");

		this.saveData(false);

		this.logger.info("A plugin sikeresen leállt!");
	}

	public @NotNull FileConfiguration getGroupsConfiguration() {
		return this.groupsConfig;
	}

	public @NotNull FileConfiguration getUsersConfiguration() {
		return this.usersConfig;
	}

	/**
	 * Játékosok és rangok adatainak elmentése a konfigfájlba.
	 * 
	 * @param silent - Visszajelzés a konzol felé.
	 */

	public void saveData(boolean silent) {
		try {
			this.groupsConfig.save(this.groupsFile);
			this.usersConfig.save(this.usersFile);

			if (!silent)
				this.logger.info("Adatok sikeresen elmentve!");
		} catch (IOException ex) {
			this.logger.severe("Nem sikerült az adatok elmentése: " + ex.getMessage());
		}
	}

}
