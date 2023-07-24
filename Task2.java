import java.util.Scanner;

class GetNumbers extends Thread {
    int[] array;
    Scanner scanner;

    public GetNumbers(int[] array) {
        this.array = array;
        scanner = new Scanner(System.in);
    }

    public void run() {
        while (true) {
            System.out.println("Enter numbers: ");
            for (int i = 0; i < array.length; i++) {
                array[i] = scanner.nextInt();
            }
            synchronized (array) {
                array.notify();
            }
            synchronized (this) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

class GetSum extends Thread {
    int[] array;

    public GetSum(int[] array) {
        this.array = array;
    }

    public void run() {
        while (true) {
            synchronized (array) {
                try {
                    array.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            int sum = 0;
            for (int i = 0; i < array.length; i++) {
                sum += array[i];
            }
            System.out.println("Sum: " + sum);
            for (int i = 0; i < array.length; i++) {
                array[i] = 0;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (this) {
                this.notify();
            }
        }
    }
}

class Task2 {
    public static void main(String[] args) {
        int N = 5;
        int[] array = new int[N];
        GetNumbers getNumbers = new GetNumbers(array);
        GetSum getSum = new GetSum(array);
        getNumbers.start();
        getSum.start();
    }
} 