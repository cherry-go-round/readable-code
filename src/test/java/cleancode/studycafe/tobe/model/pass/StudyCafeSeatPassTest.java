package cleancode.studycafe.tobe.model.pass;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StudyCafeSeatPassTest {

    @DisplayName("1인 고정석은 사물함을 이용할 수 있다.")
    @Test
    void fixedSeatCanUseLocker() {
        //given
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 4, 250000, 0.1);

        //when
        boolean passCannotUseLocker = seatPass.cannotUseLocker();

        //then
        assertThat(passCannotUseLocker).isFalse();
    }

    @DisplayName("주 단위 이용권은 사물함을 이용할 수 없다.")
    @Test
    void weeklySeatCannotUseLocker() {
        //given
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.WEEKLY, 1, 60000, 0.0);

        //when
        boolean passCannotUseLocker = seatPass.cannotUseLocker();

        //then
        assertThat(passCannotUseLocker).isTrue();
    }

    @DisplayName("시간 단위 이용권은 사물함을 이용할 수 없다.")
    @Test
    void hourlySeatCannotUseLocker() {
        //given
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.HOURLY, 2,4000,0.0);

        //when
        boolean passCannotUseLocker = seatPass.cannotUseLocker();

        //then
        assertThat(passCannotUseLocker).isTrue();
    }

}
