package Main;

public class Aresta implements Comparable<Aresta>{

	private Integer atual, proximo, peso;

	public Aresta(int atual, int proximo, int peso) {
		this.atual = atual;
		this.proximo = proximo;
		this.peso = peso;
	}

	@Override
	public String toString() {
		return "Aresta [atual=" + atual + ", proximo=" + proximo + ", peso=" + peso + "]";
	}

	@Override
	public int compareTo(Aresta outro) {
		return peso - outro.getPeso();
	}

	public int getAtual() {
		return atual;
	}

	public void setAtual(int atual) {
		this.atual = atual;
	}

	public int getProximo() {
		return proximo;
	}

	public void setProximo(int proximo) {
		this.proximo = proximo;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}
	
	
	
	
}
