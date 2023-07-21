package com.example.reporteadorBackEnd.Controller.Xml;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.example.reporteadorBackEnd.Entity.Xml.TrasladoOrRetencionXmlEntity;
import com.example.reporteadorBackEnd.Repository.Xml.TrasladoOrRetencionRepository;
import com.example.reporteadorBackEnd.Service.Xml.TrasladoOrRetencionService;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE,
        RequestMethod.PUT, })
@RestController
@RequestMapping("auth/TrasladoXml")
public class TrasladoOrRetencionController {

    @Autowired
    TrasladoOrRetencionRepository trasladoRepository;

    @Autowired
    TrasladoOrRetencionService trasladoOrRetencionService;

    @GetMapping("/getAll/{status}")
    public List<TrasladoOrRetencionXmlEntity> allByStatus(@PathVariable("status") Boolean status, Sort sort) {
        return (List<TrasladoOrRetencionXmlEntity>) trasladoOrRetencionService.getAllByStatus(status, sort);
    }

    @PostMapping("/agregar")
    public ResponseEntity<TrasladoOrRetencionXmlEntity> createRegistro(@RequestBody TrasladoOrRetencionXmlEntity concepto) {
        return trasladoOrRetencionService.createRegistro(concepto);
    }
} 
