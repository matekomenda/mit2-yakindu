package hu.bme.mit.yakindu.analysis.workhere;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import hu.bme.mit.yakindu.analysis.RuntimeService;
import hu.bme.mit.yakindu.analysis.TimerService;
import hu.bme.mit.yakindu.analysis.example.ExampleStatemachine;
import hu.bme.mit.yakindu.analysis.example.IExampleStatemachine;

public class RunStatechart {
	
	public static void main(String[] args) throws IOException {
		ExampleStatemachine s = new ExampleStatemachine();
		s.setTimer(new TimerService());
		RuntimeService.getInstance().registerStatemachine(s, 200);
		s.init();		
		s.enter();
		s.runCycle();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		while(true) {
			String line = reader.readLine();
			line = line.toLowerCase();
			if(line.equals("exit")) {
				System.out.println("Az alkalmazás leáll");
				System.exit(0);
			} else if(line.equals("start")) {
				s.raiseStart();
				s.runCycle();
			} else if(line.equals("white")) {
				s.raiseWhite();
				s.runCycle();
			}else if(line.equals("black")) {
				s.raiseBlack();
				s.runCycle();
			} else {
				System.out.println("Nem ismert szöveg a konzolon");
				break;
			}
			
			print(s);
		}
		
		reader.close();
		System.exit(0);
	}

	public static void print(IExampleStatemachine s) {
		System.out.println("W = " + s.getSCInterface().getWhiteTime());
		System.out.println("B = " + s.getSCInterface().getBlackTime());
	}
}
