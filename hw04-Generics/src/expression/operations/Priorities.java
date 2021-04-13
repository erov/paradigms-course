package expression.operations;

public enum Priorities {
   ADD, MUL, NEGATE;

    private static final Priorities[] priorities = values();

    public Priorities next() {
        int pos = ordinal() + 1;
        return priorities[pos == priorities.length ? 0 : pos];
    }

    public static Priorities first() {
        return priorities[0];
    }

    public static Priorities last() {
        return priorities[priorities.length - 1];
    }
}
