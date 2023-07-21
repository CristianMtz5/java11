package com.example.reporteadorBackEnd.Service.Xml.XmlFactura;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Signature;
import java.util.Base64;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.ssl.PKCS8Key;
import org.w3c.dom.Document;

public class CadenaOriginalService{

    private final String xsltPath = "C:/Users/Propietario/Desktop/reporteadorBackEnd/resources/xslt/cadenaoriginal_4_0.xslt";
	private final String key = "C:/Users/Propietario/Desktop/reporteadorBackEnd/resources/certificados/CSD_EKU9003173C9.key";

	public String getCadenaOriginal(String xmlPath){
        try {
            File xslt = new File(xsltPath);
            StreamSource sourceXsl = new StreamSource(xslt);
            File cfdi = new File(xmlPath);
            StreamSource sourceXml = new StreamSource(cfdi);

            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer(sourceXsl);
            StringWriter out = new StringWriter();

            transformer.transform(sourceXml, new StreamResult(out));
            String cadenaOriginal = out.toString();

            return cadenaOriginal;
        } catch (Exception e) {
            return null;
        }
    }

    public String sellarXml(String passKey, String xmlPath, String xmlSelladoPath){
        try {
			PKCS8Key pkcs8 = new PKCS8Key(Files.readAllBytes(Paths.get(key)), passKey.toCharArray());
			java.security.PrivateKey pk = pkcs8.getPrivateKey();

			Signature signature = Signature.getInstance("SHA256withRSA");
			signature.initSign(pk);
			signature.update(getCadenaOriginal(xmlPath).getBytes("UTF-8"));

			String selloCfdi = new String(Base64.getEncoder().encode(signature.sign()));

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
			Document document = documentBuilder.parse(xmlPath);

			document.getDocumentElement().normalize();
			document.getDocumentElement().setAttribute("Sello", selloCfdi);
		
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(new File(xmlSelladoPath));
			
			transformer.transform(source, result);

			return "oK";
		} catch (Exception e) {
			return null;
		}
    }
}