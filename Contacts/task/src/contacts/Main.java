package contacts;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;
public class Main {
    public static ArrayList<Contact> contactsArray = new ArrayList<>();
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Boolean running = true;

        while (running) {
            System.out.println("Enter action (add, remove, edit, count, info, exit):");
            String menu1Option = scanner.nextLine();

            if (menu1Option.equals("count")) {
                System.out.println("The Phonebook has " + contactsArray.size() + " records.");

            } else if (menu1Option.equals("edit")) {
                if (contactsArray.isEmpty()) {
                    System.out.println("No records to edit");
                } else {
                    listContacts();
                    System.out.println("Select a record:");
                    String contactNoStr = scanner.nextLine();
                    int ContactNo = Integer.parseInt(contactNoStr);
                    Contact contact = contactsArray.get(ContactNo - 1);

                    if (contact.getClass() == Person.class) {
                        System.out.println("Select a field (name, surname, number):");
                        String submenuEdit = scanner.nextLine();
                        if (submenuEdit.equals("name")) {
                            System.out.println("Enter name:");
                            String editName = scanner.nextLine();
                            ((Person) contact).setName(editName);
                        } else if (submenuEdit.equals("surname")) {
                            System.out.println("Enter surname:");
                            String editSurname = scanner.nextLine();
                            ((Person) contact).setSurName(editSurname);
                        } else if (submenuEdit.equals("number")) {
                            System.out.println("Enter number:");
                            String editNumber = scanner.nextLine();
                            String validatedNumber = numberValidator(editNumber);
                            ((Person) contact).setNumber(validatedNumber);
                        } else if (submenuEdit.equals("gender")) {
                            System.out.println("Enter the gender (M, F):");
                            String editgender = scanner.nextLine();
                            String validatedGender = genderValidator(editgender);
                            ((Person) contact).setGender(validatedGender);
                        } else if (submenuEdit.equals("birthday")) {
                            String editbday = scanner.nextLine();
                            System.out.println("Enter the gender (M, F):");
                            String validatedbday = bdayValidator(editbday);
                            ((Person) contact).setBirthDay(validatedbday);
                        } else {
                            System.out.println("fail");
                        }
                        ((Person) contact).setLastEditDate(String.valueOf(LocalDateTime.now()));
                    } else if (contact.getClass() == Company.class) {
                        System.out.println("Select a field (name, surname, number):");
                        String submenuEdit = scanner.nextLine();

                        if (submenuEdit.equals("name")) {
                            System.out.println("Enter name:");
                            String editName = scanner.nextLine();
                            ((Company) contact).setName(editName);
                        } else if (submenuEdit.equals("address")) {
                            System.out.println("Enter address:");
                            String address = scanner.nextLine();
                            ((Company) contact).setAddress(address);
                        } else if (submenuEdit.equals("number")) {
                            System.out.println("Enter number:");
                            String editNumber = scanner.nextLine();
                            String validatedNumber = numberValidator(editNumber);
                            ((Company) contact).setNumber(validatedNumber);
                        } else {
                            System.out.println("fail");
                        }
                    }
                    System.out.println("The record updated!");
                }

            } else if (menu1Option.equals("remove")) {
                if (contactsArray.isEmpty()) {
                    System.out.println("No records to remove");
                } else {
                    listContacts();
                    System.out.println("Select a record: ");
                    int x = scanner.nextInt();
                    contactsArray.remove(x - 1);
                }

            }  else if (menu1Option.equals("info")) {
                if (contactsArray.isEmpty()) {
                    System.out.println("no contacts are saved");
                } else {
                    listContacts();
                    System.out.println("Select a record:");
                    String optionStr = scanner.nextLine();
                    int option = Integer.parseInt(optionStr);
                    contactsArray.get(option - 1).displayAllInfo();
                }

            } else if (menu1Option.equals("exit")) {
                System.exit(0);
            }
        }
    }

    private static String genderValidator(String gender) {
        String verifiedGender = "";
        if(gender.isEmpty()){
            verifiedGender = "[no data]";
            System.out.println("Bad gender!");
        } else if (!gender.equals("M") && !gender.equals("F")){
            verifiedGender = "[no data]";
            System.out.println("Bad gender!");
        } else{
            verifiedGender = gender;
        }
        return verifiedGender;
    }

    private static String bdayValidator(String birthDate) {
        String verifiedBirthDate = "";
        try {
            verifiedBirthDate = String.valueOf(LocalDate.parse(birthDate));
        } catch (Exception e) {
            System.out.println("Bad birth date!");
        }
        return verifiedBirthDate;
    }

    private static String numberValidator(String editNumber) {
        String validatedNumbeer = "";
        if (editNumber.isEmpty()) {
            validatedNumbeer = "[no number]";
        } else if (validateNumber(editNumber) == false) {
            validatedNumbeer = "[no number]";
        } else {
            validatedNumbeer = editNumber;
        }
        return validatedNumbeer;
    }

    private static void listContacts() {
        for (int i = 0; i < contactsArray.size(); i++) {
            if (contactsArray.get(i).getClass() == Person.class) {
                contactsArray.get(i).getNumber();
            } else if (contactsArray.get(i).getClass() == Company.class) {
                contactsArray.get(i).getNumber();
            }
        }
    }


    public static boolean  validateNumber(String number){
        //valadition check
        final String regex = "^\\+?([\\da-zA-Z]+[\\s-]?)?(\\([\\da-zA-Z]{2,}(\\)[\\s-]|\\)$))?([\\da-zA-Z]{2,}[\\s-]?)*([\\da-zA-Z]{2,})?$";
        if(number.matches(regex)){
            return true;
        } else {
            return false;
        }
    }
}
