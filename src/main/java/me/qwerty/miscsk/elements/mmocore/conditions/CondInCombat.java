package me.qwerty.miscsk.elements.mmocore.conditions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import net.Indyuce.mmocore.api.player.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class CondInCombat extends Condition {

    static {
        Skript.registerCondition(CondInCombat.class,
                "%player% is in combat",
                "%player% is(n't| not) in combat");
    }

    private Expression<Player> player;
    private int pattern;

    @Override
    public boolean check(Event e) {
        if (player == null) return isNegated();
        if (pattern == 0) {
            return PlayerData.get(player.getSingle(e).getUniqueId()).isInCombat() ? !isNegated() : isNegated();
        } else {
            return PlayerData.get(player.getSingle(e).getUniqueId()).isInCombat() ? isNegated() : !isNegated();
        }
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "Player " + player.getSingle(e) + " is (not) in combat";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        player = (Expression<Player>) exprs[0];
        pattern = matchedPattern;
        return true;
    }
}
