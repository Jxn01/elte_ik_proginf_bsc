package bead8;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Using IntList with Array");

        IntList intlistWarray1 = new IntList(6);
        IntList intlistWarray2 = new IntList(6);

        intlistWarray1.add(1);
        intlistWarray1.add(2);
        intlistWarray1.add(3);
        intlistWarray2.add(4);
        intlistWarray2.add(5);

        System.out.print("Array1: ");
        System.out.println(intlistWarray1.toString());

        System.out.print("Array2: ");
        System.out.println(intlistWarray2.toString()+"\n");

        System.out.println("Concatenating Array1 with Array2");

        intlistWarray1.concat(intlistWarray2);

        System.out.println("Array1 after concatenated with Array2: ");
        System.out.println(intlistWarray1.toString()+"\n");

        System.out.println("Removing items greater than 3 from Array1");

        intlistWarray1.removeItemsGreaterThan(3);

        System.out.println("Array1 after removing the items: ");
        System.out.println(intlistWarray1.toString()+"\n");

        System.out.println("Using IntList with ArrayList");

        int[] array = {4, 5};
        IntListWArrayList intlistWarraylist1 = new IntListWArrayList(6);
        IntListWArrayList intlistWarraylist2 = new IntListWArrayList(array);
        intlistWarraylist1.add(1);
        intlistWarraylist1.add(2);
        intlistWarraylist1.add(3);

        System.out.print("Array1: ");
        System.out.println(intlistWarraylist1.toString());

        System.out.print("Array2: ");
        System.out.println(intlistWarraylist2.toString()+"\n");

        System.out.println("Concatenating Array1 with Array2");

        intlistWarraylist1.concat(intlistWarraylist2);

        System.out.println("Array1 after concatenated with Array2: ");
        System.out.println(intlistWarraylist1.toString()+"\n");

        System.out.println("Removing items greater than 3 from Array1");

        intlistWarraylist1.removeItemsGreaterThan(3);

        System.out.println("Array1 after removing the items: ");
        System.out.println(intlistWarraylist1.toString()+"\n");

        System.out.println("Using named IntList with ArrayList");

        NamedIntList namedIntList = new NamedIntList("Lista", 6);
        namedIntList.add(1);
        namedIntList.add(2);
        namedIntList.add(3);
        System.out.println(namedIntList.toString());
    }
}

class IntList {
    private int currentSize;
    private int maxSize;
    private int[] array;

    public IntList(int maxSize) {
        this.maxSize = maxSize;
        this.currentSize = 0;
        array = new int[maxSize];
    }

    void add(int number) {
        if (currentSize >= maxSize) {
            throw new IllegalStateException();
        }

        array[currentSize] = number;
        currentSize++;
    }

    int getIntAt(int index) {
        if (index < 0 || index >= maxSize) {
            throw new IllegalArgumentException();
        }
        return array[index];
    }

    int getSize() {
        return currentSize;
    }

    int getMaxSize() {
        return maxSize;
    }

    void concat(IntList intList) {
        if (this.maxSize - this.currentSize >= intList.getSize()) {
            for (int i = 0; i < intList.getSize(); i++) {
                this.array[currentSize] = intList.getIntAt(i);
                currentSize++;
            }
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public String toString() {
        if (this.currentSize == 0) {
            return "empty";
        } else {
            String result = "";
            for (int i = 0; i < currentSize; i++) {
                if (i != currentSize - 1) {
                    result += array[i] + ", ";
                } else {
                    result += array[i];
                }
            }
            return result;
        }
    }

    void removeItemsGreaterThan(int limit) {
        for (int i = 0; i < currentSize; i++) {
            if (array[i] > limit) {
                for (int j = i; j < currentSize - 1; j++) {
                    array[j] = array[j + 1];
                }
                array[currentSize - 1] = 0;
                currentSize--;
                i--;
            }
        }
    }
}

class IntListWArrayList {
    protected int currentSize;
    protected int maxSize;
    protected List<Integer> list;

    public IntListWArrayList(int maxSize) {
        this.maxSize = maxSize;
        this.currentSize = 0;
        list = new ArrayList<>();
    }

    public IntListWArrayList(int[] array) {
        this.maxSize = array.length;
        this.currentSize = array.length;
        list = new ArrayList<>();
        for (int elem : array) {
            list.add(elem);
        }
    }

    List<Integer> getData() {
        List<Integer> result = new ArrayList<>();
        result.addAll(list);
        return result;
    }

    void add(int number) {
        if (currentSize >= maxSize) {
            throw new IllegalStateException();
        }

        list.add(number);
        currentSize++;
    }

    int getIntAt(int index) {
        if (index < 0 || index >= maxSize) {
            throw new IllegalArgumentException();
        }
        return list.get(index);
    }

    int getSize() {
        return currentSize;
    }

    int getMaxSize() {
        return maxSize;
    }

    void concat(IntListWArrayList intList) {
        if (this.maxSize - this.currentSize >= intList.getSize()) {
            list.addAll(intList.getData());
            currentSize+=intList.currentSize;
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public String toString() {
        if (this.currentSize == 0) {
            return "empty";
        } else {
            String result = "";
            for (int i = 0; i < currentSize; i++) {
                if (i != currentSize - 1) {
                    result += list.get(i) + ", ";
                } else {
                    result += list.get(i);
                }
            }
            return result;
        }
    }

    void removeItemsGreaterThan(int limit) {
        int minus = 0;
        for(int elem : this.list){
            if(elem > limit){
                minus++;
            }
        }
        list.removeIf(elem -> (elem > limit));
        currentSize-=minus;
    }
}

class NamedIntList extends IntListWArrayList{
    private String name;

    public NamedIntList(String name, int maxSize){
        super(maxSize);
        this.name = name;
    }

    public NamedIntList(String name, int[] array){
        super(array);
        this.name = name;
    }

    @Override
    public String toString(){
        if (this.currentSize == 0) {
            return "empty";
        } else {
            String result = this.name+": ";
            for (int i = 0; i < currentSize; i++) {
                if (i != currentSize - 1) {
                    result += list.get(i) + ", ";
                } else {
                    result += list.get(i);
                }
            }
            return result;
        }
    }
}