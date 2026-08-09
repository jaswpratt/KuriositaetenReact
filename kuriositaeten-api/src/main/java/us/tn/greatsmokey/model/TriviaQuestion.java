/**
 * 
 */
package us.tn.greatsmokey.model;

import java.util.ArrayList;

/**
  * Represents an individual trivia question retrieved from the
  * Open Trivia DB API.
  *
  * <p>A trivia question contains details such as category, type,
  * difficulty, the question text itself, the correct answer, and
  * a list of incorrect answers. Instances of this class are typically
  * populated by the DAO when parsing JSON returned from the API, and
  * then delivered to clients through servlets as part of trivia results.
  *
  * <p>Example fields:
  * <ul>
  *   <li>category: "Mythology"</li>
  *   <li>type: "multiple"</li>
  *   <li>difficulty: "medium"</li>
  *   <li>question: "Who was the Greek god of the underworld?"</li>
  *   <li>correctAnswer: "Hades"</li>
  *   <li>incorrectAnswers: ["Poseidon", "Zeus", "Apollo"]</li>
  * </ul>
  * 
  * @author Sabine
  *
  */
public class TriviaQuestion {

    /**
      * Creates an empty {@code TriviaQuestion} instance.
      * Fields may be populated later through their setter methods.
      */
   public TriviaQuestion() { }
   
   /** 
     * The trivia category this question belongs to.
     */
   private String category = null;

   /** The question type (e.g., multiple, boolean). */

   private String type;

   /** 
    * The difficulty level (e.g., easy, medium, hard). 
    */
   private String difficulty;

   /** 
    * The text of the trivia question. 
    */
   private String question;

   /** 
    * The correct answer to the question. 
    */
   private String correctAnswer;

   /** 
    * A list of incorrect answer choices. 
    */
   private ArrayList<String> incorrectAnswers;
   
   /**
     * Returns the category of the trivia question.
     *
     * @return the category, or {@code null} if unset
     */
   public String getCategory() {
      return category;
   }
   
   /**
     * Sets the trivia question category.
     *
     * @param category the category value to assign
     */
   public void setCategory(String category) {
      this.category = category;
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
     * Returns the difficulty level.
     *
     * @return the difficulty, or {@code null} if unset
     */
   public String getDifficulty() {
      return difficulty;
   }
   
   /**
    * @param difficulty the difficulty to set
    */
   public void setDifficulty(String difficulty) {
      this.difficulty = difficulty;
   }
   
   /**
     * Returns the text of the question.
     *
     * @return the question text, or {@code null} if unset
     */
   public String getQuestion() {
      return question;
   }
   
   /**
     * Sets the text of the question.
     *
     * @param question the question text to assign
     */
   public void setQuestion(String question) {
      this.question = question;
   }
   
   /**
     * Returns the correct answer.
     *
     * @return the correct answer, or {@code null} if unset
     */
   public String getCorrectAnswer() {
      return correctAnswer;
   }
   
   /**
     * Sets the correct answer to the question.
     *
     * @param correctAnswer the correct answer value to assign
     */
   public void setCorrectAnswer(String correctAnswer) {
      this.correctAnswer = correctAnswer;
   }
   
   /**
     * Returns the list of incorrect answer choices.
     *
     * @return a list of incorrect answers; may be {@code null} or empty
     */
   public ArrayList<String> getIncorrectAnswers() {
      return incorrectAnswers;
   }
   
   /**
     * Sets the list of incorrect answer choices.
     *
     * @param incorrectAnswers the list of incorrect answers to assign
     */
   public void setIncorrectAnswers(ArrayList<String> incorrectAnswers) {
      this.incorrectAnswers = incorrectAnswers;
   }

   /**
     * Returns a string representation of the trivia question and all its fields.
     *
     * @return a descriptive string containing category, type, difficulty, question,
     *         correct answer, and incorrect answer list
     */
@Override
public String toString() {
   return "TriviaQuestion [category=" + category + 
         ", type=" + type + 
         ", difficulty=" + difficulty + 
         ", question=" + question + 
         ", correctAnswer=" + correctAnswer +
         ", incorrectAnswers=" + incorrectAnswers + "]";
}
   
   

}
