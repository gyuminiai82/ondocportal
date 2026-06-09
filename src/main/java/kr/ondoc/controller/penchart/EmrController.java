package kr.ondoc.controller.penchart;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Stack;

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

import kr.ondoc.domain.sybase.common.CommonVO;
import kr.ondoc.domain.sybase.legacy.penchart.EmrAttachDataVO;
import kr.ondoc.domain.sybase.penchart.EmrAddParamVO;
import kr.ondoc.domain.sybase.penchart.EmrAddVO;
import kr.ondoc.domain.sybase.penchart.EmrAttachCopyParamVO;
import kr.ondoc.domain.sybase.penchart.EmrAttachCopyTempVO;
import kr.ondoc.domain.sybase.penchart.EmrAttachVO;
import kr.ondoc.domain.sybase.penchart.EmrAuthVO;
import kr.ondoc.domain.sybase.penchart.EmrCopyVO;
import kr.ondoc.domain.sybase.penchart.EmrDateParamVO;
import kr.ondoc.domain.sybase.penchart.EmrDateVO;
import kr.ondoc.domain.sybase.penchart.EmrDeleteParamVO;
import kr.ondoc.domain.sybase.penchart.EmrLockClearVO;
import kr.ondoc.domain.sybase.penchart.EmrNewPageVO;
import kr.ondoc.domain.sybase.penchart.EmrOpenParamVO;
import kr.ondoc.domain.sybase.penchart.EmrOpenVO;
import kr.ondoc.domain.sybase.penchart.EmrParamVO;
import kr.ondoc.domain.sybase.penchart.EmrReplaceParamVO;
import kr.ondoc.domain.sybase.penchart.EmrReplaceVO;
import kr.ondoc.domain.sybase.penchart.EmrSavedVO;
import kr.ondoc.domain.sybase.penchart.EmrSheetVO;
import kr.ondoc.domain.sybase.penchart.EmrTempVO;
import kr.ondoc.domain.sybase.penchart.EmrTimeParamVO;
import kr.ondoc.domain.sybase.penchart.FormVO;
import kr.ondoc.domain.sybase.penchart.TemplateBoilerplateCateVO;
import kr.ondoc.domain.sybase.penchart.TemplateBoilerplateDetailVO;
import kr.ondoc.domain.sybase.penchart.TemplateTreatementCateVO;
import kr.ondoc.domain.sybase.penchart.TemplateTreatementDetailVO;
import kr.ondoc.domain.sybase.penchart.TemplateVO;
import kr.ondoc.mapper.sybase.common.OptionInfoMapper;
import kr.ondoc.mapper.sybase.penchart.EmrMapper;
import kr.ondoc.mapper.sybaseemr.penchart.DbEmrMapper;
import kr.ondoc.util.AES256;
import kr.ondoc.util.Dasom2006INI;
import kr.ondoc.util.EmrSheet;

@CrossOrigin(origins = "*")
@RestController
public class EmrController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private EmrMapper emrMapper;
	
	@Autowired
	private DbEmrMapper dbEmrMapper;
	
	@Autowired
	private OptionInfoMapper optionInfoMapper;
	
	private Stack<String> arrExPageKey = new Stack<String>();
	private int num = 0;
	
	@RequestMapping(value="/penchartNewEmr", method=RequestMethod.GET)
    public List<EmrNewPageVO> selectPenchartNewEmr(EmrNewPageVO vo) throws Exception {
		String base72 = optionInfoMapper.selectBase72();	//0: 암호화 사용함, 1: 암호화 사용안함, 2: db사용
		
		List<EmrNewPageVO> listResultVO = new ArrayList<EmrNewPageVO>();
		
		String[] arrBesIds = vo.getBes_ids().split(",");
		
		for (String bes_id : arrBesIds) {
			vo.setBes_id(bes_id);
			
			String kind = null;
			
			if(base72.equals("2")) {
				kind = dbEmrMapper.selectKind(bes_id).trim();
			} else {
				kind = emrMapper.selectKind(bes_id).trim();
			}
						
			if(!(kind.equals("80") || kind.equals("99"))) {
				kind = "";
			} else {
				kind = "80, 99";
			}
			
			vo.setKind(kind);
			
			EmrTempVO emrTempVO = null;
			EmrNewPageVO resultVO = null;
			
			if(base72.equals("2")) {
				//emrchart에 insert
				dbEmrMapper.insertNewEmr(vo);
				
				//todo emrchart에 insert한 값 select
				emrTempVO = dbEmrMapper.selectTempEmr(vo);
				
				//Page(bef_form) 가져오기
				resultVO = dbEmrMapper.selectPage(vo);
			} else {
				//emrchart에 insert
				emrMapper.insertNewEmr(vo);
				
				//todo emrchart에 insert한 값 select
				emrTempVO = emrMapper.selectTempEmr(vo);
				
				//Page(bef_form) 가져오기
				resultVO = emrMapper.selectPage(vo);
			}
			
			JSONObject json = new JSONObject();
			//JSONArray arrPanelJson = new JSONArray();
			//JSONArray arrItemJson = new JSONArray();
			
			String tpBefNo = resultVO.getBef_no();
			String tpBesName = resultVO.getBes_name();
			String tpKey = emrTempVO.getOec_seq();
			String tpDate = emrTempVO.getDate();
			String tpTime = emrTempVO.getTime();
			
			resultVO.setKey(tpKey);
			resultVO.setDate(tpDate);
			resultVO.setTime(tpTime);
			
			Stack<String> arrForm = new Stack<String>();
			
			String[] arrPageBarPanel = resultVO.getBef_form().split("\\|\\^@4@\\^\\|");
			
			for(int i=0; i<arrPageBarPanel.length; i++) {
				String[] arrPagePanel = arrPageBarPanel[i].split("\\|\\^@3@\\^\\|");
				if(arrPagePanel[0].equals("PANEL_NAMEVALUE")) {
					String[] arrKeyValue = arrPageBarPanel[i].split("\\|\\^@2@\\^\\|");
					for(int j=0; j<arrKeyValue.length; j++) {
						String[] arrKey = arrKeyValue[j].split("\\|\\^@1@\\^\\|");
						
						if(arrKey[0].equals("ExPageKey")) {
							String query = null;
							
							List<FormVO> formList = null;
							
							if(base72.equals("2")) {
								query = dbEmrMapper.selectPanelQuery(arrKey[1]);
								
								formList = dbEmrMapper.selectForm(query);
							} else {
								query = emrMapper.selectPanelQuery(arrKey[1]);
								
								formList = emrMapper.selectForm(query);
							}
							
							StringBuilder sbForm = new StringBuilder();
							for (FormVO form : formList) {
								sbForm.append(form.getForm().replaceAll("\"", "doublequotationmarks").replaceAll("\'", "smallquotation"));
					        }
							
							arrForm.push(sbForm.toString().trim());
						}
					}
				}
			}
			
			String[] form = new String[arrForm.size()];
			for(int i=0; i<arrForm.size(); i++) {
				form[i] = arrForm.get(i);
			}
			
			resultVO.setForm(form);
			
			EmrAuthVO emrAuthVO = null;
			
			if(base72.equals("2")) {
				emrAuthVO = dbEmrMapper.selectAuth(tpKey, vo.getPtno(), vo.getId());
			} else {
				emrAuthVO = emrMapper.selectAuth(tpKey, vo.getPtno(), vo.getId());
			}
			
			resultVO.setXer_view(emrAuthVO.getXer_view());
			resultVO.setXer_edit(emrAuthVO.getXer_edit());
			resultVO.setXer_print(emrAuthVO.getXer_print());
			
			listResultVO.add(resultVO);
		}
		
		return listResultVO;
    }
	
	@RequestMapping(value="/penchartSavedEmr", method=RequestMethod.GET)
    public List<EmrSavedVO> selectPenchartSavedEmr(EmrSavedVO vo) throws Exception {
		String base72 = optionInfoMapper.selectBase72();	//0: 암호화 사용함, 1: 암호화 사용안함, 2: db사용
		
		List<EmrSavedVO> listResultVO = new ArrayList<EmrSavedVO>();
		
		String[] arrSeq = vo.getOec_seqs().split(",");
		
		for (String seq : arrSeq) {
			vo.setOec_seq(seq);
			
			EmrSavedVO resultVO = null;
			
			if(base72.equals("2")) {
				resultVO = dbEmrMapper.selectSavedEmr(vo);
				resultVO.setSheet("");
			} else {
				resultVO = emrMapper.selectSavedEmr(vo);
			}
			
			//한번에 처리시 서식데이터 로딩에 시간이 오래 걸려 주석처리
			/*
			EmrSheet emrSheet = new EmrSheet();
			
			//락이 걸려 있는지 확인하기 위해 SELECT
			EmrSavedVO resultVO = emrMapper.selectLockCheck(vo);
			
			//락이 걸려 있을경우 같은 기기이면 락을 해제 OR 락을 건지 12시간이 지난 경우이면 락을 해제
			if(resultVO.getOec_lockyn().equals("1")) {
				if(resultVO.getOec_loichost().equals(vo.getUuid()) || Integer.parseInt(resultVO.getLock_hour()) > 12) {
					emrMapper.updateSavedEmr(vo);
				}
			}
			
			resultVO = emrMapper.selectSavedEmr(vo);
			
			try {
				resultVO.setSheet(emrSheet.sheetRead(resultVO.getOec_ptno(), resultVO.getYear(), resultVO.getOec_file()));
				
			} catch (Exception e) {
				throw new Exception("NOT_FOUND");
			}

			//잠금기능
			emrMapper.updateEmrLock(vo);
			
			EmrAuthVO emrAuthVO = emrMapper.selectAuth(vo.getOec_seq(), vo.getOec_ptno());
			
			resultVO.setId(vo.getId());
			resultVO.setUuid(vo.getUuid());
			resultVO.setXur_name(emrAuthVO.getXur_name());
			resultVO.setOec_lockid(emrAuthVO.getOec_lockid());
			resultVO.setOec_lockdt(emrAuthVO.getOec_lockdt());
			resultVO.setOec_loichost(emrAuthVO.getOec_loichost());
			resultVO.setXer_view(emrAuthVO.getXer_view());
			resultVO.setXer_edit(emrAuthVO.getXer_edit());
			resultVO.setXer_print(emrAuthVO.getXer_print());
			*/
			
			listResultVO.add(resultVO);
		}
		
		return listResultVO;
    }
	
	@RequestMapping(value="/penchartSavedEmrSheet", method=RequestMethod.GET)
    public EmrSavedVO selectPenchartSavedEmrSheet(EmrSavedVO vo) throws Exception {
		String base72 = optionInfoMapper.selectBase72();	//0: 암호화 사용함, 1: 암호화 사용안함, 2: db사용
		
		EmrSheet emrSheet = new EmrSheet();
		
		//락이 걸려 있는지 확인하기 위해 SELECT
		EmrSavedVO resultVO = null;
		
		if(base72.equals("2")) {
			resultVO = dbEmrMapper.selectLockCheck(vo);
		} else {
			resultVO = emrMapper.selectLockCheck(vo);
		}
		
		//락이 걸려 있을경우 같은 기기이면 락을 해제 OR 락을 건지 12시간이 지난 경우이면 락을 해제
		if(resultVO.getOec_lockyn().equals("1")) {
			if(resultVO.getOec_loichost().equals(vo.getUuid()) || Integer.parseInt(resultVO.getLock_hour()) > 12) {
				if(base72.equals("2")) {
					dbEmrMapper.updateSavedEmr(vo);
				} else {
					emrMapper.updateSavedEmr(vo);
				}
			}
		}
		
		if(base72.equals("2")) {
			resultVO = dbEmrMapper.selectSavedEmr(vo);
		} else {
			resultVO = emrMapper.selectSavedEmr(vo);
			
			if(!base72.equals("1")) {
				try {
					resultVO.setSheet(emrSheet.sheetRead(resultVO.getOec_ptno(), resultVO.getYear(), resultVO.getOec_file()));
					
				} catch (Exception e) {
					throw new Exception("NOT_FOUND");
				}
			} else {
				Ini dasom2006ini = Dasom2006INI.getDasom2006Ini();
				
				String sEMRDrv = dasom2006ini.get("EMRChart", "Drive").replaceAll("=/=", "\\\\");
				String sEMRPath = dasom2006ini.get("EMRChart", "ChartPath").replaceAll("=/=", "\\\\");
				
				String path = sEMRDrv + sEMRPath + resultVO.getOec_ptno() + '\\' + resultVO.getYear() + '\\' + resultVO.getOec_file();
				
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
			        
			        resultVO.setSheet(stringBuilder.toString());
				} catch (Exception e) {
					
					resultVO.setSheet(emrSheet.sheetReadNotDelete(resultVO.getOec_ptno(), resultVO.getYear(), resultVO.getOec_file()));
				}
			}
		}
		
		//잠금기능
		if(base72.equals("2")) {
			dbEmrMapper.updateEmrLock(vo);
		} else {
			emrMapper.updateEmrLock(vo);
		}
		
		
		EmrAuthVO emrAuthVO = null;
		
		if(base72.equals("2")) {
			emrAuthVO = dbEmrMapper.selectAuth(vo.getOec_seq(), vo.getOec_ptno(), vo.getId());
		} else {
			emrAuthVO = emrMapper.selectAuth(vo.getOec_seq(), vo.getOec_ptno(), vo.getId());
		}
		
		resultVO.setId(vo.getId());
		resultVO.setUuid(vo.getUuid());
		resultVO.setXur_name(emrAuthVO.getXur_name());
		resultVO.setOec_lockid(emrAuthVO.getOec_lockid());
		resultVO.setOec_lockdt(emrAuthVO.getOec_lockdt());
		resultVO.setOec_loichost(emrAuthVO.getOec_loichost());
		resultVO.setXer_view(emrAuthVO.getXer_view());
		resultVO.setXer_edit(emrAuthVO.getXer_edit());
		resultVO.setXer_print(emrAuthVO.getXer_print());
		
		return resultVO;
    }
	
	@RequestMapping(value="/penchartSave", method=RequestMethod.POST)
	public CommonVO penchartSave(EmrParamVO vo) throws Exception {
		String base72 = optionInfoMapper.selectBase72();	//0: 암호화 사용함, 1: 암호화 사용안함, 2: db사용
		
		CommonVO resultVO = new CommonVO();
		
		if(base72.equals("2")) {
			JSONArray jsonArray = new JSONArray(vo.getEmr());

			jsonArray.forEach(item -> {
				JSONObject jsonObject = (JSONObject) item;
				
				String type = jsonObject.getString("type");
				String key = jsonObject.getString("key");
				String title = jsonObject.getString("title");
				String date = jsonObject.getString("date");
				String time = jsonObject.getString("time");
				String state = jsonObject.getString("state");
				String sheet = jsonObject.getString("sheet").replaceAll("·", "・");
				
				String fileName = vo.getPtno() + "_" + key + "_" + title + ".emr";
				
				EmrSheetVO emrSheetVO = new EmrSheetVO();
				
				emrSheetVO.setId(vo.getId());
				emrSheetVO.setPtno(vo.getPtno());
				emrSheetVO.setKey(key);
				emrSheetVO.setFile_name(fileName);
				emrSheetVO.setState(state);
				emrSheetVO.setDate(date);
				emrSheetVO.setTime(time);
				emrSheetVO.setSheet(sheet);
				
				try {
					dbEmrMapper.updateEmr(emrSheetVO);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		} else {
			Ini dasom2006ini = Dasom2006INI.getDasom2006Ini();
			
			String sEMRDrv = dasom2006ini.get("EMRChart", "Drive").replaceAll("=/=", "\\\\");
			String sEMRPath = dasom2006ini.get("EMRChart", "ChartPath").replaceAll("=/=", "\\\\");
			
			JSONArray jsonArray = new JSONArray(vo.getEmr());

			jsonArray.forEach(item -> {
				JSONObject jsonObject = (JSONObject) item;
				
				String type = jsonObject.getString("type");
				String key = jsonObject.getString("key");
				String title = jsonObject.getString("title");
				String date = jsonObject.getString("date");
				String time = jsonObject.getString("time");
				String state = jsonObject.getString("state");
				String sheet = jsonObject.getString("sheet").replaceAll("・", "·");
				
				String fileName = vo.getPtno() + "_" + key + "_" + title + ".emr";
				
				if(!base72.equals("1")) {
					String path = sEMRDrv + sEMRPath + fileName;
								
					File file = new File(path);
					
			        // 2. 파일 존재여부 체크 및 생성
			        if (!file.exists()) {
			            try {
							file.createNewFile();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							resultVO.setStatus("500");
							e.printStackTrace();
						}
			        }
			        
			        try {
						FileOutputStream fos = new FileOutputStream(file);
						fos.write(sheet.getBytes("EUC-KR"));
				        
				        fos.close();
				        
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			        
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
					
					try {
			            // 2. 파일 존재여부 체크 및 생성
			            if (!file.exists()) {
			                file.createNewFile();
			            }
			            
			            FileOutputStream fos = new FileOutputStream(file);
			            
			            fos.write(sheet.getBytes("EUC-KR"));
			            
			            fos.close();
					} catch (Exception e) {
						// TODO: handle exception
						logger.warn(e.getMessage());
					}
				}
				
				EmrSheetVO emrSheetVO = new EmrSheetVO();
				
				emrSheetVO.setId(vo.getId());
				emrSheetVO.setPtno(vo.getPtno());
				emrSheetVO.setKey(key);
				emrSheetVO.setFile_name(fileName);
				emrSheetVO.setState(state);
				emrSheetVO.setDate(date);
				emrSheetVO.setTime(time);
				
				try {
					emrMapper.updateEmr(emrSheetVO);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		}
		
		if(resultVO.getStatus().equals("500")) {
			throw new Exception("EMR 폴더가 존재하지 않습니다.");
		}
		
		return resultVO;
	}
	
	//POST로 하면 약  1.8메가의 데이터값에서 에러가 발생하여 PUT으로 했더니 에러 없이 정상작동함
	@RequestMapping(value="/penchartSave", method=RequestMethod.PUT)
	public CommonVO penchartSaveUpdate(EmrParamVO vo) throws Exception {
		String base72 = optionInfoMapper.selectBase72();	//0: 암호화 사용함, 1: 암호화 사용안함, 2: db사용
		
		CommonVO resultVO = new CommonVO();
		
		if(base72.equals("2")) {
			JSONArray jsonArray = new JSONArray(vo.getEmr());

			jsonArray.forEach((item) -> {
				JSONObject jsonObject = (JSONObject) item;
				
				String type = jsonObject.getString("type");
				String key = jsonObject.getString("key");
				String title = jsonObject.getString("title");
				String date = jsonObject.getString("date");
				String time = jsonObject.getString("time");
				String state = jsonObject.getString("state");
				String sheet = jsonObject.getString("sheet").replaceAll("·", "・");
				
				String fileName = vo.getPtno() + "_" + key + "_" + title + ".emr";
				
				EmrSheetVO emrSheetVO = new EmrSheetVO();
				
				emrSheetVO.setId(vo.getId());
				emrSheetVO.setPtno(vo.getPtno());
				emrSheetVO.setKey(key);
				emrSheetVO.setFile_name(fileName);
				emrSheetVO.setState(state);
				emrSheetVO.setDate(date);
				emrSheetVO.setTime(time);
				emrSheetVO.setSheet(sheet);
				
				try {
					dbEmrMapper.updateEmr(emrSheetVO);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		} else {
			Ini dasom2006ini = Dasom2006INI.getDasom2006Ini();
			
			String sEMRDrv = dasom2006ini.get("EMRChart", "Drive").replaceAll("=/=", "\\\\");
			String sEMRPath = dasom2006ini.get("EMRChart", "ChartPath").replaceAll("=/=", "\\\\");
			
			JSONArray jsonArray = new JSONArray(vo.getEmr());

			jsonArray.forEach(item -> {
				JSONObject jsonObject = (JSONObject) item;
				
				String type = jsonObject.getString("type");
				String key = jsonObject.getString("key");
				String title = jsonObject.getString("title");
				String date = jsonObject.getString("date");
				String time = jsonObject.getString("time");
				String state = jsonObject.getString("state");
				String sheet = jsonObject.getString("sheet").replaceAll("・", "·");
				
				String fileName = vo.getPtno() + "_" + key + "_" + title + ".emr";
				
				if(!base72.equals("1")) {
					String path = sEMRDrv + sEMRPath + fileName;
								
					File file = new File(path);
					
			        // 2. 파일 존재여부 체크 및 생성
			        if (!file.exists()) {
			            try {
							file.createNewFile();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			        }
					
			        try {
						FileOutputStream fos = new FileOutputStream(file);
						fos.write(sheet.getBytes("EUC-KR"));
				        
				        fos.close();
				        
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			        
			        //Date nowDate = new Date();
			        String strDate = date;
					
			        SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd");
			        SimpleDateFormat format = new SimpleDateFormat("yyyy");
			        
			        Date formatDate = null;
			        
			        try {
						formatDate = dtFormat.parse(strDate);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			        
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
					String strDate = date;
					
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
					
					try {
			            // 2. 파일 존재여부 체크 및 생성
			            if (!file.exists()) {
			                file.createNewFile();
			            }
			            
			            FileOutputStream fos = new FileOutputStream(file);
			            
			            fos.write(sheet.getBytes("EUC-KR"));
			            
			            fos.close();
					} catch (Exception e) {
						// TODO: handle exception
						logger.warn(e.getMessage());
					}
				}
				
				EmrSheetVO emrSheetVO = new EmrSheetVO();
				
				emrSheetVO.setId(vo.getId());
				emrSheetVO.setPtno(vo.getPtno());
				emrSheetVO.setKey(key);
				emrSheetVO.setFile_name(fileName);
				emrSheetVO.setState(state);
				emrSheetVO.setDate(date);
				emrSheetVO.setTime(time);
				
				try {
					emrMapper.updateEmr(emrSheetVO);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		}
		
		return resultVO;
	}

	@RequestMapping(value="/penchartReplace", method=RequestMethod.GET)
    public String penchartReplace(EmrReplaceParamVO vo) throws Exception {
		JSONArray jsonArray = new JSONArray(vo.getReplace());
		JSONArray arrJson = new JSONArray();
		
		jsonArray.forEach(item -> {
			JSONObject jsonObject = (JSONObject) item;
			
			String pageKey = jsonObject.getString("pageKey");
			String panelKey = jsonObject.getString("panelKey");
			JSONArray datas = jsonObject.getJSONArray("data");
			
			datas.forEach(data -> {
				String[] arrData = data.toString().split("\\^");
				
				String dataName = arrData[0];
				
				for (int i=1; i<arrData.length; i++) {					
					if(arrData[i].indexOf(":") > 0) {	
						String[] arrItem = arrData[i].split(":");
						
						try {
							String[] arrField = arrItem[1].split(",");
							for (int j=0; j<arrField.length; j++) {
								JSONObject obj = new JSONObject();
								obj.put("pageKey", pageKey);
								obj.put("panelKey", panelKey);
								obj.put("itemKey", arrField[j]);
								
								if(dataName.equals("BASIC")) {
									if(arrItem[0].equals("DATE")) {
										Date nowDate = new Date();
										
										SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
										
										obj.put("itemValue", format.format(nowDate));
									} else if(arrItem[0].equals("TIME")) {
										Date nowDate = new Date();
										
										SimpleDateFormat format = new SimpleDateFormat("HH:mm");
										
										obj.put("itemValue", format.format(nowDate));
									} else if(arrItem[0].equals("UNM")) {
										obj.put("itemValue", vo.getName());
									} else if(arrItem[0].equals("INCHARGE")) {
										try {
											obj.put("itemValue", emrMapper.selectReplaceIncharge(vo.getRegno()));
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
								} else if(dataName.equals("PATIENT")) {
									try {
										EmrReplaceVO erVo = emrMapper.selectReplacePatient(vo.getPtno());
										
										if(arrItem[0].equals("bpt_addr")) {
											obj.put("itemValue", erVo.getBpt_addr().trim());
										} else if(arrItem[0].equals("bpt_sex")) {
											if(erVo.getBpt_sex().trim().equals("MAN")) {
												obj.put("itemValue", "남자");
											} else {
												obj.put("itemValue", "여자");
											}
										} else if(arrItem[0].equals("bpt_resno")) {
											obj.put("itemValue", erVo.getBpt_resno().substring(0, 6) + "-" + erVo.getBpt_resno().substring(6, 7) + "" + (new AES256()).decrypt(erVo.getBpt_resno3()));
										} else if(arrItem[0].equals("bpt_ptno")) {
											obj.put("itemValue", erVo.getBpt_ptno());
										} else if(arrItem[0].equals("bpt_name")) {
											obj.put("itemValue", erVo.getBpt_name());
										} else if(arrItem[0].equals("bpt_pname")) {
											obj.put("itemValue", erVo.getBpt_pname());
										} else if(arrItem[0].equals("bpt_email")) {
											obj.put("itemValue", erVo.getBpt_email());
										} else if(arrItem[0].equals("bpt_yage")) {
											obj.put("itemValue", erVo.getBpt_yage());
										} else if(arrItem[0].equals("bpt_telno")) {
											obj.put("itemValue", erVo.getBpt_telno());
										} else if(arrItem[0].equals("bpt_hpno")) {
											obj.put("itemValue", erVo.getBpt_hpno());
										} else if(arrItem[0].equals("bpt_birth")) {
											obj.put("itemValue", erVo.getBpt_birth());
										}
									} catch (Exception e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								} else if(dataName.equals("IPST")) {
									try {								
										obj.put("itemValue", emrMapper.selectReplaceIpst(arrItem[0], vo.getPtno()));
									} catch (Exception e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								} else if(dataName.equals("IPWON")) {
									try {								
										obj.put("itemValue", emrMapper.selectReplaceIpwon(arrItem[0], vo.getPtno()));
									} catch (Exception e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								} else if(dataName.equals("QUALIFY")) {
									try {	
										if(arrItem[0].equals("rqu_tgso")) {
											EmrReplaceVO erVo = emrMapper.selectReplaceQualify2(vo.getPtno());
											
											obj.put("itemValue", erVo.getRqu_tgso().substring(0, 6) + "-" + erVo.getRqu_tgso().substring(6, 7) + "" + (new AES256()).decrypt(erVo.getRqu_tgso3()));
										} else {
											obj.put("itemValue", emrMapper.selectReplaceQualify(arrItem[0], vo.getPtno()));
										}
									} catch (Exception e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								} else if(dataName.equals("DOCTOR")) {
									try {	
										obj.put("itemValue", emrMapper.selectReplaceDoctor(arrItem[0], vo.getId()));
										
									} catch (Exception e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								} else if(dataName.equals("HOSINFO")) {
									Date nowDate = new Date();
									
									SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
									
									try {								
										obj.put("itemValue", emrMapper.selectReplaceHosinfo(arrItem[0], vo.getId(), format.format(nowDate)));
									} catch (Exception e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								} else if(dataName.equals("SIGN")) {
									try {					
										if(arrItem[0].equals("UNM")) {
											obj.put("itemValue", "SIGN_"+vo.getName());
										} else if(arrItem[0].equals("HOSNM")) {
											Date nowDate = new Date();
											
											SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
											
											try {								
												obj.put("itemValue", "SIGN_"+emrMapper.selectReplaceHosinfo("bhi_hos03", vo.getId(), format.format(nowDate)));
											} catch (Exception e1) {
												// TODO Auto-generated catch block
												e1.printStackTrace();
											}
										}
									} catch (Exception e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								}
								
								arrJson.put(obj);
							}
						
						} catch (Exception e) {
							// TODO: handle exception
						}
					}
				}
			});
		});
		
		return arrJson.toString();
	}
	
	private static String[] Add(String[] originArray, String Val) {
		// 순서 1. (원본 배열의 크기 + 1)를 크기를 가지는 배열을 생성
		String[] newArray = new String[originArray.length + 1];
		
		// 순서 2. arraycopy() 메서드를 사용하여 복사
		System.arraycopy(originArray, 0, newArray, 0, originArray.length);
		
		// 순서 3. 새로운 배열의 마지막 위치에 추가하려는 값을 할당
		newArray[originArray.length] = Val;
		
		// 순서 4. 새로운 배열을 반환
		return newArray;
	}
	
	//진료용어, 상용구
	@RequestMapping(value= "/penchartTemplate", method=RequestMethod.GET)
	public TemplateVO selectTemplate() throws Exception {
		String base72 = optionInfoMapper.selectBase72();	//0: 암호화 사용함, 1: 암호화 사용안함, 2: db사용
		
		TemplateVO data = new TemplateVO();

		if(base72.equals("2")) {
			data.setTreatementCate(dbEmrMapper.selectTemplateTreatementCate());
			data.setTreatementDetail(dbEmrMapper.selectTemplateTreatementDetail(""));
			data.setBoilerplateCate(dbEmrMapper.selectTemplateBoilerplateCate());
			data.setBoilerplateDetail(dbEmrMapper.selectTemplateBoilerplateDetail(""));
		} else {
			data.setTreatementCate(emrMapper.selectTemplateTreatementCate());
			data.setTreatementDetail(emrMapper.selectTemplateTreatementDetail(""));
			data.setBoilerplateCate(emrMapper.selectTemplateBoilerplateCate());
			data.setBoilerplateDetail(emrMapper.selectTemplateBoilerplateDetail(""));
		}
		
		return data;
    }
	
	@RequestMapping(value= "/penchartTemplateTreatementCate", method=RequestMethod.GET)
	public List<TemplateTreatementCateVO> selectTemplateTreatementCate() throws Exception {
		String base72 = optionInfoMapper.selectBase72();	//0: 암호화 사용함, 1: 암호화 사용안함, 2: db사용
		
		if(base72.equals("2")) {
			return dbEmrMapper.selectTemplateTreatementCate();
		} else {
			return emrMapper.selectTemplateTreatementCate();
		}
    }
	
	@RequestMapping(value= "/penchartTemplateTreatementDetail", method=RequestMethod.GET)
	public List<TemplateTreatementDetailVO> selectTemplateTreatementDetail(String kcode) throws Exception {
		String base72 = optionInfoMapper.selectBase72();	//0: 암호화 사용함, 1: 암호화 사용안함, 2: db사용
		
		if(base72.equals("2")) {
			return dbEmrMapper.selectTemplateTreatementDetail(kcode);
		} else {
			return emrMapper.selectTemplateTreatementDetail(kcode);
		}
    }
	
	@RequestMapping(value= "/penchartTemplateBoilerplateCate", method=RequestMethod.GET)
	public List<TemplateBoilerplateCateVO> selectTemplateBoilerplateCate() throws Exception {
		String base72 = optionInfoMapper.selectBase72();	//0: 암호화 사용함, 1: 암호화 사용안함, 2: db사용
		
		if(base72.equals("2")) {
			return dbEmrMapper.selectTemplateBoilerplateCate();
		} else {
			return emrMapper.selectTemplateBoilerplateCate();
		}
    }
	
	@RequestMapping(value= "/penchartTemplateBoilerplateDetail", method=RequestMethod.GET)
	public List<TemplateBoilerplateDetailVO> selectTemplateBoilerplateDetail(String kcode) throws Exception {
		String base72 = optionInfoMapper.selectBase72();	//0: 암호화 사용함, 1: 암호화 사용안함, 2: db사용
		
		if(base72.equals("2")) {
			return dbEmrMapper.selectTemplateBoilerplateDetail(kcode);
		} else {
			return emrMapper.selectTemplateBoilerplateDetail(kcode);
		}
    }
	
	@RequestMapping(value= "/penchartAutoOpen", method=RequestMethod.GET)
	public String selectOpen(EmrOpenParamVO vo) throws Exception {
		String base72 = optionInfoMapper.selectBase72();	//0: 암호화 사용함, 1: 암호화 사용안함, 2: db사용
		
		List<EmrOpenVO> dataList = null;
		
		if(base72.equals("2")) {
			dataList = dbEmrMapper.selectOpen(vo);
		} else {
			dataList = emrMapper.selectOpen(vo);
		}
		
		StringBuilder str = new StringBuilder();
		
		String id = "";
		int idx = 0; 
		
		for(int i=0; i<dataList.size(); i++) {
			EmrOpenVO emrOpenVO = dataList.get(i);
			
			if(!id.equals(emrOpenVO.getXeo_id())) {
				id = emrOpenVO.getXeo_id();
				idx = 0;
				
				str.append(","+emrOpenVO.getOec_seq());
				idx++;
			} else {

				//System.out.println(emrOpenVO.getOec_seq()+ " "+emrOpenVO.getXeo_id()+ " "+idx+ " "+emrOpenVO.getXeo_cnt());

				if(Integer.parseInt(emrOpenVO.getXeo_cnt()) == idx) continue;
				str.append(","+emrOpenVO.getOec_seq());

				idx++;
				
			}
			
		}
		
		if(str.toString().length() > 0) return str.toString().substring(1);
		else return "";
    }
	
	@RequestMapping(value= "/penchartAutoAdd", method=RequestMethod.GET)
	public String selectAdd(EmrAddParamVO vo) throws Exception {
		String base72 = optionInfoMapper.selectBase72();	//0: 암호화 사용함, 1: 암호화 사용안함, 2: db사용
		
		String kind = null;
		
		kind = emrMapper.selectAdd1(vo);

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
		
		List<EmrAddVO> dataList = null;
		
		if(base72.equals("2")) {
			dataList = dbEmrMapper.selectAdd2(vo);
		} else {
			dataList = emrMapper.selectAdd2(vo);
		}
		
		StringBuilder str = new StringBuilder();
		
		for(int i=0; i<dataList.size(); i++) {
			EmrAddVO emrAddVO = dataList.get(i);
			
			str.append(","+emrAddVO.getBem_no());			
		}
		
		if(str.toString().length() > 0) return str.toString().substring(1);
		else return "";
    }
	
	@RequestMapping(value="/penchartUpdateTime", method=RequestMethod.PUT)
    public CommonVO selectPenchartDate(EmrTimeParamVO vo) throws Exception {
		String base72 = optionInfoMapper.selectBase72();	//0: 암호화 사용함, 1: 암호화 사용안함, 2: db사용
		
		CommonVO resultVO = new CommonVO();
		
		if(base72.equals("2")) {
			dbEmrMapper.updateTime(vo);
		} else {
			emrMapper.updateTime(vo);
		}
		
		return resultVO;
    }
	
	@RequestMapping(value="/penchartUpdateDate", method=RequestMethod.PUT)
    public CommonVO selectPenchartDate(EmrDateParamVO vo) throws Exception {
		String base72 = optionInfoMapper.selectBase72();	//0: 암호화 사용함, 1: 암호화 사용안함, 2: db사용
		
		CommonVO resultVO = new CommonVO();
		if(base72.equals("2")) {
			dbEmrMapper.updateDate(vo);
		} else {
			EmrDateVO dateVO = emrMapper.selectDate(vo);
			
			vo.setWdate(vo.getDate().replaceAll("-", ""));
			vo.setYear(vo.getDate().substring(0, 4));
			
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
			
			emrMapper.updateDate(vo);
		}
		
		return resultVO;
    }
	
	@RequestMapping(value="/penchartAttachEmr", method=RequestMethod.GET)
    public EmrAttachVO penchartAttachEmr(EmrAttachCopyParamVO vo) throws Exception {
		String base72 = optionInfoMapper.selectBase72();	//0: 암호화 사용함, 1: 암호화 사용안함, 2: db사용
		
		EmrAttachCopyTempVO attach1VO = null;
		
		if(base72.equals("2")) {
			attach1VO = dbEmrMapper.selectAttach1(vo);
		} else {
			attach1VO = emrMapper.selectAttach1(vo);
		}
		
		String inout = attach1VO.getOec_inout();
		String sid = attach1VO.getOec_sid();
		String cid = attach1VO.getOec_cid();
		String seq = attach1VO.getSeq();
		
		vo.setSeq(seq);
		vo.setInout(inout);
		vo.setSid(sid);
		vo.setCid(cid);
		
		//Page(bef_form) 가져오기
		EmrNewPageVO resultVO = null;
		
		if(base72.equals("2")) {
			dbEmrMapper.insertAttach2(vo);
			
			attach1VO = dbEmrMapper.selectAttach2(vo);
			
			resultVO = dbEmrMapper.selectPageSid(sid);
		} else {
			emrMapper.insertAttach2(vo);
			
			attach1VO = emrMapper.selectAttach2(vo);
			
			resultVO = emrMapper.selectPageSid(sid);
		}
		JSONObject json = new JSONObject();
		
		Stack<String> arrForm = new Stack<String>();
		
		String[] arrPageBarPanel = resultVO.getBef_form().split("\\|\\^@4@\\^\\|");
		
		for(int i=0; i<arrPageBarPanel.length; i++) {
			String[] arrPagePanel = arrPageBarPanel[i].split("\\|\\^@3@\\^\\|");
			if(arrPagePanel[0].equals("PANEL_NAMEVALUE")) {
				String[] arrKeyValue = arrPageBarPanel[i].split("\\|\\^@2@\\^\\|");
				for(int j=0; j<arrKeyValue.length; j++) {
					String[] arrKey = arrKeyValue[j].split("\\|\\^@1@\\^\\|");
					
					if(arrKey[0].equals("ExPageKey")) {
						String query = null;
						
						List<FormVO> formList = null;
						
						if(base72.equals("2")) {
							query = dbEmrMapper.selectPanelQuery(arrKey[1]);
							
							formList = dbEmrMapper.selectForm(query);
						} else {
							query = emrMapper.selectPanelQuery(arrKey[1]);
							
							formList = emrMapper.selectForm(query);
						}
						
						StringBuilder sbForm = new StringBuilder();
						for (FormVO form : formList) {
							sbForm.append(form.getForm());
				        }
						
						arrForm.push(sbForm.toString().trim());
					}
				}
			}
		}
		
		String[] form = new String[arrForm.size()];
		for(int i=0; i<arrForm.size(); i++) {
			form[i] = arrForm.get(i);
		}
				
		String strDate = attach1VO.getStrDate();
		String strTime = attach1VO.getStrTime();
		
		EmrAttachVO emrAttachVO = null;
		
		if(base72.equals("2")) {
			emrAttachVO = dbEmrMapper.selectAttach3(vo);
		} else {
			emrAttachVO = emrMapper.selectAttach3(vo);
		}
		
		emrAttachVO.setKey(seq);
		emrAttachVO.setDate(strDate);
		emrAttachVO.setTime(strTime);
		
		emrAttachVO.setForm(form);
		
		EmrAuthVO emrAuthVO = null;
		
		if(base72.equals("2")) {
			emrAuthVO = dbEmrMapper.selectAuth(seq, vo.getPtno(), vo.getId());
		} else {
			emrAuthVO = emrMapper.selectAuth(seq, vo.getPtno(), vo.getId());
		}
		
		emrAttachVO.setXer_view(emrAuthVO.getXer_view());
		emrAttachVO.setXer_edit(emrAuthVO.getXer_edit());
		emrAttachVO.setXer_print(emrAuthVO.getXer_print());
		
		return emrAttachVO;
    }
	
	@RequestMapping(value="/penchartCopyEmr", method=RequestMethod.GET)
    public EmrCopyVO penchartCopyEmr(EmrAttachCopyParamVO vo) throws Exception {
		String base72 = optionInfoMapper.selectBase72();	//0: 암호화 사용함, 1: 암호화 사용안함, 2: db사용
		
		EmrCopyVO emrCopyVO = new EmrCopyVO();
		EmrAttachCopyTempVO attach1VO = null;
		
		if(base72.equals("2")) {
			attach1VO = dbEmrMapper.selectAttach1(vo);
		} else {
			attach1VO = emrMapper.selectAttach1(vo);
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
			dbEmrMapper.insertAttach2(vo);
			
			attach1VO = dbEmrMapper.selectAttach2(vo);
		} else {
			emrMapper.insertAttach2(vo);
			
			attach1VO = emrMapper.selectAttach2(vo);
		}
		
		String strDate = attach1VO.getStrDate();
		String strTime = attach1VO.getStrTime();
		
		emrCopyVO.setPtno(vo.getPtno());
		emrCopyVO.setSeq(seq);
		emrCopyVO.setDate(strDate);
		emrCopyVO.setTime(strTime);
		
		return emrCopyVO;
    }
	
	@RequestMapping(value="/penchartDeleteEmr", method=RequestMethod.GET)
    public CommonVO penchartDeleteEmr(EmrDeleteParamVO vo) throws Exception {
		String base72 = optionInfoMapper.selectBase72();	//0: 암호화 사용함, 1: 암호화 사용안함, 2: db사용
		
		CommonVO resultVO = new CommonVO();
		
		if(base72.equals("2")) {
			dbEmrMapper.deleteEmr(vo);
		} else {
			emrMapper.deleteEmr(vo);
		}
		
		return resultVO;
    }
	
	@RequestMapping(value="/penchartLockClear", method=RequestMethod.GET)
    public CommonVO penchartLockClear(EmrLockClearVO vo) throws Exception {
		String base72 = optionInfoMapper.selectBase72();	//0: 암호화 사용함, 1: 암호화 사용안함, 2: db사용
		
		CommonVO resultVO = new CommonVO();
		
		if(base72.equals("2")) {
			dbEmrMapper.lockClearEmr(vo);
		} else {
			emrMapper.lockClearEmr(vo);
		}

		return resultVO;
    }
	
	@RequestMapping(value="/penchartSign", method=RequestMethod.GET)
    public String penchartSign(String name) throws Exception {
		String filePath = "C:\\StNeo\\Bin\\SIGN\\"+name+".png";
		
		System.out.println(filePath);
		
		String base64String = "";

        try {
            byte[] fileArray = Files.readAllBytes(Paths.get(filePath));
            base64String = Base64.getEncoder().encodeToString(fileArray);

        } catch (IOException e) {
            e.printStackTrace();
        }
        
        //return "data:image/png;base64,"+base64String.toString();
        return base64String.toString();
    }
}