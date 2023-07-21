package com.example.reporteadorBackEnd.Service.Nomina;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.reporteadorBackEnd.Entity.Nomina.ClientesEntity;
import com.example.reporteadorBackEnd.Entity.Nomina.DTO.ClientesDTO;
import com.example.reporteadorBackEnd.Repository.Nomina.ClientesRepository;
import com.example.reporteadorBackEnd.Security.Repository.UsuariosRepository;
import com.example.reporteadorBackEnd.Security.Usuarios.UsuariosEntity;

@Service
public class ClientesService {

    @Autowired
    ClientesRepository clientesRepository;

    @Autowired
    UsuariosRepository usuariosRepository;

    public List<ClientesDTO> getAll(Integer id, Boolean status, Sort sort) {
        Optional<UsuariosEntity> usuariosEntity = usuariosRepository.findById(id);
        UsuariosEntity usuarioId = usuariosEntity.get();
        sort = Sort.by("id");
        List<ClientesEntity> clientes = clientesRepository.findByIdUsuarioAndStatus(usuarioId, status, sort);
        return clientes.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    public ClientesDTO entityToDTO(ClientesEntity cliente) {
        return new ClientesDTO(
                cliente
            );
    }

    /* public List<ClientesEntity> getAllByIdUsuarioAndStatus(Integer id, Boolean status, Sort sort) {
        Optional<UsuariosEntity> usuariosEntity = usuariosRepository.findById(id);
        UsuariosEntity usuarioId = usuariosEntity.get();
        sort = Sort.by("id");

        List<ClientesEntity> clientes = clientesRepository.findByIdUsuarioAndStatus(usuarioId, status, sort);
        return clientes;
    } */

    public ResponseEntity<ClientesDTO> createRegistroDto(ClientesDTO clienteDTO) {
        try {
            // ClientesEntity clientesEntity = clientesRepository.save(var);
            // ClientesDTO clientesDTO = new ClientesDTO();
            ClientesEntity clientesEntity = clientesRepository.save(clienteDTO.convertToEntity());
            return new ResponseEntity<>(new ClientesDTO(clientesEntity), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ClientesEntity> createRegistro(ClientesEntity var) {
        try {
            ClientesEntity clientesEntity = clientesRepository.save(var);
            return new ResponseEntity<>(clientesEntity, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ClientesEntity> updateRegistro(Long id, ClientesEntity cliente) {
        Optional<ClientesEntity> clienteId = clientesRepository.findById(id);

        if (clienteId.isPresent()) {
            ClientesEntity clientesEntity = clientesRepository.save(cliente);
            return new ResponseEntity<>(clientesEntity, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<ClientesEntity> updateStatus(Long id, ClientesEntity cliente) {
        Optional<ClientesEntity> clienteId = clientesRepository.findById(id);

        if (clienteId.isPresent()) {
            ClientesEntity clientesEntity = clienteId.get();
            clientesEntity.setStatus(cliente.getStatus());
            return new ResponseEntity<>(clientesRepository.save(clientesEntity), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
