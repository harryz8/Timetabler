package zs1.timetabler;

import java.util.Comparator;

public class ClassSorter implements Comparator<Class> {
    @Override
    public int compare(Class o1, Class o2) {
        return Long.compare((o1.getDay()*86400000)+(o1.getStartTime().getTime()), (o2.getDay()*86400000)+(o2.getStartTime().getTime()));
    }
}
