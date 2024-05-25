package model;

import javafx.scene.control.CheckBox;

public class mungesat {
    private int id;
    private String emri;
    private String emriPrindit;
    private String mbiemri;
    private CheckBox mungonCheckbox = new CheckBox();
    private CheckBox meArsyeCheckbox = new CheckBox();
    private CheckBox paArsyeCheckbox = new CheckBox();

    public mungesat(int id, String emri, String emriPrindit, String mbiemri) {
        this.id = id;
        this.emri = emri;
        this.emriPrindit = emriPrindit;
        this.mbiemri = mbiemri;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public String getEmri() {
        return emri;
    }

    public String getEmriPrindit() {
        return emriPrindit;
    }

    public String getMbiemri() {
        return mbiemri;
    }

    public CheckBox getMungonCheckbox() {
        return mungonCheckbox;
    }

    public CheckBox getMeArsyeCheckbox() {
        return meArsyeCheckbox;
    }

    public CheckBox getPaArsyeCheckbox() {
        return paArsyeCheckbox;
    }
}

