package com.ensta.librarymanager.dao;

import com.ensta.librarymanager.modele.Livre;
import java.sql.*;

public class LivreMapper {
    private static LivreMapper instance;

    private LivreMapper() {
    }

    public static LivreMapper getInstance() {
        if (instance == null) {
            instance = new LivreMapper();
        }
        return instance;
    }

    Livre get_from_sql(ResultSet resultat) throws SQLException {
        int id = resultat.getInt("id");
        String titre = resultat.getString("titre");
        String auteur = resultat.getString("auteur");
        String isbn = resultat.getString("isbn");

        Livre livre = new Livre();

        livre.setId(id);
        livre.setAuteur(auteur);
        livre.setIsbn(isbn);
        livre.setTitre(titre);
        return livre;
    }
}
