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

import com.ensta.librarymanager.modele.Livre;

import com.ensta.librarymanager.service.LivreService;
import com.ensta.librarymanager.service.LivreServicelmpl;

public class LivreListServlet extends HttpServlet {

	/*
	 * !
	 * Cette méthode redirige sur la JSP livre_list permettant d'afficher la liste
	 * des livres
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LivreService livreService = LivreServicelmpl.getInstance();

		List<Livre> livres = new ArrayList<Livre>();
		try {
			livres = livreService.getList();
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		request.setAttribute("livres", livres);
		this.getServletContext().getRequestDispatcher("/WEB-INF/View/livre_list.jsp").forward(request, response);
	}
}