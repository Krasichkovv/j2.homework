package lesson5;

public class Main {
    static final int size = 10000000;
    static final int h = size / 2;
    static float[] arr = new float[size];

    public static void main(String[] args) {
        firstMet(arr);
        secondMet(arr);
    }


    static void firstMet(float[] arr){
        for (int i = 0; i < size ; i++)  arr[i] = 1;
        long a = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float)(arr[i] * Math.sin(0.2f + arr[i]/ 5) *
                    Math.cos(0.2f + arr[i] / 5) * Math.cos(0.4f + arr[i] / 2));
        }
        System.out.println("Время окончания 1го метода = " + (System.currentTimeMillis() - a));
    }

    static void secondMet(float[] arr) {
        for (int i = 0; i < size; i++) arr[i] = 1;
        long a = System.currentTimeMillis();
        float[] arr1 = new float[h];
        float[] arr2 = new float[h];
        System.arraycopy(arr, 0, arr1, 0, h);
        System.arraycopy(arr, h, arr2, 0, h);

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < arr1.length; i++) {
                    arr1[i] = (float)(arr1[i] * Math.sin(0.2f + arr1[i]/ 5) *
                            Math.cos(0.2f + arr1[i] / 5) * Math.cos(0.4f + arr1[i] / 2));

                }
                System.out.println("поток 1 стоп!");
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < arr2.length; i++) {
                    arr2[i] = (float)(arr2[i] * Math.sin(0.2f + arr2[i]/ 5) *
                            Math.cos(0.2f + arr2[i] / 5) * Math.cos(0.4f + arr2[i] / 2));

                }
                System.out.println("поток 2 стоп!");
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.arraycopy(arr1, 0, arr, 0, h);
        System.arraycopy(arr2, 0, arr, h, h);

        System.out.println("Время окончания 2го метода = " + (System.currentTimeMillis() - a));
    }
}
