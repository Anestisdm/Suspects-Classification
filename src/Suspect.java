public class Suspect implements Comparable<Suspect> {

    private int AFM;
    private String firstName;
    private String lastName;
    private double savings;
    private double taxedIncome;

    public Suspect(int AFM, String firstName, String lastName, double savings, double taxedIncome){
        this.AFM=AFM;
        this.firstName=firstName;
        this.lastName=lastName;
        this.savings=savings;
        this.taxedIncome=taxedIncome;
    }

    public int key() {return AFM;}

    public int getAFM() {
        return AFM;
    }

    public void setAFM(int AFM) {
        this.AFM = AFM;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public double getSavings() {
        return savings;
    }

    public void setSavings(double savings) {
        this.savings = savings;
    }

    public double getTaxedIncome() {
        return taxedIncome;
    }

    public void setTaxedIncome(double taxedIncome) {
        this.taxedIncome = taxedIncome;
    }

    @Override
    public String toString() {
        return ("AFM: " + AFM + "\n" +
                "firstName: " + firstName + "\n" +
                "lastName: " + lastName + "\n" +
                "savings: " + savings + "\n" +
                "taxedIncome: " + taxedIncome + "\n" );
    }


    public int compareTo(Suspect s) {
        if (s==null){
            return 1;
        }
        double t1 = this.getTaxedIncome();
        double t2 = s.getTaxedIncome();
        double dev1 = this.getSavings()-t1;
        double dev2 = s.getSavings()-t2;
        boolean suspicious1 = false;
        boolean suspicious2 =false;
        if (t1<9000){
            suspicious1 = true;
        }
        if (t2<9000){
            suspicious2 = true;
        }
        if (suspicious1 && suspicious2){
            return 1;
        }
        else if (suspicious1){
            return 1;
        }
        else if (suspicious2){
            return -1;
        }
        else {
            return (dev1>=dev2)? 1:-1;
        }
    }
}
