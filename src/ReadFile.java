public class ReadFile {
    String path;

    public String readMonthFile (int i) {
        path = "resources/m.20210" + (i) + ".csv";
        return path;
    }

    public String readYearFile () {
        return "resources/y.2021.csv";
    }
}
