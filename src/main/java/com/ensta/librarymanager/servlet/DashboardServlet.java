package com.ensta.librarymanager.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.exception.ServiceException;

import com.ensta.librarymanager.modele.Emprunt;

import com.ensta.librarymanager.service.MembreService;
import com.ensta.librarymanager.service.LivreService;
import com.ensta.librarymanager.service.EmpruntService;
import com.ensta.librarymanager.service.MembreServicelmpl;
import com.ensta.librarymanager.service.LivreServicelmpl;
import com.ensta.librarymanager.service.EmpruntServicelmpl;

public class DashboardServlet extends HttpServlet {
	/*
	 * Cette méthode redirige sur la JSP dashboard contenant les informations
	 * générales sur notre bibliothèque.
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		MembreService membreService = MembreServicelmpl.getInstance();
		LivreService livreService = LivreServicelmpl.getInstance();
		EmpruntService empruntService = EmpruntServicelmpl.getInstance();

		int nbMembres = -1;
		int nbLivres = -1;
		int nbEmprunts = -1;

		List<Emprunt> empruntsEnCours = new ArrayList<Emprunt>();
		try {
			nbMembres = membreService.count();
			nbLivres = livreService.count();
			nbEmprunts = empruntService.count();
			empruntsEnCours = empruntService.getListCurrent();
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		request.setAttribute("nbMembres", nbMembres);
		request.setAttribute("nbLivres", nbLivres);
		request.setAttribute("nbEmprunts", nbEmprunts);
		request.setAttribute("empruntsEnCours", empruntsEnCours);

		this.getServletContext().getRequestDispatcher("/WEB-INF/View/dashboard.jsp").forward(request, response);
	}
}