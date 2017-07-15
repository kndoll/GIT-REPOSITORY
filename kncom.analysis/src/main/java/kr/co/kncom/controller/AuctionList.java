package kr.co.kncom.controller;

import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import kr.co.kncom.service.AuctionService;

@Controller
public class AuctionList {

	AuctionService auctionService = new AuctionService();
	Gson gson = new Gson();
	
	@RequestMapping("/auctionAnalysis")
	public String auctionAnalysis(Model model, @RequestParam(value = "bidDate", defaultValue = "99.09.09") String bidDate) {
		
		System.out.println("## B I D - D A T E ==> " + bidDate);
		model.addAttribute("totalCnt", auctionService.getAuctionCnt(bidDate));
		model.addAttribute("auctionList", auctionService.getAuctionList(bidDate));
		
		return "auctionAnalysis";
	}
	
	@RequestMapping(value = "/auctionMarketPrice", method = RequestMethod.GET)
	public @ResponseBody String auctionMarketPrice(Model model, @RequestParam HashMap<String, String> params) {
		
		return auctionService.getMarketPriceList(params);
	}

}