package me.qwerty.miscsk;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import dev.lone.itemsadder.api.Events.ItemsAdderLoadDataEvent;
import org.jetbrains.annotations.NotNull;

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
