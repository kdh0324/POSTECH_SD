package edu.postech.csed332.homework2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class BookTest {

    @Test
    public void testGetTitle() {
        Book book = new Book("Unit Testing", Arrays.asList("Name 1", "Name 2"));
        Assertions.assertEquals(book.getTitle(), "Unit Testing");

        String str = "{\"title\" : \"UnitTesting\", \"authors\" : [\"Name 1\", \"Name 2\"]}";
        Book jsonBook = new Book(str);
        Assertions.assertEquals(jsonBook.getTitle(), "UnitTesting");
    }

    @Test
    public void testGetAuthors() {
        Book book = new Book("Unit Testing", Arrays.asList("Name 1", "Name 2"));
        Assertions.assertEquals(book.getAuthors(), Arrays.asList("Name 1", "Name 2"));

        String str = "{\"title\" : \"UnitTesting\", \"authors\" : [\"Name 1\", \"Name 2\"]}";
        Book jsonBook = new Book(str);
        Assertions.assertEquals(jsonBook.getAuthors(), Arrays.asList("Name 1", "Name 2"));
    }

    @Test
    public void testGetStringRepresentation() {
        Book book = new Book("Unit Testing", Arrays.asList("Name 1", "Name 2"));
        String str = "{\"title\":\"Unit Testing\",\"authors\":[\"Name 1\",\"Name 2\"]}";
        Assertions.assertEquals(book.getStringRepresentation(), str);
    }

    @Test
    public void testGetContainingCollections() {
        Book book = new Book("Unit Testing", Arrays.asList("Name 1", "Name 2"));
        Collection col1 = new Collection("Test Collection1");
        Collection col1_1 = new Collection("Test Collection1_1");
        Collection col1_2 = new Collection("Test Collection1_2");
        Collection col1_1_1 = new Collection("Test Collection1_1_1");

        col1.addElement(col1_1);
        col1.addElement(col1_2);
        col1_1.addElement(col1_1_1);
        col1_1_1.addElement(book);

        Assertions.assertEquals(book.getContainingCollections(), Arrays.asList(col1_1_1, col1_1, col1));
    }
}
