package cleancode.studycafe.mission.model;

public class StudyCafePass {

    private final StudyCafePassType passType;
    private final int duration;
    private final int price;
    private final double discountRate;

    private StudyCafePass(StudyCafePassType passType, int duration, int price, double discountRate) {
        this.passType = passType;
        this.duration = duration;
        this.price = price;
        this.discountRate = discountRate;
    }

    public static StudyCafePass of(StudyCafePassType passType, int duration, int price, double discountRate) {
        return new StudyCafePass(passType, duration, price, discountRate);
    }

    public boolean isSamePassType(StudyCafePassType passType) {
        return this.passType == passType;
    }

    public boolean isSamePassTypeAndDuration(StudyCafePassType passType, int duration) {
        return this.passType.equals(passType) && this.duration == duration;
    }

    public int getDiscountPrice() {
        return (int) (price * discountRate);
    }

    public int getDiscountedPrice() {
        return price - getDiscountPrice();
    }

    public String display() {
        return switch (passType) {
            case HOURLY -> String.format("%s시간권 - %d원", duration, price);
            case WEEKLY, FIXED -> String.format("%s주권 - %d원", duration, price);
        };
    }

}
