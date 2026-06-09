package kr.ondoc.controller.penchart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.ondoc.domain.sybase.penchart.AddTreeParamVO;
import kr.ondoc.domain.sybase.penchart.ChartTreeParamVO;
import kr.ondoc.domain.sybase.penchart.EmrSavedVO;
import kr.ondoc.domain.sybase.penchart.TempGroupAuthVO;
import kr.ondoc.domain.sybase.penchart.TreeNextSheetVO;
import kr.ondoc.domain.sybase.penchart.TreeStateVO;
import kr.ondoc.domain.sybase.penchart.TreeVO;
import kr.ondoc.mapper.sybase.legacy.OldOptionInfoMapper;
import kr.ondoc.mapper.sybase.penchart.TreeMapper;
import kr.ondoc.mapper.sybaseemr.legacy.penchart.DbOldTreeMapper;
import kr.ondoc.mapper.sybaseemr.penchart.DbTreeMapper;
import kr.ondoc.util.ArrayHelper;
import kr.ondoc.util.EmrSheet;

@CrossOrigin(origins = "*")
@RestController
public class TreeController {
	@Autowired
	private OldOptionInfoMapper systemOption;
	
	@Autowired
	private TreeMapper treeMapper;
	
	@Autowired
	private DbTreeMapper dbTreeMapper;
	
	/*
	@RequestMapping(value="/penchartChartTree", method=RequestMethod.GET)
    public List<TreeVO> selectChartTree(ChartTreeParamVO vo) throws Exception {
		//락이 걸려 있는지 확인하기 위해 SELECT
		List<ChartTreeParamVO> list1Depth = treeMapper.selectChartTree1Depth(vo);
		
		List<TreeVO> resultVO = new ArrayList<TreeVO>();
		
		for (ChartTreeParamVO item1Depth : list1Depth) {
			TreeVO chartTreeVO = new TreeVO();
			
			chartTreeVO.setId(item1Depth.getDepth1_id()+"-");
			chartTreeVO.setText(item1Depth.getDepth1_name());
			TreeStateVO chartTreeStateVO = new TreeStateVO();
			chartTreeVO.setState(chartTreeStateVO);
			
			ChartTreeParamVO param1VO = new ChartTreeParamVO();
			param1VO.setDepth1_id(item1Depth.getDepth1_id());
			param1VO.setOec_ptno(vo.getOec_ptno());
			param1VO.setOec_inout(vo.getOec_inout());
			param1VO.setOec_regno(vo.getOec_regno());
			param1VO.setXer_uid(vo.getXer_uid());
			param1VO.setSeqs(vo.getSeqs());
			param1VO.setBes_kind(vo.getBes_kind());
			param1VO.setStart_date(vo.getStart_date());
			param1VO.setEnd_date(vo.getEnd_date());
			
			List<ChartTreeParamVO> list2Depth = treeMapper.selectChartTree2Depth(param1VO);
			
			List<TreeVO> chartTree2VOList = new ArrayList<TreeVO>();			
			for (ChartTreeParamVO item2Depth : list2Depth) {
				TreeVO chartTree2VO = new TreeVO();
				
				chartTree2VO.setId(item2Depth.getDepth2_id()+"--");
				chartTree2VO.setText(item2Depth.getDepth2_name());
				TreeStateVO chartTreeState2VO = new TreeStateVO();
				chartTree2VO.setState(chartTreeState2VO);
				
				chartTree2VOList.add(chartTree2VO);
				
				ChartTreeParamVO param2VO = new ChartTreeParamVO();
				param2VO.setDepth2_id(item2Depth.getDepth2_id());
				param2VO.setOec_ptno(vo.getOec_ptno());
				param2VO.setOec_inout(vo.getOec_inout());
				param2VO.setOec_regno(vo.getOec_regno());
				param2VO.setXer_uid(vo.getXer_uid());
				param2VO.setSeqs(vo.getSeqs());
				param2VO.setBes_kind(vo.getBes_kind());
				param2VO.setStart_date(vo.getStart_date());
				param2VO.setEnd_date(vo.getEnd_date());
				
				List<ChartTreeParamVO> list3Depth = treeMapper.selectChartTree3Depth(param2VO);
				
				List<TreeVO> chartTree3VOList = new ArrayList<TreeVO>();	
				for (ChartTreeParamVO item3Depth : list3Depth) {
					TreeVO chartTree3VO = new TreeVO();
					
					chartTree3VO.setType("file");
					chartTree3VO.setId(item3Depth.getDepth3_id());
					chartTree3VO.setText(item3Depth.getDepth3_name());
					TreeStateVO chartTreeState3VO = new TreeStateVO();
					chartTreeState3VO.setDisabled(false);
					chartTree3VO.setState(chartTreeState3VO);
					
					chartTree3VOList.add(chartTree3VO);
				}
				
				chartTree2VO.setChildren(chartTree3VOList);
			}
			
			chartTreeVO.setChildren(chartTree2VOList);
				
			resultVO.add(chartTreeVO);
		}
		
		return resultVO;
    }
    */
	
	@RequestMapping(value="/penchartChartTree", method=RequestMethod.GET)
    public List<TreeVO> selectChartTree(ChartTreeParamVO vo) throws Exception {
		String base72 = systemOption.selectBase72();	//0: 암호화 사용함, 1: 암호화 사용안함, 2: db사용
		
		List<TreeVO> resultVO = new ArrayList<TreeVO>();
		
		List<ChartTreeParamVO> treeList = null;
		
		if(base72.equals("2")) {
			treeList = dbTreeMapper.selectChartTree(vo);
		} else {
			treeList = treeMapper.selectChartTree(vo);
		}
		
		String[] arrItems1Depth = new String[]{};
		String[] arrItems2Depth = new String[]{};
		String[] arrItems3Depth = new String[]{};
		
		//depth1
		for (ChartTreeParamVO item1Depth : treeList) {
			if(!Arrays.asList(arrItems1Depth).contains(item1Depth.getDepth1_id())) {
				arrItems1Depth = ArrayHelper.push(arrItems1Depth, item1Depth.getDepth1_id());
				
				TreeVO chartTreeVO = new TreeVO();
				
				chartTreeVO.setId(item1Depth.getDepth1_id()+"-");
				chartTreeVO.setText(item1Depth.getDepth1_name());
				TreeStateVO chartTreeStateVO = new TreeStateVO();
				chartTreeVO.setState(chartTreeStateVO);
				
				//2depth
				arrItems2Depth = new String[]{};
				List<TreeVO> chartTree2VOList = new ArrayList<TreeVO>();			
				for (ChartTreeParamVO item2Depth : treeList) {
					if(!Arrays.asList(arrItems2Depth).contains(item2Depth.getDepth2_id()) && (item1Depth.getDepth1_id().equals(item2Depth.getDepth1_id()))) {
						arrItems2Depth = ArrayHelper.push(arrItems2Depth, item2Depth.getDepth2_id());
						
						TreeVO chartTree2VO = new TreeVO();
						
						chartTree2VO.setId(item2Depth.getDepth2_id()+"--");
						chartTree2VO.setText(item2Depth.getDepth2_name());
						TreeStateVO chartTreeState2VO = new TreeStateVO();
						chartTree2VO.setState(chartTreeState2VO);
						
						chartTree2VOList.add(chartTree2VO);
						
						//3depth
						arrItems3Depth = new String[]{};
						List<TreeVO> chartTree3VOList = new ArrayList<TreeVO>();			
						for (ChartTreeParamVO item3Depth : treeList) {
							if((item2Depth.getDepth2_id().equals(item3Depth.getDepth2_id()))) {
								arrItems3Depth = ArrayHelper.push(arrItems3Depth, item3Depth.getDepth3_id());
								
								TreeVO chartTree3VO = new TreeVO();
								
								chartTree3VO.setType("file");
								chartTree3VO.setId(item3Depth.getDepth3_id());
								chartTree3VO.setText(item3Depth.getDepth3_name());
								TreeStateVO chartTreeState3VO = new TreeStateVO();
								chartTreeState3VO.setDisabled(false);
								chartTree3VO.setState(chartTreeState3VO);
								
								chartTree3VOList.add(chartTree3VO);
							}
						}
						
						chartTree2VO.setChildren(chartTree3VOList);
					}
				}
				
				chartTreeVO.setChildren(chartTree2VOList);
				resultVO.add(chartTreeVO);
			}
		}
		
		return resultVO;
    }
	
	@RequestMapping(value="/penchartAddTree", method=RequestMethod.GET)
    public List<TreeVO> selectAddTree(AddTreeParamVO vo) throws Exception {
		String base72 = systemOption.selectBase72();	//0: 암호화 사용함, 1: 암호화 사용안함, 2: db사용
		
		//락이 걸려 있는지 확인하기 위해 SELECT
		List<AddTreeParamVO> list1Depth = null;
		
		if(base72.equals("2")) {
			list1Depth = dbTreeMapper.selectAddTree1Depth(vo);
		} else {
			list1Depth = treeMapper.selectAddTree1Depth(vo);
		}
		
		List<TreeVO> resultVO = new ArrayList<TreeVO>();
		
		for (AddTreeParamVO item1Depth : list1Depth) {
			TreeVO addTreeVO = new TreeVO();
			
			addTreeVO.setId(item1Depth.getBes_id()+"-");
			addTreeVO.setText(item1Depth.getBes_name());
			TreeStateVO addTreeStateVO = new TreeStateVO();
			addTreeStateVO.setDisabled(true);
			addTreeVO.setState(addTreeStateVO);
			
			AddTreeParamVO param1VO = new AddTreeParamVO();
			param1VO.setBes_id(item1Depth.getBes_id());
			param1VO.setBes_inout(vo.getBes_inout());
			param1VO.setXer_uid(vo.getXer_uid());
			param1VO.setBes_kind(vo.getBes_kind());
			
			List<AddTreeParamVO> list2Depth = null;
			
			if(base72.equals("2")) {
				list2Depth = dbTreeMapper.selectAddTree2Depth(param1VO);
			} else {
				list2Depth = treeMapper.selectAddTree2Depth(param1VO);
			}
			
			List<TreeVO> chartTree2VOList = new ArrayList<TreeVO>();			
			for (AddTreeParamVO item2Depth : list2Depth) {
				TreeVO addTree2VO = new TreeVO();
				
				addTree2VO.setType("file");
				addTree2VO.setId(item2Depth.getBe2_no());
				addTree2VO.setText(item2Depth.getBes_name());
				TreeStateVO chartTreeState2VO = new TreeStateVO();
				chartTreeState2VO.setDisabled(false);
				addTree2VO.setState(chartTreeState2VO);
				
				chartTree2VOList.add(addTree2VO);
			}
			
			addTreeVO.setChildren(chartTree2VOList);
				
			resultVO.add(addTreeVO);
		}
		
		return resultVO;
    }
	
	@RequestMapping(value="/penchartChartTreeNextSheet", method=RequestMethod.GET)
    public String selectChartTreeNextSheet(ChartTreeParamVO vo) throws Exception {
		
		String parent_id = treeMapper.selectChartTree3DepthNextSheetParentId(vo);
		
		vo.setDepth2_id(parent_id);
		
		String nextSheet = treeMapper.selectChartTree3DepthNextSheet(vo);
		
		if(nextSheet == null) return "";
		else return nextSheet;
    }
}