public class Staff extends Person {
    String specialization;
    int workingDays=5;
    double  workingHours;
    double salary;

    Staff(String Specialization ,String name){
        super(name);
        this.specialization=Specialization;
    }

    void CalculateSalary(){
        this.salary= workingHours*100;
    }

}
