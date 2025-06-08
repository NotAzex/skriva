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

public class TextBefore extends SimpleExpression<String> {

    static {
        Skript.registerExpression(TextBefore.class, String.class, ExpressionType.COMBINED,
                "[the] text before %string% in [<string|text>] %string%");
    }

    private Expression<String> before;
    private Expression<String> full;

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
        before = (Expression<String>) exprs[1];
        full = (Expression<String>) exprs[0];
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "The text before";
    }

    @Override
    @Nullable
    protected String[] get(Event event) {
        String result = Skriva.getInstance().getFileManager().getTextBefore(
                before.getSingle(event),
                full.getSingle(event));

        if (result == null) {
            return null;
        }

        return new String[]{result};
    }

}
