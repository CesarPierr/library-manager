package com.ensta.librarymanager.servlet;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.exception.ServiceException;

import com.ensta.librarymanager.modele.Livre;
import com.ensta.librarymanager.modele.Membre;
import com.ensta.librarymanager.modele.Emprunt;

import com.ensta.librarymanager.service.EmpruntService;
import com.ensta.librarymanager.service.EmpruntServicelmpl;
import com.ensta.librarymanager.service.LivreService;
import com.ensta.librarymanager.service.LivreServicelmpl;
import com.ensta.librarymanager.service.MembreService;
import com.ensta.librarymanager.service.MembreServicelmpl;

public class EmpruntAddServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		MembreService membreService = MembreServicelmpl.getInstance();
		LivreService livreService = LivreServicelmpl.getInstance();

		List<Membre> membres = new ArrayList<Membre>();
		List<Livre> livres = new ArrayList<Livre>();
		try {
			livres = livreService.getListDispo();
			membres = membreService.getList();
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			throw new ServletException("Erreur au niveau du servlet : ", e);
		}

		request.setAttribute("livres", livres);
		request.setAttribute("membres", membres);

		this.getServletContext().getRequestDispatcher("/WEB-INF/View/emprunt_add.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		EmpruntService empruntService = EmpruntServicelmpl.getInstance();
		String inputIdLivre = request.getParameter("idDuLivre");
		String inputIdMembre = request.getParameter("idDuMembre");
		try {
			int idLivre = Integer.parseInt(inputIdLivre);
			int idMembre = Integer.parseInt(inputIdMembre);
			Emprunt emprunt = new Emprunt();
			emprunt.setIdLivre(idLivre);
			emprunt.setIdMembre(idMembre);
			emprunt.setDateEmprunt(LocalDate.now());
			empruntService.create(emprunt);
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (NumberFormatException ebis) {
			throw new ServletException(
					"Erreur lors du parsing : idMembre=" + inputIdMembre + " idLivre=" + inputIdLivre, ebis);
		} catch (Exception e) {
			throw new ServletException("Erreur au niveau du servlet : ", e);
		}

		List<Emprunt> emprunts = new ArrayList<Emprunt>();
		try {
			emprunts = empruntService.getListCurrent();
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			throw new ServletException("Erreur au niveau du servlet : ", e);
		}

		request.setAttribute("emprunts", emprunts);
		this.getServletContext().getRequestDispatcher("/WEB-INF/View/emprunt_list.jsp").forward(request, response);
	}
}