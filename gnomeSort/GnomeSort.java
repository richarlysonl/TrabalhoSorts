package gnomeSort;

/**
 * Complexidade:
 *   - Melhor caso : O(n)   — vetor já ordenado, só avança
 *   - Caso médio  : O(n²)
 *   - Pior caso   : O(n²)  — vetor em ordem inversa
 */
public class GnomeSort {

    private int[]        vetor;
    private Estatisticas est;
    public GnomeSort(int[] entrada) {
        this.vetor = entrada;
        this.est   = new Estatisticas();
    }
    public Estatisticas ordenar() {

        long inicio = System.nanoTime();

        gnomeSort();

        long fim = System.nanoTime();

        est.tempoMs = (fim - inicio) / 1_000_000.0;

        return est;
    }
    private void gnomeSort() {

        int n   = vetor.length;
        int pos = 0;

        while (pos < n) {

            if (pos == 0) {
                // No início do vetor: só avança
                pos++;

            } else {

                est.comparacoes++;

                if (vetor[pos] >= vetor[pos - 1]) {
                    // Ordem correta: avança
                    pos++;

                } else {
                    // Fora de ordem: troca e recua
                    trocar(pos, pos - 1);
                    pos--;
                }
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
