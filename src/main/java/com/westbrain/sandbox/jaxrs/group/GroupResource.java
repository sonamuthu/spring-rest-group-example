package com.westbrain.sandbox.jaxrs.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * A resource implementation which provides a REST-ful, JSON-based API to the groups resource and it's members
 * sub resource.
 *
 * <p>Leverages standard JAX-RS annotations for defining the API.</p>
 *
 * @author Eric Westfall (ewestfal@gmail.com)
 */
@Controller
@RequestMapping("/groups")
public class GroupResource {

    @Autowired
    private GroupRepository repository;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody Iterable<Group> getAllGroups() {
        return repository.findAll();
    }

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public  @ResponseBody Group getGroupById(@PathVariable("id") Long id) {
        Group group = repository.findOne(id);
        if (group == null) {
            throw new DoesNotExistException();
        }

        return group;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createGroup(@RequestBody Group group, HttpServletResponse response) {
        if (group == null) {
            throw new BadRequestException();
        }

        Group savedGroup = repository.save(group);

        String location = ServletUriComponentsBuilder.fromCurrentRequest()
                .pathSegment("{id}").buildAndExpand(savedGroup.getId())
                .toUriString();

        response.setHeader("Location",location);
    }

    @RequestMapping(value = "/{id}/update", method = RequestMethod.PUT)
    public @ResponseBody  void updateGroup(@PathVariable("id") Long id, @RequestBody Group group) {
        if (repository.findOne(id) == null) {
            throw new DoesNotExistException();
        }
        if (group == null || !id.equals(group.getId())) {
           throw new BadRequestException();
        }

        if (group.getId() == null) {
            // make sure we have the proper id set before we update
            group.setId(id);
        }
        repository.save(group);
    }

//    @POST
//    @Path("/{id}")
//    public Response partialUpdateGroup(@PathParam("id") Long id, Group group) {
//        // TODO: implement ability to perform a partial update via POST
//        return notFound();
//    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public @ResponseBody void deleteGroup(@PathVariable("id") Long id, HttpServletResponse response) throws IOException {
        Group removedGroup = null;
        if (id != null) {
            removedGroup = repository.delete(id);
        }
        if (removedGroup == null) {
            throw new DoesNotExistException();
        } else {
            response.sendRedirect("/groups");
        }
    }

    @RequestMapping(value="/{id}/members", method = RequestMethod.GET)
    public @ResponseBody  Iterable<Member> getGroupMembers(@PathVariable("id") Long id) {
        if (repository.findOne(id) == null) {
            throw new DoesNotExistException();
        }
        Iterable<Member> members = repository.findMembers(id);
        return members;
    }

    @RequestMapping(value="/{id}/members/{memberId}", method = RequestMethod.GET)
    public Member getGroupMemberById(@PathVariable("id") Long id, @PathVariable("memberId") Long memberId) {
        Member member = repository.findMember(id, memberId);
        if (member == null) {
            throw new DoesNotExistException();
        }
        return member;
    }
    @RequestMapping(value="/{id}/members",method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
      public void addMember(@PathVariable("id") Long id, Member member,HttpServletResponse response) {
        if (member == null) {
            throw new BadRequestException();
        }
        Member savedMember = repository.saveMember(id, member);

        String location = ServletUriComponentsBuilder.fromCurrentRequest()
                .pathSegment("{id}","members","{memberId}").buildAndExpand(id, savedMember.getId())
                .toUriString();

        response.setHeader("Location",location);

    }

//    @PUT
//    @Path("/{id}/members/{memberId}")
//    public Response updateMember(@PathParam("id") Long id, @PathParam("memberId") Long memberId, Member member) {
//        if (repository.findOne(id) == null) {
//            return notFound();
//        }
//        if (repository.findMember(id, memberId) == null) {
//            return notFound();
//        }
//        if (member == null || !Objects.equal(member.getId(), memberId)) {
//            return badRequest();
//        }
//        if (member.getId() == null) {
//            // make sure we have the proper id set before we update
//            member.setId(memberId);
//        }
//        repository.saveMember(id, member);
//        return Response.ok().build();
//    }
//
//    @POST
//    @Path("/{id}/members/{memberId}")
//    public Response partialUpdateMember(@PathParam("id") Long id, @PathParam("memberId") Long memberId, Member member) {
//        // TODO: implement ability to perform a partial update via POST
//        return notFound();
//    }
//
//    @DELETE
//    @Path("/{id}/members/{memberId}")
//    public Response deleteMember(@PathParam("id") Long id, @PathParam("memberId") Long memberId) {
//        Member removedMember = null;
//        if (id != null && memberId != null) {
//            removedMember = repository.deleteMember(id, memberId);
//        }
//        if (removedMember == null) {
//            return notFound();
//        } else {
//            return Response.noContent().build();
//        }
//    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleNotFound(DoesNotExistException ex) {
    }

    @ExceptionHandler
    @ResponseStatus((HttpStatus.BAD_REQUEST))
    public void handleBadRequest(BadRequestException ex) {
    }


}
