package iotbay.listeners;

import com.stripe.Stripe;
import iotbay.annotations.GlobalServletField;
import iotbay.database.DatabaseManager;
import iotbay.database.StaticDatabaseManager;
import iotbay.exceptions.ConfigMissingException;
import iotbay.jobs.HouseKeeper;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.Properties;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * A listener that runs when the application starts up.
 */
public class AppContextListener implements ServletContextListener {

    /**
     * The servlet context
     */
    ServletContext scx;

    /**
     * The application configuration properties..
     */
    Properties appConfig;

    /**
     * The secrets properties.
     */
    Properties secrets;

    /**
     * The database manager
     */
    DatabaseManager db;

    /**
     * The logger for this class
     */
    private static final Logger logger = LogManager.getLogger(AppContextListener.class);

    /**
     * Initialises the application configuration.
     * @throws IOException if there is an error reading the configuration files.
     * @throws ConfigMissingException if the configuration files are missing.
     */
    public void initConfig() throws IOException, ConfigMissingException {
        InputStream inputStream = this.scx.getResourceAsStream("/WEB-INF/app-config.properties");

        if (inputStream == null) {
            throw new ConfigMissingException("app-config.properties not found. Please ensure that the file exists in the /WEB-INF directory.");
        }

        InputStream inputStreamSecrets = this.scx.getResourceAsStream("/WEB-INF/secrets.properties");

        if (inputStreamSecrets == null) {
            throw new ConfigMissingException("secrets.properties not found. Please ensure that the file exists in the /WEB-INF directory.");
        }

        this.appConfig = new Properties();
        this.secrets = new Properties();


        appConfig.load(inputStream);
        secrets.load(inputStreamSecrets);

    }

    /**
     * Initialises the database.
     * @throws SQLException if there is an error connecting to the database.
     * @throws ClassNotFoundException if the database driver is not found.
     * @throws IllegalAccessException if there is an error accessing the database manager.
     */
    public void initDb() throws SQLException, ClassNotFoundException, IllegalAccessException {
        this.db = new DatabaseManager(
                appConfig.getProperty("database.url"),
                appConfig.getProperty("database.username"),
                appConfig.getProperty("database.password"),
                appConfig.getProperty("database.name")
        );

        StaticDatabaseManager.setInstance(db);

        for (Field dbField : db.getClass().getDeclaredFields()) {
            if (dbField.isAnnotationPresent(GlobalServletField.class)) {
                dbField.setAccessible(true);
                this.scx.setAttribute(dbField.getName(), dbField.get(db));
            }
        }

        this.scx.setAttribute("db", db);
        this.scx.setAttribute("appConfig", appConfig);
        this.scx.setAttribute("secrets", secrets);
    }

    /**
     * Initialises the Stripe API.
     */
    public void initStripe() {
        Stripe.apiKey = secrets.getProperty("stripe.api.key");
    }

    /**
     * Initialises the scheduler.
     * @throws SchedulerException if there is an error initialising the scheduler.
     */
    public void initScheduler() throws SchedulerException {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("db", db);

        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
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

        scheduler.start();
        scheduler.scheduleJob(job, trigger);
    }


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        this.scx = sce.getServletContext();
        try {
            // These methods must be called in this order.
            this.initConfig();
            this.initDb();
            this.initStripe();
            this.initScheduler();
        } catch (Exception e) {
            logger.error("Application failed to start.", e);
            scx.setAttribute("initError", e.getMessage());
        }

        if (scx.getAttribute("initError") != null) {
            logger.error("Application started with critical errors. Please check the logs for more information.");
        } else {
            logger.info("Application started.");
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("Goodbye!");
    }
}
