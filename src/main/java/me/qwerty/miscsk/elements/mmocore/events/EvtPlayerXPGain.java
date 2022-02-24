package me.qwerty.miscsk.elements.mmocore.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import net.Indyuce.mmocore.api.event.PlayerExperienceGainEvent;
import net.Indyuce.mmocore.experience.EXPSource;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class EvtPlayerXPGain extends SkriptEvent {

    static {
        Skript.registerEvent("MMOCore Player XP Gain", EvtPlayerXPGain.class, PlayerExperienceGainEvent.class,
                "mmo[core] player xp gain");
        EventValues.registerEventValue(PlayerExperienceGainEvent.class, Player.class,
                new Getter<Player, PlayerExperienceGainEvent>() {
                    @Override
                    public @Nullable Player get(PlayerExperienceGainEvent arg) {
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
        return true;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "Player XP Gain";
    }
}
