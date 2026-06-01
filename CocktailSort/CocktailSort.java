

/**
 * Complexidade:
 *   - Melhor caso : O(n)   — vetor já ordenado
 *   - Caso médio  : O(n²)
 *   - Pior caso   : O(n²)  — vetor em ordem inversa
 */
public class CocktailSort {

    private int[]        vetor;
    private Estatisticas est;
    public CocktailSort(int[] entrada) {
        this.vetor = entrada;
        this.est   = new Estatisticas();
    }
    public Estatisticas ordenar() {
        long inicio = System.nanoTime();
        cocktailSort();
        long fim = System.nanoTime();
        est.tempoMs = (fim - inicio) / 1_000_000.0;
        return est;
    }
    private void cocktailSort() {

        int inicio = 0;
        int fim    = vetor.length - 1;
        boolean trocou = true;

        while (trocou) {
            trocou = false;
            for (int i = inicio; i < fim; i++) {
                est.comparacoes++;
                if (vetor[i] > vetor[i + 1]) {
                    trocar(i, i + 1);
                    trocou = true;
                }
            }
            fim--;
            if (!trocou) break;
            trocou = false;
            for (int i = fim; i > inicio; i--) {
                est.comparacoes++;
                if (vetor[i] < vetor[i - 1]) {
                    trocar(i, i - 1);
                    trocou = true;
                }
            }
            inicio++;
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
