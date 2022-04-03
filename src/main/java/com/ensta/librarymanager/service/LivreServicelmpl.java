package com.ensta.librarymanager.service;

import com.ensta.librarymanager.exception.*;
import com.ensta.librarymanager.modele.*;
import com.ensta.librarymanager.dao.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class LivreServicelmpl implements LivreService {
    private static LivreServicelmpl instance;

    private LivreServicelmpl() {
    }

    public static LivreServicelmpl getInstance() {
        if (instance == null) {
            instance = new LivreServicelmpl();
        }
        return instance;
    }

    @Override
    public List<Livre> getList() throws ServiceException {
        LivreDaolmpl livreDao = LivreDaolmpl.getInstance();
        List<Livre> livres = new ArrayList<Livre>();

        try {
            livres = livreDao.getList();
        } catch (DaoException e) {
            System.out.println(e.getMessage());
        }

        return livres;
    }

    @Override
    public List<Livre> getListDispo() throws ServiceException {
        LivreDaolmpl livreDao = LivreDaolmpl.getInstance();
        EmpruntServicelmpl empruntService = EmpruntServicelmpl.getInstance();
        List<Livre> livres = new ArrayList<Livre>();

        try {
            livres = livreDao.getList();
            Iterator<Livre> iter = livres.listIterator();

            while (iter.hasNext()) {
                Livre livre = iter.next();
                if (!empruntService.isLivreDispo(livre.getId())) {
                    iter.remove();
                }
            }

        } catch (DaoException e) {
            System.out.println(e.getMessage());
        }

        return livres;
    }

    @Override
    public Livre getById(int id) throws ServiceException {
        LivreDaolmpl livreDao = LivreDaolmpl.getInstance();
        Livre livre = null;

        try {
            livre = livreDao.getById(id);
        } catch (DaoException e) {
            System.out.println(e.getMessage());
        }

        return livre;
    }

    @Override
    public int create(Livre livre) throws ServiceException {
        LivreDaolmpl livreDao = LivreDaolmpl.getInstance();
        int id = 0;
        if (livre.getTitre() == null || livre.getTitre() == "") {
            throw new ServiceException();
        }

        try {
            id = livreDao.create(livre);
        } catch (DaoException e) {
            System.out.println(e.getMessage());
        }

        return id;
    }

    @Override
    public void update(Livre livre) throws ServiceException {
        LivreDaolmpl livreDao = LivreDaolmpl.getInstance();
        if (livre.getTitre() == null || livre.getTitre() == "") {
            throw new ServiceException();
        }

        try {
            livreDao.update(livre);
        } catch (DaoException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void delete(int id) throws ServiceException {
        LivreDaolmpl livreDao = LivreDaolmpl.getInstance();

        try {
            livreDao.delete(id);
        } catch (DaoException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public int count() throws ServiceException {
        LivreDaolmpl livreDao = LivreDaolmpl.getInstance();
        int count = 0;

        try {
            count = livreDao.count();
        } catch (DaoException e) {
            System.out.println(e.getMessage());
        }

        return count;
    }

}