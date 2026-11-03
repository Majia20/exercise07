package it.unibo.inner.impl;

import it.unibo.inner.api.IterableWithPolicy;
import it.unibo.inner.api.Predicate;
import java.util.Iterator;

public class GenericIterableWithPolicy<T> implements IterableWithPolicy<T>{

    Predicate<T> p;
    private T[] array;

    public GenericIterableWithPolicy ( T[] array, Predicate<T> p ){
        this.array = array;
        this.p = p;
    }
    
    public GenericIterableWithPolicy ( T[] array ){
        
        this(array, new Predicate<T>(){
            public boolean test(T element){
                return true;
            }
        });
    }

    public void setIterationPolicy(Predicate<T> filter){
        this.p = filter;
    }

    class innerIterator implements Iterator<T>{

        private static final int START_INDEX = 0; 
        
        private T[] array;
        int i;

        public innerIterator(T[] array){
            this.array = array;
            i = START_INDEX;
        }

        public boolean hasNext(){
            while(i < array.length){
                if(GenericIterableWithPolicy.this.p.test(array[i])){
                    return true;
                }
                i++;
            }
            
            return false;
        }

        public T next(){
            if (GenericIterableWithPolicy.this.p.test(array[i])){
                T next = array[i];
                i++;
                return next;
            }
            hasNext();
            T next = array[i];
            i++;
            return next;
        }
    }

    public Iterator<T> iterator(){
        return new innerIterator(array);
    }
 
    public String toString(){
        String toPrint="";
        Iterator<T> iter = new innerIterator(this.array);
        while(iter.hasNext()){
            toPrint = toPrint + " " + iter.next();
        }
        return toPrint;
    }
} 