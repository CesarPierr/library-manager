package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.exception.ServiceException;

import com.ensta.librarymanager.modele.Membre;

import com.ensta.librarymanager.service.MembreService;
import com.ensta.librarymanager.service.MembreServicelmpl;

public class MembreAddServlet extends HttpServlet {

	/*
	 * !
	 * Cette méthode redirige sur la JSP membre_add permettant d'ajouter un membre à
	 * la bibliothèque
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher("/WEB-INF/View/membre_add.jsp").forward(request, response);
	}

	/*
	 * !
	 * Cette méthode redirige vers la JSP membre_details permettant d'observer les
	 * informations concernant le membre venant d'être créé
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("salut");
		MembreService membreService = MembreServicelmpl.getInstance();
		String inputNom = request.getParameter("nom");
		String inputPrenom = request.getParameter("prenom");
		String inputAdresse = request.getParameter("adresse");
		String inputEmail = request.getParameter("email");
		String inputTelephone = request.getParameter("telephone");

		String inputId = "-1";
		int id = -1;
		Membre newMembre;
		try {
			// Création du membre (les vérifications et modifications sont gérées par la
			// couche "Service", et non par le servlet)
			// le choix de l'abonnement est géré par le dao
			Membre membre = new Membre();
			membre.setNom(inputNom);
			membre.setPrenom(inputPrenom);
			membre.setAdresse(inputAdresse);
			membre.setEmail(inputEmail);
			membre.setTelephone(inputTelephone);
			System.out.println(membre);
			id = membreService.create(membre);
			inputId = String.valueOf(id);
			newMembre = membreService.getById(id);
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			newMembre = new Membre();
		}

		request.setAttribute("id", inputId);
		request.setAttribute("membre", newMembre);
		this.getServletContext().getRequestDispatcher("/WEB-INF/View/membre_details.jsp").forward(request, response);
	}
}