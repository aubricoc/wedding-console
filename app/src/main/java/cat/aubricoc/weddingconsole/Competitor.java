package cat.aubricoc.weddingconsole;

import android.support.annotation.NonNull;
import android.util.SparseIntArray;

import java.util.Date;
import java.util.Map;

public class Competitor implements Card, Comparable<Competitor> {

    private String language;

    private String name;

    private Map<Integer, Integer> answers;

    private Date date;

    private Integer points;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Integer, Integer> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<Integer, Integer> answers) {
        this.answers = answers;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    @Override
    public int compareTo(@NonNull Competitor o) {
        int result = o.points.compareTo(points);
        if (result == 0) {
            return o.date.compareTo(date);
        }
        return result;
    }

    @Override
    public String getTitle() {
        return name;
    }

    @Override
    public String getSubtitle() {
        return points + " Points";
    }

    @Override
    public String getText() {
        return null;
    }
}
