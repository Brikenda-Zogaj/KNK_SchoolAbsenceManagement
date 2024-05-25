package Classes;

public class Nxenesit {

    private int id;
    private String emri;
    private String emriPrindit;
    private String mbiemri;
    private String email;

    public Nxenesit(int id, String emri, String emriPrindit, String mbiemri, String email) {
        this.id = id;
        this.emri = emri;
        this.emriPrindit = emriPrindit;
        this.mbiemri = mbiemri;
        this.email = email;
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
}
