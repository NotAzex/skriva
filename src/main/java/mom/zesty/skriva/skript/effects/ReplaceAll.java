package mom.zesty.skriva.skript.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import mom.zesty.skriva.Skriva;
import org.bukkit.event.Event;

import javax.annotation.Nullable;
import java.io.File;

public class ReplaceAll extends Effect {

    static {
        Skript.registerEffect(ReplaceAll.class,
                "replace text %string% in file %string% with %string%");
    }

    private Expression<String> replace;
    private Expression<String> path;
    private Expression<String> text;


    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
        replace = (Expression<String>) exprs[0];
        path = (Expression<String>) exprs[1];
        text = (Expression<String>) exprs[2];
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "Replace all text in a file effect";
    }

    @Override
    protected void execute(Event event) {
        File file = Skriva.getInstance().getFileManager().getFile(path.getSingle(event));

        if (file != null) {
            Skriva.getInstance().getFileManager().replaceAll(file, replace.getSingle(event), text.getSingle(event));
        }

    }

}
