/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OutilsTest;

import GenerateurSignal.BadEntrySignal;
import GenerateurSignal.DestinationGenerateurInterface;
import GenerateurSignal.SourceGenerateurInterface;
import Information.Information;
import Sonde.SondeAnalogique;
import java.util.ArrayList;

/**
 *
 * @author hach
 */
public class TestGenerateurDecodeurFloat {
    public static boolean testSourceFloat(boolean entree, boolean entree1, boolean entree2, float[] resultatAttendu, SourceGenerateurInterface s, boolean sonde) throws BadEntrySignal
    {
        float[] signal = s.generer(entree, entree1, entree2);
        
        int erreurs = 0;
        ArrayList<Integer> pos_not_ok = new ArrayList<Integer>();
        
        for(int i=0 ; i < resultatAttendu.length ; i++)
        {
            if(resultatAttendu[i] != signal[i])
            {
                erreurs++;
                pos_not_ok.add(i);
            }
        }
        
        if(erreurs>0)
            System.out.println("Erreur avec le générateur " + s);
        
        for(Integer pos : pos_not_ok)
        {
            System.out.println("Erreur à la position " + pos);
        }
        
        if(sonde)
        {
            Information<Float> resAttenduInformation = new Information<Float>();
            Information<Float> resObtenuInformation = new Information<Float>();
            
            for(int i=0 ; i < resultatAttendu.length ; i++)
            {
                resAttenduInformation.add(new Float(resultatAttendu[i]));
                resObtenuInformation.add(new Float(signal[i]));
                
                SondeAnalogique s1 = new SondeAnalogique("Résultat attendu");
                SondeAnalogique s2 = new SondeAnalogique("Résultat obtenu");
                s1.recevoir(resAttenduInformation);
                s2.recevoir(resObtenuInformation);
            }
        }
        
        return erreurs==0;
    }
    
    
    public static boolean testDestinationFloat(float[] entree, boolean bitAttendu, DestinationGenerateurInterface d) throws BadEntrySignal
    {
        boolean bitObtenu = d.decoder(entree);
        
        if(bitObtenu != bitAttendu)
        {
            System.out.println("Erreur d'interprétation avec le decodeur " + d);
            return false;
        }
        return true;        
    }
}
