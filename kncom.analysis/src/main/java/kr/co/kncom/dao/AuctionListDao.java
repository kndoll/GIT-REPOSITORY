package kr.co.kncom.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.kncom.domain.AuctionList;

public interface AuctionListDao extends JpaRepository<AuctionList, String> {
	
	List<AuctionList> findBySaledayLike(String saleday);
	Long countBySaledayLike(String saleday);
	
}
