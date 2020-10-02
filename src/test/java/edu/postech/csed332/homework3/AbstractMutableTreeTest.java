package edu.postech.csed332.homework3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * An abstract test class for MutableTree with blackbox test methods
 *
 * @param <V> type of vertices
 * @param <T> type of Tree
 */
@SuppressWarnings({"rawtypes"})
@Disabled
public abstract class AbstractMutableTreeTest<V extends Comparable<V>, T extends MutableTree<V>> {

    T tree;
    V v1, v2, v3, v4, v5, v6, v7, v8;

    abstract boolean checkInv();    // call checkInv of tree

    private void init() {
        tree.addVertex(v1);
        tree.addEdge(v1, v2);
        tree.addEdge(v1, v3);
        tree.addEdge(v2, v4);
        tree.addEdge(v3, v5);
        tree.addEdge(v3, v6);
        tree.addEdge(v3, v7);
        tree.addEdge(v7, v8);
    }

    @Test
    void testGetDepthRoot() {
        tree.addVertex(v1);
        Assertions.assertEquals(tree.getDepth(v1), 0);
    }

    @Test
    void testGetDepthTwo() {
        tree.addVertex(v1);
        tree.addEdge(v1, v2);
        Assertions.assertEquals(tree.getDepth(v2), 1);
    }

    @Test
    void testGetDepthNoRoot() {
        Assertions.assertThrows(IllegalStateException.class, () -> tree.getDepth(v1));
    }

    @Test
    void testGetHeightNoRoot() {
        Assertions.assertThrows(IllegalStateException.class, () -> tree.getHeight());
    }

    @Test
    void testGetDepthNoVertex() {
        Assertions.assertTrue(tree.addVertex(v1));
        Assertions.assertThrows(IllegalArgumentException.class, () -> tree.getDepth(v2));
    }

    @Test
    void testGetHeight() {
        tree.addVertex(v1);
        tree.addEdge(v1, v2);

        Assertions.assertEquals(tree.getHeight(), 1);
    }

    @Test
    void testAddVertex() {
        Assertions.assertTrue(tree.addVertex(v1));
        Assertions.assertTrue(tree.containsVertex(v1));
        Assertions.assertFalse(tree.addVertex(v2));
        Assertions.assertFalse(tree.containsVertex(v2));
        Assertions.assertTrue(checkInv());
    }

    @Test
    void testAddEdge() {
        tree.addVertex(v1);

        Assertions.assertTrue(tree.addEdge(v1, v2));
        Assertions.assertTrue(tree.containsVertex(v2));
        Assertions.assertFalse(tree.addEdge(v1, v1));
        Assertions.assertFalse(tree.addEdge(v3, v1));
        Assertions.assertFalse(tree.containsVertex(v3));
        Assertions.assertTrue(checkInv());
    }

    @Test
    void testGetVertices() {
        init();

        Assertions.assertEquals(tree.getVertices(), Set.of(v1, v2, v3, v4, v5, v6, v7, v8));
    }

    @Test
    void testGetEdges() {
        init();

        Set<Edge> edges = Set.of(
                new Edge(v1, v2),
                new Edge(v1, v3),
                new Edge(v2, v4),
                new Edge(v3, v5),
                new Edge(v3, v6),
                new Edge(v3, v7),
                new Edge(v7, v8)
        );
        Assertions.assertEquals(tree.getEdges(), edges);
    }

    @Test
    void testGetChildren() {
        tree.addVertex(v1);
        tree.addEdge(v1, v2);
        tree.addEdge(v1, v3);
        tree.addEdge(v1, v4);
        tree.addEdge(v4, v5);

        Assertions.assertEquals(tree.getChildren(v1), Set.of(v2, v3, v4));
        Assertions.assertEquals(tree.getChildren(v2), Collections.emptySet());
        Assertions.assertEquals(tree.getChildren(v4), Set.of(v5));
    }

    @Test
    void testGetParent() {
        tree.addVertex(v1);
        tree.addEdge(v1, v2);

        Assertions.assertEquals(tree.getParent(v2), Optional.of(v1));
        Assertions.assertEquals(tree.getParent(v1), Optional.empty());
    }

    @Test
    void testGetRoot() {
        Assertions.assertEquals(tree.getRoot(), Optional.empty());

        tree.addVertex(v1);
        Assertions.assertEquals(tree.getRoot(), Optional.of(v1));
    }

    @Test
    void testRemoveVertex() {
        init();

        Assertions.assertTrue(tree.removeVertex(v8));
        Assertions.assertFalse(tree.containsVertex(v8));
        Assertions.assertFalse(tree.containsEdge(v7, v8));
        Assertions.assertFalse(tree.removeVertex(v8));

        Assertions.assertTrue(tree.removeVertex(v3));
        List<V> vertices = List.of(v3, v5, v6, v7);
        vertices.forEach((vertex)
                -> Assertions.assertFalse(tree.containsVertex(vertex)));
        List<Edge> edges = List.of(
                new Edge(v1, v3),
                new Edge(v3, v5),
                new Edge(v3, v6),
                new Edge(v3, v7)
        );
        Assertions.assertFalse(edges.stream().allMatch(edge -> tree.containsEdge((V) edge.getSource(), (V) edge.getTarget())));

        Assertions.assertTrue(tree.removeVertex(v1));
        Assertions.assertEquals(tree.getRoot(), Optional.empty());
        Assertions.assertTrue(checkInv());
    }

    @Test
    void testRemoveEdge() {
        init();

        Assertions.assertTrue(tree.removeEdge(v2, v4));
        Assertions.assertFalse(tree.containsVertex(v4));
        Assertions.assertFalse(tree.containsEdge(v2, v4));
        Assertions.assertFalse(tree.removeEdge(v2, v4));

        Assertions.assertTrue(tree.removeEdge(v1, v3));
        List<V> vertices = List.of(v3, v5, v6, v7);
        vertices.forEach((vertex)
                -> Assertions.assertFalse(tree.containsVertex(vertex)));
        List<Edge> edges = List.of(
                new Edge(v1, v3),
                new Edge(v3, v5),
                new Edge(v3, v6),
                new Edge(v3, v7)
        );
        Assertions.assertFalse(edges.stream().allMatch(edge -> tree.containsEdge((V) edge.getSource(), (V) edge.getTarget())));
    }
}
