package ToDoProject.core;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class TodoListTest {

    private List<String> list = new LinkedList<>();
    private List<String> TempList = new LinkedList<>();
    private int Element;
    @Test
    public void moveUp() {
        if(Element > 0){
            list.add("Element");
            list.add("Element2");
            String swap = this.list.get(1);
            this.list.set(0, "Element");
            this.TempList.set(1, swap);
            String Expected = TempList.get(Element);
            String Actual = list.get(Element);
            assertEquals(Expected,Actual);
        }

    }

    @Test
    public void moveDown() {
        if(Element > 0){
            list.add("Element");
            list.add("Element2");
            String swap = this.list.get(1);
            this.list.set(1, "Element");
            this.TempList.set(0, swap);
            String Expected = TempList.get(Element);
            String Actual = list.get(Element);
            assertEquals(Expected,Actual);
        }
    }

    @Test
    public void add() {
        TempList.add("Element");
        list.add("Element");
        assertEquals(TempList,list);
    }

    @Test
    public void removeAt() {
        if (Element >= 0 && Element < this.list.size()) {
            this.list.remove(Element);
        }
    }
}