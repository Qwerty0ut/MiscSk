package me.qwerty.miscsk.elements.mmocore.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import net.Indyuce.mmocore.api.event.PlayerLevelUpEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class EvtPlayerLvlUp extends SkriptEvent {

    static {
        Skript.registerEvent("MMOCore Player Level Up", EvtPlayerLvlUp.class, PlayerLevelUpEvent.class,
                "mmo[core] player (level|lvl) up");
        EventValues.registerEventValue(PlayerLevelUpEvent.class, Player.class,
                new Getter<Player, PlayerLevelUpEvent>() {
                    @Override
                    public @Nullable Player get(PlayerLevelUpEvent arg) {
                        return arg.getPlayer();
                    }
                }, 0);
        EventValues.registerEventValue(PlayerLevelUpEvent.class, Number.class,
                new Getter<Number, PlayerLevelUpEvent>() {
                    @Override
                    public @Nullable Number get(PlayerLevelUpEvent arg) {
                        return arg.getOldLevel();
                    }
                }, -1);
        EventValues.registerEventValue(PlayerLevelUpEvent.class, Number.class,
                new Getter<Number, PlayerLevelUpEvent>() {
                    @Override
                    public @Nullable Number get(PlayerLevelUpEvent arg) {
                        return arg.getNewLevel();
                    }
                }, 0);
    }

    @Override
    public boolean init(Literal<?>[] args, int matchedPattern, SkriptParser.ParseResult parseResult) {
        return true;
    }

    @Override
    public boolean check(Event e) {
        return true;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "MMOCore Player level up event";
    }
}
