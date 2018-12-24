package by.fertigi.app;

import by.fertigi.app.configuration.DbConfig;
import by.fertigi.app.service.StarterApp;
import by.fertigi.app.util.SQLCreator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@Import(DbConfig.class)
public class RunnerApp {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(RunnerApp.class, args);
        String tableName = "Patient_Info";
        List<String> listFields = new ArrayList<>();
        listFields.add("First_Name");
        listFields.add("Last_Name");
        listFields.add("Phone");
        listFields.add("City");
        listFields.add("State");
        listFields.add("ZIP");
        listFields.add("Address");
        listFields.add("Gender");
        listFields.add("DOB");
        Map<String, List<String>> entity = new HashMap<>();
        entity.put(tableName, listFields);

        String select = SQLCreator.sqlSelectCreator(tableName, listFields);
        String update = SQLCreator.sqlUpdateCreator(tableName, listFields);
        System.out.println("Select: " + select);
        System.out.println("Update: " + update);

        StarterApp bean = context.getBean(StarterApp.class);
        bean.run();
//        bean.insertDataToDB(15);
    }
}

/*
Patient Info:
First Name
Last Name
Phone
City
State
ZIP
Address
Gender
DOB

Physician info:
First Name
Last name
Phone
City
State
ZIP
Address
NPI

Insurance info:
Policy
Group
*/