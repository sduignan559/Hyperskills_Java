package battleship;

import java.util.Scanner;

public class Main
{

    public static void main(String[] args)
    {

        int battlefieldsize = 10;
        char [] Alphatbet_array = new char []{'A','B','C', 'D','E','F','G','H','I','J','K'};
        char [][] Battle_field_Player1 = new char [battlefieldsize][battlefieldsize];
        char [][] Battle_field_Player2 = new char [battlefieldsize][battlefieldsize];
        char [][] Battle_field_fog_Player1 = new char [battlefieldsize][battlefieldsize];
        char [][] Battle_field_fog_Player2 = new char [battlefieldsize][battlefieldsize];
        int[] shipSize_Player1 = new int[] {5,4,3,3,2};
        int[] shipSize_Player2 = new int[] {5,4,3,3,2};
        String[] shipname = new String[] {"Aircraft Carrier (5 cells)", "Battleship (4 cells)", "Submarine (3 cells)", "Cruiser (3 cells)", "Destroyer (2 cells)"};
        char[] ships =  new char[] {'A', 'B', 'S', 'C', 'D'};
        int hitcount = 0;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Player 1, place your ships on the game field\n");
        createEmptyBattfield(Battle_field_Player1, Battle_field_fog_Player1);
        printBattlefield(Alphatbet_array, Battle_field_Player1);
        createBattlefield(battlefieldsize, Alphatbet_array, Battle_field_Player1, shipSize_Player1, shipname, ships, scanner);
        promptEnterKey();

        System.out.println("...");
        System.out.println("Player 2, place your ships on the game field\n");
        createEmptyBattfield(Battle_field_Player2, Battle_field_fog_Player2);
        printBattlefield(Alphatbet_array, Battle_field_Player2);
        createBattlefield(battlefieldsize, Alphatbet_array, Battle_field_Player2, shipSize_Player2, shipname, ships, scanner);
        promptEnterKey();

        while(shipSize_Player1[0] + shipSize_Player1[1] + shipSize_Player1[2] + shipSize_Player1[3] + shipSize_Player1[4] > 0 ||
                shipSize_Player2[0] + shipSize_Player2[1] + shipSize_Player2[2] + shipSize_Player2[3] + shipSize_Player2[4] > 0)
        {
            System.out.println("...\n");
            printBattlefieldFog(Alphatbet_array , Battle_field_fog_Player2);
            System.out.println("---------------------");
            printBattlefield(Alphatbet_array, Battle_field_Player1);
            System.out.println("Player 1, it's your turn:\n	");
            takeShot(scanner, shipSize_Player2, Battle_field_Player2,Battle_field_fog_Player2, Alphatbet_array, ships, hitcount);
            promptEnterKey();

            System.out.println("...\n");
            printBattlefieldFog(Alphatbet_array , Battle_field_fog_Player1);
            System.out.println("---------------------");
            printBattlefield(Alphatbet_array, Battle_field_Player2);
            System.out.println("Player 2, it's your turn:\n	");
            takeShot(scanner, shipSize_Player1, Battle_field_Player1,Battle_field_fog_Player1, Alphatbet_array, ships, hitcount);
            promptEnterKey();
        }
    }

    public static void promptEnterKey() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Press Enter and pass the move to another player");
        scanner.nextLine();
    }

    public static void createEmptyBattfield(char[][] Battle_field, char[][] Battle_field_fog) {
        for (int i = 0; i < 10; i++)
        {
            for (int j = 0; j < 10; j++)
            {
                Battle_field[i][j] = '~';
                Battle_field_fog[i][j] = '~';
            }
        }
    }

    public static void createBattlefield(int battlefieldsize, char[] Alphatbet_array, char[][] Battle_field,
                                         int[] shipSize, String[] shipname, char[] ships, Scanner scanner) {

        int position1alpha = 0;
        int position1num = 0;
        int position2alpha = 0;
        int position2num = 0;


        boolean isValidLocation = true;

        for(int x=0; x<shipSize.length; x++)
        {
            do
            {
                System.out.println("Enter the coordinates of the " + shipname[x] + ":");

                String[] coordinates = scanner.nextLine().split(" ");

                position1alpha = coordinates[0].charAt(0)-65;
                position1num = Integer.parseInt(coordinates[0].substring(1))-1;
                position2alpha = coordinates[1].charAt(0)-65;
                position2num = Integer.parseInt(coordinates[1].substring(1))-1;


                //swap positions around so that the bigger position comes first

                if(position1alpha == position2alpha && position1num > position2num)
                {
                    int temp = position1num;
                    position1num= position2num;
                    position2num = temp;
                }
                if(position1num == position2num && position1alpha > position2alpha)
                {
                    int temp = position1alpha;
                    position1alpha = position2alpha;
                    position2alpha = temp;
                }

                //check for valid ship location
                if(position1alpha != position2alpha &&  position1num != position2num)
                {
                    System.out.println("Error! ships cant be diagonal:");
                    isValidLocation = false;
                }

                //check if ship is outside the boundrys
                else if (position1alpha < 0 || position2alpha < 0 || position2alpha > battlefieldsize || position2num > battlefieldsize)
                {
                    System.out.println("Error! ships outside boundries:");
                    isValidLocation = false;
                }

                //check if position is not equal to the length of the ship
                else if((position1alpha == position2alpha && position2num - position1num != shipSize[x]-1) || (position1num == position2num && position2alpha - position1alpha != shipSize[x]-1))
                {
                    System.out.println("Error! Wrong length of the Ship! Try again:");
                    isValidLocation = false;
                }

                else if (squareIsOccupied (position1alpha, position2alpha, position1num, position2num,Battle_field))
                {
                    System.out.println("Error! too close to another ship");
                    isValidLocation = false;
                }

                else
                {
                    isValidLocation = true;
                }
            }

            while
            (isValidLocation == false);


            //add ship to the battlefield

            if(position1alpha == position2alpha)
            {
                for(int i = position1num; i<= position2num; i++)
                {
                    Battle_field[position1alpha][i] = ships[x];
                }
            }

            if(position1num == position2num)
            {
                for(int i = position1alpha; i <= position2alpha; i++)
                {
                    Battle_field[i][position1num] = ships[x];
                }
            }
            System.out.println();
            printBattlefield(Alphatbet_array , Battle_field);
        }
    }

    public static void takeShot(Scanner scanner, int shipSize[],char Battle_field[][], char Battle_field_fog[][] ,char Alphatbet_array[], char ships [], int hitcount)
    {
        boolean isValidShot = false;

        {
            while(isValidShot == false)
            {
                String shotCordinates = scanner.nextLine();

                int shotPositionAlpha = shotCordinates.charAt(0)-65;
                int shotPositionNum = Integer.parseInt(shotCordinates.substring(1))-1;

                if(shotPositionAlpha > 9 || shotPositionNum >9)
                {
                    System.out.println();
                    isValidShot = false;
                }

                else if(Battle_field[shotPositionAlpha][shotPositionNum] == '~')
                {
                    isValidShot = true;
                    Battle_field_fog[shotPositionAlpha][shotPositionNum] = 'M';
                    System.out.println();
                    System.out.println("You missed!\n");
                }

                else if(Battle_field[shotPositionAlpha][shotPositionNum] == 'X')
                {
                    isValidShot = true;
                    Battle_field_fog[shotPositionAlpha][shotPositionNum] = 'X';
                    System.out.println();
                    System.out.println("You missed!\n");
                }

                else if(Battle_field[shotPositionAlpha][shotPositionNum] == 'M')
                {
                    isValidShot = true;
                    Battle_field_fog[shotPositionAlpha][shotPositionNum] = 'M';
                    System.out.println();
                    System.out.println("You missed!\n");
                }

                else if ((Battle_field[shotPositionAlpha][shotPositionNum] != '~') && (shipSize[0] + shipSize[1]+ shipSize[2]+ shipSize[3]+ shipSize[4]==1))
                {
                    isValidShot = true;
                    hitcount++;

                    Battle_field[shotPositionAlpha][shotPositionNum] = 'X';
                    Battle_field_fog[shotPositionAlpha][shotPositionNum] = 'X';
                    System.out.println();
                    System.out.println("You sank the last ship. You won. Congratulations!");
                    System.exit(0);
                }

                else if (Battle_field[shotPositionAlpha][shotPositionNum] != '~' && hitcount < 16)
                {
                    switch(Battle_field[shotPositionAlpha][shotPositionNum])
                    {
                        case 'A':
                            shipSize[0] = shipSize[0] - 1;
                            hitcount++;
                            isValidShot = true;

                            Battle_field[shotPositionAlpha][shotPositionNum] = 'X';
                            Battle_field_fog[shotPositionAlpha][shotPositionNum] = 'X';


                            if(shipSize[0] == 0)
                            {
                                System.out.println("You sank a ship!\n");
                            }
                            else
                            {
                                System.out.println("You hit a ship!\n");
                            }
                            break;

                        case 'B':
                            shipSize[1] = shipSize[1] - 1;
                            hitcount++;
                            isValidShot = true;

                            Battle_field[shotPositionAlpha][shotPositionNum] = 'X';
                            Battle_field_fog[shotPositionAlpha][shotPositionNum] = 'X';

                            if(shipSize[1] == 0)
                            {
                                System.out.println("You sank a ship!\n");
                            }
                            else
                            {
                                System.out.println("You hit a ship!\n");
                            }


                            break;

                        case 'S':
                            shipSize[2] = shipSize[2] - 1;
                            hitcount++;
                            isValidShot = true;

                            Battle_field[shotPositionAlpha][shotPositionNum] = 'X';
                            Battle_field_fog[shotPositionAlpha][shotPositionNum] = 'X';

                            if(shipSize[2] == 0)
                            {
                                System.out.println("You sank a ship!\n");
                            }
                            else
                            {
                                System.out.println("You hit a ship!\n");
                            }

                            break;

                        case 'C':
                            shipSize[3] = shipSize[3] - 1;
                            hitcount++;
                            isValidShot = true;

                            Battle_field[shotPositionAlpha][shotPositionNum] = 'X';
                            Battle_field_fog[shotPositionAlpha][shotPositionNum] = 'X';

                            if(shipSize[3] == 0)
                            {
                                System.out.println("You sank a ship! Try again\n");
                            }
                            else
                            {
                                System.out.println("You hit a ship! Try again\n");
                            }

                            break;

                        case 'D':
                            shipSize[4] = shipSize[4] - 1;
                            hitcount++;
                            isValidShot = true;

                            Battle_field[shotPositionAlpha][shotPositionNum] = 'X';
                            Battle_field_fog[shotPositionAlpha][shotPositionNum] = 'X';

                            if(shipSize[4] == 0)
                            {
                                System.out.println("You sank a ship!\n");
                            }
                            else
                            {
                                System.out.println("You hit a ship!\n");
                            }

                            break;
                    }
                }
            }
        }
    }

    //print the battlefield
    public static void printBattlefield(char Alphatbet_array[],char Battle_field[][])
    {
        for (int i = -1; i < 10; i++)
        {
            if (i == -1)
            {
                System.out.print(" ");

                for (int k = 1; k <= 10; k++)
                {
                    System.out.printf(" " + k);
                }
            }

            else
            {
                for (int j = -1; j < 10; j++)
                {
                    if (j == -1)
                    {
                        System.out.print(Alphatbet_array[i]);
                    }
                    else if(Battle_field[i][j] == '~' || Battle_field[i][j] == 'X' )
                    {
                        System.out.print(" " + Battle_field[i][j]);
                    }
                    else
                    {
                        System.out.print(" " + 'O');
                    }
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void printBattlefieldFog(char Alphatbet_array[],char Battle_field_fog[][])
    {
        for (int i = -1; i < 10; i++) {

            if (i == -1) {

                System.out.print(" ");
                for (int k = 1; k <= 10; k++)
                {
                    System.out.printf(" " + k);
                }
            }
            else
            {
                for (int j = -1; j < 10; j++)
                {
                    if (j == -1)
                    {
                        System.out.print(Alphatbet_array[i]);
                    }
                    else
                    {
                        System.out.print(" " + Battle_field_fog[i][j]);
                    }
                }
            }
            System.out.println();
        }
        System.out.println();
    }



    public static boolean squareIsOccupied (int position1alpha, int position2alpha, int position1num, int position2num, char Battle_field[][])
    {
        boolean isOccupied = false;

        for(int i=position1alpha -1; i <= position2alpha+1 ; i++)
        {
            for(int j = position1num -1; j <= position2num +1; j++)
            {
                if(i<0 || i> 9 || j<0 || j>9)
                {
                    continue;
                }

                if(Battle_field[i][j] != '~') {
                    isOccupied = true;
                    return true;
                }
            }
        } return false;
    }
}