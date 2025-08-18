import java.util.Arrays;

public class Mochila {
    // Força bruta: testa todas as combinações
    public static int[] mochilaForcaBruta(int[] pesos, int[] valores, int capacidade, int[] iteracoes) {
        int n = pesos.length;
        int maxVal = 0;
        int[] bestComb = new int[n];
        int totalComb = 1 << n;
        for (int mask = 0; mask < totalComb; mask++) {
            iteracoes[0]++;
            int peso = 0, valor = 0;
            int[] comb = new int[n];
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    peso += pesos[i];
                    valor += valores[i];
                    comb[i] = 1;
                }
            }
            if (peso <= capacidade && valor > maxVal) {
                maxVal = valor;
                bestComb = Arrays.copyOf(comb, n);
            }
        }
        return new int[]{maxVal, iteracoes[0], bestComb[0], bestComb[1], bestComb[2], bestComb[3]};
    }

    // Programação dinâmica
    public static int[] mochilaDinamica(int[] pesos, int[] valores, int capacidade, int[] iteracoes) {
        int n = pesos.length;
        int[][] dp = new int[n + 1][capacidade + 1];
        for (int i = 1; i <= n; i++) {
            for (int w = 0; w <= capacidade; w++) {
                iteracoes[0]++;
                if (pesos[i - 1] <= w) {
                    dp[i][w] = Math.max(dp[i - 1][w], valores[i - 1] + dp[i - 1][w - pesos[i - 1]]);
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }
        // Reconstruir solução
        int[] itens = new int[n];
        int w = capacidade;
        for (int i = n; i > 0; i--) {
            if (dp[i][w] != dp[i - 1][w]) {
                itens[i - 1] = 1;
                w -= pesos[i - 1];
            }
        }
        return new int[]{dp[n][capacidade], iteracoes[0], itens[0], itens[1], itens[2], itens[3]};
    }

    public static void main(String[] args) {
        // Exemplo da aula
        int[] pesos = {2, 3, 4, 5};
        int[] valores = {3, 4, 5, 6};
        int capacidade = 5;

        int[] it = new int[1];
        int[] res = mochilaForcaBruta(pesos, valores, capacidade, it);
        System.out.println("Força Bruta:");
        System.out.printf("Valor ótimo: %d, combinação: %s, iterações: %d\n",
                res[0], Arrays.toString(Arrays.copyOfRange(res, 2, 6)), res[1]);

        it[0] = 0;
        res = mochilaDinamica(pesos, valores, capacidade, it);
        System.out.println("Dinâmica:");
        System.out.printf("Valor ótimo: %d, combinação: %s, iterações: %d\n",
                res[0], Arrays.toString(Arrays.copyOfRange(res, 2, 6)), res[1]);
    }
}
