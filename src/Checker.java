import java.util.ArrayList;

public class Checker {

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
