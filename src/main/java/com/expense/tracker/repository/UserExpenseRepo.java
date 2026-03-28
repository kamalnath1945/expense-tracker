package com.expense.tracker.repository;

import com.expense.tracker.model.UserExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserExpenseRepo extends JpaRepository<UserExpense, Integer>{
    List<UserExpense> findByUserId(Integer userId);
}
