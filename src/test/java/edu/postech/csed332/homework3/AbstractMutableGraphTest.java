package edu.postech.csed332.homework3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * An abstract test class for MutableGraph with blackbox test methods
 *
 * @param <V> type of vertices
 * @param <G> type of Graph
 */
@SuppressWarnings({"unchecked", "rawtypes"})
@Disabled
public abstract class AbstractMutableGraphTest<V extends Comparable<V>, G extends MutableGraph<V>> {

    G graph;
    V v1, v2, v3, v4, v5, v6, v7, v8;

    abstract boolean checkInv();    // call checkInv of graph

    @Test
    void testAddVertex() {
        Assertions.assertTrue(graph.addVertex(v1));
        Assertions.assertTrue(graph.containsVertex(v1));
        Assertions.assertTrue(checkInv());
    }

    @Test
    void testAddEdge() {
        Assertions.assertTrue(graph.addEdge(v1, v1));
        Assertions.assertTrue(graph.addEdge(v1, v2));
        Assertions.assertTrue(graph.addEdge(v3, v1));
        Assertions.assertTrue(graph.addEdge(v3, v2));
        Assertions.assertFalse(graph.addEdge(v1, v1));
        List<Edge> edges = List.of(
                new Edge(v1, v1),
                new Edge(v1, v2),
                new Edge(v3, v2),
                new Edge(v3, v1)
        );
        edges.forEach((edge)
                -> Assertions.assertTrue(graph.containsEdge((V) edge.getSource(), (V) edge.getTarget())));
        Assertions.assertTrue(checkInv());
    }

    @Test
    void testAddDuplicateVertices() {
        Assertions.assertTrue(graph.addVertex(v6));
        Assertions.assertTrue(graph.addVertex(v7));
        Assertions.assertFalse(graph.addVertex(v6));
        Assertions.assertTrue(graph.containsVertex(v6));
        Assertions.assertTrue(graph.containsVertex(v7));
        Assertions.assertTrue(checkInv());
    }

    @Test
    void testFindReachableVertices() {
        graph.addVertex(v4);

        Assertions.assertEquals(graph.findReachableVertices(v1), Set.of(v1, v2));
        Assertions.assertEquals(graph.findReachableVertices(v2), Set.of(v2));
        Assertions.assertEquals(graph.findReachableVertices(v3), Set.of(v1, v2, v3));
        Assertions.assertEquals(graph.findReachableVertices(v4), Set.of(v4));
        Assertions.assertEquals(graph.findReachableVertices(v5), Collections.emptySet());
        Assertions.assertTrue(checkInv());
    }

    @Test
    void testGetSources() {
        Set<V> sources = Set.of(v1, v3);
        Assertions.assertEquals(graph.getSources(v1), sources);
        Assertions.assertEquals(graph.getSources(v2), sources);
        Assertions.assertEquals(graph.getSources(v3), Collections.emptySet());
    }

    @Test
    void testGetTargets() {
        Set<V> sources = Set.of(v1, v2);
        Assertions.assertEquals(graph.getSources(v1), sources);
        Assertions.assertEquals(graph.getSources(v3), sources);
        Assertions.assertEquals(graph.getSources(v2), Collections.emptySet());
    }

    @Test
    void testGetVertices() {
        Set<V> sources = Set.of(v1, v2, v3, v4, v6, v7);
        Assertions.assertEquals(graph.getVertices(), sources);
    }

    @Test
    void testGetEdges() {
        Set<Edge> edges = Set.of(
                new Edge(v1, v1),
                new Edge(v1, v2),
                new Edge(v3, v1),
                new Edge(v3, v2)
        );
        Assertions.assertEquals(graph.getEdges(), edges);
    }

    @Test
    void testRemoveEdge() {
        graph.addEdge(v4, v6);
        graph.addEdge(v6, v7);
        graph.addEdge(v6, v1);

        Assertions.assertTrue(graph.removeEdge(v4, v6));
        Assertions.assertTrue(graph.removeEdge(v6, v7));
        Assertions.assertTrue(graph.removeEdge(v6, v1));
        Assertions.assertFalse(graph.removeEdge(v6, v6));
        List<Edge> edges = List.of(
                new Edge(v4, v6),
                new Edge(v6, v7),
                new Edge(v6, v1)
        );
        edges.forEach((edge)
                -> Assertions.assertFalse(graph.containsEdge((V) edge.getSource(), (V) edge.getTarget())));
        Assertions.assertTrue(checkInv());
    }

    @Test
    void testRemoveVertex() {
        Assertions.assertTrue(graph.removeVertex(v1));
        Assertions.assertFalse(graph.removeVertex(v8));
        Assertions.assertFalse(graph.containsVertex(v1));
        List<Edge> edges = List.of(
                new Edge(v1, v1),
                new Edge(v1, v2),
                new Edge(v3, v1)
        );
        edges.forEach((edge)
                -> Assertions.assertFalse(graph.containsEdge((V) edge.getSource(), (V) edge.getTarget())));
        Assertions.assertTrue(checkInv());
    }
}
