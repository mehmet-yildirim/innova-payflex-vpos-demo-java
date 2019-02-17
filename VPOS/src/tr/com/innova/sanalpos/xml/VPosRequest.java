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

@XmlRootElement(name = "VposRequest")
public class VPosRequest {

    @XmlElement(name = "MerchantId")
    private String merchantId;

    @XmlElement(name = "Password")
    private String passo;

    @XmlElement(name = "TransactionType")
    private String transactionType;

    @XmlElement(name = "TransactionId")
    private String transactionId;

    @XmlElement(name = "CurrencyAmount")
    private String currencyAmount;

    @XmlElement(name = "CurrencyCode")
    private Integer currencyCode;

    @XmlElement(name = "Pan")
    private Long pan;

    @XmlElement(name = "Expiry")
    private String expiry;

    @XmlElement(name = "Cvv")
    private Integer cvv;

    @XmlElement(name = "TransactionDeviceSource")
    private Integer transactionDeviceSource;

    @XmlElement(name = "NumberOfInstallments")
    private Integer numberOfInstallments;

    @XmlElement(name = "ClientIp")
    private String clientIp;

    @XmlElement(name = "Location")
    private Integer location;

    @XmlElement(name = "DeviceType")
    private Integer deviceType;

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public void setPasso(String passo) {
        this.passo = passo;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public void setCurrencyAmount(String currencyAmount) {
        this.currencyAmount = currencyAmount;
    }

    public void setCurrencyCode(Integer currencyCode) {
        this.currencyCode = currencyCode;
    }

    public void setPan(Long pan) {
        this.pan = pan;
    }

    public void setCvv(Integer cvv) {
        this.cvv = cvv;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public void setTransactionDeviceSource(Integer transactionDeviceSource) {
        this.transactionDeviceSource = transactionDeviceSource;
    }

    public void setNumberOfInstallments(Integer numberOfInstallments) {
        this.numberOfInstallments = numberOfInstallments;
    }

    public void setLocation(Integer location) {
        this.location = location;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }
}
