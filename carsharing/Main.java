package carsharing;

import org.h2.engine.Database;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.concurrent.Executor;


public class Main {

    public static void main(String[] args) throws SQLException {

        Statement statment = null;
        Connection connection = null;

        try {
            Class.forName("org.h2.Driver");

            connection = DriverManager.getConnection("jdbc:h2:./src/carsharing/db/carsharing");
            connection.setAutoCommit(true);
            statment = connection.createStatement();

            statment.execute("create table COMPANY(ID int auto_increment, NAME varchar(255) UNIQUE NOT NULL,primary key(ID))");

            statment.execute("create table CAR (ID int auto_increment, " +
                                "NAME varchar(255) UNIQUE NOT NULL, " +
                                "COMPANY_ID int NOT NULL, primary key(ID) , " +
                                "foreign key (COMPANY_ID) references COMPANY(ID))");

            statment.execute("create table CUSTOMER (ID int auto_increment, " +
                                "NAME varchar(255) UNIQUE NOT NULL, " +
                                "RENTED_CAR_ID int, primary key(ID) , " +
                                "foreign key (RENTED_CAR_ID) references CAR(ID))");

        } catch (Exception e) {
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);
        boolean menu1 = true;
        while(menu1 == true)
        {
            System.out.println("1. Log in as a manager");
            System.out.println("2. Log in as a customer");
            System.out.println("3. Create a customer");
            System.out.println("0. Exit");
            int menu1_option = scanner.nextInt();

            if (menu1_option == 1)
            {
                statment.execute("ALTER TABLE COMPANY ALTER COLUMN ID RESTART WITH 1");

                boolean menu2 = true;
                while (menu2 == true)
                {
                    System.out.println("1. Company list");
                    System.out.println("2. Create a company");
                    System.out.println("0. Back");
                    int menu2_option = scanner.nextInt();

                    if (menu2_option == 1) {
                        ResultSet results = statment.executeQuery("select * from COMPANY");
                        if (results.next() == false) {
                            System.out.println("The company list is empty!");
                        } else {
                            System.out.println("Choose the company:");
                            do {
                                System.out.println(results.getInt("ID") + ". " + results.getString("NAME"));
                            }
                            while (results.next() == true);

                            System.out.println("0. Back");

                            int menu3_option = scanner.nextInt();

                            if (menu3_option == 0) {
                                continue;
                            } else {
                                String sql = "select NAME from COMPANY WHERE ID = " + "'" + String.valueOf(menu3_option) + "'";
                                ResultSet companysql = statment.executeQuery(sql);
                                String company_name = "";

                                if (companysql.next() == false) {
                                    System.out.println("The company list is empty!");
                                } else {
                                    do {
                                        company_name = companysql.getString("NAME");
                                    } while (companysql.next() == true);
                                }

                                boolean menu4 = true;
                                while (menu4 == true) {
                                    System.out.println(company_name + " company:");
                                    System.out.println("1. Car list");
                                    System.out.println("2. Create a car");
                                    System.out.println("0. Back");
                                    int menu4_option = scanner.nextInt();

                                    if (menu4_option == 2) {
                                        System.out.println("Enter the car name:");
                                        Scanner scanner3 = new Scanner(System.in);
                                        String car_name_entry = scanner3.nextLine();
                                        String sql2 = "INSERT INTO CAR(NAME, COMPANY_ID) VALUES ('" + car_name_entry + "' " + ",'" + String.valueOf(menu3_option) + "')";
                                        statment.execute(sql2);
                                        System.out.println("The car was created!");
                                        System.out.println();
                                    } else if (menu4_option == 1) {
                                        ResultSet car_results = statment.executeQuery("select * from CAR where COMPANY_ID = " + menu3_option);

                                        if (car_results.next() == false) {
                                            System.out.println("The car list is empty!");
                                        } else {
                                            System.out.println("Car list");
                                            int car_id = 1;
                                            do {
                                                System.out.println(car_id + ". " + car_results.getString("NAME"));
                                                car_id++;
                                            } while (car_results.next() == true);
                                        }
                                    } else if (menu4_option == 0) {
                                        menu4 = false;
                                        menu3_option = 0 ;
                                    }
                                }

                                if (menu3_option == 1) {
                                    ResultSet car_results = statment.executeQuery("select * from CAR where COMPANY_ID = '" + menu3_option);
                                    if (car_results.next() == false) {
                                        System.out.println("The car list is empty!");
                                    } else {
                                        System.out.println("Car list");
                                        do {
                                            System.out.println(results.getInt("ID") + ". " + results.getString("NAME"));
                                        } while (results.next() == true);
                                    }
                                    System.out.println();

                                }
                            }
                        }
                    }

                    else if (menu2_option == 2)
                    {
                        System.out.println("Enter the company name:");
                        Scanner scanner2 = new Scanner(System.in);
                        String company_name = scanner2.nextLine();
                        String sql = "INSERT INTO COMPANY(NAME) VALUES ('" + company_name + "');";
                        statment.execute(sql);
                        System.out.println("The company was created!");
                    }

                    else if (menu2_option == 0)
                    {
                        menu2 = false;
                    }
                }
            }

            else if(menu1_option == 3)
            {
                Scanner scanner5 = new Scanner(System.in);
                System.out.println("Enter the customer name:");
                String cust_name =scanner5.nextLine();
                statment.executeUpdate("INSERT INTO CUSTOMER (NAME, RENTED_CAR_ID) VALUES ('" + cust_name + "'," + "null)");
                System.out.println("The customer was added!");
            }

            else if(menu1_option == 2) {
                Scanner scanner6 = new Scanner(System.in);
                {
                    ResultSet results = statment.executeQuery("select * from CUSTOMER");

                    if (results.next() == false) {
                        System.out.println("The customer list is empty!");
                    } else {
                        System.out.println("Customer List");
                        do {
                            System.out.println(results.getInt("ID") + ". " + results.getString("NAME"));
                        } while (results.next() == true);
                        System.out.println("0. Back");
                        int customer_list = scanner.nextInt();

                        if (customer_list == 0) {
                            continue;
                        } else {
                            boolean cust_menu_1 = true;
                            while (cust_menu_1 == true) {
                                System.out.println("1. Rent a car");
                                System.out.println("2. Return a rented car");
                                System.out.println("3. My rented car");
                                System.out.println("0. Back");
                                int cust_menu_option_2 = scanner6.nextInt();

                                if (cust_menu_option_2 == 1) {

                                    ResultSet already_rented = statment.executeQuery("select * FROM CUSTOMER WHERE ID = '" + customer_list + "'");
                                    System.out.println(already_rented.next());
                                    if((Integer) already_rented.getObject("RENTED_CAR_ID") != null)
                                    {
                                        System.out.println("You've already rented a car!'.");
                                    }
                                    else {
                                        ResultSet companies_list = statment.executeQuery("select iD, NAME from COMPANY");

                                        if (companies_list.next() == false)
                                            System.out.println("there are no companies");
                                        else {
                                            System.out.println("Choose a company");
                                            do {
                                                System.out.println(companies_list.getInt("ID") + ". " + companies_list.getString("NAME"));
                                            } while (companies_list.next() == true);
                                            System.out.println("0. Back");

                                            int cust_menu_option_3 = scanner6.nextInt();

                                            if (cust_menu_option_3 == 0) {
                                                continue;
                                            } else {
                                                ResultSet car_list = statment.executeQuery("SELECT car.id, car.name, car.company_id  FROM car LEFT JOIN customer " +
                                                        " ON car.id = customer.rented_car_id  WHERE customer.name IS NULL;");

                                                if (car_list.next() == false) {
                                                    System.out.println("there are no cars to rent!");
                                                } else {
                                                    int car_index = 1;
                                                    LinkedHashMap<Integer, Integer> car_index_converter = new LinkedHashMap<>();
                                                    System.out.println("Choose a car");
                                                    do {
                                                        car_index_converter.put(car_index, car_list.getInt("ID"));
                                                        System.out.println(car_index + ". " + car_list.getString("NAME"));
                                                        car_index++;
                                                    } while (car_list.next() == true);
                                                    System.out.println("0. Back");

                                                    int cust_menu_option_4 = scanner6.nextInt();

                                                    if (cust_menu_option_4 == 0) {
                                                        continue;
                                                    } else {
                                                        int converted_index = car_index_converter.get(cust_menu_option_4);
                                                        statment.executeUpdate("UPDATE CUSTOMER SET RENTED_CAR_ID = " + "'" + converted_index +
                                                                "'" + "WHERE ID =" + "'" + customer_list + "'");
                                                        ResultSet rented_car = statment.executeQuery("select NAME from CAR where ID =" + "'" + converted_index + "'");
                                                        if (rented_car.next() == false) {
                                                            System.out.println("ERROR");
                                                        } else {
                                                            do {
                                                                System.out.println("You rented " + "'" + rented_car.getString("NAME") + "'");
                                                            } while (rented_car.next() == true);
                                                        }

                                                        if (cust_menu_option_4 == 0) {
                                                            continue;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } else if (cust_menu_option_2 == 2) {
                                    ResultSet car_rented = statment.executeQuery("select RENTED_CAR_ID FROM CUSTOMER WHERE ID = '" + customer_list + "'");

                                    if (car_rented.next() == false) {
                                        System.out.println("You did'nt rent a car!");
                                    } else {
                                        do {
                                            if ((Integer) car_rented.getObject("RENTED_CAR_ID") == null) {
                                                System.out.println("'You didn't rent a car!");
                                            } else {
                                            }
                                        } while (car_rented.next() == true);

                                        statment.executeUpdate("UPDATE CUSTOMER SET RENTED_CAR_ID = NULL WHERE ID = '" + customer_list + "'");
                                        System.out.println("You've returned a rented car!");
                                    }

                                }
                                else if (cust_menu_option_2 == 3) {
                                    ResultSet rented_car = statment.executeQuery("SELECT CAR.NAME, COMPANY.NAME FROM CUSTOMER " +
                                            "INNER JOIN CAR ON CUSTOMER.RENTED_CAR_ID = CAR.ID " +
                                            "INNER JOIN COMPANY ON CAR.COMPANY_ID = COMPANY.ID " +
                                            "WHERE CUSTOMER.ID = '" + customer_list + "'");

                                    if (rented_car.next() == false)
                                    {
                                        System.out.println("You didn't rent a car!");
                                    }
                                    else
                                    {
                                        System.out.println("your rented car:");
                                        System.out.println(rented_car.getString("CAR.NAME"));
                                        System.out.println("Company:");
                                        System.out.println(rented_car.getString("COMPANY.NAME"));
                                    }
                                }
                                else if (cust_menu_option_2 == 0)
                                {
                                    cust_menu_1 = false;
                                }
                            }
                        }
                    }
                }
            }
            else if (menu1_option == 0)
            {
                System.exit(0);
            }
        }
    }
}





