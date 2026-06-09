package kr.ondoc.controller.legacy.penchart;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.ondoc.domain.sybase.legacy.CommonVO;
import kr.ondoc.domain.sybase.legacy.penchart.DrVitaiParamVO;
import kr.ondoc.domain.sybase.legacy.penchart.DrVitalFieldDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.DrVitalFieldVO;
import kr.ondoc.domain.sybase.legacy.penchart.DrVitalValueDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.DrVitalValueVO;
import kr.ondoc.domain.sybase.legacy.penchart.InPatientNaewonV2DataVO;
import kr.ondoc.domain.sybase.legacy.penchart.InPatientNaewonV2VO;
import kr.ondoc.domain.sybase.legacy.penchart.NaewonParamVO;
import kr.ondoc.domain.sybase.legacy.penchart.NaewonYearDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.NaewonYearVO;
import kr.ondoc.domain.sybase.legacy.penchart.NurseRecordDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.NurseRecordParamVO;
import kr.ondoc.domain.sybase.legacy.penchart.NurseRecordVO;
import kr.ondoc.domain.sybase.legacy.penchart.OcsNurseDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.OcsNurseParamVO;
import kr.ondoc.domain.sybase.legacy.penchart.OcsNurseVO;
import kr.ondoc.domain.sybase.legacy.penchart.OcsViaDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.OcsViaVO;
import kr.ondoc.domain.sybase.legacy.penchart.TprParamVO;
import kr.ondoc.domain.sybase.legacy.penchart.TprPatientDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.TprPatientVO;
import kr.ondoc.domain.sybase.legacy.penchart.TprVitalDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.TprVitalHangDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.TprVitalHangVO;
import kr.ondoc.domain.sybase.legacy.penchart.TprVitalVO;
import kr.ondoc.domain.sybase.legacy.penchart.TreatementResultDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.TreatementResultDetailDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.TreatementResultDetailVO;
import kr.ondoc.domain.sybase.legacy.penchart.TreatementResultParamVO;
import kr.ondoc.domain.sybase.legacy.penchart.TreatementResultVO;
import kr.ondoc.domain.sybase.legacy.penchart.VitalDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.VitalParamVO;
import kr.ondoc.domain.sybase.legacy.penchart.VitalVO;
import kr.ondoc.domain.sybase.legacy.penchart.VitalValueDataVO;
import kr.ondoc.domain.sybase.legacy.penchart.VitalValueVO;
import kr.ondoc.mapper.sybase.legacy.penchart.OldLoginMapper;
import kr.ondoc.mapper.sybase.legacy.penchart.OldOcsMapper;

@CrossOrigin(origins = "*")
@RestController
public class OldOcsController {
	@Autowired
	private OldOcsMapper mapper;
	
	@RequestMapping(value="/resultdate", method=RequestMethod.GET)
    public TreatementResultVO selectTreatementResult(TreatementResultParamVO vo) throws Exception {
		TreatementResultVO resultVO = new TreatementResultVO();
		
		List<TreatementResultDataVO> dataList = null;
		
		if(vo.getYear().equals("")) {
			dataList = mapper.selectTreatementResult(vo);
		} else {
			dataList = mapper.selectTreatementResultYear(vo);
		}
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		return resultVO;
    }
	
	@RequestMapping(value="/result", method=RequestMethod.GET)
    public TreatementResultDetailVO selectTreatementResultDetail(TreatementResultParamVO vo) throws Exception {
		TreatementResultDetailVO resultVO = new TreatementResultDetailVO();
		
		List<TreatementResultDetailDataVO> dataList = null;
		
		if(vo.getCode().equals("")) {
			dataList = mapper.selectTreatementResultDetail(vo);
		} else {
			dataList = mapper.selectTreatementResultDetailCode(vo);
		}
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		return resultVO;
    }
	
	@RequestMapping(value="/vital", method=RequestMethod.GET)
    public VitalVO selectVital(VitalParamVO vo) throws Exception {
		VitalVO resultVO = new VitalVO();
		
		List<VitalDataVO> dataList = mapper.selectVital(vo);
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		return resultVO;
    }
	
	@RequestMapping(value="/vitalvalue", method=RequestMethod.GET)
    public VitalValueVO selectVitalValue(VitalParamVO vo) throws Exception {
		VitalValueVO resultVO = new VitalValueVO();
		
		List<VitalValueDataVO> dataList = mapper.selectVitalValue(vo);
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		return resultVO;
    }
	
	@RequestMapping(value="/drnurserecord", method=RequestMethod.GET)
    public NurseRecordVO selectNurserecordV2(NurseRecordParamVO vo) throws Exception {
		NurseRecordVO resultVO = new NurseRecordVO();
		
		List<NurseRecordDataVO> dataList = mapper.selectNurseRecordV2(vo);
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		return resultVO;
    }
	
	@RequestMapping(value="/tprVitalHang", method=RequestMethod.GET)
    public TprVitalHangVO selectTprVitalHang(TprParamVO vo) throws Exception {
		TprVitalHangVO resultVO = new TprVitalHangVO();
		
		List<TprVitalHangDataVO> dataList = mapper.selectTprVitalHang(vo);
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		return resultVO;
    }
	
	@RequestMapping(value="/tprVital", method=RequestMethod.GET)
    public TprVitalVO selectTprVital(TprParamVO vo) throws Exception {
		TprVitalVO resultVO = new TprVitalVO();
		
		List<TprVitalDataVO> dataList = null;
				
		if(vo.getDate().equals("")) dataList = mapper.selectTprVital(vo);
		else  dataList = mapper.selectTprVitalDate(vo);
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		return resultVO;
    }
	
	@RequestMapping(value="/tprVital", method=RequestMethod.POST)
    public CommonVO selectTprVitalSave(TprParamVO vo) throws Exception {
		CommonVO resultVO = new CommonVO();
		
		String[] arrValue = vo.getValues().split("\\|");
		
		for(int i=0; i<arrValue.length; i++) {
			String[] arrVal = arrValue[i].split(",");
			
			vo.setPtno(arrVal[0]);
			mapper.deleteTprVital(vo);
			
			vo.setValue(arrValue[i]);
			try {
				mapper.insertTprVital(vo);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		return resultVO;
    }
	
	@RequestMapping(value="/tprPatient", method=RequestMethod.GET)
    public TprPatientVO selectTprPatient(TprParamVO vo) throws Exception {
		TprPatientVO resultVO = new TprPatientVO();
		
		List<TprPatientDataVO> dataList = mapper.selectTprPatient(vo);
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		return resultVO;
    }
	
	@RequestMapping(value="/drVitalField", method=RequestMethod.GET)
    public DrVitalFieldVO selectDrVitalField() throws Exception {
		DrVitalFieldVO resultVO = new DrVitalFieldVO();
		
		List<DrVitalFieldDataVO> dataList = mapper.selectVitalField();
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		return resultVO;
    }
	
	@RequestMapping(value="/drVitalValue", method=RequestMethod.GET)
    public DrVitalValueVO selectDrVitalValue(DrVitaiParamVO vo) throws Exception {
		DrVitalValueVO resultVO = new DrVitalValueVO();
		
		List<DrVitalValueDataVO> dataList = mapper.selectDrVitalValue(vo);
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		return resultVO;
    }
	
	@RequestMapping(value="/mobileChartVia", method=RequestMethod.GET)
    public OcsViaVO selectVia() throws Exception {
		OcsViaVO resultVO = new OcsViaVO();
		
		List<OcsViaDataVO> dataList = mapper.selectVia();
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		return resultVO;
    }
	
	@RequestMapping(value="/mobileChartOcsNurse", method=RequestMethod.GET)
    public OcsNurseVO selectNurse(OcsNurseParamVO vo) throws Exception {
		OcsNurseVO resultVO = new OcsNurseVO();
		
		List<OcsNurseDataVO> dataList = mapper.selectNurse(vo);
		
		resultVO.setTotalCount(Integer.toString(dataList.size()));
		resultVO.setData(dataList);
		
		return resultVO;
    }
}
