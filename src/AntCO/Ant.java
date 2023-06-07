package AntCO;
import java.util.ArrayList;

/* Name: Ant
description: Ant class for Ant Colony Optimization
Date added: 04 Jun 2023
Last modified: 04 Jun 2023
*/
public class Ant {
    Colony antcolony;
    int id;
    int path[];
    int pathlength;
    int maxvertex;
    int start;
    int current;
    int next;
    int sigma; /*sum of all weights*/
    int weight;


    /*Constructor*/
    public Ant(int max, int start, int end, float gamma, int id, AntColony antcolony) {
        this.id = id;
        this.maxvertex = max;
        this.start = start;
        this.antcolony = antcolony;
        this.path = new int[this.maxvertex];
        this.sigma = 0;
        this.weight = 0;
        this.current = start;
        this.next = -1;
        this.pathlength = 0;
        this.path[0] = start;
    }

    /* Name: move
    input: none
    output: none
    description: move the ant to the next vertex and update stored path
    Date added: 04 Jun 2023
    Last modified: 04 Jun 2023
    */
    public boolean move() {
        boolean hamilton = false;
        this.next = this.selectNext(this.current);
        this.path[this.pathlength] = this.next;
        this.pathlength++;
        this.sigma +=this.antcolony.graph.getCost(this.current+1, this.next+1) ;
        this.current = this.next;
        if(this.current==this.start){
            hamilton = true;
        }
        return hamilton;
    }

    public int selectNext(int current){ /*TODO: reset path if all adjacent were already visited*/
        int next = -1;
        float max = 0;
        float alpha = this.antcolony.alpha;
        float beta = this.antcolony.beta;
        float abs_prob = 0;
        int Arraylist<Integer> possible = this.antcolony.GetAdj(current);
        float Arraylist<Float> prob_arr = new ArrayList<Float>();
        float sum=0;
        if (possible.size > 0) {
            /*Have to fix this. Had an EMERGENCY and had to left the work a meio */
            //Get probability array for all adjacent nodes
            for (int i = 0; i < possible.size; i++) {
                next = possible.get(i);
                abs_prob = (float) alpha / (beta + this.antcolony.graph.getCost(current+1, next+1));
                prob_arr.add(abs_prob);
                sum+=prob_arr.get(i);
            }
            for (int i = 0; i < possible.size; i++) {
                prob_arr.set(i, prob_arr.get(i) / sum);
                //System.out.println("Probabilities: " + prob_arr.get(i));
            }

            sum = 0;
            aux = 0;
            for (int i = 0; i < possible.size; i++) {
                aux = prob_arr.get(i);
                prob_arr.set(i, aux + sum);
                sum+=aux;
            }

            aux = rand.nextFloat();
            for (int i = 0; i < possible.size; i++) {
                if (Float.compare(aux, prob_arr.get(i)) < 0) {
                    next = possible.get(i);
                    return next;
                   break;
                }
            }
        }

    }

    /* Name: pheromonize
    input: none
    output: none
    description: pheromonize all edges in the stored path
    Date added: 04 Jun 2023
    Last modified: 04 Jun 2023
    */
    public void pheromonize(){
        for (int i=0; i<this.pathlength; i++)
            this.antcolony.pheromone.incrementFvalue(this.path[i], this.path[i+1], this.antcolony.gamma, this.sigma , this.antcolony.graph.getCost(i+1,j+1));
    }

    /* Name: reset
    input: none
    output: none
    description: reset the ant to its initial state
    Date added: 04 Jun 2023
    Last modified: 04 Jun 2023
    */
    public void reset() {
        this.pathlength = 0;
        this.sigma = 0;
        this.weight = 0;
        this.current = this.start;
        this.next = -1;
        this.path[0] = this.start;

    }
}
