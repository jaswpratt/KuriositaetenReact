/**
 * 
 */
package us.tn.greatsmokey.model;

/**
  * Represents a generic response object used within the application.
  *
  * <p>This model encapsulates a response code, a result message, and an
  * optional comment. It can be used for returning status information
  * from business logic or service layers to controllers or clients.
  *
  * <p>Typical usage might include:
  * <ul>
  *   <li>Indicating success or failure of an operation</li>
  *   <li>Providing human‑readable feedback messages</li>
  *   <li>Including diagnostic comments or additional context</li>
  * </ul>
  * 
  * @author Sabine
  *
  */
public class Response {
   /**
     * Creates a new empty {@code Response} instance.
     * All fields default to {@code null} and may be set via setters.
     */
   public Response() {
      super();
   }
   
   /** 
    * A code representing the status of the response (e.g., "OK", "ERROR"). 
    */
   private String code = null;
   
   /**
    *  A general result or outcome message associated with the response. 
    */
   private String result = null;
   
   /** 
    * Optional additional comment or explanation. 
    */
   private String comment = null;
   
   /**
     * Returns the response code.
     *
     * @return the response code, or {@code null} if unset
     */
   public String getCode() {
      return code;
   }
   
   /**
     * Sets the response code.
     *
     * @param code the response code to assign
     */
   public void setCode(String code) {
      this.code = code;
   }
   
   /**
     * Returns the result message.
     *
     * @return the result message, or {@code null} if unset
     */
   public String getResult() {
      return result;
   }
   
   /**
     * Sets the result message.
     *
     * @param result the result text to assign
     */
   public void setResult(String result) {
      this.result = result;
   }
   
   /**
     * Returns an optional comment associated with the response.
     *
     * @return the comment text, or {@code null} if unset
     */
   public String getComment() {
      return comment;
   }
   
   /**
    * @param comment the comment to set
    */
   public void setComment(String comment) {
      this.comment = comment;
   }
   
   @Override
   public String toString() {
      return "Response [code=" + code + 
            ", result=" + result + 
            ", comment=" + comment + 
            ", getCode()=" + getCode() + 
            ", getResult()=" + getResult() + 
            ", getComment()=" + getComment() + 
            ", getClass()=" + getClass() + 
            ", hashCode()=" + hashCode() + 
            ", toString()=" + super.toString() + "]";
   }
}
