package mom.zesty.skriva.skript;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import mom.zesty.skriva.Skriva;
import org.bukkit.event.Event;

import javax.annotation.Nullable;
import java.io.File;

public class Line extends SimpleExpression<String> {

    static {
        Skript.registerExpression(Line.class, String.class, ExpressionType.COMBINED,
                "[the] line %integer% <from|in> [file] %string%");
    }

    private Expression<String> path;
    private Expression<Integer> line;

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
        line = (Expression<Integer>) exprs[0];
        path = (Expression<String>) exprs[1];
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "The specified line";
    }

    @Override
    @Nullable
    protected String[] get(Event event) {

        File file = Skriva.getInstance().getFileManager().getFile(path.getSingle(event));
        if (file != null) {
            return new String[]{Skriva.getInstance().getFileManager().readLine(file, line.getSingle(event))};
        }
        return null;
    }

}
