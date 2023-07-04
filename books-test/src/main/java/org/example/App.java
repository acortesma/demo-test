package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Predicate;

public class App {

  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy", Locale.ENGLISH)
    .withZone(ZoneId.systemDefault());

  private static final Scanner scanner = new Scanner(System.in);

  public static void main(String[] args) {

    System.out.println("Inserta un parámetro de filtrado de los libros");
    String input = scanner.nextLine();

    List<Book> books = extractedBooksFromClasspathJsonFile("books.json");

    Optional<BookDate> response = filter(input, books);

    response.ifPresent(System.out::println);
  }

  private static List<Book> extractedBooksFromClasspathJsonFile(String classPathFile) {

    ClassLoader classLoader = App.class.getClassLoader();
    InputStream inputStream = classLoader.getResourceAsStream(classPathFile);

    try {
      ObjectMapper objectMapper = new ObjectMapper();
      return objectMapper.readValue(inputStream, new TypeReference<>() {
      });

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private static Optional<BookDate> filter(String filter, List<Book> books) {

    books.stream()
      .filter(book -> book.getPublicationTimestamp() == null)
      .forEach(System.out::println);

    return books.stream()
      .filter(filterByNameOrSummaryOrBio(filter))
      .max(Comparator.nullsLast(Comparator.comparingLong(Book::getPublicationTimestamp)))
      .map(App::mapperBookToBookDate);
  }

  private static Predicate<Book> filterByNameOrSummaryOrBio(String filter) {
    return book ->
      Optional.ofNullable(book.getTitle()).map(a -> a.contains(filter)).orElse(true) ||
        Optional.ofNullable(book.getSummary()).map(a -> a.contains(filter)).orElse(true) ||
        Optional.of(book.getAuthor()).map(Author::getBio).map(a -> a.contains(filter)).orElse(true);
  }

  private static BookDate mapperBookToBookDate(Book book) {
    String dateFormated = Optional.ofNullable(book.getPublicationTimestamp())
      .map(Instant::ofEpochMilli)
      .map(App.formatter::format)
      .orElse(null);

    return BookDate.builder().book(book).date(dateFormated).build();
  }


}
