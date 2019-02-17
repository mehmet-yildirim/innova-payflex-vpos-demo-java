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

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "IPaySecure")
public class EnrollmentResponse {

    @XmlElement(name = "Message")
    private Message message;

    @XmlElement(name = "MessageErrorCode")
    private Integer errorCode;

    @XmlElement(name = "ErrorMessage")
    private String errorMessage;

    public static class Message {

        @XmlAttribute(name = "ID")
        private String id;

        @XmlElement(name = "VERes")
        private VERes veRes;

        public String getId() {
            return id;
        }

        public VERes getVeRes() {
            return veRes;
        }

        @Override
        public String toString() {
            return "Message{" +
                    "id='" + id + '\'' +
                    ", veRes=" + veRes +
                    '}';
        }
    }

    public static class VERes {

        @XmlElement(name = "Version")
        private Integer version;

        @XmlElement(name = "Status")
        private String status;

        @XmlElement(name = "PaReq")
        private String paReq;

        @XmlElement(name = "ACSUrl")
        private String acsUrl;

        @XmlElement(name = "TermUrl")
        private String termUrl;

        @XmlElement(name = "MD")
        private String md;

        @XmlElement(name = "ACTUALBRAND")
        private String actualBrand;

        public Integer getVersion() {
            return version;
        }

        public String getStatus() {
            return status;
        }

        public String getPaReq() {
            return paReq;
        }

        public String getAcsUrl() {
            return acsUrl;
        }

        public String getTermUrl() {
            return termUrl;
        }

        public String getMd() {
            return md;
        }

        public String getActualBrand() {
            return actualBrand;
        }

        @Override
        public String toString() {
            return "VERes{" +
                    "version=" + version +
                    ", status='" + status + '\'' +
                    ", paReq='" + paReq + '\'' +
                    ", acsUrl='" + acsUrl + '\'' +
                    ", termUrl='" + termUrl + '\'' +
                    ", md='" + md + '\'' +
                    ", actualBrand='" + actualBrand + '\'' +
                    '}';
        }
    }

    public Message getMessage() {
        return message;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public String toString() {
        return "EnrollmentResponse{" +
                "message=" + message +
                ", errorCode=" + errorCode +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
