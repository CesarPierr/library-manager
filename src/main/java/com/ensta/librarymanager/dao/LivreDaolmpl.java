package com.ensta.librarymanager.dao;

import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.modele.Livre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.ensta.librarymanager.persistence.ConnectionManager;

public class LivreDaolmpl implements LivreDao {
    private static LivreDaolmpl instance;

    private LivreDaolmpl() {
    }

    public static LivreDaolmpl getInstance() {
        if (instance == null) {
            instance = new LivreDaolmpl();
        }
        return instance;
    }

    @Override
    public List<Livre> getList() throws DaoException {
        List<Livre> livres = new ArrayList<>();
        LivreMapper mapper = LivreMapper.getInstance();
        try (Connection connection = ConnectionManager.getConnection()) {

            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT id, titre, auteur, isbn FROM livre ;");
            ResultSet resultat = preparedStatement.executeQuery();

            while (resultat.next()) {
                Livre f = mapper.get_from_sql(resultat);
                livres.add(f);
            }
            // System.out.println("GET: " + livres);

            preparedStatement.close();
            resultat.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return livres;
    }

    @Override
    public Livre getById(int id) throws DaoException {
        LivreMapper mapper = LivreMapper.getInstance();
        Livre livre = new Livre();
        try (Connection connection = ConnectionManager.getConnection()) {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT id, titre, auteur, isbn FROM livre WHERE id = ?;");
            preparedStatement.setInt(1, id);
            ResultSet res = preparedStatement.executeQuery();

            if (res.next()) {
                livre = mapper.get_from_sql(res);
            }

            preparedStatement.close();
            res.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return livre;
    }

    @Override
    public int create(Livre livre) throws DaoException {
        int id = -1;
        PreparedStatement statement = null;

        try (Connection connection = ConnectionManager.getConnection()) {
            statement = connection.prepareStatement(
                    "INSERT INTO livre(titre, auteur, isbn) VALUES (?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, livre.getTitre());
            statement.setString(2, livre.getAuteur());
            statement.setString(3, livre.getIsbn());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                System.out.println("La première clef auto-générée vaut ");
                System.out.println(resultSet.getObject(1));
                id = resultSet.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }

    @Override
    public void update(Livre livre) throws DaoException {
        PreparedStatement statement = null;

        try (Connection connection = ConnectionManager.getConnection()) {
            statement = connection.prepareStatement(
                    "UPDATE livre SET titre = ?, auteur = ?, isbn = ? WHERE id = ?;");
            statement.setString(1, livre.getTitre());
            statement.setString(2, livre.getAuteur());
            statement.setString(3, livre.getIsbn());
            statement.setInt(4, livre.getId());
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
                    "DELETE FROM livre WHERE id = ?;");
            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int count() throws DaoException {
        int count = -1;
        Statement statement = null;

        try (Connection connection = ConnectionManager.getConnection()) {
            statement = connection.createStatement();
            ResultSet res = statement.executeQuery(
                    "SELECT COUNT(id) AS count FROM livre;");

            if (res.next()) {
                count = res.getInt("count");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }
}