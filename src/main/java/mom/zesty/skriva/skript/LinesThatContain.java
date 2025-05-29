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

public class LinesThatContain extends SimpleExpression<Integer> {

    static {
        Skript.registerExpression(LinesThatContain.class, Integer.class, ExpressionType.COMBINED,
                "[the] lines <that|which> <contain|contains> %string% in [file] %string%");
    }

    private Expression<String> string;
    private Expression<String> path;

    @Override
    public Class<? extends Integer> getReturnType() {
        return Integer.class;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
        string = (Expression<String>) exprs[0];
        path = (Expression<String>) exprs[1];
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "The lines that contain something";
    }

    @Override
    @Nullable
    protected Integer[] get(Event event) {

        File file = Skriva.getInstance().getFileManager().getFile(path.getSingle(event));
        if (file != null) {
            return Skriva.getInstance().getFileManager().linesThatContainString(file, string.getSingle(event)).toArray(new Integer[0]);
        }
        return null;
    }

}
