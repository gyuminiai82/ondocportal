package kr.ondoc.mapper.sybase.crm;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import kr.ondoc.domain.sybase.crm.CrmOperationScheduleItemRelationVO;

public interface CrmOperationScheduleItemRelationMapper {
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT * FROM crm_operation_schedule_item_relation WHERE 0=0 "
			+ "]]>"
			+ "<if test=\"operation_schedule_idx != null and operation_schedule_idx != ''\">"
			+ "AND operation_schedule_idx = #{operation_schedule_idx} "
			+ "</if>"
			+ "</script>")
	public List<CrmOperationScheduleItemRelationVO> selectListOperationScheduleItemRelation(CrmOperationScheduleItemRelationVO vo) throws Exception;
	
	@Insert("<script>"
			+ "INSERT INTO crm_operation_schedule_item_relation ("
			+ "operation_schedule_idx, item_idx, item_detail_idx"
			+ ") VALUES ( "
			+ "#{operation_schedule_idx}, #{item_idx}, #{item_detail_idx})"
			+ "</script>")
	public void insertOperationScheduleItemRelation(CrmOperationScheduleItemRelationVO vo) throws Exception;
	
	@Delete("<script>"
			+ "DELETE from crm_operation_schedule_item_relation "
			+ "WHERE 0=0  "
			+ "AND operation_schedule_idx=#{operation_schedule_idx} "
			+ "</script>")
	public void deleteOperationScheduleItemRelation(String operation_schedule_idx) throws Exception;
}
