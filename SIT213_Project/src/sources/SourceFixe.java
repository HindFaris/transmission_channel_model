package sources;

import information.Information;
/**
 * La source generant la trame de bits demandee
 * @author jerom
 *
 */
public class SourceFixe extends Source<Boolean>{
    /**
     * Une source qui envoie toujours le meme message
     * @param binaryWord
     * 			la chaine de caractere du mot en binaire a envoyer
     */
    public SourceFixe (String binaryWord) {
        informationGeneree = new Information<Boolean>();
        for (int index = 0; index < binaryWord.length(); index++) {
        	if (binaryWord.charAt(index) == '1') {
        		informationGeneree.add(true);
        	}
        	else {
        		informationGeneree.add(false);
        	}
        }
        
    }
}