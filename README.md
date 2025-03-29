# N+1 테스트

## 테스트 데이터 삽입
1. 컨테이너 실행
```bash
docker-compose up -d
```
2. 프로젝트 시작하여 스키마 자동 생성
3. CsvDataGenerator.java를 이용하여 원하는 데이터만큼 데이터 생성
> comment는 post의 x10 만큼 생성됨
```java
// 아래 데이터를 변경하여 조절
static final int TOTAL_POSTS = 1000000; 
```
4. import_mysql.sh 실행
```bash
chmod +x ./import_mysql.sh
./import_mysql.sh
```
5. api 응답 테스트
* `/`: findAll
* `/fetch`: fetch join + findAll
* `/test`: 응답 테스트 api