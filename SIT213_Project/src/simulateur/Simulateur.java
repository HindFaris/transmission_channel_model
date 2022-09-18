package simulateur;
import destinations.Destination;
import destinations.DestinationFinale;
import sources.*;
import transmetteurs.Transmetteur;
import transmetteurs.TransmetteurAnalogiqueParfait;
import visualisations.*;
import information.*;
import recepteur.*;
import emetteur.*;
/** La classe Simulateur permet de construire et simuler une chaîne de
 * transmission composée d'une Source, d'un nombre variable de
 * Transmetteur(s) et d'une Destination.
 * @author cousin
 * @author prou
 *
 */
public class Simulateur {

	public int getNbEchantillon() {
		return nbEchantillon;
	}

	public void setNbEchantillon(int nbEchantillon) {
		this.nbEchantillon = nbEchantillon;
	}

	public float getMin() {
		return min;
	}

	public void setMin(float min) {
		this.min = min;
	}

	public float getMax() {
		return max;
	}

	public void setMax(float max) {
		this.max = max;
	}

	public String getFormSignal() {
		return formSignal;
	}

	public void setFormSignal(String formSignal) {
		this.formSignal = formSignal;
	}

	public Integer getSeed() {
		return seed;
	}

	public void setSeed(Integer seed) {
		this.seed = seed;
	}

	public int getNbBitsMess() {
		return nbBitsMess;
	}

	public void setNbBitsMess(int nbBitsMess) {
		this.nbBitsMess = nbBitsMess;
	}

	public String getMessageString() {
		return messageString;
	}

	public void setMessageString(String messageString) {
		this.messageString = messageString;
	}

	public Source<Boolean> getSource() {
		return source;
	}

	public void setSource(Source<Boolean> source) {
		this.source = source;
	}

	public Transmetteur<Float, Float> getTransmetteurAnalogiqueParfait() {
		return transmetteurAnalogiqueParfait;
	}

	public void setTransmetteurAnalogiqueParfait(Transmetteur<Float, Float> transmetteurAnalogiqueParfait) {
		this.transmetteurAnalogiqueParfait = transmetteurAnalogiqueParfait;
	}

	public Destination<Boolean> getDestination() {
		return destination;
	}

	public void setDestination(Destination<Boolean> destination) {
		this.destination = destination;
	}

	public Transmetteur<Boolean, Float> getEmetteurAnalogique() {
		return emetteurAnalogique;
	}

	public void setEmetteurAnalogique(Transmetteur<Boolean, Float> emetteurAnalogique) {
		this.emetteurAnalogique = emetteurAnalogique;
	}

	public Transmetteur<Float, Boolean> getRecepteur() {
		return recepteur;
	}

	public void setRecepteur(Transmetteur<Float, Boolean> recepteur) {
		this.recepteur = recepteur;
	}

	public void setAffichage(boolean affichage) {
		this.affichage = affichage;
	}

	public void setMessageAleatoire(boolean messageAleatoire) {
		this.messageAleatoire = messageAleatoire;
	}

	public void setAleatoireAvecGerme(boolean aleatoireAvecGerme) {
		this.aleatoireAvecGerme = aleatoireAvecGerme;
	}


	public float getSNRParBit() {
		return SNRParBit;
	}

	public void setSNRParBit(float sNRParBit) {
		SNRParBit = sNRParBit;
	}

	public boolean isBruitActif() {
		return bruitActif;
	}

	public void setBruitActif(boolean bruitActif) {
		this.bruitActif = bruitActif;
	}


	/** indique le nombre d'échantillon à utiliser */
	private int nbEchantillon=30;

	/** indique le minimum en emplitude*/
	private float min=0.0f;

	/** indique le maximum en emplitude*/
	private float max=1.0f;

	
	private String formSignal="RZ";

	/** indique si le Simulateur utilise des sondes d'affichage */
	private boolean affichage = false;

	/** indique si le Simulateur utilise un message généré de manière aléatoire (message imposé sinon) */
	private boolean messageAleatoire = true;

	/** indique si le Simulateur utilise un germe pour initialiser les générateurs aléatoires */
	private boolean aleatoireAvecGerme = false;

	/** la valeur de la semence utilisée pour les générateurs aléatoires */
	private Integer seed = 0; // pas de semence par défaut

	/** la longueur du message aléatoire à transmettre si un message n'est pas imposé */
	private int nbBitsMess = 100;

	/** la chaîne de caractères correspondant à m dans l'argument -mess m */
	private String messageString = "100";


	/** le  composant Source de la chaine de transmission */
	private Source <Boolean>  source = null;

	/** le  composant Transmetteur parfait logique de la chaine de transmission */
	private Transmetteur <Float, Float>  transmetteurAnalogiqueParfait = null;

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
	 * Un simple getter qui renvoie la taille du mot  reçu à la destiation
	 * @return int 
	 * 			la longueur du mot reçu
	 */
	public int getTailleMotDestination(){
		return destination.getLongueurInformationRecue();
	}

	/**
	 * Un simple getter qui renvoie un booléen disant si le message est aléatoire ou non
	 * @return -boolean
	 * 			vrai si le message est aléatoire. Faux sinon
	 */
	public boolean getMessageAleatoire() {
		return messageAleatoire;
	}

	/**
	 * Un simple getter qui renvoie un booleéen disant si le message a une germe ou non
	 * @return -boolean
	 * 			vrai si le message contient une germe. Faux sinon
	 */
	public boolean getAleatoireAvecGerme() {
		return aleatoireAvecGerme;
	}

	/**
	 * Un simple getter qui renvoie un booleéen disant si les sondes sont actives
	 * @return -boolean
	 * 			vrai si les sondes sont actives. Faux sinon
	 */
	public boolean getAffichage() {
		return affichage;
	}
	/** Le constructeur de Simulateur construit une chaîne de
	 * transmission composée d'une Source <Boolean>, d'une Destination
	 * <Boolean> et de Transmetteur(s) [voir la méthode
	 * analyseArguments]...  <br> Les différents composants de la
	 * chaîne de transmission (Source, Transmetteur(s), Destination,
	 * Sonde(s) de visualisation) sont créés et connectés.
	 * @param args le tableau des différents arguments.
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
		//permet d'initialiser les éléments de la chaine
		emetteurAnalogique = new EmetteurAnalogique(formSignal, nbEchantillon, min, max, SNRParBit, bruitActif);
		transmetteurAnalogiqueParfait = new TransmetteurAnalogiqueParfait();
		recepteur = new Recepteur(nbEchantillon,min,max,formSignal);
		destination = new DestinationFinale();
		//permet de connecter les sondes
		
		//permet de connecter les éléments de la chaine entre eux
		if(affichage) {
			source.connecter(new SondeLogique("Source", 200));
		}
		source.connecter(emetteurAnalogique);
		if(affichage) {
			emetteurAnalogique.connecter(new SondeAnalogique("Emetteur Analogique"));
		}
		emetteurAnalogique.connecter(transmetteurAnalogiqueParfait);
		if(affichage) {
			transmetteurAnalogiqueParfait.connecter(new SondeAnalogique("Transmetteur Analogique parfait"));
		}
		transmetteurAnalogiqueParfait.connecter(recepteur);
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


	/** La méthode analyseArguments extrait d'un tableau de chaînes de
	 * caractères les différentes options de la simulation.  <br>Elle met
	 * à jour les attributs correspondants du Simulateur.
	 *
	 * @param args le tableau des différents arguments.
	 * <br>
	 * <br>Les arguments autorisés sont : 
	 * <br> 
	 * <dl>
	 * <dt> -mess m  </dt><dd> m (String) constitué de 7 ou plus digits à 0 | 1, le message à transmettre</dd>
	 * <dt> -mess m  </dt><dd> m (int) constitué de 1 à 6 digits, le nombre de bits du message "aléatoire" à transmettre</dd> 
	 * <dt> -s </dt><dd> pour demander l'utilisation des sondes d'affichage</dd>
	 * <dt> -seed v </dt><dd> v (int) d'initialisation pour les générateurs aléatoires</dd> 
	 * <dt> -form v </dt><dd> v (String) d'initialiser le type du codage du signal à emettre</dd> 
	 * <dt> -nbEch v </dt><dd> v (int) choisir le nombre d'échantillon permettront à coder le signal à emettre</dd> 
	 * <dt> -ampl v </dt><dd> v (int) choisir l'amplitude du signal à emettre</dd> 
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
				// traiter la valeur associéé
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
				else if (args[i].matches("[0-9]{1,6}")) { // de 1 à 6 chiffres
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
				if(formSignal.equals("NRZT"))
					if(nbEchantillon%3 != 0)
						throw new ArgumentsException("le nombre d'echantillon pour generer un NRZT doit etre multiple de 3 : ");
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
				if( nbEchantillon <= 0)
					throw new ArgumentsException("Le SNR par bit doit �tre un nombre strictement positif");
				SNRParBit = Float.valueOf(args[i]);
			}
		}
	}

	/** La méthode execute effectue un envoi de message par la source
	 * de la chaîne de transmission du Simulateur, en passant
	 * par les différents éléments de la chaine
	 * @throws Exception si un problème survient lors de l'exécution
	 *
	 */ 

	public void execute() throws Exception {  
		
		long debut = System.currentTimeMillis();
		source.emettre();
		long fin = System.currentTimeMillis();
		//System.out.println("il a fallu " + (fin-debut) + " millisecondes pour faire source.emettre");
		
		//debut = System.currentTimeMillis();
		emetteurAnalogique.emettre();
		//fin = System.currentTimeMillis();
		//System.out.println("il a fallu " + (fin-debut) + " millisecondes pour faire emetteur.emettre");
		
		//debut = System.currentTimeMillis();
		transmetteurAnalogiqueParfait.emettre();
		//fin = System.currentTimeMillis();
		//System.out.println("il a fallu " + (fin-debut) + " millisecondes pour faire tansmetteur.emettre");
		
		//debut = System.currentTimeMillis();
		recepteur.emettre();
		//fin = System.currentTimeMillis();
		//System.out.println("il a fallu " + (fin-debut) + " millisecondes pour faire tansmetteur.emettre");
	}


	/** La méthode qui calcule le taux d'erreur binaire en comparant
	 * les bits du message émis avec ceux du message reçu.
	 * @return  La valeur du Taux dErreur Binaire.
	 */   	   

	public float  calculTauxErreurBinaire() {

		Information <Boolean> chaineEmise = source.getInformationEmise();
		Information <Boolean> chaineRecue = destination.getInformationRecue();
		int nbVariablesDifferentes = 0;
		for(int indice = 0; indice < source.getInformationEmise().nbElements(); indice++) {
			if ((chaineEmise.iemeElement(indice) != chaineRecue.iemeElement(indice))) {
				nbVariablesDifferentes += 1;
			}
		}
		return  (float)nbVariablesDifferentes/source.getInformationEmise().nbElements();
	}


	/** La fonction main instancie un Simulateur à l'aide des
	 *  arguments paramètres et affiche le résultat de l'exécution
	 *  d'une transmission.
	 *  @param args les différents arguments qui serviront à l'instanciation du Simulateur.
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
			for (int i = 0; i < args.length; i++) { //copier tous les paramètres de simulation
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