package edu.postech.csed332.homework3;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

/**
 * An implementation of Tree that delegates to a given instance of Graph. This class
 * is a wrapper of a MutableGraph instance that enforces the Tree invariant.
 * NOTE: you should NOT add more member variables to this class.
 *
 * @param <N> type of vertices, which must be immutable and comparable
 */
public class DelegateTree<N extends Comparable<N>> implements MutableTree<N> {

    /**
     * The underlying graph of this tree
     */
    private final @NotNull MutableGraph<N> delegate;
    /**
     * A map assigning a depth to each vertex in this tree
     */
    private final @NotNull Map<N, Integer> depthMap;
    /**
     * A root vertex of this tree; {@code null} for an empty tree.
     */
    private @Nullable N root;

    /**
     * Creates an empty tree that delegates to a given graph.
     *
     * @param emptyGraph an empty graph
     * @throws IllegalArgumentException if {@code emptyGraph} is not empty
     */
    public DelegateTree(@NotNull MutableGraph<N> emptyGraph) {
        if (!emptyGraph.getVertices().isEmpty())
            throw new IllegalArgumentException();

        delegate = emptyGraph;
        depthMap = new HashMap<>();
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
        return depthMap.get(vertex);
    }

    @Override
    public int getHeight() {
        if (getRoot().isEmpty()) throw new IllegalStateException("Tree has no root.");
        return Collections.max(depthMap.values());
    }

    @Override
    public boolean containsVertex(@NotNull N vertex) {
        return depthMap.containsKey(vertex);
    }

    @Override
    public boolean addVertex(@NotNull N vertex) {
        if (getRoot().isPresent()) return false;
        delegate.addVertex(vertex);
        root = vertex;
        depthMap.put(vertex, 0);
        return true;
    }

    @Override
    public boolean removeVertex(@NotNull N vertex) {
        if (!containsVertex(vertex)) return false;
        Set<N> removed = new HashSet<>();
        removed.add(vertex);
        delegate.getVertices().forEach(v -> {
            if (getParent(v).isPresent() && removed.contains(getParent(v).get()))
                removed.add(v);
        });
        removed.forEach(delegate::removeVertex);
        removed.forEach(depthMap::remove);
        if (vertex.equals(root))
            root = null;
        return true;
    }

    @Override
    public boolean containsEdge(@NotNull N source, @NotNull N target) {
        return delegate.containsEdge(source, target);
    }

    @Override
    public boolean addEdge(@NotNull N source, @NotNull N target) {
        if (!containsVertex(source) || containsVertex(target)) return false;
        delegate.addEdge(source, target);
        depthMap.put(target, depthMap.get(source) + 1);
        return true;
    }

    @Override
    public boolean removeEdge(@NotNull N source, @NotNull N target) {
        if (!containsVertex(source)) return false;
        return removeVertex(target);
    }

    @Override
    public @NotNull Set<N> getSources(N target) {
        return delegate.getSources(target);
    }

    @Override
    public @NotNull Set<N> getTargets(N source) {
        return delegate.getTargets(source);
    }

    @Override
    public @NotNull Set<N> getVertices() {
        return depthMap.keySet();
    }

    @Override
    public @NotNull Set<Edge<N>> getEdges() {
        return delegate.getEdges();
    }

    /**
     * Checks if all class invariants hold for this object
     *
     * @return true if the representation of this tree is valid
     */
    boolean checkInv() {
        return depthMap.keySet().stream().allMatch(this::containsVertex);
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
}
