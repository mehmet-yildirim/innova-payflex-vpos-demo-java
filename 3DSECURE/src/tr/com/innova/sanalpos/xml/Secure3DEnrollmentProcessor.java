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

import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import tr.com.innova.sanalpos.SanalPOSConstants;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class Secure3DEnrollmentProcessor {

    private static final Logger LOGGER = Logger.getLogger(Secure3DEnrollmentProcessor.class.getName());

    /**
     * Istemciden alinan 3D Secure parametreleri ile bankaya arka planda kayit (enrollment) istegi
     * gonderilerek, elde edilen sonuc sayfaya yansitilir. Sonucun sayfaya yansitilmasi icin
     * genellikle {@link Map} kullanimi yeterlidir.
     * <p>
     * Enrollment istegi HTTP POST metoduyla gerceklestirilerek, donen cevap {@link EnrollmentResponse} nesnesine
     * unmarshal edilir ve elde edilen sonuc {@link Map} olarak donulur.
     * <p>
     * Gerceklestirilen implementasyon demo amaclidir. Canli ortam implementasyonunda uye isyerine saglanan bilgiler
     * sunucu tarafinda saglanmali, son kullanicidan alinan degerler uygun sekilde dogrulanmalidir. Kart bilgilerinin
     * loglama amacli olarak kayit altina alinmasi durumunda PCI-DSS standartlarina uyulmalidir.
     * <p>
     * Bankaya yapilan baglanti kesinlikle TLSv1.2 olarak saglanmali, sertifika ve hostname
     * dogrulamalari devre disi birakilmamalidir. Gerekli durumlarda JDK icerisinde bulunan
     * root CA sertifika dizinine bankadan elde edilecek root sertifikalar keytool vb.
     * araciligi ile import edilmelidir.
     *
     */
    public static Map<String, Object> doEnrollment(HttpServletRequest request) throws IOException {

        SSLConnectionSocketFactory sslConnectionSocketFactory = SSLConnectionSocketFactory.getSystemSocketFactory();

        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setSSLSocketFactory(sslConnectionSocketFactory)
                .build();

        HttpPost httpPost = new HttpPost(SanalPOSConstants.ENROLLMENT_POST_URL_DEMO);


        if (Boolean.valueOf(System.getProperty("useProxy"))) {
            HttpHost proxy = new HttpHost("127.0.0.1", 8589, "http");
            RequestConfig config = RequestConfig.custom()
                    .setProxy(proxy)
                    .build();
            httpPost.setConfig(config);
        }

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("MerchantId", request.getParameter("isyeriNo")));
        params.add(new BasicNameValuePair("MerchantPassword", request.getParameter("isyeriSifre")));
        params.add(new BasicNameValuePair("VerifyEnrollmentRequestId", request.getParameter("islemId")));
        params.add(new BasicNameValuePair("Pan", request.getParameter("kartNo")));
        params.add(new BasicNameValuePair("ExpiryDate", request.getParameter("kartSKT")));
        params.add(new BasicNameValuePair("BrandName", request.getParameter("kartMarka")));
        params.add(new BasicNameValuePair("PurchaseAmount", request.getParameter("tutar")));
        params.add(new BasicNameValuePair("Currency", request.getParameter("tutarKod")));
        params.add(new BasicNameValuePair("SuccessUrl", request.getParameter("successUrl")));
        params.add(new BasicNameValuePair("FailureUrl", request.getParameter("failUrl")));

        String taksit = request.getParameter("taksit");
        if (taksit != null && !taksit.isEmpty() && Integer.valueOf(taksit).compareTo(1) > 0) {
            params.add(new BasicNameValuePair("InstallmentCount", taksit));
        }

        httpPost.setEntity(new UrlEncodedFormEntity(params, StandardCharsets.UTF_8.toString()));

        LOGGER.info("POST istek parametreleri: " + params);

        String response = httpClient.execute(httpPost, new BasicResponseHandler());
        LOGGER.info("POST cevabi: " + response);


        /*
            https://www.owasp.org/index.php/XML_External_Entity_(XXE)_Prevention_Cheat_Sheet
            adresinde verilen XXE ataklarini engellemek amaciyla gelen cevabi guvenli sekilde parse ediyoruz.
         */
        SAXParserFactory spf = SAXParserFactory.newInstance();
        try {
            spf.setFeature("http://xml.org/sax/features/external-general-entities", false);
            spf.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            spf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            Source xmlSource = new SAXSource(spf.newSAXParser().getXMLReader(), new InputSource(new StringReader(response)));
            JAXBContext context = JAXBContext.newInstance(EnrollmentResponse.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            EnrollmentResponse enrollmentResponse = (EnrollmentResponse) unmarshaller.unmarshal(xmlSource);

            LOGGER.info("EnrollmentResponse: " + enrollmentResponse);

            Map<String, Object> responseMap = new HashMap<>();

            responseMap.put("status", enrollmentResponse.getMessage().getVeRes().getStatus());
            responseMap.put("errorCode", enrollmentResponse.getErrorCode());
            responseMap.put("errorMessage", enrollmentResponse.getErrorMessage());
            responseMap.put("PaReq", enrollmentResponse.getMessage().getVeRes().getPaReq());
            responseMap.put("ACSUrl", enrollmentResponse.getMessage().getVeRes().getAcsUrl());
            responseMap.put("TermUrl", enrollmentResponse.getMessage().getVeRes().getTermUrl());
            responseMap.put("MD", enrollmentResponse.getMessage().getVeRes().getMd());

            return responseMap;

        } catch (ParserConfigurationException | SAXException | JAXBException e) {
            e.printStackTrace();
        }

        return new HashMap<>();
    }

}
