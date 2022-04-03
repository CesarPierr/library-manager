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

import com.ensta.librarymanager.modele.Membre;

import com.ensta.librarymanager.service.MembreService;
import com.ensta.librarymanager.service.MembreServicelmpl;

public class MembreListServlet extends HttpServlet {

	/*
	 * !
	 * Cette méthode redirige sur la JSP membre_list permettant d'afficher la liste
	 * des membres
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		MembreService membreService = MembreServicelmpl.getInstance();

		List<Membre> membres = new ArrayList<Membre>();
		try {
			membres = membreService.getList();
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		request.setAttribute("membres", membres);

		this.getServletContext().getRequestDispatcher("/WEB-INF/View/membre_list.jsp").forward(request, response);
	}
}