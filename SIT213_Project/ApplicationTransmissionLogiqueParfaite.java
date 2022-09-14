
import Destination.DestinationFinale;
import Information.InformationNonConforme;
import OutilsTest.calculTEB;
import Sonde.SondeLogique;
import Source.SourceFixe;
import Transmetteur.TransmetteurParfait;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author chauquiqui
 */
public class ApplicationTransmissionLogiqueParfaite {
    
    public static void main (String [] args)
    {
        if(args.length < 2)
        {
            System.out.println("Transmetteur logique parfait : \n");
            System.out.println("Usage : ./transmission.sh [sequence de bits ou nb de bits a transmettre] [boolean de sonde]");
            System.out.println("Exemple : ./transmission \"00011110011000111\" 1");
            System.out.println("Exemple : ./transmission.sh 50 1 \"random\"");
            System.exit(1);
        }
        else
        {
            SourceFixe source;
            if(args.length == 3 && args[2].equals("random"))
                source = new SourceFixe( (int)(Integer.parseInt(args[0])) );
            else
                source = new SourceFixe(args[0]);

            TransmetteurParfait transmetteur = new TransmetteurParfait();

            DestinationFinale destination = new DestinationFinale();

            source.connecter(transmetteur);
            transmetteur.connecter(destination);

            if(args[1].equals("1"))
            {
                SondeLogique sondeSource = new SondeLogique("source");
                SondeLogique sondeDestination = new SondeLogique("destination");


                source.connecter(sondeSource);
                transmetteur.connecter(sondeDestination);
            }


            try {
                source.emettre();
                System.out.println("TEB : " + calculTEB.calculTauxErreurBinaire(source, destination));
            } catch (InformationNonConforme ex) {
                
            }
        }
    }    
}
