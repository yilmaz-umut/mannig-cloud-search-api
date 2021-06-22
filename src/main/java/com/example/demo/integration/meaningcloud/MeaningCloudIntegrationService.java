package com.example.demo.integration.meaningcloud;

import com.example.demo.entity.ATag;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Component
public class MeaningCloudIntegrationService {

    private final RestTemplate restTemplate;


    public MeaningCloudIntegrationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public ATag test(String keyword) throws URISyntaxException, JsonProcessingException {

        final String baseURl = "https://api.meaningcloud.com/topics-2.0";
        final String key  = "5bc5e6d158097334a26d806959824672";

        URI uri = new URI(baseURl);
        MeaningCloudRequest meaningCloudRequest = new MeaningCloudRequest(key,"Robert Downey");
        ResponseEntity<String> result = restTemplate.postForEntity(uri, meaningCloudRequest, String.class);

        TopicsResponse r = TopicsResponse.from(result.getBody());


        ATag aTag = new ATag();

        List<String> sementyList = new ArrayList<>();
        try {
            r.getEntityList().forEach( entity -> {
                sementyList.add(entity.getSementity().getType());
            });
        }catch (Exception e){

        }

        try {
            r.getConceptList().forEach( entity -> {
                sementyList.add(entity.getSementity().getType());

            });
        }catch (Exception e){

        }

        aTag.setSementiy_list(sementyList);



        List<String> semldList = new ArrayList<>();
        try {

            r.getConceptList().forEach(concept -> {
                semldList.addAll(concept.getSemldList());
            });
        }catch (Exception e){

        }
        aTag.setSemld_list(semldList);



        List<String> verbList = new ArrayList<>();
        try {

            r.getRelationList().forEach(relation ->
                    verbList.add(relation.getForm()));



        }catch (Exception e){

        }

        aTag.setVerb_list(verbList);


        List<String> subjectList = new ArrayList<>();
        try {
            r.getRelationList().forEach(relation ->
                    verbList.add(relation.getForm()));

        }catch (Exception e){

        }

        aTag.setSubject_list(subjectList);

        return aTag;
    }




}
