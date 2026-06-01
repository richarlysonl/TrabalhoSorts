package bucketSort;

import java.util.ArrayList;
import java.util.List;

/**
 * complexidade:
 * melhor caso - O(n) 
 * medio caso - O(n)
 * pior caso - O(n²)
 * 
 */
public class BucketSort {

    public static class Estatisticas {
        public long comparacoes = 0;
        public long trocas = 0;
        public double tempoMs = 0;
    }
    private static void insertionSort(List<Double> bucket, Estatisticas est) {

        for (int i = 1; i < bucket.size(); i++) {

            double chave = bucket.get(i);
            int j = i - 1;

            while (j >= 0) {

                est.comparacoes++;

                if (bucket.get(j) > chave) {

                    bucket.set(j + 1, bucket.get(j));
                    est.trocas++;

                    j--;

                } else {
                    break;
                }
            }

            bucket.set(j + 1, chave);
        }
    }
    public static Estatisticas sortDoubles(double[] arr) {

        Estatisticas est = new Estatisticas();

        int n = arr.length;

        if (n <= 1) {
            return est;
        }

        long inicio = System.nanoTime();

        double min = arr[0];
        double max = arr[0];

        for (double val : arr) {

            est.comparacoes++;

            if (val < min) {
                min = val;
            }

            est.comparacoes++;

            if (val > max) {
                max = val;
            }
        }
        if (min == max) {

            long fim = System.nanoTime();
            est.tempoMs = (fim - inicio) / 1_000_000.0;

            return est;
        }

        int bucketCount = n;

        @SuppressWarnings("unchecked")
        List<Double>[] buckets = new ArrayList[bucketCount];

        for (int i = 0; i < bucketCount; i++) {
            buckets[i] = new ArrayList<>();
        }
        double range = max - min;

        for (double val : arr) {

            int index = (int) ((val - min) / range * (bucketCount - 1));

            buckets[index].add(val);

            est.trocas++;
        }
        int pos = 0;

        for (List<Double> bucket : buckets) {

            insertionSort(bucket, est);

            for (double val : bucket) {

                arr[pos++] = val;

                est.trocas++;
            }
        }

        long fim = System.nanoTime();

        est.tempoMs = (fim - inicio) / 1_000_000.0;

        return est;
    }
}