
package quickSort;

/**
 * complexidade:
 * Melhor   O(n log n)
 * Médio    O(n log n)
 * pior     O(n²)
 */
public class QuickSort {

    private int[] vetor;
    private Estatisticas est;

    public QuickSort(int[] entrada) {
        this.vetor = entrada;
        this.est = new Estatisticas();
    }

    public Estatisticas ordenar() {
        long inicio = System.nanoTime();
        quickSort(0, vetor.length - 1);
        long fim = System.nanoTime();
        est.tempoMs = (fim - inicio) / 1_000_000.0;
        return est;
    }

    private void quickSort(int inicio, int fim) {
        est.comparacoes++;

        if (inicio < fim) {

            int indicePivo = particionar(inicio, fim);

            quickSort(inicio, indicePivo - 1);

            quickSort(indicePivo + 1, fim);
        }
    }

    private int particionar(int inicio, int fim) {

    int meio = (inicio + fim) / 2;

    trocar(meio, fim);

    int pivo = vetor[fim];

    int i = inicio - 1;

    for (int j = inicio; j < fim; j++) {

        est.comparacoes++;

        if (vetor[j] <= pivo) {

            i++;

            trocar(i, j);
        }
    }

    trocar(i + 1, fim);

    return i + 1;
}
    private void trocar(int a, int b) {

        int temp = vetor[a];
        vetor[a] = vetor[b];
        vetor[b] = temp;

        est.trocas++;
    }

    public void exibir() {

        for (int i = 0; i < vetor.length; i++) {
            System.out.print(vetor[i] + " ");
        }

        System.out.println();
    }

    public int[] getVetor() {
        return vetor;
    }
}