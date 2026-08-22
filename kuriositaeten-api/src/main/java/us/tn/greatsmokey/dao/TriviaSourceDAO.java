package us.tn.greatsmokey.dao;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import us.tn.greatsmokey.model.TriviaParams;
import us.tn.greatsmokey.model.TriviaQuestion;
import us.tn.greatsmokey.model.Categories;

/**
  * Data Access Object responsible for communicating with the Open Trivia DB API.
  *
  * <p>This class builds request URLs, performs HTTP GET calls, and parses JSON
  * responses into strongly-typed model objects such as {@link TriviaQuestion}
  * and {@link Categories}. It supports fetching both trivia questions and trivia
  * category metadata.
  *
  * <p>Example request:
  * {@code https://opentdb.com/api.php?amount=13&category=20&difficulty=medium&type=multiple&encode=url3986}
  *
  * <p>The API returns JSON with response codes and a results array. Trivia strings
  * are URL-encoded and must be decoded to UTF‑8, which this DAO handles internally.
  * 
  * @author Sabine
  */
public class TriviaSourceDAO {
	 /**
	  *  Base URL for trivia question queries. 
	  */
    public static final String OPENTDB_URL = "https://opentdb.com/api.php";
    
    /** 
     * URL for retrieving trivia category metadata. 
     */
    public static final String OPENTDB_CATEGORIES_URL = "https://opentdb.com/api_category.php";
    
    /**
     * Constructs a new TriviaSourceDAO instance.
     */
    public TriviaSourceDAO() { }

    /**
      * Retrieves trivia questions based on the given parameters.
      *
      * <p>This method constructs a request URL using the supplied {@link TriviaParams},
      * calls the OpenTDB API, and parses the JSON response into a list of
      * {@link TriviaQuestion} objects. If the API returns a non-zero response code
      * or an empty payload, an empty list is returned.
      *
      * @param triviaParams parameters describing amount, category, difficulty, type, etc.
      * @return a list of parsed trivia questions; possibly empty if the API indicates no results
      */
    public List<TriviaQuestion> getTriviaQuestions(TriviaParams triviaParams) {
        String requestUrl = buildRequestUrl(triviaParams);
        System.out.println("Requesting: " + requestUrl);

        String triviaJson = callOtdbUrl(requestUrl);
        System.out.println(triviaJson);

        return parseTriviaJson(triviaJson);
    }
    
    /**
      * Retrieves all available trivia categories from the OpenTDB API.
      *
      * <p>Calls the category endpoint and parses the returned JSON into
      * {@link Categories} objects, each containing a category ID and descriptive name.
      *
      * @return a list of trivia category objects
      */
    public List<Categories> getCategoryJson() {
        String categoriesJson = callOtdbUrl(OPENTDB_CATEGORIES_URL);
        System.out.println(categoriesJson);

        return parseCategoriesJson(categoriesJson);
    }

    /**
      * Builds the OpenTDB request URL based on the given trivia parameters.
      *
      * <p>Only parameters that are non-null and non-blank are appended. The method
      * automatically adds {@code encode=url3986} to ensure safe, consistent encoding.
      *
      * @param triviaParams user-specified parameters for the trivia query
      * @return a fully assembled OpenTDB API URL
      */
    private String buildRequestUrl(TriviaParams triviaParams) {
        StringBuilder sb = new StringBuilder(OPENTDB_URL).append("?");

        appendParamIfPresent(sb, "amount", triviaParams.getAmount());
        appendParamIfPresent(sb, "category", triviaParams.getCategory());
        appendParamIfPresent(sb, "difficulty", triviaParams.getDifficulty());
        appendParamIfPresent(sb, "type", triviaParams.getType());
        appendParamIfPresent(sb, "encode", "url3986");

        if (sb.charAt(sb.length() - 1) == '?' || sb.charAt(sb.length() - 1) == '&') {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }

    /**
      * Appends a URL parameter if the value is present and non-blank.
      *
      * @param sb    the URL string builder
      * @param key   the query parameter key
      * @param value the query parameter value, may be null
      */
    private void appendParamIfPresent(StringBuilder sb, String key, String value) {
        if (value != null && !value.isBlank()) {
            sb.append(key).append("=").append(value).append("&");
        }
    }

    /**
      * Parses trivia JSON returned from the OpenTDB API.
      *
      * <p>This method reads the response code, iterates through the returned results,
      * decodes URL-encoded fields, and builds a list of {@link TriviaQuestion} objects.
      *
      * @param triviaJson raw JSON response as a string
      * @return a list of parsed trivia questions; empty if invalid or response code is non-zero
      */
    private List<TriviaQuestion> parseTriviaJson(String triviaJson) {
        List<TriviaQuestion> questions = new ArrayList<>();
        if (triviaJson == null || triviaJson.isBlank()) {
            return questions;
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(triviaJson);

            int responseCode = root.path("response_code").asInt(-1);
            if (responseCode != 0) {
                System.out.println("OpenTDB returned non-zero response_code: " + responseCode);
                return questions;
            }

            JsonNode results = root.path("results");
            for (JsonNode item : results) {
                TriviaQuestion q = new TriviaQuestion();
                q.setCategory(decode(item.path("category").asText(null)));
                q.setType(decode(item.path("type").asText(null)));
                q.setDifficulty(decode(item.path("difficulty").asText(null)));
                q.setQuestion(decode(item.path("question").asText(null)));
                q.setCorrectAnswer(decode(item.path("correct_answer").asText(null)));

                ArrayList<String> incorrect = new ArrayList<>();
                for (JsonNode ia : item.path("incorrect_answers")) {
                    incorrect.add(decode(ia.asText(null)));
                }
                q.setIncorrectAnswers(incorrect);

                questions.add(q);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse trivia JSON", e);
        }

        return questions;
    }

    /**
      * Parses category JSON from the OpenTDB API.
      *
      * <p>The JSON contains a {@code trivia_categories} array, each with an ID and name.
      *
      * @param categoriesJson raw JSON string returned by the API
      * @return list of {@link Categories} objects; empty if JSON is missing or invalid
      */
    private List<Categories> parseCategoriesJson(String categoriesJson) {
        List<Categories> categories = new ArrayList<>();
        if (categoriesJson == null || categoriesJson.isBlank()) {
            return categories;
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(categoriesJson);

            JsonNode results = root.path("trivia_categories");
            for (JsonNode item : results) {
                Categories c = new Categories();
                c.setId(item.path("id").asText(null));
                c.setCategory(item.path("name").asText(null));

                categories.add(c);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse categories JSON", e);
        }

        return categories;
    }
    
    /**
      * Decodes URL‑encoded strings using UTF‑8.
      *
      * @param value the encoded value; may be null
      * @return decoded UTF‑8 string, or null if the input was null
      */
    private String decode(String value) {
        if (value == null) {
            return null;
        }
        return URLDecoder.decode(value, StandardCharsets.UTF_8);
    }

    /**
      * Performs an HTTP GET request to the specified API URL.
      *
      * <p>This method opens a connection, handles timeouts, reads the response
      * stream fully as UTF‑8, and returns the body as a string. Non‑200 HTTP
      * statuses result in a {@link RuntimeException}.
      *
      * @param otdbUrl the full URL to request
      * @return the raw JSON response body as a string
      * @throws RuntimeException if the request fails or the server returns an error status
      */
    private String callOtdbUrl(String otdbUrl) {
        System.out.println("Entering callOtdbUrl()");

        final int maxRetries = 3;
        final long retryDelayMillis = 6000; // OpenTDB's rate limit window is ~5s; pad slightly

        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            StringBuilder sb = new StringBuilder();

            try {
                URL url = URI.create(otdbUrl).toURL();
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(60 * 1000);
                connection.setConnectTimeout(15 * 1000);
                connection.setRequestMethod("GET");

                int status = connection.getResponseCode();

                if (status == 429) {
                    connection.disconnect();
                    if (attempt < maxRetries) {
                        System.out.println("OpenTDB rate limited (429). Attempt "
                                + attempt + "/" + maxRetries + " — retrying in "
                                + retryDelayMillis + "ms");
                        try {
                            Thread.sleep(retryDelayMillis);
                        } catch (InterruptedException ie) {
                            Thread.currentThread().interrupt();
                            throw new RuntimeException("Interrupted while waiting to retry OpenTDB call", ie);
                        }
                        continue; // retry the loop
                    } else {
                        throw new RuntimeException(
                                "OpenTDB rate limit exceeded after " + maxRetries + " attempts");
                    }
                }

                if (status != HttpURLConnection.HTTP_OK) {
                    throw new RuntimeException("OpenTDB returned HTTP status " + status);
                }

                try (InputStreamReader in = new InputStreamReader(
                        connection.getInputStream(), StandardCharsets.UTF_8);
                     BufferedReader bufferedReader = new BufferedReader(in)) {

                    int cp;
                    while ((cp = bufferedReader.read()) != -1) {
                        sb.append((char) cp);
                    }
                } finally {
                    connection.disconnect();
                }

                System.out.println("Exiting callOtdbUrl()");
                return sb.toString();

            } catch (RuntimeException re) {
                throw re; // already wrapped/intentional (429-exhausted or non-200 status)
            } catch (Exception e) {
                throw new RuntimeException("Exception while calling URL: " + otdbUrl, e);
            }
        }

        // Unreachable, but required for compilation
        throw new RuntimeException("Exhausted retries calling OpenTDB URL: " + otdbUrl);
    }
}