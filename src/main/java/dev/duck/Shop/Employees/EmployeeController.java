package dev.duck.Shop.Employees;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;



@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    private final JdbcEmployeeRepository jdbcEmployeeRepository;

    public EmployeeController(JdbcEmployeeRepository jdbcEmployeeRepository) {this.jdbcEmployeeRepository = jdbcEmployeeRepository;}

    @GetMapping()
    List<Employee> findAll(){
        return jdbcEmployeeRepository.findAll();
    }

    @GetMapping("/{firstName}")
    EmployeeInfo findByName(@PathVariable String firstName){
        Optional<EmployeeInfo> employee = jdbcEmployeeRepository.findByName(firstName);
        if(employee.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Employee not found");
        }
        return employee.get();
    }

    @GetMapping("/sales")
    List<EmployeeInfo> maxSalesEmployee(){
            return jdbcEmployeeRepository.maxSalesEmployee();
    }
    

    @PostMapping("/{id}")
    void createEmployee(@RequestBody Employee employee){
        jdbcEmployeeRepository.createEmployee(employee);

    }

    @DeleteMapping("/{id}")
    void deleteEmployee(@PathVariable Integer id){
        jdbcEmployeeRepository.deleteEmployee(id);
    }



}
    
    
    

