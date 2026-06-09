package kr.ondoc.controller.legacy.penchart;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import kr.ondoc.util.AES256;
import kr.ondoc.util.Dasom2006INI;

import org.ini4j.Ini;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.ondoc.domain.sybase.legacy.CommonVO;
import kr.ondoc.domain.sybase.legacy.penchart.EmrAddDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.EmrAddVO;
import kr.ondoc.domain.sybase.legacy.penchart.EmrAttachParamVO;
import kr.ondoc.domain.sybase.legacy.penchart.EmrAttachTempVO;
import kr.ondoc.domain.sybase.legacy.penchart.EmrAttachVO;
import kr.ondoc.domain.sybase.legacy.penchart.EmrAuthDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.EmrAuthParamVO;
import kr.ondoc.domain.sybase.legacy.penchart.EmrAuthVO;
import kr.ondoc.domain.sybase.legacy.penchart.EmrCopyDataVo;
import kr.ondoc.domain.sybase.legacy.penchart.EmrCopyVO;
import kr.ondoc.domain.sybase.legacy.penchart.EmrDateParamVO;
import kr.ondoc.domain.sybase.legacy.penchart.EmrDateVO;
import kr.ondoc.domain.sybase.legacy.penchart.EmrAttachDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.EmrNewDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.EmrNewPageVO;
import kr.ondoc.domain.sybase.legacy.penchart.EmrNewVO;
import kr.ondoc.domain.sybase.legacy.penchart.EmrOldParamVO;
import kr.ondoc.domain.sybase.legacy.penchart.EmrOpenDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.EmrOpenVO;
import kr.ondoc.domain.sybase.legacy.penchart.EmrReplaceParamVO;
import kr.ondoc.domain.sybase.legacy.penchart.EmrReplaceVO;
import kr.ondoc.domain.sybase.legacy.penchart.EmrSavedDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.EmrSavedVO;
import kr.ondoc.domain.sybase.legacy.penchart.EmrTempVO;
import kr.ondoc.domain.sybase.legacy.penchart.EmrTimeParamVO;
import kr.ondoc.domain.sybase.legacy.penchart.FormVO;
import kr.ondoc.mapper.sybase.legacy.OldOptionInfoMapper;
import kr.ondoc.mapper.sybase.legacy.penchart.OldEmrMapper;
import kr.ondoc.mapper.sybaseemr.legacy.penchart.DbOldEmrMapper;
import kr.ondoc.util.EmrSheet;

@CrossOrigin(origins = "*")
@RestController
public class OldEmrController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private OldOptionInfoMapper systemOption;
	
	@Autowired
	private OldEmrMapper oldEmrMapper;
	
	@Autowired
	private DbOldEmrMapper dbOldEmrMapper;
	
	@RequestMapping(value= {"/mobileChartEmr", "/emrSignpenChartEmr"}, method=RequestMethod.GET)
    public EmrNewVO selectPenchartNewEmr(EmrNewPageVO vo) throws Exception {
		String base72 = systemOption.selectBase72();	//0: 암호화 사용함, 1: 암호화 사용안함, 2: db사용
		
		EmrNewVO resultVO = new EmrNewVO();
		List<EmrNewDataVO> dataList = null;
		
		if(!vo.getBes_id().equals("")) {
			//todo emrchart에 insert한 값 select
			EmrTempVO emrTempVO = null;
			
			if(base72.equals("2")) {
				//emrchart에 insert
				dbOldEmrMapper.insertNewEmr(vo);
				
				emrTempVO = dbOldEmrMapper.selectTempEmr(vo);
				
				//Page(bef_form) 가져오기
				dataList = dbOldEmrMapper.selectPage(vo);
			} else {
				//emrchart에 insert
				oldEmrMapper.insertNewEmr(vo);
				
				emrTempVO = oldEmrMapper.selectTempEmr(vo);
				
				//Page(bef_form) 가져오기
				dataList = oldEmrMapper.selectPage(vo);
			}
			
			dataList.get(0).setKey(emrTempVO.getOec_seq());
			dataList.get(0).setDate(emrTempVO.getDate());
			dataList.get(0).setTime(emrTempVO.getTime());
		} else {
			List<FormVO> formList = null;
			
			if(base72.equals("2")) {
				//Panel(bef_no)
				//bef_form값이 너무 커서 text로 저장해서 가져오도록 변경
				dataList = dbOldEmrMapper.selectPanel(vo);
				
				formList = dbOldEmrMapper.selectForm(dataList.get(0).getQuery());
			} else {
				//Panel(bef_no)
				//bef_form값이 너무 커서 text로 저장해서 가져오도록 변경
				dataList = oldEmrMapper.selectPanel(vo);
				
				formList = oldEmrMapper.selectForm(dataList.get(0).getQuery());
			}
			
			StringBuilder sbForm = new StringBuilder();
			for (FormVO form : formList) {
				sbForm.append(form.getForm());
	        }
	        
	        dataList.get(0).setBef_form(sbForm.toString().trim());
		}
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		return resultVO;
    }
	
	@RequestMapping(value={"/mobileChartEmr", "/emrSignpenChartEmr"}, method=RequestMethod.PUT)
    public CommonVO selectPenchartNewEmrSave(EmrNewPageVO vo) throws Exception {
		String base72 = systemOption.selectBase72();	//0: 암호화 사용함, 1: 암호화 사용안함, 2: db사용
		
		CommonVO resultVO = new CommonVO();
		
		if(base72.equals("2")) {
			//db 사용
			//oec_file 필드에 값이 없으면 차트매니저에서 차트 내용 못 가져와서 넣어줌
			String fileName = vo.getPtno() + "_" + vo.getKey() + "_" + vo.getTitle() + ".emr";
			vo.setFile_name(fileName);
			
			vo.setSheet(vo.getSheet().replaceAll("·", "・"));
			
			dbOldEmrMapper.updateNewEmr(vo);
		} else {
			Ini dasom2006ini = Dasom2006INI.getDasom2006Ini();
			
			String sEMRDrv = dasom2006ini.get("EMRChart", "Drive").replaceAll("=/=", "\\\\");
			String sEMRPath = dasom2006ini.get("EMRChart", "ChartPath").replaceAll("=/=", "\\\\");
			String fileName = vo.getPtno() + "_" + vo.getKey() + "_" + vo.getTitle() + ".emr";
			
			if(!base72.equals("1")) {	//0: 암호화 사용함, 1: 암호화 사용안함, 2: db사용
	        	//암호화 함
	    		String path = sEMRDrv + sEMRPath + fileName;
	    		
	    		File file = new File(path);
	    		
	            // 2. 파일 존재여부 체크 및 생성
	            if (!file.exists()) {
	                file.createNewFile();
	            }
	            
	            FileOutputStream fos = new FileOutputStream(file);
	            
	            fos.write(vo.getSheet().replaceAll("・", "·").getBytes("EUC-KR"));
	            
	            fos.close();
	            
	            Date nowDate = new Date();
	    		
	            SimpleDateFormat format = new SimpleDateFormat("yyyy");
	            
	            Runtime rt = Runtime.getRuntime();
	    		
	    		String exec = "C:\\StNeo\\Bin\\ChtDecrypt.exe E "+vo.getPtno()+" "+format.format(nowDate)+" \"" + path + "\"";
	    		Process pro;
	    		
	    		try {
	    			pro = rt.exec(exec);
	    			pro.waitFor();
	    		} catch (Exception e) {
	    			// TODO: handle exception
	    			logger.warn(e.getMessage());
	    		}
	        } else {
	        	//암호화 안함
	        	Date nowDate = new Date();
	    		
	        	SimpleDateFormat format = new SimpleDateFormat("yyyy");
				
				String directory = sEMRDrv + sEMRPath + vo.getPtno() + "\\" + format.format(nowDate);
		        
		        Path directoryPath = Paths.get(directory);
		        try {
					Files.createDirectories(directoryPath);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		        String path = directory +"\\" + fileName;
		        
				File file = new File(path);
	    		
	            // 2. 파일 존재여부 체크 및 생성
	            if (!file.exists()) {
	                file.createNewFile();
	            }
	            
	            FileOutputStream fos = new FileOutputStream(file);
	            
	            fos.write(vo.getSheet().replaceAll("・", "·").getBytes("EUC-KR"));
	            
	            fos.close();
	        }
			
			vo.setFile_name(fileName);
			
			oldEmrMapper.updateNewEmr(vo);
		}
		
		return resultVO;
    }
	
	@RequestMapping(value="/mobileChartEmr", method=RequestMethod.DELETE)
    public CommonVO selectPenchartNewEmrDelete(EmrNewPageVO vo) throws Exception {
		String base72 = systemOption.selectBase72();	//0: 암호화 사용함, 1: 암호화 사용안함, 2: db사용
		
		CommonVO resultVO = new CommonVO();
		
		if(base72.equals("2")) {
			dbOldEmrMapper.deleteNewEmr(vo);
		} else {
			oldEmrMapper.deleteNewEmr(vo);
		}
		
		return resultVO;
    }
	
	@RequestMapping(value={"/mobileChartOldEmr", "/emrSignpenChartOldEmr"}, method=RequestMethod.GET)
    public EmrSavedVO selectPenchartOldEmr(EmrOldParamVO vo) throws Exception {
		String base72 = systemOption.selectBase72();	//0: 암호화 사용함, 1: 암호화 사용안함, 2: db사용
		
		EmrSavedVO resultVO = new EmrSavedVO();
		EmrSheet emrSheet = new EmrSheet();
		
		//락이 걸려 있는지 확인하기 위해 SELECT
		EmrSavedDataVO temp = null;
		
		if(base72.equals("2")) {
			temp = dbOldEmrMapper.selectLockCheck(vo);
		} else {
			temp = oldEmrMapper.selectLockCheck(vo);
		}
		
		//락이 걸려 있을경우 같은 기기이면 락을 해제 OR 락을 건지 12시간이 지난 경우이면 락을 해제
		if(temp.getOec_lockyn().equals("1")) {
			if(temp.getOec_loichost().equals(vo.getUuid()) || Integer.parseInt(temp.getLock_hour()) > 12) {
				
				if(base72.equals("2")) {
					dbOldEmrMapper.updateSavedEmr(vo);
				} else {
					oldEmrMapper.updateSavedEmr(vo);
				}
			}
		}
		
		List<EmrSavedDataVO> dataList = null;

		if(base72.equals("2")) {
			dataList = dbOldEmrMapper.selectSavedEmr(vo);
		} else {
			dataList = oldEmrMapper.selectSavedEmr(vo);
		}
		
		if(base72.equals("2")) {
			//db 사용
		} else {
			if(!base72.equals("1")) {
				dataList.get(0).setSheet(emrSheet.sheetRead(dataList.get(0).getOec_ptno(), dataList.get(0).getYear(), dataList.get(0).getOec_file()));
			} else {
				Ini dasom2006ini = Dasom2006INI.getDasom2006Ini();
				
				String sEMRDrv = dasom2006ini.get("EMRChart", "Drive").replaceAll("=/=", "\\\\");
				String sEMRPath = dasom2006ini.get("EMRChart", "ChartPath").replaceAll("=/=", "\\\\");
				
				String path = sEMRDrv + sEMRPath + dataList.get(0).getOec_ptno() + '\\' + dataList.get(0).getYear() + '\\' + dataList.get(0).getOec_file();
				
				StringBuilder stringBuilder = new StringBuilder();
				
				try {
					FileInputStream input=new FileInputStream(path);
			        InputStreamReader reader=new InputStreamReader(input,"EUC-KR");
			        BufferedReader in=new BufferedReader(reader);
					 
			        int ch;
			        while ((ch = reader.read()) != -1) {
			        	stringBuilder.append((char) ch);
			        }
			        
			        input.close();
			        
			        dataList.get(0).setSheet(stringBuilder.toString());
				} catch (Exception e) {
					
					dataList.get(0).setSheet(emrSheet.sheetReadNotDelete(dataList.get(0).getOec_ptno(), dataList.get(0).getYear(), dataList.get(0).getOec_file()));
				}
			}
		}
		
		if(vo.getAccess().equals("lock")) {
			if(base72.equals("2")) {
				dbOldEmrMapper.updateEmrLock(vo);
			} else {
				oldEmrMapper.updateEmrLock(vo);
			}
		}
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		return resultVO;
    }
	
	@RequestMapping(value={"/mobileChartOldEmr", "/emrSignpenChartOldEmr"}, method=RequestMethod.PUT)
    public CommonVO selectPenchartOldEmrSave(EmrNewPageVO vo) throws Exception {
		String base72 = systemOption.selectBase72();	//0: 암호화 사용함, 1: 암호화 사용안함, 2: db사용
		
		CommonVO resultVO = new CommonVO();
		
		if(base72.equals("2")) {
			//db 사용
			//oec_file 필드에 값이 없으면 차트매니저에서 차트 내용 못 가져와서 넣어줌
			String fileName = vo.getPtno() + "_" + vo.getKey() + "_" + vo.getTitle() + ".emr";
			vo.setFile_name(fileName);
			
			vo.setSheet(vo.getSheet().replaceAll("·", "・"));
			
			dbOldEmrMapper.updateNewEmr(vo);
		} else {
			Ini dasom2006ini = Dasom2006INI.getDasom2006Ini();
			
			String sEMRDrv = dasom2006ini.get("EMRChart", "Drive").replaceAll("=/=", "\\\\");
			String sEMRPath = dasom2006ini.get("EMRChart", "ChartPath").replaceAll("=/=", "\\\\");
			String fileName = vo.getPtno() + "_" + vo.getKey() + "_" + vo.getTitle() + ".emr";
	        
	        if(!base72.equals("1")) {
	        	//암호화 함
	    		String path = sEMRDrv + sEMRPath + fileName;
	    		
	    		File file = new File(path);
	    		
	            // 2. 파일 존재여부 체크 및 생성
	            if (!file.exists()) {
	                file.createNewFile();
	            }
	            
	            FileOutputStream fos = new FileOutputStream(file);
	            
	            fos.write(vo.getSheet().replaceAll("・", "·").getBytes("EUC-KR"));
	            
	            fos.close();
	            
	            //Date nowDate = new Date();
	            String strDate = vo.getDate();
	    		
	            SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd");
	            SimpleDateFormat format = new SimpleDateFormat("yyyy");
	    		
	    		Date formatDate = dtFormat.parse(strDate);
	            
	            Runtime rt = Runtime.getRuntime();
	    		
	    		String exec = "C:\\StNeo\\Bin\\ChtDecrypt.exe E "+vo.getPtno()+" "+format.format(formatDate)+" \"" + path + "\"";
	    		Process pro;
	    		
	    		try {
	    			pro = rt.exec(exec);
	    			pro.waitFor();
	    		} catch (Exception e) {
	    			// TODO: handle exception
	    			logger.warn(e.getMessage());
	    		}
	        } else {
	        	//암호화 안함
	        	String strDate = vo.getDate();

	    		SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd");
	    		SimpleDateFormat format = new SimpleDateFormat("yyyy");
	     		
	    		Date formatDate = null;
		        
		        try {
					formatDate = dtFormat.parse(strDate);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
		        String directory = sEMRDrv + sEMRPath + vo.getPtno() + "\\" + format.format(formatDate);
		        
		        Path directoryPath = Paths.get(directory);
		        try {
					Files.createDirectories(directoryPath);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String path = directory +"\\" + fileName;
				
				File file = new File(path);
	    		
	            // 2. 파일 존재여부 체크 및 생성
	            if (!file.exists()) {
	                file.createNewFile();
	            }
	            
	            FileOutputStream fos = new FileOutputStream(file);
	            
	            fos.write(vo.getSheet().replaceAll("・", "·").getBytes("EUC-KR"));
	            
	            fos.close();
	        }
			
			vo.setFile_name(fileName);
			
			oldEmrMapper.updateNewEmr(vo);
		}
		
		return resultVO;
    }
	
	@RequestMapping(value={"/mobileChartEmrReplace", "/emrSignpenChartEmrReplace"}, method=RequestMethod.GET)
    public String selectPenchartEmrReplace(EmrReplaceParamVO vo) throws Exception {
		String[] arrDataField = vo.getData_field().split("\\^");
		String[] arrItemField = vo.getItem_field().split("\\^");
		
		JSONArray arrData = new JSONArray();

		if(vo.getData_name().equals("BASIC")) {
			for (int i=0; i<arrDataField.length; i++) {
				JSONObject obj = new JSONObject();
				if(arrDataField[i].equals("DATE")) {
					Date nowDate = new Date();
					
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					
					obj.put(arrItemField[i], format.format(nowDate));
				} else if(arrDataField[i].equals("TIME")) {
					Date nowDate = new Date();
					
					SimpleDateFormat format = new SimpleDateFormat("HH:mm");
					
					obj.put(arrItemField[i], format.format(nowDate));
				} else if(arrDataField[i].equals("UNM")) {
					Date nowDate = new Date();
					
					obj.put(arrItemField[i], vo.getName());
				} else if(arrDataField[i].equals("INCHARGE")) {
					obj.put(arrItemField[i], oldEmrMapper.selectReplaceIncharge(vo.getRegno()));
				}
				
				arrData.put(obj);
			}
		} else if (vo.getData_name().equals("PATIENT")) {
			
			EmrReplaceVO erVo = oldEmrMapper.selectReplacePatient(vo.getPtno());
			
			for (int i=0; i<arrDataField.length; i++) {
				JSONObject obj = new JSONObject();
				
				if(arrDataField[i].equals("bpt_addr")) {
					obj.put(arrItemField[i], erVo.getBpt_addr().trim());
				} else if(arrDataField[i].equals("bpt_sex")) {
					if(erVo.getBpt_sex().trim().equals("MAN")) {
						obj.put(arrItemField[i], "남자");
					} else {
						obj.put(arrItemField[i], "여자");
					}
				} else if(arrDataField[i].equals("bpt_resno")) {
					obj.put(arrItemField[i], erVo.getBpt_resno().substring(0, 6) + "-" + erVo.getBpt_resno().substring(6, 7) + (new AES256()).decrypt(erVo.getBpt_resno3()));
				} else if(arrDataField[i].equals("bpt_ptno")) {
					obj.put(arrItemField[i], erVo.getBpt_ptno());
				} else if(arrDataField[i].equals("bpt_name")) {
					obj.put(arrItemField[i], erVo.getBpt_name());
				} else if(arrDataField[i].equals("bpt_yage")) {
					obj.put(arrItemField[i], erVo.getBpt_yage());
				} else if(arrDataField[i].equals("bpt_telno")) {
					obj.put(arrItemField[i], erVo.getBpt_telno());
				} else if(arrDataField[i].equals("bpt_hpno")) {
					obj.put(arrItemField[i], erVo.getBpt_hpno());
				} else if(arrDataField[i].equals("bpt_birth")) {
					obj.put(arrItemField[i], erVo.getBpt_birth());
				} 
				
				arrData.put(obj);
			}
		} else if (vo.getData_name().equals("IPST")) {
			for (int i=0; i<arrDataField.length; i++) {
				JSONObject obj = new JSONObject();
				String result = oldEmrMapper.selectReplaceIpst(arrDataField[i], vo.getPtno());
				obj.put(arrItemField[i], result);
				
				arrData.put(obj);
			}
		} else if (vo.getData_name().equals("IPWON")) {
			for (int i=0; i<arrDataField.length; i++) {
				JSONObject obj = new JSONObject();
				String result = oldEmrMapper.selectReplaceIpwon(arrDataField[i], vo.getPtno());
				obj.put(arrItemField[i], result);
				
				arrData.put(obj);
			}
		} else if (vo.getData_name().equals("QUALIFY")) {
			for (int i=0; i<arrDataField.length; i++) {
				JSONObject obj = new JSONObject();
				
				String result = oldEmrMapper.selectReplaceQualify(arrDataField[i], vo.getPtno());
				obj.put(arrItemField[i], result);
				
				arrData.put(obj);
			}
		} else if (vo.getData_name().equals("DOCTOR")) {
			for (int i=0; i<arrDataField.length; i++) {
				JSONObject obj = new JSONObject();
				
				String result = oldEmrMapper.selectReplaceDoctor(arrDataField[i], vo.getId());
				obj.put(arrItemField[i], result);
				
				arrData.put(obj);
			}
		} else if (vo.getData_name().equals("HOSINFO")) {
			Date nowDate = new Date();
			
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
						
			for (int i=0; i<arrDataField.length; i++) {
				JSONObject obj = new JSONObject();
				
				String result = oldEmrMapper.selectReplaceHosinfo(arrDataField[i], vo.getId(), format.format(nowDate));
				obj.put(arrItemField[i], result);
				
				arrData.put(obj);
			}
		}
		
		JSONObject obj = new JSONObject();
		obj.put("status", "200");
		obj.put("message", "success");
		obj.put("totalCount", "1");
		obj.put("data", arrData);
		
		return obj.toString();
    }

	@RequestMapping(value="/mobileChartEmrLockClear", method=RequestMethod.PUT)
    public CommonVO selectPenchartLockClear(EmrOldParamVO vo) throws Exception {
		String base72 = systemOption.selectBase72();	//0: 암호화 사용함, 1: 암호화 사용안함, 2: db사용
		
		CommonVO resultVO = new CommonVO();
		
		if(base72.equals("2")) {
			//db 사용
			dbOldEmrMapper.updatLockClear(vo);
		} else {
			oldEmrMapper.updatLockClear(vo);
		}
		
		return resultVO;
    }
	
	@RequestMapping(value="/mobileChartEmrLockOtherClear", method=RequestMethod.PUT)
    public CommonVO selectPenchartLockOtherClear(EmrOldParamVO vo) throws Exception {
		String base72 = systemOption.selectBase72();	//0: 암호화 사용함, 1: 암호화 사용안함, 2: db사용
		CommonVO resultVO = new CommonVO();
		
		if(base72.equals("2")) {
			dbOldEmrMapper.updatLockOtherClear(vo);
		} else {
			oldEmrMapper.updatLockOtherClear(vo);
		}
		
		return resultVO;
    }
	
	@RequestMapping(value="/mobileChartEmrOpen", method=RequestMethod.GET)
    public EmrOpenVO selectPenchartOpen(EmrOldParamVO vo) throws Exception {
		String base72 = systemOption.selectBase72();	//0: 암호화 사용함, 1: 암호화 사용안함, 2: db사용
		
		EmrOpenVO resultVO = new EmrOpenVO();
		
		List<EmrOpenDataVO> dataList = null;
		
		if(base72.equals("2")) {
			//db 사용
			dataList = dbOldEmrMapper.selectOpen(vo);
		} else {
			dataList = oldEmrMapper.selectOpen(vo);			
		}
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		return resultVO;
    }
	
	@RequestMapping(value="/mobileChartEmrAdd", method=RequestMethod.GET)
    public EmrAddVO selectPenchartAdd(EmrOldParamVO vo) throws Exception {
		String base72 = systemOption.selectBase72();	//0: 암호화 사용함, 1: 암호화 사용안함, 2: db사용
		
		EmrAddVO resultVO = new EmrAddVO();
		
		String kind = oldEmrMapper.selectAdd1(vo);
		
		String gubun = "";
		
		if(kind != null) {
			if(kind.equals("0")) {
				gubun = "2";
			} else if(kind.equals("1") || kind.equals("2") || kind.equals("3")) {
				gubun = "5";
			} else if(kind.equals("5") || kind.equals("6") || kind.equals("7") || kind.equals("8") || kind.equals("9")) {
				gubun = "1";
			}
		}
		
		vo.setGubun(gubun);
		
		List<EmrAddDataVO> dataList = null;
		
		if(base72.equals("2")) {
			//db 사용
			dataList = dbOldEmrMapper.selectAdd2(vo);
		} else {
			dataList = oldEmrMapper.selectAdd2(vo);
		}
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		return resultVO;
    }
	
	@RequestMapping(value="/mobileChartEmrAttach", method=RequestMethod.GET)
    public EmrAttachVO selectPenchartAttach(EmrAttachParamVO vo) throws Exception {
		String base72 = systemOption.selectBase72();	//0: 암호화 사용함, 1: 암호화 사용안함, 2: db사용
		
		EmrAttachVO resultVO = new EmrAttachVO();
		EmrAttachTempVO attach1VO = null;
		
		if(base72.equals("2")) {
			//db 사용
			attach1VO = dbOldEmrMapper.selectAttach1(vo);
		} else {
			attach1VO = oldEmrMapper.selectAttach1(vo);
		}
		
		String inout = attach1VO.getOec_inout();
		String sid = attach1VO.getOec_sid();
		String cid = attach1VO.getOec_cid();
		String seq = attach1VO.getSeq();
		
		vo.setSeq(seq);
		vo.setInout(inout);
		vo.setSid(sid);
		vo.setCid(cid);
		
		if(base72.equals("2")) {
			//db 사용
			dbOldEmrMapper.insertAttach2(vo);
			
			attach1VO = dbOldEmrMapper.selectAttach2(vo);
		} else {
			oldEmrMapper.insertAttach2(vo);
			
			attach1VO = oldEmrMapper.selectAttach2(vo);
		}
		
		String strDate = attach1VO.getStrDate();
		String strTime = attach1VO.getStrTime();
		
		List<EmrAttachDataVO> dataList = null;
		
		if(base72.equals("2")) {
			//db 사용
			dataList = dbOldEmrMapper.selectAttach3(vo);
		} else {
			dataList = oldEmrMapper.selectAttach3(vo);
		}
		
		for (EmrAttachDataVO data : dataList) {
			data.setKey(seq);
			data.setDate(strDate);
			data.setTime(strTime);
		}
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		return resultVO;
    }
	
	@RequestMapping(value="/mobileChartEmrCopy", method=RequestMethod.GET)
    public EmrCopyVO selectPenchartCopy(EmrAttachParamVO vo) throws Exception {
		String base72 = systemOption.selectBase72();	//0: 암호화 사용함, 1: 암호화 사용안함, 2: db사용
		
		EmrCopyVO resultVO = new EmrCopyVO();
		EmrAttachTempVO attach1VO = null;
		
		if(base72.equals("2")) {
			//db 사용
			attach1VO = dbOldEmrMapper.selectAttach1(vo);
		} else {
			attach1VO = oldEmrMapper.selectAttach1(vo);
		}
		
		String inout = attach1VO.getOec_inout();
		String sid = attach1VO.getOec_sid();
		String cid = attach1VO.getOec_cid();
		String seq = attach1VO.getSeq();
		
		vo.setSeq(seq);
		vo.setInout(inout);
		vo.setSid(sid);
		vo.setCid(cid);		
		
		if(base72.equals("2")) {
			//db 사용
			dbOldEmrMapper.insertAttach2(vo);
			
			attach1VO = dbOldEmrMapper.selectAttach2(vo);
		} else {
			oldEmrMapper.insertAttach2(vo);
			
			attach1VO = oldEmrMapper.selectAttach2(vo);
		}
		
		String strDate = attach1VO.getStrDate();
		String strTime = attach1VO.getStrTime();
		
		List<EmrCopyDataVo> dataList = new ArrayList<EmrCopyDataVo>();

		EmrCopyDataVo data = new EmrCopyDataVo();
		
		data.setPtno(vo.getPtno());
		data.setSeq(seq);
		data.setDate(strDate);
		data.setTime(strTime);
		
		dataList.add(data);
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		return resultVO;
    }
	
	@RequestMapping(value="/mobileChartEmrTime", method=RequestMethod.PUT)
    public CommonVO selectPenchartDate(EmrTimeParamVO vo) throws Exception {
		String base72 = systemOption.selectBase72();	//0: 암호화 사용함, 1: 암호화 사용안함, 2: db사용
		
		CommonVO resultVO = new CommonVO();
		
		if(base72.equals("2")) {
			//db 사용
			dbOldEmrMapper.updateTime(vo);
		} else {
			oldEmrMapper.updateTime(vo);
		}
		
		return resultVO;
    }
	
	
	
	@RequestMapping(value="/mobileChartEmrDate", method=RequestMethod.PUT)
    public CommonVO selectPenchartDate(EmrDateParamVO vo) throws Exception {
		String base72 = systemOption.selectBase72();	//0: 암호화 사용함, 1: 암호화 사용안함, 2: db사용
		
		CommonVO resultVO = new CommonVO();
		
		if(base72.equals("2")) {
			dbOldEmrMapper.updateDate(vo);
		} else {
			EmrDateVO dateVO = oldEmrMapper.selectDate(vo);
			
			String directory = dateVO.getDirectory();
			String fileName = dateVO.getOec_file().replaceAll(".emr", ".cht");
			
			Ini dasom2006ini = Dasom2006INI.getDasom2006Ini();
			
			String sEMRDrv = dasom2006ini.get("EMRChart", "Drive").replaceAll("=/=", "\\\\");
			String sEMRPath = dasom2006ini.get("EMRChart", "ChartPath").replaceAll("=/=", "\\\\");
			
			File folder = new File(sEMRDrv+sEMRPath+vo.getPtno()+"\\\\"+vo.getYear());
			if(!folder.exists()) {
				try {
					folder.mkdir();
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			
			Path nowPath = Paths.get(sEMRDrv+sEMRPath+vo.getPtno()+"\\\\"+directory+"\\\\"+fileName);
			Path movePath = Paths.get(sEMRDrv+sEMRPath+vo.getPtno()+"\\\\"+vo.getYear()+"\\\\"+fileName);
			
			Files.move(nowPath, movePath);
			
			oldEmrMapper.updateDate(vo);
		}
		
		return resultVO;
    }
	
	@RequestMapping(value= {"/mobileChartEmrAuth", "/emrSignpenChartAuth"}, method=RequestMethod.GET)
    public EmrAuthVO selectPenchartAuth(EmrAuthParamVO vo) throws Exception {
		String base72 = systemOption.selectBase72();	//0: 암호화 사용함, 1: 암호화 사용안함, 2: db사용
		
		EmrAuthVO resultVO = new EmrAuthVO();
		
		List<EmrAuthDataVO> dataList = null;
		
		if(base72.equals("2")) {
			dataList = dbOldEmrMapper.selectAuth(vo);
		} else {
			dataList = oldEmrMapper.selectAuth(vo);
		}

		resultVO.setTotalCount(Integer.toString(dataList.size()));
		
		if(dataList.size() == 0) {
			EmrAuthDataVO authDataVO = new EmrAuthDataVO();
			
			authDataVO.setOec_seq(vo.getSeq());
			authDataVO.setXer_view("0");
			authDataVO.setXer_print("0");
			authDataVO.setXer_edit("0");
			
			dataList.add(authDataVO);
		}
		resultVO.setData(dataList);
		
		return resultVO;
    }
}
