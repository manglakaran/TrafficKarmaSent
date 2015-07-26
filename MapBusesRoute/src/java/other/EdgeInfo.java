package other;


public class EdgeInfo {
    int index;
    String placeId;
    String id;
    double lat;
    double lng;

    public EdgeInfo(int index, String placeId, String id, double lat, double lng) {
        this.index = index;
        this.placeId = placeId;
        this.id = id;
        this.lat = lat;
        this.lng = lng;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    

    
}
