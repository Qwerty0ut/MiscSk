package me.qwerty.miscsk.elements.mmocore.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import net.Indyuce.mmocore.api.player.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class ExprLvlUpExp extends SimpleExpression {

    static {
        Skript.registerExpression(ExprLvlUpExp.class, Number.class, ExpressionType.SIMPLE,
                "MMO[Core] exp[erience] [needed] to level up (of|from) %player%");
    }

    private Expression<Player> player;

    @Override
    protected Number[] get(Event e) {
        return new Number[] { PlayerData.get(player.getSingle(e).getUniqueId()).getLevelUpExperience() };
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
        return "MMOCore experience needed to level up of " + player.getSingle(e);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        player = (Expression<Player>) exprs[0];
        return true;
    }
}
