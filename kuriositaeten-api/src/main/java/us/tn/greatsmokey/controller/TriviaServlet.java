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
import us.tn.greatsmokey.model.TriviaParams;
import us.tn.greatsmokey.model.TriviaQuestion;

/**
  * Servlet responsible for handling requests for trivia questions.
  *
  * <p>This servlet accepts HTTP GET requests at the {@code /TriviaServlet} endpoint.
  * Clients may specify query parameters to filter the trivia results, including:
  * <ul>
  *   <li>{@code count} – number of trivia questions to retrieve</li>
  *   <li>{@code category} – trivia category identifier</li>
  *   <li>{@code difficulty} – question difficulty (e.g., easy, medium, hard)</li>
  *   <li>{@code type} – question type (e.g., multiple choice, boolean)</li>
  * </ul>
  *
  * <p>The servlet populates a {@link TriviaParams} object with these inputs,
  * delegates the retrieval to {@link TriviaSourceDAO#getTriviaQuestions},
  * and returns the resulting list of {@link TriviaQuestion} objects in JSON format.
  */
@WebServlet("/TriviaServlet")
public class TriviaServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final TriviaSourceDAO dao = new TriviaSourceDAO();

    /**
      * Handles HTTP GET requests for trivia questions.
      *
      * <p>The method extracts query parameters from the request, builds a
      * {@link TriviaParams} instance, retrieves trivia questions via the DAO,
      * and writes the results as a UTF‑8 encoded JSON array to the response body.
      *
      * @param request  the incoming {@link HttpServletRequest} containing query parameters
      * @param response the {@link HttpServletResponse} used to write JSON output
      * @throws ServletException if an internal servlet error occurs
      * @throws IOException      if an I/O error occurs during JSON serialization
      */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        TriviaParams params = new TriviaParams();
        params.setAmount(request.getParameter("count"));
        params.setCategory(request.getParameter("category"));
        params.setDifficulty(request.getParameter("difficulty"));
        params.setType(request.getParameter("type"));

        List<TriviaQuestion> questions = dao.getTriviaQuestions(params);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        new ObjectMapper().writeValue(response.getWriter(), questions);
    }
}