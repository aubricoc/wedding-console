package cat.aubricoc.weddingconsole;

import android.support.annotation.NonNull;

import java.util.Date;

public class Confirmation implements Card, Comparable<Confirmation> {

    private String who;

    private String with;

    private String why;

    private Date date;

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public String getWith() {
        return with;
    }

    public void setWith(String with) {
        this.with = with;
    }

    public String getWhy() {
        return why;
    }

    public void setWhy(String why) {
        this.why = why;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int compareTo(@NonNull Confirmation o) {
        return o.date.compareTo(date);
    }

    @Override
    public String getTitle() {
        return who;
    }

    @Override
    public String getSubtitle() {
        return with;
    }

    @Override
    public String getText() {
        return why;
    }
}
