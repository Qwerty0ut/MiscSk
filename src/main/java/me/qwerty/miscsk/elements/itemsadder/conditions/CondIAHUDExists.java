package me.qwerty.miscsk.elements.itemsadder.conditions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import dev.lone.itemsadder.api.FontImages.PlayerHudWrapper;
import dev.lone.itemsadder.api.FontImages.PlayerHudsHolderWrapper;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class CondIAHUDExists extends Condition {

    static {
        Skript.registerCondition(CondIAHUDExists.class,
                "hud %string% [of|from] %player% [does] exist[s]",
                "hud %string% [of|from] %player% does[n't| not] exist");
    }

    private String hud;
    private Expression<Player> player;
    private int pattern;

    @Override
    public boolean check(Event e) {
        if (hud == null) return isNegated();
        if (player == null) return isNegated();
        if (pattern == 0) {
            return new PlayerHudWrapper(new PlayerHudsHolderWrapper(player.getSingle(e)), hud).exists() ? !isNegated() : isNegated();
        } else {
            return new PlayerHudWrapper(new PlayerHudsHolderWrapper(player.getSingle(e)), hud).exists() ? isNegated() : !isNegated();
        }
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "hud " + hud + " of " + player.getSingle(e) + " does('nt) exist";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        hud = String.valueOf(exprs[0]);
        player = (Expression<Player>) exprs[1];
        return true;
    }
}
