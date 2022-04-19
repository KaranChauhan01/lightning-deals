package com.deals.lightningdeals.config;

import com.deals.lightningdeals.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class UserConfig {

  @Bean
  public List<User> getActiveUsers() {
    User user1 = new User(1, "User1");
    User user2 = new User(2, "User2");
    User user3 = new User(3, "User3");
    List<User> users = new ArrayList<>();
    users.add(user1);
    users.add(user2);
    users.add(user3);
    return users;
  }
}
