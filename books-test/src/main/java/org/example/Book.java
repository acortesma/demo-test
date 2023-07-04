package org.example;

import lombok.Data;

@Data
public class Book {

  private String id;
  private String title;
  private Integer pages;
  private String summary;
  private Long publicationTimestamp;
  private Author author;
}
