package com.verifier.jsonverify;

import com.verifier.jsonverify.service.FileService;
import com.verifier.jsonverify.service.VerifierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


@RestController
@SpringBootApplication
public class JsonverifyApplication {

	@Autowired
	private VerifierService verifierService;
	@Autowired
	private FileService fileService;

	@GetMapping("/")
	public String hello(){
		return "Hello everyone :) You can POST a file to http://localhost:8080/. For default examples type http://localhost:8080/example{number}, where {number} is between 1 and 5. ";
	}

	@PostMapping("/")
	public boolean uploadFile(@RequestParam("file") MultipartFile file) throws IOException{
		String JSONtext = fileService.FileToString(file);
		return verifierService.JSONVerification(JSONtext);
	}

	@GetMapping("/example{number}")
	public String downloadExample(@PathVariable int number) throws IOException{

		String JSONtext = fileService.ExampleToString(number);

		String verificationResult = String.valueOf(verifierService.JSONVerification(JSONtext));

		return verificationResult + JSONtext;

	}

	public static void main(String[] args) {
		SpringApplication.run(JsonverifyApplication.class, args);
	}

}
