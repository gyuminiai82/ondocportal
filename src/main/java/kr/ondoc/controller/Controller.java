package kr.ondoc.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.HttpServletRequest;
import kr.ondoc.domain.sybase.common.DrVO;
import kr.ondoc.domain.sybase.common.OptionInfo2VO;
import kr.ondoc.domain.sybase.common.OptionInfoVO;
import kr.ondoc.domain.sybase.legacy.CommonVO;
import kr.ondoc.domain.sybase.legacy.HosinfoDataVO;
import kr.ondoc.domain.sybase.legacy.HosinfoVO;
import kr.ondoc.domain.sybase.legacy.VersionDataVO;
import kr.ondoc.domain.sybase.legacy.VersionVO;
import kr.ondoc.mapper.sybase.common.OptionInfoMapper;
import kr.ondoc.mapper.sybase.legacy.OldOptionInfoMapper;
import kr.ondoc.util.AES256;
import kr.ondoc.util.Sha256;

@CrossOrigin(origins = "*")
@RestController
public class Controller {
	@Autowired
	private OldOptionInfoMapper systemOption;
	
	@Autowired
	private OptionInfoMapper optionInfoMapper;
	
	private final RestTemplate restTemplate = new RestTemplate();
	
	@RequestMapping(value="/crmHosinfo", method=RequestMethod.GET)
    public ResponseEntity<String> selectHosinfo(String hosnum) throws Exception {
//		List<HosinfoDataVO> dataList = systemOption.selectHosinfo();
//		String url = "https://on-doc.kr/public/mobile_chart.php/onDocPortalHospital?hosnum=" + dataList.get(0).getBhi_hosnum();
		
		String url = "https://on-doc.kr/public/mobile_chart.php/onDocPortalHospital?hosnum=" + hosnum;
		
		try {
            // 외부 API 호출하여 JSON 문자열 받기
            String response = restTemplate.getForObject(url, String.class);
            
            // 받은 JSON을 그대로 200 OK 응답으로 반환
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json")
                    .body(response);
                    
        } catch (Exception e) {
            // 에러 발생 시 500 상태코드와 에러 메시지를 JSON으로 반환
            // 예: {"error": "Connection timeout"}
            String errorJson = String.format("{\"error\": \"%s\"}", 
                e.getMessage().replace("\"", "\\\""));
            
            return ResponseEntity.internalServerError()
                    .header("Content-Type", "application/json")
                    .body(errorJson);
        }
    }
	
	//차트매니저 옵션 설정 정보 가져오기
	//Option
	@RequestMapping(value="/optionInfo", method=RequestMethod.GET)
    public OptionInfoVO optionInfo() throws Exception {
		
        return optionInfoMapper.select();
    }
	
	//차트매니저 옵션 설정 정보 가져오기
	//Option2
	@RequestMapping(value="/optionInfo2", method=RequestMethod.GET)
    public OptionInfo2VO optionInfo2() throws Exception {
		
        return optionInfoMapper.selectOptioninfo2();
    }
	
	//외부업체에서 차트매니저 암호화 디코딩이 불가능하여 디코딩 할수 있게 만듬
	@RequestMapping(value="/decrypt", method=RequestMethod.GET)
    public DrVO selectDecrypt(String data) throws Exception {
		DrVO vo = new DrVO();
		if(data.length() == 12) {
			Runtime rt = Runtime.getRuntime();
			
			String exec = "C:\\StNeo\\Bin\\Resno2To3.exe "+data+" D";
			Process pro;
			
			try {
				pro = rt.exec(exec);
				pro.waitFor();
			} catch (Exception e) {
				// TODO: handle exception
				//logger.warn(e.getMessage());
			}
			
			String path = "C:\\StNeo\\Bin\\ondocportalserver\\" + data + ".txt";
			
			FileInputStream input=new FileInputStream(path);
	        InputStreamReader reader=new InputStreamReader(input,"EUC-KR");
	        BufferedReader in=new BufferedReader(reader);
			 
	        int ch;
	        
	        StringBuilder stringBuilder = new StringBuilder();
	        while ((ch = reader.read()) != -1) {
	        	stringBuilder.append((char) ch);
	        }
	        
	        input.close();
	        
	        File file = new File(path);
	        
	    	if( file.exists() ){
	    		if(file.delete()){
	    			//System.out.println("delete file success");
	    		}else{
	    			//System.out.println("delete file failure");
	    		}
	    	}else{
	    		//System.out.println("not exist file");
	    	}
	    	
	    	vo.setData(stringBuilder.toString().trim());
	        
	        return vo;
		} else {
			vo.setData((new AES256()).decrypt(data));
			
			return vo;
			
		}
    }
	
	@RequestMapping(value="/aes256encrypt", method=RequestMethod.GET)
    public String selectAes256Encrypt(String data) throws Exception {
		return (new AES256()).encrypt(data);
    }
	
	
	//db암호화 값 확인용 
	@RequestMapping(value="/sha256", method=RequestMethod.GET)
    public String selectSha256(HttpServletRequest request, String data) throws Exception {
		return Sha256.hash(data);
    }
	
//	@RequestMapping(value="/aes256decrypt", method=RequestMethod.GET)
//    public String selectAes256Decrypt(String data) throws Exception {
//		return (new AES256()).decrypt(data);
//    }
	
	//사용여부 불확실
	//온닥으로 푸쉬메시지 보내기 위해 만들것인지?
	@RequestMapping(value = "/call", method = RequestMethod.GET)
	// public UsrmngrVO usrmngrSelect(UsrmngrVO vo) throws Exception {
	public CommonVO call() throws Exception {
		try {
			Runtime rt = Runtime.getRuntime();

			String exec = "C:\\StNeo\\Bin\\OnDocMsg.exe";
			Process pro;

			try {
				pro = rt.exec(exec);
				pro.waitFor();
			} catch (Exception e) {
				// TODO: handle exception
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return new CommonVO();
	}
	
	//온닥서버 설치된 버전 확인
	@RequestMapping(value="/version", method=RequestMethod.GET)
    public VersionVO selectversion() throws Exception {
		VersionVO resultVO = new VersionVO();
		
		resultVO.setVersion("2.92");
		resultVO.setPcName(InetAddress.getLocalHost().getHostName());
		resultVO.setIp(InetAddress.getLocalHost().getHostAddress());
		
		List<VersionDataVO> dataList = new ArrayList<VersionDataVO>();
		
		VersionDataVO versionDataVO = new VersionDataVO();
		versionDataVO.setDate("2023-05-18");
		versionDataVO.setUpdate("온닥서버 자바버전 개발");
		
		dataList.add(versionDataVO);
		
		versionDataVO = new VersionDataVO();
		versionDataVO.setDate("2022-09-13");
		versionDataVO.setUpdate("주민등록 정상적으로 나오도록 수정");
		
		dataList.add(versionDataVO);
		
		versionDataVO = new VersionDataVO();
		versionDataVO.setDate("2023-08-01");
		versionDataVO.setUpdate("온닥포털 펜차트 작업");
		
		dataList.add(versionDataVO);
		
		versionDataVO = new VersionDataVO();
		versionDataVO.setDate("2023-08-28");
		versionDataVO.setUpdate("온닥포털 동의서 작업");
		
		dataList.add(versionDataVO);
		
		versionDataVO = new VersionDataVO();
		versionDataVO.setDate("2023-09-05");
		versionDataVO.setUpdate("건강검진 모바일검색 기능 구현");
		
		dataList.add(versionDataVO);
		
		versionDataVO = new VersionDataVO();
		versionDataVO.setDate("2023-10-20");
		versionDataVO.setUpdate("누락된 url 추가");
		
		dataList.add(versionDataVO);
		
		versionDataVO = new VersionDataVO();
		versionDataVO.setDate("2023-10-24");
		versionDataVO.setUpdate("ReceiptPatientNoConvert url 추가");
		
		dataList.add(versionDataVO);
		
		versionDataVO = new VersionDataVO();
		versionDataVO.setDate("2023-11-30");
		versionDataVO.setUpdate("무인접수 url 추가");
		
		dataList.add(versionDataVO);
		
		versionDataVO = new VersionDataVO();
		versionDataVO.setDate("2023-12-11");
		versionDataVO.setUpdate("010 연락처 추가 오류 수정");
		
		dataList.add(versionDataVO);
		
		versionDataVO = new VersionDataVO();
		versionDataVO.setDate("2023-12-14");
		versionDataVO.setUpdate("서식 번호 없어도 무시하고 되도록 수정 및 동의서 설정 80, 99 무시하도록 수정");
		
		dataList.add(versionDataVO);
		
		versionDataVO = new VersionDataVO();
		versionDataVO.setDate("2023-12-27");
		versionDataVO.setUpdate("건강검진 인지기능장애 추가");
		
		dataList.add(versionDataVO);
		
		versionDataVO = new VersionDataVO();
		versionDataVO.setDate("2024-01-16");
		versionDataVO.setUpdate("기존 연락처에 -이외의 값이 있어도 검색되도록 수정-2.35");
		
		dataList.add(versionDataVO);
		
		versionDataVO = new VersionDataVO();
		versionDataVO.setDate("2024-01-23");
		versionDataVO.setUpdate("신규서식 노출 안되는 오류 수정-2.36");
		
		dataList.add(versionDataVO);
		
		versionDataVO = new VersionDataVO();
		versionDataVO.setDate("2024-02-19");
		versionDataVO.setUpdate("건강검진 동의서 추가");
		
		dataList.add(versionDataVO);
		
		versionDataVO = new VersionDataVO();
		versionDataVO.setDate("2024-03-05");
		versionDataVO.setUpdate("작음 따옴표 처리시 화면로딩 오류 수정");
		
		dataList.add(versionDataVO);
		
		versionDataVO = new VersionDataVO();
		versionDataVO.setDate("2024-03-14");
		versionDataVO.setUpdate("건강검진 날짜 데이터 오류 수정");
		
		dataList.add(versionDataVO);
		
		versionDataVO = new VersionDataVO();
		versionDataVO.setDate("2024-04-15");
		versionDataVO.setUpdate("건강검진 날짜 데이터 오류 수정");
		
		dataList.add(versionDataVO);
					
		versionDataVO = new VersionDataVO();
		versionDataVO.setDate("2024-04-29");
		versionDataVO.setUpdate("대기환자/완료환자 동의서 카운트 수 추가");
		
		dataList.add(versionDataVO);
		
		versionDataVO = new VersionDataVO();
		versionDataVO.setDate("2024-05-14");
		versionDataVO.setUpdate("신분증 확인 기능 추가");
		
		dataList.add(versionDataVO);
		
		versionDataVO = new VersionDataVO();
		versionDataVO.setDate("2024-06-13");
		versionDataVO.setUpdate("검사결과 장문메시지 추가(legacy만)");
		
		dataList.add(versionDataVO);
		
		versionDataVO = new VersionDataVO();
		versionDataVO.setDate("2024-07-10");
		versionDataVO.setUpdate("mobuse조건 제거 온닥접수 체크 안되어 있어도 과정보 가지고 오도록 수정");
		
		dataList.add(versionDataVO);
		
		versionDataVO = new VersionDataVO();
		versionDataVO.setDate("2024-07-16");
		versionDataVO.setUpdate("서식 입원일 입력 추가");
		
		dataList.add(versionDataVO);
		
		versionDataVO = new VersionDataVO();
		versionDataVO.setDate("2024-08-08");
		versionDataVO.setUpdate("수정시 과거연도 폴더에 저장되도록 수정");
		
		dataList.add(versionDataVO);
		
		versionDataVO = new VersionDataVO();
		versionDataVO.setDate("2024-08-23");
		versionDataVO.setUpdate("펜차트 서식 로딩 방식 수정");
		
		dataList.add(versionDataVO);
		
		versionDataVO = new VersionDataVO();
		versionDataVO.setDate("2024-08-30");
		versionDataVO.setUpdate("중간 구분 특수문자 깨짐 오류 수정");
		
		dataList.add(versionDataVO);
		
		versionDataVO = new VersionDataVO();
		versionDataVO.setDate("2024-09-09");
		versionDataVO.setUpdate("EMR 서식 암호화 실패파일 서버시작시 재실행");
		
		dataList.add(versionDataVO);
		
		versionDataVO = new VersionDataVO();
		versionDataVO.setDate("2024-09-13");
		versionDataVO.setUpdate("진료실 정렬 진료실 이름 순으로 변경");
		
		dataList.add(versionDataVO);
		
		versionDataVO = new VersionDataVO();
		versionDataVO.setDate("2024-11-01");
		versionDataVO.setUpdate("예약 구간별 남은횟수 계산오류 수정");
		
		dataList.add(versionDataVO);
		
		versionDataVO = new VersionDataVO();
		versionDataVO.setDate("2024-11-28");
		versionDataVO.setUpdate("본인확인 만료여부 추가");
		
		dataList.add(versionDataVO);
		
		versionDataVO = new VersionDataVO();
		versionDataVO.setDate("2025-01-06");
		versionDataVO.setUpdate("신규설치 무인접수시 1자리수 차트번호 오류 수정");
		
		dataList.add(versionDataVO);
		
		versionDataVO = new VersionDataVO();
		versionDataVO.setDate("2025-01-21");
		versionDataVO.setUpdate("서식저장시 암호와 여부 옵션에 따라 동작하도록 수정");
		
		dataList.add(versionDataVO);
		
		versionDataVO = new VersionDataVO();
		versionDataVO.setDate("2025-03-07");
		versionDataVO.setUpdate("온닥 예약시 오늘날짜 선택 가능하도록 변경");
		
		dataList.add(versionDataVO);
		
		versionDataVO = new VersionDataVO();
		versionDataVO.setDate("2025-03-12");
		versionDataVO.setUpdate("온닥 예약시 오늘날짜 선택 안하도록 변경");
		
		dataList.add(versionDataVO);
		
		versionDataVO = new VersionDataVO();
		versionDataVO.setDate("2025-03-17");
		versionDataVO.setUpdate("온닥펜차트 잠금해제 기능 추가");
		
		dataList.add(versionDataVO);
		
		versionDataVO = new VersionDataVO();
		versionDataVO.setDate("2025-03-18");
		versionDataVO.setUpdate("온닥펜차트 권한 조건 uid 누락으로 인한 오류 수정");
		
		dataList.add(versionDataVO);
		
		versionDataVO = new VersionDataVO();
		versionDataVO.setDate("2025-03-19");
		versionDataVO.setUpdate("온닥플러스 환자조회 속도개선");
		
		dataList.add(versionDataVO);
		
		versionDataVO = new VersionDataVO();
		versionDataVO.setDate("2025-03-20");
		versionDataVO.setUpdate("온닥플러스 환자조회 ui변경");	//새봄여성만 적용
		
		dataList.add(versionDataVO);
		
		versionDataVO = new VersionDataVO();
		versionDataVO.setDate("2025-03-28");
		versionDataVO.setUpdate("온닥포털 펜차트 서식트리구조 차트매니저와 동일하도록 수정");
		
		dataList.add(versionDataVO);
		
		versionDataVO = new VersionDataVO();
		versionDataVO.setDate("2025-04-06");
		versionDataVO.setUpdate("무인접수 전화번호 1개인데 2개라고 나와서 쿼리 수정");
		
		dataList.add(versionDataVO);
		
		versionDataVO = new VersionDataVO();
		versionDataVO.setDate("2025-05-15");
		versionDataVO.setUpdate("펜차트 주민번호 뒷자리 잘림 수정");
		
		dataList.add(versionDataVO);
		
		versionDataVO = new VersionDataVO();
		versionDataVO.setDate("2025-06-24");
		versionDataVO.setUpdate("펜차트 파일저장을 db저장으로 변경(옶견에 따라)");
		
		dataList.add(versionDataVO);
		
		versionDataVO = new VersionDataVO();
		versionDataVO.setDate("2025-09-25");
		versionDataVO.setUpdate("서식 SIGN 로딩시 화면에 표시되도록 수정");
		
		dataList.add(versionDataVO);
		
		versionDataVO = new VersionDataVO();
		versionDataVO.setDate("2025-10-17");
		versionDataVO.setUpdate("차트와 서식간 매핑이 차트 삭제후 다시 차트생성하여 매핑하면 2row return에러로 사용여부 조건을 추가함");
		
		dataList.add(versionDataVO);
		
		versionDataVO = new VersionDataVO();
		versionDataVO.setDate("2025-11-11");
		versionDataVO.setUpdate("온닥CRM 인스톨 프로그램 실행 추가");
		
		dataList.add(versionDataVO);
		
		versionDataVO = new VersionDataVO();
		versionDataVO.setDate("2025-11-17");
		versionDataVO.setUpdate("온닥CRM 오류 수정");
		
		dataList.add(versionDataVO);
		
		versionDataVO = new VersionDataVO();
		versionDataVO.setDate("2025-11-27");
		versionDataVO.setUpdate("db접속시 캐릭터셋 utf8로 변경");
		
		dataList.add(versionDataVO);
		
		versionDataVO = new VersionDataVO();
		versionDataVO.setDate("2025-12-02");
		versionDataVO.setUpdate("캐릭터셋 변경후 쿼리에 한글 있으면 오류나는 버그 수정");
		
		dataList.add(versionDataVO);
		
		versionDataVO = new VersionDataVO();
		versionDataVO.setDate("2026-01-19");
		versionDataVO.setUpdate("온닥포털 환자등록 기능 추가");
		
		dataList.add(versionDataVO);
		
		resultVO.setData(dataList);
		
		return resultVO;
    }
	
	@RequestMapping(value="/log", method=RequestMethod.GET)
    public CommonVO log(String message) throws Exception {
		return new CommonVO();
	}
}
