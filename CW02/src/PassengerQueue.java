public class PassengerQueue {
    private Passenger[] queue = new Passenger[21];
    private int length=0;
    private int first = 0;
    private int last = 0;
    private int maxStay = 0;
    private int minStay = 0;

    public int getMaxStay() {
        return maxStay;
    }

    public void setMaxStay(int maxStay) {
        this.maxStay = maxStay;
    }

    public int getMinStay() {
        return minStay;
    }

    public void setMinStay(int minStay) {
        this.minStay = minStay;
    }


     public  boolean isFull(){
         return last == 21;
     }

     public boolean isEmpty(){
         return last == first;
     }

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public int getLast() {
        return last;
    }

    public void setLast(int last) {
        this.last = last;
    }

    public Passenger[] getQueue() {
        return queue;
    }

    public void delete (int i){
         for (int j=i;j<getLength(); j++){
             queue[j] = queue[j+1];
         }last--;
    }

    public void add(Passenger passenger) {
        queue[last] = passenger;
        last ++;
        length++;
    }

    public Passenger remove(){
        Passenger data = queue[first];
        first++;
        return data;
    }
    public void clear(){
        first = 0;
        last = 0;

    }

    public int getLength() {
        return length;
    }





}
