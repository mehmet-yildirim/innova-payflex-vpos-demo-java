<%--
  ~ Copyright 2018 Innova Bilişim Çözümleri A.Ş. <https://www.innova.com.tr>
  ~
  ~ Türkçe:
  ~ Apache Lisansı, Sürüm 2.0 (işbu “Lisans”) ile lisanslanan bu dosyayı, işbu lisansla uyumlu olan
  ~ durumlar dışında kullanamazsınız.
  ~
  ~ Lisansın bir kopyasını http://www.apache.org/licenses/LICENSE-2.0 adresinden temin edebilirsiniz.
  ~
  ~ Yürürlükteki bir yasada belirtilmediği veya yazılı olarak beyan edilmediği sürece, işbu lisans
  ~ altında dağıtılan yazılım “hiçbir değişiklik yapılmadan” esasıyla dağıtılmış olup, açıkça veya üstü
  ~ kapalı olarak, HİÇBİR TEMİNAT VEYA KOŞUL İÇERMEZ. Özel dil uygulama izinleri ve işbu lisans
  ~ altındaki kısıtlamalar için lisansa bakınız.
  ~
  ~ English:
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~    you may not use this file except in compliance with the License.
  ~    You may obtain a copy of the License at
  ~
  ~        http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~    Unless required by applicable law or agreed to in writing, software
  ~    distributed under the License is distributed on an "AS IS" BASIS,
  ~    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  ~    See the License for the specific language governing permissions and
  ~    limitations under the License.
  ~
  --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="tr.com.innova.sanalpos.xml.Secure3DEnrollmentProcessor" %>
<%@ page import="java.util.Map" %>
<html>
<head>
    <title>Innova Sanal POS 3D Secure Entegrasyon Demo</title>
</head>
<%
    Map<String, Object> enrollmentData = Secure3DEnrollmentProcessor.doEnrollment(request);
%>
<body>
<% if (enrollmentData.get("status") != null && enrollmentData.get("status").equals("Y")) { // Kart 3D Secure sistemine kayitli demektir.%>
<h1>3D Secure Kayıt işlemi başarılı!</h1>
<form name="f" method="POST" action="<%= enrollmentData.get("ACSUrl") %>">
    <p>3D Secure işlemine devam etmek için "Gönder" butonuna basın.</p>
    <input type="hidden" name="PaReq" value="<%= enrollmentData.get("PaReq") %>"/>
    <input type="hidden" name="TermUrl" value="<%= enrollmentData.get("TermUrl") %>"/>
    <input type="hidden" name="MD" value="<%= enrollmentData.get("MD") %>"/>

    <!-- <noscript> //-->
        <input type="submit" value="Gönder"/>
    <!-- </noscript> //-->
</form>
<script type="text/javascript">
    // Demo amacli olarak otomatik gonderme kapatilmistir. Canli ortamda kullaniminda Javascript araciligiyla
    // otomatik gonderim saglanmali, Javascript desteklemeyen tarayicilar icin <noscript> cozumler de konulmalidir.

    // document.f.submit();
</script>


<% } else { // Diger durumlar hata olarak kabul edilir. %>
<h1>3D Secure Kayıt işlemi başarısız!</h1>
<table>
    <tr>
        <td valign="top">Status</td>
        <td>
            <%= enrollmentData.get("status") %>
        </td>
    </tr>
    <tr>
        <td valign="top">Hata kodu</td>
        <td>
            <%= enrollmentData.get("errorCode") %>
        </td>
    </tr>
    <tr>
        <td valign="top">Hata mesajı</td>
        <td>
            <%= enrollmentData.get("errorMessage") %>
        </td>
    </tr>
    <tr>
        <td colspan="2">
            <hr/>
        </td>
    </tr>
    <tr>
        <td></td>
        <td>
            <a href="index.jsp">Ana Sayfaya Dön</a>
        </td>
    </tr>
</table>
<% } %>
</body>
</html>
