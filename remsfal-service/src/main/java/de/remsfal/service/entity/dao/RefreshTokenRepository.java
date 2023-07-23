package de.remsfal.service.entity.dao;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import de.remsfal.core.model.RefreshTokenModel;
import de.remsfal.service.entity.dto.ProjectEntity;
import de.remsfal.service.entity.dto.ProjectMembershipEntity;
import de.remsfal.service.entity.dto.RefreshTokenEntity;
import de.remsfal.service.entity.dto.UserEntity;

import static de.remsfal.service.entity.dao.AbstractRepository.PARAM_REFRESH_TOKEN;
import static io.quarkus.hibernate.orm.panache.Panache.getEntityManager;

/**
 * @author Alexander Stanik [alexander.stanik@htw-berlin.de]
 */
@ApplicationScoped
public class RefreshTokenRepository extends AbstractRepository<RefreshTokenEntity>{

    public RefreshTokenModel refreshTokenByToken(final String token) {
        List<RefreshTokenEntity> resultList = getEntityManager()
                .createNamedQuery("RefreshTokenEntity.findByUserId", RefreshTokenEntity.class)
                .setParameter(PARAM_REFRESH_TOKEN, token)
                .getResultList();

        if (!resultList.isEmpty()) {
            RefreshTokenEntity firstElement = resultList.get(0);
            return firstElement;
        } else {
            return null;
        }
    }
    }
