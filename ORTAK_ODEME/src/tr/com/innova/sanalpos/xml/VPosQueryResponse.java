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

/**
 *
 * Bu nesnede demo amacli olarak yalnizca bir kaç alanın bilgileri alinmistir. Canli ortam implementasyonunda tum alanlar
 * cekilmelidir.
 *
 * Örnek Cevap:
 * <VposQueryResponse xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
 *     <Rc>0051</Rc>
 *     <AuthCode />
 *     <Rrn>835417266857</Rrn>
 *     <Message>Red-Yetersiz Bakiye</Message>
 *     <TransactionId>6ca85a58-944c-4a81-b80b-ded047d861dd</TransactionId>
 *     <PaymentToken>a242fc31-2d82-42be-9def-a9bc011de114</PaymentToken>
 *     <MaskedPan>444677******2795</MaskedPan>
 *     <HostMerchantId>000000000259849</HostMerchantId>
 *     <AmountCode>949</AmountCode>
 *     <Amount>1,54</Amount>
 *     <TransactionType>Sale</TransactionType>
 *     <OrderID>e71fd7bf-ada3-4b9f-81a0-6e308450abe2</OrderID>
 *     <OrderDescription>DENEME</OrderDescription>
 *     <InstallmentCount />
 *     <IsSecure>False</IsSecure>
 *     <AllowNotEnrolledCard>False</AllowNotEnrolledCard>
 *     <SuccessUrl>http://localhost:8082/cp_success.jsp</SuccessUrl>
 *     <FailUrl>http://localhost:8082/cp_fail.jsp</FailUrl>
 *     <RequestLanguage>tr-TR</RequestLanguage>
 *     <CardHoldersName>MEHMET</CardHoldersName>
 *     <ExpireMonth>01</ExpireMonth>
 *     <ExpireYear>2019</ExpireYear>
 *     <BrandNumber>100</BrandNumber>
 *     <HostDate>20181220172114</HostDate>
 *     <CampaignResult />
 * </VposQueryResponse>
 */

@XmlRootElement(name = "VposQueryResponse")
public class VPosQueryResponse {

    @XmlElement(name = "Rc")
    private Integer rc;

    @XmlElement(name = "Message")
    private String message;

    @XmlElement(name = "TransactionId")
    private String transactionId;

    @Override
    public String toString() {
        return "VPosQueryResponse{" +
                "rc=" + rc +
                ", message='" + message + '\'' +
                ", transactionId='" + transactionId + '\'' +
                '}';
    }
}
