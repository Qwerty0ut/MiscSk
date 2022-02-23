package me.qwerty.miscsk.elements.itemsadder.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import dev.lone.itemsadder.api.FontImages.PlayerHudWrapper;
import dev.lone.itemsadder.api.FontImages.PlayerHudsHolderWrapper;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class EffIAShowHideHud extends Effect {

    static {
        Skript.registerEffect(EffIAShowHideHud.class,
                "show [ItemsAdder] hud %string% [to|for] %players%",
                "hide [ItemsAdder] hud %string% [to|for] %players%");
    }

    private boolean show;
    private Expression<Player> player;
    private Expression<String> hud;

    @Override
    protected void execute(Event e) {
        if (player != null && (hud != null || hud.getSingle(e) != null)) {
            for (Player p : player.getAll(e)) {
                PlayerHudWrapper hudWrapper = new PlayerHudWrapper(new PlayerHudsHolderWrapper(p), hud.getSingle(e));
                if (hudWrapper.exists()) {
                    hudWrapper.setVisible(show);
                }
            }
        }
        return;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "Effect that allows you to show/hide a specific hud to specifics players";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        hud = (Expression<String>) exprs[0];
        player = (Expression<Player>) exprs[1];
        show = matchedPattern == 0 ? true : false;
        return true;
    }
}
