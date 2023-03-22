---
description: EnvEduPNU의 Seed API 명세
---

# Seed

{% swagger method="get" path="/user/fetch" baseUrl="https://tmp" summary="기준 시간 내 해당 유저의 측정 데이터 조회 " %}
{% swagger-description %}

{% endswagger-description %}

{% swagger-parameter in="query" name="startDate" type="String" required="true" %}
"yyyy-MM-dd HH:mm:ss" 형식의 조회 시작 시간
{% endswagger-parameter %}

{% swagger-parameter in="query" name="endDate" type="String" required="true" %}
"yyyy-MM-dd HH:mm:ss" 형식의 조회 종료 시간
{% endswagger-parameter %}

{% swagger-parameter in="query" name="username" type="String" required="true" %}
조회할 유저명
{% endswagger-parameter %}
{% endswagger %}

{% swagger method="post" path="/user/save" baseUrl="https://tmp" summary="데이터 측정 값 등록" %}
{% swagger-description %}

{% endswagger-description %}

{% swagger-parameter in="body" name="payload" type="String" required="true" %}
센서가 측정한 값을 받아 등록
{% endswagger-parameter %}

{% swagger-response status="200: OK" description="데이터 정상 등록" %}

{% endswagger-response %}
{% endswagger %}
