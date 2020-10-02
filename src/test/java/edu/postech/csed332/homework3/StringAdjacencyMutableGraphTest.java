package edu.postech.csed332.homework3;

import org.junit.jupiter.api.BeforeEach;

public class StringAdjacencyMutableGraphTest extends AbstractMutableGraphTest<String, AdjacencyListGraph<String>> {

    @BeforeEach
    void setUp() {
        graph = new AdjacencyListGraph<>();
        v1 = "1";
        v2 = "2";
        v3 = "3";
        v4 = "4";
        v5 = "5";
        v6 = "6";
        v7 = "7";
        v8 = "8";
    }

    @Override
    boolean checkInv() {
        return graph.checkInv();
    }
}
