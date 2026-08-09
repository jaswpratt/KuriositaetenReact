/**
 * 
 */
package  us.tn.greatsmokey.model;

/**
  * Represents a collection of parameters used to request trivia questions
  * from the Open Trivia DB API.
  *
  * <p>This model encapsulates query options such as the number of questions
  * to retrieve, category filters, difficulty level, question type, and the
  * desired encoding. Instances of this class are typically populated by
  * servlets handling user input (for example, {@code TriviaServlet}) and
  * passed to the DAO for building API request URLs.
  *
  * <p>Example parameters include:
  * <ul>
  *   <li>amount: "10"</li>
  *   <li>category: "20"</li>
  *   <li>difficulty: "medium"</li>
  *   <li>type: "multiple"</li>
  *   <li>encoding: "url3986"</li>
  * </ul>
  * 
  * @author Sabine
  *
  */
public class TriviaParams {

   /**
     * Creates an empty {@code TriviaParams} instance.
     * Fields may be assigned later through their setter methods.
     */
   public TriviaParams() { }
   
   /**
     *  The number of trivia questions to request. 
     */
   private String amount = null;
   
   /** 
    * The category identifier to filter results. 
    */
   private String category = null;
   
   /** 
    * The desired difficulty level (e.g., easy, medium, hard). 
    */
   private String difficulty = null;
   
   /** 
    * The question type (e.g., multiple, boolean). 
    */
   private String type = null;
   
   /** 
    * The encoding format requested by the API (e.g., url3986). 
    */
   private String encoding = null;
   
   /**
     * Returns the number of questions requested.
     *
     * @return the amount, or {@code null} if unset
     */
   public String getAmount() {
      return amount;
   }
   
   /**
     * Sets the number of questions to request.
     *
     * @param count the amount value to assign
     */
   public void setAmount(String count) {
      this.amount = count;
   }
   
   /**
     * Returns the category identifier.
     *
     * @return the category, or {@code null} if unset
     */
   public String getCategory() {
      return category;
   }
   
   /**
     * Sets the category identifier.
     *
     * @param category the category value to assign
     */
   public void setCategory(String category) {
      this.category = category;
   }
   
   /**
     * Returns the difficulty level.
     *
     * @return the difficulty, or {@code null} if unset
     */
   public String getDifficulty() {
      return difficulty;
   }
   
   /**
     * Sets the difficulty level.
     *
     * @param difficulty the difficulty value to assign
     */
   public void setDifficulty(String difficulty) {
      this.difficulty = difficulty;
   }
   
   /**
     * Returns the question type.
     *
     * @return the type, or {@code null} if unset
     */
   public String getType() {
      return type;
   }
   
   /**
     * Sets the question type.
     *
     * @param type the type value to assign
     */
   public void setType(String type) {
      this.type = type;
   }
   
   /**
     * Returns the encoding format used for trivia content.
     *
     * @return the encoding, or {@code null} if unset
     */
   public String getEncoding() {
      return encoding;
   }

   /**
     * Sets the encoding format used for trivia content.
     *
     * @param encoding the encoding value to assign
     */
   public void setEncoding(String encoding) {
      this.encoding = encoding;
   }

   /**
     * Returns a string representation of this parameter set.
     *
     * @return a descriptive string containing all fields
     */
   @Override
   public String toString() {
      return "TriviaParams [amount=" + amount + 
         ", category=" + category + 
         ", difficulty=" + difficulty + 
         ", type=" + type + 
         ", encoding=" + encoding + "]";
   }
}
