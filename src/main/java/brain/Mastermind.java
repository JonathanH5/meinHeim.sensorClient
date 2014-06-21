package brain;

import web.WebService;

public class Mastermind {
	
	private int oldestLineToSend;
	private int newestLineToSend;
	
	private WebService ws;
	
	private Data d;
	
	public Mastermind() {
		oldestLineToSend = 0;
		newestLineToSend = 0;
		ws = new WebService();
	}
	
	private void initialize() {
		d = new Data();
		
		if (d.startUp()) {
			System.err.println("Successfully started the Data class");
		} else {
			System.err.println("Error while starting Data class");
		}
			
		newestLineToSend = d.getInformationLineCount() - 1;	
		ws.sendJsonLines(d.getInformationLines(oldestLineToSend, newestLineToSend));
		oldestLineToSend = d.getInformationLineCount();
		
		sendLines();
	}
	
	private void sendLines() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (d.readNewLines()[0] > 0) {
			newestLineToSend = d.getInformationLineCount() - 1;	
			ws.sendJsonLines(d.getInformationLines(oldestLineToSend, newestLineToSend));
			oldestLineToSend = d.getInformationLineCount();
		}
		sendLines();
	}
	
	public static void main(String[] args) {
		Mastermind m = new Mastermind();
		m.initialize();
	}


}
