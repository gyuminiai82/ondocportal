package kr.ondoc.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.Timestamp;

import org.ini4j.Ini;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmrSheet {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public String sheetRead(String ptno, String year, String fileName) throws IOException {
		Ini dasom2006ini = Dasom2006INI.getDasom2006Ini();

		String sEMRDrv = dasom2006ini.get("EMRChart", "Drive").replaceAll("=/=", "\\\\");
		String sEMRPath = dasom2006ini.get("EMRChart", "ChartPath").replaceAll("=/=", "\\\\");

		String path = sEMRDrv + sEMRPath + ptno + '\\' + year + '\\' + fileName;

		Runtime rt = Runtime.getRuntime();

		String exec = "C:\\StNeo\\Bin\\ChtDecrypt.exe D " + ptno + " " + year + " \"" + fileName + "\"";

		Process pro;

		try {
			pro = rt.exec(exec);
			pro.waitFor();
		} catch (Exception e) {
			// TODO: handle exception
			logger.warn(e.getMessage());
		}

		StringBuilder stringBuilder = new StringBuilder();

		try {
			FileInputStream input = new FileInputStream(path);
			InputStreamReader reader = new InputStreamReader(input, "EUC-KR");
			BufferedReader in = new BufferedReader(reader);

			int ch;
			while ((ch = reader.read()) != -1) {
				stringBuilder.append((char) ch);
			}

			input.close();

			File emrFile = new File(path);

			if (emrFile.exists()) {
				if (emrFile.delete()) {
					// System.out.println("delete file success");
				} else {
					// System.out.println("delete file failure");
				}
			} else {
				// System.out.println("not exist file");
			}
		} catch (Exception e) {
			// TODO: handle exception
			stringBuilder.append("FILE_NOT_FOUND");
		}
		// return stringBuilder.toString().replaceAll("\"",
		// "doublequotationmarks").replaceAll("\'", "smallquotation");
		return stringBuilder.toString();
	}

	public String sheetReadNotDelete(String ptno, String year, String fileName) throws IOException {
		Ini dasom2006ini = Dasom2006INI.getDasom2006Ini();

		String sEMRDrv = dasom2006ini.get("EMRChart", "Drive").replaceAll("=/=", "\\\\");
		String sEMRPath = dasom2006ini.get("EMRChart", "ChartPath").replaceAll("=/=", "\\\\");

		String path = sEMRDrv + sEMRPath + ptno + '\\' + year + '\\' + fileName;

		Runtime rt = Runtime.getRuntime();

		String exec = "C:\\StNeo\\Bin\\ChtDecrypt.exe D " + ptno + " " + year + " \"" + fileName + "\"";

		Process pro;

		try {
			pro = rt.exec(exec);
			pro.waitFor();
		} catch (Exception e) {
			// TODO: handle exception
			logger.warn(e.getMessage());
		}

		StringBuilder stringBuilder = new StringBuilder();

		try {
			FileInputStream input = new FileInputStream(path);
			InputStreamReader reader = new InputStreamReader(input, "EUC-KR");
			BufferedReader in = new BufferedReader(reader);

			int ch;
			while ((ch = reader.read()) != -1) {
				stringBuilder.append((char) ch);
			}

			input.close();
		} catch (Exception e) {
			// TODO: handle exception
			stringBuilder.append("FILE_NOT_FOUND");
		}
		// return stringBuilder.toString().replaceAll("\"",
		// "doublequotationmarks").replaceAll("\'", "smallquotation");
		return stringBuilder.toString();
	}

	public String sheetWrite(String ptno, String year, String key, String title, String sheet) throws IOException {
		Ini dasom2006ini = Dasom2006INI.getDasom2006Ini();

		String sEMRDrv = dasom2006ini.get("EMRChart", "Drive").replaceAll("=/=", "\\\\");
		String sEMRPath = dasom2006ini.get("EMRChart", "ChartPath").replaceAll("=/=", "\\\\");

		String fileName = ptno + '_' + key + '_' + title + ".emr";

		String path = sEMRDrv + sEMRPath + fileName;

		// 1. 파일 객체 생성
		File file = new File(path);

		// 2. 파일 존재여부 체크 및 생성
		if (!file.exists()) {

			file.createNewFile();
		}

		FileOutputStream output = new FileOutputStream(path);
		OutputStreamWriter OutputStreamWriter = new OutputStreamWriter(output, "EUC-KR");
		BufferedWriter writer = new BufferedWriter(OutputStreamWriter);

		// 4. 파일에 쓰기
		writer.write("");

		// 5. BufferedWriter close
		writer.close();

		Runtime rt = Runtime.getRuntime();

		String exec = "C:\\StNeo\\Bin\\ChtDecrypt.exe E " + ptno + " " + year + " \"" + path + "\"";

		Process pro;

		try {
			pro = rt.exec(exec);
			pro.waitFor();
		} catch (Exception e) {
			// TODO: handle exception
			logger.warn(e.getMessage());
		}

		return "";
	}
}
