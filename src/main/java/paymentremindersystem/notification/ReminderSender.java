package paymentremindersystem.notification;

import paymentremindersystem.domain.Reminder;

public interface ReminderSender {

    void send(Reminder reminder);
}
