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
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Innova Sanal POS Entegrasyon Demo</title>

</head>
<body>
<h1>VPOS XML Entegrasyon Demo</h1>
<form method="POST" action="vpos_02.jsp">
    <table>
        <tr>
            <td colspan="2"><b>Üye İşyeri Bilgileri</b></td>
        </tr>
        <tr>
            <td><label for="isyeriNo">İşyeri No</label></td>
            <td><input id="isyeriNo" name="isyeriNo" value="000000000259849"/></td>
        </tr>
        <tr>
            <td><label for="isyeriAd">İşyeri Adı</label></td>
            <td><input id="isyeriAd" name="isyeriAd" value="TEST MERCHANT"/></td>
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
                <input id="kartAy" name="kartAy" value="01" size="2" placeholder="AA"/> /
                <input id="kartYil" name="kartYil" value="2019" size="4" placeholder="YYYY"/>
            </td>
        </tr>
        <tr>
            <td><label for="kartCvv">Kart CVV</label></td>
            <td><input id="kartCvv" name="kartCvv" value="297" size="3" type="number"/></td>
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
            <td><label for="siparisNo">Sipariş No</label></td>
            <td><input id="siparisNo" name="siparisNo" value="<%= UUID.randomUUID().toString() %>"/></td>
        </tr>
        <tr>
            <td colspan="2">
                <hr/>
            </td>
        </tr>
        <tr>
            <td></td>
            <td><input type="submit" value="XML'i oluştur"/></td>
        </tr>

    </table>

</form>
</body>
</html>
