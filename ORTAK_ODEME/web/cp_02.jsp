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
<%@ page import="tr.com.innova.sanalpos.OrtakOdemeProcessor" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Innova Sanal POS Ortak Ödeme Entegrasyon Demo</title>
</head>
<body>
<% Map<String, Object> registerData = OrtakOdemeProcessor.doRegisterTransaction(request); %>
<h1>İşlem Kayıt Sonucu</h1>
<table>
    <% if(registerData.get("errorCode") != null) { %>
    <tr>
        <td>Hata Kodu</td>
        <td><%= registerData.get("errorCode") %></td>
    </tr>
    <% } else { %>
    <% String url = String.format("%s?Ptkn=%s", registerData.get("cpUrl"), registerData.get("ptkn")); %>
    <tr>

        <td>İşleme devam etmek için <a href="<%= url%>"><%= url%></a> adresine gidiniz. </td>
    </tr>
    <% } %>
</table>
</body>
</html>
