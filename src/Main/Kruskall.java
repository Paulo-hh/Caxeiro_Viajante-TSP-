package Main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Kruskall {
	// Method to find the parent of a node in the disjoint set
	private int encontrarNodo(int[] nodo, int i) {
		if (nodo[i] == -1)
			return i;
		return encontrarNodo(nodo, nodo[i]);
	}

	private void unir(int[] nodo, int x, int y) {
		int a = encontrarNodo(nodo, x);
		int b = encontrarNodo(nodo, y);
		nodo[a] = b;
	}


	public Integer[][] encontrarMST(Integer[][] matriz) {

		List<Aresta> arestas = new ArrayList<>();

		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz.length; j++) {
				if (matriz[i][j] != 0 && i > j) {
					arestas.add(new Aresta(i, j, matriz[i][j]));
				}
			}
		}

		List<Aresta> resultado = new ArrayList<>();
		int[] nodos = new int[matriz.length];
		Arrays.fill(nodos, -1);

		// Sort all the edges based on their weight
		Collections.sort(arestas);
		int contarArestas = 0;
		int indice = 0;

		// Iterate through sorted edges and add
		// them to the MST if they don't form a cycle
		while (contarArestas < matriz.length - 1) {
			Aresta proximaAresta = arestas.get(indice++);
			int x = encontrarNodo(nodos, proximaAresta.getAtual());
			int y = encontrarNodo(nodos, proximaAresta.getProximo());

			// If including this edge doesn't cause a cycle,
			// include it in the result
			if (x != y) {
				resultado.add(proximaAresta);
				unir(nodos, x, y);
				contarArestas++;
			}
		}
		return caminhoParaMatriz(resultado, matriz.length);
	}

	//um metodo que retorna uma matriz contendo o resultado
	public Integer[][] caminhoParaMatriz(List<Aresta> resultado, int n) {
		Integer[][] MST = new Integer[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				MST[i][j] = 0;
			}
		}
		for (Aresta a : resultado) {
			MST[a.getAtual()][a.getProximo()] = a.getPeso();
			MST[a.getProximo()][a.getAtual()] = a.getPeso();
		}
		return MST;
	}
}
