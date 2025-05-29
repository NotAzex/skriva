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

public class TextAfter extends SimpleExpression<String> {

    static {
        Skript.registerExpression(TextAfter.class, String.class, ExpressionType.COMBINED,
                "[the] text after %string% in [<string|text>] %string%");
    }

    private Expression<String> after;
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
        after = (Expression<String>) exprs[1];
        full = (Expression<String>) exprs[0];
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "The text after";
    }

    @Override
    @Nullable
    protected String[] get(Event event) {

        return new String[]{Skriva.getInstance().getFileManager().getTextAfter(
                after.getSingle(event),
                full.getSingle(event))};
    }

}
