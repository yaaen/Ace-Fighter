package GUI;

import java.util.ArrayList;
import Entities.*;
public interface iView {
	void updateCutscenes(ArrayList<Cutscene> cutscenes);
	
	void updateCutsceneEvents(Cutscene cutscene);
}
