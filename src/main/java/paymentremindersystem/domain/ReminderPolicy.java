package paymentremindersystem.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class ReminderPolicy {

    private final List<ReminderStep> steps;

    public ReminderPolicy(List<ReminderStep> steps) {
        if (steps == null || steps.isEmpty()) {
            throw new IllegalArgumentException("at least one reminder step is required");
        }

        List<ReminderStep> sortedSteps = new ArrayList<ReminderStep>(steps);
        Collections.sort(sortedSteps, Comparator.comparingInt(ReminderStep::getDaysOverdue));
        Set<Integer> triggerDays = new HashSet<Integer>();
        for (ReminderStep step : sortedSteps) {
            if (step == null || !triggerDays.add(step.getDaysOverdue())) {
                throw new IllegalArgumentException("reminder trigger days must be unique");
            }
        }
        this.steps = Collections.unmodifiableList(sortedSteps);
    }

    public static ReminderPolicy defaultPolicy() {
        List<ReminderStep> steps = new ArrayList<ReminderStep>();
        steps.add(new ReminderStep(1, Channel.SMS));
        steps.add(new ReminderStep(3, Channel.WHATSAPP));
        steps.add(new ReminderStep(7, Channel.PHONE_CALL));
        return new ReminderPolicy(steps);
    }

    public ReminderStep getStepFor(int daysOverdue) {
        for (ReminderStep step : steps) {
            if (step.getDaysOverdue() == daysOverdue) {
                return step;
            }
        }
        return null;
    }

    public List<ReminderStep> getSteps() {
        return steps;
    }
}
