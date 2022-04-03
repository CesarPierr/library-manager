package com.ensta.librarymanager.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.exception.ServiceException;

import com.ensta.librarymanager.utils.Abonnement;
import com.ensta.librarymanager.modele.Membre;
import com.ensta.librarymanager.modele.Emprunt;

import com.ensta.librarymanager.service.MembreService;
import com.ensta.librarymanager.service.EmpruntService;
import com.ensta.librarymanager.service.MembreServicelmpl;
import com.ensta.librarymanager.service.EmpruntServicelmpl;

public class MembreDetailsServlet extends HttpServlet {

	/*
	 * !
	 * Cette méthode redirige sur la JSP membre_details contenant les informations
	 * du membre que l'on souhaite observer.
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Récupération de l'id du membre observé (si il y en a un)
		String inputId = request.getParameter("id");
		int id = -1;

		Membre membre;
		List<Emprunt> listEmprunts = new ArrayList<Emprunt>();

		MembreService membreService = MembreServicelmpl.getInstance();
		EmpruntService empruntService = EmpruntServicelmpl.getInstance();

		try {
			id = Integer.parseInt(inputId);
			membre = membreService.getById(id);
			listEmprunts = empruntService.getListCurrentByMembre(id);

		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			membre = new Membre();

		} catch (NumberFormatException ebis) {
			membre = new Membre(); // Je ne suis pas sûr que cela soit utile, mais dans le doute, ça coûte pas tant
									// que ça d'initialiser "membre"
			throw new ServletException(("Erreur lors du parsing : id=" + inputId), ebis);
		}
		request.setAttribute("id", id);
		request.setAttribute("emprunts", listEmprunts);
		request.setAttribute("membre", membre);
		this.getServletContext().getRequestDispatcher("/WEB-INF/View/membre_details.jsp").forward(request, response);
	}

	/*
	 * !
	 * Cette méthode redirige vers la JSP membre_details permettant d'observer les
	 * informations modifiées du membre observé.
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String inputId = request.getParameter("id");
		String inputNom = request.getParameter("nom");
		String inputPrenom = request.getParameter("prenom");
		String inputAdresse = request.getParameter("adresse");
		String inputEmail = request.getParameter("email");
		String inputTelephone = request.getParameter("telephone");
		String inputAbonnement = request.getParameter("abonnement");

		Abonnement abonnement = Abonnement.valueOf("BASIC");
		int id = -1;

		Membre membre = new Membre();
		List<Emprunt> listEmprunts = new ArrayList<Emprunt>();

		MembreService membreService = MembreServicelmpl.getInstance();
		EmpruntService empruntService = EmpruntServicelmpl.getInstance();
		try {
			id = Integer.parseInt(inputId);
			// Vérification du type de l'abonnement
			try {
				abonnement = Abonnement.valueOf(inputAbonnement);
			} catch (Exception e) {
				throw new ServletException("Type d'abonnement inexistant", e);
			}

			// Modification du membre
			membre = membreService.getById(id);
			membre.setNom(inputNom);
			membre.setPrenom(inputPrenom);
			membre.setAdresse(inputAdresse);
			membre.setEmail(inputEmail);
			membre.setTelephone(inputTelephone);
			membre.setAbonnement(abonnement);

			membreService.update(membre);
			listEmprunts = empruntService.getListCurrentByMembre(id);

		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			membre = new Membre();
		} catch (NumberFormatException ebis) {
			membre = new Membre(); // Je ne suis pas sûr que cela soit utile, mais dans le doute, ça coûte pas tant
									// que ça d'initialiser "membre"
			throw new ServletException("Erreur lors du parsing : id=" + inputId, ebis);
		}

		request.setAttribute("emprunts", listEmprunts);
		request.setAttribute("membre", membre);
		this.getServletContext().getRequestDispatcher("/WEB-INF/View/membre_details.jsp").forward(request, response);
	}
}