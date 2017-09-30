package me.dslztx.assist.struct;

import java.util.*;

public class LinkedListSet {

    private LinkedList<String> list;

    public LinkedListSet() {
        list = new LinkedList<String>();
    }

    public int size() {
        return list.size();
    }

    public void addOrdered(ArrayList<String> toAdd) {
        if (list.size() == 0) {
            list = new LinkedList<String>(toAdd);
            return;
        }

        LinkedList<String> result = new LinkedList<String>();

        ListIterator<String> aIterator = list.listIterator();
        int bIndex = 0;

        String a = aIterator.next();
        String b = toAdd.get(bIndex++);

        int comp = 0;
        boolean aEnd = false;
        boolean bEnd = false;

        while (true) {
            comp = a.compareTo(b);

            if (comp == 0) {
                if (result.size() == 0 || !a.equals(result.getLast())) {
                    result.add(a);
                }
                if (!aIterator.hasNext() && bIndex == toAdd.size()) {
                    aEnd = true;
                    bEnd = true;
                    break;
                } else if (!aIterator.hasNext()) {
                    aEnd = true;
                    b = toAdd.get(bIndex++);
                    break;
                } else if (bIndex == toAdd.size()) {
                    bEnd = true;
                    a = aIterator.next();
                    break;
                } else {
                    a = aIterator.next();
                    b = toAdd.get(bIndex++);
                }
            } else if (comp < 0) {
                if (result.size() == 0 || !a.equals(result.getLast())) {
                    result.add(a);
                }
                if (!aIterator.hasNext()) {
                    aEnd = true;
                    break;
                } else {
                    a = aIterator.next();
                }
            } else {
                if (result.size() == 0 || !b.equals(result.getLast())) {
                    result.add(b);
                }

                if (bIndex == toAdd.size()) {
                    bEnd = true;
                    break;
                } else {
                    b = toAdd.get(bIndex++);
                }
            }
        }

        String value;
        if (aEnd && bEnd) {
            list = result;
        } else if (bEnd) {
            value = a;
            if (result.size() == 0 || !result.getLast().equals(value)) {
                result.add(value);
            }
            while (aIterator.hasNext()) {
                value = aIterator.next();
                if (result.size() == 0 || !result.getLast().equals(value)) {
                    result.add(value);
                }
            }
            list = result;
        } else {
            value = b;
            if (result.size() == 0 || !result.getLast().equals(value)) {
                result.add(value);
            }
            while (bIndex < toAdd.size()) {
                value = toAdd.get(bIndex++);
                if (result.size() == 0 || !result.getLast().equals(value)) {
                    result.add(value);
                }
            }
            list = result;
        }
    }


    public void addUnordered(ArrayList<String> toAdd) {
        Collections.sort(toAdd);

        addOrdered(toAdd);
    }

    public List<String> getList() {
        return list;
    }

    public static void main(String[] args) {
        LinkedListSet set = new LinkedListSet();
        ArrayList<String> a = new ArrayList<String>();
        a.add("1");
        a.add("2");
        a.add("3");
        a.add("5");
        set.addUnordered(a);

        System.out.println(set.getList());

        ArrayList<String> b = new ArrayList<String>();
        b.add("3");
        b.add("4");
        set.addUnordered(b);
        System.out.println(set.getList());

        ArrayList<String> c = new ArrayList<String>();
        c.add("10");
        c.add("10");
        c.add("11");
        c.add("10");
        set.addUnordered(c);
        System.out.println(set.getList());
    }
}
