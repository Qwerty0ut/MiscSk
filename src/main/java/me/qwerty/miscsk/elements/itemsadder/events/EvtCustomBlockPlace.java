package me.qwerty.miscsk.elements.itemsadder.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import ch.njol.util.Checker;
import dev.lone.itemsadder.api.Events.CustomBlockBreakEvent;
import dev.lone.itemsadder.api.Events.CustomBlockInteractEvent;
import dev.lone.itemsadder.api.Events.CustomBlockPlaceEvent;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public class EvtCustomBlockPlace extends SkriptEvent {

    static {
        Skript.registerEvent("Itemsadder Block Place",
                EvtCustomBlockPlace.class,
                CustomBlockPlaceEvent.class,
                "I[tems]A[dder] block place [of %-strings%]");
        EventValues.registerEventValue(CustomBlockBreakEvent.class, Block.class,
                new Getter<Block, CustomBlockBreakEvent>() {
                    @Override
                    public @Nullable Block get(CustomBlockBreakEvent evt) {
                        return evt.getBlock();
                    }
                }, 0);
        EventValues.registerEventValue(CustomBlockBreakEvent.class, Location.class,
                new Getter<Location, CustomBlockBreakEvent>() {
                    @Override
                    public @Nullable Location get(CustomBlockBreakEvent evt) {
                        return evt.getBlock().getLocation();
                    }
                }, 0);
        EventValues.registerEventValue(CustomBlockBreakEvent.class, String.class,
                new Getter<String, CustomBlockBreakEvent>() {
                    @Override
                    public @Nullable String get(CustomBlockBreakEvent evt) {
                        return evt.getNamespacedID();
                    }
                }, 0);
        EventValues.registerEventValue(CustomBlockBreakEvent.class, ItemStack.class,
                new Getter<ItemStack, CustomBlockBreakEvent>() {
                    @Override
                    public @Nullable ItemStack get(CustomBlockBreakEvent evt) {
                        return evt.getCustomBlockItem();
                    }
                }, 0);
        EventValues.registerEventValue(CustomBlockBreakEvent.class, Player.class,
                new Getter<Player, CustomBlockBreakEvent>() {
                    @Override
                    public @Nullable Player get(CustomBlockBreakEvent evt) {
                        return evt.getPlayer();
                    }
                }, 0);
    }

    Literal<String> blocksId;

    @Override
    public boolean init(Literal<?>[] args, int matchedPattern, SkriptParser.ParseResult parseResult) {
        blocksId = (Literal<String>) args[0];
        return true;
    }

    @Override
    public boolean check(Event e) {
        if (blocksId != null) {
            String customblock = ((CustomBlockPlaceEvent) e).getNamespacedID();
            return blocksId.check(e, new Checker<String>() {
                @Override
                public boolean check(String o) {
                    return o.equals(customblock);
                }
            });
        }
        return true;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "Items Adder custom block place " + blocksId.toString(e, debug);
    }
}
