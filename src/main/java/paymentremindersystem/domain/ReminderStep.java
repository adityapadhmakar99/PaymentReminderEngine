package paymentremindersystem.domain;

public final class ReminderStep {

    private final int daysOverdue;
    private final Channel channel;

    public ReminderStep(int daysOverdue, Channel channel) {
        if (daysOverdue < 1) {
            throw new IllegalArgumentException("daysOverdue must be at least 1");
        }
        if (channel == null) {
            throw new IllegalArgumentException("channel is required");
        }
        this.daysOverdue = daysOverdue;
        this.channel = channel;
    }

    public int getDaysOverdue() {
        return daysOverdue;
    }

    public Channel getChannel() {
        return channel;
    }
}
