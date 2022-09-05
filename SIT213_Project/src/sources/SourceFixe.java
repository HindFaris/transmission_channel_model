package sources;

import information.Information;

public class SourceFixe extends Source<Boolean>{
    
    
    /**
     * Une source qui envoie toujours le m�me message
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