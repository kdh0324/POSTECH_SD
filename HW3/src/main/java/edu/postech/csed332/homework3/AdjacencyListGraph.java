package edu.postech.csed332.homework3;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

/**
 * An implementation of Graph with an adjacency list representation.
 * NOTE: you should NOT add more member variables to this class.
 *
 * @param <N> type of vertices, which must be immutable and comparable
 */
public class AdjacencyListGraph<N extends Comparable<N>> implements MutableGraph<N> {

    /**
     * A map from vertices to the sets of their adjacent vertices. For example, a graph
     * with four vertices {v1, v2, v3, v4} and four edges {(v1,v1), (v1,v2), (v3,v1), (v3,v2)}
     * is represented by the map {v1 |-> {v1,v2}, v2 |-> {}, v3 -> {v1,v2}, v4 -> {}}.
     */
    private final @NotNull SortedMap<N, SortedSet<N>> adjMap;

    /**
     * Creates an empty graph
     */
    public AdjacencyListGraph() {
        adjMap = new TreeMap<>();
    }

    @Override
    public boolean containsVertex(@NotNull N vertex) {
        return adjMap.containsKey(vertex);
    }

    @Override
    public boolean addVertex(@NotNull N vertex) {
        if (containsVertex(vertex)) return false;
        adjMap.put(vertex, new TreeSet<>());
        return true;
    }

    @Override
    public boolean removeVertex(@NotNull N vertex) {
        if (!containsVertex(vertex)) return false;
        adjMap.values().forEach(targets -> targets.remove(vertex));
        adjMap.remove(vertex);
        return true;
    }

    @Override
    public boolean containsEdge(@NotNull N source, @NotNull N target) {
        if (!containsVertex(source) || !containsVertex(target)) return false;
        return adjMap.get(source).contains(target);
    }

    @Override
    public boolean addEdge(@NotNull N source, @NotNull N target) {
        if (containsEdge(source, target)) return false;
        if (containsVertex(source))
            adjMap.get(source).add(target);
        else
            adjMap.put(source, new TreeSet<>(Collections.singleton(target)));
        if (!containsVertex(target)) addVertex(target);
        return true;
    }

    @Override
    public boolean removeEdge(@NotNull N source, @NotNull N target) {
        return adjMap.get(source).remove(target);
    }

    @Override
    public @NotNull Set<N> getSources(N target) {
        return adjMap.keySet().stream()
                .filter(key -> adjMap.get(key).contains(target)).collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public @NotNull Set<N> getTargets(N source) {
        if (adjMap.isEmpty()) return Collections.emptySet();
        return Collections.unmodifiableSet(adjMap.get(source));
    }

    @Override
    public @NotNull Set<N> getVertices() {
        return Collections.unmodifiableSet(adjMap.keySet());
    }

    @Override
    public @NotNull Set<Edge<N>> getEdges() {
        return adjMap.entrySet().stream()
                .flatMap(entry -> entry.getValue().stream().map(n -> new Edge<>(entry.getKey(), n)))
                .collect(Collectors.toUnmodifiableSet());
    }

    /**
     * Checks if all class invariants hold for this object.
     *
     * Class invariant:
     * Every targets of every vertices are contained in graph.
     *
     * @return true if the representation of this graph is valid
     */
    boolean checkInv() {
        return adjMap.values().stream()
                .allMatch(targets -> targets.stream().allMatch(this::containsVertex));
    }

    /**
     * Provides a human-readable string representation for the abstract value of the graph
     *
     * @return a string representation
     */
    @Override
    public String toString() {
        return String.format("[vertex: {%s}, edge: {%s}]",
                getVertices().stream().map(N::toString).collect(Collectors.joining(", ")),
                getEdges().stream().map(Edge::toString).collect(Collectors.joining(", ")));
    }
}
