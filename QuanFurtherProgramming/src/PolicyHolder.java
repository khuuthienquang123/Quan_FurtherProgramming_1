import java.util.ArrayList;
import java.util.List;

class PolicyHolder {
    private String id;
    private String fullName;
    private String insuranceCard;
    private List<Dependent> dependents;

    public PolicyHolder(String id, String fullName, String insuranceCard) {
        this.id = id;
        this.fullName = fullName;
        this.insuranceCard = insuranceCard;
        this.dependents = new ArrayList<>();
    }

    public void addDependent(Dependent dependent) {
        dependents.add(dependent);
    }

    public void removeDependent(Dependent dependent) {
        dependents.remove(dependent);
    }
}