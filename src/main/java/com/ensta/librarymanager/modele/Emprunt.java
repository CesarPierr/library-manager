package com.ensta.librarymanager.modele;

import java.time.LocalDate;

import com.ensta.librarymanager.dao.LivreDaolmpl;
import com.ensta.librarymanager.dao.MembreDaolmpl;
import com.ensta.librarymanager.exception.DaoException;

public class Emprunt {
    private int id;
    private int idMembre;
    private int idLivre;
    private LocalDate dateEmprunt;
    private LocalDate dateRetour;

    public Emprunt() {
    }

    public Emprunt(int id, int idMembre, int idLivre, LocalDate dateEmprunt, LocalDate dateRetour) {
        this.id = id;
        this.idMembre = idMembre;
        this.idLivre = idLivre;
        this.dateEmprunt = dateEmprunt;
        this.dateRetour = dateRetour;
    }

    public Livre getLivre() throws DaoException {
        LivreDaolmpl dao = LivreDaolmpl.getInstance();
        return dao.getById(idLivre);
    }

    public Membre getMembre() throws DaoException {
        MembreDaolmpl dao = MembreDaolmpl.getInstance();
        return dao.getById(idMembre);
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdMembre() {
        return this.idMembre;
    }

    public void setIdMembre(int idMembre) {
        this.idMembre = idMembre;
    }

    public int getIdLivre() {
        return this.idLivre;
    }

    public void setIdLivre(int idLivre) {
        this.idLivre = idLivre;
    }

    public LocalDate getDateEmprunt() {
        return this.dateEmprunt;
    }

    public void setDateEmprunt(LocalDate dateEmprunt) {
        this.dateEmprunt = dateEmprunt;
    }

    public LocalDate getDateRetour() {
        return this.dateRetour;
    }

    public void setDateRetour(LocalDate dateRetour) {
        this.dateRetour = dateRetour;
    }

    public Emprunt id(int id) {
        setId(id);
        return this;
    }

    public Emprunt idMembre(int idMembre) {
        setIdMembre(idMembre);
        return this;
    }

    public Emprunt idLivre(int idLivre) {
        setIdLivre(idLivre);
        return this;
    }

    public Emprunt dateEmprunt(LocalDate dateEmprunt) {
        setDateEmprunt(dateEmprunt);
        return this;
    }

    public Emprunt dateRetour(LocalDate dateRetour) {
        setDateRetour(dateRetour);
        return this;
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", idMembre='" + getIdMembre() + "'" +
                ", idLivre='" + getIdLivre() + "'" +
                ", dateEmprunt='" + getDateEmprunt() + "'" +
                ", dateRetour='" + getDateRetour() + "'" +
                "}";
    }

}
