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

public class EmpruntReturnServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		EmpruntService empruntService = EmpruntServicelmpl.getInstance();

		List<Emprunt> emprunts = new ArrayList<Emprunt>();
		try {
			emprunts = empruntService.getListCurrent();
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			throw new ServletException("Erreur au niveau du servlet : ", e);
		}

		request.setAttribute("empruntslistCurrent", emprunts);
		this.getServletContext().getRequestDispatcher("/WEB-INF/View/emprunt_return.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		EmpruntService empruntService = EmpruntServicelmpl.getInstance();
		String inputEmprunt = request.getParameter("idEmprunt");

		try {
			int idEmprunt = Integer.parseInt(inputEmprunt);
			empruntService.returnBook(idEmprunt);
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (NumberFormatException ebis) {
			throw new ServletException("Erreur lors du parsing : idEmprunt=" + inputEmprunt, ebis);
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