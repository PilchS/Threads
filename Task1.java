import java.util.Random;

class PartialSum implements Runnable {
    int[] array;
    int startIndex;
    int endIndex;
    int partialSum;

    public PartialSum(int[] array, int startIndex, int endIndex) {
        this.array = array;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    public void run() {
        for (int i = startIndex; i < endIndex; i++) {
            partialSum += array[i];
        }
    }
}

class Task1 {
    public static void main(String[] args) throws InterruptedException {
        int N = 10000;
        int K = 4;
        int[] array = new int[N];
        Random rand = new Random();
        for (int i = 0; i < N; i++) {
            array[i] = rand.nextInt();
        }
        int m = N / K;
        PartialSum[] partialSums = new PartialSum[K];
        Thread[] threads = new Thread[K];
        for (int i = 0; i < K; i++) {
            partialSums[i] = new PartialSum(array, i * m, (i + 1) * m);
            threads[i] = new Thread(partialSums[i]);
            threads[i].start();
        }
        for (int i = 0; i < K; i++) {
            threads[i].join();
        }
        int totalSum = 0;
        for (int i = 0; i < K; i++) {
            totalSum += partialSums[i].partialSum;
        }
        System.out.println("Total sum: " + totalSum);
    }
}