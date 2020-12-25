package de.poxh.services.services.report;

import com.mongodb.client.MongoDatabase;
import de.poxh.services.models.common.Paths;
import de.poxh.services.models.common.Severity;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;

public class ReportService {
    private final MongoDatabase db;

    public ReportService(MongoDatabase db) {
        this.db = db;
    }

    public void report(Severity severity, String description, String serviceName) {
        StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();

        Map<String, Object> report = new HashMap<>();

        report.put("Severity", severity);
        report.put("StackTrace", stacktrace);
        report.put("ShortDescription", description);
        report.put("ServiceName", serviceName);

        this.db.getCollection(Paths.Report.toString()).insertOne(new Document(report));
    }
}