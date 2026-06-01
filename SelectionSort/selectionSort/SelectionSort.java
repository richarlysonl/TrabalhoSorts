package selectionSort;



public class SelectionSort {

    private int[] vetor;
    private Estatisticas est;

    public SelectionSort(int[] entrada) {

        this.vetor = entrada;
        this.est = new Estatisticas();
    }

    public Estatisticas ordenar() {

        long inicio = System.nanoTime();

        selectionSort();

        long fim = System.nanoTime();

        est.tempoMs = (fim - inicio) / 1_000_000.0;

        return est;
    }

    private void selectionSort() {

        int n = vetor.length;

        for (int i = 0; i < n - 1; i++) {

            int menorIndice = i;

            for (int j = i + 1; j < n; j++) {

                est.comparacoes++;

                if (vetor[j] < vetor[menorIndice]) {
                    menorIndice = j;
                }
            }

            if (menorIndice != i) {
                trocar(i, menorIndice);
            }
        }
    }

    private void trocar(int a, int b) {

        int temp = vetor[a];

        vetor[a] = vetor[b];

        vetor[b] = temp;

        est.trocas++;
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