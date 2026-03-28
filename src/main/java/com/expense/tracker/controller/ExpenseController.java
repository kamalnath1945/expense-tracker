package com.expense.tracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.expense.tracker.model.User;
import com.expense.tracker.model.UserExpense;
import com.expense.tracker.repository.ExpenseRepo;
import com.expense.tracker.repository.UserExpenseRepo;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class ExpenseController {

    @Autowired
    ExpenseRepo er;

    @Autowired
    BCryptPasswordEncoder encoder;

    @Autowired
    UserExpenseRepo userexprepo;

    @GetMapping("/login")
    public String get(Model m){
        m.addAttribute("user", new User());
        return "login";
    }

    @GetMapping("/dashboard")
    public String dash(HttpSession session, Model m){

    User user = (User) session.getAttribute("user");

    if (user == null) {
        return "redirect:/login";
    }

    m.addAttribute("user", user);
        return "dashboard";
    }

    @PostMapping("/login")
    public String getMethodName(@RequestParam String emails, @RequestParam String passwords, Model m, HttpSession session) {
        User user = er.findByEmail(emails);
        m.addAttribute("user", new User());
        if(encoder.matches(passwords,user.getPassword())){
            session.setAttribute("user", user);
            m.addAttribute("user", user);   
            return "dashboard";
        }
        m.addAttribute("error", "Invalid login");
        return "login";
    }

    @GetMapping("/register")
    public String getMethodNames(Model m) {
        
        m.addAttribute("user", new User());
        return "register";
    }

    @GetMapping("/getsalary")
    @ResponseBody
    public Integer getMethodNamess(HttpSession session) {
        User user = (User) session.getAttribute("user");
        return user.getFixedmonthsalary();
    }

    @GetMapping("/expenses")
    @ResponseBody
    public List<UserExpense> getExpenses(){
        return userexprepo.findAll();
    }
            

    @PostMapping("/register")
    public String save(@Valid @ModelAttribute User user, BindingResult br, Model m){
        if(br.hasErrors()){
            return "register";
        }
        User existingUser = er.findByEmail(user.getEmail());
        if (existingUser != null) {
            m.addAttribute("emailError", "Email already exists");
            return "register";
        }
        String pass = user.getPassword();
        user.setPassword(encoder.encode(pass));
        er.save(user);
        return "redirect:/login";
    }

    @PostMapping("/userexpensesave")
    public String saveExpense(@ModelAttribute UserExpense expense, RedirectAttributes ra, HttpSession session){

        User user = (User) session.getAttribute("user");

        if(user == null){
            return "redirect:/login";
        }

        expense.setUserId(user.getId());   

        userexprepo.save(expense);

        if(expense.getId()!=0){
            ra.addFlashAttribute("successMessage", "Expense updated successfully");
        }
        else{
            ra.addFlashAttribute("successMessage", "Expense added successfully");

        }
            
            ra.addFlashAttribute("openDashboard", true);
        
        return "redirect:/dashboard";
    }

    @GetMapping("/api/expenses")
    @ResponseBody
    public List<UserExpense> getExpenses(HttpSession session){

        User user = (User) session.getAttribute("user");
        if(user == null){
            return List.of(); // return empty list instead of error
        }

        return userexprepo.findByUserId(user.getId());
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); 
        return "redirect:/login";
    }
    
    @DeleteMapping("/deleteExpense/{id}")
    @ResponseBody
    public void deleteExpense(@PathVariable int id){
        userexprepo.deleteById(id);
    }
    
    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }
}