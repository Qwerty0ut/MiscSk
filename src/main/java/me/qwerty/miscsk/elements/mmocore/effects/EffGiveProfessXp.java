package me.qwerty.miscsk.elements.mmocore.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import net.Indyuce.mmocore.api.player.PlayerData;
import net.Indyuce.mmocore.experience.EXPSource;
import net.Indyuce.mmocore.experience.PlayerProfessions;
import net.Indyuce.mmocore.experience.Profession;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.io.File;

public class EffGiveProfessXp extends Effect {

    static {
        Skript.registerEffect(EffGiveProfessXp.class,
                "give %number% (amount of|quantity of) mmo[core] exp[erience] for profess[ion] %string% (of|from) %player% with hologram at %location%");
    }

    private Expression<Number> expQuantity;
    private Expression<String> professId;
    private Expression<Player> playerExpr;
    private Expression<Location> locExpr;

    @Override
    protected void execute(Event e) {
        Double xp = expQuantity.getSingle(e).doubleValue();
        PlayerProfessions playerProfessions = PlayerData.get(playerExpr.getSingle(e).getUniqueId()).getCollectionSkills();
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(new File("plugins/MMOCore/professions/" + professId.getSingle(e) + ".yml"));
        try {
            playerProfessions.giveExperience(new Profession(professId.getSingle(e), yml), xp, EXPSource.OTHER, locExpr.getSingle(e));
        } catch (Exception ex) {
            Bukkit.getLogger().info(ex.getMessage());
        }
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "give " + expQuantity.getSingle(e) + " amount of exp to " + playerExpr.getSingle(e) + " for profession " + professId.getSingle(e) + " with hologram at " + locExpr.getSingle(e);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        expQuantity = (Expression<Number>) exprs[0];
        professId = (Expression<String>) exprs[1];
        playerExpr = (Expression<Player>) exprs[2];
        locExpr = (Expression<Location>) exprs[3];
        return true;
    }
}

