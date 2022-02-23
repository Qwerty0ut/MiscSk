package me.qwerty.miscsk.elements.mmocore.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import net.Indyuce.mmocore.api.event.PlayerResourceUpdateEvent;
import net.Indyuce.mmocore.api.player.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class ExprMMOCoreStamina extends SimpleExpression {

    static {
        Skript.registerExpression(ExprMMOCoreStamina.class, Number.class, ExpressionType.COMBINED,
                "[the] [MMO[Core]] stamina of [the] %player%",
                "%player%'s [MMO[Core]] stamina");
    }

    private Expression<Player> player;

    @Override
    protected Number[] get(Event e) {
        return new Number[]{ PlayerData.get(player.getSingle(e).getUniqueId()).getStamina() };
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
        return "Stamina of " + player.getSingle(e);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        player = (Expression<Player>) exprs[0];
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
        PlayerData data = PlayerData.get(player.getSingle(e).getUniqueId());
        Number stamina = (Number) delta[0];
        switch (mode) {
            case ADD:
                data.giveStamina(stamina.doubleValue(), PlayerResourceUpdateEvent.UpdateReason.OTHER);
                break;
            case REMOVE:
                data.setStamina(data.getStamina() - stamina.doubleValue());
                break;
            case RESET:
                data.setStamina(0);
                break;
            default:
                data.setStamina(stamina.doubleValue());
        }
    }
}
