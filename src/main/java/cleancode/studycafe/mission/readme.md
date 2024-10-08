# Day 7 미션
[Readable Code: 읽기 좋은 코드를 작성하는 사고법](https://www.inflearn.com/course/readable-code-%EC%9D%BD%EA%B8%B0%EC%A2%8B%EC%9D%80%EC%BD%94%EB%93%9C-%EC%9E%91%EC%84%B1%EC%82%AC%EA%B3%A0%EB%B2%95/dashboard)

섹션 7. 리팩토링 연습

## 주요 변경 사항
* io에 인터페이스를 만들고 DI 적용 - 콘솔이 아닌 다른 방법으로 I/O 가능, csv 파일 대신 데이터베이스로 대체 가능
* for 문 대신 stream API 적용
* if 문 대신 switch 활용 (enum에 잘 어울림)
* null 체크 대신 Optional 활용

## 해결하지 못한 문제

### View와 비즈니스 로직의 강결합

* OutputHandler에서 가격을 계산하고 있음.
* StudtyCafePass에서 계산을 해주고 있긴 하지만 어쨌든 사물함의 가격을 더하는 로직이 있음.
* 그러나 스터디 이용권과 사물함 이용권은 변하기 어려운 도메인이라고 생각하고 우선 두도록 함.
* 다시 말해 더 복잡한 계산이 필요하지 않을 것 같아 우선 계산 객체를 따로 두지 않음.

### 사물함 가격 문제

* 사물함 이용권의 null 체크를 피하기 위하여 다른 메소드로 분리하였음.
* 오버로딩을 활용할까 했지만 혼란을 피하기 위해 다른 메소드 명을 사용함.
* 더 좋은 방법이 있는지 고민됨.

### 추상화 레벨 문제

* 추상화 레벨을 최대한 맞추는 게 좋다는 것은 알고 있지만 적용하기 쉽지 않았음.
* 특히

    ```
    lockerPass.ifPresentOrElse(
                    askLockerPassAndActBy(selectedPass),
                    () -> outputHandler.showPassOrderSummary(selectedPass)
                );
    ```

    이런 경우 두 메소드의 추상화 레벨이 다르지만 람다로 간단하게 표현할 수 있는 부분을 굳이 메소드로 분리해야 하나 의문이 들었음.
