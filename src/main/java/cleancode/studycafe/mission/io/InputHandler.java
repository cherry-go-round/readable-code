package cleancode.studycafe.mission.io;

import cleancode.studycafe.mission.model.StudyCafePass;
import cleancode.studycafe.mission.model.StudyCafePassType;

import java.util.List;

public interface InputHandler {

    public StudyCafePassType getPassTypeSelectingUserAction();

    public StudyCafePass getSelectPass(List<StudyCafePass> passes);

    public boolean isLockerSelected();

}
