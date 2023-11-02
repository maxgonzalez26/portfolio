public class InsertionSort {
    public static void main(String[] args) {

        int x = Integer.parseInt(args[0]);
        if (args.length != 1 || x < 0) {
            System.err.println("Incorrect amount of arguments / Negative Value");
            System.exit(1);
        }

        Element[] elarray = new Element[x];
        int randomNum;
        int minNum = x;
        int minlocation = 0;

        for (int i = 0; i < x; i++) {
            randomNum = 1 + (int) (Math.random() * x);
            elarray[i] = new Element(randomNum);
            if (randomNum < minNum) {
                minNum = randomNum;
                minlocation = i;
            }
        }

        for (int i = 0; i < x; i++) {
            elarray[i] = new Element(elarray[i].getValue(), 440 * Math.pow(2, (elarray[i].getValue() - minNum) / 12.0));
        }

        StdDraw.setXscale(0, 100);
        StdDraw.setYscale(0, elarray.length);

       for(int p = 0; p < x; p++){
            elarray[p].check(p);
        }
        System.out.println(minNum);
        System.out.println(minlocation +"\n");

        for (int j = 1; j < elarray.length; j++) {
            for (int z = j; z > 0; z--) {
                if (elarray[z - 1].compareto(elarray[z]) == 1) {
                    Element tmp = elarray[z];
                    elarray[z] = elarray[z - 1];
                    elarray[z - 1] = tmp;

                    for(int p = 0; p< elarray.length; p++){
                        for(int q =0; q< elarray[j].getSound().length; q++){
                            StdAudio.play(elarray[j].getSound()[q]);
                        }
                    }


                    StdDraw.clear();

                    Double width = Double.valueOf(100 / elarray.length);
                    Double halfwidth = (width / 2);
                    for (int i = 0; i < elarray.length; i++) {
                        double location = (2 * i * halfwidth) + halfwidth;
                        if (z - 1 == i) {
                            StdDraw.setPenColor(StdDraw.BOOK_RED);
                            elarray[i].draw(location, halfwidth);
                        } else {
                            StdDraw.setPenColor(StdDraw.BLACK);
                            elarray[i].draw(location, halfwidth);
                        }
                        StdAudio.play(elarray[i].getSound());

                    }
                }
            }
        }
    }
}
