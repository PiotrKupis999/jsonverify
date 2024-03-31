package com.verifier.jsonverify.service;

import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;

@Service
public class VerifierService {

    public boolean JSONVerification(String jsonString) {
        try {
            JSONObject fileJson = new JSONObject(jsonString);

            JSONObject policyDocumentJson = fileJson.getJSONObject("PolicyDocument");

            JSONArray statementsJson = policyDocumentJson.getJSONArray("Statement");

            for(Object statement : statementsJson) {
                JSONObject statementJson = new JSONObject(statement.toString());
                try {
                    String resourceValue = statementJson.getString("Resource");
                    if ("*".equals(resourceValue)) {
                        System.out.println("Invalid JSON: Resource field contains a single asterisk '*'");
                        return false;
                    }
                } catch (JSONException e) {

                    // in case the Resource field is an JSONarray
                    JSONArray resourcesJson = statementJson.getJSONArray("Resource");
                    for(Object resource : resourcesJson) {
                        if (resource.toString().contains("*")){
                            System.out.println("Invalid JSON: Resource field contains a single asterisk '*'");
                            return false;
                        };

                    }
                    // Resource field does not exist
                    System.out.println("Invalid JSON format: " + e.getMessage());
                }
            }

            System.out.println("JSON is valid");

        } catch (JSONException e) {
            System.out.println("Invalid JSON format: " + e.getMessage());
        }

        return true;
    }

}



