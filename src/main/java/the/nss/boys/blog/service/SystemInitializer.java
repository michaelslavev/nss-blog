package the.nss.boys.blog.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import the.nss.boys.blog.model.Role;
import the.nss.boys.blog.model.User;

import javax.annotation.PostConstruct;

@Component
public class SystemInitializer {

    private static final Logger LOG = LoggerFactory.getLogger(SystemInitializer.class);

    /**
     * Default admin username
     */
    private static final String ADMIN_USERNAME = "admin";

    private final UserService userService;

    private final PlatformTransactionManager txManager;

    @Autowired
    public SystemInitializer(UserService userService,
                             PlatformTransactionManager txManager) {
        this.userService = userService;
        this.txManager = txManager;
    }

    @PostConstruct
    private void initSystem() {
        TransactionTemplate txTemplate = new TransactionTemplate(txManager);
        txTemplate.execute((status) -> {
            generateAdmin();
            return null;
        });
    }

    /**
     * Generates admin with credentials: \n
     * firstName, lastName, password \n
     * System, Administrator, admin \n
     */
    private void generateAdmin() {
        if (userService.exists(ADMIN_USERNAME)) {
            return;
        }
        final User admin = new User();
        admin.setUsername(ADMIN_USERNAME);
        admin.setFirstName("System");
        admin.setLastName("Administrator");
        admin.setPassword("admin");
        admin.setRole(Role.ADMIN);
        LOG.info("Generated admin user with credentials " + admin.getUsername() + "/" + admin.getPassword());
        userService.persist(admin);
    }
}
