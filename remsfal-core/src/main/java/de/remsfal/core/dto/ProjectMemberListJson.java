package de.remsfal.core.dto;

import javax.validation.constraints.NotNull;

import java.util.List;
import java.util.Set;

import de.remsfal.core.model.ProjectMemberModel;
import de.remsfal.core.model.ProjectModel;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.immutables.value.Value;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

/**
 * @author Alexander Stanik [alexander.stanik@htw-berlin.de]
 */
@Value.Immutable
@Schema(description = "A list of project members")
@JsonDeserialize(as = ImmutableProjectMemberListJson.class)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public abstract class ProjectMemberListJson {

    public static Object valueOf(Set<? extends ProjectMemberModel> members) {
        final ImmutableProjectMemberListJson.Builder builder = ImmutableProjectMemberListJson.builder();
        for(ProjectMemberModel model : members){
            builder.addMembers(ProjectMemberJson.valueOf(model));
        }
        return builder.build();
    }
    public static ProjectListJson valueOf(final List<ProjectModel> projects) {
        final ImmutableProjectListJson.Builder builder = ImmutableProjectListJson.builder();
        for(ProjectModel model : projects) {
            builder.addProjects(ProjectJson.valueOf(model));
        }
        return builder.build();
    }
    @NotNull
    public abstract List<ProjectMemberJson> getMembers();

    // TODO: pagination
}
