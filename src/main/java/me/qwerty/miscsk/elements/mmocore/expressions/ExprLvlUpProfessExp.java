package me.qwerty.miscsk.elements.mmocore.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import net.Indyuce.mmocore.api.player.PlayerData;
import net.Indyuce.mmocore.experience.PlayerProfessions;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class ExprLvlUpProfessExp extends SimpleExpression {

    static {
        Skript.registerExpression(ExprLvlUpProfessExp.class, Number.class, ExpressionType.SIMPLE,
                "MMO[Core] exp[erience] [needed] to level up in profess[ion] %string% (of|from) %player%",
                "%player%'s exp[erience] [needed] to (level|lvl) up in profess[ion] %string%");
    }

    private Expression<Player> player;
    private Expression<String> profess;

    @Override
    protected Number[] get(Event e) {
        PlayerProfessions playerProfessions = PlayerData.get(player.getSingle(e).getUniqueId()).getCollectionSkills();
        return new Number[] { playerProfessions.getLevelUpExperience(profess.getSingle(e)) };
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
        return "MMOCore Profess experience needed to level up of " + player.getSingle(e);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        player = (Expression<Player>) exprs[0];
        profess = (Expression<String>) exprs[1];
        return true;
    }
}
