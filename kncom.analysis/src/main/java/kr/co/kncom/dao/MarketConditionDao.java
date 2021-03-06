package kr.co.kncom.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import com.google.gson.Gson;

import kr.co.kncom.util.FileUtil;
import kr.co.kncom.vo.MarketPriceVO;

/**
 * 
 * @author kndoll
 *
 */
public class MarketConditionDao {
	
	// 아래 데이터는 properties로 뽑아내야 함.
	
	
	private Gson gson = new Gson();
	
	public List<MarketPriceVO> getMarketPriceList(Map params) {
		
		String index = params.get("index").toString();
		
//		System.out.println("## INDEX ==> " + index);
		String marketPriceDirPath = "P:\\newvalues\\1\\";
		String areaDirPath = "F:\\1\\";
		
		String[] _dirPathArr = null;
		
		// 파일 경로를 찾는다.
		// 파일 경로가 없으면 null를 리턴한다.
		if (index != null) {
			_dirPathArr = index.split("_");
			
			for (String str : _dirPathArr) {
				marketPriceDirPath += str + "\\";
			}
		}
		
		System.out.println("## dirPath ==> " + marketPriceDirPath);
		
		List<MarketPriceVO> rtnMarketPriceList = new ArrayList<MarketPriceVO>();
		List<String> fileList = FileUtil.getXmlFileNameList(marketPriceDirPath);
		
		// 조건에 맞는 파일만 필터링을 한다.
		// 조건 : 검색 년월에 12개이전 데이터
		if (fileList != null) {
			// 파일을 읽어 vo 에 setting
			String _tmpFileStr;
			MarketPriceVO _marketPriceVO;
			
			for (String vo : fileList) {
					_tmpFileStr = null;
					_marketPriceVO = null;
					
					try {
						_tmpFileStr = FileUtil.readFileToString(marketPriceDirPath+vo);
						
//						System.out.println("### " + _tmpFileStr);
						
						JSONObject _jsonObj = XML.toJSONObject(_tmpFileStr, true);
						
						// JSON TO MAP
						Map<String, Object> map = new HashMap<String, Object>();
						map = (Map<String, Object>) gson.fromJson(_jsonObj.toString(), map.getClass());
						
						Map<String, Object> dong = (Map<String, Object>) map.get("PREDICTVALUE");
						//_marketPriceVO = gson.fromJson(_jsonObj.toString(), MarketPriceVO.class);
						//System.out.println("## dong ==> " + dong);
						
						Set<String> dongKeys = dong.keySet();
						
						Map<String, Object> ho = new HashMap<String, Object>();
						
						for (String dongKey : dongKeys) {
							// System.out.println("## dongKey ==> " + dongKey);
							
							ho = (Map<String, Object>) dong.get(dongKey);
							
							Set<String> hoKeys = ho.keySet();
							
							Map<String, Object> data = new HashMap<String, Object>();
							for (String hoKey : hoKeys) {
								_marketPriceVO = new MarketPriceVO();
								
								// System.out.println("## hoKeys ==> " + hoKey);
								
								data = (Map<String, Object>) ho.get(hoKey);
								
								_marketPriceVO.setBidName(params.get("bidName").toString());
								_marketPriceVO.setDate(vo.split("_")[0]);
								
								_marketPriceVO.setSidogus_ind(_dirPathArr[0]);
								_marketPriceVO.setDongs_ind(_dirPathArr[1]);
								_marketPriceVO.setBunji1(_dirPathArr[2]);
								_marketPriceVO.setBunji2(_dirPathArr[3]);
								
								_marketPriceVO.setDong(dongKey.split("_")[1]);
								_marketPriceVO.setHo(hoKey.split("_")[1]);
								_marketPriceVO.setCenterValue(data.get("CENTERVALUE").toString());
								_marketPriceVO.setSang(data.get("SANG").toString());
								_marketPriceVO.setHa(data.get("HA").toString());
								_marketPriceVO.setArea(data.get("AREA").toString());
								_marketPriceVO.setScount(data.get("SCOUNT").toString());
								
								rtnMarketPriceList.add(_marketPriceVO);
							}
							
						}
						
					} catch (IOException | JSONException e) {
						e.printStackTrace();
					}
			}
		} else {
			rtnMarketPriceList = null;
		}
		
		return rtnMarketPriceList;
	}
	
}
