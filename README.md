# 📊 Stock Portfolio Manager

Spring Boot와 React를 활용하여 개발한 주식 포트폴리오 관리 웹 애플리케이션입니다.

사용자는 회원가입 및 로그인 후 자신만의 주식 포트폴리오를 관리할 수 있으며, 투자 현황과 수익률을 시각적으로 확인할 수 있습니다.

---

## 🚀 Tech Stack

### Backend

* Java 17
* Spring Boot
* Spring Data JPA
* MySQL
* Lombok
* BCrypt Password Encoder

### Frontend

* React
* Axios
* Recharts

---

## ✨ 주요 기능

### 회원 기능

* 회원가입
* 로그인
* 로그아웃
* BCrypt 비밀번호 암호화

### 포트폴리오 관리

* 종목 추가
* 종목 수정
* 종목 삭제
* 종목 검색
* 회원별 포트폴리오 분리 관리

### 투자 분석

* 총 투자금액 계산
* 총 평가금액 계산
* 총 손익 계산
* 수익률 계산
* 수익률 순 정렬

### 데이터 시각화

* 포트폴리오 비중 파이 차트
* 종목별 평가금액 막대 그래프
* 수익률 TOP 3 카드

---

## 📂 프로젝트 구조

stockmanager

├── backend (Spring Boot)

│ ├── controller

│ ├── service

│ ├── repository

│ ├── entity

│ └── dto

│

└── frontend (React)

├── components

├── App.jsx

└── axios API

---

## 📸 Screenshots

프로젝트 화면 캡처 이미지를 추가할 예정

---

## 🎯 향후 개선 예정

* JWT 인증 적용
* 배포 (Render + Vercel)
* 실시간 주가 API 연동
* 반응형 UI 개선
* 포트폴리오 통계 기능 확장

---

## 👨‍💻 개발 목적

Spring Boot와 React를 활용한 Full Stack 웹 개발 역량을 향상시키고,

JPA, REST API, 회원 인증, 데이터 시각화 경험을 쌓기 위해 진행한 개인 프로젝트입니다.
