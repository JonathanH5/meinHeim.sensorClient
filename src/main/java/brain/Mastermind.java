/***********************************************************************************************************************
 * Copyright (C) 2014 Jonathan Hasenburg
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 **********************************************************************************************************************/

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
