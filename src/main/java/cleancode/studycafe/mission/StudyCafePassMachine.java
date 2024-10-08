package cleancode.studycafe.mission;

import cleancode.studycafe.mission.exception.AppException;
import cleancode.studycafe.mission.io.InputHandler;
import cleancode.studycafe.mission.io.OutputHandler;
import cleancode.studycafe.mission.io.StudyCafeFileHandler;
import cleancode.studycafe.mission.model.StudyCafeLockerPass;
import cleancode.studycafe.mission.model.StudyCafePass;
import cleancode.studycafe.mission.model.StudyCafePassType;

import java.util.List;

public class StudyCafePassMachine {

    private static final StudyCafeFileHandler FILE_HANDLER = new StudyCafeFileHandler();
    private final InputHandler inputHandler = new InputHandler();
    private final OutputHandler outputHandler = new OutputHandler();

    public void run() {
        try {
            outputHandler.showWelcomeMessage();
            outputHandler.showAnnouncement();

            outputHandler.askPassTypeSelection();
            StudyCafePassType studyCafePassType = inputHandler.getPassTypeSelectingUserAction();

            if (studyCafePassType == StudyCafePassType.HOURLY) {
                StudyCafePass selectedPass = getStudyCafePassBy(StudyCafePassType.HOURLY);
                outputHandler.showPassOrderSummary(selectedPass, null);
            } else if (studyCafePassType == StudyCafePassType.WEEKLY) {
                StudyCafePass selectedPass = getStudyCafePassBy(StudyCafePassType.WEEKLY);
                outputHandler.showPassOrderSummary(selectedPass, null);
            } else if (studyCafePassType == StudyCafePassType.FIXED) {
                StudyCafePass selectedPass = getStudyCafePassBy(StudyCafePassType.FIXED);

                List<StudyCafeLockerPass> lockerPasses = FILE_HANDLER.readLockerPasses();
                StudyCafeLockerPass lockerPass = lockerPasses.stream()
                    .filter(option -> selectedPass.isSamePassTypeAndDuration(option.getPassType(), option.getDuration()))
                    .findFirst()
                    .orElse(null);

                if (lockerPass != null) {
                    outputHandler.askLockerPass(lockerPass);
                    boolean lockerSelection = inputHandler.isLockerSelected();

                    if (lockerSelection) {
                        outputHandler.showPassOrderSummary(selectedPass, lockerPass);
                    } else {
                        outputHandler.showPassOrderSummary(selectedPass, null);
                    }

                    return;
                }

                outputHandler.showPassOrderSummary(selectedPass, null);
            }
        } catch (AppException e) {
            outputHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            outputHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    private StudyCafePass getStudyCafePassBy(StudyCafePassType passType) {
        List<StudyCafePass> studyCafePasses = FILE_HANDLER.readStudyCafePasses();
        List<StudyCafePass> filteredPasses = filterStudyCafePassesByType(studyCafePasses, passType);

        outputHandler.showPassListForSelection(filteredPasses);

        return inputHandler.getSelectPass(filteredPasses);
    }

    private List<StudyCafePass> filterStudyCafePassesByType(List<StudyCafePass> studyCafePasses, StudyCafePassType studyCafePassType) {
        return studyCafePasses.stream()
            .filter(studyCafePass -> studyCafePass.isSamePassType(studyCafePassType))
            .toList();
    }

}
