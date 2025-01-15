package Main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Kruskall {
	// Method to find the parent of a node in the disjoint set
	private int findParent(int[] parent, int i) {
		if (parent[i] == -1)
			return i;
		return findParent(parent, parent[i]);
	}

	// Method to perform union of two subsets
	private void union(int[] parent, int x, int y) {
		int xSet = findParent(parent, x);
		int ySet = findParent(parent, y);
		parent[xSet] = ySet;
	}

	// Method to find the Minimum Spanning Tree (MST) using Kruskal's algorithm
	public Integer[][] findMST(Integer[][] matriz) {

		Integer[][] MST = new Integer[matriz.length][matriz.length];
		List<Aresta> arestas = new ArrayList<>();

		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz.length; j++) {
				if (matriz[i][j] != 0 && i > j) {
					arestas.add(new Aresta(i, j, matriz[i][j]));
				}
			}
		}

		List<Aresta> result = new ArrayList<>();
		int[] parent = new int[matriz.length];
		Arrays.fill(parent, -1);

		// Sort all the edges based on their weight
		Collections.sort(arestas);
		int edgeCount = 0;
		int index = 0;

		// Iterate through sorted edges and add
		// them to the MST if they don't form a cycle
		while (edgeCount < matriz.length - 1) {
			Aresta nextEdge = arestas.get(index++);
			int x = findParent(parent, nextEdge.getAtual());
			int y = findParent(parent, nextEdge.getProximo());

			// If including this edge doesn't cause a cycle,
			// include it in the result
			if (x != y) {
				result.add(nextEdge);
				union(parent, x, y);
				edgeCount++;
			}
		}
		return caminhoParaMatriz(result, matriz.length);
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
