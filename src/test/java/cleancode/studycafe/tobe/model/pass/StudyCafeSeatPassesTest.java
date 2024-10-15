package cleancode.studycafe.tobe.model.pass;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class StudyCafeSeatPassesTest {

    @DisplayName("타입에 알맞은 좌석 이용권을 찾는다.")
    @Test
    void findPassByType() {
        //given
        StudyCafePassType findingType = StudyCafePassType.WEEKLY;

        StudyCafeSeatPass expectedPass = StudyCafeSeatPass.of(findingType, 1, 60000, 0.0);

        StudyCafeSeatPasses passes = StudyCafeSeatPasses.of(List.of(expectedPass));

        //when
        List<StudyCafeSeatPass> result = passes.findPassBy(findingType);

        //then
        assertThat(result).contains(expectedPass);
    }

    @DisplayName("타입이 다른 좌석 이용권은 찾지 않는다.")
    @Test
    void doesNotFindPassByDifferentType() {
        //given
        StudyCafePassType findingType = StudyCafePassType.WEEKLY;
        StudyCafePassType differentType = StudyCafePassType.FIXED;

        StudyCafeSeatPass unexpectedPass = StudyCafeSeatPass.of(differentType, 4, 250000, 0.1);

        StudyCafeSeatPasses passes = StudyCafeSeatPasses.of(List.of(unexpectedPass));

        //when
        List<StudyCafeSeatPass> result = passes.findPassBy(findingType);

        //then
        assertThat(result).doesNotContain(unexpectedPass);
    }

}
