# ##Selenide (UI 자동 테스트 툴)

>firefox 제외 하면 수동으로 해당 브라우저에 대한 추가 파일 설치 필요
>[다운로드](http://selenium-release.storage.googleapis.com/index.html)
>~~[safari 의 경우 2.45 에서 다운 받아 설치 진행](https://code.google.com/p/selenium/wiki/SafariDriver) 하여야 한다.~~ Safari의 경우 지원 하지 않는 듯 합니다.

###장점
- 디테일한 설정 가능
- Hidden 값이나, 스크립트의 validata를 우회한 호출 등 범용적 기능 처리 가능

###단점
- 수정하려는 값에 대한 element나 selector로 접근 가능한 값을 알아야 함
- 코드로 접근하기 때문에 사용 방법이 직관 적이지 못함 - [Selenium IDE](http://hanmomhanda.github.io/2015/09/23/Selenium-꿀팁/)를 이용할경우 UI로 처리 가능(firefox 만 가능)

###사용팁
- seletor로 element중 한 개를 선택 할 경우, jQuery의 [.eq()](https://api.jquery.com/eq/)가 동작 하지 않는 경우 $(String cssSelector, int index) 을 사용 하여 처리
- 옵션 없이 대기가 필요할 경우 Thread.sleep 를 이용

###GUITAR vs Selenide
1. GUITAR의 경우 테스트하는 사람이 특정 필드명을 알 필요 없이 진행 가능하다. 단, 정해진 기능 테스트만 가능
2. Selenide의 경우 상세한 설정이 가능하나 개발자가 아닌 사람이 접근하기는 어렵다.

>**테스터가 개발 지식이 없을 경우 GUITAR를 추천** 
>**개발 지식이 있다면 Selenide를 추천**
>>Taint Analysis(오염 분석) 툴로 사용이 가능하다.
>>서비스에서 Front의 체크사항을 무시하고 테스트 가능


참고 내용 : [Selenide-꿀팁](http://hanmomhanda.github.io/2016/01/27/Selenide-꿀팁/)
