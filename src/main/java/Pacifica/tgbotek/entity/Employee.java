package Pacifica.tgbotek.entity;
import jakarta.persistence.*;



@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "passport_series")
    private String passportSeries;

    @Column(name = "passport_number")
    private String passportNumber;


    @Column(name ="salary")
    private Integer salary;

    @Column(name = "employee_position")
    private String employeePosition;

    public Employee() {
    }

    public Employee(Integer id, String firstName, String lastName, String passportSeries,
                    String passportNumber, Integer salary, String employeePosition) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.passportSeries = passportSeries;
        this.passportNumber = passportNumber;
        this.salary = salary;
        this.employeePosition = employeePosition;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassportSeries() {
        return passportSeries;
    }

    public void setPassportSeries(String passportSeries) {
        this.passportSeries = passportSeries;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public String getEmployeePosition() {
        return employeePosition;
    }

    public void setEmployeePosition(String employeePosition) {
        this.employeePosition = employeePosition;
    }
}
