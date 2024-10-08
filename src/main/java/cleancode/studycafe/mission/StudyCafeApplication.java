package cleancode.studycafe.mission;

import cleancode.studycafe.mission.io.ConsoleInputHandler;
import cleancode.studycafe.mission.io.ConsoleOutputHandler;
import cleancode.studycafe.mission.io.StudyCafeFileHandler;

public class StudyCafeApplication {

    public static void main(String[] args) {
        StudyCafePassMachine studyCafePassMachine = new StudyCafePassMachine(
            new ConsoleInputHandler(),
            new ConsoleOutputHandler(),
            new StudyCafeFileHandler()
        );
        studyCafePassMachine.run();
    }

}
