package model.units;

import exceptions.BuildingAlreadyCollapsedException;
import exceptions.CannotTreatException;
import exceptions.IncompatibleTargetException;
import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import simulation.Address;
import simulation.Rescuable;

public class FireTruck extends FireUnit {

	public FireTruck(String unitID, Address location, int stepsPerCycle,
			WorldListener worldListener) {
		super(unitID, location, stepsPerCycle, worldListener);
	}
	public void respond(Rescuable r) throws IncompatibleTargetException, CannotTreatException, BuildingAlreadyCollapsedException {
		if ( r instanceof Citizen)
			throw new IncompatibleTargetException(this, r,"FireTruck is incompatible with citizen type");
		else {
			ResidentialBuilding x = (ResidentialBuilding)r;
			if (x.getFireDamage()==0) {
				throw new CannotTreatException(this, r,"unit of type FireTruck Cannot treat Building at "+r.getLocation());
			}
			
		}
		if (getTarget() != null && ((Citizen) getTarget()).getBloodLoss() > 0
				&& getState() == UnitState.TREATING)
			reactivateDisaster();
		finishRespond(r);
	}


	@Override
	public void treat() {
		getTarget().getDisaster().setActive(false);

		ResidentialBuilding target = (ResidentialBuilding) getTarget();
		if (target.getStructuralIntegrity() == 0) {
			jobsDone();
			return;
		} else if (target.getFireDamage() > 0)

			target.setFireDamage(target.getFireDamage() - 10);

		if (target.getFireDamage() == 0)

			jobsDone();

	}

}
