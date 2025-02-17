package de.remsfal.service.control;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import de.remsfal.core.model.UserModel;
import de.remsfal.service.entity.dto.UserEntity;

/**
 * @author Alexander Stanik [alexander.stanik@htw-berlin.de]
 */
@ApplicationScoped
public class NotificationController {
    
    @Inject
    Logger logger;
    
    public void informUserAboutRegistration(UserEntity userEntity) {
        // TODO Auto-generated method stub
        
    }

    public void informUserAboutProjectMembership(final UserModel user) {
        logger.infov("TODO: User {0} will be informed about project membership", user.getEmail());
    }

}
