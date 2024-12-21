/*package ma.ac.uir.synthesedemo.controller;

import ma.ac.uir.exemple3v25.entity.Employee;
import ma.ac.uir.exemple3v25.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
 //   @Autowired
    private EmployeeService empServ;
    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.empServ = employeeService;
    }

    @GetMapping("/list")
    public String listEmployees(Model leModel){
        List<Employee> employees = empServ.findAll();
        leModel.addAttribute("employees", employees );
        return "list-employees";
    }

    @GetMapping("/show")
    public String show() {
        return "register";
    }

    @GetMapping("/showFormAdd")
    public String showFormAdd(Model leModel){
        Employee theEmployee = new Employee();
        leModel.addAttribute("employee", theEmployee);
        return "employee-form";
    }

    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("employee") Employee theEmployee){
        empServ.save(theEmployee);
        return "redirect:/employees/list";
    }

    @GetMapping("/delete")
    public String deleteEmployee(@RequestParam("employeeId") int id,
                                 Model leModel){
        empServ.deleteById(id);
        return "redirect:/employees/list";
    }

    @GetMapping("/loadFormUpdate")
    public String loadFormUpdate(@RequestParam("employeeId") int id,
                                 Model leModel){
        Employee theEmployee = new Employee();
        theEmployee = empServ.findById(id);
        leModel.addAttribute("employee", theEmployee);
        return "employee-updateForm";
    }

    @PostMapping("/UpdateEmployee")
    public String updateEmployee(@ModelAttribute("employee") Employee theEmployee){
        empServ.update(theEmployee);
        return "redirect:/employees/list";
    }

}
*/