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

    public static final StudyCafeFileHandler FILE_HANDLER = new StudyCafeFileHandler();
    private final InputHandler inputHandler = new InputHandler();
    private final OutputHandler outputHandler = new OutputHandler();

    public void run() {
        try {
            outputHandler.showWelcomeMessage();
            outputHandler.showAnnouncement();

            outputHandler.askPassTypeSelection();
            StudyCafePassType studyCafePassType = inputHandler.getPassTypeSelectingUserAction();

            if (studyCafePassType == StudyCafePassType.HOURLY) {
                List<StudyCafePass> studyCafePasses = FILE_HANDLER.readStudyCafePasses();
                List<StudyCafePass> hourlyPasses = filterStudyCafePassesByType(studyCafePasses, StudyCafePassType.HOURLY);
                outputHandler.showPassListForSelection(hourlyPasses);
                StudyCafePass selectedPass = inputHandler.getSelectPass(hourlyPasses);
                outputHandler.showPassOrderSummary(selectedPass, null);
            } else if (studyCafePassType == StudyCafePassType.WEEKLY) {
                List<StudyCafePass> studyCafePasses = FILE_HANDLER.readStudyCafePasses();
                List<StudyCafePass> weeklyPasses = filterStudyCafePassesByType(studyCafePasses, StudyCafePassType.WEEKLY);
                outputHandler.showPassListForSelection(weeklyPasses);
                StudyCafePass selectedPass = inputHandler.getSelectPass(weeklyPasses);
                outputHandler.showPassOrderSummary(selectedPass, null);
            } else if (studyCafePassType == StudyCafePassType.FIXED) {
                List<StudyCafePass> studyCafePasses = FILE_HANDLER.readStudyCafePasses();
                List<StudyCafePass> fixedPasses = filterStudyCafePassesByType(studyCafePasses, StudyCafePassType.FIXED);
                outputHandler.showPassListForSelection(fixedPasses);
                StudyCafePass selectedPass = inputHandler.getSelectPass(fixedPasses);

                List<StudyCafeLockerPass> lockerPasses = FILE_HANDLER.readLockerPasses();
                StudyCafeLockerPass lockerPass = lockerPasses.stream()
                    .filter(option -> selectedPass.isSamePassTypeAndDuration(option.getPassType(), option.getDuration()))
                    .findFirst()
                    .orElse(null);

                boolean lockerSelection = false;
                if (lockerPass != null) {
                    outputHandler.askLockerPass(lockerPass);
                    lockerSelection = inputHandler.isLockerSelected();
                }

                if (lockerSelection) {
                    outputHandler.showPassOrderSummary(selectedPass, lockerPass);
                } else {
                    outputHandler.showPassOrderSummary(selectedPass, null);
                }
            }
        } catch (AppException e) {
            outputHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            outputHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    private List<StudyCafePass> filterStudyCafePassesByType(List<StudyCafePass> studyCafePasses, StudyCafePassType studyCafePassType) {
        return studyCafePasses.stream()
            .filter(studyCafePass -> studyCafePass.isSamePassType(studyCafePassType))
            .toList();
    }

}
