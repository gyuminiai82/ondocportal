package kr.ondoc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;

import org.ini4j.Ini;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;

import kr.ondoc.mapper.sybase.common.InitMapper;
import kr.ondoc.util.CommonUtil;
import kr.ondoc.util.Dasom2006INI;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableScheduling
public class OnDocPortalApplication {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private InitMapper mapper;

	public static void main(String[] args) {
		SpringApplication.run(OnDocPortalApplication.class, args);
	}
	
	@EventListener(ApplicationReadyEvent.class)
    public void init() throws Exception {
		try {
			String hosnum = mapper.selectHosnum();
			
			// 1. 현재 서버(로컬 호스트)의 InetAddress 객체를 얻어옵니다.
            InetAddress localHost = InetAddress.getLocalHost();
            
            // 2. IP 주소를 문자열로 변환합니다.
            String currentIP = localHost.getHostAddress().substring(0, localHost.getHostAddress().lastIndexOf('.'));
            
            
            //java에서도 회사면 실행 안되게 막음
            if(!(currentIP.equals("210.220.255"))) {
			//회사에서 접근시 갱신 안하도록 php에 막음
            	
            	//집에서 서버 실행시 ip정보 갱신 안하도록 막음
            	if(!getPublicIPAddress().equals("121.137.35.21")) {
            		//서버가 실행할때 on-doc.kr의 병원정보의 ip를 갱신해줌-병원의 공유기 아이피로 접근하는데 바뀔경우가 있어 갱신로직을 추가함
            		sendGet("https://on-doc.kr/public/index.php/hospitalInformationIpUpdate?token="+CommonUtil.token+"&num="+hosnum);
            	}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		//EMR 차트 암호화 실패 파일 재실행
		Ini dasom2006ini = Dasom2006INI.getDasom2006Ini();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		Process pro;
		Runtime rt = Runtime.getRuntime();
		
		try {
			String sEMRDrv = dasom2006ini.get("EMRChart", "Drive").replaceAll("=/=", "\\\\");
			String sEMRPath = dasom2006ini.get("EMRChart", "ChartPath").replaceAll("=/=", "\\\\");
			String path = sEMRDrv + sEMRPath;
			
			
			File dir = new File(path);
			FileFilter filter = new FileFilter() {
				public boolean accept(File f) {
					return f.getName().endsWith("emr");
				}
			};
			        
			File files[] = dir.listFiles(filter);
			for (File file : files) {
				String fileName = file.toString().replace(path, "");
				String year = sdf.format(file.lastModified());
				String ptno = fileName.split("_")[0];
				
				String exec = "C:\\StNeo\\Bin\\ChtDecrypt.exe E " + ptno + " " + year + " \"" + path+fileName + "\"";
				
				try {
					pro = rt.exec(exec);
					pro.waitFor();
				} catch (Exception e) {
					// TODO: handle exception
					logger.warn(e.getMessage());
				}
			}
			//EMR 차트 암호화 실패 파일 재실행
		} catch (Exception e) {
		}
    }
	
	void sendGet(String targetUrl) throws Exception {

		URL url = new URL(targetUrl);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();

		con.setRequestMethod("GET"); // optional default is GET
		con.setRequestProperty("User-Agent", "Mozilla/5.0"); // add request header

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
	}
	
	// 공인 IP 주소를 알려주는 간단한 외부 서비스 URL
    private String IP_CHECKER_URL = "http://checkip.amazonaws.com";
	
	public String getPublicIPAddress() {
        try {
            // 1. 외부 서비스 URL에 접속합니다.
            URL url = new URL(IP_CHECKER_URL);
            URLConnection urlConnection = url.openConnection();
            
            // 2. 응답 데이터를 읽어옵니다. (이 서비스는 응답 본문에 IP 주소만 문자열로 반환합니다.)
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            
            // 3. 첫 번째 줄(IP 주소)을 읽고 불필요한 공백을 제거합니다.
            String ip = in.readLine();
            in.close();
            
            return ip != null ? ip.trim() : null;
        } catch (Exception e) {
            // 네트워크 오류 등으로 IP를 가져오지 못한 경우
            System.err.println("외부 접속 오류: " + e.getMessage());
            return null;
        }
    }
}
