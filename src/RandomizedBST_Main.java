import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class RandomizedBST_Main {
    public static void main (String[] args) throws DublicateSuspectException, IOException {
        ImageIcon icon1 = new ImageIcon("src/images/suspect.png");
        Image suspect = icon1.getImage().getScaledInstance(40,40,0);
        ImageIcon icon2 = new ImageIcon("src/images/insert.png");
        Image insert = icon2.getImage().getScaledInstance(40,40,0);
        ImageIcon icon3 = new ImageIcon("src/images/true.png");
        Image succeed = icon3.getImage().getScaledInstance(40,40,0);
        ImageIcon icon4 = new ImageIcon("src/images/warning.png");
        Image warning = icon4.getImage().getScaledInstance(40, 40, 0);
        ImageIcon icon5 = new ImageIcon("src/images/load.png");
        Image load= icon5.getImage().getScaledInstance(40, 40, 0);
        ImageIcon icon6 = new ImageIcon("src/images/deposit.png");
        Image deposit= icon6.getImage().getScaledInstance(40, 40, 0);
        ImageIcon icon7 = new ImageIcon("src/images/search.png");
        Image search= icon7.getImage().getScaledInstance(40, 40, 0);
        ImageIcon icon8 = new ImageIcon("src/images/remove.png");
        Image remove= icon8.getImage().getScaledInstance(40, 40, 0);
        ImageIcon icon9 = new ImageIcon("src/images/mean-savings.png");
        Image mean_savings= icon9.getImage().getScaledInstance(40, 40, 0);
        ImageIcon icon10 = new ImageIcon("src/images/top-suspects.png");
        Image top_suspects= icon10.getImage().getScaledInstance(40, 40, 0);
        ImageIcon icon11 = new ImageIcon("src/images/exit.png");
        Image exit= icon11.getImage().getScaledInstance(40, 40, 0);
        try {
            RandomizedBST Tree = new RandomizedBST();
            int a = 0;
            do {
                Object[] message = {
                        "1 - Insert Suspects",
                        "2 - Load Suspects",
                        "3 - Update Savings of Suspect",
                        "4 - Search Suspect",
                        "5 - Remove Suspect",
                        "6 - Mean Savings of Suspects",
                        "7 - Print Top Suspects",
                        "8 - Print Suspects classified by\n      Tax Identification Number"
                };
                String input = (String) JOptionPane.showInputDialog(null,message, "Menu", JOptionPane.INFORMATION_MESSAGE,new ImageIcon(suspect),null,null);
                if (input!=null && !input.equals("")) {
                    a= Integer.parseInt(input);
                    switch (a) {
                        case 0:
                            break;
                        case 1:
                            JTextField TIN = new JTextField  ();
                            JTextField  FName = new JTextField ();
                            JTextField  LName = new JTextField ();
                            JTextField Savings = new JTextField  ();
                            JTextField  TIncome = new JTextField ();
                            Object[] message2 = {
                                    "Tax Identification Number:", TIN,
                                    "First Name:", FName,
                                    "Last Name:", LName,
                                    "Savings:", Savings,
                                    "Taxed Income", TIncome
                            };
                            try {
                                int option = JOptionPane.showConfirmDialog(null, message2, "Add Suspect", JOptionPane.INFORMATION_MESSAGE, JOptionPane.PLAIN_MESSAGE, new ImageIcon(insert));
                                if (option == JOptionPane.OK_OPTION) {
                                    Suspect s = new Suspect(Integer.parseInt(TIN.getText()), FName.getText(), LName.getText(), Double.parseDouble(Savings.getText()), Double.parseDouble(TIncome.getText()));
                                    Tree.insert(s);
                                    JOptionPane.showMessageDialog(null, "The Suspect added successfully!", "Message", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(succeed));
                                }
                            }
                            catch (NumberFormatException e){
                                System.out.println("Wrong input format! Try again");
                                JOptionPane.showMessageDialog(null,"Wrong input format!","Error",JOptionPane.WARNING_MESSAGE,new ImageIcon (warning));
                            }
                            break;
                        case 2:
                            String fileName = (String) JOptionPane.showInputDialog(null,"Enter file name to load data:","Load Suspects",JOptionPane.QUESTION_MESSAGE,new ImageIcon (load),null,null);
                            if (fileName !=null) {
                                try {
                                    Tree.load(fileName);
                                    JOptionPane.showMessageDialog(null, "Data loaded successfully!", "Message", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(succeed));
                                } catch (FileNotFoundException e) {
                                    JOptionPane.showMessageDialog(null, "File with the name " + fileName + " was not found in the folder data", "Error", JOptionPane.WARNING_MESSAGE, new ImageIcon(warning));
                                    System.out.println("File with the name " + fileName + " was not found in the folder 'data'");
                                }
                            }
                            break;
                        case 3:
                            JTextField TIN2 = new JTextField  ();
                            JTextField newSavings = new JTextField  ();
                            Object[] message3 = {
                                    "Enter AFM of the Suspect you want to update:", TIN2,
                                    "Enter the new value of the savings:", newSavings
                            };
                            try {
                                int option = JOptionPane.showConfirmDialog(null, message3, "Update Savings", JOptionPane.INFORMATION_MESSAGE, JOptionPane.PLAIN_MESSAGE, new ImageIcon(deposit));
                                if (option == JOptionPane.OK_OPTION) {
                                    Tree.updateSavings(Integer.parseInt(TIN2.getText()), Double.parseDouble(newSavings.getText()));
                                }
                            }
                            catch (NumberFormatException e){
                                System.out.println("Wrong input format! Try again");
                                JOptionPane.showMessageDialog(null,"Wrong input format!","Error",JOptionPane.WARNING_MESSAGE,new ImageIcon (warning));
                            }
                            break;
                        case 4:
                            String input2 = (String) JOptionPane.showInputDialog(null,"Enter AFM or Last Name of Suspect you want to search:","Search Suspect",JOptionPane.QUESTION_MESSAGE,new ImageIcon (search),null,null);
                            if (input2 !=null && !input2.equals("")) {
                                try {
                                    int AFM3 = Integer.parseInt(input2);
                                    Tree.searchByAFM(AFM3);
                                } catch (NumberFormatException e) {
                                    Tree.searchByLastName(input2);
                                }
                            }
                            else if (input2 !=null && input2.equals("")){
                                JOptionPane.showMessageDialog(null,"Needed input!","Message",JOptionPane.WARNING_MESSAGE,new ImageIcon(warning));
                            }
                            break;
                        case 5:
                            String TIN3 = (String) JOptionPane.showInputDialog(null,"Enter the AFM of the Suspect you want to remove:","Remove Suspect",JOptionPane.QUESTION_MESSAGE,new ImageIcon (remove),null,null);
                            if (TIN3 !=null && !TIN3.equals("")) {
                                try {
                                    Tree.remove(Integer.parseInt(TIN3));
                                }
                                catch (NumberFormatException e) {
                                    System.out.println("Wrong input format! Try again");
                                    JOptionPane.showMessageDialog(null,"Wrong input format!","Error",JOptionPane.WARNING_MESSAGE,new ImageIcon (warning));
                                }
                            }
                            else if (TIN3 !=null && TIN3.equals("")){
                                JOptionPane.showMessageDialog(null,"Needed input!","Message",JOptionPane.WARNING_MESSAGE,new ImageIcon(warning));
                            }
                            break;
                        case 6:
                            JOptionPane.showMessageDialog(null,"Mean Savings of Suspects is " + Tree.getMeanSavings(),"Mean Savings",JOptionPane.WARNING_MESSAGE,new ImageIcon(mean_savings));
                            break;
                        case 7:
                            String k = (String) JOptionPane.showInputDialog(null,"How many Suspects you want to print:","Top Suspects",JOptionPane.QUESTION_MESSAGE,new ImageIcon (top_suspects),null,null);
                            if (k !=null && !k.equals("")) {
                                try {
                                    Tree.printΤopSuspects(Integer.parseInt(k));
                                }
                                catch (NumberFormatException e) {
                                    System.out.println("Wrong input format! Try again");
                                    JOptionPane.showMessageDialog(null,"Wrong input format!","Error",JOptionPane.WARNING_MESSAGE,new ImageIcon (warning));
                                }
                            }
                            else if (k !=null && k.equals("")){
                                JOptionPane.showMessageDialog(null,"Needed input!","Message",JOptionPane.WARNING_MESSAGE,new ImageIcon(warning));
                            }
                            break;
                        case 8:
                            Tree.printByAFM();
                            break;
                        default:
                            System.out.println("Wrong Input");
                    }
                }
                else if (input!=null && input.equals("")){
                    JOptionPane.showMessageDialog(null,"Input needed!","Error",JOptionPane.WARNING_MESSAGE,new ImageIcon (warning));
                }
                else{
                    int input4 = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?","Exit",JOptionPane.WARNING_MESSAGE,JOptionPane.PLAIN_MESSAGE, new ImageIcon(exit));
                    if (input4 == JOptionPane.YES_OPTION) {
                        break;
                    } else if (input4 == JOptionPane.NO_OPTION || input4 == JOptionPane.CANCEL_OPTION) {
                        a=-1;
                    }
                }
            } while (a != 0);
        }
        catch (InputMismatchException e){
            System.out.println("Wrong input!");
            JOptionPane.showMessageDialog(null,"Wrong input!","Error",JOptionPane.WARNING_MESSAGE,new ImageIcon (warning));
        }

    }
}
