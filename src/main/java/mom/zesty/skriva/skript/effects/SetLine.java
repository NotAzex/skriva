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

public class SetLine extends Effect {

    private Expression<Integer> line;
    private Expression<String> path;
    private Expression<String> text;

    static {
        Skript.registerEffect(SetLine.class,
                "<set|put> line[s] %integers% in [file] %string% to %string%");
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
        line = (Expression<Integer>) exprs[0];
        path = (Expression<String>) exprs[1];
        text = (Expression<String>) exprs[2];
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "Set line(s) effect";
    }

    @Override
    protected void execute(Event event) {

        File file = Skriva.getInstance().getFileManager().getFile(path.getSingle(event));
        Integer[] lines = line.getArray(event);
        String content = text.getSingle(event);

        if (file != null && lines != null) {
            int[] lineNumbers = new int[lines.length];
            for (int i = 0; i < lines.length; i++) {
                lineNumbers[i] = lines[i] != null ? lines[i] : 0;
            }

            Skriva.getInstance().getFileManager().setLine(file, lineNumbers, content);
        }

    }

}