package simulateur;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import destinations.Destination;
import destinations.DestinationFinale;
import sources.*;
import transmetteurs.*;
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

	/** le  composant Transmetteur Multi Trajets bruite logique de la chaine de transmission */
	private Transmetteur <Float, Float>  transmetteurAnalogiqueMultiTrajetsBruite = null;

	/** le  composant Transmetteur Multi Trajets logique de la chaine de transmission */
	private Transmetteur <Float, Float>  transmetteurAnalogiqueMultiTrajetsParfait = null;

	/** le  composant Destination de la chaine de transmission */
	private Destination <Boolean>  destination = null;

	/** le  composant emetteur analogique de la chaine de transmission */
	private Transmetteur <Boolean, Float>  emetteurAnalogique = null;

	/** le  composant recepteur parfait logique de la chaine de transmission */
	private Transmetteur <Float, Boolean >  recepteur = null;

	/**	 Le rapport signal sur bruit par bit*/
	private float SNRParBit = 0;

	/** Indique si le Simulateur est bruite ou non */
	private boolean bruitActif = false;

	/** Indique s'il y a des trajets indirects**/
	private boolean trajetIndirect = false;

	/** Amplitude du signal indirect **/
	private float alpha = 0.0f;

	/** Nb d'echantillon de retard**/
	private int tau = 0;
	
	/** Amplitude du signal indirect **/
	private LinkedList<Float> alphas = new LinkedList<Float>();

	/** Nb d'echantillon de retard**/
	private LinkedList<Integer> taus = new LinkedList<Integer>();

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

		//instanciation de la source
		if(!messageAleatoire) {
			source = new SourceFixe(messageString);    		
		}
		else {
			source = new SourceAleatoire(nbBitsMess, seed);
		}

		//instanciation du recepteur
		if (trajetIndirect) {
			recepteur = new RecepteurMultiTrajets(nbEchantillon, min, max, formSignal, alpha, tau);
		} else {
			recepteur = new Recepteur(nbEchantillon, min, max, formSignal);
		}

		//instanciation des equipements (emetteur, destination)
		emetteurAnalogique = new EmetteurAnalogique(formSignal, nbEchantillon, min, max);
		destination = new DestinationFinale();

		//connexion des equipements entre eux
		source.connecter(emetteurAnalogique);
		recepteur.connecter(destination);

		//instanciation du transmetteur
		if(bruitActif && trajetIndirect) {
			transmetteurAnalogiqueMultiTrajetsBruite = new TransmetteurAnalogiqueMultiTrajetsBruite(nbEchantillon, SNRParBit, seed, alpha, tau);
			emetteurAnalogique.connecter(transmetteurAnalogiqueMultiTrajetsBruite);
			transmetteurAnalogiqueMultiTrajetsBruite.connecter(recepteur);
		}else if (bruitActif) {
			transmetteurAnalogiqueBruite = new TransmetteurAnalogiqueBruite(nbEchantillon, SNRParBit, seed);
			emetteurAnalogique.connecter(transmetteurAnalogiqueBruite);
			transmetteurAnalogiqueBruite.connecter(recepteur);
		} else if (trajetIndirect) {
			//transmetteurAnalogiqueMultiTrajetsParfait = new TransmetteurAnalogiqueMultiTrajetsParfait(nbEchantillon, SNRParBit, seed, alpha, tau);
			transmetteurAnalogiqueMultiTrajetsParfait = new TransmetteurAnalogiqueMultiTrajetsParfait(alpha, tau);
			emetteurAnalogique.connecter(transmetteurAnalogiqueMultiTrajetsParfait);
			transmetteurAnalogiqueMultiTrajetsParfait.connecter(recepteur);	
		}
		else {
			transmetteurAnalogiqueParfait = new TransmetteurAnalogiqueParfait();
			emetteurAnalogique.connecter(transmetteurAnalogiqueParfait);
			transmetteurAnalogiqueParfait.connecter(recepteur);
		}

		//ajout des sondes
		if(affichage) {
			source.connecter(new SondeLogique("Source", 200));
			emetteurAnalogique.connecter(new SondeAnalogique("Emetteur Analogique"));
			if (bruitActif && trajetIndirect) {
				transmetteurAnalogiqueMultiTrajetsBruite.connecter(new SondeAnalogique("Transmetteur Analogique Multi-Trajet bruite"));
			}else if (bruitActif){
				transmetteurAnalogiqueBruite.connecter(new SondeAnalogique("Transmetteur Analogique bruite"));
			}
			else if (trajetIndirect) {
				transmetteurAnalogiqueMultiTrajetsParfait.connecter(new SondeAnalogique("Transmetteur Analogique Multi-Trajet Parfait"));
			} else {
				transmetteurAnalogiqueParfait.connecter(new SondeAnalogique("Transmetteur Analogique parfait"));
			}
			recepteur.connecter(new SondeLogique("Recepteur", 200));
		}
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
				if( nbEchantillon < 9)
					throw new ArgumentsException("Valeur du parametre -nbEch invalide, il faut rentrer un nombre strictement plus grand que 8 : " + args[i]);
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

			else if(args[i].matches("-ti")) {
				trajetIndirect = true;
				//TODO : gerer les parametres -> jusqu'a 5
				String argsString = null ;
				while (i < args.length) {
					argsString += "\t" +args[i] ;
					i++;
				}
				String regexString = "-ti\t(([0-9]{1,3}\t0.[0-9]\t{0,1}){1,5})";
				Pattern pattern = Pattern.compile(regexString);
				MatchResult matcher = pattern.matcher(argsString);
				String tiArgsString = null;
				while (((Matcher)matcher).find()) {
					tiArgsString = matcher.group(1);
		        }
				String[] tiArgsArray = tiArgsString.split("\t");
				for (int index = 0; index < tiArgsArray.length; index++) {
					System.out.println(tiArgsArray[index]);
				}
				for (int index=0; index<tiArgsArray.length; index++) {
					if(0<Float.valueOf(tiArgsArray[index]) && Float.valueOf(tiArgsArray[index]) <= 1) {
						alphas.add(Float.valueOf(tiArgsArray[index]));
						//alpha = Float.valueOf(tiArgsArray[index]);
					} else {
						taus.add(Integer.valueOf(tiArgsArray[index]));
						//tau = Integer.valueOf(tiArgsArray[index]);
					}
				}
				System.out.println(alphas);
				System.out.println(taus);
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
		source.emettre();
		emetteurAnalogique.emettre();
		if(bruitActif && trajetIndirect) {
			transmetteurAnalogiqueMultiTrajetsBruite.emettre();
		}
		else if (trajetIndirect) {
			transmetteurAnalogiqueMultiTrajetsParfait.emettre();
		}
		else if (bruitActif) {
				transmetteurAnalogiqueBruite.emettre();
		}
		else {
			transmetteurAnalogiqueParfait.emettre();
		}
		recepteur.emettre();
	}


	/** La methode qui calcule le taux d'erreur binaire en comparant
	 * les bits du message emis avec ceux du message recu.
	 * @return  La valeur du Taux dErreur Binaire.
	 */   	   

	public float  calculTauxErreurBinaire() {

		Information <Boolean> chaineEmise = source.getInformationEmise();
		Information <Boolean> chaineRecue = destination.getInformationRecue();

		int nbVariablesDifferentes = 0;
		int tailleMotBinaire = source.getInformationEmise().nbElements();
		for(int indice = 0; indice < tailleMotBinaire; indice++) {
			if ((chaineEmise.iemeElement(0) != chaineRecue.iemeElement(0))) {
				nbVariablesDifferentes += 1;
			}
			chaineEmise.remove(0);
			chaineRecue.remove(0);
		}
		return  (float)nbVariablesDifferentes/tailleMotBinaire;//test
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
			for (int i = 0; i < args.length; i++) { //copier tous les parametres de simulation
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

	public boolean getTrajetIndirect() {
		return trajetIndirect;
	}

	public float getAlpha() {
		return alpha;
	}

	public int getTau() {
		return tau;
	}
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


}