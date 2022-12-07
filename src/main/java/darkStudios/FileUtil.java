/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package darkStudios;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import org.apache.commons.io.FileUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.resteasy.reactive.multipart.FileUpload;

/**
 *
 * @author diego
 */
@ApplicationScoped
public class FileUtil {
    @ConfigProperty(name = "carpeta.imagenes")
    String carpetaImagenes;
    private File folder;

    @PostConstruct
    void init() {
        folder = new File(carpetaImagenes);
    }

    public void escribirImagen(FileUpload file) throws IOException {
        InputStream initialStream = FileUtils.openInputStream(file.uploadedFile().toFile());
        File targetFile = new File(folder, file.fileName());

        FileUtils.writeByteArrayToFile(targetFile, initialStream.readAllBytes());
        initialStream.close();
    }
}
