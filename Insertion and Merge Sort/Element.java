public class Element {
    private int value;
    private double[] sound;

    public Element(int value){
        this.value= value;
    }

    public Element(int value, double hz){
        int hzint= (int) hz;
        setSound(hzint);
        this.value = value;
    }

    public int getValue(){
        return value;
    }

    public void check(int p){
        System.out.println(this.value + " " + this.sound[p]);
    }


    public double[] getSound(){
        return this.sound;
    }


    public int compareto(Element other){
        if (value < other.value) {
            return -1;
        } else if (value > other.value) {
            return 1;
        } else {
            if (value < other.value) {
                return -1;
            } else if (value > other.value) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    public void setSound(int hz){
        this.sound = new double[4410];
        for(int i=0; i<4410; i++){
            sound[i]=Math.sin(2 * Math.PI * i * hz / 4410);
        }

    }

    public void draw(double location, double halfwidth){
        StdDraw.filledRectangle(location, 0, halfwidth, this.value/2.0);
    }

}