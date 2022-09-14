
import Destination.DestinationFinale;
import Emetteur.EmetteurAnalogique;
import GenerateurBruit.BruitGaussien;
import GenerateurBruit.GenerateurRetard;
import GenerateurSignal.BadEntrySignal;
import GenerateurSignal.DestinationGenerateurInterface;
import GenerateurSignal.SignalNRZ;
import GenerateurSignal.SignalNRZT;
import GenerateurSignal.SignalRZ;
import GenerateurSignal.SourceGenerateurInterface;
import Information.Information;
import Information.InformationNonConforme;
import OutilsTest.calculTEB;
import Recepteurs.RecepteurAnalogique;
import Sonde.SondeAnalogique;
import Sonde.SondeLogique;
import Source.Source;
import Source.SourceFixe;
import Transducteurs.TransducteurEmission;
import Transducteurs.TransducteurReception;
import Transmetteur.TransmetteurAnalogiqueBruite;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe de l'etape 4-5
 * @author Clément
 */
public class ApplicationTransmissionAnalogiqueBruiteeTrajetsMultiples {

    public static final int SEQ_DEFAULT_LENGTH = 100;
    private static float min;
    private static float max;
    private static int nbEch;
    private static Source srcFixe;
    private static boolean sonde = false;
    private static boolean transducteur = false;
    private static SourceGenerateurInterface srcGen;
    private static DestinationGenerateurInterface dstGen;
    private static float snr;
    /*
     * Exemple trajets multiples
     * ______________________
     * | Retard | Amplitude |
     * |--------|-----------|
     * |    5   |   0,5     |
     * |--------|-----------|
     * |    5   |   0,5     |
     * |--------|-----------|
     * |    5   |   0,5     |
     * |--------|-----------|
     * |    5   |   0,5     |
     * |--------|-----------|
     * |    5   |   0,5     |
     * |--------|-----------|
     */
    public static int[] trajetsMultiplesRetard = {0,0,0,0,0};
    public static float[] trajetsMultiplesAmplitude = {0,0,0,0,0};
    public static EmetteurAnalogique emetteurAnalogique;
    public static TransmetteurAnalogiqueBruite transmetteurBruiteBBAG;
    public static TransmetteurAnalogiqueBruite transmetteurBruiteTrajets;
    public static RecepteurAnalogique recepteurAnalogique;
    public static DestinationFinale destinationFinale;
    public static SondeLogique sondeLogiqueEntree;
    public static SondeLogique sondeLogiqueSortie;
    public static SondeAnalogique sondeAnalogiqueEntree;
    public static SondeAnalogique sondeAnalogiqueSortie;
    public static TransducteurEmission transducteurEmission;
    public static TransducteurReception transducteurReception;
    public static Information<Boolean> informationSynchro = new Information<>();
    
    
    
    /**
     * Main de l'etape 4-5
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        parseArgs(args);
        
        try {
            emetteurAnalogique = new EmetteurAnalogique(srcGen, nbEch);
            
            //Création du bruit blanc additif gaussien
            BruitGaussien bruit = new BruitGaussien();
            bruit.setSNR(snr);
            //Création du transmetteur associé
            transmetteurBruiteBBAG=new TransmetteurAnalogiqueBruite(bruit);
            
            //Création des trajets multiples
            GenerateurRetard retards = new GenerateurRetard(trajetsMultiplesRetard,trajetsMultiplesAmplitude);
            //Création du transmetteur associé
            transmetteurBruiteTrajets = new TransmetteurAnalogiqueBruite(retards);
           
            recepteurAnalogique = new RecepteurAnalogique(dstGen, nbEch);
            destinationFinale = new DestinationFinale(); 
            
            
            //Si présence de transducteurs
            if (transducteur){
                transducteurEmission = new TransducteurEmission();
                transducteurReception = new TransducteurReception();
                
                
                srcFixe.connecter(transducteurEmission);
                transducteurEmission.connecter(emetteurAnalogique);
                emetteurAnalogique.connecter(transmetteurBruiteTrajets);
                
                transmetteurBruiteTrajets.connecter(transmetteurBruiteBBAG);
                transmetteurBruiteBBAG.connecter(recepteurAnalogique);
                
                recepteurAnalogique.connecter(transducteurReception);
                transducteurReception.connecter(destinationFinale);
            }
            else {
                srcFixe.connecter(emetteurAnalogique);
                emetteurAnalogique.connecter(transmetteurBruiteTrajets);
                
                transmetteurBruiteTrajets.connecter(transmetteurBruiteBBAG);
                transmetteurBruiteBBAG.connecter(recepteurAnalogique);
                
                recepteurAnalogique.connecter(destinationFinale);
            }
            
            try {
                //Envoi d'un bit de synchro
                informationSynchro.add(true);
                
                if(transducteur)
                    transducteurEmission.recevoir(informationSynchro);
                else
                    emetteurAnalogique.recevoir(informationSynchro);
                
                
                if (sonde)
                {
                    sondeLogiqueEntree = new SondeLogique("Sonde Logique Entree");
                    sondeLogiqueSortie = new SondeLogique("Sonde logique du recepteur");

                    sondeAnalogiqueEntree = new SondeAnalogique("Sonde Analogique de l'emetteur");
                    sondeAnalogiqueSortie = new SondeAnalogique("Sonde Analogique du Transmetteur");

                    srcFixe.connecter(sondeLogiqueEntree);
                    
                    emetteurAnalogique.connecter(sondeAnalogiqueEntree);
                    transmetteurBruiteBBAG.connecter(sondeAnalogiqueSortie);
                    
                    if(transducteur)
                        transducteurReception.connecter(sondeLogiqueSortie);
                    else
                        recepteurAnalogique.connecter(sondeLogiqueSortie);
                }
                
                //Envoi de l'information
                srcFixe.emettre();
            } catch (InformationNonConforme e) {
                e.printStackTrace();
                System.exit(1);
            }
            
            System.out.println("TEB : "
                    + calculTEB.calculTauxErreurBinaire(srcFixe,
                    destinationFinale));
        } catch (BadEntrySignal e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }
    
    

    private static void parseArgs(String[] args) {
        String param = "";
        for (int j = 0; j < args.length; j++) {
            param = param + " " + args[j];
        }

        Pattern pSequence = Pattern.compile("-[Mm]ess ([0-9]+)");
        Matcher mSequence = pSequence.matcher(param);
        if (mSequence.find()) {
            String sequence = mSequence.group(1);

            if (sequence.matches("[01]{7,}")) {
                srcFixe = new SourceFixe(sequence);
            } else if (sequence.matches("\\d+")) {
                srcFixe = new SourceFixe(Integer.parseInt(sequence));
            } else {
                printConsigne();
            }
        }
        else
            srcFixe = new SourceFixe(SEQ_DEFAULT_LENGTH);

        Pattern pSampling = Pattern.compile("-[Nn]bEch ([0-9]+)");
        Matcher mSampling = pSampling.matcher(param);
        if (mSampling.find()) {
            nbEch = new Integer(mSampling.group(1));
        } else {
            nbEch=10;
        }

        Pattern pAmpl = Pattern
                .compile("-[Aa]mpl ([-]{0,1}[0-9]+) ([-]{0,1}[0-9]+)");
        Matcher mAmpl = pAmpl.matcher(param);
        if (mAmpl.find()) {
            min = new Float(mAmpl.group(1));
            max = new Float(mAmpl.group(2));
        } else {
            min = 0.0f;
            max = 1.0f;
        }

        Pattern pCoding = Pattern.compile("-[Ff]orm (\\w+)");
        Matcher mCoding = pCoding.matcher(param);
        if (mCoding.find()) {
            srcGen = null;
            dstGen = null;
            String coding = mCoding.group(1);
            if (coding.equals("NRZT")) {
                SignalNRZT nrzt = new SignalNRZT(nbEch, min, max);
                srcGen = nrzt;
                dstGen = nrzt;

            } else if (coding.equals("NRZ")) {
                SignalNRZ nrz = new SignalNRZ(nbEch, min, max);
                srcGen = nrz;
                dstGen = nrz;
            } else if (coding.equals("RZ")) {
                SignalRZ rz = new SignalRZ(nbEch, min, max);
                srcGen = rz;
                dstGen = rz;
            } else if(coding.length() > 0) {
                printConsigne();
            }
            else
            {
                SignalRZ rz = new SignalRZ(nbEch, min, max);
                srcGen = rz;
                dstGen = rz;
            }


        }else
        {
            SignalRZ rz = new SignalRZ(nbEch, min, max);
            srcGen = rz;
            dstGen = rz;
        }

        Pattern pSnr = Pattern.compile("-[Ss]nr ([-]{0,1}\\w+)");
        Matcher mSnr = pSnr.matcher(param);
        if (mSnr.find()) {

            snr = Float.parseFloat(mSnr.group(1));
        } else {
            snr=0.7f;
        }

        Pattern sCoding = Pattern.compile("-[Ss][ ]|-[Ss]$");
        Matcher msCoding = sCoding.matcher(param);
        if (msCoding.find()) {
            sonde = true;
        } else {
            sonde = false;
        }
        
        Pattern tMultiple1 = Pattern.compile("-[Tt]i ([1-5]+) ([0-9]+) ([0|1]*\\.[0-9]*)");
        Matcher mtMultiple1 = tMultiple1.matcher(param);
        Pattern tMultiple2 = Pattern.compile("-[Tt]i[ ]|-[Tt]i$");
        Matcher mtMultiple2 = tMultiple2.matcher(param);
        boolean tiOk = true;
        
        for(int i=0 ; i<5 ; i++){
            if(mtMultiple2.find()){
                if(mtMultiple1.find()){
                    //Un bon trajet à été trouvé
                    tiOk=true;
                    Integer indice=new Integer(mtMultiple1.group(1));
                    trajetsMultiplesRetard[indice-1]= new Integer(mtMultiple1.group(2));
                    trajetsMultiplesAmplitude[indice-1]= new Float(mtMultiple1.group(3));
                }
                else{
                    //Mauvaise syntaxe sur le -ti
                    tiOk=false;
                }
            }
        }
        if(!tiOk){
            printConsigne();
        }
        
        Pattern pTransducteur = Pattern.compile("-[Tt]ransducteur[ ]|-[Tt]ransducteur$");
        Matcher mTransducteur = pTransducteur.matcher(param);
        if (mTransducteur.find()) {
            transducteur = true;
        } else {
            transducteur = false;
        }
    }
    
    /**
     * Methode d'affichage des consignes
     */

    public static void printConsigne() {
        System.out.println("Transmetteur analogique bruité avec trajets multiples : \n");
        System.out.println("Guide d'utilisation du programme : ");
        System.out
                .println("\"-ampl min max\" Tension minimum  et maximum (entier ou flottant)");
        System.out.println("\"-nbEch\" Nombre d'echantillons par bit (entier)");
        System.out.println("\"-form\" Choix du codage entre NRZ NZRT et RZ");
        System.out
                .println("\"-mess\" sequence a transmettre, soit une chaine de \"0\" et \"1\" (au moins 7 symboles ) ou un nombre entier de bits a generer");
        System.out.println("Parametres optionnels:");
        System.out
                .println("\"-s\" Pour afficher les courbes caracteristiques a differents moments de la transmission");
        System.out.println("\"-snr s\" spre´cise la valeur du rapport signal sur bruit (SNR)");
        System.out.println(". Un flottant ");
        System.out.println("\"-ti i dt ar\" ième trajet multiple, avec dt le decalage en nombre d'echantillons et ar son amplitude.");
        System.exit(1);
    }
}
