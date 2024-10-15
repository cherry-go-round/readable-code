package cleancode.studycafe.tobe.model.pass.locker;

import cleancode.studycafe.tobe.model.pass.StudyCafePassType;
import cleancode.studycafe.tobe.model.pass.StudyCafeSeatPass;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class StudyCafeLockerPassesTest {

    @DisplayName("좌석 이용권의 타입과 기한이 같은 사물함 이용권을 찾는다.")
    @Test
    void findLockerPassBySeatPass() {
        //given
        StudyCafePassType type = StudyCafePassType.FIXED;
        int duration = 4;

        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(type, duration, 250000, 0.1);
        StudyCafeLockerPass expectedPass = StudyCafeLockerPass.of(type, duration, 10000);

        StudyCafeLockerPasses lockerPasses = StudyCafeLockerPasses.of(List.of(expectedPass));

        //when
        Optional<StudyCafeLockerPass> result = lockerPasses.findLockerPassBy(seatPass);

        //then
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get()).isEqualTo(expectedPass);
    }

    @DisplayName("좌석 이용권과 타입이 다른 사물함 이용권은 찾을 수 없다.")
    @Test
    void doesNotFindLockerPassBySeatPassWithDifferentType() {
        //given
        StudyCafePassType type = StudyCafePassType.FIXED;
        StudyCafePassType differentType = StudyCafePassType.HOURLY;
        int duration = 4;

        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(type, duration, 250000, 0.1);
        StudyCafeLockerPass unexpectedPass = StudyCafeLockerPass.of(differentType, duration, 10000);

        StudyCafeLockerPasses lockerPasses = StudyCafeLockerPasses.of(List.of(unexpectedPass));

        //when
        Optional<StudyCafeLockerPass> result = lockerPasses.findLockerPassBy(seatPass);

        //then
        assertThat(result).isNotPresent();
    }

    @DisplayName("좌석 이용권과 기한이 다른 사물함 이용권은 찾을 수 없다.")
    @Test
    void doesNotFindLockerPassBySeatPassWithDifferentDuration() {
        //given
        StudyCafePassType type = StudyCafePassType.FIXED;
        int duration = 4;
        int differentDuration = 12;

        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(type, duration, 250000, 0.1);
        StudyCafeLockerPass unexpectedPass = StudyCafeLockerPass.of(type, differentDuration, 30000);

        StudyCafeLockerPasses lockerPasses = StudyCafeLockerPasses.of(List.of(unexpectedPass));

        //when
        Optional<StudyCafeLockerPass> result = lockerPasses.findLockerPassBy(seatPass);

        //then
        assertThat(result).isNotPresent();
    }
}
