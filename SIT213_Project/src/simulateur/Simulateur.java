package simulateur;
import destinations.Destination;
import destinations.DestinationFinale;
import sources.*;
import transmetteurs.Transmetteur;
import transmetteurs.TransmetteurAnalogiqueBruite;
import transmetteurs.TransmetteurAnalogiqueParfait;
import visualisations.*;
import information.*;
import recepteur.*;
import emetteur.*;
/** La classe Simulateur permet de construire et simuler une chaine de
 * transmission composee d'une Source, d'un nombre variable de
 * Transmetteur(s) et d'une Destination.
 * @author cousin
 * @author prou
 *
 */
public class Simulateur {


	/** @return le nombre d'echantillon a utiliser */
	public int getNbEchantillon() {
		return nbEchantillon;
	}

	public float getMin() {
		return min;
	}

	public float getMax() {
		return max;
	}

	public String getFormSignal() {
		return formSignal;
	}

	public Integer getSeed() {
		return seed;
	}

	public int getNbBitsMess() {
		return nbBitsMess;
	}

	public String getMessageString() {
		return messageString;
	}

	public Source<Boolean> getSource() {
		return source;
	}

	public Transmetteur<Float, Float> getTransmetteurAnalogiqueParfait() {
		return transmetteurAnalogiqueParfait;
	}

	public Destination<Boolean> getDestination() {
		return destination;
	}

	public Transmetteur<Boolean, Float> getEmetteurAnalogique() {
		return emetteurAnalogique;
	}

	public Transmetteur<Float, Boolean> getRecepteur() {
		return recepteur;
	}

	public float getSNRParBit() {
		return SNRParBit;
	}

	public boolean getBruitActif() {
		return bruitActif;
	}


	/** indique le nombre d'echantillon a utiliser */
	private int nbEchantillon=30;

	/** indique le minimum en amplitude*/
	private float min=0.0f;

	/** indique le maximum en amplitude*/
	private float max=1.0f;

	/** indique la forme du signal par defaut*/
	private String formSignal="RZ";

	/** indique si le Simulateur utilise des sondes d'affichage */
	private boolean affichage = false;

	/** indique si le Simulateur utilise un message genere de maniere aleatoire (message impose sinon)*/
	private boolean messageAleatoire = true;

	/** indique si le Simulateur utilise un germe pour initialiser les generateurs aleatoires */
	private boolean aleatoireAvecGerme = false;

	/** la valeur de la semence utilisee pour les generateurs aleatoires */
	private Integer seed = null; // pas de semence par defaut

	/** la longueur du message aleatoire a transmettre si un message n'est pas impose */
	private int nbBitsMess = 100;

	/** la chaine de caracteres correspondant m dans l'argument -mess m */
	private String messageString = "100";

	/** le  composant Source de la chaine de transmission */
	private Source <Boolean>  source = null;

	/** le  composant Transmetteur parfait logique de la chaine de transmission */
	private Transmetteur <Float, Float>  transmetteurAnalogiqueParfait = null;

	/** le  composant Transmetteur bruite logique de la chaine de transmission */
	private Transmetteur <Float, Float>  transmetteurAnalogiqueBruite = null;

	/** le  composant Destination de la chaine de transmission */
	private Destination <Boolean>  destination = null;

	/** le  composant Transmetteur parfait logique de la chaine de transmission */
	private Transmetteur <Boolean, Float>  emetteurAnalogique = null;

	/** le  composant Transmetteur parfait logique de la chaine de transmission */
	private Transmetteur <Float, Boolean >  recepteur = null;

	/**	 Le rapport signal sur bruit par bit*/
	private float SNRParBit = 0;

	/** indique si le Simulateur est bruite ou non */
	private boolean bruitActif = false;

	/**
	 * Un simple getter qui renvoie la taille du mot  recu a la destiation
	 * @return int 
	 * 			la longueur du mot recu
	 */
	public int getTailleMotDestination(){
		return destination.getLongueurInformationRecue();
	}

	/**
	 * Un simple getter qui renvoie un booleen disant si le message est aleatoire ou non
	 * @return -boolean
	 * 			vrai si le message est aleatoire. Faux sinon
	 */
	public boolean getMessageAleatoire() {
		return messageAleatoire;
	}

	/**
	 * Un simple getter qui renvoie un booleen disant si le message a une germe ou non
	 * @return -boolean
	 * 			vrai si le message contient une germe. Faux sinon
	 */
	public boolean getAleatoireAvecGerme() {
		return aleatoireAvecGerme;
	}

	/**
	 * Un simple getter qui renvoie un booleen disant si les sondes sont actives
	 * @return -boolean
	 * 			vrai si les sondes sont actives. Faux sinon
	 */
	public boolean getAffichage() {
		return affichage;
	}
	/** Le constructeur de Simulateur construit une chaine de
	 * transmission composee d'une Source <Boolean>, d'une Destination
	 * <Boolean> et de Transmetteur(s) [voir la methode
	 * analyseArguments]...  <br> Les differents composants de la
	 * chaine de transmission (Source, Transmetteur(s), Destination,
	 * Sonde(s) de visualisation) sont cres et connectes.
	 * @param args le tableau des differents arguments.
	 *
	 * @throws ArgumentsException si un des arguments est incorrect
	 *
	 */  
	public  Simulateur(String [] args) throws ArgumentsException {

		analyseArguments(args);

		if(messageAleatoire == false) {
			source = new SourceFixe(messageString);    		
		}

		else if(aleatoireAvecGerme == true) {
			source = new SourceAleatoire(nbBitsMess, seed);
		}
		else {//inutile ?
			source = new SourceAleatoire(nbBitsMess,0);

		}
		//permet d'initialiser les elements de la chaine
		emetteurAnalogique = new EmetteurAnalogique(formSignal, nbEchantillon, min, max, SNRParBit, bruitActif);
		if(bruitActif) {
			transmetteurAnalogiqueBruite = new TransmetteurAnalogiqueBruite(nbEchantillon, SNRParBit, seed);
		}
		else {
			transmetteurAnalogiqueParfait = new TransmetteurAnalogiqueParfait();
		}
		recepteur = new Recepteur(nbEchantillon,min,max,formSignal);
		destination = new DestinationFinale();

		//permet de connecter les sondes


		//permet de connecter les elements de la chaine entre eux


		if(affichage) {
			source.connecter(new SondeLogique("Source", 200));
		}
		source.connecter(emetteurAnalogique);

		if(affichage) {
			emetteurAnalogique.connecter(new SondeAnalogique("Emetteur Analogique"));
		}

		if(bruitActif) {
			emetteurAnalogique.connecter(transmetteurAnalogiqueBruite);
		}
		else {
			emetteurAnalogique.connecter(transmetteurAnalogiqueParfait);
		}

		if(affichage) {
			if(bruitActif) {
				transmetteurAnalogiqueBruite.connecter(new SondeAnalogique("Transmetteur Analogique bruite"));
			}
			else {
				transmetteurAnalogiqueParfait.connecter(new SondeAnalogique("Transmetteur Analogique parfait"));
			}
		}

		if(bruitActif) {
			transmetteurAnalogiqueBruite.connecter(recepteur);
		}
		else {
			transmetteurAnalogiqueParfait.connecter(recepteur);
		}
		
		if(affichage) {
			recepteur.connecter(new SondeLogique("Recepteur", 200));
		}
		
		recepteur.connecter(destination);

		/*if (affichage) {
			source.connecter(new SondeLogique("Source", 200));
			emetteurAnalogique.connecter(new SondeAnalogique("Emetteur Analogique"));
			transmetteurAnalogiqueParfait.connecter(new SondeAnalogique("Transmetteur Analogique parfait"));
			recepteur.connecter(new SondeLogique("Recepteur", 200));
		}*/
	}


	/** La methode analyseArguments extrait d'un tableau de chaines de
	 * caracteres les differentes options de la simulation.  <br>Elle met
	 * a jour les attributs correspondants du Simulateur.
	 *
	 * @param args le tableau des differents arguments.
	 * <br>
	 * <br>Les arguments autorises sont : 
	 * <br> 
	 * <dl>
	 * <dt> -mess m  </dt><dd> m (String) constitue de 7 ou plus digits Ã  0 | 1, le message a transmettre</dd>
	 * <dt> -mess m  </dt><dd> m (int) constitue de 1 a 6 digits, le nombre de bits du message "aleatoire" a transmettre</dd> 
	 * <dt> -s </dt><dd> pour demander l'utilisation des sondes d'affichage</dd>
	 * <dt> -seed v </dt><dd> v (int) d'initialisation pour les generateurs aleatoires</dd> 
	 * <dt> -form v </dt><dd> v (String) d'initialiser le type du codage du signal a emettre</dd> 
	 * <dt> -nbEch v </dt><dd> v (int) choisir le nombre d'echantillon permettront a coder le signal a emettre</dd> 
	 * <dt> -ampl v </dt><dd> v (int) choisir l'amplitude du signal a emettre</dd> 
	 * </dl>
	 *
	 * @throws ArgumentsException si un des arguments est incorrect.
	 *
	 */   
	public void analyseArguments(String[] args)  throws  ArgumentsException {

		for (int i=0;i<args.length;i++){ // traiter les arguments 1 par 1

			if (args[i].matches("-s")){
				affichage = true;
			}

			else if (args[i].matches("-seed")) {
				aleatoireAvecGerme = true;
				i++; 
				// traiter la valeur associee
				try { 
					seed = Integer.valueOf(args[i]);
				}
				catch (Exception e) {
					throw new ArgumentsException("Valeur du parametre -seed  invalide :" + args[i]);
				}           		
			}

			else if (args[i].matches("-mess")){
				i++; 
				// traiter la valeur associee

				if (args[i].matches("[0,1]{7,}")) { // au moins 7 digits entre 1 et 0
					messageAleatoire = false;
					nbBitsMess = args[i].length();
					messageString=args[i];
				} 
				else if (args[i].matches("[0-9]{1,6}")) { // de 1 a 6 chiffres
					messageAleatoire = true;
					nbBitsMess = Integer.valueOf(args[i]);
					if (nbBitsMess < 1) 
						throw new ArgumentsException ("Valeur du parametre -mess invalide : " + nbBitsMess);
				}
				else 
					throw new ArgumentsException("Valeur du parametre -mess invalide : " + args[i]);
			}

			else if(args[i].matches("-form")){
				i++;
				if (args[i].matches("NRZ")) { 
					formSignal = "NRZ";
				} 
				else if (args[i].matches("NRZT")) { 
					formSignal = "NRZT";
				}
				else if (args[i].matches("RZ")) { 
					formSignal = "RZ";
				}
				else 
					throw new ArgumentsException("Valeur du parametre -form invalide : " + args[i]);

			}

			else if(args[i].matches("-nbEch")){
				i++; 
				nbEchantillon=Integer.valueOf(args[i]);
				if( nbEchantillon < 6)
					throw new ArgumentsException("Valeur du parametre -nbEch invalide, il faut rentrer un nombre plus grand que 6 : " + args[i]);
			}

			else if(args[i].matches("-ampl")){
				i++;
				min=Float.valueOf(args[i]);
				i++;
				max=Float.valueOf(args[i]);
				if(max<min)
					throw new ArgumentsException("Valeur du parametre -ampl invalide : " + args[i]);
			} 

			else if(args[i].matches("-snrpb")) {
				i++;
				bruitActif = true;
				SNRParBit = Float.valueOf(args[i]);
			}
		}
	}

	/** La methode execute effectue un envoi de message par la source
	 * de la chaine de transmission du Simulateur, en passant
	 * par les differents elements de la chaine
	 * @throws Exception si un probleme survient lors de l'execution
	 *
	 */ 

	public void execute() throws Exception {  

		//long debut = System.currentTimeMillis();
		source.emettre();
		//long fin = System.currentTimeMillis();
		//System.out.println("il a fallu " + (fin-debut) + " millisecondes pour faire source.emettre");
		System.out.println("fin source");
		//debut = System.currentTimeMillis();
		emetteurAnalogique.emettre();
		//fin = System.currentTimeMillis();
		//System.out.println("il a fallu " + (fin-debut) + " millisecondes pour faire emetteur.emettre");
		System.out.println("fin emission");
		//debut = System.currentTimeMillis();
		if(bruitActif) {
			transmetteurAnalogiqueBruite.emettre();
		}
		else {
			transmetteurAnalogiqueParfait.emettre();
		}
		System.out.println("fin transmission");
		//fin = System.currentTimeMillis();
		//System.out.println("il a fallu " + (fin-debut) + " millisecondes pour faire tansmetteur.emettre");

		//debut = System.currentTimeMillis();
		recepteur.emettre();
		System.out.println("fin reception");
		//long fin = System.currentTimeMillis();
		//System.out.println("il a fallu " + (fin-debut) + " millisecondes pour faire recepteur.emettre");
	}


	/** La methode qui calcule le taux d'erreur binaire en comparant
	 * les bits du message emis avec ceux du message recu.
	 * @return  La valeur du Taux dErreur Binaire.
	 */   	   

	public float  calculTauxErreurBinaire() {

		Information <Boolean> chaineEmise = source.getInformationEmise();
		Information <Boolean> chaineRecue = destination.getInformationRecue();
		System.out.println("taille cahine emise : " + chaineEmise.nbElements());
		
		System.out.println("taille cahine recue : " + chaineRecue.nbElements());

		int nbVariablesDifferentes = 0;
		for(int indice = 0; indice < source.getInformationEmise().nbElements(); indice++) {
			if ((chaineEmise.iemeElement(indice) != chaineRecue.iemeElement(indice))) {
				nbVariablesDifferentes += 1;
			}
		}
		return  (float)nbVariablesDifferentes/source.getInformationEmise().nbElements();
	}


	/** La fonction main instancie un Simulateur a l'aide des
	 *  arguments parametres et affiche le resultat de l'execution
	 *  d'une transmission.
	 *  @param args les differents arguments qui serviront a l'instanciation du Simulateur.
	 */

	public static void main(String [] args) { 
		Simulateur simulateur = null;
		try {
			simulateur = new Simulateur(args);
		}
		catch (ArgumentsException e) {
			System.out.println(e);
		} 

		try {
			simulateur.execute();
			String s = "java  Simulateur  ";
			for (int i = 0; i < args.length; i++) { //copier tous les paramÃ¨tres de simulation
				s += args[i] + "  ";
			}
			System.out.println(s + "  =>   TEB : " + simulateur.calculTauxErreurBinaire());
		}
		catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
			System.exit(-2);
		}
	}
}