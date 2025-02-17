package de.remsfal.core.dto;

import jakarta.annotation.Nullable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import java.util.Set;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.immutables.value.Value;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import de.remsfal.core.model.ProjectMemberModel;
import de.remsfal.core.model.ProjectModel;

/**
 * @author Alexander Stanik [alexander.stanik@htw-berlin.de]
 */
@Value.Immutable
@Schema(description = "A project")
@JsonDeserialize(as = ImmutableProjectJson.class)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public abstract class ProjectJson implements ProjectModel {

    @Null
    @Nullable
    public abstract String getId();

    @NotNull
    public abstract String getTitle();

    @Null
    @Nullable
    public abstract Set<ProjectMemberJson> getMembers();

    public static ProjectJson valueOf(ProjectModel model) {
        final ImmutableProjectJson.Builder builder = ImmutableProjectJson.builder()
            .id(model.getId())
            .title(model.getTitle());
        for(ProjectMemberModel member : model.getMembers()) {
            builder.addMembers(ProjectMemberJson.valueOf(member));
        }
        return builder.build();
    }

}
