package liquibase.util;

import liquibase.DependencyObject;
import liquibase.exception.DependencyException;

import java.util.*;

public class DependencyUtil {

    /**
     * Sorts the passed {@link DependencyObject}(s) by their dependencies.
     */
    public static <Type extends DependencyObject> List<Type> sort(Collection<Type> objects) throws DependencyException {

        DependencyGraph graph = new DependencyGraph();
        for (DependencyObject object : objects) {
            graph.add(object);
        }
        return graph.sort();
    }

    private static class DependencyGraph<Type extends DependencyObject> {
        private Map<Class<Type>, Node> allNodes = new HashMap<>();

        private void add(Type object) {
            allNodes.put((Class<Type>) object.getClass(), new Node(object));
        }

        public List<Type> sort() throws DependencyException {
            for (Class<Type> type : allNodes.keySet()) {
                for (Class<Type> afterType : (Class<Type>[]) ObjectUtil.defaultIfNull(getNode(type).object.mustBeBefore(), new Class[0])) {
                    getNode(type).addEdge(getNode(afterType));
                }

                for (Class<Type> beforeType : (Class<Type>[]) ObjectUtil.defaultIfNull(getNode(type).object.mustBeAfter(), new Class[0])) {
                    getNode(beforeType).addEdge(getNode(type));
                }
            }

            ArrayList<Node> returnNodes = new ArrayList<>();

            SortedSet<Node> nodesWithNoIncomingEdges = new TreeSet<>(new Comparator<Node>() {
                @Override
                public int compare(Node o1, Node o2) {
                    return o1.object.getClass().getName().compareTo(o2.object.getClass().getName());
                }
            });
            for (Node n : allNodes.values()) {
                if (n.inEdges.size() == 0) {
                    nodesWithNoIncomingEdges.add(n);
                }
            }

            while (!nodesWithNoIncomingEdges.isEmpty()) {
                Node node = nodesWithNoIncomingEdges.iterator().next();
                nodesWithNoIncomingEdges.remove(node);

                returnNodes.add(node);

                for (Iterator<Edge> it = node.outEdges.iterator(); it.hasNext(); ) {
                    //remove edge e from the graph
                    Edge edge = it.next();
                    Node nodePointedTo = edge.to;
                    it.remove();//Remove edge from node
                    nodePointedTo.inEdges.remove(edge);//Remove edge from nodePointedTo

                    //if nodePointedTo has no other incoming edges then insert nodePointedTo into nodesWithNoIncomingEdges
                    if (nodePointedTo.inEdges.isEmpty()) {
                        nodesWithNoIncomingEdges.add(nodePointedTo);
                    }
                }
            }
            //Check to see if all edges are removed
            for (Node n : allNodes.values()) {
                if (!n.inEdges.isEmpty()) {
                    String message = "Could not resolve dependencies due to dependency cycle. Dependencies: \n";
                    for (Node node : allNodes.values()) {
                        SortedSet<String> fromTypes = new TreeSet<>();
                        SortedSet<String> toTypes = new TreeSet<>();
                        for (Edge edge : node.inEdges) {
                            fromTypes.add(edge.from.object.getClass().getName());
                        }
                        for (Edge edge : node.outEdges) {
                            toTypes.add(edge.to.object.getClass().getName());
                        }
                        String from = StringUtil.join(fromTypes, ",");
                        String to = StringUtil.join(toTypes, ",");
                        message += "    [" + from + "] -> " + node.object.getClass().getName() + " -> [" + to + "]\n";
                    }

                    throw new DependencyException(message);
                }
            }
            List<Type> returnList = new ArrayList<>();
            for (Node node : returnNodes) {
                returnList.add(node.object);
            }
            return returnList;
        }


        private Node getNode(Class<Type> type) throws DependencyException {
            Node node = allNodes.get(type);
            if (node == null) {
                try {
                    node = new Node(type.newInstance());
                } catch (Exception e) {
                    throw new DependencyException(e);
                }
            }
            return node;
        }


        private class Node {
            public final Type object;
            public final HashSet<Edge> inEdges;
            public final HashSet<Edge> outEdges;

            public Node(Type object) {
                this.object = object;
                inEdges = new HashSet<>();
                outEdges = new HashSet<>();
            }

            public Node addEdge(Node node) {
                Edge e = new Edge(this, node);
                outEdges.add(e);
                node.inEdges.add(e);
                return this;
            }

            @Override
            public String toString() {
                return object.toString();
            }
        }

        private class Edge {
            public final Node from;
            public final Node to;

            public Edge(Node from, Node to) {
                this.from = from;
                this.to = to;
            }

            @Override
            public boolean equals(Object obj) {
                if (obj == null) {
                    return false;
                }
                if (!Edge.class.isAssignableFrom(obj.getClass())) {
                    return false;
                }
                Edge e = (Edge) obj;
                return e.from == from && e.to == to;
            }

            @Override
            public int hashCode() {
                return (this.from.toString() + "." + this.to.toString()).hashCode();
            }
        }
    }
}