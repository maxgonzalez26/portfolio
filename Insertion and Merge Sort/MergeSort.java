public class MergeSort {
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
        for (int i = 0; i < x; i++){
            randomNum = 1 + (int)(Math.random() * x);
            elarray[i] = new Element(randomNum);
            if(randomNum<minNum){
                minNum=randomNum;
                minlocation=i;
            }
        }

        for (int i = 0; i < x; i++){
            elarray[i] = new Element(elarray[i].getValue(),  440 * Math.pow(2, (elarray[i].getValue() - minNum) / 12.0));
        }

        StdDraw.setXscale(0,100);
        StdDraw.setYscale(0,elarray.length);

        mergesort_helper(elarray, 0, elarray.length);
    }


    public static void mergesort_helper(Element[] arr, int low, int high){
        if ( high- low <= 1){
            return;
        }
        else {
            int mid = low + (high-low) / 2;
            mergesort_helper(arr, low, mid);
            mergesort_helper(arr, mid, high);
            merge( arr, low, high);
            StdDraw.clear();


            Double width= Double.valueOf(100/arr.length);
            Double halfwidth = (width/2);
            for(int i=0; i<arr.length; i++){
                double location = (2*i*halfwidth)+ halfwidth;
                if(i>=low && i< high) {
                    StdDraw.setPenColor(StdDraw.BOOK_RED);
                    arr[i].draw(location, halfwidth);
                }
                else {
                    StdDraw.setPenColor(StdDraw.BLACK);
                    arr[i].draw(location, halfwidth);
                }

                StdAudio.play(arr[i].getSound());
            }
        }
    }

    public static void merge(Element[] arr, int low, int high){
        int mid = low + (high - low) / 2;
        Element[] merged = new Element[high - low];
        int low_i = low;
        int upp_i = mid;
        for (int mer_i = 0; mer_i < merged.length; mer_i++) {
            if (low_i == mid) {
                while (upp_i < high) {
                    merged[mer_i] = arr[upp_i];
                    upp_i++;
                    mer_i++;
                }
                break;
            } else if (upp_i == high) {
                while (low_i < mid) {
                    merged[mer_i] = arr[low_i];
                    low_i++;
                    mer_i++;
                }
                break;
            } else if (arr[low_i].compareto(arr[upp_i]) <0 ) {
                merged[mer_i] = arr[low_i];
                low_i++;
            } else {
                merged[mer_i] = arr[upp_i];
                upp_i++;
            }
        }

        for (int i = 0; i < merged.length; i++) {
            arr[low + i] = merged[i];
        }
    }



}
