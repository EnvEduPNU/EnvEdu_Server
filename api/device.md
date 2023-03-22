---
description: EnvEduPNU의 Device API 명세
---

# Device

{% swagger method="post" path="/manager/device" baseUrl="https://tmp" summary="" %}
{% swagger-description %}

{% endswagger-description %}

{% swagger-parameter in="body" name="username" type="String" required="true" %}
등록 유저 명
{% endswagger-parameter %}

{% swagger-parameter in="body" name="MAC" required="true" type="String" %}
등록 맥 주소
{% endswagger-parameter %}

{% swagger-response status="200: OK" description="사용자 디바이스 추가" %}

{% endswagger-response %}
{% endswagger %}
