package radixSort;


/**
 * Armazena as estatísticas coletadas durante a execução de um algoritmo de ordenação.
 *
 * Rastreia:
 *   - Número de comparações entre elementos
 *   - Número de trocas / movimentações de elementos
 *   - Tempo de execução em milissegundos
 */
public class Estatisticas {

    public long   comparacoes = 0;
    public long   trocas      = 0;
    public double tempoMs     = 0.0;

    /** Incrementa o contador de comparações. */
    public void addComparacao() {
        comparacoes++;
    }

    /** Incrementa o contador de trocas. */
    public void addTroca() {
        trocas++;
    }

    /** Define o tempo de execução a partir de valores em nanosegundos. */
    public void setTempo(long inicioNano, long fimNano) {
        this.tempoMs = (fimNano - inicioNano) / 1_000_000.0;
    }

    /** Reseta todos os contadores. */
    public void reset() {
        comparacoes = 0;
        trocas      = 0;
        tempoMs     = 0.0;
    }

    @Override
    public String toString() {
        return String.format(
            "Comparacoes: %,d | Trocas: %,d | Tempo: %.3f ms",
            comparacoes, trocas, tempoMs
        );
    }
}