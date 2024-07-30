package org.doubleoops.mosh;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

/**
 * Main class for the mosh project.
 */
public class Main {

	/**
	 * Entry point for the program.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			(new MoshEngine()).run();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
