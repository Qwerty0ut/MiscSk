package me.qwerty.miscsk;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class MiscSk extends JavaPlugin {

    MiscSk instance;
    SkriptAddon addon;

    @Override
    public void onEnable() {
        instance = this;
        addon = Skript.registerAddon(this);
        try {
            addon.loadClasses("me.qwerty.miscsk", "elements");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bukkit.getLogger().info("[MiscSk] Has been enabled!");
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("[MiscSk] Has been disabled :(. See ya!");
    }

    public MiscSk getInstance() {
        return instance;
    }

    public SkriptAddon getAddonInstance() {
        return addon;
    }
}
