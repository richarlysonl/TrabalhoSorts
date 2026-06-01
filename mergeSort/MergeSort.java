package mergeSort;

/**
 * Complexidade:
 *   - Melhor caso:  O(n log n)
 *   - Médio caso:   O(n log n)
 *   - Pior caso:    O(n log n)
 *   - Espaço - consome espaço extra para a recusão
 *   - Estável:      SIM
 */
public class MergeSort {
    private static void merge(double[] arr, int left, int mid, int right,
                              Estatisticas est) {
        int lenL = mid - left + 1;
        int lenR = right - mid;

        double[] L = new double[lenL];
        double[] R = new double[lenR];

        // Copia para arrays auxiliares
        for (int i = 0; i < lenL; i++) { L[i] = arr[left + i];    est.addTroca(); }
        for (int j = 0; j < lenR; j++) { R[j] = arr[mid + 1 + j]; est.addTroca(); }

        int i = 0, j = 0, k = left;

        while (i < lenL && j < lenR) {
            est.addComparacao();
            if (L[i] <= R[j]) {
                arr[k++] = L[i++];
            } else {
                arr[k++] = R[j++];
            }
            est.addTroca();
        }
        while (i < lenL) { arr[k++] = L[i++]; est.addTroca(); }
        while (j < lenR) { arr[k++] = R[j++]; est.addTroca(); }
    }
    private static void mergeSort(double[] arr, int left, int right,
                                  Estatisticas est) {
        if (left >= right) return;

        int mid = left + (right - left) / 2;

        mergeSort(arr, left,    mid,   est);
        mergeSort(arr, mid + 1, right, est);
        merge    (arr, left,    mid,   right, est);
    }

    public static Estatisticas sort(double[] arr) {
        Estatisticas est = new Estatisticas();
        if (arr.length <= 1) return est;

        long inicio = System.nanoTime();
        mergeSort(arr, 0, arr.length - 1, est);
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