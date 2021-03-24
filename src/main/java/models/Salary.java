package models;

public class Salary{

    private String grade;
    private int workOfDays;
    private int workOfHours;

    public Salary() {
    }

    public Salary(String grade, int workOfDays, int workOfHours) {
        this.grade = grade;
        this.workOfDays = workOfDays;
        this.workOfHours = workOfHours;
    }


    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public int getWorkOfDays() {
        return workOfDays;
    }

    public void setWorkOfDays(int workOfDays) {
        this.workOfDays = workOfDays;
    }

    public int getWorkOfHours() {
        return workOfHours;
    }

    public void setWorkOfHours(int workOfHours) {
        this.workOfHours = workOfHours;
    }

    @Override
    public String toString() {
        return "Salary{" +
                "grade='" + grade + '\'' +
                ", workOfDays=" + workOfDays +
                ", workOfHours=" + workOfHours +
                '}';
    }
}