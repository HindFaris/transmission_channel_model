/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OutilsTest;

import Destination.Destination;
import Source.Source;

/**
 *
 * @author hach
 */
public class calculTEB {
    public static float calculTauxErreurBinaire(Source <Boolean> src, Destination <Boolean> dst)
    {
        int nb_faux = 0;
        int nb= src.getInformationEmise().nbElements();
        
        for(int i = 0 ; i < nb ; i++)
        {
            if(src.getInformationEmise().iemeElement(i) != dst.getInformationRecue().iemeElement(i))
                nb_faux++;
        }
        //nb_faux += dst.getInformationRecue().nbElements() - src.getInformationEmise().nbElements();
        
        return ((float)(nb_faux)/(float)(nb));
    }
}
