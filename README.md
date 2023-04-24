# jwp-racingcar

1. 자동차 경주 코드 가져오기
- [x] 코드 가져오기

2. 웹 요청/응답 구현하기
- [x] /plays(post)에 대한 기능 생성
  - [x] json request 받아 객체 생성
  - [x] 결과 json 형태로 response
- [x] /plays(get)에 대한 기능 생성
  - [x] 호출 시 json 형태로 결과 반환

3. DB 연동하기
- [x] H2 DB를 연동
- [x] 자동차 경주 게임의 플레이 이력을 DB에 저장
  - 저장해야 할 정보
    - 플레이 횟수(trialCount)
    - 플레이어 별 최종 이동 거리 (이름(name), 최종 위치(position))
    - 우승자(winners)
    - 플레이한 날짜/시간
- [x] 전체 결과 정보 가져오기
  - 우승자
  - 플레이어 별 최종 이동 거리 
