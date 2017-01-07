package be.gerard.calculation.model;

/**
 * Term
 *
 * @author bartgerard
 * @version v0.0.1
 */
public interface Term {

    int X = Type.X.ordinal();
    int Y = Type.Y.ordinal();
    int OUT = Type.OUT.ordinal();

    /**
     * X (operator) Y = OUT
     */
    enum Type {
        X,
        Y,
        OUT
    }

}
