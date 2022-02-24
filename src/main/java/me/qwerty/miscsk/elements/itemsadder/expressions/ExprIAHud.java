package me.qwerty.miscsk.elements.itemsadder.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import dev.lone.itemsadder.api.FontImages.PlayerHudsHolderWrapper;
import dev.lone.itemsadder.api.FontImages.PlayerQuantityHudWrapper;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class ExprIAHud extends SimpleExpression {

    static {
        Skript.registerExpression(ExprIAHud.class, Number.class, ExpressionType.COMBINED,
                "[the] [ItemsAdder] %player%'s hud %string% quantity");
    }

    private Expression<Player> player;
    private Expression<String> hud;

    @Override
    protected Object[] get(Event e) {
        Player p = player.getSingle(e);
        PlayerQuantityHudWrapper hudWrapper = new PlayerQuantityHudWrapper(new PlayerHudsHolderWrapper(p), hud.getSingle(e));
        if (hudWrapper.exists()) {
            return new Number[]{ hudWrapper.getFloatValue() };
        }
        return null;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class getReturnType() {
        return Number.class;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "Expression related to Specific ItemsAdder hud of player";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        player = (Expression<Player>) exprs[0];
        hud = (Expression<String>) exprs[1];
        return true;
    }

    @Override
    public @Nullable
    Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode.equals(Changer.ChangeMode.ADD) || mode.equals(Changer.ChangeMode.REMOVE) || mode.equals(Changer.ChangeMode.RESET) || mode.equals(Changer.ChangeMode.SET)) {
            return CollectionUtils.array(Number.class);
        }
        return null;
    }

    @Override
    public void change(Event e, @Nullable Object[] delta, Changer.ChangeMode mode) {
        Player p = player.getSingle(e);
        String hudName = hud.getSingle(e);
        Number quantity = (Number) delta[0];
        PlayerQuantityHudWrapper hudWrapper = new PlayerQuantityHudWrapper(new PlayerHudsHolderWrapper(p), hudName);
        if (hudWrapper.exists()) {
            switch (mode) {
                case ADD:
                    hudWrapper.setFloatValue(hudWrapper.getFloatValue() + quantity.floatValue());
                    break;
                case REMOVE:
                    hudWrapper.setFloatValue(hudWrapper.getFloatValue() - quantity.floatValue());
                    break;
                case RESET:
                    hudWrapper.setFloatValue(0);
                    break;
                default:
                    hudWrapper.setFloatValue(quantity.floatValue());
            }
        }
    }
}
