package hu.bme.mit.yakindu.analysis.workhere;

import java.util.Random;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.junit.Test;
import org.yakindu.sct.model.sgraph.State;
import org.yakindu.sct.model.sgraph.Statechart;

import hu.bme.mit.model2gml.Model2GML;
import hu.bme.mit.yakindu.analysis.modelmanager.ModelManager;

public class Main {
	@Test
	public void test() {
		main(new String[0]);
	}
	
	public static void main(String[] args) {
		ModelManager manager = new ModelManager();
		Model2GML model2gml = new Model2GML();
		
		// Loading model
		EObject root = manager.loadModel("model_input/example.sct");
		
		// Reading model
		Statechart s = (Statechart) root;
		TreeIterator<EObject> iterator = s.eAllContents();
		//2.3as
		State prevstate = null;
		//2.4es
		int zero = 0;
		while (iterator.hasNext()) {
			EObject content = iterator.next();
		
			if(content instanceof State) {
				State state = (State) content;
				//2.3as feladat
				if(prevstate != null) {
					System.out.println(prevstate.getName() + " -> " + state.getName());
				} 
				prevstate = state;
				
				//2.4es feladat
				if(state.getOutgoingTransitions().size() == zero) {
					System.out.println("Trap state: " + state.getName());
				}
				
				//2.5os feladat
				if(state.getName().equals("empty")) {
					Random rand = new Random();
					int myrand = rand.nextInt(100);
					state.setName("MyState" + myrand);
					System.out.println(state.getName());
				}
			}		
		}
		
		// Transforming the model into a graph representation
		String content = model2gml.transform(root);
		// and saving it
		manager.saveFile("model_output/graph.gml", content);
	}
}
