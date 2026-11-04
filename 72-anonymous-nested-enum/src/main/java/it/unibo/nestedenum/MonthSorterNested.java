package it.unibo.nestedenum;

//import java.io.EOFException;
import java.util.Comparator;
//import java.util.Locale;
//import java.util.Objects;

/**
 * Implementation of {@link MonthSorter}.
 */
public final class MonthSorterNested implements MonthSorter {

    public enum Month{
        JANUARY(31), FEBRUARY(28), MARCH(31), APRIL(30), MAY(31), JUNE(30), JULY(31), 
        AUGUST(31), SEPTEMBER(30), OCTOBER(31), NOVEMBER(30), DECEMBER(31);

        private final int days;
        private Month(int days){
            this.days = days;
        }

        public int getDays(){
            return this.days;
        }

        public static Month fromString(String originalName){
            int numFound=0;
            String name = originalName.toUpperCase();
            Month result = null;

            for (Month month: Month.values()){
                String monthName = month.toString();
                if (name.equals(monthName)){
                    numFound++;
                    result = month;
                }else if (monthName.startsWith(name)){
                    numFound++;
                    result = month;
                }
            }
            if (numFound == 1){
                return result;
            }else{
                //return null;
                throw new IllegalArgumentException("month not found or non valid name");
            }
        }
    }

    @Override
    public Comparator<String> sortByDays() {
        class SortByDate implements Comparator<String>{
            public int compare(String firstMonth, String secondMonth){
                MonthSorterNested.Month first = MonthSorterNested.Month.fromString(firstMonth);
                MonthSorterNested.Month second = MonthSorterNested.Month.fromString(secondMonth);

                if (first.getDays() > second.getDays()){
                    return 1;
                }else if(first.getDays() == second.getDays()){
                    return 0;
                }else{
                    return -1;
                }
            }
        }
        return new SortByDate();
    }

    @Override
    public Comparator<String> sortByOrder() {

        class SortByMonthOrder implements Comparator<String>{
            public int compare(String firstMonth, String secondMonth){
                MonthSorterNested.Month first = MonthSorterNested.Month.fromString(firstMonth);
                MonthSorterNested.Month second = MonthSorterNested.Month.fromString(secondMonth);
                
                if (first.ordinal() > second.ordinal()){
                    return 1;
                }else if(first.ordinal() == second.ordinal()){
                    return 0;
                }else{
                    return -1;
                }

            }
        }
        return new SortByMonthOrder();
    }
}
