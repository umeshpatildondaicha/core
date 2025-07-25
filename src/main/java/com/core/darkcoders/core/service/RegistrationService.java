package com.core.darkcoders.core.service;

import com.core.darkcoders.core.dto.DoctorRegistrationRequest;
import com.core.darkcoders.core.dto.PatientRegistrationRequest;
import com.core.darkcoders.core.exception.AuthenticationException;
import com.core.darkcoders.core.exception.DuplicateResourceException;
import com.core.darkcoders.core.model.Doctor;
import com.core.darkcoders.core.model.DoctorStatus;
import com.core.darkcoders.core.model.Patient;
import com.core.darkcoders.core.model.User;
import com.core.darkcoders.core.model.UserRole;
import com.core.darkcoders.core.repository.DoctorRepository;
import com.core.darkcoders.core.repository.PatientRepository;
import com.core.darkcoders.core.repository.UserRepository;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final Keycloak keycloak;

    @Value("${keycloak.realm}")
    private String realm;

    @Transactional
    public Doctor registerDoctor(DoctorRegistrationRequest request) {
        log.info("Starting doctor registration for email: {}", request.getEmail());
        
        // Check if doctor already exists
        if (doctorRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("A doctor with email " + request.getEmail() + " is already registered. Please use a different email address or contact support if you believe this is an error.");
        }

        // Ensure realm and roles exist
        ensureRealmAndRolesExist();

        // Create Keycloak user with password
        String keycloakUserId = createKeycloakUser(request.getEmail(), request.getPassword(), "ROLE_DOCTOR");
        log.info("Created Keycloak user with ID: {}", keycloakUserId);

        // Create Doctor entity
        Doctor doctor = new Doctor();
        doctor.setFirstName(request.getFirstName());
        doctor.setLastName(request.getLastName());
        doctor.setEmail(request.getEmail());
        doctor.setRegistrationNumber(request.getRegistrationNumber());
        doctor.setSpecialization(request.getSpecialization());
        doctor.setKeycloakId(keycloakUserId);
        doctor.setDoctorStatus(DoctorStatus.ACTIVE);

        Doctor savedDoctor = doctorRepository.save(doctor);
        log.info("Doctor registered successfully with ID: {}", savedDoctor.getDoctorId());
        
        return savedDoctor;
    }

    @Transactional
    public Patient registerPatient(PatientRegistrationRequest request) {
        log.info("Starting patient registration for email: {}", request.getEmail());
        
        // Check if patient already exists with the same email
        if (patientRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("A patient with email " + request.getEmail() + " is already registered. Please use a different email address or contact support if you believe this is an error.");
        }

        // Check if patient already exists with the same contact number
        if (patientRepository.existsByContact(request.getContact())) {
            throw new DuplicateResourceException("A patient with contact number " + request.getContact() + " is already registered. Please use a different contact number or contact support if you believe this is an error.");
        }

        // Create Patient entity
        Patient patient = new Patient();
        patient.setFirstName(request.getFirstName());
        patient.setLastName(request.getLastName());
        patient.setEmail(request.getEmail());
        patient.setDateOfBirth(request.getDateOfBirth());
        patient.setGender(request.getGender());
        patient.setContact(request.getContact());
        // Set a temporary keycloak ID that will be updated later when Keycloak is available
        String tempKeycloakId = "TEMP_" + UUID.randomUUID().toString();
        patient.setKeycloakId(tempKeycloakId);

        Patient savedPatient = patientRepository.save(patient);
        log.info("Patient registered successfully with ID: {}", savedPatient.getPatientId());

        // Create User record for authentication
        User user = new User();
        user.setUsername(request.getEmail());
        user.setEmail(request.getEmail());
        user.setMobileNumber(request.getContact());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setKeycloakId(tempKeycloakId);
        user.setRole(UserRole.ROLE_PATIENT);
        user.setEnabled(true);
        // Generate a random password since patients will use OTP-based authentication
        String randomPassword = UUID.randomUUID().toString();
        user.setPassword(randomPassword);
        userRepository.save(user);
        log.info("User record created for patient with ID: {}", savedPatient.getPatientId());
        
        return savedPatient;
    }

    private void ensureRealmAndRolesExist() {
        try {
            // Check if realm exists
            boolean realmExists = keycloak.realms().findAll().stream()
                    .anyMatch(r -> r.getRealm().equals(realm));

            if (!realmExists) {
                log.info("Creating realm: {}", realm);
                RealmRepresentation realmRep = new RealmRepresentation();
                realmRep.setRealm(realm);
                realmRep.setEnabled(true);
                keycloak.realms().create(realmRep);
            }

            RealmResource realmResource = keycloak.realm(realm);

            // Create roles if they don't exist
            Set<String> requiredRoles = new HashSet<>(Arrays.asList("ROLE_DOCTOR", "ROLE_PATIENT"));
            List<RoleRepresentation> existingRoles = realmResource.roles().list();
            Set<String> existingRoleNames = new HashSet<>();
            for (RoleRepresentation role : existingRoles) {
                existingRoleNames.add(role.getName());
            }

            for (String roleName : requiredRoles) {
                if (!existingRoleNames.contains(roleName)) {
                    log.info("Creating role: {}", roleName);
                    RoleRepresentation role = new RoleRepresentation();
                    role.setName(roleName);
                    role.setDescription(roleName + " role");
                    realmResource.roles().create(role);
                }
            }
        } catch (Exception e) {
            log.error("Error ensuring realm and roles exist: {}", e.getMessage(), e);
            throw new AuthenticationException("Failed to setup Keycloak realm and roles: " + e.getMessage());
        }
    }

    private String createKeycloakUser(String email, String role) {
        try {
            RealmResource realmResource = keycloak.realm(realm);
            UsersResource usersResource = realmResource.users();

            // Check if user already exists in Keycloak
            List<UserRepresentation> existingUsers = usersResource.search(email);
            if (!existingUsers.isEmpty()) {
                throw new DuplicateResourceException("A user with email " + email + " already exists in the system. Please use a different email address or contact support if you believe this is an error.");
            }

            // Create user representation
            UserRepresentation user = new UserRepresentation();
            user.setEnabled(true);
            user.setUsername(email);
            user.setEmail(email);
            user.setEmailVerified(true);

            // Create user in Keycloak without password
            Response response = usersResource.create(user);
            if (response.getStatus() != Response.Status.CREATED.getStatusCode()) {
                throw new AuthenticationException("Failed to create user in Keycloak. Status: " + response.getStatus());
            }

            // Get the created user's ID
            String userId = response.getLocation().getPath().substring(response.getLocation().getPath().lastIndexOf('/') + 1);

            // Get role representation
            RoleRepresentation roleRepresentation = realmResource.roles().get(role).toRepresentation();
            if (roleRepresentation == null) {
                throw new AuthenticationException("Role " + role + " not found in Keycloak");
            }

            // Assign role
            realmResource.users().get(userId).roles().realmLevel()
                    .add(Collections.singletonList(roleRepresentation));

            return userId;
        } catch (DuplicateResourceException e) {
            log.error("User already exists in Keycloak: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error creating Keycloak user: {}", e.getMessage(), e);
            throw new AuthenticationException("Failed to create user in Keycloak: " + e.getMessage());
        }
    }

    private String createKeycloakUser(String email, String password, String role) {
        try {
            RealmResource realmResource = keycloak.realm(realm);
            UsersResource usersResource = realmResource.users();

            // Check if user already exists in Keycloak
            List<UserRepresentation> existingUsers = usersResource.search(email);
            if (!existingUsers.isEmpty()) {
                throw new DuplicateResourceException("A user with email " + email + " already exists in the system. Please use a different email address or contact support if you believe this is an error.");
            }

            // Create user representation
            UserRepresentation user = new UserRepresentation();
            user.setEnabled(true);
            user.setUsername(email);
            user.setEmail(email);
            user.setEmailVerified(true);

            // Set credentials
            CredentialRepresentation credential = new CredentialRepresentation();
            credential.setType(CredentialRepresentation.PASSWORD);
            credential.setValue(password);
            credential.setTemporary(false);
            user.setCredentials(Collections.singletonList(credential));

            // Create user in Keycloak
            Response response = usersResource.create(user);
            if (response.getStatus() != Response.Status.CREATED.getStatusCode()) {
                throw new AuthenticationException("Failed to create user in Keycloak. Status: " + response.getStatus());
            }

            // Get the created user's ID
            String userId = response.getLocation().getPath().substring(response.getLocation().getPath().lastIndexOf('/') + 1);

            // Get role representation
            RoleRepresentation roleRepresentation = realmResource.roles().get(role).toRepresentation();
            if (roleRepresentation == null) {
                throw new AuthenticationException("Role " + role + " not found in Keycloak");
            }

            // Assign role
            realmResource.users().get(userId).roles().realmLevel()
                    .add(Collections.singletonList(roleRepresentation));

            return userId;
        } catch (DuplicateResourceException e) {
            log.error("User already exists in Keycloak: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error creating Keycloak user: {}", e.getMessage(), e);
            throw new AuthenticationException("Failed to create user in Keycloak: " + e.getMessage());
        }
    }
} 