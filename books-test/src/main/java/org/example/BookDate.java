package org.example;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BookDate {

  private Book book;
  private String date;

}
