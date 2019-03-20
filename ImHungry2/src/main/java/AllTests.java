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
	// should some tests fail? NO ex. test elements of classes based on class diagram,
			// but our designs changed in implementation process
			// all tests should pass; write tests for the implementation
			// https://piazza.com/class/jqh6yg2ejhc2aj?cid=128
			// "Points will not be deducted if your implementation deviates from the design,
			// as long as you explain and justify this in your validation against design section." - Halfond
	// Design Diagrams:
		// make sure every class in the diagram has complete coverage, or why it doesn't have coverage
			// ex. "this class doesn't have this element, and this is why"
		// Communication
		// Class Overall Diagram
		// Sequence Diagrams
		// State Machine
	
	
	public static Test suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		//$JUnit-BEGIN$

		//$JUnit-END$
		return suite;
	}

}