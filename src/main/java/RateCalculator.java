import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * This represents a rate calculator
 */
public class RateCalculator {
    private Robot robot;
    private Shift shift;

    public RateCalculator(Robot robot, Shift shift) {
        this.robot = robot;
        this.shift = shift;
    }

    public int calculateValue(Shift shift) {
        LocalDateTime startTime = shift.getStartTime();
        LocalDateTime endTime = shift.getEndTime();
        if (startTime.isAfter(endTime)) {
            return 0;
        }
        if (startTime.isEqual(endTime)) {
            return 0;
        }

        // If the duration is longer than 8 hours, split into two parts and run recursively
        if (startTime.plusHours(8).isBefore(endTime)) {
            return calculateValue(new Shift(startTime, startTime.plusHours(8))) +
                    calculateValue(new Shift(startTime.plusHours(9), endTime));
        }

        // If the duration is longer than 1 hour, split into two parts and run recursively
        if (startTime.plusHours(1).isBefore(endTime)) {
            return calculateValue(new Shift(startTime, startTime.plusHours(1))) +
                    calculateValue(new Shift(startTime.plusHours(1), endTime));
        }

        // find the nearest hour
        LocalDateTime nextHour = startTime.plusHours(1);
        nextHour = nextHour.truncatedTo(ChronoUnit.HOURS);
        if (nextHour.isBefore(endTime)) {
            return calculateHourValue(this.robot, new Shift(startTime, nextHour)) +
                    calculateHourValue(this.robot, new Shift(nextHour, endTime));
        } else {
            return calculateHourValue(this.robot, shift);
        }
    }

    // This calculate the total cost from the start time of the shift to the nearest hour or within the same hour.
    public int calculateHourValue(Robot robot, Shift shift) {
        LocalDateTime startTime = shift.getStartTime();
        long duration = ChronoUnit.MINUTES.between(shift.getStartTime(), shift.getEndTime());
        boolean isWeekend = isWeekend(startTime);
        boolean isNight = isNight(startTime);
        if (isNight) {
            if (isWeekend) {
                return (int) duration * robot.getPrice("extraNight");
            } else {
                return (int) duration * robot.getPrice("standardNight");
            }
        } else {
            if (isWeekend) {
                return (int) duration * robot.getPrice("extraDay");
            } else {
                return (int) duration * robot.getPrice("standardDay");
            }
        }
    }

    // Returns true if a DateTime is during weekend
    public boolean isWeekend(LocalDateTime localDateTime) {
        DayOfWeek day = localDateTime.getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }


    // Returns true if a DateTime is at night
    public boolean isNight(LocalDateTime localDateTime) {
        int startHour = robot.getShift("standardNight").getStartTime().getHour();
        int endHour = robot.getShift("standardDay").getStartTime().getHour();
        return localDateTime.getHour() >= startHour || localDateTime.getHour() < endHour;
    }
}
