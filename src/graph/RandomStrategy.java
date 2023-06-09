package graph2;

import java.util.Random;

public class RandomStrategy implements GenerationStrategy{

	public RandomStrategy() {
		
	}
	
    public void generate(Graph G, Object o) {
    	
    	int maxCost = (Integer)o;
        Random rand = new Random();  //Fazer singleton neste rand?

        //Creates a Ring Graph which automatically has a Hamilatonian Cycle
        for (int i = 0; i < G.getNumNodes() - 1; i++) {
            G.addEdge(Vertex.getInstance(i+1), Vertex.getInstance(i + 2), rand.nextInt(maxCost) + 1);
        }
        G.addEdge(Vertex.getInstance(1), Vertex.getInstance(G.getNumNodes()), rand.nextInt(maxCost) + 1);

        //Add up to n(n-1)/2 - n edges, where n is the number of nodes
        int bound = G.getNumNodes() * (G.getNumNodes() - 1) / 2 - G.getNumNodes();
        if (bound > 0) {
            bound = rand.nextInt(bound+1); //number of extra edges to add
            int n1, n2;
            //Add extra edges in new places
            for (int i = 0; i < bound; i++) {
                n1 = rand.nextInt(1, G.getNumNodes()+1);
                n2 = rand.nextInt(1, G.getNumNodes()+1);
                if (n1 != n2 && G.getCost(Vertex.getInstance(n1), Vertex.getInstance(n2)) == 0) {
                    G.addEdge(Vertex.getInstance(n1), Vertex.getInstance(n2), (rand.nextInt(maxCost)+ 1));
                } else {
                    i--;
                }
            }
        }
    }
}
