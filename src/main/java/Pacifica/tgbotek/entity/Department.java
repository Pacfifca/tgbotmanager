package Pacifica.tgbotek.entity;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;


@Entity
@Table(name = "departments")
public class Department
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "manager_id")
    private Integer managerId;

    @Column(name = "budget")
    private Integer budget;


    public Department(){

    }

    public Department(Integer id, String name, Integer managerId, Integer budget){
        this.id = id;
        this.name = name;
        this.managerId = managerId;
        this.budget = budget;

    }

    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public Integer getManagerId(){
        return managerId;
    }
    public void setManagerId(){
        this.managerId = managerId;
    }

    public Integer getBudget(){
        return budget;
    }
    public void setBudget(){
        this.budget = budget;
    }



}
