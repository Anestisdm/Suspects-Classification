import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class RandomizedBST implements MafiaInterface {

    private class TreeNode {

        Suspect item;
        TreeNode left; // pointer to left subtree
        TreeNode right; // pointer to right subtree
        int N; //number of nodes in the subtree rooted at this TreeNode

        public TreeNode(Suspect item){
            this.item=item;
            this.N=0;
        }

        public int getN() {
            return N;
        }

        public void setN(int n) {
            N = n;
        }
    }

    private TreeNode root; //root in the BST
    private int count; // count of suspects
    String str ="The Suspects by Tax Identification Number are:\n\n";
    ImageIcon icon1 = new ImageIcon("src/images/true.png");
    Image succeed = icon1.getImage().getScaledInstance(40,40,0);
    ImageIcon icon2 = new ImageIcon("src/images/warning.png");
    Image warning = icon2.getImage().getScaledInstance(40, 40, 0);
    ImageIcon icon3 = new ImageIcon("src/images/suspect.png");
    Image suspect = icon3.getImage().getScaledInstance(40, 40, 0);
    ImageIcon icon4 = new ImageIcon("src/images/top-suspects.png");
    Image top_suspects = icon4.getImage().getScaledInstance(40, 40, 0);
    ImageIcon icon5 = new ImageIcon("src/images/print-by-tax.png");
    Image print_by_tax = icon5.getImage().getScaledInstance(40, 40, 0);

    public RandomizedBST(){
        this.root = null;
        this.count = 0 ;
    }

    private int less (int afm1, int afm2){
        if (afm1<afm2){
            return 1;
        }
        else if (afm1>afm2){
            return -1;
        }
        else {
            return 0;
        }
    }

    @Override
    public void insert(Suspect item) throws DublicateSuspectException {
        root = insertR(root,item);
        count++;
    }

    private TreeNode insertR (TreeNode h, Suspect x) throws DublicateSuspectException {
       if (h==null) {
           return new TreeNode(x);
       }
        if (Math.random() < (1.0/(h.N+1.0))) {
            return insertAsRoot(h, x);
        }
        if (less(x.key(), h.item.key())==1) {
            h.N++;
            h.left = insertR(h.left, x);
        }
        else if(less(x.key(), h.item.key())==-1 ){
            h.N++;
            h.right = insertR(h.right, x);
        }
        else {
            throw new DublicateSuspectException("Error");
        }
        return h;
    }

    private TreeNode insertAsRoot (TreeNode h, Suspect x) throws DublicateSuspectException {
        if (h==null) {
            return new TreeNode(x);
        }
        if (less(x.key(), h.item.key())==1) {
            h.N++;
            h.left = insertAsRoot(h.left, x);
            h = rotR(h);
        }
        else if(less(x.key(), h.item.key())==-1 ){
            h.N++;
            h.right = insertAsRoot(h.right, x);
            h= rotL(h);
        }
        else {
            throw new DublicateSuspectException("Error");
        }
        return h;
    }

    private TreeNode rotR(TreeNode h) {
        TreeNode x = h.left;
        h.left = x.right;
        x.right = h;
        if (x.right!=null) {
            x.right.setN(getNodes(x.right.left) + getNodes(x.right.right));
        }
        x.setN( getNodes(x.left)+getNodes(x.right));
        return x;
    }

    private TreeNode rotL(TreeNode h) {
        TreeNode x = h.right;
        h.right = x.left;
        x.left = h;
        if (x.left!=null) {
            x.left.setN(getNodes(x.left.left) + getNodes(x.left.right));
        }
        x.setN( getNodes(x.left)+getNodes(x.right));
        return x;
    }

    public int getNodes(TreeNode h)
    {
        return h == null ? 0 : h.getN()+1;
    }


    @Override
    public void load(String filename) throws DublicateSuspectException, IOException {
        String filePath = "src\\data\\"+filename+".txt";
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line = reader.readLine();
        while (line!=null){
            StringTokenizer st = new StringTokenizer(line, " ");
            int afm = Integer.parseInt(st.nextToken());
            String firstName = st.nextToken();
            String lastName = st.nextToken();
            double savings = Double.parseDouble(st.nextToken());
            double taxedIncome = Double.parseDouble(st.nextToken());
            Suspect object = new Suspect(afm, firstName, lastName, savings, taxedIncome);
            insert(object);
            line = reader.readLine();
        }
    }

    @Override
    public void updateSavings(int AFM, double savings) {
        Suspect SuspectToUpdate = searchR(root, AFM);
        if (SuspectToUpdate == null){
            JOptionPane.showMessageDialog(null,"There is no suspect that corresponds to this AFM!","Message",JOptionPane.WARNING_MESSAGE,new ImageIcon(warning));
        }
        else {
            SuspectToUpdate.setSavings(savings);
            JOptionPane.showMessageDialog(null,"Savings updated successfully!","Message",JOptionPane.WARNING_MESSAGE,new ImageIcon(succeed));

        }
    }

    @Override
    public Suspect searchByAFM(int AFM) {
        Suspect SuspectToPrint = searchR(root, AFM);
        if (SuspectToPrint == null){
            JOptionPane.showMessageDialog(null,"There is no suspect that corresponds to this AFM!","Message",JOptionPane.WARNING_MESSAGE,new ImageIcon(warning));
            return SuspectToPrint;
        }
        JOptionPane.showMessageDialog(null,"The Suspect that corresponds to AFM is:\n"+SuspectToPrint.toString(),"Message",JOptionPane.WARNING_MESSAGE,new ImageIcon(suspect));
        return SuspectToPrint;
    }

    private Suspect searchR(TreeNode h, int afm) {
        if (h == null) return null;
        if (equals(afm, h.item.key())) return h.item;
        if (less(afm, h.item.key())==1) return searchR(h.left, afm);
        else return searchR(h.right, afm); }

    private boolean equals(int afm1, int afm2) {
        return afm1==(afm2);
    }

    @Override
    public List searchByLastName(String last_name) {
        List Suspects = new List();
        traverseR(root, last_name, Suspects);
        if (Suspects.isEmpty()){
            JOptionPane.showMessageDialog(null,"No Suspect was found with this Last Name!","Message",JOptionPane.WARNING_MESSAGE,new ImageIcon(warning));
            return null;
        }
        else if (Suspects.size()<=5){
            String output = Suspects.returnList();
            JOptionPane.showMessageDialog(null,"The Suspects that corresponds to Last Name are:\n\n"+output,"Message",JOptionPane.WARNING_MESSAGE,new ImageIcon(suspect));
            return Suspects;
        }
        else {
            return Suspects;
        }
    }

    private void traverseR(TreeNode h,String last_name, List Suspects) {
        if (h == null) return;
        if (h.item.getLastName().equals(last_name)){
            Suspects.insertAtFront(h.item);
        }
        traverseR(h.left, last_name, Suspects);
        traverseR(h.right, last_name, Suspects); }


    @Override
    public void remove(int AFM) {
        int a = count;
        TreeNode h = removeR(root, AFM,a);
        if (count==a){
            JOptionPane.showMessageDialog(null,"No Suspect was found with this AFM!","Message",JOptionPane.WARNING_MESSAGE,new ImageIcon(warning));
            return;
        }
        JOptionPane.showMessageDialog(null,"The Suspect with AFM "+AFM+" was removed","Message",JOptionPane.WARNING_MESSAGE,new ImageIcon(succeed));


    }

    private TreeNode removeR(TreeNode h, int afm, int countOfNodes) {
        if (h == null) {
            return null;
        }
        int w = h.item.key();
        if (less(afm, w)==1) {
            h.left = removeR(h.left, afm, countOfNodes);
        }
        if (less(w, afm)==1) {
            h.right = removeR(h.right, afm, countOfNodes);
        }
        if (equals(afm, w)) {
            h = joinLR(h.left, h.right);
            count--;
        }
        if (count<countOfNodes) {
            if (h!=null) {
                h.N--;
            }
        }
        return h;

    }

    private TreeNode joinLR(TreeNode a, TreeNode b) {
        int N = getNodes(a) + getNodes(b);
        if (a == null) {
            return b;
        }
        if (b == null) {
            return a;
        }
        if (Math.random()*N < 1.0*a.N) {
            a.right = joinLR(a.right, b);
            a.setN(a.N+b.N);
            return a;
        }
        else {
            b.left = joinLR(a, b.left);
            b.setN(b.N+a.N);
            return b;
        }
    }


    @Override
    public double getMeanSavings() {
        double sumOfSavings = traverseR(root);
        return (sumOfSavings/count);
    }

    private double traverseR(TreeNode h) {
        if (h == null) return 0;
        return h.item.getSavings() + traverseR(h.left) + traverseR(h.right);
    }

    @Override
    public void printΤopSuspects(int k) {
        Suspect [] TopSuspects = new Suspect [k];
        traverseSuspects(root, TopSuspects);
        String output = "The Top " + k + " Suspects are:\n\n";
        int i = 0;
        while (i< TopSuspects.length) {
            if (TopSuspects[i]!=null) {
                output += (i+1)+ ".\n" + TopSuspects[i].toString();
                i++;
            }
            else {
                break;
            }
        }
        JOptionPane.showMessageDialog(null,output,"Top Suspects",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(top_suspects));
    }

    private void traverseSuspects(TreeNode h, Suspect [] TopSuspects) {
        if (h == null){ return;}
        if (h.item.compareTo(TopSuspects[TopSuspects.length-1])>0 ) {
          insertTopSuspects(h.item, TopSuspects);
        }
        traverseSuspects(h.left,TopSuspects);
        traverseSuspects(h.right,TopSuspects);
    }

    private void insertTopSuspects(Suspect s, Suspect [] TopSuspects) {
        int position = 0;
        while (position < TopSuspects.length) {
            if (s.compareTo(TopSuspects[position]) > 0) {
                break;
            }
            position++;
        }
        for (int i = TopSuspects.length - 1; i > position; i--) {
            TopSuspects[i] = TopSuspects[i - 1];
        }
        TopSuspects[position] = s;
    }


    @Override
    public void printByAFM() {
        String output = traverseInOrder(root);
        JOptionPane.showMessageDialog(null,output,"Suspects",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(print_by_tax));

    }

    private String traverseInOrder(TreeNode h) {
        if (h == null) return str;
        traverseInOrder(h.left);
        str+=h.item.toString()+"\n";
        traverseInOrder(h.right);
        return str;
    }

}
