package proc;

import java.util.Set;

public class GrafoPesado
{
	private Grafo _grafo;
	private double[][] _pesos;
	
	public GrafoPesado(int n)
	{
		_grafo = new Grafo(n);
		_pesos = new double[n][n];
	}
	
	public void agregarArista(int i, int j, double peso)
	{
		_grafo.agregarArista(i,j);
		_pesos[i][j] = peso;
		_pesos[j][i] = peso;
	}
	
	public boolean contieneArista(int i, int j)
	{
		return _grafo.contieneArista(i, j);
	}
	
	public double getPeso(int i, int j)
	{
		if( _grafo.contieneArista(i, j) == false )
			throw new IllegalArgumentException("Se consulto el peso de una arista inexistente! " + i + ", " + j);

		return _pesos[i][j];		
	}

	// Consultas
	public int vertices()
	{
		return _grafo.vertices();
	}
	public int aristas()
	{
		return _grafo.aristas();
	}
	public Set<Integer> vecinos(int i)
	{
		return _grafo.vecinos(i);
	}
}
