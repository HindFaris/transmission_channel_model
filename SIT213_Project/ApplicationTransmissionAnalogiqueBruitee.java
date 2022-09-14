import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Destination.DestinationFinale;
import Emetteur.EmetteurAnalogique;
import GenerateurBruit.BruitGaussien;
import GenerateurSignal.BadEntrySignal;
import GenerateurSignal.DestinationGenerateurInterface;
import GenerateurSignal.SignalNRZ;
import GenerateurSignal.SignalNRZT;
import GenerateurSignal.SignalRZ;
import GenerateurSignal.SourceGenerateurInterface;
import Information.InformationNonConforme;
import OutilsTest.calculTEB;
import Recepteurs.RecepteurAnalogique;
import Sonde.SondeAnalogique;
import Sonde.SondeLogique;
import Source.Source;
import Source.SourceFixe;
import Transmetteur.TransmetteurAnalogiqueBruite;


/**
 *
 */

/**
 * Merveilleusement realise par Julien Gambier-Morel
 * Le 16 oct. 2013
 * "Quand ce sera termine, on fera une petite pause"
 */
public class ApplicationTransmissionAnalogiqueBruitee {
    
    
    public static final int SEQ_DEFAULT_LENGTH = 100;
    
    private static float min;
    private static float max;
    private static int nbEch;
    private static Source srcFixe;
    private static boolean sonde = false;
    private static SourceGenerateurInterface srcGen;
    private static DestinationGenerateurInterface dstGen;
    private static float snr;
    
    public static EmetteurAnalogique emetteurAnalogique;
    public static TransmetteurAnalogiqueBruite transmetteurBruite;
    public static RecepteurAnalogique recepteurAnalogique;
    public static DestinationFinale destinationFinale;
    public static SondeLogique sondeLogiqueEntree;
    public static SondeLogique sondeLogiqueSortie;
    public static SondeAnalogique sondeAnalogiqueEntree;
    public static SondeAnalogique sondeAnalogiqueSortie;
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        parseArgs(args);
        
        try {
            emetteurAnalogique = new EmetteurAnalogique(srcGen, nbEch);
            //TODO Réparer le transmetteur bruité
            BruitGaussien bruit = new BruitGaussien();
            bruit.setSNR(snr);
            transmetteurBruite
                    = new TransmetteurAnalogiqueBruite(bruit);
            recepteurAnalogique = new RecepteurAnalogique(dstGen, nbEch);
            destinationFinale = new DestinationFinale();
            
            srcFixe.connecter(emetteurAnalogique);
            emetteurAnalogique.connecter(transmetteurBruite);
            
            transmetteurBruite.connecter(recepteurAnalogique);
            recepteurAnalogique.connecter(destinationFinale);
            
            if (sonde)
            {
                sondeLogiqueEntree = new SondeLogique("Sonde Logique Entree");
                sondeLogiqueSortie = new SondeLogique("Sonde logique du recepteur");
                
                sondeAnalogiqueEntree = new SondeAnalogique("Sonde Analogique de l'emetteur");
                sondeAnalogiqueSortie = new SondeAnalogique("Sonde Analogique du Transmetteur");
                
                srcFixe.connecter(sondeLogiqueEntree);
                recepteurAnalogique.connecter(sondeLogiqueSortie);
                emetteurAnalogique.connecter(sondeAnalogiqueEntree);
                transmetteurBruite.connecter(sondeAnalogiqueSortie);
            }
            
            try {
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
    
    /**
     * Methode de detection et de gestion des parametres du main
     */
    
    private static void parseArgs(String[] args) {
        String param = "";
        for (int j = 0; j < args.length; j++) {
            param = param + " " + args[j];
        }
        
        Pattern pSequence = Pattern.compile("-[Mm]ess ([0-9]+)");
        Matcher mSequence = pSequence.matcher(param);
        if (mSequence.find()) {
            String sequence = mSequence.group(1);
            
            if (sequence.matches("[01]{7,}"))
                srcFixe = new SourceFixe(sequence);
            else if (sequence.matches("\\d+"))
                srcFixe = new SourceFixe(Integer.parseInt(sequence));
            else
                printConsigne();
        }
        else
            srcFixe = new SourceFixe(SEQ_DEFAULT_LENGTH);
        
        Pattern pSampling = Pattern.compile("-[Nn]bEch ([0-9]+)");
        Matcher mSampling = pSampling.matcher(param);
        if (mSampling.find())
            nbEch = new Integer(mSampling.group(1));
        else
            nbEch=10;
        
        Pattern pAmpl = Pattern
                .compile("-[Aa]mpl ([-]{0,1}[0-9]+) ([-]{0,1}[0-9]+)");
        Matcher mAmpl = pAmpl.matcher(param);
        if (mAmpl.find()) {
            min = new Float(mAmpl.group(1));
            max = new Float(mAmpl.group(2));
        } else
        {
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
            
            
        }
        else
        {
            SignalRZ rz = new SignalRZ(nbEch, min, max);
            srcGen = rz;
            dstGen = rz;
        }
        
        Pattern pSnr = Pattern.compile("-[Ss]nr ([-]{0,1}\\w+)");
        Matcher mSnr = pSnr.matcher(param);
        if(mSnr.find()){
            
            snr=Float.parseFloat(mSnr.group(1));
        }else{
            snr=0.7f;
        }
        
        Pattern sCoding = Pattern.compile("-[Ss][ ]|-[Ss]$");
        Matcher msCoding = sCoding.matcher(param);
        if (msCoding.find()){
            sonde = true;
        }
        else{
            sonde=false;
        }
    }
    
    
    /**
     * Methode d'affichage des consignes de l'etape
     */
    public static void printConsigne() {
        System.out.println("Transmetteur analogique bruité : \n");
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
        System.out.println("\"-snr s\" sprécise la valeur du rapport signal sur bruit (SNR)");
        System.out.print(". Un flottant ");
        System.exit(1);
    }
    
}