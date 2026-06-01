package shellSort;

/**
 * Complexidade:
 *   - Melhor caso  : O(n log n)
 *   - Caso médio   : O(n log² n)
 *   - Pior caso    : O(n²)
 */
public class ShellSort {

    private int[]        vetor;
    private Estatisticas est;

    public ShellSort(int[] entrada) {
        this.vetor = entrada;
        this.est   = new Estatisticas();
    }

    public Estatisticas ordenar() {

        long inicio = System.nanoTime();

        shellSort();

        long fim = System.nanoTime();

        est.tempoMs = (fim - inicio) / 1_000_000.0;

        return est;
    }

    private void shellSort() {

        int n = vetor.length;
        int gap = 1;
        while (gap < n / 3) {
            gap = gap * 3 + 1;   // 1, 4, 13, 40, 121, 364, ...
        }

        while (gap >= 1) {
            for (int i = gap; i < n; i++) {

                int temp = vetor[i];
                int j    = i;

                while (j >= gap) {

                    est.comparacoes++;

                    if (vetor[j - gap] > temp) {

                        vetor[j] = vetor[j - gap];
                        est.trocas++;
                        j -= gap;

                    } else {
                        break;
                    }
                }

                vetor[j] = temp;
            }

            gap = gap / 3;  
        }
    }
    public void exibir() {

        for (int v : vetor) {
            System.out.print(v + " ");
        }

        System.out.println();
    }

    public int[] getVetor() {
        return vetor;
    }
}
