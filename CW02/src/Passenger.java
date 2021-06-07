public class Passenger{
    private String fName;
    private  String sName;
    private String name;

    public int getSecondsINQueue() {
        return SecondsINQueue;
    }

    public void setSecondsINQueue(int secondsINQueue) {
        SecondsINQueue = secondsINQueue;
    }

    private String seat;
    private int SecondsINQueue;
    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }
}
