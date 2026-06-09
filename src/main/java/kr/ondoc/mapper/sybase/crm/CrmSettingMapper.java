package kr.ondoc.mapper.sybase.crm;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.ondoc.domain.sybase.crm.CrmCodeParamVO;
import kr.ondoc.domain.sybase.crm.CrmFieldVO;
import kr.ondoc.domain.sybase.crm.CrmFieldkindVO;
import kr.ondoc.domain.sybase.crm.CrmOpcodeVO;
import kr.ondoc.domain.sybase.crm.CrmPartVO;
import kr.ondoc.domain.sybase.crm.CrmSettingVO;
import kr.ondoc.domain.sybase.legacy.HosinfoDataVO;

public interface CrmSettingMapper {
	@Select("<script>"
			+ "<![CDATA["
			+ "SELECT TOP 1 bhi_hos03 "
			+ "FROM hosinfo "
			+ "WHERE 0=0 AND bhi_usechk='Y'"
			+ "AND bhi_stdate<=convert(char,GETDATE(),112) ORDER BY bhi_stdate DESC"
			+ "]]>"
			+ "</script>")
	public String selectHosName() throws Exception;
	
	@Select("<script>"
			+ "SELECT TOP 1 * FROM crm_setting "
			+ "WHERE 0=0 "
			+ "</script>")
	public CrmSettingVO setting() throws Exception;
	
	@Select("<script>"
			+ "SELECT TOP 1 * FROM crm_setting "
			+ "WHERE 0=0 "
			+ "</script>")
	public List<CrmSettingVO> selectSetting() throws Exception;
	
	@Insert("<script>"
			+ "INSERT INTO crm_setting ("
			+ "dashboard_type) "
			+ "VALUES ( "
			+ "#{dashboard_type})"
			+ "</script>")
	public void insertSetting(CrmSettingVO vo) throws Exception;
	
	@Update("<script>"
			+ "UPDATE crm_setting SET "
			+ "setting_idx = setting_idx "
			+ "<if test=\"dashboard_type != null\">"
			+ ",dashboard_type = #{dashboard_type} "
			+ "</if>"
			+ "<if test=\"open_time_monday != null\">"
			+ ",open_time_monday = #{open_time_monday} "
			+ "</if>"
			+ "<if test=\"close_time_monday != null\">"
			+ ",close_time_monday = #{close_time_monday} "
			+ "</if>"
			+ "<if test=\"open_time_tuesday != null\">"
			+ ",open_time_tuesday = #{open_time_tuesday} "
			+ "</if>"
			+ "<if test=\"close_time_tuesday != null\">"
			+ ",close_time_tuesday = #{close_time_tuesday} "
			+ "</if>"
			+ "<if test=\"open_time_wednesday != null\">"
			+ ",open_time_wednesday = #{open_time_wednesday} "
			+ "</if>"
			+ "<if test=\"close_time_wednesday != null\">"
			+ ",close_time_wednesday = #{close_time_wednesday} "
			+ "</if>"
			+ "<if test=\"open_time_thursday != null\">"
			+ ",open_time_thursday = #{open_time_thursday} "
			+ "</if>"
			+ "<if test=\"close_time_thursday != null\">"
			+ ",close_time_thursday = #{close_time_thursday} "
			+ "</if>"
			+ "<if test=\"open_time_friday != null\">"
			+ ",open_time_friday = #{open_time_friday} "
			+ "</if>"
			+ "<if test=\"close_time_friday != null\">"
			+ ",close_time_friday = #{close_time_friday} "
			+ "</if>"
			+ "<if test=\"open_time_saturday != null\">"
			+ ",open_time_saturday = #{open_time_saturday} "
			+ "</if>"
			+ "<if test=\"close_time_saturday != null\">"
			+ ",close_time_saturday = #{close_time_saturday} "
			+ "</if>"
			+ "<if test=\"open_time_sunday != null\">"
			+ ",open_time_sunday = #{open_time_sunday} "
			+ "</if>"
			+ "<if test=\"close_time_sunday != null\">"
			+ ",close_time_sunday = #{close_time_sunday} "
			+ "</if>"
			+ "<if test=\"lunch_start_time != null\">"
			+ ",lunch_start_time = #{lunch_start_time} "
			+ "</if>"
			+ "<if test=\"lunch_end_time != null\">"
			+ ",lunch_end_time = #{lunch_end_time} "
			+ "</if>"
			+ "<if test=\"online_reserve_yn != null\">"
			+ ",online_reserve_yn = #{online_reserve_yn} "
			+ "</if>"
			+ "<if test=\"sms_member_id != null\">"
			+ ",sms_member_id = #{sms_member_id} "
			+ "</if>"
			+ "<if test=\"sms_send_number != null\">"
			+ ",sms_send_number = #{sms_send_number} "
			+ "</if>"
			+ "<if test=\"sms_use_yn != null\">"
			+ ",sms_use_yn = #{sms_use_yn} "
			+ "</if>"
			+ "<if test=\"sms_kakao_hospitalCode != null\">"
			+ ",sms_kakao_hospitalCode = #{sms_kakao_hospitalCode} "
			+ "</if>"
			+ "<if test=\"sms_kakao_send_number != null\">"
			+ ",sms_kakao_send_number = #{sms_kakao_send_number} "
			+ "</if>"
			+ "<if test=\"sms_kakao_use_yn != null\">"
			+ ",sms_kakao_use_yn = #{sms_kakao_use_yn} "
			+ "</if>"
			+ "<if test=\"modify_id != null and modify_id != ''\">"
			+ ",modify_date = (convert(char,GETDATE(),112) + SUBSTRING(convert(char,GETDATE(),8), 1, 2) + SUBSTRING(convert(char,GETDATE(),8), 4, 2)) "
			+ ",modify_id = #{modify_id} "
			+ "</if>"
			+ "WHERE 0=0 "
			//+ "AND setting_idx = #{setting_idx}"
			+ "</script>")
	public void updateSetting(CrmSettingVO vo) throws Exception;
	
	@Update("<script>"
			+ "UPDATE crm_setting SET "
			+ "setting_idx = setting_idx "
			+ "<if test=\"status_setcolor_use_yn != null and status_setcolor_use_yn != ''\">"
			+ ",status_setcolor_use_yn = #{status_setcolor_use_yn} "
			+ "</if>"
			+ "<if test=\"status_setcolor_change_use_yn != null and status_setcolor_change_use_yn != ''\">"
			+ ",status_setcolor_change_use_yn = #{status_setcolor_change_use_yn} "
			+ "</if>"
			+ "<if test=\"status_first_background_color != null and status_first_background_color != ''\">"
			+ ",status_first_background_color = #{status_first_background_color} "
			+ "</if>"
			+ "<if test=\"status_first_color != null and status_first_color != ''\">"
			+ ",status_first_color = #{status_first_color} "
			+ "</if>"
			+ "<if test=\"status_re_background_color != null and status_re_background_color != ''\">"
			+ ",status_re_background_color = #{status_re_background_color} "
			+ "</if>"
			+ "<if test=\"status_re_color != null and status_re_color != ''\">"
			+ ",status_re_color = #{status_re_color} "
			+ "</if>"
			+ "<if test=\"modify_id != null and modify_id != ''\">"
			+ ",modify_date = (convert(char,GETDATE(),112) + SUBSTRING(convert(char,GETDATE(),8), 1, 2) + SUBSTRING(convert(char,GETDATE(),8), 4, 2)) "
			+ ",modify_id = #{modify_id} "
			+ "</if>"
			+ "WHERE 0=0 "
			//+ "AND setting_idx = #{setting_idx}"
			+ "</script>")
	public void updateColorSetting(CrmSettingVO vo) throws Exception;

//	@Select("<script>"
//			+ "SELECT cfk_code, cfk_name, cfk_seq, cfk_use FROM crm_fieldkind "
//			+ "WHERE 0=0 "
//			+ "AND cfk_use='Y' "
//			+ "ORDER BY cfk_seq"
//			+ "</script>")
//	public List<CrmFieldkindVO> selectFieldkind() throws Exception;
//	
//	@Select("<script>"
//			+ "SELECT cfk_code, cfk_name, cfk_seq, cfk_use FROM crm_fieldkind "
//			+ "WHERE 0=0 "
//			+ "ORDER BY cfk_seq"
//			+ "</script>")
//	public List<CrmFieldkindVO> selectFieldkindAll() throws Exception;
//	
//	@Insert("<script>"
//			+ "INSERT INTO crm_fieldkind ("
//			+ "cfk_code, cfk_name, cfk_seq, cfk_use) "
//			+ "VALUES ( "
//			+ "#{code}, #{name}, + (SELECT MAX(cfk_seq) + 1 FROM crm_fieldkind), #{use})"
//			+ "</script>")
//	public void insertFieldkind(CrmCodeParamVO vo) throws Exception;
//	
//	@Update("<script>"
//			+ "UPDATE crm_fieldkind SET "
//			+ "cfk_code = cfk_code "
//			+ "<if test=\"name != null and name != ''\">"
//			+ ",cfk_name = #{name} "
//			+ "</if>"
//			+ "<if test=\"seq != null and seq != ''\">"
//			+ ",cfk_seq = #{seq} "
//			+ "</if>"
//			+ "<if test=\"use != null and use != ''\">"
//			+ ",cfk_use = #{use} "
//			+ "</if>"
//			+ "WHERE 0=0 "
//			+ "AND cfk_code = #{code}"
//			+ "</script>")
//	public void updateFieldkind(CrmCodeParamVO vo) throws Exception;
//
//
//	@Delete("<script>"
//			+ "DELETE crm_fieldkind "
//			+ "WHERE 0=0 "
//			+ "AND cfk_code = #{code}"
//			+ "</script>")
//	public void deleteFieldkind(CrmCodeParamVO vo) throws Exception;
//	
//	
//	@Update("<script>"
//			+ "UPDATE crm_fieldkind SET "
//			+ "cfk_code = cfk_code "
//			+ ",cfk_seq = cfk_seq + 1 "
//			+ "WHERE 0=0 "
//			+ "<![CDATA["
//			+ "AND cfk_seq >= #{seq}"
//			+ "]]>"
//			+ "</script>")
//	public void updateFieldkindSort(CrmCodeParamVO vo) throws Exception;
//	
//	@Update("<script>"
//			+ "UPDATE crm_fieldkind SET "
//			+ "cfk_seq = (SELECT MAX(cfk_seq) + 1 FROM crm_fieldkind) "
//			+ "WHERE 0=0 "
//			+ "<![CDATA["
//			+ "AND cfk_code = #{code}"
//			+ "]]>"
//			+ "</script>")
//	public void updateFieldkindSortLast(CrmCodeParamVO vo) throws Exception;
//	
//	@Select("<script>"
//			+ "SELECT cfd_kind, cfd_code, cfd_name, cfd_seq, cfd_use, cfd_med FROM crm_field WHERE 0=0 "
//			+ "<if test=\"code != null and code != ''\">"
//			+ "AND cfd_code = #{code} "
//			+ "</if>"
//			+ "ORDER BY cfd_seq DESC"
//			+ "</script>")
//	public List<CrmFieldVO> selectField(String code) throws Exception;
//	
//	@Select("<script>"
//			+ "SELECT cpu_code, cpu_name, cpu_seq, cpu_use FROM crm_part "
//			+ "WHERE 0=0 "
//			+ "AND cpu_use='Y' "
//			+ "ORDER BY cpu_seq"
//			+ "</script>")
//	public List<CrmPartVO> selectPart() throws Exception;
//	
//	@Select("<script>"
//			+ "SELECT cpu_code, cpu_name, cpu_seq, cpu_use FROM crm_part "
//			+ "WHERE 0=0 "
//			+ "ORDER BY cpu_seq"
//			+ "</script>")
//	public List<CrmPartVO> selectPartAll() throws Exception;
//	
//	@Insert("<script>"
//			+ "INSERT INTO crm_part ("
//			+ "cpu_code, cpu_name, cpu_seq, cpu_use) "
//			+ "VALUES ( "
//			+ "#{code}, #{name}, + (SELECT MAX(cpu_seq) + 1 FROM crm_part), #{use})"
//			+ "</script>")
//	public void insertPart(CrmCodeParamVO vo) throws Exception;
//	
//	@Update("<script>"
//			+ "UPDATE crm_part SET "
//			+ "cpu_code = cpu_code "
//			+ "<if test=\"name != null and name != ''\">"
//			+ ",cpu_name = #{name} "
//			+ "</if>"
//			+ "<if test=\"seq != null and seq != ''\">"
//			+ ",cpu_seq = #{seq} "
//			+ "</if>"
//			+ "<if test=\"use != null and use != ''\">"
//			+ ",cpu_use = #{use} "
//			+ "</if>"
//			+ "WHERE 0=0 "
//			+ "AND cpu_code = #{code}"
//			+ "</script>")
//	public void updatePart(CrmCodeParamVO vo) throws Exception;
//
//
//	@Delete("<script>"
//			+ "DELETE crm_part "
//			+ "WHERE 0=0 "
//			+ "AND cpu_code = #{code}"
//			+ "</script>")
//	public void deletePart(CrmCodeParamVO vo) throws Exception;
//	
//	
//	@Update("<script>"
//			+ "UPDATE crm_part SET "
//			+ "cpu_code = cpu_code "
//			+ ",cpu_seq = cpu_seq + 1 "
//			+ "WHERE 0=0 "
//			+ "<![CDATA["
//			+ "AND cpu_seq >= #{seq}"
//			+ "]]>"
//			+ "</script>")
//	public void updatePartSort(CrmCodeParamVO vo) throws Exception;
//	
//	@Update("<script>"
//			+ "UPDATE crm_part SET "
//			+ "cpu_seq = (SELECT MAX(cpu_seq) + 1 FROM crm_Part) "
//			+ "WHERE 0=0 "
//			+ "<![CDATA["
//			+ "AND cpu_code = #{code}"
//			+ "]]>"
//			+ "</script>")
//	public void updatePartSortLast(CrmCodeParamVO vo) throws Exception;
//	
//	@Select("<script>"
//			+ "SELECT cop_code, cop_name, cop_seq, cop_use FROM crm_opcode "
//			+ "WHERE 0=0 "
//			+ "AND cop_use='Y' "
//			+ "ORDER BY cop_seq"
//			+ "</script>")
//	public List<CrmOpcodeVO> selectOpcode() throws Exception;
//	
//	@Select("<script>"
//			+ "SELECT cop_code, cop_name, cop_seq, cop_use FROM crm_opcode "
//			+ "WHERE 0=0 "
//			+ "ORDER BY cop_seq"
//			+ "</script>")
//	public List<CrmOpcodeVO> selectOpcodeAll() throws Exception;
//	
//	@Insert("<script>"
//			+ "INSERT INTO crm_opcode ("
//			+ "cop_code, cop_name, cop_seq, cop_use) "
//			+ "VALUES ( "
//			+ "#{code}, #{name}, + (SELECT MAX(cop_seq) + 1 FROM crm_opcode), #{use})"
//			+ "</script>")
//	public void insertOpcode(CrmCodeParamVO vo) throws Exception;
//	
//	@Update("<script>"
//			+ "UPDATE crm_opcode SET "
//			+ "cop_code = cop_code "
//			+ "<if test=\"name != null and name != ''\">"
//			+ ",cop_name = #{name} "
//			+ "</if>"
//			+ "<if test=\"seq != null and seq != ''\">"
//			+ ",cop_seq = #{seq} "
//			+ "</if>"
//			+ "<if test=\"use != null and use != ''\">"
//			+ ",cop_use = #{use} "
//			+ "</if>"
//			+ "WHERE 0=0 "
//			+ "AND cop_code = #{code}"
//			+ "</script>")
//	public void updateOpcode(CrmCodeParamVO vo) throws Exception;
//
//	@Delete("<script>"
//			+ "DELETE crm_opcode "
//			+ "WHERE 0=0 "
//			+ "AND cop_code = #{code}"
//			+ "</script>")
//	public void deleteOpcode(CrmCodeParamVO vo) throws Exception;
//	
//	@Update("<script>"
//			+ "UPDATE crm_opcode SET "
//			+ "cop_code = cop_code "
//			+ ",cop_seq = cop_seq + 1 "
//			+ "WHERE 0=0 "
//			+ "<![CDATA["
//			+ "AND cop_seq >= #{seq}"
//			+ "]]>"
//			+ "</script>")
//	public void updateOpcodeSort(CrmCodeParamVO vo) throws Exception;
//	
//	@Update("<script>"
//			+ "UPDATE crm_opcode SET "
//			+ "cop_seq = (SELECT MAX(cop_seq) + 1 FROM crm_Opcode) "
//			+ "WHERE 0=0 "
//			+ "<![CDATA["
//			+ "AND cop_code = #{code}"
//			+ "]]>"
//			+ "</script>")
//	public void updateOpcodeSortLast(CrmCodeParamVO vo) throws Exception;
}
