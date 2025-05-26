package exercises;


import java.util.*;

class Stack<T> {

}

class Outside {
    public int counter=5;
    static class Inside {
        public Inside() {
            System.out.println("Created Inside object");
        }
    }
    class Inner {
        private static int counter2=2;
        public int getCounter() {
            return counter;
        }
    }
}

public class Practice {
//    static void printCollection(Collection c) {
//        Iterator iterator=c.iterator();
//        for(int k=0; k<c.size(); k++) {
//            System.out.println(iterator.next());
//        }
//    }
    static void printCollection(Collection c) {
        for(Object o: c) {
            System.out.println(o);
        }
    }
    public static void main(String[] args) {
        List<Integer> list=new ArrayList<>();
        list.add(1);
        list.add(3);
        list.add(1, 2);
        list.set(1, 4);
        System.out.println(list.indexOf(3));
        list.remove((Integer) 1);
        list.get(1);
        if(list.contains(1)) System.out.println("List contains 1");

        ArrayList<Integer> arrayList=new ArrayList<>();
        LinkedList<Integer> linkedList=new LinkedList<>();
        linkedList.addFirst(1);
        linkedList.addLast(2);
        linkedList.addLast(3);
        linkedList.getFirst(); linkedList.getLast();
        linkedList.remove((Integer) 2);

        Stack<Integer> s=new Stack<>();
        s.push(1);
        s.push(2);
        s.pop();
        s.empty();

        Queue<Integer> q=new LinkedList<>();
        q.add(1);
        q.add(2);
        q.add(3);
        q.remove();
        System.out.println(q.poll());
        System.out.println(q);

        Iterator<Integer> iterator=q.iterator();
        while(iterator.hasNext()) {
            System.out.println(iterator.next() + " ");
        }

        Map<String, Integer> map=new HashMap<>();
        map.put("Toyota", 1);
        map.put("Ford", 2);
        map.put("BMW", 3);
        map.put("Ford", 4);
        for(Map.Entry<String, Integer> e: map.entrySet()) {
            System.out.println(e.getKey() + " " + e.getValue());
        }
        NavigableMap<String, Integer> map2=new TreeMap<>();
        map2.put("ABC", 1);
        map2.put("DEF", 2);
        map2.put("GHI", 3);
        System.out.println(map2.ceilingEntry("ABC").getValue());
        Practice.printCollection(linkedList);
        Outside.Inside example=new Outside.Inside();
        Outside outer=new Outside();
        Outside.Inner inner=outer.new Inner();
        System.out.println(1+2+"3");
        System.out.println("3"+2+1);
    }
}
