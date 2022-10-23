package simulateur;
/**
 * classe d'exception
 */
public class ArgumentsException extends Exception {
    
    private static final long serialVersionUID = 1789L; 
	
    /**
     * @param s la String a verifier
     */
    public ArgumentsException(String s) {
	super(s);
    }
}
