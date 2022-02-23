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

public class ExprMMOCoreExp extends SimpleExpression {

    static {
        Skript.registerExpression(ExprMMOCoreExp.class, Number.class, ExpressionType.COMBINED,
                "[the] MMO[Core] exp[erience] of %player% [with hologram at %-location%]",
                "[the] %player%'s MMO[Core] exp[erience] [with hologram at %-location%]");
    }

    private Expression<Player> player;
    private Expression<Location> loc;

    @Override
    protected Object[] get(Event e) {
        Player p = player.getSingle(e);
        Location l = loc.getSingle(e);
        if (p != null) {
            return new Number[] { PlayerData.get(p.getUniqueId()).getExperience() };
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
        if (exprs.length > 1) {
            if (exprs[1] instanceof Location) {
                loc = (Expression<Location>) exprs[1];
            }
        }
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
                if (loc != null) {
                    data.giveExperience(exp.intValue(), EXPSource.OTHER, loc.getSingle(e), false);
                }  else {
                    data.giveExperience(exp.intValue(), EXPSource.OTHER);
                }
                break;
            case REMOVE:
                data.setExperience(data.getExperience() - exp.intValue());
                break;
            case RESET:
                data.setExperience(0);
                break;
            case SET:
                data.setExperience(exp.intValue());
                break;
            default:
                break;
        }
    }
}
