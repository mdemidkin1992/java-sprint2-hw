import java.util.ArrayList;
import java.util.HashMap;

public class ReportManager {
    MonthlyReport monthlyReport = new MonthlyReport();
    YearlyReport yearlyReport = new YearlyReport();
    Stats stats = new Stats();
    Checker checker = new Checker();

    HashMap<String, ArrayList<MonthRowData>> allMonthData = null;
    ArrayList<YearRowData> yearData = null;
    ArrayList<YearRowData> monthData = null;

    public void addMonthData () {
        allMonthData = monthlyReport.addMonths();
        monthData = monthlyReport.changeMonthFormat(allMonthData);
    }

    public void addYearData () {
        yearData = yearlyReport.addYear();
    }

    public void showMonthStats (HashMap<String, ArrayList<MonthRowData>> allMonthData) {
        stats.showMonthStats(allMonthData);
    }

    public void showYearStats (ArrayList<YearRowData> yearData) {
        stats.showYearStats(yearData);
    }

    public void compareMonthToYear (ArrayList<YearRowData> monthData, ArrayList<YearRowData> yearData) {
        checker.compareMonthToYear(monthData, yearData);
    }
}

class Stats {

    String[] months = {"Январь", "Февраль", "Март"};

    public void showMonthStats (HashMap<String, ArrayList<MonthRowData>> allMonthData) {
        if (allMonthData == null) {
            System.out.println("Отчеты не были загружены");
        } else {
            for (String month : months) {
                System.out.println("Статистика за " + month);
                int maxProfit = 0;
                String maxProfitCategory = "";
                int maxExpense = 0;
                String maxExpenseCategory = "";
                ArrayList<MonthRowData> monthData = allMonthData.get(month);
                for (MonthRowData line : monthData) {
                    if (!line.is_expense) {
                        if ((line.sum_of_one * line.quantity) > maxProfit) {
                            maxProfit = line.sum_of_one * line.quantity;
                            maxProfitCategory = line.int_name;
                        }
                    } else {
                        if ((line.sum_of_one * line.quantity) > maxExpense) {
                            maxExpense = (line.sum_of_one * line.quantity);
                            maxExpenseCategory = line.int_name;
                        }
                    }
                }
                System.out.println("Самый прибыльный товар " + maxProfitCategory + " на сумму: " + maxProfit);
                System.out.println("Самая большая трата " + maxExpenseCategory + " на сумму: " + maxExpense + "\n");
            }
        }
    }

    public void showYearStats (ArrayList<YearRowData> yearData) {
        if (yearData == null) {
            System.out.println("Отчеты не были загружены");
        } else {
            System.out.println("Рассматриваемый год: 2021");
            for (int i = 0; i < months.length; i++) {
                int expense = 0;
                int income = 0;
                for (YearRowData line : yearData) {
                    if ((line.month == (i + 1)) && (line.is_expense)) {
                        expense = line.amount;
                    } else if ((line.month == (i + 1)) && (!line.is_expense)) {
                        income = line.amount;
                    }
                }
                int profit = income - expense;
                System.out.println("В месяце " + months[i] + " прибыль: " + profit);
            }

            int sumExpense = 0;
            int sumIncome = 0;
            for (YearRowData line : yearData) {
                if (line.is_expense) {
                    sumExpense += line.amount;
                } else {
                    sumIncome += line.amount;
                }
            }

            int averageExpense = sumExpense / months.length;
            int averageIncome = sumIncome / months.length;

            System.out.println("Средний расход по месяцам: " + averageExpense);
            System.out.println("Средний доход по месяцам: " + averageIncome);
        }
    }
}

class Checker {

    public void compareMonthToYear (ArrayList<YearRowData> monthAdjustedReport, ArrayList<YearRowData> yearReport) {

        if ((monthAdjustedReport == null) || (yearReport == null)) {
            System.out.println("Отчеты не были загружены");
        } else {
            boolean error = false;
            System.out.println("Проверка баланса по месяцам.");
            for (int i = 0; i < yearReport.size(); i++) {
                for (int j = 0; j < monthAdjustedReport.size(); j++) {
                    if ((yearReport.get(i).month == monthAdjustedReport.get(j).month) &&
                            (yearReport.get(i).is_expense == monthAdjustedReport.get(j).is_expense)) {
                        if (!(yearReport.get(i).amount == monthAdjustedReport.get(j).amount)) {
                            error = true;
                            System.out.println("Баланс за месяц " + yearReport.get(i).month + " не сошелся");
                        }
                    }
                }
            }
            if (!error) {
                System.out.println("Баланс сошелся по всем месяцам.");
            }
        }
    }
}
