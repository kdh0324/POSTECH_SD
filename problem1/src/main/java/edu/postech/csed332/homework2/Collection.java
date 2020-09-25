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

    public Collection(JSONArray jsonArray) {
        name = jsonArray.getJSONObject(0).getString(JsonKey.NAME);
        elements = new ArrayList<>();
        for (int i = 1; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (jsonObject.has(JsonKey.COLLECTION)) {
                Collection collection = new Collection(jsonObject.getJSONArray(JsonKey.COLLECTION));
                collection.setParentCollection(this);
                elements.add(collection);
            }
            else {
                Book book = new Book(jsonObject.toString());
                book.setParentCollection(this);
                elements.add(book);
            }
        }
    }

    /**
     * Restores a collection from its string representation in JSON
     *
     * @param stringRepr the string representation
     */
    public static Collection restoreCollection(String stringRepr) {
        JSONObject jo = new JSONObject(stringRepr);
        JSONArray jsonArray = jo.getJSONArray(JsonKey.COLLECTION);

        String name = jsonArray.getJSONObject(0).getString(JsonKey.NAME);
        Collection collection = new Collection(name);

        for (int i = 1; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (jsonObject.has(JsonKey.COLLECTION)) {
                Collection subCollection = new Collection(jsonObject.getJSONArray(JsonKey.COLLECTION));
                subCollection.setParentCollection(collection);
                collection.addElement(subCollection);
            }
            else {
                Book book = new Book(jsonObject.toString());
                book.setParentCollection(collection);
                collection.addElement(book);
            }
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
        JSONArray array = new JSONArray();
        JSONObject object = new JSONObject();
        object.put(JsonKey.NAME, name);
        array.put(object);

        for (Element element : elements) {
            if (element instanceof Book)
                array.put(new JSONObject(((Book) element).getStringRepresentation()));
            else
                array.put(new JSONObject(((Collection) element).getStringRepresentation()));
        }

        JSONStringer stringer = new JSONStringer();
        return stringer
                .object()
                    .key(JsonKey.COLLECTION)
                    .value(array)
                .endObject().toString();
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
        element.setParentCollection(null);
        return elements.remove(element);
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
