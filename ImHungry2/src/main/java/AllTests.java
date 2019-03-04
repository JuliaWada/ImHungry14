import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	// https://www.vogella.com/tutorials/JUnit/article.html
	// test against design doc: https://docs.google.com/document/d/1DvwTjMrdSm9AbjWrdTcSk0mFvmZ8EWanD479vqJhduU/edit#
		// SRS should be fully covered by Cucumber
	// Goal: demonstrate that the implementation satisfies the design
	// Do: Map design elements and implementation structures
	// Grading: Complete with respect to design diagram and elements
			//  Clear explanation of mapping
	// should some tests fail? ex. test elements of classes based on class diagram,
			// but our designs changed in implementation process
			// https://piazza.com/class/jqh6yg2ejhc2aj?cid=128
			// "Points will not be deducted if your implementation deviates from the design,
			// as long as you explain and justify this in your validation against design section." - Halfond
	// Design Diagrams: some of these can probably be technically satisfied by the same test - ask in OH
		// Software Architecture- do we need to test this? - ask in OH
		// Communication
		// Class Overall Diagram
		// Sequence Diagrams
		// State Machine
		// User Interfaces
			// already tested with Cucumber? - ask in OH
	
	
	public static Test suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		//$JUnit-BEGIN$

		//$JUnit-END$
		return suite;
	}

}