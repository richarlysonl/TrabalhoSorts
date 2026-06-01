package combSort;

/**
 * Implementação do algoritmo Comb Sort.
 *
 * O Comb Sort é uma melhoria do Bubble Sort.
 * Em vez de comparar elementos adjacentes (gap = 1),
 * começa com um gap grande que vai encolhendo pelo
 * fator de redução (1.3) a cada passagem, eliminando
 * tartarugas (elementos pequenos no final do vetor)
 * muito mais rapidamente.
 *
 * Complexidade:
 *   - Melhor caso  : O(n log n)
 *   - Caso médio   : O(n² / 2^p)  onde p = número de incrementos
 *   - Pior caso    : O(n²)
 */
public class CombSort {

    private int[]       vetor;
    private Estatisticas est;

    /** Fator de redução recomendado pela literatura. */
    private static final double FATOR_REDUCAO = 1.3;

    /**
     * Construtor — recebe o vetor original (não o modifica;
     * trabalha sobre uma cópia interna).
     */
    public CombSort(int[] entrada) {
        this.vetor = entrada;          // referência direta, igual ao QuickSort original
        this.est   = new Estatisticas();
    }

    /**
     * Método público para iniciar a ordenação.
     * Retorna as estatísticas coletadas.
     */
    public Estatisticas ordenar() {

        long inicio = System.nanoTime();

        combSort();

        long fim = System.nanoTime();

        est.tempoMs = (fim - inicio) / 1_000_000.0;

        return est;
    }

    /**
     * Algoritmo Comb Sort.
     */
    private void combSort() {

        int n       = vetor.length;
        int gap     = n;
        boolean ordenado = false;

        while (!ordenado) {

            // Reduz o gap pelo fator de redução
            gap = proximoGap(gap);

            // Quando gap == 1 comporta-se como Bubble Sort;
            // só termina se não houve trocas nessa passagem.
            if (gap == 1) {
                ordenado = true;   // otimista; será false se houver troca
            }

            // Percorre o vetor com o gap atual
            for (int i = 0; i + gap < n; i++) {

                est.comparacoes++;

                if (vetor[i] > vetor[i + gap]) {

                    trocar(i, i + gap);
                    ordenado = false;   // ainda não terminou
                }
            }
        }
    }

    /**
     * Calcula o próximo gap. Nunca retorna valor menor que 1.
     */
    private int proximoGap(int gap) {

        gap = (int) (gap / FATOR_REDUCAO);

        return Math.max(gap, 1);
    }

    /**
     * Troca dois elementos e contabiliza.
     */
    private void trocar(int a, int b) {

        int temp  = vetor[a];
        vetor[a]  = vetor[b];
        vetor[b]  = temp;

        est.trocas++;
    }

    /**
     * Exibe o vetor no console.
     */
    public void exibir() {

        for (int v : vetor) {
            System.out.print(v + " ");
        }

        System.out.println();
    }

    /**
     * Retorna o vetor.
     */
    public int[] getVetor() {
        return vetor;
    }
}
