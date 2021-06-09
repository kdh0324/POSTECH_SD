package edu.postech.csed332.homework2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class CollectionTest {

    @Test
    public void testGetName() {
        Collection col = new Collection("Software");
        Assertions.assertEquals(col.getName(), "Software");
    }

    @Test
    public void testAddElement() {
        Book book = new Book("Unit Testing", Arrays.asList("Name 1", "Name 2"));
        Collection col1 = new Collection("Test Collection1");
        Collection col1_1 = new Collection("Test Collection1_1");
        Collection col1_2 = new Collection("Test Collection1_2");
        Collection col1_1_1 = new Collection("Test Collection1_1_1");

        Assertions.assertTrue(col1.addElement(col1_1));
        Assertions.assertTrue(col1.addElement(col1_2));
        Assertions.assertTrue(col1_1.addElement(col1_1_1));
        Assertions.assertTrue(col1_1_1.addElement(book));
        Assertions.assertFalse(col1_2.addElement(col1_1_1));
    }

    @Test
    public void testGetElements() {
        Book book = new Book("Unit Testing", Arrays.asList("Name 1", "Name 2"));
        Collection col1 = new Collection("Test Collection1");
        Collection col1_1 = new Collection("Test Collection1_1");
        Collection col1_2 = new Collection("Test Collection1_2");
        Collection col1_1_1 = new Collection("Test Collection1_1_1");

        col1.addElement(col1_1);
        col1.addElement(col1_2);
        col1_1.addElement(col1_1_1);
        col1_1_1.addElement(book);

        List<Element> list1 = Arrays.asList(col1_1, col1_2);
        Assertions.assertEquals(col1.getElements(), list1);

        List<Element> list2 = Collections.singletonList(book);
        Assertions.assertEquals(col1_1_1.getElements(), list2);
    }

    @Test
    public void testGetStringRepresentation() {
        Book book = new Book("Unit Testing", Arrays.asList("Name 1", "Name 2"));
        Collection col1 = new Collection("Test Collection1");
        Collection col1_1 = new Collection("Test Collection1_1");
        Collection col1_2 = new Collection("Test Collection1_2");
        Collection col1_1_1 = new Collection("Test Collection1_1_1");

        col1.addElement(col1_1);
        col1.addElement(col1_2);
        col1_1.addElement(col1_1_1);
        col1_1_1.addElement(book);

        String str = "{\"collection\":[{\"name\":\"Test Collection1\"},{\"collection\":[{\"name\":\"Test Collection1_1\"},{\"collection\":[{\"name\":\"Test Collection1_1_1\"},{\"title\":\"Unit Testing\",\"authors\":[\"Name 1\",\"Name 2\"]}]}]},{\"collection\":[{\"name\":\"Test Collection1_2\"}]}]}";
        Assertions.assertEquals(col1.getStringRepresentation(), str);
    }

    @Test
    public void testDeleteElements() {
        Book book = new Book("Unit Testing", Arrays.asList("Name 1", "Name 2"));
        Collection col1 = new Collection("Test Collection1");
        Collection col1_1 = new Collection("Test Collection1_1");
        Collection col1_2 = new Collection("Test Collection1_2");
        Collection col1_1_1 = new Collection("Test Collection1_1_1");

        col1.addElement(col1_1);
        col1.addElement(col1_2);
        col1_1.addElement(col1_1_1);
        col1_1_1.addElement(book);

        Assertions.assertFalse(col1.deleteElement(book));
        Assertions.assertTrue(col1.deleteElement(col1_1));
        Assertions.assertTrue(col1_1_1.deleteElement(book));

        String str1 = "{\"collection\":[{\"name\":\"Test Collection1\"},{\"collection\":[{\"name\":\"Test Collection1_2\"}]}]}";
        Assertions.assertEquals(col1.getStringRepresentation(), str1);

        String str2 = "{\"collection\":[{\"name\":\"Test Collection1_1_1\"}]}";
        Assertions.assertEquals(col1_1_1.getStringRepresentation(), str2);
    }

    @Test
    public void testRestoreCollection() {
        Book book = new Book("Unit Testing", Arrays.asList("Name 1", "Name 2"));
        Collection col1 = new Collection("Test Collection1");
        Collection col1_1 = new Collection("Test Collection1_1");
        Collection col1_2 = new Collection("Test Collection1_2");
        Collection col1_1_1 = new Collection("Test Collection1_1_1");

        col1.addElement(col1_1);
        col1.addElement(col1_2);
        col1.addElement(book);
        col1_1.addElement(col1_1_1);
        col1_1_1.addElement(book);
        String str = col1.getStringRepresentation();

        Assertions.assertEquals(str, Collection.restoreCollection(str).getStringRepresentation());
    }

    @Test
    public void testGetBooksByAuthor() {
        Book book1 = new Book("Unit Testing1", Arrays.asList("Name 1", "Name 2"));
        Book book2 = new Book("Unit Testing2", Collections.singletonList("Name 1"));
        Book book3 = new Book("Unit Testing3", Collections.singletonList("Name 2"));
        Collection col1 = new Collection("Test Collection1");
        Collection col1_1 = new Collection("Test Collection1_1");
        Collection col1_2 = new Collection("Test Collection1_2");
        Collection col1_1_1 = new Collection("Test Collection1_1_1");

        col1.addElement(col1_1);
        col1.addElement(col1_2);
        col1_1.addElement(col1_1_1);
        col1_1_1.addElement(book1);
        col1_1_1.addElement(book3);
        col1_2.addElement(book2);

        Set<String> name1 = new HashSet<>();
        name1.add("{\"title\":\"Unit Testing1\",\"authors\":[\"Name 1\",\"Name 2\"]}");
        name1.add("{\"title\":\"Unit Testing2\",\"authors\":[\"Name 1\"]}");
        Set<String> name2 = new HashSet<>();
        name2.add("{\"title\":\"Unit Testing1\",\"authors\":[\"Name 1\",\"Name 2\"]}");
        name2.add("{\"title\":\"Unit Testing3\",\"authors\":[\"Name 2\"]}");

        for (Book book : col1.getBooksByAuthor("Name 1"))
            Assertions.assertTrue(name1.contains(book.getStringRepresentation()));
        for (Book book : col1.getBooksByAuthor("Name 2"))
            Assertions.assertTrue(name2.contains(book.getStringRepresentation()));
    }

    @Test
    public void testGetBooksByCollection() {
        Book book1 = new Book("Unit Testing1", Arrays.asList("Name 1", "Name 2"));
        Book book2 = new Book("Unit Testing2", Collections.singletonList("Name 1"));
        Book book3 = new Book("Unit Testing3", Collections.singletonList("Name 2"));
        Collection col1 = new Collection("Test Collection1");
        Collection col1_1 = new Collection("Test Collection1_1");
        Collection col1_2 = new Collection("Test Collection1_2");
        Collection col1_1_1 = new Collection("Test Collection1_1_1");

        col1.addElement(col1_1);
        col1.addElement(col1_2);
        col1_1.addElement(col1_1_1);
        col1_1_1.addElement(book1);
        col1_1_1.addElement(book3);
        col1_2.addElement(book2);

        Set<String> collection1 = new HashSet<>();
        collection1.add("{\"title\":\"Unit Testing1\",\"authors\":[\"Name 1\",\"Name 2\"]}");
        collection1.add("{\"title\":\"Unit Testing3\",\"authors\":[\"Name 2\"]}");
        Set<String> collection2 = new HashSet<>();
        collection2.add("{\"title\":\"Unit Testing2\",\"authors\":[\"Name 1\"]}");

        for (Book book : col1.getBooksByCollection("Test Collection1_1", false))
            Assertions.assertTrue(collection1.contains(book.getStringRepresentation()));
        for (Book book : col1.getBooksByCollection("Test Collection1_2", false))
            Assertions.assertTrue(collection2.contains(book.getStringRepresentation()));
    }
}
