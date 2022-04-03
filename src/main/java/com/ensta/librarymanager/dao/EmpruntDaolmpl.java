package com.ensta.librarymanager.dao;

import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.modele.Emprunt;
import com.ensta.librarymanager.persistence.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpruntDaolmpl implements EmpruntDao {
    private static EmpruntDaolmpl instance;

    private EmpruntDaolmpl() {
    }

    public static EmpruntDaolmpl getInstance() {
        if (instance == null) {
            instance = new EmpruntDaolmpl();
        }
        return instance;
    }

    @Override
    public List<Emprunt> getList() throws DaoException {

        List<Emprunt> emprunts = new ArrayList<Emprunt>();
        Statement statement = null;
        ResultSet resultat = null;
        EmpruntMapper mapper = EmpruntMapper.getInstance();

        try (Connection connexion = ConnectionManager.getConnection()) {
            statement = connexion.createStatement();
            resultat = statement.executeQuery(
                    "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, "
                            + " abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM "
                            + "emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre "
                            + "ON livre.id = e.idLivre ORDER BY dateRetour DESC;");

            while (resultat.next()) {
                Emprunt emprunt = mapper.get_from_sql(resultat);
                emprunts.add(emprunt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emprunts;
    }

    @Override
    public List<Emprunt> getListCurrent() throws DaoException {
        List<Emprunt> emprunts = new ArrayList<Emprunt>();
        Statement statement = null;
        ResultSet resultat = null;
        EmpruntMapper mapper = EmpruntMapper.getInstance();

        try (Connection connexion = ConnectionManager.getConnection()) {
            statement = connexion.createStatement();
            resultat = statement.executeQuery(
                    "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, "
                            + "telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, "
                            + "dateRetour "
                            + "FROM emprunt AS e "
                            + "INNER JOIN membre ON membre.id = e.idMembre "
                            + "INNER JOIN livre ON livre.id = e.idLivre "
                            + "WHERE dateRetour IS NULL;");

            while (resultat.next()) {
                Emprunt emprunt = mapper.get_from_sql(resultat);
                emprunts.add(emprunt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emprunts;
    }

    @Override
    public List<Emprunt> getListCurrentByMembre(int idMembre) throws DaoException {
        List<Emprunt> emprunts = new ArrayList<Emprunt>();
        PreparedStatement preparedStatement = null;
        ResultSet resultat = null;
        EmpruntMapper mapper = EmpruntMapper.getInstance();
        try (Connection connexion = ConnectionManager.getConnection()) {
            preparedStatement = connexion.prepareStatement("SELECT e.id AS id, idMembre, nom, prenom, adresse, email, "
                    + "telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, "
                    + "dateRetour "
                    + "FROM emprunt AS e "
                    + "INNER JOIN membre ON membre.id = e.idMembre "
                    + "INNER JOIN livre ON livre.id = e.idLivre "
                    + "WHERE dateRetour IS NULL AND membre.id = ?; ");
            preparedStatement.setInt(1, idMembre);

            resultat = preparedStatement.executeQuery();

            while (resultat.next()) {
                Emprunt emprunt = mapper.get_from_sql(resultat);
                emprunts.add(emprunt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emprunts;
    }

    @Override
    public List<Emprunt> getListCurrentByLivre(int idLivre) throws DaoException {
        List<Emprunt> emprunts = new ArrayList<Emprunt>();
        PreparedStatement preparedStatement = null;
        ResultSet resultat = null;
        EmpruntMapper mapper = EmpruntMapper.getInstance();
        try (Connection connexion = ConnectionManager.getConnection()) {
            preparedStatement = connexion.prepareStatement("SELECT e.id AS id, idMembre, nom, prenom, adresse, email, "
                    + "telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, "
                    + "dateRetour "
                    + "FROM emprunt AS e "
                    + "INNER JOIN membre ON membre.id = e.idMembre "
                    + "INNER JOIN livre ON livre.id = e.idLivre "
                    + "WHERE dateRetour IS NULL AND livre.id = ?;");
            preparedStatement.setInt(1, idLivre);

            resultat = preparedStatement.executeQuery();

            while (resultat.next()) {
                Emprunt emprunt = mapper.get_from_sql(resultat);
                emprunts.add(emprunt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emprunts;
    }

    @Override
    public Emprunt getById(int id) throws DaoException {
        Emprunt emprunt = new Emprunt();
        PreparedStatement preparedStatement = null;
        ResultSet resultat = null;
        EmpruntMapper mapper = EmpruntMapper.getInstance();

        try (Connection connexion = ConnectionManager.getConnection()) {
            preparedStatement = connexion
                    .prepareStatement("SELECT e.id AS idEmprunt, idMembre, nom, prenom, adresse, email, "
                            + "telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, "
                            + "dateRetour "
                            + "FROM emprunt AS e "
                            + "INNER JOIN membre ON membre.id = e.idMembre "
                            + "INNER JOIN livre ON livre.id = e.idLivre "
                            + "WHERE e.id = ?; ");
            preparedStatement.setInt(1, id);

            resultat = preparedStatement.executeQuery();
            while (resultat.next())
                emprunt = mapper.get_from_sql(resultat);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("emprunt not found ");
        }
        return emprunt;
    }

    @Override
    public void create(Emprunt emprunt) throws DaoException {
        PreparedStatement preparedStatement = null;

        try (Connection connexion = ConnectionManager.getConnection()) {
            preparedStatement = connexion
                    .prepareStatement("INSERT INTO emprunt(idMembre, idLivre, dateEmprunt, dateRetour) "
                            + "VALUES (?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, emprunt.getIdMembre());
            preparedStatement.setInt(2, emprunt.getIdLivre());

            preparedStatement.setString(3, emprunt.getDateEmprunt() + "");
            if (emprunt.getDateRetour() == null)
                preparedStatement.setString(4, null);
            else
                preparedStatement.setString(4, emprunt.getDateRetour() + "");

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("emprunt not found");
        }
    }

    @Override
    public void update(Emprunt emprunt) throws DaoException {
        PreparedStatement preparedStatement = null;

        try (Connection connexion = ConnectionManager.getConnection()) {
            preparedStatement = connexion
                    .prepareStatement("UPDATE emprunt "
                            + "SET idMembre = ?, idLivre = ?, dateEmprunt = ?, dateRetour = ? "
                            + "WHERE id = ?;");
            preparedStatement.setInt(1, emprunt.getIdMembre());
            preparedStatement.setInt(2, emprunt.getIdLivre());

            preparedStatement.setString(3, emprunt.getDateEmprunt() + "");
            preparedStatement.setString(4, emprunt.getDateRetour() + "");
            preparedStatement.setInt(5, emprunt.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("emprunt not found");
        }
    }

    @Override
    public int count() throws DaoException {
        int count = -1;
        Statement statement = null;

        try (Connection connection = ConnectionManager.getConnection()) {
            statement = connection.createStatement();
            ResultSet res = statement.executeQuery(
                    "SELECT COUNT(id) AS count FROM emprunt;");

            if (res.next()) {
                count = res.getInt("count");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }
}