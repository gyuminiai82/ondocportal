package kr.ondoc.controller.crm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.ondoc.domain.sybase.crm.CrmAgeReserveVO;
import kr.ondoc.domain.sybase.crm.CrmAgeSalesVO;
import kr.ondoc.domain.sybase.crm.CrmGroupItemPaymentVO;
import kr.ondoc.domain.sybase.crm.CrmGroupPaymentVO;
import kr.ondoc.domain.sybase.crm.CrmPathReserveVO;
import kr.ondoc.domain.sybase.crm.CrmPathSalesVO;
import kr.ondoc.domain.sybase.crm.CrmPatientParamVO;
import kr.ondoc.domain.sybase.crm.CrmSettingVO;
import kr.ondoc.domain.sybase.crm.CrmStatisticsAgeSalesVO;
import kr.ondoc.domain.sybase.crm.CrmStatisticsParamVO;
import kr.ondoc.domain.sybase.crm.CrmStatisticsPathSalesVO;
import kr.ondoc.domain.sybase.crm.CrmStatisticsPeriodPaymentTotalVO;
import kr.ondoc.domain.sybase.crm.CrmStatisticsPeriodPaymentVO;
import kr.ondoc.domain.sybase.crm.CrmStatisticsProgressVO;
import kr.ondoc.domain.sybase.crm.CrmStatisticsRegionVO;
import kr.ondoc.domain.sybase.crm.CrmStatisticsTimeSalesVO;
import kr.ondoc.domain.sybase.crm.CrmStatisticsWeekdaySalesVO;
import kr.ondoc.domain.sybase.crm.CrmTimeReserveVO;
import kr.ondoc.domain.sybase.crm.CrmTimeSalesVO;
import kr.ondoc.domain.sybase.crm.CrmTreatementPaymentDetailVO;
import kr.ondoc.domain.sybase.crm.CrmWeekdayReserveVO;
import kr.ondoc.domain.sybase.crm.CrmWeekdaySalesVO;
import kr.ondoc.mapper.sybase.crm.CrmSettingMapper;
import kr.ondoc.mapper.sybase.crm.CrmStatisticsMapper;
import kr.ondoc.mapper.sybase.crm.CrmTreatementPaymentDetailMapper;

@CrossOrigin(origins = "*")
@RestController
public class StatisticsController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CrmStatisticsMapper statisticsMapper;
	
	@Autowired
	private CrmSettingMapper settingMapper;
	
	@Autowired
	private CrmTreatementPaymentDetailMapper paymentDetailMapper;
	
	@RequestMapping(value="/crmStatisticsRegionList", method=RequestMethod.GET)
    public List<CrmStatisticsRegionVO> selectPatientList(CrmPatientParamVO vo) throws Exception {
		
		return statisticsMapper.selectRegionList();
    }
	
	@RequestMapping(value="/crmStatisticsPeriodPaymentList", method=RequestMethod.GET)
    public List<CrmStatisticsPeriodPaymentVO> selectPeriodPaymentList(CrmStatisticsParamVO vo) throws Exception {
		//List<CrmStatisticsPeriodPaymentVO> tempList = statisticsMapper.selectPeriodPaymentList(vo);
		
//		tempList.forEach(obj -> {
//			CrmTreatementPaymentDetailVO tempVO = new CrmTreatementPaymentDetailVO();
//			tempVO.setPtno(obj.getPtno());
//			tempVO.setPayment_idx(obj.getPayment_idx());
//			tempVO.setDel_yn("N");
//			
//			try {
//				obj.setDetailList(paymentDetailMapper.selectListPaymentDetail(tempVO));
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		});
		
		return statisticsMapper.selectPeriodPaymentList(vo);
    }
	
	@RequestMapping(value="/crmStatisticsPeriodPaymentDetailList", method=RequestMethod.GET)
    public List<CrmTreatementPaymentDetailVO> selectPeriodPaymentDetailList(CrmTreatementPaymentDetailVO vo) throws Exception {
		return statisticsMapper.selectListPaymentDetail(vo);
    }
	
	@RequestMapping(value="/crmStatisticsPeriodPaymentDayList", method=RequestMethod.GET)
    public List<CrmStatisticsPeriodPaymentVO> selectPeriodPaymentDayList(CrmStatisticsParamVO vo) throws Exception {
		
		return statisticsMapper.selectPeriodPaymentDayList(vo);
    }
	
	@RequestMapping(value="/crmStatisticsPeriodPaymentMonthList", method=RequestMethod.GET)
    public List<CrmStatisticsPeriodPaymentVO> selectPeriodPaymentMonthList(CrmStatisticsParamVO vo) throws Exception {
		
		return statisticsMapper.selectPeriodPaymentMonthList(vo);
    }
	
	@RequestMapping(value="/crmStatisticsPeriodPaymentTotalList", method=RequestMethod.GET)
    public CrmStatisticsPeriodPaymentTotalVO selectPeriodPaymentTotalList(CrmStatisticsParamVO vo) throws Exception {
		
		return statisticsMapper.selectPeriodPaymentTotalList(vo);
    }
	
	//예약통계-내원경로
	@RequestMapping(value="/crmStatisticsPathSalesList", method=RequestMethod.GET)
    public List<CrmStatisticsPathSalesVO> selectPathSalesList(CrmStatisticsParamVO vo) throws Exception {
		List<CrmStatisticsPathSalesVO> list = new ArrayList<>();

		List<CrmPathReserveVO> pathReserveList = statisticsMapper.selectPathReserveList(vo);
		List<CrmPathSalesVO> pathSalesList = statisticsMapper.selectPathSalesList(vo);
		
		for(int i = 0; i <= 12; i++) {
			if(i == 5) continue;
			if(i == 7) continue;
			if(i == 8) continue;
			if(i == 9) continue;
			CrmStatisticsPathSalesVO tempVO = new CrmStatisticsPathSalesVO();
			
			tempVO.setBpt_channel(Integer.toString(i));
			
			pathReserveList.forEach(pathReserve -> {
				//0: 미방문, 1: 예약취소, 나머지는 방문으로 처리
				
				if(pathReserve.getBpt_channel().equals(tempVO.getBpt_channel()) && pathReserve.getStatus_idx().equals("0")) { tempVO.setReserve_no_visit(pathReserve.getCount()); }
				if(pathReserve.getBpt_channel().equals(tempVO.getBpt_channel()) && pathReserve.getStatus_idx().equals("1")) { tempVO.setReserve_cancel_count(pathReserve.getCount()); }
				if(pathReserve.getBpt_channel().equals(tempVO.getBpt_channel()) && pathReserve.getStatus_idx().equals("2")) { tempVO.setReserve_visit(pathReserve.getCount()); }
				
			});
			
			tempVO.setReserve_count(
						Integer.toString(
								Integer.parseInt(tempVO.getReserve_no_visit()) + Integer.parseInt(tempVO.getReserve_cancel_count()) + Integer.parseInt(tempVO.getReserve_visit())
							)
					);
			
			pathSalesList.forEach(pathSales -> {
				if(pathSales.getBpt_channel().equals(tempVO.getBpt_channel())) { tempVO.setSale_count(pathSales.getCount()); }
				if(pathSales.getBpt_channel().equals(tempVO.getBpt_channel())) { tempVO.setCharge_price(pathSales.getCharge_price()); }
				if(pathSales.getBpt_channel().equals(tempVO.getBpt_channel())) { tempVO.setSale_after_price(pathSales.getSale_after_price()); }
				if(pathSales.getBpt_channel().equals(tempVO.getBpt_channel())) { tempVO.setCnt_price(pathSales.getCnt_price()); }
			});
			
			list.add(tempVO);
		}
		
		return list;
    }
	
	//예약통계-요일별
	@RequestMapping(value="/crmStatisticsWeekdaySalesList", method=RequestMethod.GET)
    public List<CrmStatisticsWeekdaySalesVO> selectWeekdaySalesList(CrmStatisticsParamVO vo) throws Exception {
		List<CrmStatisticsWeekdaySalesVO> list = new ArrayList<>();

		List<CrmWeekdayReserveVO> weekdayReserveList = statisticsMapper.selectWeekdayReserveList(vo);
		List<CrmWeekdaySalesVO> weekdaySalesList = statisticsMapper.selectWeekdaySalesList(vo);
		
		for(int i = 1; i <= 7; i++) {
			CrmStatisticsWeekdaySalesVO tempVO = new CrmStatisticsWeekdaySalesVO();
			
			tempVO.setWeekday(Integer.toString(i));
			
			weekdayReserveList.forEach(weekdayReserve -> {
				//0: 미방문, 1: 예약취소, 나머지는 방문으로 처리
				
				if(weekdayReserve.getWeekday().equals(tempVO.getWeekday()) && weekdayReserve.getStatus_idx().equals("0")) { tempVO.setReserve_no_visit(weekdayReserve.getCount()); }
				if(weekdayReserve.getWeekday().equals(tempVO.getWeekday()) && weekdayReserve.getStatus_idx().equals("1")) { tempVO.setReserve_cancel_count(weekdayReserve.getCount()); }
				if(weekdayReserve.getWeekday().equals(tempVO.getWeekday()) && weekdayReserve.getStatus_idx().equals("2")) { tempVO.setReserve_visit(weekdayReserve.getCount()); }
				
			});
			
			tempVO.setReserve_count(
						Integer.toString(
								Integer.parseInt(tempVO.getReserve_no_visit()) + Integer.parseInt(tempVO.getReserve_cancel_count()) + Integer.parseInt(tempVO.getReserve_visit())
							)
					);
			
			weekdaySalesList.forEach(weekdaySales -> {
				if(weekdaySales.getWeekday().equals(tempVO.getWeekday())) { tempVO.setSale_count(weekdaySales.getCount()); }
				if(weekdaySales.getWeekday().equals(tempVO.getWeekday())) { tempVO.setCharge_price(weekdaySales.getCharge_price()); }
				if(weekdaySales.getWeekday().equals(tempVO.getWeekday())) { tempVO.setSale_after_price(weekdaySales.getSale_after_price()); }
				if(weekdaySales.getWeekday().equals(tempVO.getWeekday())) { tempVO.setCnt_price(weekdaySales.getCnt_price()); }
			});
			
			list.add(tempVO);
		}
		
		return list;
    }
	
	//예약통계-시간별
	@RequestMapping(value="/crmStatisticsTimeSalesList", method=RequestMethod.GET)
    public List<CrmStatisticsTimeSalesVO> selectTimeSalesList(CrmStatisticsParamVO vo) throws Exception {
		List<CrmSettingVO> settingList = settingMapper.selectSetting();
		CrmSettingVO settingVO = settingList.get(0);
		
//		int[] time = {Integer.parseInt(settingVO.getOpen_time_monday().substring(0, 2)), 
//				Integer.parseInt(settingVO.getOpen_time_tuesday().substring(0, 2)), 
//				Integer.parseInt(settingVO.getOpen_time_wednesday().substring(0, 2)), 
//				Integer.parseInt(settingVO.getOpen_time_thursday().substring(0, 2)), 
//				Integer.parseInt(settingVO.getOpen_time_friday().substring(0, 2)),
//				Integer.parseInt(settingVO.getOpen_time_saturday().substring(0, 2)),
//				Integer.parseInt(settingVO.getOpen_time_sunday().substring(0, 2))};
//		int min = Arrays.stream(time).min().getAsInt();
		
		String[] arrTime = {"0800", "0900", "1000", "1100", "1200", "1300", "1400", "1500", "1600", "1700", "1800", "1900", "2000"};

		List<CrmStatisticsTimeSalesVO> list = new ArrayList<>();

		List<CrmTimeReserveVO> timeReserveList = statisticsMapper.selectTimeReserveList(vo);
		List<CrmTimeSalesVO> timeSalesList = statisticsMapper.selectTimeSalesList(vo);
		
		for(int i = 0; i < arrTime.length; i++) {
			CrmStatisticsTimeSalesVO tempVO = new CrmStatisticsTimeSalesVO();
			
			tempVO.setTime(arrTime[i]);
			
			timeReserveList.forEach(timeReserve -> {
				//0: 미방문, 1: 예약취소, 나머지는 방문으로 처리
				
				if(timeReserve.getTime().equals(tempVO.getTime()) && timeReserve.getStatus_idx().equals("0")) { tempVO.setReserve_no_visit(timeReserve.getCount()); }
				if(timeReserve.getTime().equals(tempVO.getTime()) && timeReserve.getStatus_idx().equals("1")) { tempVO.setReserve_cancel_count(timeReserve.getCount()); }
				if(timeReserve.getTime().equals(tempVO.getTime()) && timeReserve.getStatus_idx().equals("2")) { tempVO.setReserve_visit(timeReserve.getCount()); }
				
			});
			
			tempVO.setReserve_count(
						Integer.toString(
								Integer.parseInt(tempVO.getReserve_no_visit()) + Integer.parseInt(tempVO.getReserve_cancel_count()) + Integer.parseInt(tempVO.getReserve_visit())
							)
					);
			
			timeSalesList.forEach(timeSales -> {
				if(timeSales.getTime().equals(tempVO.getTime())) { tempVO.setSale_count(timeSales.getCount()); }
				if(timeSales.getTime().equals(tempVO.getTime())) { tempVO.setCharge_price(timeSales.getCharge_price()); }
				if(timeSales.getTime().equals(tempVO.getTime())) { tempVO.setSale_after_price(timeSales.getSale_after_price()); }
				if(timeSales.getTime().equals(tempVO.getTime())) { tempVO.setCnt_price(timeSales.getCnt_price()); }
			});
			
			list.add(tempVO);
		}
		
		return list;
    }
	
	//예약통계-연령별
	@RequestMapping(value="/crmStatisticsAgeSalesList", method=RequestMethod.GET)
    public List<CrmStatisticsAgeSalesVO> selectAgeSalesList(CrmStatisticsParamVO vo) throws Exception {
		String[] arrAge = {"0", "10", "20", "30", "40", "50", "60", "70", "80", "90"};
		
		List<CrmStatisticsAgeSalesVO> list = new ArrayList<>();

		List<CrmAgeReserveVO> ageReserveList = statisticsMapper.selectAgeReserveList(vo);
		List<CrmAgeSalesVO> ageSalesList = statisticsMapper.selectAgeSalesList(vo);
		
		for(int i = 0; i <arrAge.length; i++) {
			CrmStatisticsAgeSalesVO tempVO = new CrmStatisticsAgeSalesVO();
			
			tempVO.setAge_group(arrAge[i]);
			
			ageReserveList.forEach(ageReserve -> {
				//0: 미방문, 1: 예약취소, 나머지는 방문으로 처리
				
				if(ageReserve.getAge_group().equals(tempVO.getAge_group()) && ageReserve.getStatus_idx().equals("0")) { tempVO.setReserve_no_visit(ageReserve.getCount()); }
				if(ageReserve.getAge_group().equals(tempVO.getAge_group()) && ageReserve.getStatus_idx().equals("1")) { tempVO.setReserve_cancel_count(ageReserve.getCount()); }
				if(ageReserve.getAge_group().equals(tempVO.getAge_group()) && ageReserve.getStatus_idx().equals("2")) { tempVO.setReserve_visit(ageReserve.getCount()); }
				
			});
			
			tempVO.setReserve_count(
						Integer.toString(
								Integer.parseInt(tempVO.getReserve_no_visit()) + Integer.parseInt(tempVO.getReserve_cancel_count()) + Integer.parseInt(tempVO.getReserve_visit())
							)
					);
			
			ageSalesList.forEach(ageSales -> {
				if(ageSales.getAge_group().equals(tempVO.getAge_group())) { tempVO.setSale_count(ageSales.getCount()); }
				if(ageSales.getAge_group().equals(tempVO.getAge_group())) { tempVO.setCharge_price(ageSales.getCharge_price()); }
				if(ageSales.getAge_group().equals(tempVO.getAge_group())) { tempVO.setSale_after_price(ageSales.getSale_after_price()); }
				if(ageSales.getAge_group().equals(tempVO.getAge_group())) { tempVO.setCnt_price(ageSales.getCnt_price()); }
			});
			
			list.add(tempVO);
		}
		
		return list;
    }
	
	//상담사별 통계
	@RequestMapping(value="/crmStatisticsCounselorList", method=RequestMethod.GET)
    public List<CrmGroupPaymentVO> selectCounselorList(CrmStatisticsParamVO vo) throws Exception {
		
		return statisticsMapper.selectCounselorList(vo);
    }
	
	//치료사별 통계
	@RequestMapping(value="/crmStatisticsTherapistList", method=RequestMethod.GET)
    public List<CrmGroupPaymentVO> selectTherapistList(CrmStatisticsParamVO vo) throws Exception {
		
		return statisticsMapper.selectTherapistList(vo);
    }
	
	//담당의사별 통계
	@RequestMapping(value="/crmStatisticsDoctorList", method=RequestMethod.GET)
    public List<CrmGroupPaymentVO> selectDoctorList(CrmStatisticsParamVO vo) throws Exception {
		
		return statisticsMapper.selectDoctorList(vo);
    }
	
	//연령대별 통계
	@RequestMapping(value="/crmStatisticsAgeList", method=RequestMethod.GET)
    public List<CrmGroupPaymentVO> selectAgeList(CrmStatisticsParamVO vo) throws Exception {
		
		return statisticsMapper.selectAgeList(vo);
    }
	
	//진료항목별 통계
	@RequestMapping(value="/crmStatisticsItemList", method=RequestMethod.GET)
    public List<CrmGroupItemPaymentVO> selectItemList(CrmStatisticsParamVO vo) throws Exception {
		
		return statisticsMapper.selectItemList(vo);
    }
	
	//담당의사/상담자/치료사별 통계 내 진료항목 목록
	@RequestMapping(value="/crmStatisticsItemSearchList", method=RequestMethod.GET)
    public List<CrmTreatementPaymentDetailVO> selectItemSearchList(CrmStatisticsParamVO vo) throws Exception {
		
		return statisticsMapper.selectItemSearchList(vo);
    }
	
	@RequestMapping(value="/crmStatisticsItemDetailList", method=RequestMethod.GET)
    public List<CrmGroupItemPaymentVO> selectItemDetailList(CrmStatisticsParamVO vo) throws Exception {
		
		return statisticsMapper.selectItemDetailList(vo);
    }
	
	@RequestMapping(value="/crmDoctorProgressList", method=RequestMethod.GET)
    public List<CrmStatisticsProgressVO> selectListDoctorProgress(CrmStatisticsParamVO vo) throws Exception {
		
		return statisticsMapper.selectListDoctorProgress(vo);
    }
	
	@RequestMapping(value="/crmCounselorProgressList", method=RequestMethod.GET)
    public List<CrmStatisticsProgressVO> selectListCounselorProgress(CrmStatisticsParamVO vo) throws Exception {
		
		return statisticsMapper.selectListCounselorProgress(vo);
    }
	
	@RequestMapping(value="/crmTherapistProgressList", method=RequestMethod.GET)
    public List<CrmStatisticsProgressVO> selectListTherapistProgress(CrmStatisticsParamVO vo) throws Exception {
		
		return statisticsMapper.selectListTherapistProgress(vo);
    }
}
