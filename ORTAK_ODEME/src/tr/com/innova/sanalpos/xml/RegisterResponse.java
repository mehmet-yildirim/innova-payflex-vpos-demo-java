/*
 * Copyright 2018 Innova Bilişim Çözümleri A.Ş. <https://www.innova.com.tr>
 *
 * Türkçe:
 * Apache Lisansı, Sürüm 2.0 (işbu “Lisans”) ile lisanslanan bu dosyayı, işbu lisansla uyumlu olan
 * durumlar dışında kullanamazsınız.
 *
 * Lisansın bir kopyasını http://www.apache.org/licenses/LICENSE-2.0 adresinden temin edebilirsiniz.
 *
 * Yürürlükteki bir yasada belirtilmediği veya yazılı olarak beyan edilmediği sürece, işbu lisans
 * altında dağıtılan yazılım “hiçbir değişiklik yapılmadan” esasıyla dağıtılmış olup, açıkça veya üstü
 * kapalı olarak, HİÇBİR TEMİNAT VEYA KOŞUL İÇERMEZ. Özel dil uygulama izinleri ve işbu lisans
 * altındaki kısıtlamalar için lisansa bakınız.
 *
 * English:
 * Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */

package tr.com.innova.sanalpos.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "RegisterResponse")
public class RegisterResponse {

    @XmlElement(name = "PaymentToken")
    private String paymentToken;

    @XmlElement(name = "CommonPaymentUrl")
    private String commonPaymentUrl;

    @XmlElement(name = "ErrorCode")
    private String errorCode;

    public String getPaymentToken() {
        return paymentToken;
    }

    public String getCommonPaymentUrl() {
        return commonPaymentUrl;
    }

    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String toString() {
        return "RegisterResponse{" +
                "paymentToken='" + paymentToken + '\'' +
                ", commonPaymentUrl='" + commonPaymentUrl + '\'' +
                ", errorCode='" + errorCode + '\'' +
                '}';
    }
}
