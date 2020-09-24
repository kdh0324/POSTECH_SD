package edu.postech.csed332.homework2;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A container class for all collections (that eventually contain all
 * books). A library can be exported to or imported from a JSON file.
 */
public final class Library {
    private final List<Collection> collections;

    /**
     * Builds a new, empty library.
     */
    public Library() {
        collections = new ArrayList<>();
    }

    /**
     * Builds a new library and restores its contents from a file.
     *
     * @param fileName the file from where to restore the library.
     */
    public Library(String fileName) {
        collections = new ArrayList<>();

        try {
            String text = new String(Files.readAllBytes(Paths.get(fileName)));
            JSONArray jsonArray = new JSONArray(text);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                JSONArray collectionArray = jsonObject.getJSONArray(JsonKey.COLLECTION);
                Collection collection = new Collection(collectionArray, null);
                collections.add(collection);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves the contents of the library to the given file.
     *
     * @param fileName the file where to save the library
     */
    public void saveLibraryToFile(String fileName) {
        JSONStringer stringer = new JSONStringer();
        stringer.array();
        for (Collection collection : collections)
            stringer
                .object()
                    .key(JsonKey.COLLECTION)
                    .value(new JSONArray(collection.getStringRepresentation()))
                .endObject();
        stringer.endArray();

        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write(stringer.toString());
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the set of all books that belong to the collections
     * of a given name. Note that different collections may have the
     * same name. Return null if there is no collection of the
     * given name, and the empty set of there are such collections but
     * all of them are empty. For example, suppose that
     * - "Computer Science" is a top collection.
     * - "Operating Systems" is a collection under "Computer Science".
     * - "Linux Kernel" is a book under "Operating System".
     * - "Software Engineering" is a collection under "Computer Science".
     * - "Software Design Methods" is a bool under "Software Engineering".
     * Then, the findBooks method for "Computer Science" returns the set
     * of two books "Linux Kernel" and "Software Design Methods".
     *
     * @param collection a collection name
     * @return a set of books
     */
    public Set<Book> findBooks(String collection) {
        Set<Book> bookSet = new HashSet<>();
        for (Collection col : collections)
            bookSet.addAll(col.getBooksByCollection(collection));
        if (bookSet.size() == 0)
            return null;
        return bookSet;
    }

    /**
     * Return the set of all books written by a given author in this
     * collection (including the sub-collections). Return the empty
     * set if there is no book written by the author. Note that a book
     * may involve multiple authors.
     *
     * @param author an author
     * @return Return the set of books written by the given author
     */
    public Set<Book> findBooksByAuthor(String author) {
        Set<Book> bookSet = new HashSet<>();
        for (Collection col : collections)
            bookSet.addAll(col.getBooksByAuthor(author));
        return bookSet;
    }

    /**
     * Returns the collections contained in the library.
     *
     * @return library contained elements
     */
    public List<Collection> getCollections() {
        return collections;
    }
}
