package darkStudios;

import darkStudios.entities.Estudiantes;
import darkStudios.servicios.EstudiantesService;
import java.io.IOException;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

@Path("procesarformulario")
public class Controller {

    @Inject
    FileUtil fileUtil;
    private static final Logger LOG = Logger.getLogger(Controller.class);
    @Inject
    EstudiantesService service;
    @POST
    @Produces(MediaType.TEXT_HTML)
    public Response procesar(
    @RestForm("photo") FileUpload file,
            @RestForm String firstName, @RestForm String secondName, @RestForm String email, @RestForm String codigo
    ) {
      Estudiantes info = new Estudiantes(codigo);
      info.setFoto(file.fileName());
      if(secondName!=null && secondName.length() > 0){
          info.setApellidos(secondName);
      }
      if(codigo!=null && codigo.length() > 0){
          info.setCarnet(codigo);
      }
      if(email!=null && email.length() > 0){
          info.setEmail(email);
      }
      if(firstName!=null && firstName.length() > 0){
          info.setNombres(firstName);
      }
      if(file!=null){
            try {
                fileUtil.escribirImagen(file);
            }catch (IOException e){
                LOG.error("Error escribiendo el archivo: ",e);
            }
        }else {
            LOG.error("No se subio el archivo");
        }
      service.create(info);
      
      StringBuilder sb = new StringBuilder("");
        sb.append("<html>");
        sb.append("<head><title>Reporte de Estudiante</title></head>");
        sb.append("<body>");

        sb.append("<ul>");
        sb.append("<li>Nombre: " + firstName + "</li>");
        sb.append("<li>Apellidos: " + secondName + "</li>");
        sb.append("<li>Correo " + email + "</li>");
        sb.append("<li>Codigo: " + codigo + "</li>");
        sb.append("</ul>");

        sb.append("</body>");
        sb.append("</html>");
        LOG.info("Se escribio el HTML");

        return Response.ok(sb.toString(), MediaType.TEXT_HTML).build();
    }
}