package com.deals.lightningdeals.repository;

import com.deals.lightningdeals.model.Deal;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

//@Component
//@AllArgsConstructor
//@NoArgsConstructor
public class MongoRepo {

  private Map<Integer, Deal> activeDeals = new HashMap<>();
  private Map<Integer, Integer> dealsQuantitiy = new HashMap<>();
  private Map<Integer, Set<Integer>> dealUsers = new HashMap<>();

  private int dealIdCounter = 0;


  public Map<Integer, Deal> getActiveDeals() {
    return activeDeals;
  }

  public Deal addActiveDeal(Deal deal) {
    if(deal.getId() == -1)
      deal.setId(++dealIdCounter);
    activeDeals.put(deal.getId(), deal);
    return deal;
  }

  public Mono<Integer> deleteDeal(int dealId) {
    activeDeals.remove(dealId);
    return Mono.just(dealId);
  }

  public int getDealQuantityCount(int dealId) {
    return dealsQuantitiy.get(dealId);
  }

  public boolean idNewDealUser(int dealId, int userId) {
    return dealUsers.getOrDefault(dealId, new HashSet<>()).contains(userId);
  }

  public int claimDeal(int dealId, int userId) {
    dealsQuantitiy.put(dealId, dealsQuantitiy.get(dealId) - 1);
    Set<Integer> userSet = dealUsers.getOrDefault(dealId, new HashSet<>());
    userSet.add(userId);
    dealUsers.put(dealId, userSet);
    return dealId;
  }

  public int updateDeal(Deal origDeal) {
    activeDeals.put(origDeal.getId(), origDeal);
    return origDeal.getId();
  }
}
