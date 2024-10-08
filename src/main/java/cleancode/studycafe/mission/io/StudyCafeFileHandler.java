package cleancode.studycafe.mission.io;

import cleancode.studycafe.mission.model.StudyCafeLockerPass;
import cleancode.studycafe.mission.model.StudyCafePass;
import cleancode.studycafe.mission.model.StudyCafePassType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class StudyCafeFileHandler {

    private static final String PASS_LIST_PATH = "src/main/resources/cleancode/studycafe/pass-list.csv";
    private static final String LOCKER_PATH = "src/main/resources/cleancode/studycafe/locker.csv";

    public List<StudyCafePass> readStudyCafePasses() {
        try {
            List<String> lines = readAllLinesFrom(Paths.get(PASS_LIST_PATH));

            return getStudyCafePasses(lines);
        } catch (IOException e) {
            throw new RuntimeException("파일을 읽는데 실패했습니다.", e);
        }
    }

    public List<StudyCafeLockerPass> readLockerPasses() {
        try {
            List<String> lines = readAllLinesFrom(Paths.get(LOCKER_PATH));
            List<StudyCafeLockerPass> lockerPasses = new ArrayList<>();
            for (String line : lines) {
                String[] values = line.split(",");
                StudyCafePassType studyCafePassType = StudyCafePassType.valueOf(values[0]);
                int duration = Integer.parseInt(values[1]);
                int price = Integer.parseInt(values[2]);

                StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(studyCafePassType, duration, price);
                lockerPasses.add(lockerPass);
            }

            return lockerPasses;
        } catch (IOException e) {
            throw new RuntimeException("파일을 읽는데 실패했습니다.", e);
        }
    }

    private List<StudyCafePass> getStudyCafePasses(List<String> lines) {
        return lines.stream()
            .map(line -> line.split(","))
            .map(values -> {
                StudyCafePassType studyCafePassType = StudyCafePassType.valueOf(values[0]);
                int duration = Integer.parseInt(values[1]);
                int price = Integer.parseInt(values[2]);
                double discountRate = Double.parseDouble(values[3]);
                return StudyCafePass.of(studyCafePassType, duration, price, discountRate);
            })
            .toList();
    }

    private List<String> readAllLinesFrom(Path path) throws IOException {
        return Files.readAllLines(path);
    }

}
