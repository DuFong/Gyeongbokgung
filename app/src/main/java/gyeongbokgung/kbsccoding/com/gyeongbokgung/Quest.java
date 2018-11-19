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
    private String goal2;
    private String goal3;
    private String hint;
    private String explanation;
    private int point;
    private int type;
    private double latitude;
    private double longitude;


    // TODO: drawable, color 변경
    static int drawable = R.drawable.image1;
    static String color =  "#4BAA50";

    @Override
    public String toString() {
        return "Quest{" +
                "titleID=" + titleID +'\'' +
                "subID=" + subID +'\'' +
                "rowID=" + rowID +'\'' +
                ", title='" + title + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", description='" + description + '\'' +
                ", sumDescription='" + sumDescription + '\'' +
                ", goal='" + goal + '\'' +
                ", goal2='" + goal2 + '\'' +
                ", goal3='" + goal3 + '\'' +
                ", hint='" + hint + '\'' +
                ", explanation='" + explanation + '\'' +
                ", type='" + type + '\'' +
                ", point=" + point +'\'' +
                ", latitude=" + latitude +'\'' +
                ", longitude=" + longitude +
                '}';
    }

    public Quest(int titleID, int subID, int rowID, String title, String subTitle, String description, String sumDescription, String goal, String goal2, String goal3, String hint, String explanation, int point, int type,double latitude,double longitude) {
        this.titleID = titleID;
        this.subID = subID;
        this.rowID = rowID;
        this.title = title;
        this.subTitle = subTitle;
        this.description = description;
        this.sumDescription = sumDescription;
        this.goal = goal;
        this.goal2 = goal2;
        this.goal3 = goal3;
        this.hint = hint;
        this.explanation = explanation;
        this.point = point;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
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


    public String getGoal2() {
        return goal2;
    }

    public void setGoal2(String Goal2) {
        this.goal2 = Goal2;
    }

    public String getGoal3() {
        return goal3;
    }

    public void setGoal3(String Goal3) {
        this.goal3 = Goal3;
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

    public double getLatitude() { return latitude; }

    public void setLatitude(double Latitude) { this.latitude = Latitude; }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double Longitude) {
        this.latitude = Longitude;
    }

}