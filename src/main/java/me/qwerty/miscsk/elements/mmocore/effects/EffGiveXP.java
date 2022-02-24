package me.qwerty.miscsk.elements.mmocore.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import net.Indyuce.mmocore.api.player.PlayerData;
import net.Indyuce.mmocore.api.util.MMOCoreUtils;
import net.Indyuce.mmocore.experience.EXPSource;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class EffGiveXP extends Effect {

    static {
        Skript.registerEffect(EffGiveXP.class,
                "give %number% (amount of|quantity of) mmo[core] exp[erience] to %player% with hologram at %location%");
    }

    private Expression<Number> expQuantity;
    private Expression<Player> playerExpr;
    private Expression<Location> locExpr;

    @Override
    protected void execute(Event e) {
        int xp = expQuantity.getSingle(e).intValue();
        PlayerData playerData = PlayerData.get(playerExpr.getSingle(e).getUniqueId());
        playerData.giveExperience(xp, EXPSource.OTHER);
        MMOCoreUtils.displayIndicator(locExpr.getSingle(e), "§e+" + xp + " EXP!");
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "give " + expQuantity.getSingle(e) + " amount of exp to " + playerExpr.getSingle(e);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        expQuantity = (Expression<Number>) exprs[0];
        playerExpr = (Expression<Player>) exprs[1];
        locExpr = (Expression<Location>) exprs[2];
        return true;
    }
}
