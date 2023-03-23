---
description: EnvEduPNU의 User API 명세
---

# User

{% swagger method="post" path="/register" baseUrl="http://tmp" summary="일반 사용자 계정 등록" %}
{% swagger-description %}

{% endswagger-description %}

{% swagger-parameter in="body" required="true" name="username" type="String" %}
등록할 유저명
{% endswagger-parameter %}

{% swagger-parameter in="body" required="true" name="password" type="String" %}
등록할 유저 패스워드
{% endswagger-parameter %}

{% swagger-parameter in="body" required="true" name="email" type="String" %}
등록할 유저 이메일
{% endswagger-parameter %}

{% swagger-response status="200: OK" description="유저 등록 성공" %}
{

&#x20;   "code" : 200,

&#x20;   "data" : null

}
{% endswagger-response %}

{% swagger-response status="400: Bad Request" description="요청 데이터 형식이 정확하지 못함" %}

{% endswagger-response %}

{% swagger-response status="409: Conflict" description="요청 인자가 정확하지 못함" %}

{% endswagger-response %}
{% endswagger %}

{% swagger method="post" path="/register/educator" baseUrl="http://tmp" summary="교육자 계정 등록" %}
{% swagger-description %}

{% endswagger-description %}

{% swagger-parameter in="body" name="username" type="String" required="true" %}
등록할 유저명
{% endswagger-parameter %}

{% swagger-parameter in="body" type="String" name="password" required="true" %}
등록할 유저 패스워드
{% endswagger-parameter %}

{% swagger-parameter in="body" type="String" name="email" required="true" %}
등록할 유저 이메일
{% endswagger-parameter %}

{% swagger-response status="200: OK" description="유저 등록 성공" %}
{

&#x20;   "code" : 200,

&#x20;   "data" : null

}
{% endswagger-response %}

{% swagger-response status="400: Bad Request" description="요청 데이터 형식이 정확하지 못함" %}

{% endswagger-response %}

{% swagger-response status="409: Conflict" description="요청 인자가 정확하지 못함" %}

{% endswagger-response %}
{% endswagger %}

{% swagger method="post" path="/educator/student/add" baseUrl="http://tmp" summary="교육자 계정에 학생 계정 등록" %}
{% swagger-description %}

{% endswagger-description %}

{% swagger-parameter in="body" type="String" name="username" %}
등록할 교육자명
{% endswagger-parameter %}

{% swagger-parameter in="body" type="String" name="student" %}
등록할 학생명
{% endswagger-parameter %}

{% swagger-response status="200: OK" description="** 반환값이 없습니다." %}

{% endswagger-response %}

{% swagger-response status="400: Bad Request" description="요청 데이터 형식이 정확하지 못함" %}

{% endswagger-response %}

{% swagger-response status="409: Conflict" description="요청 인자가 정확하지 못함" %}

{% endswagger-response %}
{% endswagger %}

{% swagger method="patch" path="/register/auth" baseUrl="http://tmp" summary="이메일 인증절차 진행" %}
{% swagger-description %}

{% endswagger-description %}

{% swagger-parameter in="body" name="username" type="String" required="true" %}
인증할 유저명
{% endswagger-parameter %}

{% swagger-parameter in="body" name="email" type="String" required="true" %}
인증할 이메일
{% endswagger-parameter %}

{% swagger-parameter in="body" name="authNum" type="Integer" required="true" %}
인증번호
{% endswagger-parameter %}

{% swagger-response status="200: OK" description="이메일 인증 성공" %}
{

&#x20;   "code" : 200,

&#x20;   "data" : null

}
{% endswagger-response %}

{% swagger-response status="400: Bad Request" description="요청 데이터 형식이 정확하지 못함" %}

{% endswagger-response %}

{% swagger-response status="409: Conflict" description="요청 인자가 정확하지 못함" %}

{% endswagger-response %}
{% endswagger %}

{% swagger method="post" path="/register/resend" baseUrl="http://tmp" summary="이메일 인증요청 재전송" %}
{% swagger-description %}

{% endswagger-description %}

{% swagger-parameter in="body" name="email" type="String" %}
인증메일을 재전송할 이메일 주소
{% endswagger-parameter %}

{% swagger-response status="200: OK" description="인증메일 전송 완료" %}
{

&#x20;   "code" : 200,

&#x20;   "data" : null

}
{% endswagger-response %}

{% swagger-response status="400: Bad Request" description="요청 데이터 형식이 정확하지 못함" %}

{% endswagger-response %}

{% swagger-response status="409: Conflict" description="요청 인자가 정확하지 못함" %}

{% endswagger-response %}
{% endswagger %}
