package mom.zesty.skriva;

import mom.zesty.skriva.managers.FileManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Skriva extends JavaPlugin {

    private FileManager fileManager;
    private final LoadSkript loading = new LoadSkript();
    private static Skriva instance;

    @Override
    public void onEnable() {
        fileManager = new FileManager();
        instance = this;
        loading.load();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Skriva getInstance() {
        return instance;
    }

    public FileManager getFileManager() {
        return fileManager;
    }

}
