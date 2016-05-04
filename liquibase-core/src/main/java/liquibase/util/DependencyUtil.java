package liquibase.util;

import liquibase.DependencyObject;
import liquibase.exception.DependencyException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Utility class for managing dependencies between objects.
 */
public abstract class DependencyUtil {

    /**
     * Sorts the passed {@link DependencyObject}(s) by their dependencies.
     */
    public static <Type extends DependencyObject> List<Type> sort(Collection<Type> objects) throws DependencyException {

        final Logger log = LoggerFactory.getLogger(DependencyUtil.class);

        final Map<Class, Type> instanceByClass = new HashMap<>();
        for (Type obj : objects) {
            instanceByClass.put(obj.getClass(), obj);
        }

        final List<Type> returnList = new ArrayList<>();
        DependencyGraph graph = new DependencyGraph(new NodeValueListener<Class>() {
            @Override
            public void evaluating(Class nodeValue) {
                if (nodeValue != null) {
                    Type originalObject = instanceByClass.get(nodeValue);
                    if (originalObject == null) {
                        log.debug(nodeValue.getName()+" was declared as a dependency, but no instance of that class was passed");
                    }
                    returnList.add(originalObject);
                }
            }
        });

        for (DependencyObject object : objects) {
            Class[] mustBeBefore = object.mustBeBefore();
            Class[] mustBeAfter = object.mustBeAfter();

            Class objectClass = object.getClass();
            if ((mustBeBefore == null || mustBeBefore.length == 0) && (mustBeAfter == null || mustBeAfter.length == 0)) {
                graph.add(objectClass, null);
            } else {
                if (mustBeAfter != null) {
                    for (Class beAfter : mustBeAfter) {
                        graph.add(beAfter, objectClass);
                    }
                }
                if (mustBeBefore != null) {
                    for (Class beBefore : mustBeBefore) {
                        graph.add(objectClass, beBefore);
                    }
                }
            }
        }

        graph.computeDependencies();

        if (log.isDebugEnabled()) {
            log.debug("Final dependency order: "+ StringUtils.join(returnList, ", "));
        }
        return returnList;
    }


    private static class DependencyGraph<T> {

        private HashMap<T, GraphNode<T>> nodes = new HashMap<>();
        private NodeValueListener<T> listener;
        private List<GraphNode<T>> evaluatedNodes = new ArrayList<>();


        public DependencyGraph(NodeValueListener<T> listener) {
            this.listener = listener;
        }

        public void add(T evalFirstValue, T evalAfterValue) {
            GraphNode<T> firstNode = null;
            GraphNode<T> afterNode = null;
            if (nodes.containsKey(evalFirstValue)) {
                firstNode = nodes.get(evalFirstValue);
            } else {
                firstNode = createNode(evalFirstValue);
                nodes.put(evalFirstValue, firstNode);
            }
            if (nodes.containsKey(evalAfterValue)) {
                afterNode = nodes.get(evalAfterValue);
            } else {
                afterNode = createNode(evalAfterValue);
                nodes.put(evalAfterValue, afterNode);
            }
            firstNode.addGoingOutNode(afterNode);
            afterNode.addComingInNode(firstNode);
        }

        private GraphNode<T> createNode(T value) {
            GraphNode<T> node = new GraphNode<>();
            node.value = value;
            return node;
        }

        public void computeDependencies() {
            List<GraphNode<T>> orphanNodes = getOrphanNodes();
            List<GraphNode<T>> nextNodesToDisplay = new ArrayList<>();
            for (GraphNode<T> node : orphanNodes) {
                listener.evaluating(node.value);
                evaluatedNodes.add(node);
                nextNodesToDisplay.addAll(node.getGoingOutNodes());
            }
            computeDependencies(nextNodesToDisplay);
        }

        private void computeDependencies(List<GraphNode<T>> nodes) {
            List<GraphNode<T>> nextNodesToDisplay = null;
            for (GraphNode<T> node : nodes) {
                if (!isAlreadyEvaluated(node)) {
                    List<GraphNode<T>> comingInNodes = node.getComingInNodes();
                    if (areAlreadyEvaluated(comingInNodes)) {
                        listener.evaluating(node.value);
                        evaluatedNodes.add(node);
                        List<GraphNode<T>> goingOutNodes = node.getGoingOutNodes();
                        if (goingOutNodes != null) {
                            if (nextNodesToDisplay == null)
                                nextNodesToDisplay = new ArrayList<>();
                            // add these too, so they get a chance to be displayed
                            // as well
                            nextNodesToDisplay.addAll(goingOutNodes);
                        }
                    } else {
                        if (nextNodesToDisplay == null)
                            nextNodesToDisplay = new ArrayList<>();
                        // the checked node should be carried
                        nextNodesToDisplay.add(node);
                    }
                }
            }
            if (nextNodesToDisplay != null) {
                computeDependencies(nextNodesToDisplay);
            }
            // here the recursive call ends
        }

        private boolean isAlreadyEvaluated(GraphNode<T> node) {
            return evaluatedNodes.contains(node);
        }

        private boolean areAlreadyEvaluated(List<GraphNode<T>> nodes) {
            return evaluatedNodes.containsAll(nodes);
        }

        private List<GraphNode<T>> getOrphanNodes() {
            List<GraphNode<T>> orphanNodes = null;
            Set<T> keys = nodes.keySet();
            for (T key : keys) {
                GraphNode<T> node = nodes.get(key);
                if (node.getComingInNodes() == null) {
                    if (orphanNodes == null)
                        orphanNodes = new ArrayList<>();
                    orphanNodes.add(node);
                }
            }
            return orphanNodes;
        }
    }

    private static class GraphNode<T> {
        public T value;
        private List<GraphNode<T>> comingInNodes;
        private List<GraphNode<T>> goingOutNodes;

        public void addComingInNode(GraphNode<T> node) {
            if (comingInNodes == null)
                comingInNodes = new ArrayList<>();
            comingInNodes.add(node);
        }

        public void addGoingOutNode(GraphNode<T> node) {
            if (goingOutNodes == null)
                goingOutNodes = new ArrayList<>();
            goingOutNodes.add(node);
        }

        public List<GraphNode<T>> getComingInNodes() {
            return comingInNodes;
        }

        public List<GraphNode<T>> getGoingOutNodes() {
            return goingOutNodes;
        }
    }


    private interface NodeValueListener<T> {
        void evaluating(T nodeValue);
    }


}