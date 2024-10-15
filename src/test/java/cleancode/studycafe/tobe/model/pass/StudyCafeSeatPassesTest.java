package cleancode.studycafe.tobe.model.pass;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class StudyCafeSeatPassesTest {

    @DisplayName("타입에 알맞은 좌석 이용권만을 찾는다.")
    @Test
    void findPassByType() {
        //given
        StudyCafePassType findingType = StudyCafePassType.WEEKLY;

        StudyCafeSeatPass findingPass1 = StudyCafeSeatPass.of(findingType, 1, 60000, 0.0);
        StudyCafeSeatPass findingPass2 = StudyCafeSeatPass.of(findingType, 2, 100000, 0.1);
        StudyCafeSeatPass unexpectedPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 4, 250000, 0.1);

        StudyCafeSeatPasses passes = StudyCafeSeatPasses.of(List.of(findingPass1, findingPass2, unexpectedPass));

        //when
        List<StudyCafeSeatPass> result = passes.findPassBy(findingType);

        //then
        assertThat(result).contains(findingPass1);
        assertThat(result).contains(findingPass2);
        assertThat(result).doesNotContain(unexpectedPass);
    }
}
