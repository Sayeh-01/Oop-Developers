import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AcademicCalendar {
    private String semester;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<LocalDate> holidays;
    private List<LocalDate> examDates;
    private List<LocalDate> registrationDates;

    public AcademicCalendar() {
        this.holidays = new ArrayList<>();
        this.examDates = new ArrayList<>();
        this.registrationDates = new ArrayList<>();
    }

    public AcademicCalendar(String semester, LocalDate startDate, LocalDate endDate) {
        this();
        this.semester = semester;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void addHoliday(LocalDate date) {
        if (!holidays.contains(date)) {
            holidays.add(date);
        }
    }

    public void addExamDate(LocalDate date) {
        if (!examDates.contains(date)) {
            examDates.add(date);
        }
    }

    public void addRegistrationDate(LocalDate date) {
        if (!registrationDates.contains(date)) {
            registrationDates.add(date);
        }
    }

    public boolean isHoliday(LocalDate date) {
        return holidays.contains(date);
    }

    public boolean isExamDate(LocalDate date) {
        return examDates.contains(date);
    }

    public boolean isRegistrationDate(LocalDate date) {
        return registrationDates.contains(date);
    }

    public boolean isWithinSemester(LocalDate date) {
        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }

    // Getters and Setters
    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<LocalDate> getHolidays() {
        return new ArrayList<>(holidays);
    }

    public List<LocalDate> getExamDates() {
        return new ArrayList<>(examDates);
    }

    public List<LocalDate> getRegistrationDates() {
        return new ArrayList<>(registrationDates);
    }
} 