package com.example.demo.configuration;

import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.NotSupportedException;
import java.io.*;
import java.util.List;
import java.util.Optional;

@Configuration
public class XmlConfiguration {

    @Value("${demo.upload.filetype.allow}")
    private List<String> fileTypes;

    public <T> T readXml(String pathName, Class<T> clazz) throws IOException, NotSupportedException {
        isValid(pathName);
        File file = new File(pathName);
        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        XmlMapper xmlMapper = new XmlMapper(module);
        String xml = inputStreamToString(new FileInputStream(file));
        return clazz.cast(xmlMapper.readValue(xml, clazz));

    }

    private boolean isValid(String fileName) throws NotSupportedException {
        String extension = Optional.ofNullable(fileName)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(fileName.lastIndexOf(".") + 1))
                .orElseThrow(()-> new NotSupportedException("File doesn't have extension"))
                ;
        if (!fileTypes.contains(extension.toUpperCase())) {
            throw new NotSupportedException("Just support these extensions : KML,GPX,PLT,GDB" );
        }

        return true;
    }

    private String inputStreamToString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }

    public File convertFromMultiPartToFile(MultipartFile multipartFile) throws IOException {
        File file = new File(multipartFile.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(multipartFile.getBytes());
        fos.close();

        return file;
    }
}
