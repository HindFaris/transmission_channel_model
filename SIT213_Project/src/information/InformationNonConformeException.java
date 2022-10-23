package information;

/**
 * Une exception pour les informations non conformes
 * @author jerom
 *
 */
public class InformationNonConformeException extends Exception {

	private static final long serialVersionUID = 1917L;

	/**
	 * Le constructeur
	 */
	public InformationNonConformeException() {
		super();
	}

	/**
	 * Le constructeur
	 * @param motif l'explication justifiant la levee d'exception
	 */
	public InformationNonConformeException(String motif) {
		super(motif);
	}
}
