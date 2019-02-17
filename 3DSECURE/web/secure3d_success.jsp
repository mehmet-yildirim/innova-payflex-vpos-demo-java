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
<%@ page import="java.util.Map" %>
<html>
<head>
    <title>Innova Sanal POS 3D Secure Entegrasyon Demo</title>
</head>
<body>
<h1>3D Secure kart doğrulaması başarılı!</h1>
<table>
    <tr>
        <td colspan="2"><b>Başarılı Sonuç URL sayfasına dönülen POST verileri aşağıdadır.</b></td>
    </tr>
    <% for(Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) { %>
    <tr>
        <td valign="top"><%= entry.getKey() %></td>
        <td>
            <%= entry.getValue()[0] %>
        </td>
    </tr>
    <% } // for%>
    <tr>
        <td colspan="2">
            <hr/>
        </td>
    </tr>
    <tr>
        <td></td>
        <td>
            Yukarıda iletilen parametrelerle Payflex MPI ISD'de açıklanan yönergelere uyularak VPOS API üzerinden
            ön otorizasyon veya satış işlemine tamamlanmalıdır. VPOS API entegrasyon demosu için
            <code>VPOS</code> adlı proje örneğini inceleyiniz.
            <br>
            <a href="index.jsp">Ana Sayfaya Dön</a>
        </td>
    </tr>
</table>
</body>
</html>
