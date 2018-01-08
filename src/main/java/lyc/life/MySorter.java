package lyc.life;

import java.util.List;
import java.util.Collections;
import java.util.Comparator;

public class MySorter implements Sorter {
    public void sort(List list){
        Collections.sort(list, new Comparator<Huluwa>() {
            @Override
            public int compare(Huluwa a, Huluwa b)
            {
                return  a.getName().compareTo(b.getName());
            }
        });
    }
}
