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
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class ExprExp extends SimpleExpression {

    static {
        Skript.registerExpression(ExprExp.class, Number.class, ExpressionType.COMBINED,
                "[the] MMO[Core] exp[erience] of %player%",
                "[the] %player%'s MMO[Core] exp[erience]");
    }

    private Expression<Player> player;
    private Expression<Location> loc;

    @Override
    protected Number[] get(Event e) {
        Player p = player.getSingle(e);
        if (loc != null) {
            Location l = loc.getSingle(e);
        }
        if (p != null) {
            return new Number[]{PlayerData.get(p.getUniqueId()).getExperience()};
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
        return "Expression related to MMOCore Experience";
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
        PlayerData data = PlayerData.get(player.getSingle(e).getUniqueId());
        Number exp = (Number) delta[0];
        switch (mode) {
            case ADD:
                data.giveExperience(exp.intValue(), EXPSource.OTHER);
                break;
            case REMOVE:
                data.setExperience(data.getExperience() - exp.intValue());
                break;
            case RESET:
                data.setExperience(0);
                break;
            default:
                data.setExperience(exp.intValue());
        }
    }
}
