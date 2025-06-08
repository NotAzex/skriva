package mom.zesty.skriva;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import org.bukkit.Bukkit;

import java.io.IOException;

public class LoadSkript {

    private SkriptAddon addon;

    public void load() {

        Skriva instance = Skriva.getInstance();

        if (Bukkit.getPluginManager().isPluginEnabled("Skript")) {
            addon = Skript.registerAddon(instance);

            try {
                instance.getLogger().info("Loading Skript elements!");
                addon.loadClasses("mom.zesty.skriva.skript");
            } catch (IOException e) {
                instance.getLogger().info("Failed to load Skript elements due to " + e.getMessage());
            }

        } else {
            instance.getLogger().info("Skript wasn't detected, please install it to make use of this plugin/addon.");
        }

    }

}
