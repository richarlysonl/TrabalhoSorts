
public class InsertSort {

    private int[] vetor;
    private Estatisticas est;

    public InsertSort(int[] entrada) {

        this.vetor = entrada;
        this.est = new Estatisticas();
    }

    public Estatisticas ordenar() {

        long inicio = System.nanoTime();

        insertionSort();

        long fim = System.nanoTime();

        est.tempoMs = (fim - inicio) / 1_000_000.0;

        return est;
    }

    private void insertionSort() {

        int n = vetor.length;

        for (int i = 1; i < n; i++) {

            int chave = vetor[i];

            int j = i - 1;

            while (j >= 0) {

                est.comparacoes++;

                if (vetor[j] > chave) {

                    vetor[j + 1] = vetor[j];

                    est.trocas++;

                    j--;

                } else {
                    break;
                }
            }

            vetor[j + 1] = chave;
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