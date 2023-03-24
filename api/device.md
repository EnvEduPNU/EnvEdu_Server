---
description: EnvEduPNU의 Device API 명세
---

# Device

{% swagger method="get" path="/user/device/{username}" baseUrl="https://tmp" summary="디바이스 조회" %}
{% swagger-description %}

{% endswagger-description %}

{% swagger-parameter in="path" name="username" type="String" required="true" %}
조회 할 디바이스의  유저명
{% endswagger-parameter %}

{% swagger-response status="200: OK" description="해당 유저의 디바이스 리스트" %}
{

&#x20;   //Response

&#x20;   "username" : "username"

&#x20;   "macList" : \["MAC" : "00:00:00:00:00:00:", "MAC" : "00:00:00:00:00:00"]

}
{% endswagger-response %}

{% swagger-response status="400: Bad Request" description="요청 데이터 형식이 정확하지 못함" %}

{% endswagger-response %}

{% swagger-response status="409: Conflict" description="요청 인자가 정확하지 못함" %}

{% endswagger-response %}
{% endswagger %}

{% swagger method="post" path="/user/device" baseUrl="https://tmp" summary="디바이스 수정" %}
{% swagger-description %}

{% endswagger-description %}

{% swagger-parameter in="body" name="username" type="String" required="true" %}
수정 유저명
{% endswagger-parameter %}

{% swagger-parameter in="body" name="MAC" type="String" required="true" %}
수정 맥 주소
{% endswagger-parameter %}

{% swagger-response status="200: OK" description="디바이스 수정" %}

{% endswagger-response %}

{% swagger-response status="400: Bad Request" description="요청 데이터 형식이 정확하지 못함" %}

{% endswagger-response %}

{% swagger-response status="409: Conflict" description="요청 인자가 정확하지 못함" %}

{% endswagger-response %}
{% endswagger %}

{% swagger method="post" path="/manager/device" baseUrl="https://tmp" summary="디바이스 최초 등록" %}
{% swagger-description %}

{% endswagger-description %}

{% swagger-parameter in="body" name="username" type="String" required="true" %}
등록 유저명
{% endswagger-parameter %}

{% swagger-parameter in="body" name="MAC" required="true" type="String" %}
등록 맥 주소
{% endswagger-parameter %}

{% swagger-response status="200: OK" description="사용자 디바이스 추가" %}

{% endswagger-response %}

{% swagger-response status="400: Bad Request" description="요청 데이터 형식이 정확하지 못함" %}

{% endswagger-response %}

{% swagger-response status="409: Conflict" description="요청 인자가 정확하지 못함" %}

{% endswagger-response %}
{% endswagger %}

