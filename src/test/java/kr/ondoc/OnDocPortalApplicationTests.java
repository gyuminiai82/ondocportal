package kr.ondoc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.ini4j.Ini;
import org.ini4j.InvalidFileFormatException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.ondoc.domain.sybase.crm.CrmRevptNaverVO;
import kr.ondoc.mapper.sybase.crm.CrmRevptNaverMapper;
import kr.ondoc.scheduler.OnlineScheduleRestClient;
import kr.ondoc.util.AES256;
import kr.ondoc.util.Dasom2006INI;
import kr.ondoc.util.EmrSheet;
import kr.ondoc.util.StNeoSysINI;

@SpringBootTest
class OnDocPortalApplicationTests {
	@Autowired
	private CrmRevptNaverMapper revptNaverMapper;
	
	/*
	<!-- JNA -->
	<dependency>
		<groupId>net.java.dev.jna</groupId>
		<artifactId>jna-platform</artifactId>
		<version>5.13.0</version>
	</dependency>
	*/
	//델파이 dll 호출시 64bit 필요 파라미터와 리턴 값은 PAnsiChart 여야 Java에서 String으로 받아짐
	
	@Test
	void iniRead() throws InvalidFileFormatException, IOException {
		/*
		BufferedReader in = null;

		File fileToParse = new File("C:\\Windows\\Dasom2006.ini");
		in = new BufferedReader(new InputStreamReader(new FileInputStream(fileToParse),"euc-kr"));
		StringBuffer fileIni = new StringBuffer();
		String fileString = new String();
		
		int data = 0;
		
		while((data = in.read()) != -1) {		
			fileIni.append((char)data);
		}
		*/
		
		//fileString = fileIni.toString().replaceAll("/\\*([^*]|[]|(\\*+([^*/]|[])))*\\*+/", "").replaceAll("\\\\", "=/=");
		
		/*
		in.close();
		
		Reader reader = new StringReader(fileString);
		
		Ini ini = new Ini(reader);
		
		String sEMRDrv = ini.get("EMRChart", "Drive").replaceAll("=/=", "\\\\");
		String sEMRPath = ini.get("EMRChart", "ChartPath").replaceAll("=/=", "\\\\");
		*/
		
	}
	@Test
	void dllLoad() {
		/*
		try {
			library = Native.load("D:\\100_individual\\XE10\\xeDllLoad\\Win64\\Debug\\Project1.dll", ResnoSecurityASE.class);
			
			System.out.println(library.hello2("으하하", "넘어간다", "ㅋㅋㅋㅋ"));
			
		} catch (Exception e) {
			// TODO: handle exception
			
			System.out.println(e.getMessage());
		}
		*/
	}
	
	@Test
	void exeSheetRead() throws IOException {
		//EmrSheet emrSheet = new EmrSheet();
		
		//String ptno, String year, String fileName
		//System.out.println(emrSheet.sheetRead("119005", "2022", "119005_17_처방전 대리수령신청서.emr"));
		
	}
	
	@Test
	void exeSheetWrite() throws IOException {
		/*
		EmrSheet emrSheet = new EmrSheet();
		
		//String ptno, String year, String key, String title, String sheet
		String date = "2022-01-25";
		String sheet = "테스트|test";
		System.out.println(emrSheet.sheetWrite("119005", date.substring(0, 4), "17", "처방전 대리수령신청서", sheet));
		*/
	}
	
	@Test
	void dbPass() throws IOException {
		/*
		Ini stNeoSysIni = (new StNeoSysINI()).getStNeoSysIni();
		
		String dbpass = stNeoSysIni.get("NeoSysID", "SysXE");
        
		try {
			//System.out.println((new AES256()).decrypt(dbpass));
		} catch (Exception e) {
			// TODO: handle exception
		}
		*/
	}
	
	@Test
	void restApi() throws Exception {
//		CrmRevptNaverVO vo = new CrmRevptNaverVO();
//		vo.setRev_reservationkey("crm1");
//		vo.setRev_bookingkey("DBK-5kqrF3he-fEV9-4Xih-al2f-6an42yCOkrX9");
//		vo.setRev_datetime("2025-06-30 18:00:00");
//		vo.setRev_name("테스트");
//		vo.setRev_phone("01031577158");
//		vo.setRev_state("BS_WAITING");
//		vo.setRev_naveruserid("8462068");
//		vo.setRev_bookingtype("NORMAL");
//		vo.setRev_platformtype("NAVER");
//		vo.setRev_cancelleddesc("네이버에서 예약 변경");
//		vo.setRev_email("test@test.com");
//		vo.setRev_userrequest("아프다");
//		vo.setRev_prebookingkey("");
//		vo.setRev_visitorname("방문자");
//		vo.setRev_visitorphone("01000001111");
//		vo.setRev_customformdata(""); //예약커스텀 정보
//		vo.setRev_itemoptions("");	//시술정보
//		vo.setRev_date("20250627");
//		vo.setRev_time("2130");
//		vo.setRev_confirm("N");
//		vo.setRev_ptno("");
//		vo.setRev_reservationname("내과진료");
//		vo.setRev_period("30");
		
		//revptNaverMapper.insertRevptNaver(vo);
	}
	
	@Test
	void restApiUpdate() throws Exception {
//		CrmRevptNaverVO vo = new CrmRevptNaverVO();
//		vo.setRev_reservationkey("crm1");
//		vo.setRev_bookingkey("DBK-5kqrF3he-fEV9-4Xih-al2f-6an42yCOkrX9");
//		vo.setRev_datetime("2025-06-30 18:00:00");
//		vo.setRev_name("테스트1");
//		vo.setRev_phone("01031577148");
//		vo.setRev_state("BS_WAITING");
//		vo.setRev_naveruserid("8462068");
//		vo.setRev_bookingtype("NORMAL");
//		vo.setRev_platformtype("NAVER");
//		vo.setRev_cancelleddesc("네이버에서 예약 변경");
//		vo.setRev_email("test@test.com");
//		vo.setRev_userrequest("아프다");
//		vo.setRev_prebookingkey("");
//		vo.setRev_visitorname("방문자");
//		vo.setRev_visitorphone("01000001111");
//		vo.setRev_customformdata(""); //예약커스텀 정보
//		vo.setRev_itemoptions("");	//시술정보
//		vo.setRev_date("20250627");
//		vo.setRev_time("2130");
//		vo.setRev_confirm("N");
//		vo.setRev_ptno("");
//		vo.setRev_reservationname("내과진료");
//		
//		revptNaverMapper.updateRevptNaver(vo);
	}
}
