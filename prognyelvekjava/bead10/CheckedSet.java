package bead10;

import java.util.HashSet;

public class CheckedSet<T> {
    private HashSet<T> hashset;

    public CheckedSet(){
        this.hashset = new HashSet<>();
    }

    public void add(T element) throws AlreadyContainedException{
        if(hashset.contains(element)){
            throw new AlreadyContainedException();
        }else{
            this.hashset.add(element);
        }
    }

    public boolean contains(T element){
        return this.hashset.contains(element);
    }

    public int getSize(){
        return this.hashset.size();
    }
}

class AlreadyContainedException extends Exception{
    public AlreadyContainedException(){
        super();
    }
}

class Main{
    public static void main (String [] args){
        CheckedSet<Integer> cs = new CheckedSet<>();
        try{
            cs.add(1);
            cs.add(2);
            System.out.println(cs.getSize());
            System.out.println(cs.contains(2));
            cs.add(2);
        }catch(AlreadyContainedException exc){
            exc.printStackTrace();
        }
    }
}
