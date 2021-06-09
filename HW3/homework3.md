# Problem 1


Let $`\mathcal{N}`$ be the set of all elements of type $`\textsf{N}`$, and $`\mathsf{null} \notin \mathcal{N}`$ be an distinguished element to represent `null`. Write formal abstract specifications of the interfaces below with respect to following abstract values:

- A graph is a pair $`G = (V, E)`$, where $`V \subseteq \mathcal{N}`$ and $`E \subseteq V \times V`$.
- A tree is a triple $`T = (V, E, \hat{v})`$, where $`(V, E)`$ is a graph and $`\hat{v} \in \mathcal{N}`$ denotes the root.

Other data types, such as `boolean`, `int`, `Set<N>`, etc. have conventional abstract values, e.g., Boolean values, integers, and subsets of $`\mathcal{N}`$, etc.

## `Graph<N>`

Let $`G_{this} = (V_{this}, E_{this})`$ be an abstract value of the current graph object. 

##### Class invariant 

```math
\forall (v, w) \in E_{this}.\ v, w \in V_{this}
```

##### containsVertex

```java 
boolean containsVertex(N vertex);
```

- requires: vertex is in $`\mathcal{N}`$ and not $`\mathsf{null}`$
- ensures:  
    + returns true if vertex is in $`V_{this}`$; and
    - returns false, otherwise.

##### containsEdge

```java
boolean containsEdge(N source, N target);
```

- requires: source and target are in $`\mathcal{N}`$ and not $`\mathsf{null}`$
- ensures:  
    + returns true if source and target is connected by an edge which is in $`\mathcal{N}`$; and
    - returns false, otherwise.

##### getSources

```java
Set<N> getSources(N target);
```

- requires: target is in $`\mathcal{N}`$ and not $`\mathsf{null}`$
- ensures:  
    + returns the set of vertices that have an edge to target, immutable.

##### getTargets

```java
Set<N> getTargets(N source);
```

- requires: source is in $`\mathcal{N}`$ and not $`\mathsf{null}`$
- ensures:  
    + returns the set of vertices that have an edge from source, immutable.


## `Tree<N>`

Let $`T_{this} = (V_{this}, E_{this}, \hat{v}_{this})`$ be an abstract value of the current graph object. 

##### Class invariant 

```math
\forall (v, w) \in E_{this}.\ v, w \in V_{this},\newline
\hat{v}_{this} \in V_{this},\newline
\forall (v, w) \in E_{this}.\ (w, v) \notin E_{this},\newline
Let, T'_{this} = (V'_{this}, E'_{this}, \hat{v}'_{this}), T'_{this} \in T_{this}.
\forall v \in V'_{this}, w \in V_{this} - V'_{this}. (v, w) \notin E_{this} 
```

##### getDepth

```java
int getDepth(N vertex);
```

- requires: 
  + vertex is in $`\mathcal{N}`$ and not $`\mathsf{null}`$; and
  + getRoot.isPresent()
- ensures:  
  + returns 0 if vertex is getRoot.get(); and
  + returns getDepth(getParent(vertex)) + 1, otherwise.

##### getHeight

```java
int getHeight();
```

- requires: getRoot.isPresent()
- ensures:  
  + returns the maximum of getDepth(N vertex)

##### getRoot

```java
Optional<N> getRoot();
```

- requires: 
- ensures:  
  + returns Optional.empty() if tree is empty; and
  + returns $`\hat{v}_{this}`$, otherwise.

##### getParent

```java
Optional<N> getParent(N vertex);
```

- requires: vertex is in $`\mathcal{N}`$ and not $`\mathsf{null}`$
- ensures:  
  + returns Optional.empty() if vertex is getRoot.get(); and
  + returns an element of getSources(vertex), otherwise.


## `MutableGraph<N>`

Let $`G_{this} = (V_{this}, E_{this})`$ be an abstract value of the current graph object,
and $`G_{next} = (V_{next}, E_{next})`$ be an abstract value of the graph object _modified by_ the method call. 

##### Class invariant 

```math
\forall (v, w) \in E_{this}.\ v, w \in V_{this}
```

##### addVertex

```java
boolean addVertex(N vertex);
```

- requires: vertex is in $`\mathcal{N}`$ and not $`\mathsf{null}`$
- ensures:  
    + $`V_{next} = V_{this} \cup \{\texttt{vertex}\}`$; 
    + $`E_{next} = E_{this}`$ (the edges are not modified)
    + If $`G_{this}`$ satisfies the class invariant, $`G_{next}`$ also satisfies the class invariant; and
    + returns true if and only if $`\texttt{vertex} \notin V_{this}`$.

##### removeVertex

```java
boolean removeVertex(N vertex);
```

- requires: vertex is in $`\mathcal{N}`$ and not $`\mathsf{null}`$
- ensures:  
    + $`V_{next} = V_{this} - \{\texttt{vertex}\}`$; 
    + $`E_{next} = E_{this} - \{\forall (\texttt{vertex}, v)\} - \{\forall (v, \texttt{vertex})\}`$;
    + If $`G_{this}`$ satisfies the class invariant, $`G_{next}`$ also satisfies the class invariant; and
    + returns true if and only if $`V_{this} \neq V_{next}`$.

##### addEdge

```java
boolean addEdge(N source, N target);
```

- requires: source and target are in $`\mathcal{N}`$ and not $`\mathsf{null}`$
- ensures:  
    + $`V_{next} = V_{this}`$; 
    + $`E_{next} = E_{this} \cup \{(\texttt{source}, \texttt{target})\}`$;
    + If $`G_{this}`$ satisfies the class invariant, $`G_{next}`$ also satisfies the class invariant; and
    + returns true if and only if $`E_{this} \neq E_{next}`$.

##### removeEdge

```java
boolean removeEdge(N source, N target);
```

- requires: source and target are in $`\mathcal{N}`$ and not $`\mathsf{null}`$
- ensures:  
    + $`V_{next} = V_{this}`$; 
    + $`E_{next} = E_{this} - {(\texttt{source}, \texttt{target})}`$;
    + If $`G_{this}`$ satisfies the class invariant, $`G_{next}`$ also satisfies the class invariant; and
    + returns true if and only if $`E_{this} \neq E_{next}`$.


## `MutableTree<N>`

##### Class invariant 

Let $`T_{this} = (V_{this}, E_{this}, \hat{v}_{this})`$ be an abstract value of the current tree object,
and $`T_{next} = (V_{next}, E_{next}, \hat{v}_{next})`$ be an abstract value of the tree object _modified by_ the method call. 

##### addVertex

```java
boolean addVertex(N vertex);
```

- requires: vertex is in $`\mathcal{N}`$ and not $`\mathsf{null}`$
- ensures:  
    + If $`V_{this}`$ is empty, $`V_{next} = \texttt{vertex}`$; 
    + $`E_{next} = E_{this}`$ (the edges are not modified);
    + If $`V_{this}`$ is empty, &`\hat{v}_{next} = \texttt{vertex}`$
    + If $`G_{this}`$ satisfies the class invariant, $`G_{next}`$ also satisfies the class invariant; and
    + returns true if and only if $`V_{this}`$ is empty.

##### removeVertex

```java
boolean removeVertex(N vertex);
```

- requires: vertex is in $`\mathcal{N}`$ and not $`\mathsf{null}`$
- ensures:  
    + $`V_{next} = V_{this} - \{v|v \in \texttt{subgraph which a root is vertex}\}`$; 
    + $`E_{next} = E_{this} - \{e|e \in \texttt{subgraph which a root is vertex}\}`$;
    + If $`G_{this}`$ satisfies the class invariant, $`G_{next}`$ also satisfies the class invariant; and
    + returns true if and only if $`V_{this} \neq V_{next}`$.

##### addEdge

```java
boolean addEdge(N source, N target);
```

- requires: source and target are in $`\mathcal{N}`$ and not $`\mathsf{null}`$
- ensures:  
    + If $`\texttt{source} \in E_{this} && \texttt{target} \notin E_{this}`$,
    $`V_{next} = V_{this} \cup \{\texttt{target}\}`$; 
    + If $`\texttt{source} \in E_{this} && \texttt{target} \notin E_{this}`$,
    $`E_{next} = E_{this} \cup \{(\texttt{source}, \texttt{target})\}`$;
    + If $`G_{this}`$ satisfies the class invariant, $`G_{next}`$ also satisfies the class invariant; and
    + returns true if and only if $`\texttt{source} \in E_{this} && \texttt{target} \notin E_{this}`$.

##### removeEdge

```java
boolean removeEdge(N source, N target);
```

- requires: source and target are in $`\mathcal{N}`$ and not $`\mathsf{null}`$
- ensures:  
    + If $`\texttt{source} \in E_{this} && \texttt{target} \in E_{this}`$,
    $`V_{next} = V_{this} - \{v|v \in \texttt{subgraph which a root is target}\}`$; 
    + If $`\texttt{source} \in E_{this} && \texttt{target} \in E_{this}`$,
    $`E_{next} = E_{this} - \{e|e \in \texttt{subgraph which a root is target}\} - \{(source, target)\}`$;
    + If $`G_{this}`$ satisfies the class invariant, $`G_{next}`$ also satisfies the class invariant; and
    + returns true if and only if $`\texttt{source} \in E_{this} && \texttt{target} \in E_{this}`$.


# Problem 2

Identify whether the abstract interfaces satisfy the Liskov substitution principle.
For each question, explain your reasoning _using the abstract specifications that you have defined in Problem 1_. 


##### `Tree<N>` and `Graph<N>`

* Is `Tree<N>` a subtype of `Graph<N>`?
    + Yes, it is. Because $`\hat{v}_{this} \in V_{this}`$ and `Tree<N>` extends `Graph<N>`.
    + Also, a class invariant of `Graph<N>` contains a class invariant of `Tree<N>`. 

##### `MutableGraph<N>` and `Graph<N>`

* Is `MutableGraph<N>` a subtype of `Graph<N>`
    + Yes, it is. Because `MutableGraph<N>` extends `Graph<N>`.
    + Also, a class invariant of `MutableGraph<N>` contains a class invariant of `Graph<N>`.

##### `MutableTree<N>` and `Tree<N>`

* Is `MutableTree<N>` a subtype of `Tree<N>`
    + Yes, it is. Because `MutableTree<N>` extends `Tree<N>`.
    + Also, a class invariant of `MutableTree<N>` contains a class invariant of `Tree<N>`.

##### `MutableTree<N>` and `MutableGraph<N>`

* Is `MutableTree<N>` a subtype of `MutableGraph<N>`
    + No, it isn't.
    + `addVertex` in `MutableTree<N>` returns false when $`V_{this}`$ is not empty, 
    but `MutableGraph<N>` returns true only if vertex is not in $`V_{this}`$.
    + Also, `removeVertex` in `MutableTree<N>` remove only selected vertex, 
    but `MutableGraph<N>` remove subtree which a root is selected vertex.