package by.fertigi.app.util;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Locker {
    public static boolean locker = false;
    private List<String> listRow = new ArrayList<>();

    public Locker() {
    }

    public static boolean isLocker() {
        return locker;
    }

    public static void setLocker(boolean locker) {
        Locker.locker = locker;
    }

    public List<String> getListRow() {
        return listRow;
    }

    public void setListRow(List<String> listRow) {
        this.listRow = listRow;
    }

    public void addElement(String string){
        listRow.add(string);
    }


}
