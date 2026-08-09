/**
 * 
 */
package us.tn.greatsmokey.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import us.tn.greatsmokey.dao.TriviaSourceDAO;
import us.tn.greatsmokey.model.Categories;

/**
  * Servlet responsible for providing the list of trivia categories.
  *
  * <p>This servlet handles HTTP GET requests at the {@code /CategoriesServlet}
  * endpoint and returns a JSON array of {@link Categories} objects. The category
  * list is retrieved from the {@link TriviaSourceDAO}, which queries the
  * underlying trivia source.
  *
  * <p>No query parameters are required or processed; the servlet simply
  * returns the full set of available categories supported by the trivia source.
  */
@WebServlet("/CategoriesServlet")
public class CategoriesServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final TriviaSourceDAO triviaSourceDAO = new TriviaSourceDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Categories> categories = triviaSourceDAO.getCategoryJson();

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        new ObjectMapper().writeValue(response.getWriter(), categories);
    }
}
