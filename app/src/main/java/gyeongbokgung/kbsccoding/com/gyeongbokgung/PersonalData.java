package gyeongbokgung.kbsccoding.com.gyeongbokgung;


import java.io.Serializable;

public class PersonalData implements Serializable {
    private String member_id;
    private String member_name;
    private String member_password;
    private int member_score;
    private int member_rank;
    private int member_idx;
    private int member_currentQuest;

    public int getMember_idx() {
        return member_idx;
    }

    public String getMember_id() {
        return member_id;
    }

    public String getMember_name() {
        return member_name;
    }

    public String getMember_password() {
        return member_password;
    }

    public int getMember_score() {
        return member_score;
    }

    public int getMember_rank() {
        return member_rank;
    }

    public int getMember_currentQuest() {
        return member_currentQuest;
    }


    public void setMember_idx(int member_idx) { this.member_idx = member_idx; }

    public void setMember_id(String member_id) { this.member_id = member_id; }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public void setMember_password(String member_address) {
        this.member_password = member_address;
    }

    public void setMember_score(int member_score) {
        this.member_score = member_score;
    }

    public void setMember_rank(int member_rank) {
        this.member_rank = member_rank;
    }

    public void setMember_currentQuest(int member_currentQuest) {
        this.member_currentQuest = member_currentQuest;
    }
}