package edu.postech.csed332.homework2;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The Collection class represents a single collection, which contains
 * a name of the collection and also has a list of references to every
 * book and every subcollection in the collection. A collection can
 * also be exported to and imported from a JSON string representation.
 */
public final class Collection extends Element {
    private final List<Element> elements;
    private final String name;

    /**
     * Creates a new collection with the given name.
     *
     * @param name the name of the collection
     */
    public Collection(String name) {
        this.name = name;
        elements = new ArrayList<>();
        setParentCollection(null);
    }

    /**
     * Restores a collection from its string representation in JSON
     *
     * @param stringRepr the string representation
     */
    public static Collection restoreCollection(String stringRepr) {
        JSONObject jo = new JSONObject(stringRepr);

        String name = jo.getString(JsonKey.NAME);
        Collection collection = new Collection(name);
        JSONArray elements = jo.getJSONArray(JsonKey.COLLECTION);

        for (int i = 0; i < elements.length(); i++) {
            JSONObject jsonObject = elements.getJSONObject(i);
            if (jsonObject.has(JsonKey.TITLE))
                collection.addElement(new Book(jsonObject.toString()));
            else
                collection.addElement(restoreCollection(jsonObject.toString()));
        }
        return collection;
    }

    /**
     * Returns the JSON string representation of this collection, which
     * contains the name of this collection and all elements (books and
     * subcollections) contained in the collection.
     *
     * @return string representation of this collection
     */
    public String getStringRepresentation() {
        JSONStringer stringer = new JSONStringer();
        JSONArray array = new JSONArray();
        for (Element element : elements) {
            if (element instanceof Book)
                array.put(new JSONObject(((Book) element).getStringRepresentation()));
            else
                array.put(new JSONObject(((Collection) element).getStringRepresentation()));
        }

        return stringer
                .array()
                    .key(JsonKey.NAME)
                    .value(name)
                    .key(JsonKey.COLLECTION)
                    .value(array)
                .endArray().toString();
    }

    public Set<Book> getBooksByCollection(String collection) {
        Set<Book> bookSet = new HashSet<>();
        boolean flag = false;
        if (name.equals(collection))
            flag = true;
        for (Element element : elements) {
            if (flag && element instanceof Book)
                bookSet.add((Book) element);
            if (element instanceof Collection)
                bookSet.addAll(((Collection) element).getBooksByCollection(collection));
        }
        return bookSet;
    }

    public Set<Book> getBooksByAuthor(String author) {
        Set<Book> bookSet = new HashSet<>();
        for (Element element : elements) {
            if (element instanceof Book && ((Book) element).getAuthors().contains(author))
                bookSet.add((Book) element);
            if (element instanceof Collection)
                bookSet.addAll(((Collection) element).getBooksByAuthor(author));
        }
        return bookSet;
    }

    /**
     * Adds an element to this collection, if the element has no parent
     * collection yet. Otherwise, this method returns false, without
     * actually adding the element to this collection.
     *
     * @param element the element to add
     * @return true on success, false on fail
     */
    public boolean addElement(Element element) {
        try {
            element.setParentCollection(this);
            elements.add(element);
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

    /**
     * Deletes an element from the collection. Returns false if the
     * collection does not have this element. Hint: do not forget to
     * clear parentCollection of given element.
     *
     * @param element the element to remove
     * @return true on success, false on fail
     */
    public boolean deleteElement(Element element) {
        try {
            element.setParentCollection(null);
            elements.remove(element);
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

    /**
     * Returns the name of the collection.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the list of elements.
     *
     * @return the list of elements
     */
    public List<Element> getElements() {
        return elements;
    }
}
