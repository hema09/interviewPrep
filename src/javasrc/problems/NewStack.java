package javasrc.problems;

import java.util.EmptyStackException;

/**
 * Created by Hema on 3/3/2016.
 */
public class NewStack<T> {
    T val;
    int top = -1;
    int size = -1;
    T[] arr;

    public NewStack() {
        this.size = 5;
        arr = (T[])new Object[size];
    }
    public NewStack(int size) {
        this.size = size;
        arr = (T[])new Object[size];
    }
    public void push(T v) {
        if(top == size-1)
            throw new IllegalStateException("Stack full");
        arr[top++] = v;
    }
    public T pop() {
        if(top == -1)
            throw new EmptyStackException();
        T val = arr[top--];
        return val;
    }
    public T peek() {
        if(top == -1)
            throw new EmptyStackException();
        return arr[top];
    }

}

