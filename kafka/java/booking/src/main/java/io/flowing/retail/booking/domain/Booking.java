package io.flowing.retail.booking.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Booking {
  
  private String orderId = "checkout-generated-" + UUID.randomUUID().toString();
  private Customer customer;
  private List<Item> items = new ArrayList<>();
  
  public void addItem(String articleId, int amount) {
    // keep only one item, but increase amount accordingly
    Item existingItem = removeItem(articleId);
    if (existingItem!=null) {
      amount += existingItem.getAmount();
    }
    
    Item item = new Item();
    item.setAmount(amount);
    item.setArticleId(articleId);
    items.add(item);    
  }

  public Item removeItem(String articleId) {
    for (Item item : items) {
      if (articleId.equals(item.getArticleId())) {
        items.remove(item);
        return item;
      }
    }
    return null;
  }

  @Override
  public String toString() {
    return "Order [orderId=" + orderId + ", items=" + items + "]";
  }


  public void setCustomer(Customer customer) {
    this.customer = customer;
  }
}
