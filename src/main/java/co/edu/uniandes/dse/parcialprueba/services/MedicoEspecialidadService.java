package co.edu.uniandes.dse.parcialprueba.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcialprueba.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialprueba.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialprueba.repositories.EspecialidadRepository;
import co.edu.uniandes.dse.parcialprueba.repositories.MedicoRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MedicoEspecialidadService {

    @Autowired 
    private MedicoRepository medicoRepository;

    @Autowired 
    private EspecialidadRepository especialidadRepository;

    @Transactional
	public EspecialidadEntity addEspecialidad(Long especialidadId, Long medicoId) throws EntityNotFoundException {
		log.info("Inicia proceso de agregarle una especialidad al médico con id = {0}", medicoId);
		
		Optional<EspecialidadEntity> especialidadEntity = especialidadRepository.findById(especialidadId);
		if(especialidadEntity.isEmpty())
			throw new EntityNotFoundException("Especialidad no existe");
		
		Optional<MedicoEntity> medicoEntity = medicoRepository.findById(medicoId);
		if(medicoEntity.isEmpty())
			throw new EntityNotFoundException("Medico no");
		
		medicoEntity.get().getEspecialidades().add(especialidadEntity.get());
		log.info("Termina proceso de agregarle una especialidad al medico con id = {0}", medicoId);
		return especialidadEntity.get();
	}

}
