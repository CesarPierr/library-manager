package test;

import com.ensta.librarymanager.modele.*;
import com.ensta.librarymanager.dao.*;

public class DaoTest {
    public static void main(String args[]) {
        Livre l = new Livre(1, "woulah", "robespierre", "hello");
        System.out.println(l);
    }
}
