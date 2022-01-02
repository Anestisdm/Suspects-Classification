import java.io.IOException;

public interface MafiaInterface<T> {

    void insert(Suspect item) throws DublicateSuspectException;

    void load(String filename) throws DublicateSuspectException, IOException;

    void updateSavings(int AFM, double savings);

    Suspect searchByAFM(int AFM);

    List searchByLastName(String last_name);

    void remove(int AFM);

    double getMeanSavings();

    void printΤopSuspects(int k);

    void printByAFM();

}
