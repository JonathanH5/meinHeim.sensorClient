package web;

import java.util.List;

import brain.InformationLine;

public class WebService {

	public boolean sendJsonLines(List<InformationLine> lines) {
		System.out.println(lines);
		//TODO send infos to server
		return true;
	}
	
}
