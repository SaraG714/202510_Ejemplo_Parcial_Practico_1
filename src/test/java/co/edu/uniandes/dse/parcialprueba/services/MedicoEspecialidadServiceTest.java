package co.edu.uniandes.dse.parcialprueba.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import co.edu.uniandes.dse.parcialprueba.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialprueba.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(MedicoEspecialidadService.class)
public class MedicoEspecialidadServiceTest {

    private PodamFactory factory;
    
    @Autowired 
    private MedicoEspecialidadService medicoEspecialidadService;

    @Autowired
    private TestEntityManager entityManager;

    
    @BeforeEach
    void setUp() {
        factory = new PodamFactoryImpl();
    }

    @Test
    void testAddEspecialidad() throws EntityNotFoundException{
        EspecialidadEntity esp = factory.manufacturePojo(EspecialidadEntity.class);
        esp.setDescripcion("Cardiologia");
        entityManager.persist(esp);

        MedicoEntity medico = factory.manufacturePojo(MedicoEntity.class);
        medico.setRegMedico("RM0029");
        entityManager.persist(medico);

        medicoEspecialidadService.addEspecialidad(esp.getId(), medico.getId());

        assertEquals(1, medico.getEspecialidades().size());

        assertEquals(esp, medico.getEspecialidades().getFirst());


    }


    @Test
    void testAddEspecialidadMedicoInvalido() throws EntityNotFoundException{
        EspecialidadEntity esp = factory.manufacturePojo(EspecialidadEntity.class);
        esp.setDescripcion("Cardiologia");
        entityManager.persist(esp);

        MedicoEntity medico = factory.manufacturePojo(MedicoEntity.class);
        medico.setId(0L);

        assertThrows(EntityNotFoundException.class, ()->{
            medicoEspecialidadService.addEspecialidad(esp.getId(), medico.getId());

        });  


    }


    @Test
    void testAddEspecialidadEspInvalida() throws EntityNotFoundException{
        EspecialidadEntity esp = factory.manufacturePojo(EspecialidadEntity.class);
        esp.setDescripcion("KK");
        esp.setId(0L);

        MedicoEntity medico = factory.manufacturePojo(MedicoEntity.class);
        medico.setRegMedico("RM0029");
        entityManager.persist(medico);

        assertThrows(EntityNotFoundException.class, ()->{
            medicoEspecialidadService.addEspecialidad(esp.getId(), medico.getId());

        });  


    }
}
