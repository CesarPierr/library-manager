package com.ensta.librarymanager.dao;

import com.ensta.librarymanager.modele.Emprunt;
import java.sql.*;
import java.time.LocalDate;

public class EmpruntMapper {
    private static EmpruntMapper instance;

    private EmpruntMapper() {
    }

    public static EmpruntMapper getInstance() {
        if (instance == null) {
            instance = new EmpruntMapper();
        }
        return instance;
    }

    Emprunt get_from_sql(ResultSet resultat) throws SQLException {
        int id = resultat.getInt("id");
        int idMembre = resultat.getInt("idMembre");
        int idLivre = resultat.getInt("idLivre");
        Date dateEmpruntr = resultat.getDate("dateEmprunt");
        LocalDate dateEmprunt = dateEmpruntr.toLocalDate();
        Date dateRetourr = resultat.getDate("dateRetour") == null ? null : resultat.getDate("dateRetour");
        LocalDate dateRetour = dateRetourr == null ? null : dateRetourr.toLocalDate();

        Emprunt emprunt = new Emprunt();

        emprunt.setId(id);
        emprunt.setIdLivre(idLivre);
        emprunt.setIdMembre(idMembre);
        emprunt.setDateEmprunt(dateEmprunt);
        emprunt.setDateRetour(dateRetour);

        return emprunt;
    }
}
