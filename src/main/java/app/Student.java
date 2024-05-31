package app;

public class Student {
    private int ID;
    private String Emri;
    private String Mbiemri;
    private String Klasa;
    private String Paralelja;
    private int Mungesat;
    private int Tearsyeshme;

    private int Tepaarsyeshme;

    public Student(int ID, String Emri, String Mbiemri, String Klasa, String Paralelja, int Mungesat, int Tearsyeshme, int Tepaarsyeshme) {
        this.Emri = Emri;
        this.Mbiemri = Mbiemri;
        this.Klasa = Klasa;
        this.Paralelja= Paralelja;
        this.Mungesat = Mungesat;
        this.Tearsyeshme= Tearsyeshme;
        this.Tepaarsyeshme=Tepaarsyeshme;
        this.ID=ID;
    }

    // Getters and setters
    // ...

    public int getID(){return ID;}
    public void setID(int ID){this.ID=ID;}

    public String getEmri() {
        return Emri;
    }

    public void setEmri(String Emri) {
        this.Emri= Emri;
    }

    public String getMbiemri() {
        return Mbiemri;
    }

    public void setMbiemri(String Mbiemri) {
        this.Mbiemri= Mbiemri;
    }

    public String getKlasa() {
        return Klasa;
    }

    public void setKlasa(String Klasa) {
        this.Klasa = Klasa;
    }

    public String getParalelja() {
        return Paralelja;
    }

    public void setParalelja(String Paralelja) {
        this.Paralelja = Paralelja;
    }

    public int getMungesat() {
        return Mungesat;
    }

    public void setMungesat(int Mungesat) {
        this.Mungesat = Mungesat;
    }

    public int getTearsyeshme() {
        return Tearsyeshme;
    }

    public void setTearsyeshme(int Tearsyeshme) {
        this.Tearsyeshme = Tearsyeshme;
    }
    public int Tepaarsyeshme() {
        return Tepaarsyeshme;
    }

    public void setTepaarsyeshme(int Tepaarsyeshme) {
        this.Tepaarsyeshme = Tepaarsyeshme;
    }
    public int calculateNotaesjelljes() {
        if (Tepaarsyeshme < 15) {
            return 5;
        } else if (Tepaarsyeshme < 30) {
            return 3;
        } else {
            return 1;
        }
    }

}
