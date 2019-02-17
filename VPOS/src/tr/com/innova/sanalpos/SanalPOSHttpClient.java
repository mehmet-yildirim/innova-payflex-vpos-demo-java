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
import tr.com.innova.sanalpos.xml.VPosResponse;

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
import java.util.List;
import java.util.logging.Logger;

public class SanalPOSHttpClient {

    private static final Logger LOGGER = Logger.getLogger(SanalPOSHttpClient.class.getName());

    /**
     * {@link tr.com.innova.sanalpos.xml.VPosRequest} nesnesinden hazirlanan XML mesajini
     * arkaplanda bankaya gondererek cevap elde edilir.
     * <p>
     * Apache HttpComponents kutuphanesi kullanilan bu implementasyon demo amaclidir.
     * Canli ortamda {@link tr.com.innova.sanalpos.xml.VPosRequest} ile elde edilen ham XML
     * metni bankaya POST edilerek elde edilen cevap bir POJO olarak islenmelidir.
     * <p>
     * Bankaya yapilan baglanti kesinlikle TLSv1.2 olarak saglanmali, sertifika ve hostname
     * dogrulamalari devre disi birakilmamalidir. Gerekli durumlarda JDK icerisinde bulunan
     * root CA sertifika dizinine bankadan elde edilecek root sertifikalar keytool vb.
     * raciligi ile import edilmelidir.
     * <p>
     * Cevap formati icin bu pakette saglanan dokumanlari inceleyiniz.
     */
    public static VPosResponse postSatis(String xml) throws IOException, JAXBException {

        SSLConnectionSocketFactory sslConnectionSocketFactory = SSLConnectionSocketFactory.getSystemSocketFactory();

        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setSSLSocketFactory(sslConnectionSocketFactory)
                .build();

        HttpPost httpPost = new HttpPost(SanalPOSConstants.XML_POST_URL_DEMO);


        if (Boolean.valueOf(System.getProperty("useProxy"))) {
            HttpHost proxy = new HttpHost("127.0.0.1", 8589, "http");
            RequestConfig config = RequestConfig.custom()
                    .setProxy(proxy)
                    .build();
            httpPost.setConfig(config);
        }

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("prmstr", xml));
        httpPost.setEntity(new UrlEncodedFormEntity(params, StandardCharsets.UTF_8.toString()));

        LOGGER.info("POST istegi: " + xml);
        String response = httpClient.execute(httpPost, new BasicResponseHandler());
        LOGGER.info("POST cevabi: " + response);

        // Gelen cevabi JAXB ile POJO haline getir.

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
            JAXBContext context = JAXBContext.newInstance(VPosResponse.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            return (VPosResponse) unmarshaller.unmarshal(xmlSource);
        } catch (ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }

        return null;
    }

}
