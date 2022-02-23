package me.qwerty.miscsk.elements.mmocore.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import net.Indyuce.mmocore.api.player.PlayerData;
import net.Indyuce.mmocore.experience.EXPSource;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class ExprMMOCoreLevel extends SimpleExpression<Number> {

    static {
        Skript.registerExpression(ExprMMOCoreLevel.class, Number.class, ExpressionType.COMBINED,
                "[the] MMO[Core] [lvl|level] of %player%",
                "[the] %player%'s MMO[Core] [lvl|level]");
    }

    private Expression<Player> player;

    @Override
    protected @Nullable Number[] get(Event e) {
        Player p = player.getSingle(e);
        if (p != null) {
            return new Number[]{PlayerData.get(p.getUniqueId()).getLevel()};
        }
        return null;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "Expression related to Player's Level from MMOCore";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        player = (Expression<Player>) exprs[0];
        return true;
    }

    @Override
    public @Nullable Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode.equals(Changer.ChangeMode.ADD) || mode.equals(Changer.ChangeMode.REMOVE) || mode.equals(Changer.ChangeMode.RESET) || mode.equals(Changer.ChangeMode.SET)) {
            return CollectionUtils.array(Number.class);
        }
        return null;
    }

    @Override
    public void change(Event e, @Nullable Object[] delta, Changer.ChangeMode mode) {
        Player p = player.getSingle(e);
        if (p != null) {
            PlayerData data = PlayerData.get(p.getUniqueId());
            Number levels = (Number) delta[0];
            if (mode.equals(Changer.ChangeMode.ADD)) {
                data.giveLevels(levels.intValue(), EXPSource.OTHER);
            } else if (mode.equals(Changer.ChangeMode.REMOVE)) {
                data.takeLevels(levels.intValue());
            } else if (mode.equals(Changer.ChangeMode.SET)) {
                data.setLevel(levels.intValue());
            } else if (mode.equals(Changer.ChangeMode.RESET)) {
                data.setLevel(0);
            }
        }
    }
}
