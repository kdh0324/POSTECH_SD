package edu.postech.csed332.homework3;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

/**
 * An implementation of Tree, where each vertex has a reference to its parent node but
 * no references to its children.
 *
 * @param <N> type of vertices, which must be immutable and comparable
 */
public class ParentPointerTree<N extends Comparable<N>> implements MutableTree<N> {

    /**
     * A map assigning to each vertex a pair of a parent reference and a depth. The parent
     * of the root is {@code null}. For example, a tree with four vertices {v1, v2, v3, v4}
     * and three edges {(v1,v2), (v1,v3), (v2,v4)}, where v1 is the root, is represented by
     * the map {v1 |-> (null,0), v2 |-> (v1,1), v3 |-> (v1,1), v4 |-> (v2,2)}.
     */
    private final @NotNull SortedMap<N, Node<N>> nodeMap;
    /**
     * A root vertex of this tree; {@code null} for an empty tree.
     */
    private @Nullable N root;

    /**
     * Creates an empty parent pointer tree.
     */
    public ParentPointerTree() {
        this.root = null;
        this.nodeMap = new TreeMap<>();
    }

    @Override
    public @NotNull Optional<N> getRoot() {
        if (root == null) return Optional.empty();
        return Optional.of(root);
    }

    @Override
    public int getDepth(@NotNull N vertex) {
        if (getRoot().isEmpty()) throw new IllegalStateException("Tree has no root.");
        if (!containsVertex(vertex)) throw new IllegalArgumentException("The vertex in not in tree.");
        return nodeMap.get(vertex).depth;
    }

    @Override
    public int getHeight() {
        if (getRoot().isEmpty()) throw new IllegalStateException("Tree has no root.");
        return nodeMap.get(nodeMap.lastKey()).depth;
    }

    @Override
    public boolean containsVertex(@NotNull N vertex) {
        return nodeMap.containsKey(vertex);
    }

    @Override
    public boolean addVertex(@NotNull N vertex) {
        if (getRoot().isPresent()) return false;
        nodeMap.put(vertex, new Node<>(null, 0));
        root = vertex;
        return true;
    }

    @Override
    public boolean removeVertex(@NotNull N vertex) {
        if (!containsVertex(vertex)) return false;
        Set<N> removed = new HashSet<>();
        removed.add(vertex);
        nodeMap.forEach((key, value) -> {
            if (value.parent != null && removed.contains(value.parent))
                removed.add(key);
        });
        removed.forEach(nodeMap::remove);
        if (vertex.equals(root))
            root = null;
        return true;
    }

    @Override
    public boolean containsEdge(@NotNull N source, @NotNull N target) {
        if (!containsVertex(target)) return false;
        return Objects.equals(nodeMap.get(target).parent, source);
    }

    @Override
    public boolean addEdge(@NotNull N source, @NotNull N target) {
        if (!containsVertex(source) || containsVertex(target)) return false;
        nodeMap.put(target, new Node<>(source, getHeight() + 1));
        return true;
    }

    @Override
    public boolean removeEdge(@NotNull N source, @NotNull N target) {
        if (!containsVertex(source)) return false;
        return removeVertex(target);
    }

    @Override
    public @NotNull Set<N> getSources(N target) {
        if (nodeMap.isEmpty()) return Collections.emptySet();
        return nodeMap.get(target).parent == null ?
                Collections.emptySet() : Collections.singleton(nodeMap.get(target).parent);
    }

    @Override
    public @NotNull Set<N> getTargets(N source) {
        return nodeMap.keySet().stream()
                .filter(v -> Objects.equals(nodeMap.get(v).parent, source))
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public @NotNull Set<N> getVertices() {
        return nodeMap.keySet();
    }

    @Override
    public @NotNull Set<Edge<N>> getEdges() {
        return nodeMap.entrySet().stream()
                .map(entry -> new Edge<>(entry.getValue().parent, entry.getKey()))
                .filter(entry -> entry.getSource() != null)
                .collect(Collectors.toUnmodifiableSet());
    }

    /**
     * Checks if all class invariants hold for this object
     *
     * Class invariant:
     * Each parent of every vertices except root in tree is contained in tree.
     *
     * @return true if the representation of this tree is valid
     */
    boolean checkInv() {
        return nodeMap.values().stream()
                .allMatch(node -> {
                    if (node.parent == null)
                        return true;
                    return containsVertex(node.parent);
                });
    }

    /**
     * Provides a human-readable string representation for the abstract value of the tree
     *
     * @return a string representation
     */
    @Override
    public String toString() {
        return String.format("[root: %s, vertex: {%s}, edge: {%s}]",
                root,
                getVertices().stream().map(N::toString).collect(Collectors.joining(", ")),
                getEdges().stream().map(Edge::toString).collect(Collectors.joining(", ")));
    }

    private static class Node<V> {
        final @Nullable V parent;
        final int depth;

        Node(@Nullable V parent, int depth) {
            this.parent = parent;
            this.depth = depth;
        }
    }
}
