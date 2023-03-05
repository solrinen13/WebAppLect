package pro.sky.budgetapp.services;

public interface BudgetService {
    int getDailyBudget();
    int getBalance();

    int getVacationBonus(int daysCount);

    int getSalaryWithVacation(int vacationDayCount, int vacationWorkingDaysCount, int workingDaysInMonth);
}
