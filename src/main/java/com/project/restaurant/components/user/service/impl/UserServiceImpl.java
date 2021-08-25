package com.project.restaurant.components.user.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.restaurant.base.dtos.PageParams;
import com.project.restaurant.base.exceptions.BadRequestException;
import com.project.restaurant.base.exceptions.NotFoundException;
import com.project.restaurant.base.service.BaseService;
import com.project.restaurant.base.specifications.SpecificationFilter;
import com.project.restaurant.base.utils.ErrorMessage;
import com.project.restaurant.base.utils.LoggedUser;
import com.project.restaurant.base.utils.SearchCriteria;
import com.project.restaurant.components.cities.entities.City;
import com.project.restaurant.components.cities.repository.CityRepository;
import com.project.restaurant.components.items.repository.ItemRepository;
import com.project.restaurant.components.roles.entities.Roles;
import com.project.restaurant.components.roles.repository.RolesRepository;
import com.project.restaurant.components.user.converter.UserConverter;
import com.project.restaurant.components.user.dtos.UserCreateRequest;
import com.project.restaurant.components.user.dtos.UserDto;
import com.project.restaurant.components.user.dtos.UserUpdateRequest;
import com.project.restaurant.components.user.entities.User;
import com.project.restaurant.components.user.entities.UserDetails;
import com.project.restaurant.components.user.exceptions.UserBadRequestException;
import com.project.restaurant.components.user.repository.TokenRepo;
import com.project.restaurant.components.user.repository.UserDetailsRepository;
import com.project.restaurant.components.user.repository.UserRepository;
import com.project.restaurant.components.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@Service
@Transactional
public class UserServiceImpl extends BaseService implements UserService, org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private TokenRepo tokenRepo;

    @Value("${minLength}")
    private int minLength;

    private final LoggedUser loggedUser;

    @Autowired
    private UserConverter userConverter;

    public UserServiceImpl() {
        this.loggedUser = new LoggedUser(this);
    }

    public void save(UserCreateRequest userCreateRequest) {
        Roles roles = rolesRepository
                .findById(userCreateRequest.getRoleId())
                .orElseThrow(() ->
                        new UserBadRequestException(ErrorMessage.NOT_FOUND + " role with id "
                                + userCreateRequest.getRoleId(), userCreateRequest, "roleId"));
        City city = cityRepository.findById(userCreateRequest.getCityId())
                .orElseThrow(
                        () -> new UserBadRequestException(ErrorMessage.NOT_FOUND + " city with id "
                                + userCreateRequest.getCityId(), userCreateRequest, "cityId")
                );

        UserDetails user
                = userConverter.toEntity(userCreateRequest, roles, city, false);
        try {
            User foundUser = findByEmail(userCreateRequest.getEmail());

            if (foundUser.isEnabled())
                throw new UserBadRequestException(ErrorMessage.DUPLICATE + " user exists. Email : "
                        + userCreateRequest.getEmail(), userCreateRequest, "email");
            else
                throw new BadRequestException("We have already sent you the email in your email: "
                        + foundUser.getUserDetails().getEmail()
                        + "\n Please check it carefully");
        } catch (NotFoundException e) {
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            userDetailsRepository.save(user);
        }
    }

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() ->
                        new NotFoundException(ErrorMessage.NOT_FOUND + " user id = " + userId));
    }


    public void delete(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new NotFoundException(ErrorMessage.NOT_FOUND + " user id=" + userId));


        UserDetails userDetails = user.getUserDetails();
        userDetails.setDeleted(true);

        user.setDeleted(true);

        itemRepository.findAllBySellerId(userId)
                .forEach(
                        item -> item.setDeleted(true)
                );

        userDetailsRepository.save(userDetails);
        userRepository.save(user);
    }


    public User findByEmail(String email) {
        return userDetailsRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.NOT_FOUND + " user with email " + email))
                .getUser();
    }

    public void update(Long id, UserUpdateRequest request) {

        User loggedUser1 = loggedUser.getLoggedUser();

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException(ErrorMessage.NOT_FOUND + " " + id));

        if (!loggedUser1.getId().equals(user.getId())) {
            throw new BadRequestException(ErrorMessage.NOT_ALLOWED + " to modify the existing user since you aren't him");
        }

        if (request.getCityId() != null && request.getCityId() != 0) {
            City city = cityRepository.findById(request.getCityId())
                    .orElseThrow(
                            () -> new NotFoundException("City with this id wasn't found")
                    );
            user.getUserDetails().setCity(city);
        }

        if (isStringValid(request.getPassword(), minLength)) {
            user.getUserDetails().setPassword(new BCryptPasswordEncoder().encode(request.getPassword()));
        }
        if (isStringValid(request.getPhoneNumber(), minLength)) {
            user.setPhoneNumber(request.getPhoneNumber());
        }
        if (isStringValid(request.getEmail(), minLength)) {
            user.getUserDetails().setEmail(request.getEmail());
        }
        if (isStringValid(request.getAddress(), minLength)) {
            user.getUserDetails().setAddress(request.getAddress());
        }

        userRepository.save(user);

    }


    @Override
    public org.springframework.security.core.userdetails.User loadUserByUsername(String s) throws UsernameNotFoundException {

        UserDetails user
                = userDetailsRepository.findByEmailAndUserEnabled(s, true)
                .orElseThrow(() ->
                        new UsernameNotFoundException("The user with this username wasn't found"));

        log.info("User is present in the db {}", user);

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getUser().getRole().getName()));

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword()
                , authorities);

    }

    public Page<User> findAllSellers(PageParams pageParams) {
        return userRepository.findAllSellers(
                pageParams.getSortDirection().compareToIgnoreCase("asc") == 0
                        ?
                        PageRequest.of(pageParams.getPageNumber(), pageParams.getPageSize()
                                , Sort.Direction.ASC, pageParams.getSort())
                        :
                        PageRequest.of(pageParams.getPageNumber(), pageParams.getPageSize()
                                , Sort.Direction.DESC, pageParams.getSort()));
    }

    public List<UserDto> findAll() {
        return userConverter.toDtoList(userRepository.findAll());
    }

    public User getUser(String token) {
        return tokenRepo.findByToken(token).getUser();
    }

    public void registerUser(User user) {
        user.setEnabled(true);
        userRepository.save(user);
    }

    public List<User> filterSpecification(SearchCriteria searchCriteria) {
        return userRepository.findAll(new SpecificationFilter().userFilter(searchCriteria, User.class, ""));
    }

    public void generateAccessTokenFromRefreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {

            log.info("We're inside the refresh token");

            String refreshToken = authorizationHeader.substring(("Bearer ".length()));

            Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
            log.info("Decoding it");
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(refreshToken);
            log.info("Decoded it");
            String username = decodedJWT.getSubject();
            User user = findByEmail(username);
            List<String> roles = new ArrayList<>();
            roles.add(user.getRole().getName());

            log.info("Before generating access token");

            String accessToken = JWT.create().withSubject(user.getUserDetails().getEmail())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                    .withIssuer(request.getRequestURL().toString())
                    .withClaim("roles", roles)
                    .sign(algorithm);

            log.info("Generated access token {}", accessToken);
            Map<String, String> tokens = new HashMap<>();
            tokens.put("access_token", accessToken);
            tokens.put("refresh_token", refreshToken);
            response.setContentType(APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            log.info("End");
        } else {
            throw new NotFoundException("Refresh token is missing");
        }
    }
}
