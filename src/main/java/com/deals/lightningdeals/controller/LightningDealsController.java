package com.deals.lightningdeals.controller;

import com.deals.lightningdeals.model.Deal;
import com.deals.lightningdeals.service.LightningDealsService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/deals")
@AllArgsConstructor
//@NoArgsConstructor
public class LightningDealsController {

  private LightningDealsService dealsService;

  @PostMapping("/create")
  String createDeal(@RequestBody Deal deal) {
    Deal newDeal =  dealsService.createDeal(deal);
    return "{\"status\":\"success\", \"dealId\":\""+ newDeal.getId() +"\"}";
  }

  @PostMapping("/end")
  String endDeal(@RequestParam("dealId") int dealId) {
    int deletedDealId =  dealsService.deleteDeal(dealId).block();
    return "{\"status\":\"success\", \"dealId\":\""+ deletedDealId +"\"}";
  }

  //Revisit
  @PostMapping("/update")
  String updateDeal(@RequestParam("dealId") int dealId, @RequestParam("numberOfItems") int numberOfItems, @RequestParam("endTime") float endTime) {
    int uodatedDealId =  dealsService.updateDeal(dealId, numberOfItems, endTime).block();
    return "{\"status\":\"success\", \"dealId\":\""+ uodatedDealId +"\"}";
  }

  @PostMapping("/claim")
  String claimDeal(@RequestParam("dealId") int dealId, @RequestParam("userId") int userId) {
    int newDeal =  dealsService.claimDeal(dealId, userId).block();
    return "{\"status\":\"success\", \"dealId\":\""+ newDeal +"\"}";
  }

}
