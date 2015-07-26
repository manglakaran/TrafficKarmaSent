
package TrafficMap.beans;

/**
 *
 * @author traffickarma@iiitd
 */
public class TrafficMapBean {
    double actuallat;
    double actuallng;
    double transitlat;
    double transitlng;
    String state;

    public TrafficMapBean(double actuallat, double actuallng, double transitlat, double transitlng, String state) {
        this.actuallat = actuallat;
        this.actuallng = actuallng;
        this.transitlat = transitlat;
        this.transitlng = transitlng;
        this.state = state;
    }

    public double getActuallat() {
        return actuallat;
    }

    public void setActuallat(double actuallat) {
        this.actuallat = actuallat;
    }

    public double getActuallng() {
        return actuallng;
    }

    public void setActuallng(double actuallng) {
        this.actuallng = actuallng;
    }

    public double getTransitlat() {
        return transitlat;
    }

    public void setTransitlat(double transitlat) {
        this.transitlat = transitlat;
    }

    public double getTransitlng() {
        return transitlng;
    }

    public void setTransitlng(double transitlng) {
        this.transitlng = transitlng;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    
}
