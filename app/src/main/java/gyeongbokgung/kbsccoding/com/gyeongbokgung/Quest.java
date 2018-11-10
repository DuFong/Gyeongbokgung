package gyeongbokgung.kbsccoding.com.gyeongbokgung;

/* QuestDTO
 *  */
public class Quest {
    private int id;
    private String title;
    private String subTitle;
    private String description;
    private String briefDescription;
    private String goal;
    private String hint;
    private int point;
    // TODO: drawable, color 변경
    static int drawable = R.drawable.ic_format_align_justify_black_24dp;
    static String color =  "#4BAA50";

    @Override
    public String toString() {
        return "Quest{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", description='" + description + '\'' +
                ", briefDescription='" + briefDescription + '\'' +
                ", goal='" + goal + '\'' +
                ", hint='" + hint + '\'' +
                ", point=" + point +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBriefDescription() {
        return briefDescription;
    }

    public void setBriefDescription(String briefDescription) {
        this.briefDescription = briefDescription;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public Quest(int id, String title, String subTitle, String description, String briefDescription, String goal, String hint, int point) {
        this.id = id;
        this.title = title;
        this.subTitle = subTitle;
        this.description = description;
        this.briefDescription = briefDescription;
        this.goal = goal;
        this.hint = hint;
        this.point = point;
    }
}
