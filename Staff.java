public class Staff  {
    String specialization;
    int workingDays=5;
    double  workingHours;
    double salary;

    Staff(String Specialization ,String name){
        
        this.specialization=Specialization;
    }

    void CalculateSalary(){
        this.salary= workingHours*100;
    }

}
