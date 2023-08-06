package de.remsfal.service.boundary;

import java.net.URI;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.auth0.jwt.interfaces.DecodedJWT;
import de.remsfal.core.dto.*;
import de.remsfal.core.model.CustomerModel;
import de.remsfal.core.model.ProjectMemberModel;
import de.remsfal.core.model.UserModel;
import de.remsfal.service.control.AuthController;
import de.remsfal.service.control.UserController;
import org.jboss.logging.Logger;

import de.remsfal.core.ProjectEndpoint;
import de.remsfal.core.model.ProjectModel;
import de.remsfal.service.boundary.authentication.RemsfalPrincipal;
import de.remsfal.service.control.ProjectController;
import javax.validation.Valid;

/**
 * @author Alexander Stanik [alexander.stanik@htw-berlin.de]
 */
@Path(ProjectEndpoint.CONTEXT + "/" + ProjectEndpoint.VERSION + "/" + ProjectEndpoint.SERVICE)
public class ProjectResource implements ProjectEndpoint {

    @Context
    UriInfo uri;

    @Inject
    RemsfalPrincipal principal;
    
    @Inject
    Logger logger;

    @Inject
    ProjectController controller;

    @Inject
    AuthController authController;
    @Inject
    UserController userController;
    @Override
    public Response getProjects() {
        List<ProjectModel> projects = controller.getProjects(principal);
        if(projects.isEmpty()) {
            throw new NotFoundException("No projects for this user fond");
        }
        return Response.ok(ProjectListJson.valueOf(projects)).build();
    }

    @Override
    public Response createProject(final ProjectJson project) {
        if(project.getId() != null) {
            throw new BadRequestException("ID should not be provided by the client");
        }
        final ProjectModel model = controller.createProject(principal, project);
        final URI location = uri.getAbsolutePathBuilder().path(model.getId()).build();
        return Response.created(location)
            .type(MediaType.APPLICATION_JSON)
            .entity(ProjectJson.valueOf(model))
            .build();
    }

    @Override
    public Response getProject(final String projectId) {
        boolean isAuthorized = authController.isOneOfGivenRolesInProject(projectId, new ProjectMemberModel.UserRole[]{
                ProjectMemberModel.UserRole.PROPRIETOR,
                ProjectMemberModel.UserRole.MANAGER,
                ProjectMemberModel.UserRole.LESSOR,
                ProjectMemberModel.UserRole.LESSEE,
                ProjectMemberModel.UserRole.CONSULTANT,
                ProjectMemberModel.UserRole.CARETAKER,
        }, authController.getJwt());
        if(!isAuthorized) { return Response.status(Response.Status.FORBIDDEN).entity("You don't have the rights to access this resource.").build(); }
        if(projectId == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid project ID")
                    .build();
        }

        final ProjectModel model = controller.getProject(principal, projectId);
        return Response.ok(ProjectJson.valueOf(model)).build();
    }

    @Override
    public Response getUserRoleForProject(final String projectId) {
        boolean isAuthorized = authController.isOneOfGivenRolesInProject(projectId, new ProjectMemberModel.UserRole[]{
                ProjectMemberModel.UserRole.PROPRIETOR,
                ProjectMemberModel.UserRole.MANAGER,
                ProjectMemberModel.UserRole.LESSOR,
                ProjectMemberModel.UserRole.LESSEE,
                ProjectMemberModel.UserRole.CONSULTANT,
                ProjectMemberModel.UserRole.CARETAKER,
        }, authController.getJwt());
        if(!isAuthorized) { return Response.status(Response.Status.FORBIDDEN).entity("You don't have the rights to access this resource.").build(); }
        if(projectId == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid project ID")
                    .build();
        }
        try{
            DecodedJWT jwt =  authController.getDecodedJWT(authController.getJwt());
            CustomerModel user = userController.getUser(jwt.getSubject());
            ProjectMemberModel.UserRole role = controller.getUserRoleInProject(user, projectId);
            String roleString = role.toString();
            System.out.println("roleString " + roleString);
            return Response.ok(roleString).build();
        }
        catch(Exception e){
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Bad Request")
                    .build();
        }
    }


    @Override
    public Response updateProject(final String projectId, @Valid final ProjectJson project) {
        boolean isAuthorized = authController.isOneOfGivenRolesInProject(projectId, new ProjectMemberModel.UserRole[]{
                ProjectMemberModel.UserRole.MANAGER,
        }, authController.getJwt());
        if(!isAuthorized) { return Response.status(Response.Status.FORBIDDEN).entity("You don't have the rights to access this resource.").build(); }
        if(projectId == null || projectId.isBlank()) {
            throw new BadRequestException("Invalid project ID");
        }
        final ProjectModel model = controller.updateProject(principal, projectId, project);
        final ProjectJson json = ProjectJson.valueOf(model);
        return Response.ok(json).build();
    }


    @Override
    public Response deleteProject(final String projectId) {
        boolean isAuthorized = authController.isOneOfGivenRolesInProject(projectId, new ProjectMemberModel.UserRole[]{
                ProjectMemberModel.UserRole.MANAGER
        }, authController.getJwt());
        if(!isAuthorized) { return Response.status(Response.Status.FORBIDDEN).entity("You don't have the rights to access this resource.").build(); }
        if(projectId == null || projectId.isBlank()) {
            throw new BadRequestException("Invalid project ID");
        }
        controller.deleteProject(principal, projectId);
        return Response.status(Response.Status.NO_CONTENT).build();  // successfully deleted
    }


    @Override
    public Response addProjectMember(final String projectId, final ProjectMemberJson member) {
        boolean isAuthorized = authController.isOneOfGivenRolesInProject(projectId, new ProjectMemberModel.UserRole[]{
                ProjectMemberModel.UserRole.MANAGER,

        }, authController.getJwt());
        if(!isAuthorized) { return Response.status(Response.Status.FORBIDDEN).entity("You don't have the rights to access this resource.").build(); }
        if(projectId == null || projectId.isBlank()) {
            throw new BadRequestException("Invalid project ID");
        }
        try {
            UserModel user = userController.getUserByEmail(member.getEmail());
            ProjectModel project = controller.addProjectMember(user, projectId, member);
            return Response.ok(ProjectJson.valueOf(project)).build();
        }
catch (Exception e){
    return Response.status(Response.Status.BAD_REQUEST)
            .entity("Bad Request")
            .build();
}
    }

    @Override
    public Response getProjectMembers(final String projectId) {
        boolean isAuthorized = authController.isOneOfGivenRolesInProject(projectId, new ProjectMemberModel.UserRole[]{
                ProjectMemberModel.UserRole.PROPRIETOR,
                ProjectMemberModel.UserRole.MANAGER,
                ProjectMemberModel.UserRole.LESSOR,
                ProjectMemberModel.UserRole.LESSEE,
                ProjectMemberModel.UserRole.CONSULTANT,
                ProjectMemberModel.UserRole.CARETAKER,

        }, authController.getJwt());
        if(!isAuthorized) { return Response.status(Response.Status.FORBIDDEN).entity("You don't have the rights to access this resource.").build(); }
        if(projectId == null || projectId.isBlank()) {
            throw new BadRequestException("Invalid project ID");
        }
        Set<? extends ProjectMemberModel> members =  controller.getProjectMembers(principal, projectId);
        if(members.isEmpty()) {
            throw new NotFoundException("No members for this project fond");
        }
        return Response.ok(ProjectMemberListJson.valueOf(members)).build();
    }

    @Override
    public Response updateProjectMember(String projectId, ProjectMemberJson member, ProjectJson project){
        boolean isAuthorized = authController.isOneOfGivenRolesInProject(projectId, new ProjectMemberModel.UserRole[]{
                ProjectMemberModel.UserRole.MANAGER,

        }, authController.getJwt());
        if(!isAuthorized) { return Response.status(Response.Status.FORBIDDEN).entity("You don't have the rights to access this resource.").build(); }
        if(projectId == null || projectId.isBlank()) {
            throw new BadRequestException("Invalid project ID");
        }
        UserModel user = userController.getUser(member.getId());

        controller.changeProjectMemberRole(user, projectId, member);
        Set<? extends ProjectMemberModel> members =  controller.getProjectMembers(principal, projectId);
        if(members.isEmpty()) {
            throw new NotFoundException("No members for this project fond");
        }
        return Response.ok(ProjectMemberListJson.valueOf(members)).build();
    }

    @Override
    public Response deleteProjectMember(String projectId, String memberId){
        boolean isAuthorized = authController.isOneOfGivenRolesInProject(projectId, new ProjectMemberModel.UserRole[]{
                ProjectMemberModel.UserRole.MANAGER,

        }, authController.getJwt());
        if(!isAuthorized) { return Response.status(Response.Status.FORBIDDEN).entity("You don't have the rights to access this resource.").build(); }
        if(projectId == null || projectId.isBlank()) {
            throw new BadRequestException("Invalid project ID");
        }
        UserModel user = userController.getUser(memberId);

        controller.removeProjectMember(user, projectId);
        Set<? extends ProjectMemberModel> members =  controller.getProjectMembers(principal, projectId);
        if(members.isEmpty()) {
            throw new NotFoundException("No members for this project fond");
        }
        return Response.ok(ProjectMemberListJson.valueOf(members)).build();

    }


}