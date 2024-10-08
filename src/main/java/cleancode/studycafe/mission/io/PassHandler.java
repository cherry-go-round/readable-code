package cleancode.studycafe.mission.io;

import cleancode.studycafe.mission.model.StudyCafeLockerPass;
import cleancode.studycafe.mission.model.StudyCafePass;

import java.util.List;

public interface PassHandler {

    public List<StudyCafePass> readStudyCafePasses();

    public List<StudyCafeLockerPass> readLockerPasses();

}
