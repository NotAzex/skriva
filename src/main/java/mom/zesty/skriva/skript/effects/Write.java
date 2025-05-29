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

public class Write extends Effect {

    static {
        Skript.registerEffect(Write.class,
                "write %string% <in|to> [file] %string%");
    }

    private Expression<String> text;
    private Expression<String> path;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
        text = (Expression<String>) exprs[0];
        path = (Expression<String>) exprs[1];
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "Write in file effect";
    }

    @Override
    protected void execute(Event event) {
        File file = Skriva.getInstance().getFileManager().getFile(path.getSingle(event));
        if (file != null) {
            Skriva.getInstance().getFileManager().writeInFile(file, text.getSingle(event));
        }
    }

}
