package Main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TSP {

	public static void main(String[] args) {
		String[] arquivos = { "C:\\Arquivos\\tsp1_253.txt", "C:\\Arquivos\\tsp2_1248.txt",
				"C:\\Arquivos\\tsp3_1194.txt", "C:\\Arquivos\\tsp4_7013.txt", "C:\\Arquivos\\tsp5_27603.txt" };

		System.out.println("TSP\tCusto A.Aproximativo\tTempo A.Aproximativo\t"
				+ "    Custo A.Exato\t\tTempo A.Exato");

		for (int i=1; i<=arquivos.length; i++) {  
			Integer[][] matriz = lerArquivo(arquivos[i-1]);

			List<Integer> tamanho = new ArrayList<>();
			int custo_minimo = Integer.MAX_VALUE;
			double tempo_minimo = Long.MAX_VALUE;

			for (int k = 0; k < matriz.length; k++) {
				tamanho.add(k);
			}

			for (Integer nodo_inicial : tamanho) {
				double iniciar_tempo = System.nanoTime();
				int custo = algoritmo_aproximativo(matriz, nodo_inicial);
				double tempo_aproximado = System.nanoTime() - iniciar_tempo; // em segundo, divida por 1000
				if(custo_minimo >= custo) {
					custo_minimo = custo;
					tempo_minimo = tempo_aproximado;
				}
			}
			
			if(i < 3) {
				double iniciar_tempo = System.nanoTime();
				int custo_exato = algoritmo_exato(matriz, Arrays.asList(0), Integer.MAX_VALUE, null);
				double tempo_exato = System.nanoTime() - iniciar_tempo;
				System.out.println(
						"TSP " + i + "\t\t" + custo_minimo + "\t\t\t" + String.format("%.7f", tempo_minimo / Math.pow(10, 9)) + " s" 
				+ "\t\t" + custo_exato + "\t\t\t" + String.format("%.7f", tempo_exato / Math.pow(10, 9)) + " s");
				
			}
			else {
				System.out.println(
						"TSP " + i + "\t\t" + custo_minimo + "\t\t\t" + String.format("%.7f", tempo_minimo / Math.pow(10, 9)) + " s");
				
			}
			tamanho.clear();
			}
	}

	public static Integer[][] lerArquivo(String path) {
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String line = br.readLine();
			String[] lenght = line.split("\\s+");
			Integer[][] matriz = new Integer[lenght.length][lenght.length];
			int cont = 0;
			while (line != null) {
				String[] parts = line.split("\\s+");
				for (int i = 0; i < parts.length; i++) {
					matriz[cont][i] = Integer.parseInt(parts[i].trim());
				}
				cont++;
				line = br.readLine();
			}
			return matriz;
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public static int algoritmo_exato(Integer[][] matriz, List<Integer> caminho, int melhor_custo,
			List<Integer> melhor_caminho) {
		List<Integer> caminho_atual = new ArrayList<>();
		for (Integer c : caminho) {
			caminho_atual.add(c);
		}

		if (caminho_atual.size() == matriz.length) {
			caminho_atual.add(0);
			int custo_final = calcularCustoCaminho(matriz, caminho_atual);
			if (custo_final < melhor_custo) {
				for (Integer i : caminho_atual)
					if (melhor_caminho != null) {
						melhor_caminho.add(i);
					}
				melhor_custo = custo_final;
			}
			caminho_atual.remove(caminho_atual.size() - 1);
			return melhor_custo;
		}

		for (int i = 0; i < matriz.length; i++) {
			if (caminho_atual.contains(i)) {
				continue;
			}
			caminho_atual.add(i);
			melhor_custo = algoritmo_exato(matriz, caminho_atual, melhor_custo, melhor_caminho);
			caminho_atual.remove(caminho_atual.size() - 1);
		}
		return melhor_custo;

	}

	public static int algoritmo_aproximativo(Integer[][] matriz, int nodo_inicial) {
		List<Integer> caminho = new ArrayList<>();
		Integer[][] MST;
		
		Kruskall k = new Kruskall();
		MST = k.findMST(matriz);

		List<Integer> nodos = new ArrayList<>();
		for (int i = 0; i < MST.length; i++) {
			nodos.add(i);
		}
		Set<Integer> conjunto = new HashSet<>();
		caminho.add(nodo_inicial);
		conjunto.add(nodo_inicial);
		int nodo_atual = nodo_inicial;
		int nodo_anterior = 1;

		while (conjunto.size() != nodos.size()) {
			boolean ok = true;
			for (Integer nodo_conectado : nodos) {
				if (MST[nodo_atual][nodo_conectado] == 0 && MST[nodo_conectado][nodo_atual] == 0) {
				} else {
					if (caminho.contains(nodo_conectado)) {
					} else {
						ok = false;
						caminho.add(nodo_conectado);
						conjunto.add(nodo_conectado);
						nodo_atual = nodo_conectado;
						nodo_anterior = 1;
						break;
					}
				}
			}
			if (ok) {
				nodo_atual = caminho.get(caminho.size() - 1 - nodo_anterior);
				nodo_anterior++;
			}
		}

		caminho.add(nodo_inicial);
		int tsp_custo = calcularCustoCaminho(matriz, caminho);
		return tsp_custo;
	}

	public static int calcularCustoCaminho(Integer[][] matrix, List<Integer> caminho) {
		int custo = 0;
		for (int i = 0; i < caminho.size() - 1; i++) {
			int atual = caminho.get(i);
			int proximo = caminho.get(i + 1);
			custo += matrix[atual][proximo];
		}
		return custo;
	}

}
