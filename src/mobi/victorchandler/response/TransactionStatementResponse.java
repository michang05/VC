package mobi.victorchandler.response;
/**
 * Class for storing Transaction Statements response attributes
 */
public class TransactionStatementResponse {
    
    private String id;
    private String typeId;
    private String typeDescription;
    private String subTypeId;
    private String subTypeDescription;
    private String settled;
    private String date;
    private String description;
    private String debitDecimal;
    private String debitFormatted;
    private String creditDecimal;
    private String creditFormatted;
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getTypeId() {
        return typeId;
    }
    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }
    public String getTypeDescription() {
        return typeDescription;
    }
    public void setTypeDescription(String typeDescription) {
        this.typeDescription = typeDescription;
    }
    public String getSubTypeId() {
        return subTypeId;
    }
    public void setSubTypeId(String subTypeId) {
        this.subTypeId = subTypeId;
    }
    public String getSubTypeDescription() {
        return subTypeDescription;
    }
    public void setSubTypeDescription(String subTypeDescription) {
        this.subTypeDescription = subTypeDescription;
    }
    public String getSettled() {
        return settled;
    }
    public void setSettled(String settled) {
        this.settled = settled;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getDebitDecimal() {
        return debitDecimal;
    }
    public void setDebitDecimal(String debitDecimal) {
        this.debitDecimal = debitDecimal;
    }
    public String getDebitFormatted() {
        return debitFormatted;
    }
    public void setDebitFormatted(String debitFormatted) {
        this.debitFormatted = debitFormatted;
    }
    public String getCreditDecimal() {
        return creditDecimal;
    }
    public void setCreditDecimal(String creditDecimal) {
        this.creditDecimal = creditDecimal;
    }
    public String getCreditFormatted() {
        return creditFormatted;
    }
    public void setCreditFormatted(String creditFormatted) {
        this.creditFormatted = creditFormatted;
    }
    
    

}
