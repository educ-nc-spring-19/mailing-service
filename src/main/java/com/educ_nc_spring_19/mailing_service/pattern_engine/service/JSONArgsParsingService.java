package com.educ_nc_spring_19.mailing_service.pattern_engine.service;

import com.educ_nc_spring_19.mailing_service.pattern_engine.client.MasterDataClient;
import org.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class JSONArgsParsingService {
    public ArrayList<String> Parse(String rtype, String jsonArgs, UUID receiverId) {
        ArrayList<String> args = new ArrayList<>();
        JSONObject obj = new JSONObject(jsonArgs);
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        MasterDataClient masterDataClient = new MasterDataClient(restTemplateBuilder);
        for (String key : obj.keySet()) {
            String tmp = obj.getString(key);

            switch (key) {
                case "name":
                    String name;
                    if (rtype.equals("mentor")) {
                        name = masterDataClient.getMentorById(receiverId);
                        //TODO!!!!!!!!!!!!!!!!!!!!!!!!
                    } else {
                        name = masterDataClient.getStudentById(receiverId);
                        //TODO!!!!!!!!!!!!!!!!!!!!!!!!
                    }
                    args.add(name);
                    break;
                case "mentor_name":
                case "backup_name":
                    args.add(masterDataClient.getMentorById(UUID.fromString(obj.getString(key))));
                    //TODO!!!!!!!!!!!!!!!!!!!!!!!!
                    break;
                default:
                    args.add(obj.getString(key));
                    //TODO: what if keys are similar? if I'm iterating, wouldn't it return only one value?
                    break;
            }

        }
        return args;
    }
}
