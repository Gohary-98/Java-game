package model.events;

import exceptions.BuildingAlreadyCollapsedException;
import exceptions.CannotTreatException;
import exceptions.CitizenAlreadyDeadException;
import exceptions.IncompatibleTargetException;
import simulation.Rescuable;

public interface SOSResponder {
public void respond(Rescuable r) throws CannotTreatException, IncompatibleTargetException, CitizenAlreadyDeadException, BuildingAlreadyCollapsedException ;
}
