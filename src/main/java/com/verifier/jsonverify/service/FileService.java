package com.verifier.jsonverify.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.crypto.Data;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FileService {

    public String ExampleToString(int number) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/testFiles/example" + number + ".json"));
        return JSONToText(reader);
    }

    public String FileToString(MultipartFile multipartFile) throws IOException {

        return new String(multipartFile.getBytes(), StandardCharsets.UTF_8);
    }


    private static String JSONToText(BufferedReader reader) throws IOException {
        String line = null;
        StringBuilder stringBuilder = new StringBuilder();
        String ls = System.getProperty("line.separator");

        try {
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }

            return stringBuilder.toString();
        } finally {
            reader.close();
        }
    }
}
