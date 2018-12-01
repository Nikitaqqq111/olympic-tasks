package training.thirteen.b;

import training.Downloader;
import training.IDownloader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.Deque;

public class Main {

    public static void main(String[] args) throws IOException {
        IDownloader downloader = new Downloader();
        Path path = Paths.get("src/main/resources/thirteen_b.input");
        String[] data = downloader.download(path).get(0).split(" ");
        int i = Integer.parseInt(data[0]);
        int fi = Integer.parseInt(data[1]);
        int j = Integer.parseInt(data[2]);
        int fj = Integer.parseInt(data[3]);
        int n = Integer.parseInt(data[4]);
        int fn = calculateNMemberFibonacci(i, fi, j, fj, n);
        System.out.println("Fn = " + fn);
    }

    private static int calculateNMemberFibonacci(int i, int fi, int j, int fj, int n) {
        if (n == i) {
            return fi;
        }
        if (n == j) {
            return fj;
        }
        if (i > j) {
            int tmp = i;
            i = j;
            j = tmp;
            tmp = fi;
            fi = fj;
            fj = tmp;
        }
        int fiNext = getNextAfterIMemberFibonacci(i, fi, j, fj);
        Deque<Integer> deque = new ArrayDeque<>();
        if (n > i) {
            deque.addLast(fi);
            deque.addLast(fiNext);
            for (int k = i + 2; k <= n; k++) {
                deque.addLast(deque.getFirst() + deque.getLast());
                deque.pollFirst();
            }
        } else {
            deque.addLast(fiNext);
            deque.addLast(fi);
            for (int k = i - 1; k >= n; k--) {
                deque.addLast(deque.getFirst() - deque.getLast());
                deque.pollFirst();
            }
        }
        return deque.getLast();
    }

    private static int getNextAfterIMemberFibonacci(int i, int fi, int j, int fj) {
        if (i + 1 == j) {
            return fj;
        }
        Pair pair1 = new Pair(1, 0);
        Pair pair2 = new Pair(0, 1);
        Deque<Pair> pairs = new ArrayDeque<>();
        pairs.addLast(pair1);
        pairs.addLast(pair2);
        int step = 1;
        while (i + step != j) {
            Pair pair = new Pair(pairs.getFirst().x + pairs.getLast().x,
                    pairs.getFirst().y + pairs.getLast().y);
            pairs.addLast(pair);
            pairs.pollFirst();
            step++;
        }
        return (fj - pairs.getLast().x * fi) / pairs.getLast().y;
    }

    private static class Pair {
        int x;
        int y;

        Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }

    }

}
