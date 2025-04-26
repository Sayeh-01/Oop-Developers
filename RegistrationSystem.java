import java.util.ArrayList;
import java.util.List;

public class RegistrationSystem {
    private University university;
    private List<RegistrationRule> rules;

    public RegistrationSystem(University university) {
        this.university = university;
        this.rules = new ArrayList<>();
        initializeDefaultRules();
    }

    private void initializeDefaultRules() {
        // Add default registration rules
        rules.add(new PrerequisiteRule());
        rules.add(new CapacityRule());
        rules.add(new TimeConflictRule());
        rules.add(new AcademicStandingRule());
    }

    public boolean registerForCourse(Student student, CourseGrade course) {
        // Check all registration rules
        for (RegistrationRule rule : rules) {
            if (!rule.validate(student, course, university)) {
                System.out.println("Registration failed: " + rule.getErrorMessage());
                return false;
            }
        }

        // If all rules pass, proceed with registration
        university.enrollStudentInCourse(student, course);
        return true;
    }

    public void addRegistrationRule(RegistrationRule rule) {
        rules.add(rule);
    }

    // Interface for registration rules
    public interface RegistrationRule {
        boolean validate(Student student, CourseGrade course, University university);
        String getErrorMessage();
    }

    // Prerequisite rule implementation
    private class PrerequisiteRule implements RegistrationRule {
        @Override
        public boolean validate(Student student, CourseGrade course, University university) {
            List<String> prerequisites = course.getPreRequisites();
            if (prerequisites.isEmpty()) {
                return true;
            }

            List<CourseGrade> completedCourses = university.getStudentCourses(student);
            for (String prerequisite : prerequisites) {
                boolean found = false;
                for (CourseGrade completed : completedCourses) {
                    if (completed.getCourseId().equals(prerequisite)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public String getErrorMessage() {
            return "Prerequisites not satisfied";
        }
    }

    // Capacity rule implementation
    private class CapacityRule implements RegistrationRule {
        @Override
        public boolean validate(Student student, CourseGrade course, University university) {
            return course.getAvailableSeats() > 0;
        }

        @Override
        public String getErrorMessage() {
            return "Course is full";
        }
    }

    // Time conflict rule implementation
    private class TimeConflictRule implements RegistrationRule {
        @Override
        public boolean validate(Student student, CourseGrade course, University university) {
            List<CourseGrade> currentCourses = university.getStudentCourses(student);
            for (CourseGrade currentCourse : currentCourses) {
                if (hasTimeConflict(currentCourse, course)) {
                    return false;
                }
            }
            return true;
        }

        private boolean hasTimeConflict(CourseGrade course1, CourseGrade course2) {
            return course1.getDays().equals(course2.getDays()) &&
                   doTimeRangesOverlap(course1.getTimes(), course2.getTimes());
        }

        private boolean doTimeRangesOverlap(String time1, String time2) {
            String[] range1 = time1.split("-");
            String[] range2 = time2.split("-");
            
            int start1 = convertTimeToMinutes(range1[0]);
            int end1 = convertTimeToMinutes(range1[1]);
            int start2 = convertTimeToMinutes(range2[0]);
            int end2 = convertTimeToMinutes(range2[1]);
            
            return (start1 < end2) && (start2 < end1);
        }

        private int convertTimeToMinutes(String time) {
            String[] parts = time.split(":");
            int hours = Integer.parseInt(parts[0]);
            int minutes = Integer.parseInt(parts[1]);
            return hours * 60 + minutes;
        }

        @Override
        public String getErrorMessage() {
            return "Time conflict with existing course";
        }
    }

    // Academic standing rule implementation
    private class AcademicStandingRule implements RegistrationRule {
        @Override
        public boolean validate(Student student, CourseGrade course, University university) {
            // Check if student has any failing grades
            List<CourseEnrollment> enrollments = university.getStudentEnrollments(student);
            for (CourseEnrollment enrollment : enrollments) {
                if (enrollment.getGrade() < 60.0) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public String getErrorMessage() {
            return "Academic standing does not meet requirements";
        }
    }
} 