package com.project.restaurant.components.user.controller;

import com.project.restaurant.base.dtos.PageParams;
import com.project.restaurant.base.exceptions.BadRequestException;
import com.project.restaurant.base.utils.EmailUtil;
import com.project.restaurant.base.utils.SearchCriteria;
import com.project.restaurant.components.user.converter.UserConverter;
import com.project.restaurant.components.user.dtos.UserCreateRequest;
import com.project.restaurant.components.user.dtos.UserDto;
import com.project.restaurant.components.user.dtos.UserUpdateRequest;
import com.project.restaurant.components.user.entities.User;
import com.project.restaurant.components.user.entities.VerificationToken;
import com.project.restaurant.components.user.service.MailService;
import com.project.restaurant.components.user.service.UserService;
import com.project.restaurant.components.user.service.VerificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private MailService mailService;
    @Autowired
    private VerificationService verificationService;
    @Autowired
    private UserConverter userConverter;

    @Value("${confirmUrl}")
    private String url;

    @PostMapping("/save")
    public void save(@Valid @RequestBody UserCreateRequest userCreateRequest) {

        userService.save(userCreateRequest);
        User user = userService.findByEmail(userCreateRequest.getEmail());
        log.info(" user {}", user.getUserDetails().getEmail());
        VerificationToken v = verificationService.createVerificationToken(user);
        String uRL = url + v.getToken();
        mailService.sendEmail(
                EmailUtil.getSimpleMailMessage(
                        user.getUserDetails().getEmail()
                        , uRL)
        );
        log.info("Just sent app url {}" + uRL);

    }

    @GetMapping("/confirm-account")
    @ResponseStatus(HttpStatus.OK)
    public String confirumUserAccount(@RequestParam("token") String token){
        VerificationToken verificationToken = verificationService.getVerificationToken(token);
        if (verificationToken!=null)
        {
            userService.registerUser(verificationToken.getUser());
        }
        else {
            throw new BadRequestException("Token invalid");
        }
        return "Success!";
    }

    /**
     * Id is always required but when email is also given
     * it is prioritized and the method called will be findByEmail
     */
    @GetMapping
    @ResponseBody
    public ResponseEntity<UserDto> findById(@RequestParam(required = false) Long id, @RequestParam(required = false) String email) {
        return email != null && !email.isEmpty() ? ResponseEntity.ok(userConverter.toDto(userService.findByEmail(email))) :
                ResponseEntity.ok(userConverter.toDto(userService.findById(id)));
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public void updateUser(@RequestParam Long id, @RequestBody UserUpdateRequest userUpdateRequest){
        userService.update(id,userUpdateRequest);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam Long id){
        userService.delete(id);
    }

    @GetMapping("/fulluser")
    public ResponseEntity<User> find(Long id){
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping("/logout")
    public String logout(){
        SecurityContextHolder.getContext().setAuthentication(null);
        return "login";
    }

    @GetMapping("/sellers")
    public ResponseEntity<Page<UserDto>> displaySellers(PageParams pageParams) {
        return ResponseEntity.ok(userConverter.toDtoPage(userService.findAllSellers(pageParams)));
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAll() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        userService.generateAccessTokenFromRefreshToken(request, response);

    }

    @GetMapping("/filter")
    public ResponseEntity<List<UserDto>> filter(@RequestBody SearchCriteria searchCriteria) {
        return ResponseEntity.ok(userConverter.toDtoList(userService.filterSpecification(searchCriteria)));
    }

}
