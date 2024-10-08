package cleancode.studycafe.mission.io;

import cleancode.studycafe.mission.exception.AppException;
import cleancode.studycafe.mission.model.StudyCafePass;
import cleancode.studycafe.mission.model.StudyCafePassType;

import java.util.List;
import java.util.Scanner;

public class InputHandler {

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final String HOURLY = "1";
    private static final String WEEKLY = "2";
    private static final String FIXED = "3";
    private static final String LOCKER_SELECTED = "1";

    public StudyCafePassType getPassTypeSelectingUserAction() {
        String userInput = SCANNER.nextLine();

        if (HOURLY.equals(userInput)) {
            return StudyCafePassType.HOURLY;
        }
        if (WEEKLY.equals(userInput)) {
            return StudyCafePassType.WEEKLY;
        }
        if (FIXED.equals(userInput)) {
            return StudyCafePassType.FIXED;
        }
        throw new AppException("잘못된 입력입니다.");
    }

    public StudyCafePass getSelectPass(List<StudyCafePass> passes) {
        String userInput = SCANNER.nextLine();
        int selectedIndex = Integer.parseInt(userInput) - 1;
        return passes.get(selectedIndex);
    }

    public boolean isLockerSelected() {
        String userInput = SCANNER.nextLine();
        return LOCKER_SELECTED.equals(userInput);
    }

}
