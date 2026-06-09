package kr.ondoc.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

import org.ini4j.Ini;

public class StNeoSysINI {
	public static Ini getStNeoSysIni() throws IOException {
		BufferedReader in = null;

		File fileToParse = new File("C:\\Windows\\StNeoSys.ini");
		in = new BufferedReader(new InputStreamReader(new FileInputStream(fileToParse),"euc-kr"));
		StringBuffer fileIni = new StringBuffer();
		String fileString = new String();
		
		int data = 0;
		
		while((data = in.read()) != -1) {		
			fileIni.append((char)data);
		}
		fileString = fileIni.toString().replaceAll("/\\*([^*]|[\r\n]|(\\*+([^*/]|[\r\n])))*\\*+/", "");
		
		in.close();
		
		Reader reader = new StringReader(fileString);
		
		Ini ini = new Ini(reader);
		
		return ini;
	}
}
