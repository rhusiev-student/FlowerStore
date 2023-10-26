package flower.store;

public enum FlowerColor {
    /**
     * Flower colors.
     */
    RED("#FF0000"),
    BLUE("#0000FF");

    private final String stringRepresentation;

    FlowerColor(String stringRepresentation) {
        this.stringRepresentation = stringRepresentation;
    }

    public static FlowerColor fromString(String stringRepresentation) {
        for (FlowerColor color : FlowerColor.values()) {
            if (color.stringRepresentation.equals(stringRepresentation)
                    || color.name().equals(stringRepresentation)) {
                return color;
            }
        }
        throw new IllegalArgumentException(
            "No FlowerColor with string representation "
            + stringRepresentation);
    }

    @Override
    public String toString() {
        return stringRepresentation;
    }
}
