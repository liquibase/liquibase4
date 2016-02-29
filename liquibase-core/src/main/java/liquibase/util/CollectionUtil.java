package liquibase.util;

import liquibase.exception.UnexpectedLiquibaseException;
import org.slf4j.LoggerFactory;

import java.util.*;

public abstract class CollectionUtil {

    public static <T> Set<Set<T>> powerSet(Collection<T> originalSet) {
        Set<Set<T>> sets = new HashSet<>();
        if (originalSet.isEmpty()) {
            sets.add(new HashSet<T>());
            return sets;
        }
        List<T> list = new ArrayList<>(originalSet);
        T head = list.get(0);
        Collection<T> rest = list.subList(1, list.size());
        for (Set<T> set : powerSet(rest)) {
            Set<T> newSet = new HashSet<>();
            newSet.add(head);
            newSet.addAll(set);
            sets.add(newSet);
            sets.add(set);
        }
        return sets;
    }

    public static <T> List<List<T>> permutations(List<List<T>> parameterValues) {
        return permutations(parameterValues, new IncludeAllFilter<T>());
    }

    public static <T> List<List<T>> permutationsWithoutNulls(List<List<T>> parameterValues) {
        return permutationsWithoutNulls(parameterValues, new IncludeAllFilter<T>());
    }


    public static <T> List<List<T>> permutationsWithoutNulls(List<List<T>> parameterValues, CollectionFilter<Map<String, T>> filter) {
        return permutations(parameterValues, filter, false);
    }

    public static <T> List<List<T>> permutations(List<List<T>> parameterValues, CollectionFilter<Map<String, T>> filter) {
        return permutations(parameterValues, filter, true);
    }

    private static <T> List<List<T>> permutations(List<List<T>> parameterValues, CollectionFilter<Map<String, T>> filter, boolean addNulls) {
        List<List<T>> list = new ArrayList<>();
        if (parameterValues == null || parameterValues.size() == 0) {
            return list;
        }

        int i = 0;
        LinkedHashMap<String, List<T>> map = new LinkedHashMap<>();
        for (Collection<T> value : parameterValues) {
            map.put(String.valueOf(i++), new ArrayList<T>(value));
        }

        for (Map<String, T> permutation : permutations(map, filter, addNulls)) {
            List<T> val = new ArrayList<>();
            for (String key : permutation.keySet()) {
                val.add(permutation.get(key));
            }
            list.add(val);
        }

        return list;
    }

    public static <T> List<Map<String, T>> permutations(Map<String, List<T>> parameterValues) {
        return permutations(parameterValues, new CollectionFilter<Map<String, T>>() {
            @Override
            public boolean include(Map<String, T> obj) {
                return true;
            }
        }, true);
    }

    public static <T> List<Map<String, T>> permutations(Map<String, List<T>> parameterValues, boolean addNulls) {
        return permutations(parameterValues, new CollectionFilter<Map<String, T>>() {
            @Override
            public boolean include(Map<String, T> obj) {
                return true;
            }
        }, addNulls);
    }

    public static <T> List<Map<String, T>> permutations(Map<String, List<T>> parameterValues, CollectionFilter<Map<String, T>> filter) {
        return permutations(parameterValues, filter, true);
    }

    public static <T> List<Map<String, T>> permutations(Map<String, List<T>> parameterValues, CollectionFilter<Map<String, T>> filter, boolean addNulls) {
        PermutationIterator<T> it = new PermutationIterator<>(parameterValues, filter, addNulls);
        List<Map<String, T>> returnList = new ArrayList<>();
        while (it.hasNext()) {
            returnList.add(it.next());
        }

        LoggerFactory.getLogger(CollectionUtil.class).debug("Unfiltered {} permutations: {}, filtered: {}", parameterValues.keySet(), it.unfilteredTotal, it.filteredTotal);
        return returnList;
    }

    /**
     * Convenience method returns passed currentValue if it is not null and creates a new ArrayList if it is null.
     * <br><br>
     * Example: values = createIfNull(values)
     */
    public static <T> List<T> createIfNull(List<T> currentValue) {
        if (currentValue == null) {
            return new ArrayList<T>();
        } else {
            return currentValue;
        }
    }

    public static <T> T[] createIfNull(T[] arguments) {
        if (arguments == null) {
            return (T[]) new Object[0];
        } else {
            return arguments;
        }
    }

    public static <T> Set<T> createIfNull(Set<T> currentValue) {
        if (currentValue == null) {
            return new HashSet<>();
        } else {
            return currentValue;
        }
    }


    /**
     * Return a new list, filtered by the collectionFilter
     */
    public static <T> List<T> select(List<T> collection, CollectionFilter<T> collectionFilter) {
        List<T> newCollection = new ArrayList<>();

        for (T obj : collection) {
            if (collectionFilter.include(obj)) {
                newCollection.add(obj);
            }
        }

        return newCollection;
    }

    /**
     * Return a new set, filtered by the collectionFilter
     */
    public static <T> Set<T> select(Set<T> collection, CollectionFilter<T> collectionFilter) {
        Set<T> newCollection = new HashSet<>();

        for (T obj : collection) {
            if (collectionFilter.include(obj)) {
                newCollection.add(obj);
            }
        }

        return newCollection;
    }

    /**
     * Adds a null value to the given collection and returns the collection.
     */
    public static <T extends Collection> T addNull(T collection) {
        T returnCollection = null;
        if (collection instanceof List) {
            returnCollection = (T) new ArrayList();
        } else if (collection instanceof SortedSet) {
            returnCollection = (T) new TreeSet<>();
        } else if (collection instanceof Set) {
            returnCollection = (T) new HashSet<>();
        } else {
            throw new UnexpectedLiquibaseException("Unknown collection type: "+collection.getClass().getName());
        }
        returnCollection.addAll(collection);
        returnCollection.add(null);
        return returnCollection;
    }

    /**
     * Create a new list containing all the values in the passed list as single-entry lists
     */
    public static <T> List<List<T>> toSingletonLists(List<T> list) {
        List<List<T>> returnList = new ArrayList<>();
        for (T obj : list) {
            List<T> objList = new ArrayList<>();
            objList.add(obj);
            returnList.add(objList);
        }

        return returnList;
    }

    /**
     * Add items to an existing list and return the existing list
     */
    public static <T> List<T> addTo(List<T> list, T... items) {
        for (T item : items) {
            list.add(item);
        }
        return list;
    }

    public interface CollectionFilter<T> {

        boolean include(T obj);
    }

    private static class PermutationIterator<T> implements Iterator<Map<String, T>> {

        private final CollectionFilter<Map<String, T>> filter;
        private final boolean addNulls;
        private Map<String, T> next = null;

        private Map<String, List<T>> parameterValues;

        private List<String> params;
        private LinkedHashMap<String, Iterator<T>> baseMapValueIterator;

        private int unfilteredTotal = 0;
        private int filteredTotal = 0;


        public PermutationIterator(Map<String, List<T>> originalParameterValues, CollectionFilter<Map<String, T>> filter, boolean addNulls) {
            this.addNulls = addNulls;
            this.filter = filter;
            if (originalParameterValues == null || originalParameterValues.size() == 0) {
                this.next = null;
                return;
            }

            Map<String, List<T>> nonEmptyValues = new HashMap<>();
            for (String param : originalParameterValues.keySet()) {
                List<T> value = originalParameterValues.get(param);
                if (value != null && value.size() > 0) {
                    if (addNulls && !value.contains(null)) {
                        value = new ArrayList<>(value);
                        value.add(null);
                    }
                    nonEmptyValues.put(param, value);
                }
            }

            this.params = new ArrayList<>(nonEmptyValues.keySet());
            Collections.sort(this.params);

            this.parameterValues = nonEmptyValues;

            this.next = new LinkedHashMap<>();
            this.baseMapValueIterator = new LinkedHashMap<>();

            for (String param : this.params) {
                Iterator<T> iterator = this.parameterValues.get(param).iterator();
                this.baseMapValueIterator.put(param, iterator);
                T value = iterator.next();
                if (value != null) {
                    this.next.put(param, value);
                }
            }

            unfilteredTotal++;
            if (filter.include(this.next)) {
                filteredTotal++;
            } else {
                findNext();
            }
        }

        private void findNext() {
            next = new LinkedHashMap<>(this.next);
            while (true) {
                unfilteredTotal++;

                int paramToUpdate = this.params.size() - 1;
                while (paramToUpdate > -1 && !this.baseMapValueIterator.get(this.params.get(paramToUpdate)).hasNext()) {
                    String param = this.params.get(paramToUpdate);
                    this.baseMapValueIterator.put(param, this.parameterValues.get(param).iterator());
                    paramToUpdate = paramToUpdate - 1;
                }

                if (paramToUpdate < 0) {
                    this.next = null;
                    return;
                }

                while (paramToUpdate < this.params.size()) {
                    String param = this.params.get(paramToUpdate++);
                    T value = this.baseMapValueIterator.get(param).next();
                    if (value == null) {
                        this.next.remove(param);
                    } else {
                        this.next.put(param, value);
                    }
                }

                if (filter.include(this.next)) {
                    filteredTotal++;
                    return;
                }
            }
        }


        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public Map<String, T> next() {
            Map<String, T> lastNext = this.next;
            findNext();
            return lastNext;
        }

        @Override
        public void remove() {
            throw new RuntimeException("Cannot remove() from PermutationIterator");
        }
    }

    private static class IncludeAllFilter<T> implements CollectionFilter<Map<String, T>> {
        @Override
        public boolean include(Map<String, T> obj) {
            return true;
        }
    }
}
