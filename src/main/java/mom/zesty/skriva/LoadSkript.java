package mom.zesty.skriva;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import org.bukkit.Bukkit;

import java.io.IOException;

public class LoadSkript {

    private SkriptAddon addon;

    public void load() {

        if (Bukkit.getPluginManager().isPluginEnabled("Skript")) {
            addon = Skript.registerAddon(Skriva.getInstance());

            try {
                Skriva.getInstance().getLogger().info("Loading Skript elements!");
                addon.loadClasses("mom.zesty.skriva.skript");
            } catch (IOException e) {
                Skriva.getInstance().getLogger().info("Failed to load Skript elements due to " + e.getMessage());
            }

        } else {
            Skriva.getInstance().getLogger().info("Skript wasn't detected, this plugin will not do anything.");
        }

    }

}
