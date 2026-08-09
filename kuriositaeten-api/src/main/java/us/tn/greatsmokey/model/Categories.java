/**
 * 
 */
package us.tn.greatsmokey.model;

/**
  * Represents a trivia category as provided by the Open Trivia DB API.
  *
  * <p>Each category consists of an identifier and a human‑readable name.
  * Instances of this class are typically created and populated by the
  * {@code TriviaSourceDAO} when parsing category JSON returned by the API.
  *
  * <p>Example category:
  * <ul>
  *   <li>ID: "20"</li>
  *   <li>Name: "Mythology"</li>
  * </ul>
  *
  * This model is used by the application to display available categories
  * to clients and to support category‑specific trivia queries.
  * 
  * @author Sabine
  *
  */
public class Categories {

    /**
      * Creates an empty {@code Categories} instance.
      * Fields may be populated later through their setter methods.
      */
   public Categories() { }
   
   /** 
    * The category identifier as returned by the API. 
    */
   String id = null;
   
   /**
    * The human‑readable name of the category.
    */
   String category = null;
   
   /**
     * Returns the category identifier.
     *
     * @return the category ID, or {@code null} if not set
     */
   public String getId() {
      return id;
   }
   
   /**
     * Sets the category identifier.
     *
     * @param id the category ID to assign
     */
   public void setId(String id) {
      this.id = id;
   }
   
   /**
     * Returns the human‑readable category name.
     *
     * @return the category name, or {@code null} if not set
     */
   public String getCategory() {
      return category;
   }
   
   /**
     * Sets the human‑readable category name.
     *
     * @param category the category name to assign
     */
   public void setCategory(String category) {
      this.category = category;
   }
   
   
   

}
