package mom.zesty.skriva.skript.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import mom.zesty.skriva.Skriva;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class FileExists extends SimpleExpression<Boolean> {

    static {
        Skript.registerExpression(FileExists.class, Boolean.class, ExpressionType.COMBINED,
                "[the] [file] %string% exist[s]");
    }

    private Expression<String> path;

    @Override
    public Class<? extends Boolean> getReturnType() {
        return Boolean.class;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
        path = (Expression<String>) exprs[0];
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "Status of a file";
    }

    @Override
    @Nullable
    protected Boolean[] get(Event event) {
        return new Boolean[]{Skriva.getInstance().getFileManager().fileExists(path.getSingle(event))};
    }

}
