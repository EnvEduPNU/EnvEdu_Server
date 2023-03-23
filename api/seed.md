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

{% swagger-response status="200: OK" description="측정 데이터 조회" %}
{

&#x20;   "code" : 200

&#x20;   "data" : \[ {"id" : 1 , "mac" : "00:00:00:00:00:00", "hum" : 0.1, "temp" : 0.2, "tur" : 0.3

&#x20;               "ph" : 0.4, "dust" : 0.5, "dox" : 0.6, "co2" : 0.7, "lux" : 0.8, "hum\_EARTH" : 0.9

&#x20;               "pre" : 1.0, "date" : 2023-03-23T01:22:20, "dateString" : "tmp", "location" : "tmp"}

&#x20;                ],&#x20;

&#x20;                \[ ... ], \[ ... ]

}
{% endswagger-response %}

{% swagger-response status="404: Not Found" description="요청 인자가 정확하지 못함" %}

{% endswagger-response %}
{% endswagger %}

{% swagger method="post" path="/user/save" baseUrl="https://tmp" summary="데이터 측정 값 등록" %}
{% swagger-description %}

{% endswagger-description %}

{% swagger-parameter in="body" name="payload" type="String" required="true" %}
센서가 측정한 값을 받아 등록
{% endswagger-parameter %}

{% swagger-response status="200: OK" description="데이터 정상 등록" %}

{% endswagger-response %}

{% swagger-response status="404: Not Found" description="요청 인자가 정확하지 못함" %}

{% endswagger-response %}
{% endswagger %}
