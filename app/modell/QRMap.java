package modell;

/**
 * Created by Sarah on 14.03.2017.
 */
public class QRMap {
    private int wuerzburg;
    private int madrid;
    private int london;
    private int idQRMap;

    /**
     * initialisiert gesammelte QR-Codes. 0 = noch nicht eingescannt, 1 = eingescannt
     */
    public QRMap(){
        wuerzburg = 0;
        madrid = 0;
        london = 0;

    }

    public int getWuerzburg() {
        return wuerzburg;
    }

    public void setWuerzburg(int wuerzburg) {
        this.wuerzburg = wuerzburg;
    }

    public int getMadrid() {
        return madrid;
    }

    public void setMadrid(int madrid) {
        this.madrid = madrid;
    }

    public int getLondon() {
        return london;
    }

    public void setLondon(int london) {
        this.london = london;
    }


}
