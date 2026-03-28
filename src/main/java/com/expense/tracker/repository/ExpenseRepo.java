package com.expense.tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.expense.tracker.model.User;

public interface ExpenseRepo extends JpaRepository<User, Integer> {
    User findByEmail(String email);
    User findByFixedmonthsalary(Integer fixedmonthsalary);

}
