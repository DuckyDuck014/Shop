package dev.duck.Shop.Employees;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository
public class JdbcEmployeeRepository {

      private static final Logger log = LoggerFactory.getLogger(CrudRepository.class);
        private final JdbcClient jdbcClient;

            public JdbcEmployeeRepository(JdbcClient jdbcClient){this.jdbcClient = jdbcClient;}


    public List<Employee> findAll(){
        return jdbcClient.sql("select * from employee")
                        .query(Employee.class)
                        .list();
    }
    // Поиск всех сотрудников

    public Optional<EmployeeInfo> findByName(String firstName){

      return jdbcClient.sql("select firstName,surName,age, maxsales from employee where firstName = :firstName")
        .param("firstName", firstName)
        .query(EmployeeInfo.class)
        .optional();

         
    }
    //Поиск по имени, выводится только: Имя, Фамилия, Возраст, Максимальное количество продаж.

    public List<EmployeeInfo> maxSalesEmployee(){
        return jdbcClient.sql("select * from employee where maxsales = (select max(maxsales)from employee);")
        .query(EmployeeInfo.class)
        .list();
    }
    //Выбор продавца с максимальным количеством продаж.

    public void createEmployee(Employee employee){
      var updated = jdbcClient.sql("insert into employee(id,firstName,surName,age,employeePos,employeeGender,maxSales)values(?,?,?,?,?,?,?)")
      .params(List.of(employee.id(),employee.firstName(),employee.surName(),employee.age(),employee.employeePos(),employee.employeeGender(),employee.maxSales()))
      .update();

      Assert.state(updated == 1, "Faild to create employee" + employee.firstName());

    }
    //Создание сотрудника 



    public void deleteEmployee(Integer id){
      var updated = jdbcClient.sql("delete from employee where id = :id")
      .param("id",id)
      .update();

      Assert.state(updated == 1, "Faild to delete employee" + id);
    }
    // Удаление сотрудника


}
