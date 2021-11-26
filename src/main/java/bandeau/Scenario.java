package bandeau;
import java.util.List;
import java.util.LinkedList;

/**
 * Classe utilitaire pour représenter la classe-association UML
 */
class ScenarioElement {

    Effect effect;
    int repeats;

    ScenarioElement(Effect e, int r) {
        effect = e;
        repeats = r;
    }
}
/**
 * Un scenario mémorise une liste d'effets, et le nombre de repetitions pour chaque effet
 * Un scenario sait se jouer sur un bandeau.
 */
public class Scenario {

    private final List<ScenarioElement> myElements = new LinkedList<>();

    /**
     * Ajouter un effect au scenario.
     *
     * @param e l'effet à ajouter
     * @param repeats le nombre de répétitions pour cet effet
     */
    public void addEffect(Effect e, int repeats) {
        myElements.add(new ScenarioElement(e, repeats));
    }

    /**
     * Jouer ce scenario sur un bandeau
     *
     * @param b le bandeau ou s'afficher.
     */
    public void playOn(Bandeau b) {
        //On crée le nouveau thread afin de pouvoir lancer plusieurs scénarii sur plusieurs bandeaux parallèlement
    	Thread t = new Thread() {
		    public void run() {
                //On verrouille implicitement le bandeau b passé en paramètre afin d'empêcher de jouer plusieurs scénarii en même temps sur ce bandeau
		    	synchronized(b) {
                    //Pour chaque effet du scénario
		    		for (ScenarioElement element : myElements) {
                        //On le joue autant de fois qu'il doit être répété
		    			for (int repeats = 0; repeats < element.repeats; repeats++) {
		    				element.effect.playOn(b);
		    			}
		    		}
		    	} //Le verrou sur le bandeau b est libéré une fois tous les effets joués
		    }
    	};
        //On lance le thread
    	t.start();
    }
}
