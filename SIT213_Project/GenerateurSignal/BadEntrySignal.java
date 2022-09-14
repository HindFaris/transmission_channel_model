package GenerateurSignal;

/**
 *
 * @author Clément
 */
public class BadEntrySignal extends Exception {

    /**
     * Creates a new instance of
     * <code>BadEntriSignal</code> without detail message.
     */
    public BadEntrySignal() {
    }

    /**
     * Constructs an instance of
     * <code>BadEntriSignal</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public BadEntrySignal(String msg) {
        super(msg);
    }
}
