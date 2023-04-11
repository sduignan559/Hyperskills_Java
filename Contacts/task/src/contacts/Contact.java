package contacts;

public class Contact {
    private String creationDate;
    private String lastEditDate;
    private String number;

    public Contact(String creationDate, String lastEditDate,  String number) {
            this.creationDate = creationDate;
            this.lastEditDate = lastEditDate;
            this.number = number;
        }

    public void displayAllInfo(){

    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getLastEditDate() {
        return lastEditDate;
    }

    public void setLastEditDate(String lastEditDate) {
        this.lastEditDate = lastEditDate;
    }
}
