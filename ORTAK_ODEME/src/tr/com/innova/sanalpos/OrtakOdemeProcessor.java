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
import tr.com.innova.sanalpos.xml.RegisterResponse;
import tr.com.innova.sanalpos.xml.VPosQueryResponse;

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
import java.util.*;
import java.util.logging.Logger;

public class OrtakOdemeProcessor {

    private static final Logger LOGGER = Logger.getLogger(OrtakOdemeProcessor.class.getName());

    /**
     * Ortak odeme sayfasina ait Ptkn degerini elde etmek uzere arka planda HTTPS istegi yapilmaktadir.
     * <p>
     * Yapilan implementasyon demo amaclidir. Canli ortam için bu entegrasyona gecilecek parametreler son
     * kullanici tarafindan <u>alinmamalidir</u>.
     * <p>
     * Bankaya yapilan baglanti kesinlikle TLSv1.2 olarak saglanmali, sertifika ve hostname
     * dogrulamalari devre disi birakilmamalidir. Gerekli durumlarda JDK icerisinde bulunan
     * root CA sertifika dizinine bankadan elde edilecek root sertifikalar keytool vb.
     * araciligi ile import edilmelidir.
     */
    public static Map<String, Object> doRegisterTransaction(HttpServletRequest request) throws IOException {

        SSLConnectionSocketFactory sslConnectionSocketFactory = SSLConnectionSocketFactory.getSystemSocketFactory();

        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setSSLSocketFactory(sslConnectionSocketFactory)
                .build();

        HttpPost httpPost = new HttpPost(SanalPOSConstants.REGISTER_POST_URL_DEMO);


        if (Boolean.valueOf(System.getProperty("useProxy"))) {
            HttpHost proxy = new HttpHost("127.0.0.1", 8589, "http");
            RequestConfig config = RequestConfig.custom()
                    .setProxy(proxy)
                    .build();
            httpPost.setConfig(config);
        }

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("HostMerchantId", request.getParameter("isyeriNo")));
        params.add(new BasicNameValuePair("MerchantPassword", request.getParameter("isyeriSifre")));
        params.add(new BasicNameValuePair("OrderId", request.getParameter("siparisNo")));
        params.add(new BasicNameValuePair("OrderDescription", request.getParameter("siparisAciklama")));
        params.add(new BasicNameValuePair("TransactionId", UUID.randomUUID().toString()));
        params.add(new BasicNameValuePair("Amount", request.getParameter("tutar")));
        params.add(new BasicNameValuePair("AmountCode", request.getParameter("tutarKod")));
        params.add(new BasicNameValuePair("SuccessUrl", request.getParameter("successUrl")));
        params.add(new BasicNameValuePair("FailUrl", request.getParameter("failUrl")));

        // satis gonderimi orneklenmistir.
        params.add(new BasicNameValuePair("TransactionType", "Sale"));

        params.add(new BasicNameValuePair("IsSecure", (request.getParameter("isSecure") != null) ? "true" : "false"));
        params.add(new BasicNameValuePair("AllowNotEnrolledCard", (request.getParameter("allowNotEnrolled") != null) ? "true" : "false"));


        httpPost.setEntity(new UrlEncodedFormEntity(params, StandardCharsets.UTF_8.toString()));

        // XML cevap elde etmek icin Accept header eklenir.
        httpPost.addHeader("Accept", "application/xml");

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
            JAXBContext context = JAXBContext.newInstance(RegisterResponse.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            RegisterResponse registerResponse = (RegisterResponse) unmarshaller.unmarshal(xmlSource);

            LOGGER.info("RegisterResponse: " + registerResponse);

            Map<String, Object> responseMap = new HashMap<>();

            responseMap.put("ptkn", registerResponse.getPaymentToken());
            responseMap.put("errorCode", registerResponse.getErrorCode());
            responseMap.put("cpUrl", registerResponse.getCommonPaymentUrl());

            return responseMap;

        } catch (ParserConfigurationException | SAXException | JAXBException e) {
            e.printStackTrace();
        }

        return new HashMap<>();

    }

    /**
     * Basarili URL donusunde Ortak Odeme islem sorgulama uygulanarak islemin validasyonu saglanir, boylece islemin
     * gercekten Ortak Odeme sistemi uzerinde gerceklestigi teyit edilir.
     *
     */
    public static String doVposQuery(HttpServletRequest request) throws IOException {

        SSLConnectionSocketFactory sslConnectionSocketFactory = SSLConnectionSocketFactory.getSystemSocketFactory();

        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setSSLSocketFactory(sslConnectionSocketFactory)
                .build();

        HttpPost httpPost = new HttpPost(SanalPOSConstants.VPOS_QUERY_POST_URL_DEMO);


        if (Boolean.valueOf(System.getProperty("useProxy"))) {
            HttpHost proxy = new HttpHost("127.0.0.1", 8589, "http");
            RequestConfig config = RequestConfig.custom()
                    .setProxy(proxy)
                    .build();
            httpPost.setConfig(config);
        }

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("HostMerchantId", "000000000259849"));        // Bu deger internal olarak sabittir.
        params.add(new BasicNameValuePair("Password", "TEST123"));                      // Bu deger internal olarak sabittir.
        params.add(new BasicNameValuePair("PaymentToken", request.getParameter("PaymentToken")));
        params.add(new BasicNameValuePair("TransactionId", request.getParameter("TransactionId")));

        httpPost.setEntity(new UrlEncodedFormEntity(params, StandardCharsets.UTF_8.toString()));

        // XML cevap elde etmek icin Accept header eklenir.
        httpPost.addHeader("Accept", "application/xml");

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
            JAXBContext context = JAXBContext.newInstance(VPosQueryResponse.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            VPosQueryResponse vPosQueryResponse = (VPosQueryResponse) unmarshaller.unmarshal(xmlSource);

            LOGGER.info("VPosQueryResponse: " + vPosQueryResponse);
            return vPosQueryResponse.toString();

        } catch (ParserConfigurationException | SAXException | JAXBException e) {
            e.printStackTrace();
        }

        return "";
    }

}
