package me.qwerty.miscsk.elements.types;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import net.Indyuce.mmocore.experience.EXPSource;
import org.jetbrains.annotations.Nullable;

public class ClassInfos {
    static {
        Classes.registerClass(new ClassInfo<>(EXPSource.class, "MMOCore Xp Source")
                .user("MMOCore XP Source?")
                .name("MMOCore XP Source")
                .description("Represents the XP Source from MMOCore Player XP Gain event.")
                .defaultExpression(new EventValueExpression<>(EXPSource.class))
                .parser(new Parser<EXPSource>() {

                    @Override
                    public String toString(EXPSource o, int flags) {
                        return o.name();
                    }

                    @Override
                    public String toVariableNameString(EXPSource o) {
                        return o.name();
                    }

                    @Override
                    @Nullable
                    public EXPSource parse(String input, ParseContext context) {
                        return EXPSource.valueOf(input);
                    }

                    @Override
                    public boolean canParse(ParseContext context) {
                        return true;
                    }
                }));
    }
}
