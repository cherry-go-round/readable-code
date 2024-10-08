package cleancode.studycafe.mission;

import cleancode.studycafe.mission.exception.AppException;
import cleancode.studycafe.mission.io.*;
import cleancode.studycafe.mission.model.StudyCafeLockerPass;
import cleancode.studycafe.mission.model.StudyCafePass;
import cleancode.studycafe.mission.model.StudyCafePassType;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class StudyCafePassMachine {

    private static final StudyCafeFileHandler FILE_HANDLER = new StudyCafeFileHandler();
    private final InputHandler inputHandler = new ConsoleInputHandler();
    private final OutputHandler consoleOutputHandler = new ConsoleOutputHandler();

    public void run() {
        try {
            consoleOutputHandler.showWelcomeMessage();
            consoleOutputHandler.showAnnouncement();

            consoleOutputHandler.askPassTypeSelection();
            StudyCafePassType studyCafePassType = inputHandler.getPassTypeSelectingUserAction();

            actBy(studyCafePassType);
        } catch (AppException e) {
            consoleOutputHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            consoleOutputHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    private void actBy(StudyCafePassType studyCafePassType) {
        switch (studyCafePassType) {
            case HOURLY, WEEKLY -> {
                StudyCafePass selectedPass = getStudyCafePassBy(studyCafePassType);
                consoleOutputHandler.showPassOrderSummary(selectedPass);
            }
            case FIXED -> {
                StudyCafePass selectedPass = getStudyCafePassBy(studyCafePassType);

                Optional<StudyCafeLockerPass> lockerPass = findLockerPassBy(selectedPass);
                lockerPass.ifPresentOrElse(
                    askLockerPassAndActBy(selectedPass),
                    () -> consoleOutputHandler.showPassOrderSummary(selectedPass)
                );
            }
        }
    }

    private StudyCafePass getStudyCafePassBy(StudyCafePassType passType) {
        List<StudyCafePass> studyCafePasses = FILE_HANDLER.readStudyCafePasses();
        List<StudyCafePass> filteredPasses = filterStudyCafePassesByType(studyCafePasses, passType);

        consoleOutputHandler.showPassListForSelection(filteredPasses);

        return inputHandler.getSelectPass(filteredPasses);
    }

    private List<StudyCafePass> filterStudyCafePassesByType(List<StudyCafePass> studyCafePasses, StudyCafePassType studyCafePassType) {
        return studyCafePasses.stream()
            .filter(studyCafePass -> studyCafePass.isSamePassType(studyCafePassType))
            .toList();
    }

    private Optional<StudyCafeLockerPass> findLockerPassBy(StudyCafePass studyCafePass) {
        List<StudyCafeLockerPass> lockerPasses = FILE_HANDLER.readLockerPasses();
        return lockerPasses.stream()
            .filter(option -> studyCafePass.isSamePassTypeAndDuration(option.getPassType(), option.getDuration()))
            .findFirst();
    }

    private Consumer<? super StudyCafeLockerPass> askLockerPassAndActBy(StudyCafePass selectedPass) {
        return lockerPass -> {
            consoleOutputHandler.askLockerPass(lockerPass);
            boolean lockerSelection = inputHandler.isLockerSelected();

            if (lockerSelection) {
                consoleOutputHandler.showPassOrderSummaryWithLockerPass(selectedPass, lockerPass);
            } else {
                consoleOutputHandler.showPassOrderSummary(selectedPass);
            }
        };
    }

}
