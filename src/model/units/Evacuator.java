package model.units;

import exceptions.BuildingAlreadyCollapsedException;
import exceptions.CannotTreatException;
import exceptions.IncompatibleTargetException;
import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import simulation.Address;
import simulation.Rescuable;

public class Evacuator extends PoliceUnit {

	public Evacuator(String unitID, Address location, int stepsPerCycle,
			WorldListener worldListener, int maxCapacity) {
		super(unitID, location, stepsPerCycle, worldListener, maxCapacity);

	}
	public void respond(Rescuable r) throws IncompatibleTargetException, CannotTreatException, BuildingAlreadyCollapsedException {
		if ( r instanceof Citizen)
			throw new IncompatibleTargetException(this, r,"Evactuator is incompatible with Citizen type");
		else {
			ResidentialBuilding x = (ResidentialBuilding)r;
			if (x.getFoundationDamage()==0)
				throw new CannotTreatException(this,r,"unit of type Evactuator Cannot treat Building at "+r.getLocation());
				
		}
		if (getTarget() != null && ((Citizen) getTarget()).getBloodLoss() > 0
				&& getState() == UnitState.TREATING)
			reactivateDisaster();
		finishRespond(r);
	}

	@Override
	public void treat() {
		ResidentialBuilding target = (ResidentialBuilding) getTarget();
		if (target.getStructuralIntegrity() == 0
				|| target.getOccupants().size() == 0) {
			jobsDone();
			return;
		}

		for (int i = 0; getPassengers().size() != getMaxCapacity()
				&& i < target.getOccupants().size(); i++) {
			getPassengers().add(target.getOccupants().remove(i));
			i--;
		}

		setDistanceToBase(target.getLocation().getX()
				+ target.getLocation().getY());

	}

}
