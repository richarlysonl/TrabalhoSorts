package radixSort;


/**
 * Complexidade:
 *   - Melhor caso:  O(n * k)
 *   - Médio caso:   O(n * k)
 *   - Pior caso:    O(n * k)
 *   - Espaço:       O(n + b)
 *   - Estável:      SIM
 */
public class RadixSort {

    private static final int BASE = 10;
    /**
     * Ordena arr[] pelo dígito representado por (exp = 1, 10, 100, ...).
     */
    private static void countingSort(int[] arr, int exp, Estatisticas est) {
        int n = arr.length;
        int[] saida = new int[n];
        int[] contagem = new int[BASE];

        for (int val : arr) {
            int digito = (Math.abs(val) / exp) % BASE;
            contagem[digito]++;
            est.addComparacao(); 
        }
        for (int i = 1; i < BASE; i++) {
            contagem[i] += contagem[i - 1];
        }

        for (int i = n - 1; i >= 0; i--) {
            int digito = (Math.abs(arr[i]) / exp) % BASE;
            saida[--contagem[digito]] = arr[i];
            est.addTroca();
        }

        for (int i = 0; i < n; i++) {
            arr[i] = saida[i];
            est.addTroca();
        }
    }


    private static void radixSortPositivos(int[] arr, Estatisticas est) {
        if (arr.length == 0) return;

        int max = arr[0];
        for (int val : arr) if (val > max) max = val;

        for (int exp = 1; max / exp > 0; exp *= BASE) {
            countingSort(arr, exp, est);
        }
    }
    public static Estatisticas sort(int[] arr) {
        Estatisticas est = new Estatisticas();
        int n = arr.length;
        if (n <= 1) return est;

        long inicio = System.nanoTime();

        int countNeg = 0;
        for (int v : arr) if (v < 0) countNeg++;

        int[] negativos = new int[countNeg];
        int[] positivos = new int[n - countNeg];
        int iN = 0, iP = 0;

        for (int v : arr) {
            if (v < 0) negativos[iN++] = -v; 
            else        positivos[iP++] = v;
        }

        radixSortPositivos(negativos, est);
        radixSortPositivos(positivos, est);

        int k = 0;
        for (int i = negativos.length - 1; i >= 0; i--) {
            arr[k++] = -negativos[i];
            est.addTroca();
        }
        for (int v : positivos) {
            arr[k++] = v;
            est.addTroca();
        }

        est.setTempo(inicio, System.nanoTime());
        return est;
    }
}