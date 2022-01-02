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
        String filePath = "Data\\"+filename+".txt";
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
            System.out.println("There is no suspect that corresponds to this AFM!");
        }
        else {
            SuspectToUpdate.setSavings(savings);
            System.out.println("Savings updated successfully!");
        }
    }

    @Override
    public Suspect searchByAFM(int AFM) {
        Suspect SuspectToPrint = searchR(root, AFM);
        if (SuspectToPrint == null){
            System.out.println("There is no Suspect that corresponds to this AFM!");
            return SuspectToPrint;
        }
        System.out.println("The Suspect that corresponds to AFM is:\n"+SuspectToPrint.toString());
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
            System.out.println("No Suspect was found with this Last Name!");
            return null;
        }
        else if (Suspects.size()<=5){
            System.out.println("The Suspects that corresponds to Last Name are:");
            Suspects.printList();
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
            System.out.println("No Suspect was found with this AFM!");
            return;
        }
        System.out.println("The Suspect with AFM "+AFM+" was removed");

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
        System.out.println("The Top " + k + " Suspects are:");
        int i = 0;
        while (i< TopSuspects.length) {
            if (TopSuspects[i]!=null) {
                System.out.println((i+1)+ ".\n" + TopSuspects[i].toString());
                i++;
            }
            else {
                break;
            }
        }
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
        traverseInOrder(root);
    }

    private void traverseInOrder(TreeNode h) {
        if (h == null) return;
        traverseInOrder(h.left);
        System.out.println(h.item.toString());
        traverseInOrder(h.right); }

}
