package cleancode.studycafe.tobe.model.order;

import cleancode.studycafe.tobe.model.pass.StudyCafePassType;
import cleancode.studycafe.tobe.model.pass.StudyCafeSeatPass;
import cleancode.studycafe.tobe.model.pass.locker.StudyCafeLockerPass;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StudyCafePassOrderTest {

    @DisplayName("할인율이 0인 경우 좌석 이용권 가격이 총 가격이다.")
    @Test
    void zeroDiscountRate() {
        //given
        int price = 60000;
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.WEEKLY, 1, price, 0.0);
        StudyCafePassOrder order = StudyCafePassOrder.of(seatPass, null);

        //when
        int totalPrice = order.getTotalPrice();

        //then
        assertThat(totalPrice).isEqualTo(price);
    }

    @DisplayName("할인율이 0이 아닌 경우 좌석 이용권에 할인을 적용한다.")
    @Test
    void nonZeroDiscountRate() {
        //given
        int price = 100000;
        double discountRate = 0.1;
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.WEEKLY, 2, price, discountRate);
        StudyCafePassOrder order = StudyCafePassOrder.of(seatPass, null);

        //when
        int totalPrice = order.getTotalPrice();
        int expected = (int) (price * (1 - discountRate));

        //then
        assertThat(totalPrice).isEqualTo(expected);
    }

    @DisplayName("사물함을 이용할 경우 사물함 가격을 더하여 계산한다.")
    @Test
    void calculateWithLockerPass() {
        //given
        int seatPassPrice = 700000;
        double discountRate = 0.15;
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 12, seatPassPrice, discountRate);

        int lockerPassPrice = 30000;
        StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(StudyCafePassType.FIXED, 12, lockerPassPrice);

        StudyCafePassOrder order = StudyCafePassOrder.of(seatPass, lockerPass);

        //when
        int totalPrice = order.getTotalPrice();
        int expected = (int) (seatPassPrice * (1 - discountRate)) + lockerPassPrice;

        //then
        assertThat(totalPrice).isEqualTo(expected);
    }
}
