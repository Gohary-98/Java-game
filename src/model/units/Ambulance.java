package model.units;

import exceptions.CannotTreatException;
import exceptions.CitizenAlreadyDeadException;
import exceptions.IncompatibleTargetException;
import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import simulation.Address;
import simulation.Rescuable;

public class Ambulance extends MedicalUnit {

	public Ambulance(String unitID, Address location, int stepsPerCycle,
			WorldListener worldListener) {
		super(unitID, location, stepsPerCycle, worldListener);
	}

	@Override
	public void treat() {
		getTarget().getDisaster().setActive(false);

		Citizen target = (Citizen) getTarget();
		if (target.getHp() == 0) {
			jobsDone();
			return;
		} else if (target.getBloodLoss() > 0) {
			target.setBloodLoss(target.getBloodLoss() - getTreatmentAmount());
			if (target.getBloodLoss() == 0)
				target.setState(CitizenState.RESCUED);
		}

		else if (target.getBloodLoss() == 0)

			heal();

	}

	public void respond(Rescuable r) throws IncompatibleTargetException, CannotTreatException, CitizenAlreadyDeadException {
		if ( r instanceof ResidentialBuilding)
			throw new IncompatibleTargetException(this, r,"Ambulance is incompatible with Building type");
		else {
			Citizen x = (Citizen)r;
			if (x.getBloodLoss()==0) {
				throw new CannotTreatException(this, r,"Unit of type Ambulance Cannot treat Citizen at "+r.getLocation());
			}
			
				
		}
		if (getTarget() != null && ((Citizen) getTarget()).getBloodLoss() > 0
				&& getState() == UnitState.TREATING)
			reactivateDisaster();
		finishRespond(r);
	}

}
