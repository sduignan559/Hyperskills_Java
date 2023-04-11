package contacts;

public class Company extends Contact{
    private String name;
    private String address;

    public Company(String name, String address, String creationDate, String lastEditDate, String number ) {
        super(creationDate, lastEditDate, number);
    }

    public void displayAllInfo(){
        System.out.println("Organization name: " + this.getName());
        System.out.println("Address: " + this.getAddress());
        System.out.println("Number: " + this.getNumber());
        System.out.println("Time created: " + this.getCreationDate().substring(0, 16));
        System.out.println("Time last edit: " + this.getLastEditDate().substring(0, 16));
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
