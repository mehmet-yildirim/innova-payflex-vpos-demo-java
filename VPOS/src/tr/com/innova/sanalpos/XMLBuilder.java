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

package tr.com.innova.sanalpos;

import tr.com.innova.sanalpos.xml.EnumTransactionTypes;
import tr.com.innova.sanalpos.xml.VPosRequest;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

public class XMLBuilder {

    /**
     * Satis islemi icin gerekli bilgilerle XML mesajini olusturur.
     *
     * Diger mesaj tipleri icin bu pakette saglanan dokumanlari inceleyiniz.
     *
     * Yapilan implementasyon XML olusturma icin demo amaclidir,
     * canli ortam kullaniminda uye isyerine saglanan bilgiler sunucu tarafinda saglanmali,
     * son kullanicidan alinan degerler uygun sekilde dogrulanmalidir. Kart bilgilerinin
     * loglama amacli olarak kayit altina alinmasi durumunda PCI-DSS standartlarina uyulmalidir.
     *
     */
    public static String createSatisXML(HttpServletRequest request) {

        try {

            VPosRequest vPosRequest = new VPosRequest();
            vPosRequest.setMerchantId(request.getParameter("isyeriNo"));
            vPosRequest.setPasso(request.getParameter("isyeriSifre"));
            vPosRequest.setTransactionType(EnumTransactionTypes.SALE.getText());
            vPosRequest.setTransactionId(request.getParameter("siparisNo"));
            vPosRequest.setPan(Long.valueOf(request.getParameter("kartNo")));

            vPosRequest.setExpiry(String.format("%04d%02d", Integer.valueOf(request.getParameter("kartYil")),
                    Integer.valueOf(request.getParameter("kartAy"))));

            vPosRequest.setCvv(Integer.valueOf(request.getParameter("kartCvv")));

            vPosRequest.setCurrencyAmount(request.getParameter("tutar"));
            vPosRequest.setCurrencyCode(Integer.valueOf(request.getParameter("tutarKod")));

            vPosRequest.setTransactionDeviceSource(0);
            vPosRequest.setClientIp(Inet4Address.getLocalHost().getHostAddress());
            vPosRequest.setLocation(1);
            vPosRequest.setDeviceType(1);

            // Taksit degeri opsiyonel olabilir, bu durumda eger deger varsa iletilmelidir.
            if(!request.getParameter("taksit").isEmpty()) {
                vPosRequest.setNumberOfInstallments(Integer.valueOf(request.getParameter("taksit")));
            }

            StringWriter sw = new StringWriter();

            // JAXB ile POJO -> XML String donusumu
            JAXBContext context = JAXBContext.newInstance(VPosRequest.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, StandardCharsets.UTF_8.toString());

            marshaller.marshal(vPosRequest, sw);

            return sw.toString();

        } catch (JAXBException | UnknownHostException e) {
            e.printStackTrace();
        }

        return "";
    }

}
