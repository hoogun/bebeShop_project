package com.bebe.spring.search.dao;

import java.util.List;

import com.bebe.spring.vo.Criteria;
import com.bebe.spring.vo.ProductVO;


public interface SearchDAO {
	//카테고리별 수량
	public Integer count(Criteria cri);
	//출력
	public List<Criteria> getlist(Criteria cri);
<<<<<<< HEAD
	//검색기준 수량
	public Integer scount(Criteria cri);
	//검색상품 출력
=======
	
	
	public Integer scount(Criteria cri);
>>>>>>> origin/oyw_work_backup
	public List<Criteria> searchlist(Criteria cri);

	
}
