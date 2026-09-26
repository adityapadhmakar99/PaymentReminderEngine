package paymentremindersystem.repository.inmemory;

import paymentremindersystem.repository.ReminderHistory;
import java.util.HashSet;
import java.util.Set;

public final class InMemoryReminderHistory implements ReminderHistory {

    private final Set<String> sentReminderKeys = new HashSet<String>();

    @Override
    public boolean hasBeenSent(String emiId, int daysOverdue) {
        return sentReminderKeys.contains(key(emiId, daysOverdue));
    }

    @Override
    public void recordSent(String emiId, int daysOverdue) {
        sentReminderKeys.add(key(emiId, daysOverdue));
    }

    private String key(String emiId, int daysOverdue) {
        return emiId + ":" + daysOverdue;
    }
}
