package gyeongbokgung.kbsccoding.com.gyeongbokgung;

import java.io.Serializable;

/* QuestDTO
 *  */
public class Quest implements Serializable {
    private int idx;
    private String Title;
    private String Subtitle;
    private String Description;
    private String Description_sum;
    private String Goal;
    private String Hint;
    private int Point;
    private int Type;
    // TODO: drawable, color 변경
    static int drawable = R.drawable.ic_format_align_justify_black_24dp;
    static String color =  "#4BAA50";

    @Override
    public String toString() {
        return "Quest{" +
                "idx=" + idx +
                ", Title='" + Title + '\'' +
                ", Subtitle='" + Subtitle + '\'' +
                ", Description='" + Description + '\'' +
                ", Description_sum='" + Description_sum + '\'' +
                ", Goal='" + Goal + '\'' +
                ", Hint='" + Hint + '\'' +
                ", Type='" + Type + '\'' +
                ", Point=" + Point +
                '}';
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getSubtitle() {
        return Subtitle;
    }

    public void setSubtitle(String Subtitle) {
        this.Subtitle = Subtitle;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getDescription_sum() {
        return Description_sum;
    }

    public void setDescription_sum(String Description_sum) {
        this.Description_sum = Description_sum;
    }

    public String getGoal() {
        return Goal;
    }

    public void setGoal(String Goal) {
        this.Goal = Goal;
    }

    public String getHint() {
        return Hint;
    }

    public void setHint(String Hint) {
        this.Hint = Hint;
    }

    public int getPoint() {
        return Point;
    }

    public void setPoint(int Point) {
        this.Point = Point;
    }
    public int getType() {
        return Type;
    }

    public void setType(int Type) {
        this.Type = Type;
    }

    public Quest(int idx, String Title, String Subtitle, String Description, String Description_sum, String Goal, String Hint, int Point,int Type) {
        this.idx = idx;
        this.Title = Title;
        this.Subtitle = Subtitle;
        this.Description = Description;
        this.Description_sum = Description_sum;
        this.Goal = Goal;
        this.Hint = Hint;
        this.Point = Point;
        this.Type = Type;
    }
}
