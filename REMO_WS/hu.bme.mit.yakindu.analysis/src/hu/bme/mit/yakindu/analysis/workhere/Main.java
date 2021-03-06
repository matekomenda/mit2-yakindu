package hu.bme.mit.yakindu.analysis.workhere;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.junit.Test;
import org.yakindu.sct.model.sgraph.State;
import org.yakindu.sct.model.sgraph.Statechart;
import org.yakindu.sct.model.stext.stext.EventDefinition;
import org.yakindu.sct.model.stext.stext.VariableDefinition;

import hu.bme.mit.model2gml.Model2GML;
import hu.bme.mit.yakindu.analysis.RuntimeService;
import hu.bme.mit.yakindu.analysis.TimerService;
import hu.bme.mit.yakindu.analysis.example.ExampleStatemachine;
import hu.bme.mit.yakindu.analysis.example.IExampleStatemachine;
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
		
		// 2es feladat
		// 2.3
		State prevstate = null;
		// 2.4
		int zero = 0;
		// 4.4
		List <String> myVariables = new LinkedList<>();
		List <String> myEvents = new LinkedList<>();
		while (iterator.hasNext()) {
			EObject content = iterator.next();
			
			// 4.3
			if(content instanceof VariableDefinition) {
				VariableDefinition vd = (VariableDefinition) content;
				//System.out.println(vd.getName());
				myVariables.add(vd.getName());
			}
			// 4.3
			if(content instanceof EventDefinition) {
				EventDefinition ed = (EventDefinition) content;
				//System.out.println(ed.getName());
				myEvents.add(ed.getName());
			}
			
		
			if(content instanceof State) {
				State state = (State) content;
				// 2.3
				if(prevstate != null) {
					//System.out.println(prevstate.getName() + " -> " + state.getName());
				} 
				prevstate = state;
				
				// 2.4
				if(state.getOutgoingTransitions().size() == zero) {
					//System.out.println("Trap state: " + state.getName());
				}
				
				// 2.5
				if(state.getName().equals("empty")) {
					Random rand = new Random();
					int myrand = rand.nextInt(100);
					state.setName("MyState" + myrand);
					//System.out.println(state.getName());
				}
			}		
		}
		System.out.println("public static void main(String[] args) throws IOException {\r\n" + 
				"		ExampleStatemachine s = new ExampleStatemachine();\r\n" + 
				"		s.setTimer(new TimerService());\r\n" + 
				"		RuntimeService.getInstance().registerStatemachine(s, 200);\r\n" + 
				"		s.init();		\r\n" + 
				"		s.enter();\r\n" + 
				"		s.runCycle();\r\n" + 
				"		\r\n" + 
				"		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));\r\n" + 
				"		while(true) {\r\n" + 
				"			String line = reader.readLine();\r\n" + 
				"			line = line.toLowerCase();\r\n" + 
				"			if(line.equals(\"exit\")) {\r\n" + 
				"				System.out.println(\"Az alkalmaz??s le??ll\");\r\n" + 
				"				System.exit(0);\r\n");
		for(int i = 0; i < myEvents.size(); i++) {
			String tmp = myEvents.get(i);
			String big = tmp.substring(0,1).toUpperCase() + tmp.substring(1);
			System.out.println("\t\t\t} else if(line.equals(\""+tmp+ "\")) {\r\n" + 
					"				s.raise"+big+"();\r\n" + 
					"				s.runCycle();");
		}
		System.out.println("	\t\t} else {\r\n" + 
				"				System.out.println(\"Nem ismert sz??veg a konzolon\");\r\n" + 
				"				break;\r\n" + 
				"			}\r\n" + 
				"			\r\n" + 
				"			print(s);\r\n" + 
				"		}\r\n" + 
				"		\r\n" + 
				"		reader.close();\r\n" + 
				"		System.exit(0);\r\n" + 
				"	}"
		);
		
		// 4.4
		//System.out.println("public static void print(IExampleStatemachine s) {\r\n");
		for(int i = 0; i < myEvents.size(); i++) {
			String tmp = myEvents.get(i);
			String big = tmp.substring(0,1).toUpperCase() + tmp.substring(1);
			char firstchar = big.charAt(0);
			//System.out.println("		System.out.println(\"" + firstchar + " = \" + s.getSCInterface().get" + big +"());");
		}
		//4.4
		for(int i = 0; i < myVariables.size(); i++) {
			String tmp = myVariables.get(i);
			String big= tmp.substring(0,1).toUpperCase() + tmp.substring(1);
			char firstchar = big.charAt(0);
			//System.out.println("		System.out.println(\"" + firstchar + "= \" + s.getSCInterface().get"+ big +"());\r" );
		}
		//System.out.println("\t}");
		
		
		
		
		// Transforming the model into a graph representation
		String content = model2gml.transform(root);
		// and saving it
		manager.saveFile("model_output/graph.gml", content);
	}
	
	public static void negyedikfeladat(Statechart s) {
		//
	}
	
}
