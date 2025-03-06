package co.edu.uniandes.dse.parcialprueba.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import co.edu.uniandes.dse.parcialprueba.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(EspecialidadService.class)
public class EspecialidadServiceTest {
        
    private PodamFactory factory;
    
    @Autowired 
    private EspecialidadService especialidadService;

    @Autowired
    private TestEntityManager entityManager;

    
    @BeforeEach
    void setUp() {
        factory = new PodamFactoryImpl();
    }

    @Test 
    void testCrearEspecialidad() throws IllegalOperationException{
        EspecialidadEntity esp = factory.manufacturePojo(EspecialidadEntity.class);
        esp.setDescripcion("Cardiologia");
        entityManager.persist(esp);

        EspecialidadEntity espCreada = especialidadService.createEspecialidad(esp);

        assertNotNull(espCreada, "La especialidad no debería ser nula");
    
        assertEquals(espCreada.getNombre(), esp.getNombre());
    }

    @Test 
    void testCrearEspecialidadDescrInvalida() throws IllegalOperationException{
        EspecialidadEntity esp = factory.manufacturePojo(EspecialidadEntity.class);
        esp.setDescripcion("KKK");
        entityManager.persist(esp);


        assertThrows(IllegalOperationException.class, ()->{
            especialidadService.createEspecialidad(esp);
        }); 
    }

  

}
