package me.qwerty.miscsk.elements.itemsadder.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import dev.lone.itemsadder.api.FontImages.PlayerHudsHolderWrapper;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class EffIAUpdateHuds extends Effect {

    static {
        Skript.registerEffect(EffIAUpdateHuds.class,
                "update [ItemsAdder] huds (of|from) %players%");
    }

    private Expression<Player> player;

    @Override
    protected void execute(Event e) {
        if (player != null) {
            for (Player p : player.getAll(e)) {
                PlayerHudsHolderWrapper hudWrapper = new PlayerHudsHolderWrapper(p);
                hudWrapper.sendUpdate();
            }
        }
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "Effect related to ItemsAdder that reload every hud of a player";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        player = (Expression<Player>) exprs[0];
        return true;
    }
}
