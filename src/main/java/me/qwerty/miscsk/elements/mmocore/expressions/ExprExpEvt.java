package me.qwerty.miscsk.elements.mmocore.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import net.Indyuce.mmocore.api.event.PlayerExperienceGainEvent;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class ExprExpEvt extends SimpleExpression {

    static {
        Skript.registerExpression(ExprExpEvt.class, Number.class, ExpressionType.COMBINED,
                "[event-]experience");
    }

    @Override
    protected Number[] get(Event e) {
        return new Number[]{ ((PlayerExperienceGainEvent) e).getExperience() };
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
        return "Experience involved in MMOCore Player XP Gain " + ((PlayerExperienceGainEvent) e).getExperience();
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        if (!getParser().isCurrentEvent(PlayerExperienceGainEvent.class)) {
            Skript.error("You can't use that expression outside mmocore player xp gain");
            return false;
        }
        return false;
    }

    @Override
    public @Nullable
    Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode.equals(Changer.ChangeMode.RESET) || mode.equals(Changer.ChangeMode.SET)) {
            return CollectionUtils.array(Number.class);
        }
        return null;
    }

    @Override
    public void change(Event e, @Nullable Object[] delta, Changer.ChangeMode mode) {
        int experience = ((Number) delta[0]).intValue();
        switch (mode) {
            case RESET:
                ((PlayerExperienceGainEvent) e).setExperience(0);
                break;
            default:
                ((PlayerExperienceGainEvent) e).setExperience(experience);
        }
    }
}
