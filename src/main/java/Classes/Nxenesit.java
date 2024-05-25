package Classes;

import javafx.scene.control.CheckBox;

public class Nxenesit {

    private int id;
    private String emri;
    private String emriPrindit;
    private String mbiemri;
    private String email;
    private CheckBox mungonCheckbox;
    private CheckBox meArsyeCheckbox;
    private CheckBox paArsyeCheckbox;
    private int mea;
    private int paa;
    private int gjithsej;

    public Nxenesit(int id, String emri, String emriPrindit, String mbiemri, String email, int mea, int paa, int gjithsej) {
        this.id = id;
        this.emri = emri;
        this.emriPrindit = emriPrindit;
        this.mbiemri = mbiemri;
        this.email = email;
        this.mea = mea;
        this.paa = paa;
        this.gjithsej = gjithsej;
        this.mungonCheckbox = new CheckBox();
        this.meArsyeCheckbox = new CheckBox();
        this.paArsyeCheckbox = new CheckBox();
    }

    public Nxenesit(int id, String emri, String emriPrindit, String mbiemri) {
        this.id = id;
        this.emri = emri;
        this.emriPrindit = emriPrindit;
        this.mbiemri = mbiemri;
        this.mungonCheckbox = new CheckBox();
        this.meArsyeCheckbox = new CheckBox();
        this.paArsyeCheckbox = new CheckBox();
    }

    public int getMea() {
        return mea;
    }

    public void setMea(int mea) {
        this.mea = mea;
    }

    public int getPaa() {
        return paa;
    }

    public void setPaa(int paa) {
        this.paa = paa;
    }

    public int getGjithsej() {
        return gjithsej;
    }

    public void setGjithsej(int gjithsej) {
        this.gjithsej = gjithsej;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmri() {
        return emri;
    }

    public void setEmri(String emri) {
        this.emri = emri;
    }

    public String getEmriPrindit() {
        return emriPrindit;
    }

    public void setEmriPrindit(String emriPrindit) {
        this.emriPrindit = emriPrindit;
    }

    public String getMbiemri() {
        return mbiemri;
    }

    public void setMbiemri(String mbiemri) {
        this.mbiemri = mbiemri;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
