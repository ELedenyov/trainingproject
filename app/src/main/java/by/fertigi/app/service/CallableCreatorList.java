package by.fertigi.app.service;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@Component
public class CallableCreatorList<T> {
    public List<Callable<T>> getListCallable(Callable<T> callable, Integer listSize){
        ArrayList<Callable<T>> tasks = new ArrayList<>();
        for (int i = 0; i < listSize; i++) {
            tasks.add(callable);
        }
        return tasks;
    }
}
