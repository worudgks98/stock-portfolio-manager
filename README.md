# 📈 Stock Portfolio Manager

개인 투자자를 위한 주식 포트폴리오 관리 웹 애플리케이션

## 🚀 프로젝트 소개

사용자가 보유한 주식을 등록하고 관리하며, 실시간 주가를 반영하여 수익률을 확인할 수 있는 포트폴리오 관리 서비스입니다.

한국 주식과 미국 주식을 모두 지원하며, 미국 주식은 실시간 환율을 적용하여 원화 기준으로 평가됩니다.

---

## 🛠 기술 스택

### Backend

* Java 17
* Spring Boot
* Spring Data JPA
* Spring Security
* MySQL
* Lombok

### Frontend

* React
* Axios

### API

* Yahoo Finance API
* USD/KRW 환율 조회

---

## ✨ 주요 기능

### 회원 기능

* 회원가입
* 로그인
* 비밀번호 암호화(BCrypt)

### 포트폴리오 관리

* 종목 추가
* 종목 수정
* 종목 삭제
* 회원별 포트폴리오 분리 관리

### 종목 검색 및 정렬

* 종목명 검색
* 수익률 순 정렬

### 실시간 주가 조회

* Yahoo Finance API 연동
* 미국 주식 실시간 가격 조회
* 한국 주식 실시간 가격 조회

### 환율 적용

* USD/KRW 환율 조회
* 미국 주식 가격을 원화(KRW)로 자동 변환

### 수익 분석

* 투자금액 계산
* 평가금액 계산
* 손익 계산
* 수익률 계산

---

## 📊 지원 종목 예시

### 미국 주식

* Apple (AAPL)
* Nvidia (NVDA)
* Tesla (TSLA)
* Microsoft (MSFT)

### 한국 주식

* 삼성전자 (005930.KS)
* SK하이닉스 (000660.KS)
* NAVER (035420.KS)

---

## 🏗 프로젝트 구조

```text
React
 ├─ 회원가입
 ├─ 로그인
 ├─ 종목 관리
 └─ 포트폴리오 조회

Spring Boot
 ├─ Member
 ├─ Stock
 ├─ Yahoo Finance API
 └─ MySQL

Database
 ├─ member
 └─ stock
```

---

## 📌 구현 내용

* Spring Security 기반 비밀번호 암호화
* React + Spring Boot REST API 통신
* JPA 연관관계(Member ↔ Stock)
* Yahoo Finance API 연동
* 미국/한국 주식 통합 관리
* 실시간 환율 반영 원화 평가 시스템

---

## 🔥 향후 개선 예정

* 포트폴리오 대시보드
* 총 투자금액 / 총 평가금액 카드
* 수익률 차트
* 현재가 자동 갱신
* 관심종목 기능
* 배포(AWS 또는 Docker)

```
```
