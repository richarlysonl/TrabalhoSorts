package BubbleSort;



public class BubbleSort {

    private int[] vetor;
    private Estatisticas est;

    public BubbleSort(int[] entrada) {

        this.vetor = entrada;
        this.est = new Estatisticas();
    }

    public Estatisticas ordenar() {

        long inicio = System.nanoTime();

        bubbleSort();

        long fim = System.nanoTime();

        est.tempoMs = (fim - inicio) / 1_000_000.0;

        return est;
    }

    private void bubbleSort() {

        int n = vetor.length;

        boolean houveTroca;

        for (int i = 0; i < n - 1; i++) {

            houveTroca = false;

            for (int j = 0; j < n - i - 1; j++) {

                est.comparacoes++;

                if (vetor[j] > vetor[j + 1]) {

                    trocar(j, j + 1);

                    houveTroca = true;
                }
            }

            if (!houveTroca) {
                break;
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