package kr.ondoc.util;

public class PagingUtil {
	String page = "";
	String totalRecord = "";
	String totalPage = "";
	int first = 0;
	int last = 0;
	String paging = "";
	String num = "";
	
	public PagingUtil(String fnName, String totalRecord, String page, String listNum, String blockNum) {
		this.totalRecord = totalRecord;
		this.page = page;
		
		String paging = "";
		int isPage = 0;
		
		if(totalRecord.equals("1")) {
			this.first = 1;
			this.last = 0;
		} else {
			this.first = Integer.parseInt(listNum) * (Integer.parseInt(this.page) - 1) + 1;
			this.last = Integer.parseInt(listNum) * Integer.parseInt(this.page) + 1;
			
			int isNext = Integer.parseInt(totalRecord) - this.last;
			
			if(isNext > 0) {
				this.last -= 1;
			} else {
				this.last = Integer.parseInt(this.totalRecord) - 1;
			}
		}
		
		this.totalPage = Integer.toString((int)Math.ceil(Double.parseDouble(this.totalRecord) / Double.parseDouble(listNum)));
		
		int totalBlock = (int)Math.ceil((Double.parseDouble(this.totalPage) / Double.parseDouble(blockNum)));
		int block = (int)Math.ceil((Double.parseDouble(this.page) / Double.parseDouble(blockNum)));
		int firstPage = (block- 1) * Integer.parseInt(blockNum);
		int lastPage = block * Integer.parseInt(blockNum);
		
		this.num = Integer.toString(Integer.parseInt(this.totalRecord) - Integer.parseInt(listNum) * (Integer.parseInt(this.page) - 1));
		
		if(block >= totalBlock) {
			lastPage = Integer.parseInt(this.totalPage);
		}
		
		//처음으로
		paging = paging+"<li class='page-item'><a class='page-link' href='#1' onclick='"+fnName+"(1)'>처음</a></li>";
		
		//10페이지 이전
		if(block > 1){
			isPage = firstPage;
			paging = paging+"<li class='page-item'><a href='#Prev' class='page-link' onclick='"+fnName+"("+isPage+")'>&laquo;</a></li>";
		} else {
			paging = paging+"<li class='page-item'><a href='#Prev' onclick='return false;' class='page-link'>&laquo;</a></li>";
		}
		
		//1페이지 이전
		if(lastPage >= Integer.parseInt(this.page) && Integer.parseInt(this.page) != 1) {
			paging = paging+"<li class='page-item'><a class='page-link' href='#"+(Integer.parseInt(this.page)-1)+"' onclick='"+fnName+"("+(Integer.parseInt(this.page)-1)+")'>&lt;</a></li> ";
		} else {
			paging = paging+"<li class='page-item'><a class='page-link' href='#'>&lt;</a></li> ";
		}
		
		//페이지
		for(int linkPage = firstPage+1; linkPage<=lastPage; linkPage++){
			if(Integer.parseInt(this.page) == linkPage){
				paging = paging+"<li class='selected page-item'><a class='page-link' href='#"+linkPage+"' onclick='return false;'><strong>"+linkPage+"</strong></a></li>";
			} else {
				paging = paging+"<li class='page-item'><a class='page-link' href='#"+linkPage+"' onclick='"+fnName+"("+linkPage+")'>"+linkPage+"</a></li>";
			}
		}
		
		//1페이지 다음
		if(Integer.parseInt(this.page) < Integer.parseInt(this.totalPage)) {
			paging = paging+"<li class='page-item'><a class='page-link' href='#"+(Integer.parseInt(this.page)+1)+"' onclick='"+fnName+"("+(Integer.parseInt(this.page)+1)+")'>&gt;</a></li> ";
		} else {
			paging = paging+"<li class='page-item'><a class='page-link' href='#'>&gt;</a></li> ";
		}
		
		//10페이지 다음
		if(block < totalBlock) {
			isPage = lastPage + 1;
			paging = paging+"<li class='page-item'><a href='#Next' class='page-link' onclick='"+fnName+"("+isPage+")'>&raquo;</a></li>";
		} else {
			paging = paging+"<li class='page-item'><a href='#Next' onclick='return false;' class='page-link'>&raquo;</a></li>";
		}
		
		//마지막으로
		paging = paging+"<li class='page-item'><a class='page-link' href='#"+this.totalPage+"' onclick='"+fnName+"("+this.totalPage+")'>맨뒤</a></li>";
		
		
		
		this.paging = paging;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(String totalRecord) {
		this.totalRecord = totalRecord;
	}

	public String getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(String totalPage) {
		this.totalPage = totalPage;
	}

	public int getFirst() {
		return first;
	}

	public void setFirst(int first) {
		this.first = first;
	}

	public int getLast() {
		return last;
	}

	public void setLast(int last) {
		this.last = last;
	}

	public String getPaging() {
		return paging;
	}

	public void setPaging(String paging) {
		this.paging = paging;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}
}
