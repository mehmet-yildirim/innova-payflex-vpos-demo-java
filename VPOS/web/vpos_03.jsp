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
<%@ page import="tr.com.innova.sanalpos.SanalPOSHttpClient" %>
<%@ page import="tr.com.innova.sanalpos.xml.VPosResponse" %>
<%@ page import="javax.xml.bind.JAXBException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    VPosResponse vPosResponse = null;
    try {
        vPosResponse = SanalPOSHttpClient.postSatis(request.getParameter("prmstr"));
    } catch (JAXBException e) {
        e.printStackTrace();
    }
%>
<html>
<head>
    <title>Innova Sanal POS Entegrasyon Demo</title>

</head>
<body>
<table>
    <tr>
        <td valign="top">Cevap Kodu</td>
        <td>
                <%= vPosResponse.getResultCode() %>
        </td>
    </tr>
    <tr>
        <td valign="top">Cevap Detayı</td>
        <td>
            <%= vPosResponse.getResultDetail() %>
        </td>
    </tr>
    <tr>
        <td colspan="2"><hr/></td>
    </tr>
    <tr>
        <td></td>
        <td>
            Tam mesaj detayı için log çıktısını ve POJO dönüşümü için <code>SanalPOSHttpClient.java</code> dosyasını inceleyebilirsiniz.
            <br/>
            <a href="index.jsp">Ana Sayfaya Dön</a>
        </td>
    </tr>

</table>
</body>
</html>
