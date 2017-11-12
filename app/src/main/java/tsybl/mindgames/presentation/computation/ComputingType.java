package tsybl.mindgames.presentation.computation;

/**
 * Created by Boolka4 on 12.11.2017.
 */

public enum ComputingType {
    ADDITION("+"), SUBTRACTION("-"), MULTIPLICATION("*"), DIVISION("/");

    private final String text;

    ComputingType(final String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }


    @Override
    public String toString() {
        return text;
    }
}
