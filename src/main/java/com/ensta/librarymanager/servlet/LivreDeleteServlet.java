package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.exception.ServiceException;

import com.ensta.librarymanager.modele.Livre;

import com.ensta.librarymanager.service.LivreService;
import com.ensta.librarymanager.service.LivreServicelmpl;

public class LivreDeleteServlet extends HttpServlet {

	/*
	 * !
	 * Cette méthode redirige sur la JSP livre_delete permettant de supprimer le
	 * livre observé
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LivreService livreService = LivreServicelmpl.getInstance();
		String inputId = request.getParameter("id");
		System.out.println("l'id est : " + inputId);
		int id = -1;

		Livre livre;

		try {
			id = Integer.parseInt(inputId);
			livre = livreService.getById(id);

		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			livre = new Livre();

		} catch (NumberFormatException ebis) {
			livre = new Livre(); // Je ne suis pas sûr que cela soit utile, mais dans le doute, ça coûte pas tant
									// que ça d'initialiser "livre"
			throw new ServletException("Erreur lors du parsing : id=" + inputId, ebis);
		} catch (Exception e) {
			throw new ServletException("Erreur au niveau du servlet : ", e);
		}
		request.setAttribute("id", id);
		request.setAttribute("livre", livre);
		this.getServletContext().getRequestDispatcher("/WEB-INF/View/livre_delete.jsp").forward(request, response);
	}

	/*
	 * !
	 * Cette méthode redirige sur la JSP livre_list permettant d'observer la liste
	 * des livres
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LivreService livreService = LivreServicelmpl.getInstance();

		String inputId = request.getParameter("id");
		System.out.println("l'id est : " + inputId);
		int id = -1;

		try {
			id = Integer.parseInt(inputId);
			livreService.delete(id);

		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();

		} catch (NumberFormatException ebis) {
			throw new ServletException("Erreur lors du parsing : id=" + inputId, ebis);
		} catch (Exception e) {
			throw new ServletException("Erreur au niveau du servlet : ", e);
		}

		this.getServletContext().getRequestDispatcher("/WEB-INF/View/livre_list.jsp").forward(request, response);
	}
}