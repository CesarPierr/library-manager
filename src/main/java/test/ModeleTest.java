package test;

import com.ensta.librarymanager.dao.MembreDaolmpl;
import com.ensta.librarymanager.exception.DaoException;

public class ModeleTest {
    public static void main(String args[]) throws DaoException {
        MembreDaolmpl modele = MembreDaolmpl.getInstance();

        System.out.println(modele.getList());
    }
}
