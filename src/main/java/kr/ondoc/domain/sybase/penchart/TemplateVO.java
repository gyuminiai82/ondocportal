package kr.ondoc.domain.sybase.penchart;

import java.util.ArrayList;
import java.util.List;

public class TemplateVO {
	List<TemplateTreatementCateVO> treatementCate;
	List<TemplateTreatementDetailVO> treatementDetail;
	List<TemplateBoilerplateCateVO> boilerplateCate;
	List<TemplateBoilerplateDetailVO> boilerplateDetail;
	
	public List<TemplateTreatementCateVO> getTreatementCate() {
		return treatementCate;
	}
	public void setTreatementCate(List<TemplateTreatementCateVO> treatementCate) {
		this.treatementCate = treatementCate;
	}
	public List<TemplateTreatementDetailVO> getTreatementDetail() {
		return treatementDetail;
	}
	public void setTreatementDetail(List<TemplateTreatementDetailVO> treatementDetail) {
		this.treatementDetail = treatementDetail;
	}
	public List<TemplateBoilerplateCateVO> getBoilerplateCate() {
		return boilerplateCate;
	}
	public void setBoilerplateCate(List<TemplateBoilerplateCateVO> boilerplateCate) {
		this.boilerplateCate = boilerplateCate;
	}
	public List<TemplateBoilerplateDetailVO> getBoilerplateDetail() {
		return boilerplateDetail;
	}
	public void setBoilerplateDetail(List<TemplateBoilerplateDetailVO> boilerplateDetail) {
		this.boilerplateDetail = boilerplateDetail;
	}
}
