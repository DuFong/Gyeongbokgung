package gyeongbokgung.kbsccoding.com.gyeongbokgung;

import java.io.Serializable;

/* QuestDTO
 *  */
public class Quest implements Serializable {
    private int titleID;
    private int subID;
    private int rowID;
    private String title;
    private String subTitle;
    private String description;
    private String sumDescription;
    private String goal;
    private String hint;
    private String explanation;
    private int point;
    private int type;

    // TODO: drawable, color 변경
    static int drawable = R.drawable.image1;
    static String color =  "#4BAA50";

    @Override
    public String toString() {
        return "Quest{" +
                "rowID=" + rowID +
                ", title='" + title + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", description='" + description + '\'' +
                ", sumDescription='" + sumDescription + '\'' +
                ", goal='" + goal + '\'' +
                ", hint='" + hint + '\'' +
                ", type='" + type + '\'' +
                ", point=" + point +
                '}';
    }

    public Quest(int titleID, int subID, int rowID, String title, String subTitle, String description, String sumDescription, String goal, String hint, String explanation, int point, int type) {
        this.titleID = titleID;
        this.subID = subID;
        this.rowID = rowID;
        this.title = title;
        this.subTitle = subTitle;
        this.description = description;
        this.sumDescription = sumDescription;
        this.goal = goal;
        this.hint = hint;
        this.explanation = explanation;
        this.point = point;
        this.type = type;
    }

    public int getRowID() {
        return rowID;
    }

    public void setRowID(int rowID) {
        this.rowID = rowID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String Title) {
        this.title = Title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String Subtitle) {
        this.subTitle = Subtitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String Description) {
        this.description = Description;
    }

    public int getTitleID() {
        return titleID;
    }

    public void setTitleID(int titleID) {
        this.titleID = titleID;
    }

    public int getSubID() {
        return subID;
    }

    public void setSubID(int subID) {
        this.subID = subID;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getSumDescription() {
        return sumDescription;
    }

    public void setSumDescription(String Description_sum) {
        this.sumDescription = Description_sum;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String Goal) {
        this.goal = Goal;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String Hint) {
        this.hint = Hint;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int Point) {
        this.point = Point;
    }
    public int getType() {
        return type;
    }

    public void setType(int Type) {
        this.type = Type;
    }
    
}