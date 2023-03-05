package pro.sky.budgetapp.services.impl;

import org.apache.coyote.http11.upgrade.UpgradeServletInputStream;
import org.springframework.stereotype.Service;
import pro.sky.budgetapp.model.Transaction;
import pro.sky.budgetapp.services.BudgetService;

import java.time.LocalDate;
import java.time.Month;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

@Service
public class BudgetServiceImpl implements BudgetService {

    public static final int SALARY = 20_000;
    public static final int SAVING = 3_000;
    public static final int DAILY_BUDGET = (SALARY - SAVING)/LocalDate.now().lengthOfMonth();

    public static int balance = 0;
    //public static final int AVG_SALARY = (10_000 + 10_000 + 10_000 + 10_000 + 10_000 + 15_000 + 15_000 + 15_000 + 15_000 + 15_000 + 15_000 + 20_000)/12 ;
    public static final int AVG_SALARY = 20_000;
    public static final  double AVG_DAYS = 29.3;

    private static Map<Month, Map<Long, Transaction>> transactions = new TreeMap<>();
    private static long lastId = 0;

    @Override
    public int getDailyBudget() {
        return SALARY / LocalDate.now().getMonthValue();
    }

    @Override
    public int getBalance() {
        return SALARY - SAVING - getAllSpend();
    }
    public void addTransaction(Transaction transaction){
        Map<Long, Transaction> monthTransactions = transactions.getOrDefault(LocalDate.now().getMonth(), new LinkedHashMap<>());
        monthTransactions.put(lastId++,transaction);
    }
    public int getDailyBalance(){
        return DAILY_BUDGET * LocalDate.now().getDayOfMonth() - getAllSpend();
    }
    public int getAllSpend(){
        Map<Long, Transaction> monthTransactions = transactions.getOrDefault(LocalDate.now().getMonth(), new LinkedHashMap<>());
        int sum  = 0;
        for (Transaction transaction : monthTransactions.values()) {
            sum += transaction.getSum();
        }
        return sum;
    }
    @Override
    public int getVacationBonus(int daysCount){
       double avgDaySalary = AVG_SALARY /AVG_DAYS;
       return (int)(daysCount * avgDaySalary);
    }
    @Override
    public int getSalaryWithVacation(int vacationDayCount, int vacationWorkingDaysCount, int workingDaysInMonth){
        getVacationBonus(vacationWorkingDaysCount);
        int salary = SALARY / workingDaysInMonth * (workingDaysInMonth - vacationWorkingDaysCount);
        return salary + getVacationBonus(vacationDayCount);
    }
}
