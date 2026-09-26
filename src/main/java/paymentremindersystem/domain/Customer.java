package paymentremindersystem.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Customer {

    private final String id;
    private final String phoneNumber;
    private final String whatsappNumber;
    private final List<Loan> loans;

    public Customer(String id, String phoneNumber, String whatsappNumber, List<Loan> loans) {
        if (id == null || id.trim().isEmpty() || loans == null) {
            throw new IllegalArgumentException("valid id and loans are required");
        }
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.whatsappNumber = whatsappNumber;
        this.loans = Collections.unmodifiableList(new ArrayList<Loan>(loans));
    }

    public String getId() {
        return id;
    }

    public String getContactFor(Channel channel) {
        if (channel == Channel.SMS || channel == Channel.PHONE_CALL) {
            return phoneNumber;
        } else if (channel == Channel.WHATSAPP) {
            return whatsappNumber;
        }
        return null;
    }

    public List<Loan> getLoans() {
        return loans;
    }
}
