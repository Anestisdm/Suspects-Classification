import java.io.IOException;
import java.util.Scanner;

public class RandomizedBST_Main {
    public static void main (String[] args) throws DublicateSuspectException, IOException {
        RandomizedBST Tree = new RandomizedBST();

        Scanner scanner = new Scanner(System.in);
        int a;
        do {
            System.out.println("\n~~~~~~~~~~~~~ Option Menu ~~~~~~~~~~~~~");
            System.out.println("1.Insert Suspects");
            System.out.println("2.Load Suspects");
            System.out.println("3.Update Savings of Suspect");
            System.out.println("4.Search Suspect");
            System.out.println("5.Remove Suspect");
            System.out.println("6.Mean Savings of Suspects");
            System.out.println("7.Print Top Suspects");
            System.out.println("8.Print Suspects classified by AFM");
            System.out.println("Enter Option [1-8] or press 0 to exit:   ");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            a= scanner.nextInt();

            switch(a) {
                case 0 :
                    break;
                case 1 :
                    System.out.println("Enter AFM of Suspect:");
                    int AFM1 = scanner.nextInt();
                    System.out.println("Enter First Name of Suspect:");
                    String FName = scanner.next();
                    System.out.println("Enter Last Name of Suspect:");
                    String LName = scanner.next();
                    System.out.println("Enter Savings of Suspect:");
                    Double Savings = scanner.nextDouble();
                    System.out.println("Enter Taxed Income of Suspect:");
                    Double TIncome = scanner.nextDouble();
                    Suspect s = new Suspect(AFM1,FName, LName, Savings, TIncome);
                    Tree.insert(s);
                    System.out.println("The Suspect added successfully!");
                    break;
                case 2 :
                    System.out.println("Enter file name to load data:");
                    String fileName = scanner.next();
                    Tree.load(fileName);
                    System.out.println("Data loaded successfully!");
                    break;
                case 3 :
                    System.out.println("Enter AFM of the Suspect you want to update:");
                    int AFM2 = scanner.nextInt();
                    System.out.println("Enter the new value of the savings:");
                    Double NewSavings = scanner.nextDouble();
                    Tree.updateSavings(AFM2,NewSavings);
                    break;
                case 4 :
                    System.out.println("Enter AFM or Last Name of Suspect you want to search:");
                    String input = scanner.next();
                    try {
                        int AFM3 = Integer.parseInt(input);
                        Tree.searchByAFM(AFM3);
                    } catch (NumberFormatException e) {
                        Tree.searchByLastName(input);
                    }
                    break;
                case 5 :
                    System.out.println("Enter the AFM of the Suspect you want to remove:");
                    int AFM4 = scanner.nextInt();
                    Tree.remove(AFM4);
                    break;
                case 6 :
                    System.out.println("Mean Savings of Suspects is "+ Tree.getMeanSavings());
                    break;
                case 7 :
                    System.out.println("Enter the parameter to print Top Suspects:");
                    int k = scanner.nextInt();
                    Tree.printΤopSuspects(k);
                    break;
                case 8:
                    System.out.println("Suspects\n");
                    Tree.printByAFM();
                    break;
                default:
                    System.out.println("Wrong Input");
            }
        }while (a!=0);
    }
}
