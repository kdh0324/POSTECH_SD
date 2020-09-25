package edu.postech.csed332.homework2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Set;

public class LibraryTest {

    @Test
    public void testfindBooksNull() {
        Library lib = new Library();
        Assertions.assertNull(lib.findBooks("any"));
    }

    @Test
    public void testFindBooksByAuthorsNull() {
        Library lib = new Library();
        Assertions.assertNull(lib.findBooksByAuthor("any"));
    }

    @Test
    public void testLibraryFromFile() {
        Library lib = new Library("library.txt");
        Book book = new Book("Unit Testing", Arrays.asList("Name 1", "Name 2"));
        Set<Book> bookSet = lib.findBooks("Test Collection1");

        Assertions.assertNotNull(bookSet);
        Assertions.assertEquals(((Book) bookSet.toArray()[0]).getStringRepresentation(), book.getStringRepresentation());

        Collection col1 = new Collection("Test Collection1");
        Collection col1_1 = new Collection("Test Collection1_1");
        Collection col1_2 = new Collection("Test Collection1_2");
        Collection col1_1_1 = new Collection("Test Collection1_1_1");

        Assertions.assertTrue(col1.addElement(col1_1));
        Assertions.assertTrue(col1.addElement(col1_2));
        Assertions.assertTrue(col1_1.addElement(col1_1_1));
        Assertions.assertTrue(col1_1_1.addElement(book));

        Assertions.assertEquals(lib.getCollections().get(0).getStringRepresentation(), col1.getStringRepresentation());
    }

    @Test
    public void testSaveLibraryToFile() throws IOException {
        Library lib = new Library("library.txt");
        lib.saveLibraryToFile("library_copy.txt");

        String _text = new String(Files.readAllBytes(Paths.get("library.txt")));
        String _text_copy = new String(Files.readAllBytes(Paths.get("library_copy.txt")));
        String text = _text.replaceAll("\\s+", "");
        String text_copy = _text_copy.replaceAll("\\s+", "");

        Assertions.assertEquals(text, text_copy);
    }
}
