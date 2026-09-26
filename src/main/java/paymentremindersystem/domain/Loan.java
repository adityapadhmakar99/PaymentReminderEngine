package paymentremindersystem.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Loan {

    private final String id;
    private final List<Emi> emis;

    public Loan(String id, List<Emi> emis) {
        if (id == null || id.trim().isEmpty() || emis == null) {
            throw new IllegalArgumentException("valid id and emis are required");
        }
        this.id = id;
        this.emis = Collections.unmodifiableList(new ArrayList<Emi>(emis));
    }

    public String getId() {
        return id;
    }

    public List<Emi> getEmis() {
        return emis;
    }
}
