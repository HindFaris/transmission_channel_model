package tests;


/**
 * La classe definissant les tests, et execute tous les tests de classes individuelles d'affilees.
 * @author Cudennec Gael
 *
 */
public class Tests {
	
	private int nbTests;  // Nombre de tests effectues
	private int nbErrors; // Nombre total d'erreurs liees aux tests
	
	/**
	 * 
	 * @param nbTests Nombre de tests effectues
	 * @param nbErrors Nombre total d'erreurs liees aux tests
	 */
	public Tests (int nbTests, int nbErrors) {
		this.nbTests = nbTests;
		this.nbErrors = nbErrors;
	}
	
	/**
	 * Retourne une chaine de caracteres decrivant les tests
	 */
	public String toString (){
		String s = "[ Nb of performed tests : " + nbTests + " / nb of detected error(s) : "+ nbErrors + " ]";
		return s;
	}
	
	/**
	 * Ajoute au test courant les valeurs du test en parametre
	 * @param other un autre test
	 */
	public void add (Tests other){
		this.nbTests += other.nbTests;
		this.nbErrors += other.nbErrors;
	}
	
	/**
	 * Deux tests sont egaux si leurs valeurs interne sont egales
	 */
	public boolean equals (Object o){
		if (o instanceof Tests){
			Tests tr = (Tests) o;
			return ((this.nbTests == tr.nbTests) && (this.nbErrors == tr.nbErrors));
		}
		return false;
	}

	

	public static Tests TestReport(){
		Tests bigTestReport = new Tests(0, 0);
		Tests tr = new Tests(0, 0);
		//On peut faire un truc qui compte les erreurs aussi
		
		System.out.println("Beginning of a new Test Report...  ");
		try {
			
			System.out.println("Testing each class individually before testing the whole process : \n");
			System.out.println("\n\n***************************************************************************************************\n");
			
			
			System.out.println("Testing SourceFixe \n");
			tr = SourceFixeTest.testReport();
			bigTestReport.add(tr);
			System.out.println("\n\n***************************************************************************************************\n");
			
			
			System.out.println("Testing SourceAleatoire \n");
			tr = SourceAleatoireTest.testReport();
			bigTestReport.add(tr);
			System.out.println("\n\n***************************************************************************************************\n");
			
			
			System.out.println("Testing Emetteur \n");
			System.out.println("Testing with a NRZ Signal \n");
			tr = EmetteurTest.testReport("NRZ");
			bigTestReport.add(tr);
			
			System.out.println("\nTesting with a NRZT Signal \n");
			tr = EmetteurTest.testReport("NRZT");
			bigTestReport.add(tr);
			
			System.out.println("\nTesting with a RZ Signal \n");
			tr = EmetteurTest.testReport("RZ");
			bigTestReport.add(tr);
			System.out.println("\n\n***************************************************************************************************\n");
			

			System.out.println("Testing Signal \n");
			tr = SignalTest.testReport();
			bigTestReport.add(tr);
			System.out.println("\n\n***************************************************************************************************\n");
			
			System.out.println("Testing Bruit \n");
			tr = BruitTest.testReport();
			bigTestReport.add(tr);
			System.out.println("\n\n***************************************************************************************************\n");
			
			System.out.println("Testing Recepteur \n");
			tr = RecepteurTest.testReport();
			bigTestReport.add(tr);
			System.out.println("\n\n***************************************************************************************************\n");
			
			
			System.out.println("Testing DestinationFinale \n");
			tr = DestinationFinaleTest.testReport();
			bigTestReport.add(tr);
			System.out.println("\n\n***************************************************************************************************\n");
			
		}catch (Exception e) {
			System.out.println("Err : Unexpected exception : " + e);
			e.printStackTrace();
			System.exit(1);
		}
		
		System.out.println("Hereby is the big test report : \n" + bigTestReport);
		return bigTestReport;
	}
	
	/**
	 * Lance test()
	 * @param args
	 */
	public static void main(String[] args) {
		TestReport();
	}

}

