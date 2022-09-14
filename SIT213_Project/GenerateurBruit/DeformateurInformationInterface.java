/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GenerateurBruit;

import GenerateurSignal.BadEntrySignal;
import Information.Information;

/**
 *
 * @author manfred
 */
public interface DeformateurInformationInterface <T> {
    /**
     * Genere un bruit, different pour chaque classe de bruit
     * 
     * @return Le signal bruite sous forme d'un tableau de float
     */
    public Information<T> deformerInformation(Information<T> signal) throws BadEntrySignal;
}
