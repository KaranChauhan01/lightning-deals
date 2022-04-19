package com.deals.lightningdeals.service;

import com.deals.lightningdeals.model.Deal;
import com.deals.lightningdeals.repository.MongoRepo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class LightningDealsService {

  private MongoRepo repository = new MongoRepo();

  //Revisit
  public Deal createDeal(Deal deal) {
    if(repository.getActiveDeals().containsKey(deal.getId()))
      return null;
    else {
      deal.setStartTime(System.currentTimeMillis());
      Deal activeDeal = repository.addActiveDeal(deal);
      return activeDeal;
    }
  }

  public Mono<Integer> deleteDeal(int dealId) {
    if(repository.getActiveDeals().containsKey(dealId))
      return repository.deleteDeal(dealId);
    else {
      return null;
    }
  }

  public Mono<Integer> updateDeal(int dealId, int numberOfItems, float endTime) {
    if(repository.getActiveDeals().containsKey(dealId)) {
      // Revisit - the api structure. Should id be request param?
      Deal origDeal = repository.getActiveDeals().get(dealId);
      if(numberOfItems == -1 && endTime == -1)
        return null;
      else {
        if(endTime != -1 && (origDeal.getStartTime() + endTime * 3600 * 1000 > System.currentTimeMillis())) {
          origDeal.setDuration(endTime);
        }
        if(numberOfItems != -1)
          origDeal.setQuantity(numberOfItems);
        return Mono.just(repository.updateDeal(origDeal));
      }
    }
    return null;
  }

  public Mono<Integer> claimDeal(int dealId, int userId) {
    //Validate user
    if(repository.getActiveDeals().containsKey(dealId)) {
      Deal deal = repository.getActiveDeals().get(dealId);
      long dealDuration = (long) (deal.getStartTime() + (deal.getDuration() * 3600 * 1000));
      if(System.currentTimeMillis() < dealDuration && repository.getDealQuantityCount(dealId) < deal.getQuantity() && repository.idNewDealUser(dealId, userId)) {
        return Mono.just(repository.claimDeal(dealId, userId));
      } else
        return null;
    } else
      return null;
  }
}
