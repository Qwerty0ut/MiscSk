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
import net.Indyuce.mmocore.experience.PlayerProfessions;
import net.Indyuce.mmocore.experience.Profession;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.io.File;

public class ExprProfessLvl extends SimpleExpression {

    static {
        Skript.registerExpression(ExprProfessLvl.class, Number.class, ExpressionType.COMBINED,
                "%player%'s profess[ion] %string% mmo[core] level");
    }

    private Expression<Player> player;
    private Expression<String> profess;

    @Override
    protected Number[] get(Event e) {
        if (player.getSingle(e) != null) {
            PlayerProfessions playerProfessions = PlayerData.get(player.getSingle(e).getUniqueId()).getCollectionSkills();
            return new Number[]{ playerProfessions.getLevel(profess.getSingle(e)) };
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
        return "MMOCore Profession Experience";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        player = (Expression<Player>) exprs[0];
        profess = (Expression<String>) exprs[1];
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
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(new File("plugins/MMOCore/professions/" + profess.getSingle(e) + ".yml"));
        PlayerProfessions playerProfessions = PlayerData.get(player.getSingle(e).getUniqueId()).getCollectionSkills();
        Number lvl = (Number) delta[0];
        switch (mode) {
            case ADD:
                playerProfessions.giveLevels(new Profession(profess.getSingle(e), yml), lvl.intValue(), EXPSource.OTHER);
                break;
            case REMOVE:
                playerProfessions.takeLevels(new Profession(profess.getSingle(e), yml), lvl.intValue());
                break;
            case RESET:
                playerProfessions.setLevel(new Profession(profess.getSingle(e), yml), 0);
                break;
            default:
                playerProfessions.setLevel(new Profession(profess.getSingle(e), yml), lvl.intValue());
        }
    }
}
