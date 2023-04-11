package contacts;

public class Person extends Contact{
    private String name;
    private String surName;
    private String birthDay;
    private String gender;


    public Person(String name, String surName, String birthDay, String gender, String number,String creationDate, String lastEditDate ) {
        super(creationDate, lastEditDate, number);
        this.name = name;
        this.surName = surName;
        this.birthDay = birthDay;
        this.gender = gender;
    }

    public void displayAllInfo() {
        System.out.println("Name: " + this.getName());
        System.out.println("Surname: " + this.getSurName());
        System.out.println("Birth date: " + this.getBirthDay());
        System.out.println("Gender: " + this.getGender());
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

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
