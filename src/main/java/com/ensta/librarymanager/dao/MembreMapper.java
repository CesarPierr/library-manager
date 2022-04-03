package com.ensta.librarymanager.dao;

import com.ensta.librarymanager.modele.Membre;
import java.sql.*;
import com.ensta.librarymanager.utils.Abonnement;

public class MembreMapper {
    private static MembreMapper instance;

    private MembreMapper() {
    }

    public static MembreMapper getInstance() {
        if (instance == null) {
            instance = new MembreMapper();
        }
        return instance;
    }

    Membre get_from_sql(ResultSet resultat) throws SQLException {
        int id = resultat.getInt("id");
        String nom = resultat.getString("nom");
        String prenom = resultat.getString("prenom");
        String adresse = resultat.getString("adresse");
        String email = resultat.getString("email");
        String telephone = resultat.getString("telephone");
        Abonnement abonnement = Abonnement.get_from_string(resultat.getString("abonnement"));
        Membre membre = new Membre();

        membre.setId(id);
        membre.setNom(nom);
        membre.setPrenom(prenom);
        membre.setAdresse(adresse);
        membre.setEmail(email);
        membre.setTelephone(telephone);
        membre.setAbonnement(abonnement);
        return membre;
    }
}
