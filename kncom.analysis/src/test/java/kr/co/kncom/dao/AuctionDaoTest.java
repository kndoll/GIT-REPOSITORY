package kr.co.kncom.dao;

import java.sql.SQLException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import kr.co.kncom.vo.AuctionVO;

public class AuctionDaoTest {
	
	@Before
	public void setUp() {
	}
	
	@Test
	public void test() throws SQLException {
		AuctionDao auctionDao = new AuctionDao();
	
		List<AuctionVO> _testAuctionVO = auctionDao.getAuctionList("06.12.12");
		
		for(AuctionVO vo : _testAuctionVO) {
			System.out.println("ind ==> " + vo.getInd());
			System.out.println("address ==> " + vo.getAddress());
		}
	}
}
