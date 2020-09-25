package edu.postech.csed332.homework2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.List;

/**
 * A book contains the title and the author(s), each of which is
 * represented as a string. A book can be exported to and import
 * from a string representation in JSON (https://www.json.org/).
 * Using the string, you should be able to construct a book object.
 */
public final class Book extends Element {
    private String title;
    private List<String> authors;

    /**
     * Builds a book with the given title and author(s).
     *
     * @param title   the title of the book
     * @param authors the author(s) of the book
     */
    public Book(String title, List<String> authors) {
        this.title = title;
        this.authors = authors;
        setParentCollection(null);
    }

    /**
     * Builds a book from the string representation in JSON, which
     * contains the title and author(s) of the book.
     *
     * @param stringRepr the JSON string representation
     */
    public Book(String stringRepr) {
        try {
            JSONObject jo = new JSONObject(stringRepr);
            title = jo.getString(JsonKey.TITLE);
            JSONArray authors = jo.getJSONArray(JsonKey.AUTHORS);
            this.authors = new ArrayList<>();
            for (int i = 0; i < authors.length(); i++)
                this.authors.add(authors.getString(i));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the JSON string representation of this book. The string
     * representation contains the title and author(s) of the book.
     *
     * @return the string representation
     */
    public String getStringRepresentation() {
        JSONStringer stringer = new JSONStringer();
        return stringer
                .object()
                    .key(JsonKey.TITLE).value(title)
                    .key(JsonKey.AUTHORS).value(authors)
                .endObject().toString();
    }

    /**
     * Returns all the collections that this book belongs to directly
     * and indirectly, starting from the bottom-level collection.
     * <p>
     * For example, suppose that "Computer Science" is a top collection,
     * "Operating Systems" is a collection under "Computer Science", and
     * "Linux Kernel" is a book under "Operating System". Then, this
     * method for "The Linux Kernel" returns the list of the collections
     * (Operating System, Computer Science), starting from the bottom.
     *
     * @return the list of collections
     */
    public List<Collection> getContainingCollections() {
        List<Collection> collections = new ArrayList<>();
        Collection element = getParentCollection();
        while (element != null) {
            collections.add(element);
            element = element.getParentCollection();
        }
        return collections;
    }

    /**
     * Returns the title of the book.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the author(s) of the book.
     *
     * @return the author(s)
     */
    public List<String> getAuthors() {
        return authors;
    }
}