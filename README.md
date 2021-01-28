# Hungry Dog

<a href="https://github.com/Nam-Ki-Bok/HungryDog" style="color:#0FA678">GitHub</a>

2020 3-2 문제해결기법 : 기개발된 프로젝트를 리팩토링 하는 수업이다.

리팩토링 완료 : https://github.com/Nam-Ki-Bok/HungryDog-Refactoring

***



## 환경 구축

게임 결과를 데이터베이스에 저장 해 읽어 온다.

따라서 mysql-connector 가 필요한데 자신의 MySQL 버전에 맞는 connector 를 다운받자.

<center><img width="400" alt="mysql connector" src="https://user-images.githubusercontent.com/54533309/93333262-043de180-f85e-11ea-89ce-9d06a5e516ba.png"></center>

다운을 받았다면 위와 같이 **프로젝트 우클릭** &rarr; **Build Path** &rarr; **Configure Build Path...** 를 클릭

<br>

<center><img width="900" alt="Build Path" src="https://user-images.githubusercontent.com/54533309/93333511-61399780-f85e-11ea-8197-0c04bc716471.png"></center>

**Modulepath** 를 클릭 한 뒤 오른쪽 **Add External JARs** 를 클릭 해 자신이 다운받은 mysql-connector ~ .jar 을 추가하자.

그리고 하던대로 하면 끝 !

txt 파일로 게임 결과 저장을 하는 방식에서 MySQL 로 바꾸느라 버그가 있을 수 있으니 이것저것 해보다가 버그있으면 말 좀..

테스트 해보느라 콘솔창에 출력되는게 있는데 무시바람 !
