package com.deals.lightningdeals.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Deal {
  int id = -1;
  long startTime;
  float price = 0;
  int quantity = 0;
  float duration = 0;
}
