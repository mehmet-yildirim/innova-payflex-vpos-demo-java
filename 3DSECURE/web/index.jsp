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
<%@ page import="java.util.UUID" %>
<%@ page import="java.net.InetAddress" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Innova Sanal POS 3D Secure Entegrasyon Demo</title>
</head>
<body>
<h1>VPOS 3D Secure Entegrasyon Demo</h1>
<form method="POST" action="secure3d_02.jsp">
    <table>
        <tr>
            <td colspan="2"><b>Üye İşyeri Bilgileri</b></td>
        </tr>
        <tr>
            <td><label for="isyeriNo">İşyeri No</label></td>
            <td><input id="isyeriNo" name="isyeriNo" value="000000000259849"/></td>
        </tr>
        <tr>
            <td><label for="isyeriSifre">İşyeri Şifre</label></td>
            <td><input id="isyeriSifre" name="isyeriSifre" value="TEST123"/></td>
        </tr>
        <tr>
            <td colspan="2">&nbsp;</td>
        </tr>
        <tr>
            <td colspan="2"><b>Kart Bilgileri</b></td>
        </tr>
        <tr>
            <td><label for="kartNo">Kart Numarası</label></td>
            <td><input id="kartNo" name="kartNo" value="4446770711152795" type="number"/></td>
        </tr>
        <tr>
            <td><label for="kartAy">Kart SKT</label></td>
            <td>
                <input id="kartAy" name="kartSKT" value="1901" size="4" placeholder="YYAA"/>
            </td>
        </tr>
        <tr>
            <td><label for="kartMarka">Kart Markası</label></td>
            <td><input id="kartMarka" name="kartMarka" value="100"/>&nbsp;100: VISA, 200: MASTERCARD</td>
        </tr>
        <tr>
            <td colspan="2">&nbsp;</td>
        </tr>
        <tr>
            <td><label for="tutar">Tutar</label></td>
            <td><input id="tutar" name="tutar" value="1.54"/></td>
        </tr>
        <tr>
            <td><label for="tutarKod">Tutar Birim Kodu</label></td>
            <td><input id="tutarKod" name="tutarKod" value="949" size="3" type="number" placeholder="949"/></td>
        </tr>
        <tr>
            <td><label for="taksit">Taksit</label></td>
            <td><input id="taksit" name="taksit" size="4" type="number"/></td>
        </tr>
        <tr>
            <td><label for="islemId">İşlem ID</label></td>
            <td><input id="islemId" name="islemId" value="<%= UUID.randomUUID().toString() %>"/></td>
        </tr>
        <tr>
            <td colspan="2">&nbsp;</td>
        </tr>
        <tr>
            <td><label for="successUrl">Başarılı Sonuç URL</label></td>
            <td><input id="successUrl" name="successUrl" value="<%= String.format("http://localhost:%d/secure3d_success.jsp", request.getServerPort()) %>"/></td>
        </tr>
        <tr>
            <td><label for="failUrl">Başarısız Sonuç URL</label></td>
            <td><input id="failUrl" name="failUrl" value="<%= String.format("http://localhost:%d/secure3d_fail.jsp", request.getServerPort()) %>"/></td>
        </tr>
        <tr>
            <td colspan="2">
                <hr/>
            </td>
        </tr>
        <tr>
            <td></td>
            <td><input type="submit" value="Kayıt Kontrol"/></td>
        </tr>

    </table>

</form>
</body>
</html>
