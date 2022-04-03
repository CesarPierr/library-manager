package com.ensta.librarymanager.service;

import com.ensta.librarymanager.exception.*;
import com.ensta.librarymanager.modele.*;
import com.ensta.librarymanager.dao.*;
import com.ensta.librarymanager.utils.Abonnement;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmpruntServicelmpl implements EmpruntService {
    private static EmpruntServicelmpl instance;

    private EmpruntServicelmpl() {
    }

    public static EmpruntServicelmpl getInstance() {
        if (instance == null) {
            instance = new EmpruntServicelmpl();
        }
        return instance;
    }

    @Override
    public List<Emprunt> getList() throws ServiceException {
        EmpruntDaolmpl empruntDao = EmpruntDaolmpl.getInstance();
        List<Emprunt> emprunts = new ArrayList<Emprunt>();

        try {
            emprunts = empruntDao.getList();
        } catch (DaoException e) {
            System.out.println(e.getMessage());
        }

        return emprunts;
    }

    @Override
    public List<Emprunt> getListCurrent() throws ServiceException {
        EmpruntDaolmpl empruntDao = EmpruntDaolmpl.getInstance();
        List<Emprunt> emprunts = new ArrayList<Emprunt>();

        try {
            emprunts = empruntDao.getListCurrent();
        } catch (DaoException e) {
            System.out.println(e.getMessage());
        }

        return emprunts;
    }

    @Override
    public List<Emprunt> getListCurrentByMembre(int idMembre) throws ServiceException {
        EmpruntDaolmpl empruntDao = EmpruntDaolmpl.getInstance();
        List<Emprunt> emprunts = new ArrayList<Emprunt>();

        try {
            emprunts = empruntDao.getListCurrentByMembre(idMembre);
        } catch (DaoException e) {
            System.out.println(e.getMessage());
        }

        return emprunts;
    }

    @Override
    public List<Emprunt> getListCurrentByLivre(int idLivre) throws ServiceException {
        EmpruntDaolmpl empruntDao = EmpruntDaolmpl.getInstance();
        List<Emprunt> emprunts = new ArrayList<Emprunt>();

        try {
            emprunts = empruntDao.getListCurrentByLivre(idLivre);
        } catch (DaoException e) {
            System.out.println(e.getMessage());
        }

        return emprunts;
    }

    @Override
    public Emprunt getById(int id) throws ServiceException {
        EmpruntDaolmpl empruntDao = EmpruntDaolmpl.getInstance();
        Emprunt emprunt = null;

        try {
            emprunt = empruntDao.getById(id);
        } catch (DaoException e) {
            System.out.println(e.getMessage());
        }

        return emprunt;
    }

    @Override
    public void create(Emprunt emprunt) throws ServiceException {
        EmpruntDaolmpl empruntDao = EmpruntDaolmpl.getInstance();

        try {
            empruntDao.create(emprunt);
        } catch (DaoException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void returnBook(int id) throws ServiceException {
        EmpruntDaolmpl empruntDao = EmpruntDaolmpl.getInstance();

        try {
            Emprunt emprunt = empruntDao.getById(id);
            emprunt.setDateRetour(LocalDate.now());
            empruntDao.update(emprunt);
        } catch (DaoException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public int count() throws ServiceException {
        EmpruntDaolmpl empruntDao = EmpruntDaolmpl.getInstance();
        int count = 0;

        try {
            count = empruntDao.count();
        } catch (DaoException e) {
            System.out.println(e.getMessage());
        }
        return count;
    }

    @Override
    public boolean isLivreDispo(int idLivre) throws ServiceException {
        EmpruntDaolmpl empruntDao = EmpruntDaolmpl.getInstance();
        boolean res = false;

        try {
            List<Emprunt> listLivre = empruntDao.getListCurrentByLivre(idLivre);
            if (listLivre.size() == 0) {
                res = true;
            }
        } catch (DaoException e) {
            System.out.println(e.getMessage());
        }
        return res;
    }

    @Override
    public boolean isEmpruntPossible(Membre membre) throws ServiceException {
        EmpruntDaolmpl empruntDao = EmpruntDaolmpl.getInstance();
        boolean res = false;

        try {
            List<Emprunt> listemprunt = empruntDao.getListCurrentByMembre(membre.getId());
            Abonnement abo = membre.getAbonnement();
            int n_emprunts = listemprunt.size();
            switch (abo) {
                case BASIC:
                    if (n_emprunts < 2) {
                        res = true;
                    }
                    break;
                case PREMIUM:
                    if (n_emprunts < 5) {
                        res = true;
                    }
                    break;
                case VIP:
                    if (n_emprunts < 20) {
                        res = true;
                    }
                    break;
            }
        } catch (DaoException e) {
            System.out.println(e.getMessage());
        }
        return res;
    }
}
