package de.remsfal.core.dto;
import jakarta.annotation.Nullable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.immutables.value.Value;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import de.remsfal.core.model.PropertyModel;

/**
 * @author Alexander Stanik [alexander.stanik@htw-berlin.de]
 */
@Value.Immutable
@Schema(description = "A property")
@JsonDeserialize(as = ImmutablePropertyJson.class)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public abstract class PropertyJson implements PropertyModel {

    @Null
    @Nullable
    public abstract String getId();

    @NotNull
    public abstract String getTitle();

    public static PropertyJson valueOf(PropertyModel model) {
        final ImmutablePropertyJson.Builder builder = ImmutablePropertyJson.builder()
                .id(model.getId())
                .projectId(model.getProjectId())
                .title(model.getTitle());
        return builder.build();
    }

}
