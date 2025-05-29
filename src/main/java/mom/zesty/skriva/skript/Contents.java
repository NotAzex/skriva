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

public class Contents extends SimpleExpression<String> {

    static {
        Skript.registerExpression(Contents.class, String.class, ExpressionType.COMBINED,
                "[the] <content[s]|line[s]|text[s]> <of|from> [file] [<in|in path>] %string%");
    }

    private Expression<String> path;

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
        path = (Expression<String>) exprs[0];
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "The lines";
    }

    @Override
    @Nullable
    protected String[] get(Event event) {

        File file = Skriva.getInstance().getFileManager().getFile(path.getSingle(event));
        if (file != null) {
            return Skriva.getInstance().getFileManager().getContent(file).toArray(new String[0]);
        }
        return null;

    }

}
