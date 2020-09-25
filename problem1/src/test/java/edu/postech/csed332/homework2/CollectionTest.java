package edu.postech.csed332.homework2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CollectionTest {

    @Test
    public void testGetName() {
        Collection col = new Collection("Software");
        Assertions.assertEquals(col.getName(), "Software");
    }

    @Test
    public void testAddElementGetElements() {
        Book book = new Book("Unit Testing", Arrays.asList("Name 1", "Name 2"));
        Collection col1 = new Collection("Test Collection1");
        Collection col1_1 = new Collection("Test Collection1_1");
        Collection col1_2 = new Collection("Test Collection1_2");
        Collection col1_1_1 = new Collection("Test Collection1_1_1");

        Assertions.assertTrue(col1.addElement(col1_1));
        Assertions.assertTrue(col1.addElement(col1_2));
        Assertions.assertTrue(col1_1.addElement(col1_1_1));
        Assertions.assertTrue(col1_1_1.addElement(book));

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
        col1_1.addElement(col1_1_1);
        col1_1_1.addElement(book);
        String str = col1.getStringRepresentation();

        Assertions.assertEquals(str, Collection.restoreCollection(str).getStringRepresentation());
    }
}
