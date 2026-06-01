package TimSort;
/**
 * Complexidade:
 *   - Melhor caso:  O(n)
 *   - Médio caso:   O(n log n)
 *   - Pior caso:    O(n log n)
 *   - Estável:      SIM
 */
public class TimSort {

    private static final int RUN = 32;


    private static void insertionSort(double[] arr, int left, int right,
                                      Estatisticas est) {
        for (int i = left + 1; i <= right; i++) {
            double temp = arr[i];
            int j = i - 1;

            while (j >= left) {
                est.addComparacao();
                if (arr[j] > temp) {
                    arr[j + 1] = arr[j];
                    est.addTroca();
                    j--;
                } else {
                    break;
                }
            }

            if (j + 1 != i) {
                arr[j + 1] = temp;
                est.addTroca();
            }
        }
    }

    private static void merge(double[] arr, int left, int mid, int right,
                              Estatisticas est) {
        int lenL = mid - left + 1;
        int lenR = right - mid;

        double[] L = new double[lenL];
        double[] R = new double[lenR];

        for (int i = 0; i < lenL; i++) { L[i] = arr[left + i];    est.addTroca(); }
        for (int j = 0; j < lenR; j++) { R[j] = arr[mid + 1 + j]; est.addTroca(); }

        int i = 0, j = 0, k = left;
        while (i < lenL && j < lenR) {
            est.addComparacao();
            arr[k++] = (L[i] <= R[j]) ? L[i++] : R[j++];
            est.addTroca();
        }
        while (i < lenL) { arr[k++] = L[i++]; est.addTroca(); }
        while (j < lenR) { arr[k++] = R[j++]; est.addTroca(); }
    }
    public static Estatisticas sort(double[] arr) {
        Estatisticas est = new Estatisticas();
        int n = arr.length;
        if (n <= 1) return est;

        long inicio = System.nanoTime();
        for (int i = 0; i < n; i += RUN) {
            insertionSort(arr, i, Math.min(i + RUN - 1, n - 1), est);
        }
        for (int size = RUN; size < n; size *= 2) {
            for (int left = 0; left < n; left += 2 * size) {
                int mid   = Math.min(left + size - 1, n - 1);
                int right = Math.min(left + 2 * size - 1, n - 1);
                if (mid < right) merge(arr, left, mid, right, est);
            }
        }
        est.setTempo(inicio, System.nanoTime());
        return est;
    }
    public static Estatisticas sort(int[] arr) {
        double[] tmp = new double[arr.length];
        for (int i = 0; i < arr.length; i++) tmp[i] = arr[i];
        Estatisticas est = sort(tmp);
        for (int i = 0; i < arr.length; i++) arr[i] = (int) tmp[i];
        return est;
    }
}