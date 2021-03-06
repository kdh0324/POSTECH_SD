package edu.postech.csed332.homework3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * An abstract test class for MutableGraph with blackbox test methods
 *
 * @param <V> type of vertices
 * @param <G> type of Graph
 */
@SuppressWarnings({"rawtypes"})
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
        Assertions.assertTrue(edges.stream().allMatch(edge -> graph.containsEdge((V) edge.getSource(), (V) edge.getTarget())));
        Assertions.assertTrue(checkInv());
    }

    @Test
    void testContainsEdge() {
        graph.addEdge(v1, v1);

        Assertions.assertTrue(graph.containsEdge(v1, v1));
        Assertions.assertFalse(graph.containsEdge(v1, v4));
        Assertions.assertFalse(graph.containsEdge(v4, v1));
        Assertions.assertFalse(graph.containsEdge(v4, v4));
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

        graph.addEdge(v1, v1);
        graph.addEdge(v1, v2);
        graph.addEdge(v3, v1);
        graph.addEdge(v3, v2);
        Assertions.assertEquals(graph.findReachableVertices(v1), Set.of(v1, v2));
        Assertions.assertEquals(graph.findReachableVertices(v2), Set.of(v2));
        Assertions.assertEquals(graph.findReachableVertices(v3), Set.of(v1, v2, v3));
        Assertions.assertEquals(graph.findReachableVertices(v4), Set.of(v4));
        Assertions.assertEquals(graph.findReachableVertices(v5), Collections.emptySet());
        Assertions.assertTrue(checkInv());
    }

    @Test
    void testGetSources() {
        Assertions.assertEquals(graph.getSources(v1), Collections.emptySet());
        graph.addEdge(v1, v1);
        graph.addEdge(v1, v2);
        graph.addEdge(v3, v1);
        graph.addEdge(v3, v2);

        Set<V> sources = Set.of(v1, v3);
        Assertions.assertEquals(graph.getSources(v1), sources);
        Assertions.assertEquals(graph.getSources(v2), sources);
        Assertions.assertEquals(graph.getSources(v3), Collections.emptySet());
    }

    @Test
    void testGetTargets() {
        Assertions.assertEquals(graph.getTargets(v1), Collections.emptySet());
        graph.addEdge(v1, v1);
        graph.addEdge(v1, v2);
        graph.addEdge(v3, v1);
        graph.addEdge(v3, v2);

        Set<V> sources = Set.of(v1, v2);
        Assertions.assertEquals(graph.getTargets(v1), sources);
        Assertions.assertEquals(graph.getTargets(v3), sources);
        Assertions.assertEquals(graph.getTargets(v2), Collections.emptySet());
    }

    @Test
    void testGetVertices() {
        Assertions.assertEquals(graph.getVertices(), Collections.emptySet());
        graph.addEdge(v1, v1);
        graph.addEdge(v1, v2);
        graph.addEdge(v3, v1);
        graph.addEdge(v3, v2);

        Set<V> sources = Set.of(v1, v2, v3);
        Assertions.assertEquals(graph.getVertices(), sources);
    }

    @Test
    void testGetEdges() {
        Assertions.assertEquals(graph.getTargets(v1), Collections.emptySet());
        graph.addEdge(v1, v1);
        graph.addEdge(v1, v2);
        graph.addEdge(v3, v1);
        graph.addEdge(v3, v2);

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
        graph.addEdge(v1, v1);
        graph.addEdge(v1, v2);
        graph.addEdge(v3, v1);
        graph.addEdge(v3, v2);
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
        Assertions.assertFalse(edges.stream().anyMatch(edge -> graph.containsEdge((V) edge.getSource(), (V) edge.getTarget())));
        Assertions.assertTrue(checkInv());
    }

    @Test
    void testRemoveVertex() {
        graph.addEdge(v1, v1);
        graph.addEdge(v1, v2);
        graph.addEdge(v3, v1);
        graph.addEdge(v3, v2);

        Assertions.assertTrue(graph.removeVertex(v1));
        Assertions.assertFalse(graph.removeVertex(v8));
        Assertions.assertFalse(graph.containsVertex(v1));
        List<Edge> edges = List.of(
                new Edge(v1, v1),
                new Edge(v1, v2),
                new Edge(v3, v1)
        );
        Assertions.assertFalse(edges.stream().anyMatch(edge -> graph.containsEdge((V) edge.getSource(), (V) edge.getTarget())));
        Assertions.assertTrue(checkInv());
    }
}
