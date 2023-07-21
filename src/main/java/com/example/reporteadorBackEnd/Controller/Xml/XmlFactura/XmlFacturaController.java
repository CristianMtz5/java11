package com.example.reporteadorBackEnd.Controller.Xml.XmlFactura;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.reporteadorBackEnd.Service.Xml.XmlFactura.XmlFacturaService;

import javax.transaction.Transactional;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("auth/XmlFactura")
public class XmlFacturaController {
    
    @Autowired
    XmlFacturaService xmlFacturaService;
    
    @Transactional
    @GetMapping("/byIdComprobante/{id}")
    public String formarXml(@PathVariable("id") Long id) {
        return xmlFacturaService.formarXml(id);
    }

    @GetMapping("/login")
    public String getTokenSw(){
        return xmlFacturaService.getTokenSw();
    }

    @GetMapping("/timbrarXml")
    public String timbrarXml(){
        return xmlFacturaService.timbrarXml();
    }
}
