
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
/**
 * Merveilleusement realise par Julien Gambier-Morel Le 16 oct. 2013 "Quand ce
 * sera termine, on fera une petite pause"
 */
public class Simulateur {

    /**
     * Classe centrale du programme, agrege les differentes etapes
     * @param args
     */
    public static int etape;

    // TODO Remove this fucking throw BadEntry
    public static void main(String[] args) {
        ParseEtape(args);
        switch (etape) {

            case 1:
                ApplicationTransmissionLogiqueParfaite.main(args);
                break;
            case 2:
                ApplicationTransmissionAnalogiqueParfaite.main(args);
                break;
            case 3:
                ApplicationTransmissionAnalogiqueBruitee.main(args);
                break;
            case 4:
                ApplicationTransmissionAnalogiqueBruiteeTrajetsMultiples.main(args);
                break;
			// case 4: Etape4.main(args);
            // case 5: Etape5.main(args);
            // case 6: Etape6.main(args);
            // case 7: Etape7.main(args);

            default:
                ApplicationTransmissionLogiqueParfaite.main(args);
                break;
        }

    }

    private static void ParseEtape(String[] args) {

        String parametres = "";
        for (int j = 0; j < args.length; j++) {
            parametres = parametres + " " + args[j];
        }

        Pattern patternEtape = Pattern.compile("-[Ee]tape ([1-7])");
        Matcher matcherEtape = patternEtape.matcher(parametres);
        if (matcherEtape.find()) {
            etape = new Integer(matcherEtape.group(1));
        } else {

            PrintMan();

        }
    }

    
    /**
     * Detecte une demande d'aide de l'utilisateur
     * @param args la chaine a tester
     */
    private static void DetectHelp(String args) {

        Pattern patternHelp = Pattern.compile("--[hH]elp||-[Hh]");
        Matcher matcherHelp = patternHelp.matcher(args);
        if (matcherHelp.find()) {
            PrintMan();

        }
    }

    /**
     * Methode d'affichage de l'aide
     */
    private static void PrintMan() {

        System.out.println("Aide du programme de transmission \n" + 
                "Lancez ce programme en précisant l'étape voulue \n" + 
                "L'etape affichera les paramètres à préçiser pour l'appel\n\n" + 
                "Exemple : ./transmission.sh -etape 2\n\n");
    }

}
