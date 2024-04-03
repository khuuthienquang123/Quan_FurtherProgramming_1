package Customer;

import java.util.ArrayList;
import java.util.List;

class PolicyHolder extends Customers {
    private String id;
    private String fullName;
    private String insuranceCard;
    private List<Dependent> dependents;

    public PolicyHolder(String id, String fullName, String insuranceCard) {
        super(id, fullName, insuranceCard);
        this.dependents = new ArrayList<>();
    }

    public void setDependents(List<Dependent> dependents) {
        this.dependents = dependents;
    }

    public void addDependent(Dependent dependent) {
        dependents.add(dependent);
    }

    public void removeDependent(Dependent dependent) {
        dependents.remove(dependent);
    }
}