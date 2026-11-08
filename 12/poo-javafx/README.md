# Paso a Paso: Proyecto 12 - poo-javafx (Patrón Repository, Interfaz Genérica IDAO<T>, Herencia en Entidades y Persistencia Segregada)

Guía paso a paso para evolucionar el **Proyecto 11** al **Proyecto 12**, incorporando herencia en las entidades de dominio (`Persona` abstracta, `Alumno`, `Profesor` con `@SuperBuilder`), la interfaz genérica `IDAO<T>`, los DAOs especializados (`AlumnoDAO`, `ProfesorDAO` sobre `alumno.json` y `profesor.json`) y el orquestador `PersonaRepository`.

> Documentación general y diagramas de arquitectura: [../README.md](../README.md)

---

## Paso a Paso para la Construcción Incremental (desde Proyecto 11)

### Paso 1: Convertir la Entidad `Persona` en Abstracta con `@SuperBuilder`
Modifica `Persona.java` para que sea `abstract` y utilice `@SuperBuilder`, permitiendo que sus clases hijas hereden el builder fluido:

- **Archivo**: `src/main/java/cl/elprofecarreno/app/dao/entity/Persona.java`
```java
package cl.elprofecarreno.app.dao.entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract class Persona {

    private int run;
    private String dv;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private Direccion direccion;
    private Date fechaNacimiento;

}
```

---

### Paso 2: Crear las Entidades Hijas `Alumno` y `Profesor`
Crea las entidades concretas dentro del paquete `cl.elprofecarreno.app.dao.entity`:

- **Archivo**: `src/main/java/cl/elprofecarreno/app/dao/entity/Alumno.java`
```java
package cl.elprofecarreno.app.dao.entity;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter 
@Setter 
@NoArgsConstructor
@AllArgsConstructor 
@SuperBuilder 
public class Alumno extends Persona {
    
    @SerializedName("carrera")
    private String carrera;

    @SerializedName("semestre")
    private String semestre;

}
```

- **Archivo**: `src/main/java/cl/elprofecarreno/app/dao/entity/Profesor.java`
```java
package cl.elprofecarreno.app.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter 
@Setter 
@NoArgsConstructor
@AllArgsConstructor 
@SuperBuilder 
public class Profesor extends Persona {
    
    private String especialidad;
    private String jornada;
    private long sueldoBruto;
}
```

---

### Paso 3: Definir la Interfaz Genérica `IDAO<T>`
Crea el contrato genérico para estandarizar todas las operaciones de acceso a datos:

- **Archivo**: `src/main/java/cl/elprofecarreno/app/dao/IDAO.java`
```java
package cl.elprofecarreno.app.dao;

import java.util.List;
import cl.elprofecarreno.app.dao.excepcion.DAOException;

public interface IDAO<T> {
    
    // Obtener listado de registros desde un json o una tabla de la base de datos.
    public List<T> findAll() throws DAOException;

    // Eliminar registros existentes de un json o una tabla de la base de datos.
    public void delete(List<T> personas) throws DAOException;

    // Guardar un nuevo registro en un json o una tabla de la base de datos.
    public void save(T persona) throws DAOException;
}
```

---

### Paso 4: Implementar `AlumnoDAO` y `ProfesorDAO` y Crear los Archivos JSON
Crea las implementaciones especializadas de `IDAO<Alumno>` e `IDAO<Profesor>` apuntando a `/alumno.json` y `/profesor.json`:

- **Archivo**: `src/main/java/cl/elprofecarreno/app/dao/AlumnoDAO.java`
```java
package cl.elprofecarreno.app.dao;

import cl.elprofecarreno.app.dao.entity.Alumno;
import cl.elprofecarreno.app.dao.excepcion.DAOException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AlumnoDAO implements IDAO<Alumno> {
    
    private static final String FILE = "/alumno.json";

    @Override
    public List<Alumno> findAll() throws DAOException {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        try (Reader reader = new InputStreamReader(getClass().getResourceAsStream(FILE))) {
            Type listType = new TypeToken<ArrayList<Alumno>>(){}.getType();
            return gson.fromJson(reader, listType);
        } catch (Exception e) {
            throw new DAOException("ERROR al leer data.json", e);
        }
    }

    @Override
    public void delete(List<Alumno> alumnos) throws DAOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy-MM-dd").create();
        try (Reader reader = new InputStreamReader(getClass().getResourceAsStream(FILE))) {
            Type listType = new TypeToken<ArrayList<Alumno>>(){}.getType();
            List<Alumno> data = gson.fromJson(reader, listType);
            List<Alumno> alumnosAEliminar = new ArrayList<>();
            for (Alumno p : data) {
                for (Alumno pE : alumnos) {
                    if (p.getRun() == pE.getRun()) {
                        alumnosAEliminar.add(p);
                    }
                }
            }
            data.removeAll(alumnosAEliminar);
            try (java.io.Writer writer = new java.io.FileWriter(getClass().getResource(FILE).getFile())) {
                gson.toJson(data, writer);
            }
        } catch (Exception e) {
            throw new DAOException("ERROR al leer data.json", e);
        }
    }

    @Override
    public void save(Alumno alumno) throws DAOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy-MM-dd").create();
        try (Reader reader = new InputStreamReader(getClass().getResourceAsStream(FILE))) {
            Type listType = new TypeToken<ArrayList<Alumno>>(){}.getType();
            List<Alumno> data = gson.fromJson(reader, listType);
            for (Alumno p : data) {
                if (p.getRun() == alumno.getRun()) {
                    throw new DAOException("Alumno con RUN " + alumno.getRun() + " ya existe");
                }
            }
            data.add(alumno);
            try (java.io.Writer writer = new java.io.FileWriter(getClass().getResource(FILE).getFile())) {
                gson.toJson(data, writer);
            }
        } catch (DAOException e) {
            throw e;
        } catch (Exception e) {
            throw new DAOException("ERROR al leer data.json", e);
        }
    }
}
```

- **Archivo**: `src/main/java/cl/elprofecarreno/app/dao/ProfesorDAO.java`
  Sigue la misma estructura que `AlumnoDAO`, implementando `IDAO<Profesor>` y operando sobre `private static final String FILE = "/profesor.json";`.

- **Archivos de recursos JSON iniciales**:
  Crea `src/main/resources/alumno.json` y `src/main/resources/profesor.json` inicializados con un arreglo JSON vacío:
  ```json
  []
  ```

---

### Paso 5: Construir la Clase `PersonaRepository`
Crea el paquete `cl.elprofecarreno.app.repository` y la clase `PersonaRepository` encargada de coordinar los DAOs especializados:

- **Archivo**: `src/main/java/cl/elprofecarreno/app/repository/PersonaRepository.java`
```java
package cl.elprofecarreno.app.repository;

import cl.elprofecarreno.app.dao.IDAO;
import cl.elprofecarreno.app.dao.AlumnoDAO;
import cl.elprofecarreno.app.dao.ProfesorDAO;
import cl.elprofecarreno.app.dao.entity.Alumno;
import cl.elprofecarreno.app.dao.entity.Persona;
import cl.elprofecarreno.app.dao.entity.Profesor;
import cl.elprofecarreno.app.dao.excepcion.DAOException;

import java.util.ArrayList;
import java.util.List;

public class PersonaRepository {
    
    private IDAO<Profesor> profesorDAO;
    private IDAO<Alumno> alumnoDAO;

    public PersonaRepository() {
        this.profesorDAO = new ProfesorDAO();
        this.alumnoDAO = new AlumnoDAO();
    }

    public List<Persona> findAll() throws DAOException {
        List<Persona> personas = new ArrayList<>();
        personas.addAll(profesorDAO.findAll());
        personas.addAll(alumnoDAO.findAll());
        return personas;
    }

    public void delete(List<Persona> persona) throws DAOException {
        List<Profesor> deleteProfesores = new ArrayList<>();
        List<Alumno> deleteAlumnos = new ArrayList<>();
        for (Persona p : persona) {
            if (p instanceof Profesor) {
                deleteProfesores.add((Profesor) p);
            } else if (p instanceof Alumno) {
                deleteAlumnos.add((Alumno) p);
            }
        }
        if (!deleteProfesores.isEmpty()) {
            profesorDAO.delete(deleteProfesores);
        }
        if (!deleteAlumnos.isEmpty()) {
            alumnoDAO.delete(deleteAlumnos);
        }
    }

    public void save(Persona persona) throws DAOException {
        if (persona instanceof Profesor) {
            profesorDAO.save((Profesor) persona);
        } else if (persona instanceof Alumno) {
            alumnoDAO.save((Alumno) persona);
        }
    }
}
```

---

### Paso 6: Actualizar `PersonaService` con `PersonaRepository` y Mapeo Polimórfico
Conecta `PersonaService` con `PersonaRepository` y actualiza `toEntity(PersonaDTO)` para instanciar `Alumno` o `Profesor`:

- **Archivo**: `src/main/java/cl/elprofecarreno/app/service/PersonaService.java`
```java
    private PersonaRepository personaRepository;
    
    public PersonaService() {
        personaRepository = new PersonaRepository();
    }

    public Persona toEntity(PersonaDTO personaDTO) {
        String[] run = personaDTO.getRunDv().split("-");
        Persona persona = null;
        if (personaDTO instanceof AlumnoDTO) {
            AlumnoDTO alumnoDTO = (AlumnoDTO) personaDTO;
            persona = Alumno.builder()
                    .run(Integer.valueOf(run[0]))
                    .dv(run[1])
                    .nombres(alumnoDTO.getNombres())
                    .apellidoPaterno(alumnoDTO.getApellidoPaterno())
                    .apellidoMaterno(alumnoDTO.getApellidoMaterno())
                    .fechaNacimiento(alumnoDTO.getFechaNacimiento())
                    .direccion(
                        Direccion.builder()
                            .calle(alumnoDTO.getCalle())
                            .numero(alumnoDTO.getNumero())
                            .comuna(alumnoDTO.getComuna())
                            .region(alumnoDTO.getRegion())
                            .build()
                    )
                    .semestre(alumnoDTO.getSemestre())
                    .carrera(alumnoDTO.getCarrera())
                    .build();
        } else if (personaDTO instanceof ProfesorDTO) {
            ProfesorDTO profesorDTO = (ProfesorDTO) personaDTO;
            persona = Profesor.builder()
                    .run(Integer.valueOf(run[0]))
                    .dv(run[1])
                    .nombres(profesorDTO.getNombres())
                    .apellidoPaterno(profesorDTO.getApellidoPaterno())
                    .apellidoMaterno(profesorDTO.getApellidoMaterno())
                    .fechaNacimiento(profesorDTO.getFechaNacimiento())
                    .direccion(
                        Direccion.builder()
                            .calle(profesorDTO.getCalle())
                            .numero(profesorDTO.getNumero())
                            .comuna(profesorDTO.getComuna())
                            .region(profesorDTO.getRegion())
                            .build()
                    )
                    .jornada(profesorDTO.getJornada())
                    .sueldoBruto(profesorDTO.getSueldoBruto())
                    .build();
        }
        return persona;
    }
```

---

## Ejecución del Proyecto

Desde este directorio (`12/poo-javafx`):

```bash
mvn clean compile javafx:run
```

