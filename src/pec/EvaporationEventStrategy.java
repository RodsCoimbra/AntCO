package pec;
import  antco.IColony;
/**
 * Strategy pattern for Evaporation Event
 * @see antco.Pheromone
 */
public class EvaporationEventStrategy implements EventStrategy {
    private final Double mean;
    private final IColony colony;
    private final PEC pec;

	/**Public Constructor for EvaporationEventStrategy class
	 * @param mean arithmethic mean parameter to schedule events
	 * @param colony interface to access the colony the simulator refers to
	 * @param pec simulator for which the event refers
	 */
    public EvaporationEventStrategy(Double mean, IColony colony, PEC pec) {
        this.mean = mean;
        this.colony = colony;
        this.pec = pec;
    }
	/**Public executer for Evaporation Events,
	 * executes trigger of Pheromone decay and schedules next Evaporation for refered edge
	 * @param id ID of edge to evaporate pheromone from
	 * @param tempo current "time"
	 * @param NumberEvents current number of events in pec queue
	 */
    @Override
    public void execute(int id, double tempo, Integer[] NumberEvents) {
        NumberEvents[1]++;
        if (this.colony.triggerPheromoneDecay(id)) {
            this.pec.Addevent(tempo + this.pec.ExponentialTime(this.mean), "Evaporação", id);
        }
    }
}
