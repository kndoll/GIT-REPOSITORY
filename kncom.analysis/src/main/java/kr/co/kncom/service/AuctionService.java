package kr.co.kncom.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import com.google.gson.Gson;

import kr.co.kncom.dao.AuctionDao;
import kr.co.kncom.dao.MarketConditionDao;
import kr.co.kncom.vo.AuctionVO;
import kr.co.kncom.vo.MarketConditionVO;
import kr.co.kncom.vo.MarketPriceVO;

public class AuctionService {

	// 빈 설정으로 변경해야 함.
	private AuctionDao auctionDao = new AuctionDao();
	private MarketConditionDao marketConditionDao = new MarketConditionDao();

	public List<AuctionVO> getAuctionList(String bidDate) {

		List<AuctionVO> rtnAuctionList = null;

		try {
			rtnAuctionList = auctionDao.getAuctionList(bidDate);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rtnAuctionList;
	}

	public int getAuctionCnt(String bidDate) {

		return auctionDao.getAuctionCnt(bidDate);
	}

	public String getMarketPriceList(Map params) {

		List<MarketPriceVO> marketPriceList = new ArrayList<MarketPriceVO>();
		Gson gson = new Gson();

		// obj -> json 변환
		marketPriceList = marketConditionDao.getMarketPriceList(params);
		String jsonStr = gson.toJson(marketPriceList);

		return jsonStr;
	}

	private List<MarketConditionVO> getMargetConditionFromXML(String dirPath) throws JSONException, IOException {

		List<MarketConditionVO> rtnJsonList = new ArrayList<MarketConditionVO>();

		// #step1. xml -> json 변환
		// JSONObject xmlJSONObj = XML.toJSONObject(readFileAsString(filePath));
		// System.out.println(xmlJSONObj.toString());

		List<String> _fileList = getXmlFileNameList(dirPath, null);
		MarketConditionVO _marketConditionVO = null;
		for (String fileName : _fileList) {
			_marketConditionVO = new MarketConditionVO();
			_marketConditionVO.setFileName(fileName);
			_marketConditionVO.setJsonData(readFileAsString(dirPath + fileName));

			rtnJsonList.add(_marketConditionVO);
		}

		return rtnJsonList;
	}

	/**
	 * 파일 경로에 있는 xml 파일을 json으로 변환하여 리턴한다.
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 * @throws JSONException
	 */
	public String readFileAsString(String filePath) throws IOException, JSONException {

		/*
		 * StringBuffer fileData = new StringBuffer(); BufferedReader reader =
		 * new BufferedReader(new FileReader(filePath)); char[] buf = new
		 * char[1024]; int numRead=0; while((numRead=reader.read(buf)) != -1){
		 * String readData = String.valueOf(buf, 0, numRead);
		 * fileData.append(readData); } reader.close();
		 * 
		 * String xml = fileData.toString();
		 * 
		 * JSONObject xmlJSONObj = XML.toJSONObject(xml); //
		 * System.out.println(xmlJSONObj.toString());
		 * 
		 * return xmlJSONObj.toString();
		 */

		FileInputStream is = new FileInputStream(filePath);

		StringBuffer buffer = new StringBuffer();
		InputStreamReader isr = new InputStreamReader(is, "EUCKR");
		Reader in = new BufferedReader(isr);
		int ch;
		while ((ch = in.read()) > -1) {
			buffer.append((char) ch);
		}
		in.close();

		JSONObject jsonObject = XML.toJSONObject(buffer.toString(), true);

		return jsonObject.toString();
	}

	/**
	 * 특정 경로에 있는 파일목록을 리턴한다.
	 * 
	 * @param dirPath
	 * @return
	 */
	public List<String> getXmlFileNameList(String dirPath, String filterStr) {

		List<String> rtnFileNameList = new ArrayList<String>();

		File dir = new File(dirPath);
		File[] fileList = dir.listFiles();

		for (File file : fileList) {
			if (file.isFile()) {
				rtnFileNameList.add(file.getName());
			}
		}

		return rtnFileNameList;
	}
}
