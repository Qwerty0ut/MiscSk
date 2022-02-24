package me.qwerty.miscsk.elements.mmocore.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import net.Indyuce.mmocore.api.event.PlayerCombatEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class EvtPlayerLeaveCombat extends SkriptEvent {

    static {
        Skript.registerEvent("Player Leave Combat", EvtPlayerLeaveCombat.class, PlayerCombatEvent.class,
                "[mmocore] player leave combat");
        EventValues.registerEventValue(PlayerCombatEvent.class, Player.class,
                new Getter<Player, PlayerCombatEvent>() {
                    @Override
                    public @Nullable Player get(PlayerCombatEvent arg) {
                        return arg.getPlayer();
                    }
                }, 0);
    }

    @Override
    public boolean init(Literal<?>[] args, int matchedPattern, SkriptParser.ParseResult parseResult) {
        return true;
    }

    @Override
    public boolean check(Event e) {
        return !((PlayerCombatEvent) e).entersCombat();
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "Player leave combat " + ((PlayerCombatEvent) e).getPlayer();
    }
}
