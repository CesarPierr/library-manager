package com.ensta.librarymanager.service;

import com.ensta.librarymanager.exception.*;
import com.ensta.librarymanager.modele.*;
import com.ensta.librarymanager.utils.Abonnement;
import com.ensta.librarymanager.dao.*;

import java.util.ArrayList;
import java.util.List;

public class MembreServicelmpl implements MembreService {
    private static MembreServicelmpl instance;

    private MembreServicelmpl() {
    }

    public static MembreServicelmpl getInstance() {
        if (instance == null) {
            instance = new MembreServicelmpl();
        }
        return instance;
    }

    @Override
    public List<Membre> getList() throws ServiceException {
        MembreDao membreDao = MembreDaolmpl.getInstance();
        List<Membre> membres = new ArrayList<Membre>();
        try {
            membres = membreDao.getList();
        } catch (DaoException e) {
            System.out.println(e.getMessage());
        }

        return (membres);
    }

    @Override
    public List<Membre> getListMembreEmpruntPossible() throws ServiceException {
        MembreDao membreDao = MembreDaolmpl.getInstance();
        EmpruntService empruntService = EmpruntServicelmpl.getInstance();

        List<Membre> membres = new ArrayList<Membre>();
        List<Membre> membresEmpruntPossible = new ArrayList<Membre>();
        try {
            membres = membreDao.getList();
            for (Membre m : membres) {
                if (empruntService.isEmpruntPossible(m)) {
                    membresEmpruntPossible.add(m);
                }
            }
        } catch (DaoException e) {
            System.out.println(e.getMessage());
        }

        return (membresEmpruntPossible);
    }

    @Override
    public Membre getById(int id) throws ServiceException {
        MembreDao membreDao = MembreDaolmpl.getInstance();
        Membre membre = new Membre();
        try {
            membre = membreDao.getById(id);
        } catch (DaoException e) {
            System.out.println(e.getMessage());
        }
        return membre;
    }

    @Override
    public int create(Membre membre)
            throws ServiceException {
        int id = -1;

        /// Vérifions que le membre dispose d'un nom et d'un prénom non-vide
        if (membre.getNom().isEmpty() || membre.getPrenom().isEmpty()) {
            /*
             * Notons qu'on n'a pas précisé dans les specs si le prénom et le nom du membre
             * devait contenir uniquement des caractères alphanumériques,
             * traits-d'unions ou espaces
             */
            throw new ServiceException();
        } else {
            MembreDao membreDao = MembreDaolmpl.getInstance();
            try {
                // On passe le nom du membre en majuscules
                membre.setNom(membre.getNom().toUpperCase());
                id = membreDao.create(membre);
            } catch (DaoException e) {
                System.out.println(e.getMessage());
            }
        }

        return id;
    }

    @Override
    public void update(Membre membre) throws ServiceException {
        MembreDao membreDao = MembreDaolmpl.getInstance();
        String nom = membre.getNom();
        if (nom.isEmpty() || (membre.getPrenom()).isEmpty()) {
            /*
             * Notons qu'on n'a pas précisé dans les specs si le prénom et le nom du membre
             * devait contenir uniquement des caractères alphanumériques,
             * traits-d'unions ou espaces
             */
            throw new ServiceException();
        } else {
            try {
                membre.setNom(nom.toUpperCase());
                membreDao.update(membre);
            } catch (DaoException e) {
                System.out.println(e.getMessage());
            }
        }

    }

    @Override
    public void delete(int id) throws ServiceException {
        MembreDao membreDao = MembreDaolmpl.getInstance();
        try {
            membreDao.delete(id);
        } catch (DaoException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public int count() throws ServiceException {
        MembreDao membreDao = MembreDaolmpl.getInstance();
        int counter = -1;
        try {
            counter = membreDao.count();
        } catch (DaoException e) {
            System.out.println(e.getMessage());
        }

        return (counter);
    }

}