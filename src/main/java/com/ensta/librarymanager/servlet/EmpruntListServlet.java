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

import com.ensta.librarymanager.modele.Emprunt;

import com.ensta.librarymanager.service.EmpruntService;
import com.ensta.librarymanager.service.EmpruntServicelmpl;

public class EmpruntListServlet extends HttpServlet {

	/*
	 * !
	 * Cette méthode redirige sur la JSP emprunt_list permettant d'afficher la liste
	 * des membres
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		EmpruntService empruntService = EmpruntServicelmpl.getInstance();
		String show = request.getParameter("show");

		List<Emprunt> emprunts = new ArrayList<Emprunt>();
		try {
			emprunts = show == null ? empruntService.getListCurrent() : empruntService.getList();
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