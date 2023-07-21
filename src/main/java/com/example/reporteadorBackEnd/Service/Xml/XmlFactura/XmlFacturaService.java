package com.example.reporteadorBackEnd.Service.Xml.XmlFactura;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.example.reporteadorBackEnd.Entity.Xml.ComprobanteXmlEntity;
import com.example.reporteadorBackEnd.Entity.Xml.ConceptosXmlEntity;
import com.example.reporteadorBackEnd.Entity.Xml.TrasladoOrRetencionXmlEntity;
import com.example.reporteadorBackEnd.Service.Xml.TrasladoOrRetencionService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

@Service
public class XmlFacturaService {

    @Autowired
    TrasladoOrRetencionService trasladoOrRetencionService;

    public String formarXml(@PathVariable("id") Long id) {

        List<TrasladoOrRetencionXmlEntity> trasladoOrRetencionId = trasladoOrRetencionService.getByIdComprobanteXml(id);
        try {
            String directorio = System.getProperty("user.dir")  + "/xml/" + id;
            FileUtils.forceMkdir(new File(directorio));

            String aux = directorio + "/" + id  + ".xml"; 

            String nameSpace = "http://www.sat.gob.mx/cfd/4";
            String prefijo = "cfdi:";

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
            DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element comprobante = document.createElementNS(nameSpace, prefijo + "Comprobante");
            document.appendChild(comprobante);
            comprobante.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            comprobante.setAttribute("xmlns:implocal", "http://www.sat.gob.mx/implocal");
            comprobante.setAttribute("xsi:schemaLocation",
                    "http://www.sat.gob.mx/cfd/4 http://www.sat.gob.mx/sitio_internet/cfd/4/cfdv40.xsd http://www.sat.gob.mx/implocal http://www.sat.gob.mx/sitio_internet/cfd/implocal/implocal.xsd");

            TrasladoOrRetencionXmlEntity trasladoOrRetencionXmlEntity = trasladoOrRetencionId.get(0);
            ComprobanteXmlEntity comprobanteXmlEntity = trasladoOrRetencionXmlEntity.getIdComprobante();

            comprobante.setAttribute("Version", comprobanteXmlEntity.getVersion());
            comprobante.setAttribute("Fecha", comprobanteXmlEntity.getFecha().toString());
            // comprobante.setAttribute("Sello", comprobanteXmlEntity.getSello());
            comprobante.setAttribute("NoCertificado", comprobanteXmlEntity.getNoCertificado());
            comprobante.setAttribute("Certificado", comprobanteXmlEntity.getCertificado());
            comprobante.setAttribute("SubTotal", comprobanteXmlEntity.getSubTotal().toString());
            comprobante.setAttribute("Moneda", comprobanteXmlEntity.getIdMoneda().getId());
            comprobante.setAttribute("Total", comprobanteXmlEntity.getTotal().toString());
            comprobante.setAttribute("TipoDeComprobante", comprobanteXmlEntity.getIdTipoComprobante().getId().toString());
            comprobante.setAttribute("Exportacion", comprobanteXmlEntity.getIdExportacion().getId().toString());
            comprobante.setAttribute("MetodoPago", comprobanteXmlEntity.getIdMetodoPago().getId().toString());
            comprobante.setAttribute("LugarExpedicion", comprobanteXmlEntity.getIdCodigoPostal().getId().toString());
            comprobante.setAttribute("FormaPago", comprobanteXmlEntity.getIdFormaPago().getId().toString());

            Element emisor = document.createElement(prefijo + "Emisor");
            comprobante.appendChild(emisor);

            emisor.setAttribute("Rfc", comprobanteXmlEntity.getIdEmpresa().getRfc());
            emisor.setAttribute("Nombre", comprobanteXmlEntity.getIdEmpresa().getNombre());
            emisor.setAttribute("RegimenFiscal", comprobanteXmlEntity.getIdEmpresa().getIdRegimenFiscal().getId());

            Element receptor = document.createElement(prefijo + "Receptor");
            comprobante.appendChild(receptor);

            receptor.setAttribute("Rfc", comprobanteXmlEntity.getIdCliente().getRfc());
            receptor.setAttribute("Nombre", comprobanteXmlEntity.getIdCliente().getNombre());
            receptor.setAttribute("DomicilioFiscalReceptor",
                    comprobanteXmlEntity.getIdCliente().getCodigoPostal());
            receptor.setAttribute("RegimenFiscalReceptor",
                    comprobanteXmlEntity.getIdCliente().getIdRegimenFiscal().getId());
            receptor.setAttribute("UsoCFDI", comprobanteXmlEntity.getIdCliente().getIdUsoCfdi().getId());

            Element conceptos = document.createElement(prefijo + "Conceptos");
            comprobante.appendChild(conceptos);

            for(int i=0; i<trasladoOrRetencionId.size(); i++){
                TrasladoOrRetencionXmlEntity trasladoOrRetencionConceptos = trasladoOrRetencionId.get(i);
                ConceptosXmlEntity conceptosXml = trasladoOrRetencionConceptos.getIdConcepto();

                Element concepto = document.createElement(prefijo + "Concepto");
                conceptos.appendChild(concepto);
                concepto.setAttribute("ClaveProdServ", conceptosXml.getIdClaveProdServ().getId());
                concepto.setAttribute("Cantidad", conceptosXml.getCantidad().toString());
                concepto.setAttribute("ClaveUnidad", conceptosXml.getIdClaveUnidad().getId());
                concepto.setAttribute("Unidad", conceptosXml.getUnidad());
                concepto.setAttribute("Descripcion", conceptosXml.getDescripcion());
                concepto.setAttribute("ValorUnitario", conceptosXml.getValorUnitario().toString());
                concepto.setAttribute("Importe", conceptosXml.getImporte().toString());
                concepto.setAttribute("ObjetoImp", conceptosXml.getIdObjetoImp().getId());

                Element impuestos = document.createElement(prefijo + "Impuestos");
                concepto.appendChild(impuestos);
                Element traslados = document.createElement(prefijo + "Traslados");
                impuestos.appendChild(traslados);
                Element traslado = document.createElement(prefijo + "Traslado");
                traslados.appendChild(traslado);

                TrasladoOrRetencionXmlEntity trasladoXmlEntity = trasladoOrRetencionId.get(i);

                traslado.setAttribute("Base", trasladoXmlEntity.getBase().toString());
                traslado.setAttribute("Impuesto", trasladoXmlEntity.getIdImpuesto().getId());
                traslado.setAttribute("TipoFactor", trasladoXmlEntity.getIdTipoFactor().getId());
                traslado.setAttribute("TasaOCuota", trasladoXmlEntity.getIdTasaCuota().getValorMaximo().toString());
                traslado.setAttribute("Importe", trasladoXmlEntity.getImporte().toString());
            }

            String tipoComprobante = comprobanteXmlEntity.getIdTipoComprobante().getId().toString();

            if (tipoComprobante != "T" || tipoComprobante != "P") {
                Element nodoImpuestos = document.createElement(prefijo + "Impuestos");
                comprobante.appendChild(nodoImpuestos);

                String impuestosTrasladado = trasladoOrRetencionService.sumaImporteTraslado(id).get(0);                
                String impuestosRetenidos = trasladoOrRetencionService.sumaImporteRetenidos(id).get(0);
                
                if(impuestosTrasladado != null){
                    nodoImpuestos.setAttribute("TotalImpuestosTrasladados", impuestosTrasladado);
                }

                if(impuestosRetenidos != null){
                    nodoImpuestos.setAttribute("TotalImpuestosRetenidos", impuestosRetenidos);
                }

                List<String> array = trasladoOrRetencionService.sumaAndgroupByTasaCuota(id);
                List<String> array2 = trasladoOrRetencionService.innerJoinTasaCuotaId(id);

                Element trasladosNodoImpuestos = document.createElement(prefijo + "Traslados");
                nodoImpuestos.appendChild(trasladosNodoImpuestos);

                for(int i=0; i<array.size(); i++){
                    String result [] = array.get(i).split(",");
                    Element trasladoChild = document.createElement(prefijo + "Traslado");
                    trasladosNodoImpuestos.appendChild(trasladoChild);
                    
                    trasladoChild.setAttribute("Base", result[6]);
                    trasladoChild.setAttribute("Impuesto", result[1]);
                    trasladoChild.setAttribute("TipoFactor", result[2]);
                    
                    String attrTipoFac = trasladoChild.getAttribute("TipoFactor");
                    attrTipoFac.indexOf("Exento");
                    if(attrTipoFac.indexOf("Exento") == -1){
                        trasladoChild.setAttribute("TasaOCuota", array2.get(i));
                        trasladoChild.setAttribute("Importe", result[5]);
                    }
                }   
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(aux));
            transformer.transform(source, result);

            String passKey = comprobanteXmlEntity.getIdEmpresa().getPassKey();

            String xmlSellado = directorio + "/cfdiSellado.xml";

            CadenaOriginalService cadenaOriginalService = new CadenaOriginalService();
            cadenaOriginalService.sellarXml(passKey, aux, xmlSellado);
            
            return "ok";
        } catch (Exception e) {
            return null;
        }
    }

    public ResponseEntity<Resource> exportToPdf(String route){

        String jasperRoute = "/resources/Jasper/reportEdited.jasper";
        //"C:/Users/Propietario/Desktop/reporteadorBackEnd/resources/Jasper/reportEdited.jasper";

        String imageRoute = "C:/Users/Propietario/Desktop/reporteadorBackEnd/resources/Jasper/factura.png";
        
        // List<TrasladoOrRetencionXmlEntity> trasladoOrRetencionId = trasladoOrRetencionService.getByIdComprobanteXml(id);
        try {
            File file = new File(route);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(file);

            document.getDocumentElement().normalize();
        
            Map<String, Object> parametros = new HashMap<>();
            // String fileJasper = "C:/Users/Propietario/Desktop/reporteadorBackEnd/resources/Jasper/report_1.jrxml";
            File fileJasper = ResourceUtils.getFile(jasperRoute);
            JasperReport jasper = (JasperReport) JRLoader.loadObject(fileJasper);

            File imagenFac = new File(imageRoute);
            parametros.put("image", imagenFac.getAbsolutePath());

            NodeList listComprobante = document.getElementsByTagName("cfdi:Comprobante");
            Node nodeComprobante = listComprobante.item(0);
            Element atribsComprobante = (Element) nodeComprobante;
            String total = atribsComprobante.getAttribute("Total");

            parametros.put("numSerie", atribsComprobante.getAttribute("NoCertificado"));
            parametros.put("codPostal", atribsComprobante.getAttribute("LugarExpedicion"));
            String fecha = atribsComprobante.getAttribute("Fecha").replace("T", ", ");
            parametros.put("fechaYhora", fecha);
            parametros.put("tipoComprobante", atribsComprobante.getAttribute("TipoDeComprobante"));
            List<String> moneda = trasladoOrRetencionService.getDescripcionFromMoneda(atribsComprobante.getAttribute("Moneda"));
            parametros.put("moneda", moneda.get(0));
            List<String> formaPago = trasladoOrRetencionService.getDescripcionFromFormaPago(atribsComprobante.getAttribute("FormaPago"));
            parametros.put("formaPago", formaPago.get(0));
            List<String> metodoPago = trasladoOrRetencionService.getDescripcionFromMetodoPago(atribsComprobante.getAttribute("MetodoPago"));
            parametros.put("metodoPago", metodoPago.get(0));
            parametros.put("numCertificado", atribsComprobante.getAttribute("NoCertificado"));
            parametros.put("subTotal", atribsComprobante.getAttribute("SubTotal"));
            parametros.put("total", atribsComprobante.getAttribute("Total"));

            NodeList listEmisor = document.getElementsByTagName("cfdi:Emisor");
            Node nodeEmisor = listEmisor.item(0);
            Element atribsEmisor = (Element) nodeEmisor;
            
            String rfcEmisor = atribsEmisor.getAttribute("Rfc");
            parametros.put("rfcEmisor", rfcEmisor);
            parametros.put("nombreEmisor", atribsEmisor.getAttribute("Nombre"));
            parametros.put("regimenFiscal", atribsEmisor.getAttribute("RegimenFiscal"));

            NodeList listReceptor = document.getElementsByTagName("cfdi:Receptor");
            Node nodeReceptor = listReceptor.item(0);
            Element atribsReceptor = (Element) nodeReceptor;

            String rfcReceptor = atribsReceptor.getAttribute("Rfc");
            parametros.put("rfcReceptor", rfcReceptor);
            parametros.put("nombreReceptor", atribsReceptor.getAttribute("Nombre"));
            parametros.put("usoCfdi", atribsReceptor.getAttribute("UsoCFDI"));

            NodeList listConcepto = document.getElementsByTagName("cfdi:Concepto");
            NodeList listTraslado = document.getElementsByTagName("cfdi:Traslado");
            List<XmlFacturaDetails> conceptoList = new ArrayList<>();
            for(int i=0; i<listConcepto.getLength(); i++){
                Node nodeConcepto = listConcepto.item(i);
                Element atribsConcepto = (Element) nodeConcepto;
                Node nodeTraslado = listTraslado.item(i);
                Element atribsTraslado = (Element) nodeTraslado;

                XmlFacturaDetails conceptoAux = new XmlFacturaDetails(
                    atribsConcepto.getAttribute("ClaveProdServ"),
                    atribsConcepto.getAttribute("Cantidad"),
                    atribsConcepto.getAttribute("ClaveUnidad"),
                    atribsConcepto.getAttribute("Unidad"),
                    atribsConcepto.getAttribute("ValorUnitario"),
                    atribsConcepto.getAttribute("Importe"),
                    atribsConcepto.getAttribute("Descuento"),
                    atribsConcepto.getAttribute("Descripcion"),
                    atribsTraslado.getAttribute("Impuesto"),
                    atribsTraslado.getAttribute("Base"),
                    atribsTraslado.getAttribute("TipoFactor"),
                    atribsTraslado.getAttribute("TasaOCuota"),
                    atribsTraslado.getAttribute("Importe")
                );
                conceptoList.add(conceptoAux);
            }

            NodeList listSAT = document.getElementsByTagName("tfd:TimbreFiscalDigital");
            Node nodeSAT = listSAT.item(0);
            Element atribSAT = (Element) nodeSAT;
            
            String folioFiscal = atribSAT.getAttribute("UUID");

            parametros.put("folioFiscal", folioFiscal);
            parametros.put("certificadoSat", atribSAT.getAttribute("NoCertificadoSAT"));
            parametros.put("rfcProvCert", atribSAT.getAttribute("RfcProvCertif"));
            parametros.put("selloSat", atribSAT.getAttribute("SelloSAT"));
            parametros.put("selloCfdi", atribsComprobante.getAttribute("Sello"));

            String aux = atribsComprobante.getAttribute("Sello");
            String sello = aux.substring(aux.length() - 8);

            String codigo = folioFiscal + rfcEmisor + rfcReceptor + total + sello ;
            final String QR_CODE_IMAGE_PATH = "C:/Users/Propietario/Desktop/reporteadorBackEnd/MyQRCode.png";
            this.generaQR(codigo, 300, 300, QR_CODE_IMAGE_PATH);
            File archivo = new File(QR_CODE_IMAGE_PATH);
            parametros.put("codigoQr", archivo.getAbsolutePath());

            CadenaOriginalService cadena = new CadenaOriginalService();
            String cadenaOriginal = cadena.getCadenaOriginal(route);
            parametros.put("cadenaOriginal", cadenaOriginal);

            JRBeanCollectionDataSource conceptosDataSource = new JRBeanCollectionDataSource(conceptoList);

            String route2 = "C:/Users/Propietario/Desktop/reporteadorBackEnd/xml/" + folioFiscal + ".pdf";

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, parametros, conceptosDataSource);
            JasperExportManager.exportReportToPdfFile(jasperPrint, route2);

            byte[] reporte = JasperExportManager.exportReportToPdf(jasperPrint);
            String sdf = (new SimpleDateFormat("dd/MM/yyyy")).format(new Date());
            StringBuilder stringBuilder = new StringBuilder().append("InvoicePDF:");

            ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                .filename(stringBuilder.append("ARCHIVO")
                    .append("generateDate:")
                    .append(sdf)
                    .append(".pdf")
                    .toString())
                .build();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentDisposition(contentDisposition);

            return ResponseEntity.ok().contentLength((long) reporte.length)
                .contentType(MediaType.APPLICATION_PDF)
                .headers(headers).body(new ByteArrayResource(reporte));
            } catch (Exception e) {
                return null;
        }
    }

    public void generaQR(String text, int height, int width, String filePath) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, height, width);
        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }
    
    public String timbrarXml() {
        try {
            String swTimbrar = "https://services.test.sw.com.mx/cfdi33/stamp/v4";
            String route = "C:/Users/Propietario/Desktop/reporteadorBackEnd/xml/XmlPrueba2.xml";

            String token = "T2lYQ0t4L0RHVkR4dHZ5Nkk1VHNEakZ3Y0J4Nk9GODZuRyt4cE1wVm5tbXB3YVZxTHdOdHAwVXY2NTdJb1hkREtXTzE3dk9pMmdMdkFDR2xFWFVPUTQyWFhnTUxGYjdKdG8xQTZWVjFrUDNiOTVrRkhiOGk3RHladHdMaEM0cS8rcklzaUhJOGozWjN0K2h6R3gwQzF0c0g5aGNBYUt6N2srR3VoMUw3amtvPQ.T2lYQ0t4L0RHVkR4dHZ5Nkk1VHNEakZ3Y0J4Nk9GODZuRyt4cE1wVm5tbFlVcU92YUJTZWlHU3pER1kySnlXRTF4alNUS0ZWcUlVS0NhelhqaXdnWTRncklVSWVvZlFZMWNyUjVxYUFxMWFxcStUL1IzdGpHRTJqdS9Zakw2UGRKVVc4aElqRVRMVWVhbUNoSVVNODRrcHZiZDVGd3JNYXM0V2VJUDVPQWw1azBSN3NEVEoreGZHYkJ2MmtxOTYxR3hjYnBwQjBRQ3VXUUM1WFBSZGtsWjl5bjg4ZkhYdW5ScEtuMjExZ3hXVHpQbWEzaHRvSFp3NmVnSi92SnFQQk5oNjVDNmZybjZTMjB5eExqdys3Y2pkUkNEdFg1NWt6MGgrWmhrZXRJM3BaSjlXLytSM2RrZlFxdTRGWTVkdUV1ZHNRN29lMGpmTzN4TGhRL3BXWmVVd2QwTWJRTU5oRzZiUjM5YnladkVzb3ZhUkFkWTZxM3BqZTl2U21wR1NodjE3ZlJwSllScjIzKzlDMTAxdlZlUzduWEFRUngyWG9KVHlFeFBEaDZvRERmSFZSVkRzeVlveldlS1ZKTEw3bDk1NDlmK25FbXg5MzNUeFcvTXp2eDlTL0NoeGhJcng0aXJHbEw2eEhJeEhseEtTSUFzRjErL0E0MkZkMGhtamJvNElyQ3YvOXlRQldUWjlnN0ZVWm1nPT0.3WnthnH-o7RfllBn45qzkxYUQRgTqAAoqh2Hp7C_1gU";
            
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            File file = new File(route);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("xml", new FileSystemResource(file));
            
            // HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(swTimbrar, HttpMethod.POST, requestEntity, String.class);
            
            System.out.println("Result - status ("+ response.getStatusCode() + ") has body: " + response.hasBody());
            return response.toString();
        } catch (Exception e) {
            return e.toString();
        }
    }

    public String getTokenSw(){
        String swLogin = "https://services.test.sw.com.mx/security/authenticate";

        HttpHeaders headers = new HttpHeaders();
        headers.add("user", "cristianmartinez@ceag.com.mx"); 
        headers.add("password", "admin123");
        
        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(swLogin, HttpMethod.POST, requestEntity, String.class);
        
        return response.toString();
    } 
}
