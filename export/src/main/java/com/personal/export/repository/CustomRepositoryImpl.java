package com.personal.export.repository;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.personal.export.entity.Employee;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class CustomRepositoryImpl implements CustomRepository{
    @Autowired
    MongoClient mongoClient;

    @Autowired
    MongoConverter mongoConverter;

    @Override
    public List<Employee> searchByText(String text) {
        List<Employee> employees = new ArrayList<>();

        MongoDatabase database = mongoClient.getDatabase("companyX");
        MongoCollection<Document> collection = database.getCollection("employee");

        AggregateIterable<Document> result = collection.aggregate(Arrays.asList(new Document("$search",
                        new Document("text",
                                new Document("query", text)
                                        .append("path", Arrays.asList("role", "tech_stack", "name")))),
                new Document("$sort",
                        new Document("experience", 1L))));

        result.forEach(document -> employees.add(mongoConverter.read(Employee.class, document)));

        return employees;
    }
}
