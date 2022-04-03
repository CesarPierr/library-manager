package com.ensta.librarymanager.dao;

import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.modele.Membre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.ensta.librarymanager.persistence.ConnectionManager;
import com.ensta.librarymanager.utils.Abonnement;

public class MembreDaolmpl implements MembreDao {
    private static MembreDaolmpl instance;

    private MembreDaolmpl() {
    }

    public static MembreDaolmpl getInstance() {
        if (instance == null) {
            instance = new MembreDaolmpl();
        }
        return instance;
    }

    @Override
    public List<Membre> getList() throws DaoException {
        List<Membre> membres = new ArrayList<Membre>();
        Statement statement = null;
        ResultSet resultat = null;
        MembreMapper mapper = MembreMapper.getInstance();

        try (Connection connexion = ConnectionManager.getConnection()) {
            statement = connexion.createStatement();
            resultat = statement.executeQuery(
                    "SELECT id, nom, prenom, adresse, email, telephone, abonnement "
                            + "FROM membre "
                            + "ORDER BY nom, prenom;");

            while (resultat.next()) {
                Membre membre = mapper.get_from_sql(resultat);
                membres.add(membre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return membres;
    }

    @Override
    public Membre getById(int id) throws DaoException {
        MembreMapper mapper = MembreMapper.getInstance();
        Membre membre = new Membre();

        try (Connection connection = ConnectionManager.getConnection()) {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(
                            "SELECT id, nom, prenom, adresse, email, telephone, abonnement FROM membre WHERE id = ?;");

            preparedStatement.setInt(1, id);
            ResultSet res = preparedStatement.executeQuery();

            if (res.next()) {
                membre = mapper.get_from_sql(res);
            }

            preparedStatement.close();
            res.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return membre;
    }

    @Override
    public int create(Membre membre) throws DaoException {
        int id = -1;
        PreparedStatement statement = null;
        if (membre.getAbonnement() == null)
            membre.setAbonnement(Abonnement.BASIC);

        try (Connection connection = ConnectionManager.getConnection()) {
            statement = connection.prepareStatement(
                    "INSERT INTO membre(nom, prenom, adresse, email, telephone, abonnement) VALUES (?, ?, ?, ?, ?, ?);",
                    Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, membre.getNom());
            statement.setString(2, membre.getPrenom());
            statement.setString(3, membre.getAdresse());
            statement.setString(4, membre.getEmail());
            statement.setString(5, membre.getTelephone());
            statement.setString(6, membre.getAbonnement() + "");
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }

    @Override
    public void update(Membre membre) throws DaoException {
        PreparedStatement statement = null;

        try (Connection connection = ConnectionManager.getConnection()) {
            statement = connection.prepareStatement(
                    "UPDATE membre"
                            + "SET nom = ?, prenom = ?, adresse = ?, email = ?, telephone = ?, "
                            + "abonnement = ? "
                            + "WHERE id = ?;");
            statement.setString(1, membre.getNom());
            statement.setString(2, membre.getPrenom());
            statement.setString(3, membre.getAdresse());
            statement.setString(4, membre.getEmail());
            statement.setString(5, membre.getTelephone());
            statement.setString(6, membre.getAbonnement() + "");
            statement.setInt(7, membre.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void delete(int id) throws DaoException {
        PreparedStatement statement = null;

        try (Connection connection = ConnectionManager.getConnection()) {
            statement = connection.prepareStatement(
                    "DELETE FROM membre WHERE id = ?;");
            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int count() throws DaoException {
        int coun = -1;
        Statement statement = null;

        try (Connection connection = ConnectionManager.getConnection()) {
            statement = connection.createStatement();
            ResultSet res = statement.executeQuery(
                    "SELECT COUNT(id) AS count FROM membre;");

            if (res.next()) {
                coun = res.getInt("count");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return coun;
    }
}// class validator pour valider les requetes