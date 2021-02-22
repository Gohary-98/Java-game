package model.units;

import exceptions.BuildingAlreadyCollapsedException;
import exceptions.CannotTreatException;
import exceptions.IncompatibleTargetException;
import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import simulation.Address;
import simulation.Rescuable;

public class GasControlUnit extends FireUnit {

	public GasControlUnit(String unitID, Address location, int stepsPerCycle,
			WorldListener worldListener) {
		super(unitID, location, stepsPerCycle, worldListener);
	}
	public void respond(Rescuable r) throws IncompatibleTargetException, CannotTreatException, BuildingAlreadyCollapsedException {
		if ( r instanceof Citizen)
			throw new IncompatibleTargetException(this, r,"GasControlUnit is incompatible with Citizen type");
		else {
			ResidentialBuilding x = (ResidentialBuilding)r;
			if (x.getGasLevel()==0) {
				throw new CannotTreatException(this, r,"unit of type GasControlUnit Cannot treat Building at "+r.getLocation());
			}
			
		}
		if (getTarget() != null && ((Citizen) getTarget()).getBloodLoss() > 0
				&& getState() == UnitState.TREATING)
			reactivateDisaster();
		finishRespond(r);
	}

	public void treat() {
		getTarget().getDisaster().setActive(false);

		ResidentialBuilding target = (ResidentialBuilding) getTarget();
		if (target.getStructuralIntegrity() == 0) {
			jobsDone();
			return;
		} else if (target.getGasLevel() > 0) 
			target.setGasLevel(target.getGasLevel() - 10);

		if (target.getGasLevel() == 0)
			jobsDone();

	}

}
