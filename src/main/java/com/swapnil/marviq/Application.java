package com.swapnil.marviq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class Application {

    public static void main(String[] args) throws Exception {

        Application app = new Application();
        File file = new File(
                app.getClass().getClassLoader().getResource("Runtime.csv").getFile()
        );
        FileReader reader = new FileReader(file);
        BufferedReader br = new BufferedReader(reader);


        BufferedReader csvReader = new BufferedReader(new FileReader("/Users/Swapnil/Downloads/Marviq/Runtime.csv"));
        int i = 0;

        while (csvReader.readLine() != null) {
            String[] data = csvReader.readLine().split(",");
            String id = data[0];
            String machineName = data[1].replaceAll("\"", "");
            String time = data[2].replaceAll("\"", "");
            String isRunning = data[3];
            System.out.println();

        }
        csvReader.close();

        SpringApplication.run(Application.class, args);
    }

}
