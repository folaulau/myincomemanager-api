package com.incomemanager.api.dataloader;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import com.incomemanager.api.entity.account.BudgetPeriod;
import com.incomemanager.api.entity.goal.Goal;
import com.incomemanager.api.entity.goal.GoalRepository;
import com.incomemanager.api.entity.income.Income;
import com.incomemanager.api.entity.income.IncomeRepository;
import com.incomemanager.api.entity.income.PayPeriod;
import com.incomemanager.api.entity.income.PayType;
import com.incomemanager.api.entity.income.expense.Expense;
import com.incomemanager.api.entity.income.expense.ExpenseRepository;
import com.incomemanager.api.entity.income.expense.ExpenseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.incomemanager.api.entity.account.Account;
import com.incomemanager.api.entity.account.AccountRepository;
import com.incomemanager.api.entity.user.User;
import com.incomemanager.api.entity.user.UserRepository;
import com.incomemanager.api.entity.user.UserType;
import com.incomemanager.api.utils.RandomGeneratorUtils;

import lombok.extern.slf4j.Slf4j;

// @DependsOn(value = {"groomerDataLoader", "parentDataLoader"})
@Slf4j
@Profile(value = {"local", "dev"})
@Component
public class AccountDataLoader implements ApplicationRunner {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository    userRepository;

    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private ExpenseRepository expenseRepository;


    @Autowired
    private GoalRepository goalRepository;


    @Override
    public void run(ApplicationArguments args) throws Exception {

        Account account = Account.builder()
                .id(1L)
                .budgetPeriod(BudgetPeriod.WEEKLY)
                .budgetDate(LocalDate.now().plusDays(2))
                .budgetTime(LocalTime.of(19,30))
                .build();

        accountRepository.saveAndFlush(account);

        String firstName = "Folau";
        String lastName = "Kaveinga";
        String email = (firstName+lastName).toLowerCase()+"@gmail.com";
        User user = User.builder()
                .id(1L)
                .firstName(firstName)
                .lastName(lastName)
                .type(UserType.user)
                .email(email)
                .phoneNumber(RandomGeneratorUtils.getRandomPhone())
                .account(account)
                .build();

        userRepository.saveAndFlush(user);

        Income income = Income.builder()
                .id(1L)
                .payPeriod(PayPeriod.SEMI_MONTHLY)
                .payType(PayType.SALARY)
                .yearlyTotal(BigDecimal.valueOf(185000))
                .payPeriodNetAmount(BigDecimal.valueOf(5700))
                .companyName("Anvilogic")
                .position("Senior Software Engineer")
                .startDate(LocalDate.of(2022,03,15))
                .user(user)
                .build();

        incomeRepository.saveAndFlush(income);

        income = Income.builder()
                .id(2L)
                .payPeriod(PayPeriod.BI_WEEKLY)
                .payType(PayType.SALARY)
                .yearlyTotal(BigDecimal.valueOf(117000))
                .payPeriodNetAmount(BigDecimal.valueOf(3700))
                .companyName("Datappraise")
                .position("Senior Software Engineer")
                .startDate(LocalDate.of(2020,02,15))
                .user(user)
                .build();

        incomeRepository.saveAndFlush(income);

        Goal goal = Goal.builder()
                .id(1L)
                .currentAmount(BigDecimal.valueOf(3000))
                .targetAmount(BigDecimal.valueOf(100000))
                .title("Buy New House")
                .deadline(LocalDate.now().plusYears(2))
                .description("Buy a bigger home")
                .account(account)
                .build();

        goalRepository.saveAndFlush(goal);

        Expense expense = Expense.builder()
                .id(1L)
                .monthlyDueDay(1)
                .account(account)
                .name("Mortgage")
                .amount(BigDecimal.valueOf(1500))
                .type(ExpenseType.HOUSING)
                .build();

        expenseRepository.saveAndFlush(expense);

        expense = Expense.builder()
                .id(2L)
                .monthlyDueDay(1)
                .account(account)
                .name("Gas")
                .amount(BigDecimal.valueOf(60))
                .type(ExpenseType.UTILITY)
                .build();

        expenseRepository.saveAndFlush(expense);

        expense = Expense.builder()
                .id(3L)
                .monthlyDueDay(1)
                .account(account)
                .name("Netflix")
                .amount(BigDecimal.valueOf(14.50))
                .type(ExpenseType.ENTERTAINMENT)
                .build();

        expenseRepository.saveAndFlush(expense);

        expense = Expense.builder()
                .id(4L)
                .account(account)
                .name("Groceries")
                .amount(BigDecimal.valueOf(1000))
                .type(ExpenseType.GROCERIES)
                .build();

        expenseRepository.saveAndFlush(expense);
    }

}
