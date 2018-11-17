package training.ten.c;

import java.util.List;
import java.util.Objects;

public class Situation {

    final List<Integer> strips;
    int isWin;


    public Situation(List<Integer> strips) {
        this.strips = strips;
    }

    public boolean isEmpty() {
        return strips.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Situation situation = (Situation) o;
        return Objects.equals(strips, situation.strips);
    }

    @Override
    public int hashCode() {
        return Objects.hash(strips);
    }
}
