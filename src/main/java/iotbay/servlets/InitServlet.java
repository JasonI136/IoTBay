package iotbay.servlets;

import com.stripe.Stripe;
import iotbay.annotations.GlobalServletField;
import iotbay.database.DatabaseManager;
import iotbay.jobs.HouseKeeper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Properties;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class InitServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(InitServlet.class);
    @Override
    public void init() throws ServletException {
        super.init();
               // Load the application configuration.
        InputStream inputStream = getServletContext().getResourceAsStream("/WEB-INF/app-config.properties");

        if (inputStream == null) {
            logger.error("app-config.properties not found. Please ensure that the file exists in the /WEB-INF directory.");
            throw new ServletException("app-config.properties not found.");
        }

        InputStream inputStreamSecrets = getServletContext().getResourceAsStream("/WEB-INF/secrets.properties");

        if (inputStreamSecrets == null) {
            logger.error("secrets.properties not found. Please ensure that the file exists in the /WEB-INF directory.");
            throw new ServletException("secrets.properties not found.");
        }

        Properties appConfig = new Properties();
        Properties secrets = new Properties();


        try {
            appConfig.load(inputStream);
            secrets.load(inputStreamSecrets);
        } catch (IOException err) {
            logger.error("Application configuration failed to load.");
            throw new ServletException("Application configuration failed to load.");
        }

        // Initialise the database
        DatabaseManager db;
        try {
            db = new DatabaseManager(
                    appConfig.getProperty("database.url"),
                    appConfig.getProperty("database.username"),
                    appConfig.getProperty("database.password"),
                    appConfig.getProperty("database.name")
            );
        } catch (Exception e) {
            logger.error("An error occurred whilst intialising the database: " + e.getMessage());
            throw new ServletException("An error occurred whilst intialising the database: " + e.getMessage());
        }

        for (Field dbField: db.getClass().getDeclaredFields()) {
            if (dbField.isAnnotationPresent(GlobalServletField.class)) {
                try {
                    dbField.setAccessible(true);
                    getServletContext().setAttribute(dbField.getName(), dbField.get(db));
                } catch (IllegalAccessException e) {
                    logger.error("An error occurred whilst intialising the database: " + e.getMessage());
                    throw new ServletException("An error occurred whilst intialising the database: " + e.getMessage());
                }
            }
        }

        getServletContext().setAttribute("db", db);
        getServletContext().setAttribute("appConfig", appConfig);
        getServletContext().setAttribute("secrets", secrets);

        Stripe.apiKey = secrets.getProperty("stripe.api.key");

        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("db", db);

        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = null;
        try {
            scheduler = schedulerFactory.getScheduler();
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
        JobDetail job = newJob(HouseKeeper.class)
                .withIdentity("housekeeper", "iotbay")
                .usingJobData(jobDataMap)
                .build();

        Trigger trigger = newTrigger()
                .withIdentity("housekeeper", "iotbay")
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInMinutes(1)
                        .repeatForever())
                .build();

        try {
            scheduler.start();
            scheduler.scheduleJob(job, trigger);
        } catch (Exception e) {
            logger.error("Failed to start scheduler: " + e.getMessage());
            throw new ServletException("Failed to start scheduler: " + e.getMessage());
        }

        logger.info("Application started.");
    }
}
