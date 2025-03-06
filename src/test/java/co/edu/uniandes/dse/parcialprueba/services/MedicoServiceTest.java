package co.edu.uniandes.dse.parcialprueba.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import co.edu.uniandes.dse.parcialprueba.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(MedicoService.class)
public class MedicoServiceTest {

    private PodamFactory factory;
    
    @Autowired 
    private MedicoService medicoService;

    @Autowired
    private TestEntityManager entityManager;

    
    @BeforeEach
    void setUp() {
        factory = new PodamFactoryImpl();
    }

    @Test 
    void testCrearMedico() throws IllegalOperationException{
        MedicoEntity medico = factory.manufacturePojo(MedicoEntity.class);
        medico.setRegMedico("RM0029");
        entityManager.persist(medico);

        MedicoEntity medicoCreado = medicoService.createMedicos(medico);

        assertNotNull(medicoCreado, "El medico no debería ser nulo");
    
    }

    @Test 
    void testCrearMedicoRegistroInvalido() throws IllegalOperationException{
        MedicoEntity medico = factory.manufacturePojo(MedicoEntity.class);
        medico.setRegMedico("RK0029");
        entityManager.persist(medico);

        assertThrows(IllegalOperationException.class, ()->{
            medicoService.createMedicos(medico);
        });      
    }

}
