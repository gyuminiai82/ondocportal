package kr.ondoc.controller.legacy.penchart;

import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import kr.ondoc.domain.sybase.legacy.penchart.LoginDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.LoginVO;
import kr.ondoc.domain.sybase.legacy.penchart.PatientRoomSymptomVO;
import kr.ondoc.domain.sybase.legacy.penchart.SavedTreeDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.SavedTreeVO;
import kr.ondoc.domain.sybase.legacy.penchart.SheetTreeDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.SheetTreeVO;
import kr.ondoc.domain.sybase.legacy.penchart.TreeParamVO;
import kr.ondoc.mapper.sybase.legacy.OldOptionInfoMapper;
import kr.ondoc.mapper.sybase.legacy.penchart.OldLoginMapper;
import kr.ondoc.mapper.sybase.legacy.penchart.OldTreeMapper;
import kr.ondoc.mapper.sybaseemr.legacy.penchart.DbOldEmrMapper;
import kr.ondoc.mapper.sybaseemr.legacy.penchart.DbOldTreeMapper;

@CrossOrigin(origins = "*")
@RestController
public class OldTreeController {
	@Autowired
	private OldOptionInfoMapper systemOption;
	
	@Autowired
	private OldTreeMapper oldMapper;
	
	@Autowired
	private DbOldTreeMapper dbOldTreeMapper;
	
	@RequestMapping(value="/mobileChartSavedTree", method=RequestMethod.GET)
    public String selectSavedTree(TreeParamVO vo) throws Exception {
		String base72 = systemOption.selectBase72();	//0: 암호화 사용함, 1: 암호화 사용안함, 2: db사용
		
		//삽질중... 변수명이 숫자가 먼저 올수 없기에 VO 사용 불가
		SavedTreeVO resultVO = new SavedTreeVO();
		
		List<SavedTreeDataVO> dataList = null;
		
		if(base72.equals("2")) {
			//db 사용
			dataList = dbOldTreeMapper.selectSavedTree(vo);
		} else {
			dataList = oldMapper.selectSavedTree(vo);
		}
		
		
		JSONArray arrData = new JSONArray();
		for (SavedTreeDataVO data : dataList) {
			JSONObject obj = new JSONObject();
			obj.put("1depth_id", data.getDepth1_id());
			obj.put("1depth_name", data.getDepth1_name());
			obj.put("2depth_id", data.getDepth2_id());
			obj.put("2depth_name", data.getDepth2_name());
			obj.put("3depth_id", data.getDepth3_id());
			obj.put("3depth_name", data.getDepth3_name());
			obj.put("3depth_time", data.getDepth3_time());
			obj.put("oec_lockyn", data.getOec_lockyn());
			obj.put("now_time", data.getNow_time());
			obj.put("oec_state", data.getOec_state());
			
			arrData.put(obj);
		}
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		JSONObject obj =new JSONObject();
		obj.put("status", "200");
		obj.put("message", "success");
		obj.put("totalCount", "1");
		obj.put("data", arrData);
		
		return obj.toString();
    }
	
	@RequestMapping(value="/mobileChartSheetTree", method=RequestMethod.GET)
    public SheetTreeVO selectSheetTree(TreeParamVO vo) throws Exception {
		String base72 = systemOption.selectBase72();	//0: 암호화 사용함, 1: 암호화 사용안함, 2: db사용

		//삽질중... 변수명이 숫자가 먼저 올수 없기에 VO 사용 불가
		SheetTreeVO resultVO = new SheetTreeVO();
		
		List<SheetTreeDataVO> dataList = null;
		
		if(base72.equals("2")) {
			dataList = dbOldTreeMapper.selectSheetTree(vo);
		} else {
			dataList = oldMapper.selectSheetTree(vo);
		}
		
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		return resultVO;
    }
}
