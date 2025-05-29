package mom.zesty.skriva.skript;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import mom.zesty.skriva.Skriva;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class CreateFile extends Effect {

    static {
        Skript.registerEffect(CreateFile.class,
                "<create|make> file %string%");
    }

    private Expression<String> path;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
        path = (Expression<String>) exprs[0];
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "Make file effect";
    }

    @Override
    protected void execute(Event event) {
        Skriva.getInstance().getFileManager().createFile(path.getSingle(event));
    }

}
