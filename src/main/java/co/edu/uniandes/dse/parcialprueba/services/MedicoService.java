package co.edu.uniandes.dse.parcialprueba.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcialprueba.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialprueba.repositories.MedicoRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    public boolean validateRM (String registroMed){

        boolean is = false;

        if (registroMed.startsWith("RM")){
            is = true;
        }

        return is;
    }

    @Transactional
    public MedicoEntity createMedicos (MedicoEntity medico) throws IllegalOperationException {      
        log.info("Inicia proceso de creación de un médico");

        if (!(validateRM(medico.getRegMedico())))
            throw new IllegalOperationException("El registro médico es inválido.");

        log.info("Se creó el médico con id: "+ medico.getIdMedico());
        return medicoRepository.save(medico);
    }
}
